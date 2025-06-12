package com.example.asian.issue2.ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.example.asian.R;

public class ActivityEx1 extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;
    private boolean mCheck;
    private Drawable mIcon;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex1);

        mCheck = true;
        mUsername = findViewById(R.id.edtUsername);
        mPassword = findViewById(R.id.edtPassword);
        mUsername.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        mUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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

        mIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ic_hide);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else {
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
    }
}
