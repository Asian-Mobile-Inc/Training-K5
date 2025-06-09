package issues2.ex3;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ActivityEx3Binding;

public class Ex3Activity extends AppCompatActivity {
    private ActivityEx3Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEx3Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.clExercise3, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String character = getColoredSpanned("*", getColor(R.color.red_F82B1F));
        String text1 = getColoredSpanned("Full name", getColor(R.color.black));

        binding.tvFullName.setText(Html.fromHtml(character + " " + text1));

        String text2 = getColoredSpanned("National ID", getColor(R.color.black));

        binding.tvNationalId.setText(Html.fromHtml(character + " " + text2));

        binding.btnSubmit.setOnClickListener(view -> {
            if (String.valueOf(binding.edtFullName.getText()).isEmpty()) {
                binding.ivDeleteFullName.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_warning)
                        .into(binding.ivDeleteFullName);

                binding.tvWarningFullName.setVisibility(View.VISIBLE);
            } else {
                binding.ivDeleteFullName.setVisibility(View.GONE);
                binding.edtFullName.clearFocus();
                binding.tvWarningFullName.setVisibility(View.GONE);
            }

            if (String.valueOf(binding.edtNationalId.getText()).isEmpty()) {
                binding.ivDeleteNationalId.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_warning)
                        .into(binding.ivDeleteNationalId);

                binding.tvWarningNationalId.setVisibility(View.VISIBLE);
            } else {
                binding.ivDeleteNationalId.setVisibility(View.GONE);
                binding.edtNationalId.clearFocus();
                binding.tvWarningNationalId.setVisibility(View.GONE);
            }

            if (String.valueOf(binding.edtAdditionalInformation.getText()).length() < 100) {
                binding.ivDeleteAdditionalInformation.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_warning)
                        .into(binding.ivDeleteAdditionalInformation);

                binding.tvWarningAdditionalInformation.setVisibility(View.VISIBLE);
            } else {
                binding.ivDeleteAdditionalInformation.setVisibility(View.GONE);
                binding.edtAdditionalInformation.clearFocus();
                binding.tvWarningAdditionalInformation.setVisibility(View.GONE);
            }
        });

        binding.edtFullName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.ivDeleteFullName.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_delete)
                        .into(binding.ivDeleteFullName);
            } else {
                binding.ivDeleteFullName.setVisibility(View.GONE);
            }
        });

        binding.ivDeleteFullName.setOnClickListener(view -> {
            binding.edtFullName.setText("");
            binding.edtFullName.clearFocus();
            binding.ivDeleteFullName.setVisibility(View.GONE);
        });

        binding.edtNationalId.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.ivDeleteNationalId.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_delete)
                        .into(binding.ivDeleteNationalId);
            } else {
                binding.ivDeleteNationalId.setVisibility(View.GONE);
            }
        });

        binding.ivDeleteNationalId.setOnClickListener(view -> {
            binding.edtNationalId.setText("");
            binding.edtNationalId.clearFocus();
            binding.ivDeleteNationalId.setVisibility(View.GONE);
        });

        binding.edtAdditionalInformation.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.ivDeleteAdditionalInformation.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_delete)
                        .into(binding.ivDeleteAdditionalInformation);
            } else {
                binding.ivDeleteAdditionalInformation.setVisibility(View.GONE);
            }
        });

        binding.ivDeleteAdditionalInformation.setOnClickListener(view -> {
            binding.edtAdditionalInformation.setText("");
            binding.edtAdditionalInformation.clearFocus();
            binding.ivDeleteAdditionalInformation.setVisibility(View.GONE);
        });
    }

    private String getColoredSpanned(String text, int color) {
        return "<font color=" + color + ">" + text + "</font>";
    }
}
