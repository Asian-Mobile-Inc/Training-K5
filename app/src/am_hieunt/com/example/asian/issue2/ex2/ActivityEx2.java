package com.example.asian.issue2.ex2;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asian.R;

public class ActivityEx2 extends AppCompatActivity {
    private ImageButton mImgBtnInc, mImgBtnDec, mImgBtnMul, mImgBtnDiv;
    private EditText mEdtTerm1, mEdtTerm2;
    private TextView mTvResult, mTvTerm1, mTvTerm2, mTvErrorDiv;
    private double mTerm1 = 0, mTerm2 = 0;
    private Drawable mIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mImgBtnInc = findViewById(R.id.btnInc);
        mImgBtnDec = findViewById(R.id.btnDec);
        mImgBtnMul = findViewById(R.id.btnMul);
        mImgBtnDiv = findViewById(R.id.btnDiv);
        mEdtTerm1 = findViewById(R.id.edtTerm1);
        mEdtTerm2 = findViewById(R.id.edtTerm2);
        mTvResult = findViewById(R.id.tvResult);
        mTvTerm1 = findViewById(R.id.tvTerm1);
        mTvTerm2 = findViewById(R.id.tvTerm2);
        mTvErrorDiv = findViewById(R.id.tvErrorDiv);
        mTvTerm1.setText(initText(getString(R.string.term1)));
        mTvTerm2.setText(initText(getString(R.string.term2)));
        initListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {
        mEdtTerm2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtTerm2.setFocusable(true);
                mEdtTerm2.setSelected(false);
                mTvErrorDiv.setVisibility(View.GONE);
                mEdtTerm2.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                return false;
            }
        });

        mImgBtnInc.setOnTouchListener((v, event) -> {
            mEdtTerm1.setFocusable(false);
            mEdtTerm1.setFocusableInTouchMode(false);
            mEdtTerm2.setFocusable(false);
            mEdtTerm2.setFocusableInTouchMode(false);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setAlpha(0.5f);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setAlpha(1f);
                    mTerm1 = getTerm1(mEdtTerm1.getText().toString().trim());
                    mTerm2 = getTerm1(mEdtTerm2.getText().toString().trim());
                    mTvResult.setText((mTerm1 + mTerm2) + "");
                    break;
            }
            mEdtTerm1.setFocusable(true);
            mEdtTerm1.setFocusableInTouchMode(true);
            mEdtTerm2.setFocusable(true);
            mEdtTerm2.setFocusableInTouchMode(true);
            return false;
        });

        mImgBtnDec.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtTerm1.setFocusable(false);
                mEdtTerm1.setFocusableInTouchMode(false);
                mEdtTerm2.setFocusable(false);
                mEdtTerm2.setFocusableInTouchMode(false);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.5f);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setAlpha(1f);
                        mTerm1 = getTerm1(mEdtTerm1.getText().toString().trim());
                        mTerm2 = getTerm1(mEdtTerm2.getText().toString().trim());
                        mTvResult.setText((mTerm1 - mTerm2) + "");
                        break;
                }
                mEdtTerm1.setFocusable(true);
                mEdtTerm1.setFocusableInTouchMode(true);
                mEdtTerm2.setFocusable(true);
                mEdtTerm2.setFocusableInTouchMode(true);
                return false;
            }
        });

        mImgBtnMul.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtTerm1.setFocusable(false);
                mEdtTerm1.setFocusableInTouchMode(false);
                mEdtTerm2.setFocusable(false);
                mEdtTerm2.setFocusableInTouchMode(false);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.5f);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setAlpha(1f);
                        mTerm1 = getTerm1(mEdtTerm1.getText().toString().trim());
                        mTerm2 = getTerm1(mEdtTerm2.getText().toString().trim());
                        mTvResult.setText((mTerm1 * mTerm2) + "");
                        break;
                }
                mEdtTerm1.setFocusable(true);
                mEdtTerm1.setFocusableInTouchMode(true);
                mEdtTerm2.setFocusable(true);
                mEdtTerm2.setFocusableInTouchMode(true);
                return false;
            }
        });

        mImgBtnDiv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mEdtTerm1.setFocusable(false);
                mEdtTerm1.setFocusableInTouchMode(false);
                mEdtTerm2.setFocusable(false);
                mEdtTerm2.setFocusableInTouchMode(false);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.5f);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setAlpha(1f);
                        mTerm1 = getTerm1(mEdtTerm1.getText().toString().trim());
                        mTerm2 = getTerm1(mEdtTerm2.getText().toString().trim());
                        if (mTerm2 == 0) {
                            mEdtTerm2.setSelected(true);
                            mTvErrorDiv.setVisibility(View.VISIBLE);
                            String errorDiv = getString(R.string.error_div);
                            mTvErrorDiv.setText(errorDiv);
                            mIcon = ContextCompat.getDrawable(ActivityEx2.this, R.drawable.ic_alert);
                            mEdtTerm2.setCompoundDrawablesWithIntrinsicBounds(null, null, mIcon, null);
                            mTvResult.setText("0");
                        } else {
                            mEdtTerm2.setSelected(false);
                            mTvErrorDiv.setVisibility(View.GONE);
                            mTvResult.setText((mTerm1 / mTerm2) + "");
                        }
                        break;
                }
                mEdtTerm1.setFocusable(true);
                mEdtTerm1.setFocusableInTouchMode(true);
                mEdtTerm2.setFocusable(true);
                mEdtTerm2.setFocusableInTouchMode(true);
                return false;
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

    private Double getTerm1(String term1) {
        if (!term1.isEmpty()) {
            return Double.parseDouble(term1);
        }
        return 0.0;
    }

    private Double getTerm2(String term2) {
        if (!term2.isEmpty()) {
            return Double.parseDouble(term2);
        }
        return 0.0;
    }
}
