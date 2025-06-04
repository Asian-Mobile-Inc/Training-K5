package issues2.ex3;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asian.databinding.ActivityEx3Binding;

import issues2.ex1.Ex1Activity;

public class Ex3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.asian.databinding.ActivityEx3Binding binding = ActivityEx3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.clExercise3, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String character = getColoredSpanned("*", "#F82B1F");
        String text1 = getColoredSpanned("Full name","#000000");

        binding.tvFullName.setText(Html.fromHtml(character + " " + text1));

        String text2 = getColoredSpanned("National ID","#000000");

        binding.tvNationalId.setText(Html.fromHtml(character + " " + text2));

        binding.btnSubmit.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Ex1Activity.class);
            startActivity(intent);
        });

        binding.edtFullName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.btnDeleteFullName.setVisibility(View.VISIBLE);
            } else {
                binding.btnDeleteFullName.setVisibility(View.GONE);
            }
        });

        binding.btnDeleteFullName.setOnClickListener(view -> {
            binding.edtFullName.setText("");
            binding.edtFullName.clearFocus();
            binding.btnDeleteFullName.setVisibility(View.GONE);
        });

        binding.edtNationalId.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.btnDeleteNationalId.setVisibility(View.VISIBLE);
            } else {
                binding.btnDeleteNationalId.setVisibility(View.GONE);
            }
        });

        binding.btnDeleteNationalId.setOnClickListener(view -> {
            binding.edtNationalId.setText("");
            binding.edtNationalId.clearFocus();
            binding.btnDeleteNationalId.setVisibility(View.GONE);
        });

        binding.edtAdditionalInfor.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                binding.btnDeleteAdditionalInformation.setVisibility(View.VISIBLE);
            } else {
                binding.btnDeleteAdditionalInformation.setVisibility(View.GONE);
            }
        });

        binding.btnDeleteAdditionalInformation.setOnClickListener(view -> {
            binding.edtAdditionalInfor.setText("");
            binding.edtAdditionalInfor.clearFocus();
            binding.btnDeleteAdditionalInformation.setVisibility(View.GONE);
        });
    }

    private String getColoredSpanned(String text, String color) {
        return "<font color=" + color + ">" + text + "</font>";
    }
}