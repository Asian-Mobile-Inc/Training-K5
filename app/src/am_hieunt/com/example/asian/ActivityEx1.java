package com.example.asian;

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

public class ActivityEx1 extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtPassword;
    private boolean kt;
    private Drawable viewIcon;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex1);

        kt = true;
        edtUsername = findViewById(R.id.edit_username);
        edtPassword = findViewById(R.id.edit_password);
        edtUsername.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        edtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    edtUsername.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else {
                    Drawable deleteIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.streamline_delete_1_remix);
                    edtUsername.setCompoundDrawablesWithIntrinsicBounds(null, null, deleteIcon, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edtUsername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(edtUsername.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                        int touchAreaStart = edtUsername.getWidth() - edtUsername.getPaddingRight() - edtUsername.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                        if(event.getX() >= touchAreaStart) {
                            edtUsername.setText("");
                            return true;
                        }
                    }
                }
                return false;
            }
        });

        viewIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ci_hide);
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                } else {
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, viewIcon, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(edtPassword.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                        int touchAreaStart = edtPassword.getWidth() - edtPassword.getPaddingRight() - edtPassword.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width();
                        if(event.getX() >= touchAreaStart) {
                            if (kt) {
                                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT);;
                                viewIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.mi_eye);
                                edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, viewIcon, null);
                                kt = false;
                            } else {
                                kt = true;
                                edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                                viewIcon = ContextCompat.getDrawable(ActivityEx1.this, R.drawable.ci_hide);
                                edtPassword.setCompoundDrawablesWithIntrinsicBounds(null, null, viewIcon, null);
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