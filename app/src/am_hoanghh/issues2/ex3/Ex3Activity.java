package issues2.ex3;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ActivityEx3Binding;

public class Ex3Activity extends AppCompatActivity {
    private ActivityEx3Binding mBinding;
    private static final String FULL_NAME = "Full name";
    private static final String NATIONAL_ID = "National ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityEx3Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        String character = getColoredSpanned("*", getColor(R.color.red_F82B1F));
        String text1 = getColoredSpanned(FULL_NAME, getColor(R.color.black));

        mBinding.tvFullName.setText(Html.fromHtml(character + " " + text1));

        String text2 = getColoredSpanned(NATIONAL_ID, getColor(R.color.black));

        mBinding.tvNationalId.setText(Html.fromHtml(character + " " + text2));

        mBinding.btnSubmit.setOnClickListener(view -> {
            if (String.valueOf(mBinding.edtFullName.getText()).isEmpty()) {
                mBinding.ivDeleteFullName.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_warning)
                        .into(mBinding.ivDeleteFullName);

                mBinding.tvWarningFullName.setVisibility(View.VISIBLE);
            } else {
                mBinding.ivDeleteFullName.setVisibility(View.GONE);
                mBinding.edtFullName.clearFocus();
                mBinding.tvWarningFullName.setVisibility(View.GONE);
            }

            if (String.valueOf(mBinding.edtNationalId.getText()).isEmpty()) {
                mBinding.ivDeleteNationalId.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_warning)
                        .into(mBinding.ivDeleteNationalId);

                mBinding.tvWarningNationalId.setVisibility(View.VISIBLE);
            } else {
                mBinding.ivDeleteNationalId.setVisibility(View.GONE);
                mBinding.edtNationalId.clearFocus();
                mBinding.tvWarningNationalId.setVisibility(View.GONE);
            }

            if (String.valueOf(mBinding.edtAdditionalInformation.getText()).length() < 100) {
                mBinding.ivDeleteAdditionalInformation.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_warning)
                        .into(mBinding.ivDeleteAdditionalInformation);

                mBinding.tvWarningAdditionalInformation.setVisibility(View.VISIBLE);
            } else {
                mBinding.ivDeleteAdditionalInformation.setVisibility(View.GONE);
                mBinding.edtAdditionalInformation.clearFocus();
                mBinding.tvWarningAdditionalInformation.setVisibility(View.GONE);
            }
        });

        mBinding.edtFullName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mBinding.ivDeleteFullName.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_delete)
                        .into(mBinding.ivDeleteFullName);
            } else {
                mBinding.ivDeleteFullName.setVisibility(View.GONE);
            }
        });

        mBinding.ivDeleteFullName.setOnClickListener(view -> {
            mBinding.edtFullName.setText("");
            mBinding.edtFullName.clearFocus();
            mBinding.ivDeleteFullName.setVisibility(View.GONE);
        });

        mBinding.edtNationalId.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mBinding.ivDeleteNationalId.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_delete)
                        .into(mBinding.ivDeleteNationalId);
            } else {
                mBinding.ivDeleteNationalId.setVisibility(View.GONE);
            }
        });

        mBinding.ivDeleteNationalId.setOnClickListener(view -> {
            mBinding.edtNationalId.setText("");
            mBinding.edtNationalId.clearFocus();
            mBinding.ivDeleteNationalId.setVisibility(View.GONE);
        });

        mBinding.edtAdditionalInformation.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mBinding.ivDeleteAdditionalInformation.setVisibility(View.VISIBLE);

                Glide.with(this)
                        .load(R.drawable.ic_delete)
                        .into(mBinding.ivDeleteAdditionalInformation);
            } else {
                mBinding.ivDeleteAdditionalInformation.setVisibility(View.GONE);
            }
        });

        mBinding.ivDeleteAdditionalInformation.setOnClickListener(view -> {
            mBinding.edtAdditionalInformation.setText("");
            mBinding.edtAdditionalInformation.clearFocus();
            mBinding.ivDeleteAdditionalInformation.setVisibility(View.GONE);
        });
    }

    private String getColoredSpanned(String text, int color) {
        return "<font color=" + color + ">" + text + "</font>";
    }
}
