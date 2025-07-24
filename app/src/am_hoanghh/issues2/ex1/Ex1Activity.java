package issues2.ex1;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ActivityEx1Binding;

public class Ex1Activity extends AppCompatActivity {
    private ActivityEx1Binding mBinding;
    private boolean isPasswordVisible = false;
    private EmailValidate emailValidate;
    private static final String TEXT_EMAIL = "email";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityEx1Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initEmailValidate();
        initListeners();
    }

    private void initEmailValidate() {
        emailValidate = new EmailValidate();
    }

    private void initListeners() {
        mBinding.btnLogin.setOnClickListener(view -> {
            boolean check = true;

            if (!emailValidate.isValid(String.valueOf(mBinding.edtEmailInput.getText()))) {
                mBinding.btnDelete.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_warning)
                        .into(mBinding.btnDelete);

                mBinding.tvWarningUsername.setVisibility(View.VISIBLE);

                check = false;
            } else {
                mBinding.btnDelete.setVisibility(View.GONE);
                mBinding.edtEmailInput.clearFocus();
                mBinding.tvWarningUsername.setVisibility(View.GONE);
            }

            if (String.valueOf(mBinding.edtPasswordInput.getText()).length() < 8) {
                mBinding.btnSeenPassword.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_warning)
                        .into(mBinding.btnSeenPassword);

                mBinding.tvWarningPassword.setVisibility(View.VISIBLE);

                check = false;
            } else {
                mBinding.btnSeenPassword.setVisibility(View.GONE);
                mBinding.edtPasswordInput.clearFocus();
                mBinding.tvWarningPassword.setVisibility(View.GONE);
            }

            if (check) {
                Intent intent = new Intent(this, SubEx1Activity.class);
                intent.putExtra(TEXT_EMAIL, mBinding.edtEmailInput.getText().toString());
                startActivity(intent);
            }
        });

        mBinding.edtEmailInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mBinding.btnDelete.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_delete)
                        .into(mBinding.btnDelete);
            } else {
                mBinding.btnDelete.setVisibility(View.GONE);
            }
        });

        mBinding.btnDelete.setOnClickListener(view -> {
            mBinding.edtEmailInput.setText("");
            mBinding.edtEmailInput.clearFocus();
            mBinding.btnDelete.setVisibility(View.GONE);
        });

        mBinding.edtPasswordInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mBinding.btnSeenPassword.setVisibility(View.VISIBLE);

                if (isPasswordVisible) {
                    mBinding.edtPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Glide.with(this)
                            .load(R.drawable.ic_unseen)
                            .into(mBinding.btnSeenPassword);
                } else {
                    mBinding.edtPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Glide.with(this)
                            .load(R.drawable.ic_seen)
                            .into(mBinding.btnSeenPassword);
                }
            } else {
                mBinding.btnSeenPassword.setVisibility(View.GONE);
            }
        });

        mBinding.btnSeenPassword.setOnClickListener(view -> {
            if (isPasswordVisible) {
                mBinding.edtPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                Glide.with(this)
                        .load(R.drawable.ic_unseen)
                        .into(mBinding.btnSeenPassword);
            } else {
                mBinding.edtPasswordInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                Glide.with(this)
                        .load(R.drawable.ic_seen)
                        .into(mBinding.btnSeenPassword);
            }

            mBinding.edtPasswordInput.setSelection(mBinding.edtPasswordInput.getText().length());

            isPasswordVisible = !isPasswordVisible;
        });
    }
}
