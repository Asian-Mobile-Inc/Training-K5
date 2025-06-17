package issues6.ex1.permission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asian.R;
import com.example.asian.databinding.FragmentPermissionBinding;

public class PermissionFragment extends Fragment {
    private FragmentPermissionBinding binding;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private final ViewPager2 viewPager;
    private static final String PERMISSION_ALREADY_GRANTED = "Permission already granted";
    private static final String GPS_PERMISSION_DENIED = "GPS permission denied";
    private static final String GPS_DISABLED = "GPS disabled";
    private static final String PACKAGE = "package";

    public PermissionFragment(ViewPager2 viewPager2) {
        this.viewPager = viewPager2;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPermissionBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        checkAlreadyGranted();
        initListeners();
        initLocationPermissionLauncher();
    }

    private void initListeners() {
        binding.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        // Permission already granted
                        Toast.makeText(getContext(), PERMISSION_ALREADY_GRANTED, Toast.LENGTH_SHORT).show();
                        setAllowedPermission();
                        viewPager.setCurrentItem(1);
                    } else if (!ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                        binding.switchCompat.setChecked(false);

                        // Move to settings
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts(PACKAGE, requireContext().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }, 1000);
                    } else {
                        // Granted permission
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                        setAllowedPermission();
                        viewPager.setCurrentItem(1);
                    }
                } else {
                    Toast.makeText(getContext(), GPS_DISABLED, Toast.LENGTH_SHORT).show();
                    setNotAllowedPermission();
                }
            }
        });
    }

    private void initLocationPermissionLauncher() {
        requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Toast.makeText(getContext(), PERMISSION_ALREADY_GRANTED, Toast.LENGTH_SHORT).show();
                        setAllowedPermission();
                    } else {
                        Toast.makeText(getContext(), GPS_PERMISSION_DENIED, Toast.LENGTH_SHORT).show();
                        binding.switchCompat.setChecked(false);
                        setNotAllowedPermission();
                    }
                });
    }

    private void setNotAllowedPermission() {
        binding.tvAllowedPermission.setText(getString(R.string.textview_text_permission_is_not_allowed));
        binding.tvAllowedPermission.setTextColor(getResources().getColor(R.color.red_FF3131));
        binding.switchCompat.setChecked(false);
    }

    private void setAllowedPermission() {
        binding.tvAllowedPermission.setText(getString(R.string.textview_text_permission_is_allowed));
        binding.tvAllowedPermission.setTextColor(getResources().getColor(R.color.primary_1877F2));
        binding.switchCompat.setChecked(true);
    }

    private void checkAlreadyGranted() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // permission already granted
            Toast.makeText(getContext(), PERMISSION_ALREADY_GRANTED, Toast.LENGTH_SHORT).show();
            setAllowedPermission();
            viewPager.setCurrentItem(1);
        }
    }
}
