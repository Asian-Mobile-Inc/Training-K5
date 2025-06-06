package issues3.ex2;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ActivityEx2Binding;

public class Ex2Activity extends AppCompatActivity {
    private ActivityEx2Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEx2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.llExercise2, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String character = getColoredSpanned("*", getColor(R.color.red_F82B1F));
        String text1 = getColoredSpanned("Term 1", getColor(R.color.black));

        binding.tvTerm1.setText(Html.fromHtml(character + " " + text1));

        String text2 = getColoredSpanned("Term 2", getColor(R.color.black));

        binding.tvTerm2.setText(Html.fromHtml(character + " " + text2));

        binding.edtTerm1Input.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.btnDeleteTerm1.setVisibility(View.VISIBLE);
            } else {
                binding.btnDeleteTerm1.setVisibility(View.GONE);
            }
        });

        binding.btnDeleteTerm1.setOnClickListener(view -> {
            binding.edtTerm1Input.setText("");
            binding.edtTerm1Input.clearFocus();
            binding.btnDeleteTerm1.setVisibility(View.GONE);
        });

        binding.edtTerm2Input.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.ivDeleteTerm2.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_delete)
                        .into(binding.ivDeleteTerm2);
            } else {
                binding.ivDeleteTerm2.setVisibility(View.GONE);
            }
        });

        binding.ivDeleteTerm2.setOnClickListener(view -> {
            binding.edtTerm2Input.setText("");
            binding.edtTerm2Input.clearFocus();
            binding.ivDeleteTerm2.setVisibility(View.GONE);
        });

        binding.ivPlus.setOnClickListener(view -> {
            try {
                if (!String.valueOf(binding.edtTerm1Input.getText()).isEmpty() && !String.valueOf(binding.edtTerm2Input.getText()).isEmpty()) {
                    double result = calculation(Double.parseDouble(String.valueOf(binding.edtTerm1Input.getText())), Double.parseDouble(String.valueOf(binding.edtTerm2Input.getText())), '+');

                    binding.tvResult.setText(String.valueOf(result));
                }
            } catch (NumberFormatException numberFormatException) {
                binding.tvResult.setText(getString(R.string.textview_error_result));
            }
        });

        binding.ivMinus.setOnClickListener(view -> {
            try {
                if (!String.valueOf(binding.edtTerm1Input.getText()).isEmpty() && !String.valueOf(binding.edtTerm2Input.getText()).isEmpty()) {
                    double result = calculation(Double.parseDouble(String.valueOf(binding.edtTerm1Input.getText())), Double.parseDouble(String.valueOf(binding.edtTerm2Input.getText())), '-');

                    binding.tvResult.setText(String.valueOf(result));
                }
            } catch (NumberFormatException numberFormatException) {
                binding.tvResult.setText(getString(R.string.textview_error_result));
            }
        });

        binding.ivMultiply.setOnClickListener(view -> {
            try {
                if (!String.valueOf(binding.edtTerm1Input.getText()).isEmpty() && !String.valueOf(binding.edtTerm2Input.getText()).isEmpty()) {
                    double result = calculation(Double.parseDouble(String.valueOf(binding.edtTerm1Input.getText())), Double.parseDouble(String.valueOf(binding.edtTerm2Input.getText())), '*');

                    binding.tvResult.setText(String.valueOf(result));
                }
            } catch (NumberFormatException numberFormatException) {
                binding.tvResult.setText(getString(R.string.textview_error_result));
            }
        });

        binding.ivDivide.setOnClickListener(view -> {
            try {
                if (!String.valueOf(binding.edtTerm1Input.getText()).isEmpty() && !String.valueOf(binding.edtTerm2Input.getText()).isEmpty()) {
                    double term1 = Double.parseDouble(String.valueOf(binding.edtTerm1Input.getText()));
                    double term2 = Double.parseDouble(String.valueOf(binding.edtTerm2Input.getText()));

                    if (term2 == 0) {
                        binding.ivDeleteTerm2.setVisibility(View.VISIBLE);

                        Glide.with(this)
                                .load(R.drawable.ic_warning)
                                .into(binding.ivDeleteTerm2);

                        binding.tvWarningTerm2.setVisibility(View.VISIBLE);

                        binding.tvResult.setText(getString(R.string.textview_error_result));
                    } else {
                        double result = calculation(term1, term2, '/');

                        binding.tvResult.setText(String.valueOf(result));

                        binding.ivDeleteTerm2.setVisibility(View.GONE);
                        binding.edtTerm2Input.clearFocus();
                        binding.tvWarningTerm2.setVisibility(View.GONE);
                    }
                }
            } catch (NumberFormatException numberFormatException) {
                binding.tvResult.setText(getString(R.string.textview_error_result));
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
