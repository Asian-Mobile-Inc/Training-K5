package com.example.asian.issue8.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asian.R;
import com.example.asian.issue8.adapter.UserAdapter;
import com.example.asian.issue8.database.UserDatabase;
import com.example.asian.issue8.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {
    private EditText mEdtUserName, mEdtAge;
    private RecyclerView mRvListUser;
    private UserAdapter mUserAdapter;
    private Button mBtnShow, mTvDeleteDialogAll, mBtnAddUser;
    private TextView mTvLabel, mTvContent, mTvCancelDialog, mTvDeleteDialog;;
    private ImageView mIvList;
    private List<User> mUsers;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.clListUser), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });
        mEdtUserName = findViewById(R.id.edtUserName);
        mEdtAge = findViewById(R.id.edtAge);
        mBtnShow = findViewById(R.id.btnShow);
        mRvListUser = findViewById(R.id.rvListUser);
        mTvDeleteDialogAll = findViewById(R.id.btnDeleteAll);
        mBtnAddUser = findViewById(R.id.btnAddUser);
        mRvListUser.setVisibility(View.GONE);
        mTvDeleteDialogAll.setVisibility(View.GONE);
        mTvLabel = findViewById(R.id.tvLabel);
        mIvList = findViewById(R.id.ivList);
        mUsers = new ArrayList<>();
        mUsers = UserDatabase.getInstance(this).getAllUser();
        mUserAdapter = new UserAdapter(this);
        mRvListUser.setAdapter(mUserAdapter);
        mUserAdapter.submitList(mUsers);
        mRvListUser.setLayoutManager(new LinearLayoutManager(this));
        initAlertDialog();
        initListener();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void initListener() {
        mBtnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvLabel.setVisibility(View.GONE);
                mIvList.setVisibility(View.GONE);
                mBtnShow.setVisibility(View.GONE);
                mRvListUser.setVisibility(View.VISIBLE);
                mTvDeleteDialogAll.setVisibility(View.VISIBLE);
            }
        });
        mTvDeleteDialogAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvContent.setText(getString(R.string.are_you_sure_you_want_to_delete_all) + " " + mUsers.size() + " " + getString(R.string.users) + " ?");
                mAlertDialog.show();
            }
        });
        mTvCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        mTvDeleteDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                UserDatabase.getInstance(UserActivity.this).deleteAllUser();
                List<User> users = new ArrayList<>();
                mUserAdapter.submitList(users);
            }
        });
        mEdtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEdtUserName.setSelected(s.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mEdtAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEdtAge.setSelected(s.length() != 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mBtnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEdtUserName.length() != 0 && mEdtAge.length() != 0) {
                    User user = new User();
                    user.setUsername(mEdtUserName.getText().toString());
                    user.setAge(Integer.parseInt(mEdtAge.getText().toString()));
                    UserDatabase.getInstance(UserActivity.this).insertUser(user);
                    List<User> users = new ArrayList<>(mUserAdapter.getCurrentList());
                    users.add(UserDatabase.getInstance(UserActivity.this).getNewUser());
                    mUserAdapter.submitList(users);
                }
            }
        });

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initAlertDialog() {
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View mDialogView = inflater.inflate(R.layout.item_dialog, null);
        mTvContent = mDialogView.findViewById(R.id.tvContent);
        mTvCancelDialog = mDialogView.findViewById(R.id.tvCancel);
        mTvDeleteDialog = mDialogView.findViewById(R.id.tvDelete);
        mDialogBuilder.setView(mDialogView);
        mAlertDialog = mDialogBuilder.create();
        if (mAlertDialog.getWindow() != null) {
            mAlertDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.my_dialog));
        }
    }
}
