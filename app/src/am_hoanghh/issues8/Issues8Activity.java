package issues8;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.asian.R;
import com.example.asian.databinding.ActivityIssues8Binding;
import com.example.asian.databinding.DialogConfirmDeleteBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.List;
import java.util.Objects;

public class Issues8Activity extends AppCompatActivity {
    private ActivityIssues8Binding mBinding;
    private DBHandler mDbHandler;
    private UserAdapter mUserAdapter;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityIssues8Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initBottomSheet();
        initDBHandler();
        initDialog();
        initListeners();
        initAdapter();
    }

    private void initBottomSheet() {
        BottomSheetBehavior<ConstraintLayout> behavior = BottomSheetBehavior.from(mBinding.bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void initListeners() {
        mBinding.viewShowAll.setOnClickListener(v -> {
            mBinding.ivUser.setVisibility(View.GONE);
            mBinding.tvDescription.setVisibility(View.GONE);
            mBinding.viewShowAll.setVisibility(View.GONE);
            mBinding.rvUsers.setVisibility(View.VISIBLE);
            mBinding.flDeleteAll.setVisibility(View.VISIBLE);
        });

        mBinding.tvAddUser.setOnClickListener(v -> {
            String name = mBinding.edtUsername.getText().toString();
            String age = mBinding.edtAge.getText().toString();

            if (name.isEmpty() || age.isEmpty()) {
                Toast.makeText(this, getString(R.string.toast_text_please_enter_all_data), Toast.LENGTH_SHORT).show();
            } else {
                mDbHandler.addNewUser(name, age);
                Toast.makeText(this, getString(R.string.toast_text_user_has_been_added), Toast.LENGTH_SHORT).show();
                mBinding.edtUsername.setText("");
                mBinding.edtAge.setText("");
                mBinding.edtUsername.clearFocus();
                mBinding.edtAge.clearFocus();
                updateDataUsers();
            }
        });

        mBinding.flDeleteAll.setOnClickListener(v -> initDialogListeners());
    }

    private void initAdapter() {
        List<UserModel> userModelLists = mDbHandler.readUsers();
        mUserAdapter = new UserAdapter(mDbHandler);
        mBinding.rvUsers.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvUsers.setAdapter(mUserAdapter);
        mUserAdapter.submitList(userModelLists);
    }

    private void initDBHandler() {
        mDbHandler = new DBHandler(this);
    }

    private void updateDataUsers() {
        List<UserModel> userModelLists = mDbHandler.readUsers();
        mUserAdapter.submitList(userModelLists);
    }

    private void initDialog() {
        mDialog = new Dialog(this);
    }

    private void initDialogListeners() {
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DialogConfirmDeleteBinding dialogConfirmDeleteBinding = DialogConfirmDeleteBinding.inflate(getLayoutInflater());
        mDialog.setContentView(dialogConfirmDeleteBinding.getRoot());
        Objects.requireNonNull(mDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.setCancelable(false);

        dialogConfirmDeleteBinding.tvExplain.setText(getString(R.string.textview_text_are_you_sure_you_want_to_delete_all, mUserAdapter.getCurrentList().size()));
        dialogConfirmDeleteBinding.tvCancel.setOnClickListener(view -> mDialog.dismiss());
        dialogConfirmDeleteBinding.tvConfirm.setOnClickListener(view -> {
            mDialog.dismiss();
            mDbHandler.deleteAll();
            updateDataUsers();
        });
        mDialog.show();
    }
}
