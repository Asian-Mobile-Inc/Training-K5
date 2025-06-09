package issues2.ex1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ActivityEx1Binding;

import issues2.ex2.Ex2Activity;

public class Ex1Activity extends AppCompatActivity {
    private ActivityEx1Binding binding;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEx1Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.rlExercise1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Ex2Activity.class);
            startActivity(intent);
        });

        binding.edtEmailInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.btnDelete.setVisibility(View.VISIBLE);
            } else {
                binding.btnDelete.setVisibility(View.GONE);
            }
        });

        binding.btnDelete.setOnClickListener(view -> {
            binding.edtEmailInput.setText("");
            binding.edtEmailInput.clearFocus();
            binding.btnDelete.setVisibility(View.GONE);
        });

        binding.edtPasswordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    binding.btnSeenPassword.setVisibility(View.VISIBLE);
                } else {
                    binding.btnSeenPassword.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
