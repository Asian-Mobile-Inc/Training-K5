package issues2.ex2;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ActivityEx2Binding;

public class Ex2Activity extends AppCompatActivity {
    private ActivityEx2Binding mBinding;
    private static final String TERM_1 = "Term 1";
    private static final String TERM_2 = "Term 2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityEx2Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initColorTextView();
        initListeners();
    }

    private void initColorTextView() {
        String character = getColoredSpanned("*", getColor(R.color.red_F82B1F));
        String text1 = getColoredSpanned(TERM_1, getColor(R.color.black));
        mBinding.tvTerm1.setText(Html.fromHtml(character + " " + text1));

        String text2 = getColoredSpanned(TERM_2, getColor(R.color.black));
        mBinding.tvTerm2.setText(Html.fromHtml(character + " " + text2));
    }

    private void initListeners() {
        mBinding.edtTerm1Input.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mBinding.btnDeleteTerm1.setVisibility(View.VISIBLE);
            } else {
                mBinding.btnDeleteTerm1.setVisibility(View.GONE);
            }
        });

        mBinding.btnDeleteTerm1.setOnClickListener(view -> {
            mBinding.edtTerm1Input.setText("");
            mBinding.edtTerm1Input.clearFocus();
            mBinding.btnDeleteTerm1.setVisibility(View.GONE);
        });

        mBinding.edtTerm2Input.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mBinding.ivDeleteTerm2.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_delete)
                        .into(mBinding.ivDeleteTerm2);
            } else {
                mBinding.ivDeleteTerm2.setVisibility(View.GONE);
            }
        });

        mBinding.ivDeleteTerm2.setOnClickListener(view -> {
            mBinding.edtTerm2Input.setText("");
            mBinding.edtTerm2Input.clearFocus();
            mBinding.ivDeleteTerm2.setVisibility(View.GONE);
        });

        mBinding.ivPlus.setOnClickListener(view -> {
            try {
                if (!String.valueOf(mBinding.edtTerm1Input.getText()).isEmpty() && !String.valueOf(mBinding.edtTerm2Input.getText()).isEmpty()) {
                    double result = calculation(Double.parseDouble(String.valueOf(mBinding.edtTerm1Input.getText())), Double.parseDouble(String.valueOf(mBinding.edtTerm2Input.getText())), '+');
                    mBinding.tvResult.setText(String.valueOf(result));
                }
            } catch (NumberFormatException numberFormatException) {
                mBinding.tvResult.setText(getString(R.string.textview_error_result));
            }
        });

        mBinding.ivMinus.setOnClickListener(view -> {
            try {
                if (!String.valueOf(mBinding.edtTerm1Input.getText()).isEmpty() && !String.valueOf(mBinding.edtTerm2Input.getText()).isEmpty()) {
                    double result = calculation(Double.parseDouble(String.valueOf(mBinding.edtTerm1Input.getText())), Double.parseDouble(String.valueOf(mBinding.edtTerm2Input.getText())), '-');
                    mBinding.tvResult.setText(String.valueOf(result));
                }
            } catch (NumberFormatException numberFormatException) {
                mBinding.tvResult.setText(getString(R.string.textview_error_result));
            }
        });

        mBinding.ivMultiply.setOnClickListener(view -> {
            try {
                if (!String.valueOf(mBinding.edtTerm1Input.getText()).isEmpty() && !String.valueOf(mBinding.edtTerm2Input.getText()).isEmpty()) {
                    double result = calculation(Double.parseDouble(String.valueOf(mBinding.edtTerm1Input.getText())), Double.parseDouble(String.valueOf(mBinding.edtTerm2Input.getText())), '*');
                    mBinding.tvResult.setText(String.valueOf(result));
                }
            } catch (NumberFormatException numberFormatException) {
                mBinding.tvResult.setText(getString(R.string.textview_error_result));
            }
        });

        mBinding.ivDivide.setOnClickListener(view -> {
            try {
                if (!String.valueOf(mBinding.edtTerm1Input.getText()).isEmpty() && !String.valueOf(mBinding.edtTerm2Input.getText()).isEmpty()) {
                    double term1 = Double.parseDouble(String.valueOf(mBinding.edtTerm1Input.getText()));
                    double term2 = Double.parseDouble(String.valueOf(mBinding.edtTerm2Input.getText()));

                    if (term2 == 0) {
                        mBinding.ivDeleteTerm2.setVisibility(View.VISIBLE);

                        Glide.with(this)
                                .load(R.drawable.ic_warning)
                                .into(mBinding.ivDeleteTerm2);

                        mBinding.tvWarningTerm2.setVisibility(View.VISIBLE);
                        mBinding.tvResult.setText(getString(R.string.textview_error_result));
                    } else {
                        double result = calculation(term1, term2, '/');

                        mBinding.tvResult.setText(String.valueOf(result));
                        mBinding.ivDeleteTerm2.setVisibility(View.GONE);
                        mBinding.edtTerm2Input.clearFocus();
                        mBinding.tvWarningTerm2.setVisibility(View.GONE);
                    }
                }
            } catch (NumberFormatException numberFormatException) {
                mBinding.tvResult.setText(getString(R.string.textview_error_result));
            }
        });
    }

    private String getColoredSpanned(String text, int color) {
        return "<font color=" + color + ">" + text + "</font>";
    }

    private double calculation(double a, double b, char c) {
        double result = 0;

        switch (c) {
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                result = a / b;
                break;
            default:
                break;
        }

        return result;
    }
}
