package issues2.ex1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asian.databinding.ActivitySubScreenEx1Binding;

public class SubEx1Activity extends AppCompatActivity {
    private ActivitySubScreenEx1Binding mBinding;
    private static final String TEXT_EMAIL = "email";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySubScreenEx1Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initIntent();
        initListeners();
    }

    private void initIntent() {
        Intent intent = getIntent();
        String email = intent.getStringExtra(TEXT_EMAIL);

        if (email != null) {
            mBinding.tvEmail.setText(email);
        }
    }

    private void initListeners() {
        mBinding.ivBack.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), Ex1Activity.class);
            startActivity(i);
            finish();
        });
    }
}
