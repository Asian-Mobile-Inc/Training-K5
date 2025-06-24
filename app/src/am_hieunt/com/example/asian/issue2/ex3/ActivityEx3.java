package com.example.asian.issue2.ex3;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asian.R;
import com.google.android.material.textfield.TextInputEditText;

public class ActivityEx3 extends AppCompatActivity {
    private View mVLine;
    private TextView mTvFullName, mTvNational, mTvErrorFullName, mTvErrorNational, mTvErrorAddInfo;
    private EditText mEdtFullName, mEdtNational;
    private TextInputEditText mTiAddInfo;
    private ScrollView mSvMenu;
    private float mStartY;
    private Button mBtnSubmit;
    private Drawable mIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mVLine = findViewById(R.id.vLine);
        mTvFullName = findViewById(R.id.tvFullName);
        mTvNational = findViewById(R.id.tvNational);
        mSvMenu = findViewById(R.id.svMenu);
        mTvErrorFullName = findViewById(R.id.tvErrorFullName);
        mTvErrorNational = findViewById(R.id.tvErrorNational);
        mTvErrorAddInfo = findViewById(R.id.tvErrorAddInfo);
        mBtnSubmit = findViewById(R.id.btnSubmit);
        mEdtFullName = findViewById(R.id.edtFullName);
        mEdtNational = findViewById(R.id.edtNational);
        mTiAddInfo = findViewById(R.id.tiAddInfo);
        mTvFullName.setText(initText(getString(R.string.full_name)));
        mTvNational.setText(initText(getString(R.string.national_id)));
        initListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        mVLine.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float currentY = event.getRawY();
                        float deltaY = currentY - mStartY;
                        if (deltaY < 50) {
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mSvMenu.getLayoutParams();
                            params.topMargin = dpToPx(20);
                            mSvMenu.setLayoutParams(params);
                        } else {
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mSvMenu.getLayoutParams();
                            params.topMargin = dpToPx(120);
                            mSvMenu.setLayoutParams(params);
                        }
                        return true;
                }
                return false;
            }
        });

        mEdtFullName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTvErrorFullName.setVisibility(View.GONE);
                mEdtFullName.setSelected(false);
                mEdtFullName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                return false;
            }
        });

        mEdtNational.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTvErrorNational.setVisibility(View.GONE);
                mEdtNational.setSelected(false);
                mEdtNational.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                return false;
            }
        });

        mTiAddInfo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mTvErrorAddInfo.setVisibility(View.GONE);
                mTiAddInfo.setSelected(false);
                mTiAddInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                return false;
            }
        });

        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtFullName.setFocusable(false);
                mEdtFullName.setFocusableInTouchMode(false);
                mEdtNational.setFocusable(false);
                mEdtNational.setFocusableInTouchMode(false);
                mTiAddInfo.setFocusable(false);
                mTiAddInfo.setFocusableInTouchMode(false);
                if (mEdtFullName.getText().toString().length() >= 3) {
                    mEdtFullName.setSelected(false);
                    mTvErrorFullName.setVisibility(View.GONE);
                } else {
                    mEdtFullName.setSelected(true);
                    String errorFullName = getString(R.string.error_full_name);
                    mTvErrorFullName.setText(errorFullName);
                    mTvErrorFullName.setVisibility(View.VISIBLE);
                    mIcon = ContextCompat.getDrawable(ActivityEx3.this, R.drawable.ic_alert);
                    mEdtFullName.setCompoundDrawablesWithIntrinsicBounds(null, null, mIcon, null);
                }
                String numberRegex= ".*[0-9].*";
                if (mEdtNational.getText().toString().matches(numberRegex) && mEdtNational.getText().toString().length() == 9) {
                    mEdtNational.setSelected(false);
                    mTvErrorNational.setVisibility(View.GONE);
                } else {
                    mEdtNational.setSelected(true);
                    mTvErrorNational.setVisibility(View.VISIBLE);
                    String errorNational = getString(R.string.error_national_id);
                    mTvErrorNational.setText(errorNational);
                    mIcon = ContextCompat.getDrawable(ActivityEx3.this, R.drawable.ic_alert);
                    mEdtNational.setCompoundDrawablesWithIntrinsicBounds(null, null, mIcon, null);
                }
                if (mTiAddInfo.getText().toString().length() >= 100) {
                    mTiAddInfo.setSelected(false);
                    mTvErrorAddInfo.setVisibility(View.GONE);
                } else {
                    mTiAddInfo.setSelected(true);
                    mTvErrorAddInfo.setVisibility(View.VISIBLE);
                    String errorAddInfo = getString(R.string.error_additional_information);
                    mTvErrorAddInfo.setText(errorAddInfo);
                    mIcon = ContextCompat.getDrawable(ActivityEx3.this, R.drawable.ic_alert);
                    mTiAddInfo.setCompoundDrawablesWithIntrinsicBounds(null, null, mIcon, null);
                }
                mEdtFullName.setFocusable(true);
                mEdtFullName.setFocusableInTouchMode(true);
                mEdtNational.setFocusable(true);
                mEdtNational.setFocusableInTouchMode(true);
                mTiAddInfo.setFocusable(true);
                mTiAddInfo.setFocusableInTouchMode(true);
            }
        });
    }

    private String initText(String str) {
        SpannableStringBuilder spannable = new SpannableStringBuilder(str);
        spannable.setSpan(
                new ForegroundColorSpan(Color.RED),
                0,
                1,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );
        return spannable.toString();
    }

    private int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }

}
