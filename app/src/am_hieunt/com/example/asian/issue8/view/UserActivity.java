package com.example.asian.issue8.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
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
    private UserAdapter mUserAdapter;
    private Button mBtnShow, mTvDeleteAll, mBtnAddUser;
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
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initAlertDialog();
        initListener();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void initListener() {
        mBtnShow.setOnClickListener(v -> {
            mTvLabel.setVisibility(View.GONE);
            mIvList.setVisibility(View.GONE);
            mBtnShow.setVisibility(View.GONE);
            mTvDeleteAll.setVisibility(View.VISIBLE);
            mUserAdapter.submitList(mUsers);
        });
        mTvDeleteAll.setOnClickListener(v -> {
            if (!mUserAdapter.getCurrentList().isEmpty()) {
                mTvContent.setText(getString(R.string.are_you_sure_you_want_to_delete_all) + " " + mUserAdapter.getCurrentList().size() + " " + getString(R.string.users) + " ?");
                mAlertDialog.show();
            }
        });
        mTvCancelDialog.setOnClickListener(v -> {
            mAlertDialog.dismiss();
        });
        mTvDeleteDialog.setOnClickListener(v -> {
            mAlertDialog.dismiss();
            UserDatabase.getInstance(UserActivity.this).deleteAllUser();
            mUsers = new ArrayList<>();
            mUserAdapter.submitList(mUsers);
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
        mBtnAddUser.setOnClickListener(v -> {
            if (mEdtUserName.length() != 0 && mEdtAge.length() != 0) {
                User user = new User();
                user.setUsername(mEdtUserName.getText().toString());
                user.setAge(Integer.parseInt(mEdtAge.getText().toString()));
                UserDatabase.getInstance(UserActivity.this).insertUser(user);
                List<User> users = new ArrayList<>(mUserAdapter.getCurrentList());
                users.add(UserDatabase.getInstance(UserActivity.this).getNewUser());
                mUserAdapter.submitList(users);
            }
        });
    }

    private void initView() {
        mEdtUserName = findViewById(R.id.edtUserName);
        mEdtAge = findViewById(R.id.edtAge);
        mBtnShow = findViewById(R.id.btnShow);
        RecyclerView mRvListUser = findViewById(R.id.rvListUser);
        mTvDeleteAll = findViewById(R.id.btnDeleteAll);
        mBtnAddUser = findViewById(R.id.btnAddUser);
        mTvDeleteAll.setVisibility(View.GONE);
        mTvLabel = findViewById(R.id.tvLabel);
        mIvList = findViewById(R.id.ivList);
        mUsers = new ArrayList<>();
        mUsers = UserDatabase.getInstance(this).getAllUser();
        mUserAdapter = new UserAdapter(this);
        mRvListUser.setAdapter(mUserAdapter);
        mRvListUser.setLayoutManager(new LinearLayoutManager(this));
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
