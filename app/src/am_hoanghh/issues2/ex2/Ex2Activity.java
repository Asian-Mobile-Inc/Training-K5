package issues2.ex2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asian.R;
import com.example.asian.databinding.ActivityEx2Binding;

import issues2.ex3.Ex3Activity;

public class Ex2Activity extends AppCompatActivity {
    private ActivityEx2Binding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEx2Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.llExercise2, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String character = getColoredSpanned("*", String.valueOf(R.color.red_F82B1F));
        String text1 = getColoredSpanned("Term 1", String.valueOf(R.color.black));

        binding.tvTerm1.setText(Html.fromHtml(character + " " + text1));

        String text2 = getColoredSpanned("Term 2", String.valueOf(R.color.black));

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
                binding.btnDeleteTerm2.setVisibility(View.VISIBLE);
            } else {
                binding.btnDeleteTerm2.setVisibility(View.GONE);
            }
        });

        binding.btnDeleteTerm2.setOnClickListener(view -> {
            binding.edtTerm2Input.setText("");
            binding.edtTerm2Input.clearFocus();
            binding.btnDeleteTerm2.setVisibility(View.GONE);
        });

        binding.ivPlus.setOnClickListener(view -> {
            try {
                if (!String.valueOf(binding.edtTerm1Input.getText()).isEmpty() && !String.valueOf(binding.edtTerm2Input.getText()).isEmpty()) {
                    double result = calculation(Double.parseDouble(String.valueOf(binding.edtTerm1Input.getText())), Double.parseDouble(String.valueOf(binding.edtTerm2Input.getText())), '+');

                    binding.tvResult.setText(String.valueOf(result));

                    delay();
                }
            } catch (NumberFormatException numberFormatException) {
                binding.tvResult.setText(R.string.textview_error_result);
            }
        });

        binding.ivMinus.setOnClickListener(view -> {
            try {
                if (!String.valueOf(binding.edtTerm1Input.getText()).isEmpty() && !String.valueOf(binding.edtTerm2Input.getText()).isEmpty()) {
                    double result = calculation(Double.parseDouble(String.valueOf(binding.edtTerm1Input.getText())), Double.parseDouble(String.valueOf(binding.edtTerm2Input.getText())), '-');

                    binding.tvResult.setText(String.valueOf(result));

                    delay();
                }
            } catch (NumberFormatException numberFormatException) {
                binding.tvResult.setText(R.string.textview_error_result);
            }
        });

        binding.ivMultiply.setOnClickListener(view -> {
            try {
                if (!String.valueOf(binding.edtTerm1Input.getText()).isEmpty() && !String.valueOf(binding.edtTerm2Input.getText()).isEmpty()) {
                    double result = calculation(Double.parseDouble(String.valueOf(binding.edtTerm1Input.getText())), Double.parseDouble(String.valueOf(binding.edtTerm2Input.getText())), '*');

                    binding.tvResult.setText(String.valueOf(result));

                    delay();
                }
            } catch (NumberFormatException numberFormatException) {
                binding.tvResult.setText(R.string.textview_error_result);
            }
        });

        binding.ivDivide.setOnClickListener(view -> {
            try {
                if (!String.valueOf(binding.edtTerm1Input.getText()).isEmpty() && !String.valueOf(binding.edtTerm2Input.getText()).isEmpty()) {
                    double result = calculation(Double.parseDouble(String.valueOf(binding.edtTerm1Input.getText())), Double.parseDouble(String.valueOf(binding.edtTerm2Input.getText())), '/');

                    binding.tvResult.setText(String.valueOf(result));

                    delay();
                }
            } catch (NumberFormatException numberFormatException) {
                binding.tvResult.setText(R.string.textview_error_result);
            }
        });
    }

    private String getColoredSpanned(String text, String color) {
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

    private void delay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Ex3Activity.class);
                startActivity(intent);
            }
        }, 2000);
    }
}
