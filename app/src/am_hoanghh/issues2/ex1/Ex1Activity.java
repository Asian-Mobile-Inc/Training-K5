package issues2.ex1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ActivityEx1Binding;

public class Ex1Activity extends AppCompatActivity {
    private ActivityEx1Binding binding;
    private boolean isPasswordVisible = false;
    private EmailValidate emailValidate;
    private static final String TEXT_EMAIL = "email";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEx1Binding.inflate(getLayoutInflater());
        emailValidate = new EmailValidate();

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.rlExercise1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnLogin.setOnClickListener(view -> {
            boolean check = true;

            if (!emailValidate.isValid(String.valueOf(binding.edtEmailInput.getText()))) {
                binding.btnDelete.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_warning)
                        .into(binding.btnDelete);

                binding.tvWarningUsername.setVisibility(View.VISIBLE);

                check = false;
            } else {
                binding.btnDelete.setVisibility(View.GONE);
                binding.edtEmailInput.clearFocus();
                binding.tvWarningUsername.setVisibility(View.GONE);
            }

            if (String.valueOf(binding.edtPasswordInput.getText()).length() < 8) {
                binding.btnSeenPassword.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_warning)
                        .into(binding.btnSeenPassword);

                binding.tvWarningPassword.setVisibility(View.VISIBLE);

                check = false;
            } else {
                binding.btnSeenPassword.setVisibility(View.GONE);
                binding.edtPasswordInput.clearFocus();
                binding.tvWarningPassword.setVisibility(View.GONE);
            }

            if (check) {
                Intent intent = new Intent(this, SubEx1Activity.class);
                intent.putExtra(TEXT_EMAIL, binding.edtEmailInput.getText().toString());
                startActivity(intent);
            }
        });

        binding.edtEmailInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.btnDelete.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_delete)
                        .into(binding.btnDelete);
            } else {
                binding.btnDelete.setVisibility(View.GONE);
            }
        });

        binding.btnDelete.setOnClickListener(view -> {
            binding.edtEmailInput.setText("");
            binding.edtEmailInput.clearFocus();
            binding.btnDelete.setVisibility(View.GONE);
        });

        binding.edtPasswordInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.btnSeenPassword.setVisibility(View.VISIBLE);

                if (isPasswordVisible) {
                    binding.edtPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Glide.with(this)
                            .load(R.drawable.ic_unseen)
                            .into(binding.btnSeenPassword);
                } else {
                    binding.edtPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Glide.with(this)
                            .load(R.drawable.ic_seen)
                            .into(binding.btnSeenPassword);
                }
            } else {
                binding.btnSeenPassword.setVisibility(View.GONE);
            }
        });

        binding.btnSeenPassword.setOnClickListener(view -> {
            if (isPasswordVisible) {
                binding.edtPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                Glide.with(this)
                        .load(R.drawable.ic_unseen)
                        .into(binding.btnSeenPassword);
            } else {
                binding.edtPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                Glide.with(this)
                        .load(R.drawable.ic_seen)
                        .into(binding.btnSeenPassword);
            }

            binding.edtPasswordInput.setSelection(binding.edtPasswordInput.getText().length());

            isPasswordVisible = !isPasswordVisible;
        });
    }
}
