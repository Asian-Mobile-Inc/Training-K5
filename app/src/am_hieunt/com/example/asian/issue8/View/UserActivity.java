package com.example.asian.issue8.View;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asian.R;
import com.example.asian.issue8.Model.User;
import com.example.asian.issue8.ViewModel.UserViewModel;

public class UserActivity extends AppCompatActivity {
    private EditText mEdtUserName, mEdtAge;
    private ScrollView mSvListUser;

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
        mEdtUserName = findViewById(R.id.edtUserName);
        mEdtAge = findViewById(R.id.edtAge);
//        mSvListUser = findViewById(R.id.svListUser);
        initListener();
        User user = new User();
//        user.setUsername("Hieu");
//        user.setAge(21);
//        UserViewModel.getInstance(this).insertUser(user);
//        user = UserViewModel.getInstance(this).getUser(1);
//        mTvUserName.setText(user.getUsername());
//        mTvAge.setText(user.getAge() + "");
//        UserViewModel.getInstance(this).deleteAllUser();
    }

    private void initListener() {

    }
}
