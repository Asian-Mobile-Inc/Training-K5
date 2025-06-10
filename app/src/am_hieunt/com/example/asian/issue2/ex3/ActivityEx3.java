package com.example.asian.issue2.ex3;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asian.R;

public class ActivityEx3 extends AppCompatActivity {
    private View mVLine;
    private TextView mTvFullName, mTvNational;
    private LinearLayout mLlMain;
    private float mStartY;

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
        mLlMain = findViewById(R.id.llMain);

        SpannableStringBuilder spannable = new SpannableStringBuilder("* Full name");
        spannable.setSpan(
                new ForegroundColorSpan(Color.RED),
                0, // start
                1, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );
        mTvFullName.setText(spannable);

        spannable = new SpannableStringBuilder("* National ID");
        spannable.setSpan(
                new ForegroundColorSpan(Color.RED),
                0, // start
                1, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );
        mTvNational.setText(spannable);

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
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLlMain.getLayoutParams();
                            params.topMargin = dpToPx(15);
                            mLlMain.setLayoutParams(params);
                        }
                        return true;
                }
                return false;
            }
        });
    }

    public int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics()
        );
    }

}
