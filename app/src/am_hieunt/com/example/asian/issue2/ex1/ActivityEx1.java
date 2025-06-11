package com.example.asian.issue2.ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asian.R;
import com.example.asian.issue3.ActivityEx1_1;

public class ActivityEx1 extends AppCompatActivity {
    private EditText mUsername, mPassword;
    private boolean mCheck;
    private Drawable mIcon;
    private Button mLogin;
    private TextView mTvErrorUsername, mTvErrorPassword;
    private static final String INTENTCODE = "username";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex1);

        mCheck = true;
        mUsername = findViewById(R.id.edtUsername);
        mPassword = findViewById(R.id.edtPassword);
        mLogin = findViewById(R.id.btnLogin);
        mTvErrorUsername = findViewById(R.id.tvErrorUsername);
        mTvErrorPassword = findViewById(R.id.tvErrorPassword);
        mUsername.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUsername.setFocusable(true);
                mUsername.setSelected(false);
                if (s.length() == 0) {
                    mUsername.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else {
                    Drawable deleteIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ic_delete);
                    mUsername.setCompoundDrawablesWithIntrinsicBounds(null, null, deleteIcon, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mUsername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTvErrorUsername.setVisibility(View.GONE);
                if (mUsername.getText().toString().isEmpty()) {
                    mUsername.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else {
                    Drawable deleteIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ic_delete);
                    mUsername.setCompoundDrawablesWithIntrinsicBounds(null, null, deleteIcon, null);
                }
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(mUsername.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                        int touchAreaStart = mUsername.getWidth() - mUsername.getPaddingRight() - mUsername.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                        if(event.getX() >= touchAreaStart) {
                            mUsername.setText("");
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else {
                    if (mCheck) {
                        mIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ic_hide);
                    } else {
                        mIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ic_eye);
                    }
                    mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, mIcon, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTvErrorPassword.setVisibility(View.GONE);
                if (mCheck) {
                    mIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ic_hide);
                } else {
                    mIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ic_eye);
                }
                mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, mIcon, null);

                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(mPassword.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                        int touchAreaStart = mPassword.getWidth() - mPassword.getPaddingRight() - mPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                        if(event.getX() >= touchAreaStart) {
                            if (mCheck) {
                                mPassword.setInputType(InputType.TYPE_CLASS_TEXT);;
                                mIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ic_eye);
                                mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, mIcon, null);
                                mCheck = false;
                            } else {
                                mCheck = true;
                                mPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                mIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ic_hide);
                                mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, mIcon, null);
                            }
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean kt = true;
                mUsername.setFocusable(false);
                mUsername.setFocusableInTouchMode(false);
                mPassword.setFocusable(false);
                mPassword.setFocusableInTouchMode(false);
                if (mUsername.getText().toString().length() > 10 && mUsername.getText().toString().substring(mUsername.getText().toString().length() - 10).equals("@gmail.com")) {
                    mTvErrorUsername.setVisibility(View.GONE);
                    mUsername.setSelected(false);
                } else {
                    mTvErrorUsername.setVisibility(View.VISIBLE);
                    String errorUsername = getString(R.string.error_username);
                    mTvErrorUsername.setText(errorUsername);
                    kt = false;
                    mIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ic_alert);
                    mUsername.setCompoundDrawablesWithIntrinsicBounds(null, null, mIcon, null);
                    mUsername.setSelected(true);
                }
                String specialCharRegex= ".*[@#!$%^&+=].*";
                String upperCaseRegex= ".*[A-Z].*";
                String numberRegex= ".*[0-9].*";
                if (mPassword.getText().toString().length() >= 8 && (mPassword.getText().toString().matches(specialCharRegex)
                    || mPassword.getText().toString().matches(upperCaseRegex) || mPassword.getText().toString().matches(numberRegex))) {
                    mTvErrorPassword.setVisibility(View.GONE);
                    mPassword.setSelected(false);
                } else {
                    mTvErrorPassword.setVisibility(View.VISIBLE);
                    String errorPassword = getString(R.string.error_password);
                    mTvErrorPassword.setText(errorPassword);
                    kt = false;
                    mIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ic_alert);
                    mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, mIcon, null);
                    mPassword.setSelected(true);
                }

                mUsername.setFocusable(true);
                mUsername.setFocusableInTouchMode(true);
                mPassword.setFocusable(true);
                mPassword.setFocusableInTouchMode(true);
                if (kt) {
                    Intent intent = new Intent(ActivityEx1.this, ActivityEx1_1.class);
                    intent.putExtra(INTENTCODE, mUsername.getText().toString());
                    startActivity(intent);
                }

            }
        });
    }
}
