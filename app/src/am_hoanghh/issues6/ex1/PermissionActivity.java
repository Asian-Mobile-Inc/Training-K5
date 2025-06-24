package issues6.ex1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.asian.R;
import com.example.asian.databinding.ActivityPermissionBinding;

public class PermissionActivity extends AppCompatActivity {
    private ActivityPermissionBinding mBinding;
    private ActivityResultLauncher<String> mRequestPermissionLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPermissionBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initListeners();
        initLocationPermissionLauncher();
    }

    private void initListeners() {
        mBinding.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        // Permission already granted
                        Toast.makeText(getApplicationContext(), getString(R.string.toast_text_permission_already_granted), Toast.LENGTH_SHORT).show();
                        setAllowedPermission();
                        moveToLocationActivity();
                    } else {
                        mRequestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_text_gps_disabled), Toast.LENGTH_SHORT).show();
                    setNotAllowedPermission();
                }
            }
        });
    }

    private void initLocationPermissionLauncher() {
        mRequestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Toast.makeText(this, getString(R.string.toast_text_permission_already_granted), Toast.LENGTH_SHORT).show();
                        setAllowedPermission();
                        moveToLocationActivity();
                    } else {
                        Toast.makeText(this, getString(R.string.toast_text_gps_permission_denied), Toast.LENGTH_SHORT).show();
                        mBinding.switchCompat.setChecked(false);
                        setNotAllowedPermission();
                    }
                });
    }

    private void setNotAllowedPermission() {
        mBinding.tvAllowedPermission.setText(getString(R.string.textview_text_permission_is_not_allowed));
        mBinding.tvAllowedPermission.setTextColor(getColor(R.color.red_FF3131));
        mBinding.switchCompat.setChecked(false);
    }

    private void setAllowedPermission() {
        mBinding.tvAllowedPermission.setText(getString(R.string.textview_text_permission_is_allowed));
        mBinding.tvAllowedPermission.setTextColor(getColor(R.color.primary_1877F2));
        mBinding.switchCompat.setChecked(true);
    }

    private void moveToLocationActivity() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }
}
