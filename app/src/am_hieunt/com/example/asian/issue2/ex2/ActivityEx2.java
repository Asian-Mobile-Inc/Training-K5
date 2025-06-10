package com.example.asian.issue2.ex2;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asian.R;

public class ActivityEx2 extends AppCompatActivity {
    private ImageButton mImgBtnInc, mImgBtnDec, mImgBtnMul, mImgBtnDiv;
    private EditText mEdtTerm1, mEdtTerm2;
    private TextView mTvResult, mTvTerm1, mTvTerm2;
    private double mTerm1 = 0, mTerm2 = 0;
    @SuppressLint("ClickableViewAccessibility")
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

        SpannableStringBuilder spannable = new SpannableStringBuilder("* Term1");
        spannable.setSpan(
                new ForegroundColorSpan(Color.RED),
                0, // start
                1, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );
        mTvTerm1.setText(spannable);

        spannable = new SpannableStringBuilder("* Term2");
        spannable.setSpan(
                new ForegroundColorSpan(Color.RED),
                0, // start
                1, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );
        mTvTerm2.setText(spannable);

        mImgBtnInc.setOnTouchListener((v, event) -> {
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
            return false;
        });

        mImgBtnDec.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
                return false;
            }
        });

        mImgBtnMul.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
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
                return false;
            }
        });

        mImgBtnDiv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.5f);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setAlpha(1f);
                        mTerm1 = getTerm1(mEdtTerm1.getText().toString().trim());
                        mTerm2 = getTerm1(mEdtTerm2.getText().toString().trim());
                        mTvResult.setText((mTerm1 / mTerm2) + "");
                        break;
                }
                return false;
            }
        });
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
