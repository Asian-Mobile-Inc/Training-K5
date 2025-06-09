package com.example.asian;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
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

public class ActivityEx2 extends AppCompatActivity {
    private ImageButton btnInc, btnDec, btnMul, btnDiv;
    private EditText edtTerm1, edtTerm2;
    private TextView tvResult, tvTerm1, tvTerm2;
    private double term1 = 0, term2 = 0;
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

        btnInc = findViewById(R.id.btn_inc);
        btnDec = findViewById(R.id.btn_dec);
        btnMul = findViewById(R.id.btn_mul);
        btnDiv = findViewById(R.id.btn_div);

        edtTerm1 = findViewById(R.id.edt_term1);
        edtTerm2 = findViewById(R.id.edt_term2);

        tvResult = findViewById(R.id.tv_result);
        tvTerm1 = findViewById(R.id.tv_term1);
        tvTerm2 = findViewById(R.id.tv_term2);

        SpannableStringBuilder spannable = new SpannableStringBuilder("* Term1");
        spannable.setSpan(
                new ForegroundColorSpan(Color.RED),
                0, // start
                1, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );
        tvTerm1.setText(spannable);

        spannable = new SpannableStringBuilder("* Term2");
        spannable.setSpan(
                new ForegroundColorSpan(Color.RED),
                0, // start
                1, // end
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        );
        tvTerm2.setText(spannable);

        btnInc.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setAlpha(0.5f);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.setAlpha(1f);
                    if (!edtTerm1.getText().toString().trim().isEmpty()) {
                        term1 = Double.parseDouble(edtTerm1.getText().toString().trim());
                    }
                    if (!edtTerm2.getText().toString().trim().isEmpty()) {
                        term2 = Double.parseDouble(edtTerm2.getText().toString().trim());
                    }
                    tvResult.setText((term1 + term2) + "");
                    break;
            }
            return false;
        });

        btnDec.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.5f);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setAlpha(1f);
                        if (!edtTerm1.getText().toString().trim().isEmpty()) {
                            term1 = Double.parseDouble(edtTerm1.getText().toString().trim());
                        }
                        if (!edtTerm2.getText().toString().trim().isEmpty()) {
                            term2 = Double.parseDouble(edtTerm2.getText().toString().trim());
                        }
                        tvResult.setText((term1 - term2) + "");
                        break;
                }
                return false;
            }
        });

        btnMul.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.5f);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setAlpha(1f);
                        if (!edtTerm1.getText().toString().trim().isEmpty()) {
                            term1 = Double.parseDouble(edtTerm1.getText().toString().trim());
                        }
                        if (!edtTerm2.getText().toString().trim().isEmpty()) {
                            term2 = Double.parseDouble(edtTerm2.getText().toString().trim());
                        }
                        tvResult.setText((term1 * term2) + "");
                        break;
                }
                return false;
            }
        });

        btnDiv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setAlpha(0.5f);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        v.setAlpha(1f);
                        if (!edtTerm1.getText().toString().trim().isEmpty()) {
                            term1 = Double.parseDouble(edtTerm1.getText().toString().trim());
                        }
                        if (!edtTerm2.getText().toString().trim().isEmpty()) {
                            term2 = Double.parseDouble(edtTerm2.getText().toString().trim());
                        }
                        tvResult.setText((term1 / term2) + "");
                        break;
                }
                return false;
            }
        });
    }
}