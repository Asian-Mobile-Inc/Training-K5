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
    private ActivityPermissionBinding binding;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private static final String PERMISSION_ALREADY_GRANTED = "Permission already granted";
    private static final String GPS_PERMISSION_DENIED = "GPS permission denied";
    private static final String GPS_DISABLED = "GPS disabled";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPermissionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initListeners();
        initLocationPermissionLauncher();
    }

    private void initListeners() {
        binding.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        // Permission already granted
                        Toast.makeText(getApplicationContext(), PERMISSION_ALREADY_GRANTED, Toast.LENGTH_SHORT).show();
                        setAllowedPermission();
                        moveToLocationActivity();
                    } else {
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), GPS_DISABLED, Toast.LENGTH_SHORT).show();
                    setNotAllowedPermission();
                }
            }
        });
    }

    private void initLocationPermissionLauncher() {
        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Toast.makeText(this, PERMISSION_ALREADY_GRANTED, Toast.LENGTH_SHORT).show();
                        setAllowedPermission();
                        moveToLocationActivity();
                    } else {
                        Toast.makeText(this, GPS_PERMISSION_DENIED, Toast.LENGTH_SHORT).show();
                        binding.switchCompat.setChecked(false);
                        setNotAllowedPermission();
                    }
                });
    }

    private void setNotAllowedPermission() {
        binding.tvAllowedPermission.setText(getString(R.string.textview_text_permission_is_not_allowed));
        binding.tvAllowedPermission.setTextColor(getColor(R.color.red_FF3131));
        binding.switchCompat.setChecked(false);
    }

    private void setAllowedPermission() {
        binding.tvAllowedPermission.setText(getString(R.string.textview_text_permission_is_allowed));
        binding.tvAllowedPermission.setTextColor(getColor(R.color.primary_1877F2));
        binding.switchCompat.setChecked(true);
    }

    private void moveToLocationActivity() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }
}
