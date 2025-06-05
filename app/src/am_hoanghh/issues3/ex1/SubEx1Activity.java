package issues3.ex1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asian.R;
import com.example.asian.databinding.ActivitySubScreenEx1Binding;

public class SubEx1Activity extends AppCompatActivity {
    private ActivitySubScreenEx1Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySubScreenEx1Binding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.clSubExercise1, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String email = intent.getStringExtra(getString(R.string.text_email));

        if (email != null) {
            binding.tvEmail.setText(email);
        }

        binding.ivBack.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Ex1Activity.class);
            startActivity(i);
            finish();
        });
    }
}
