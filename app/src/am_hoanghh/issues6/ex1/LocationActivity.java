package issues6.ex1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.asian.databinding.ActivityLocationBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationActivity extends AppCompatActivity {
    private ActivityLocationBinding mBinding;
    private FusedLocationProviderClient mLocationClient;
    private LocationCallback mLocationCallback;
    private static final String LOCATION = "Location";
    private static final int INTERVAL = 5000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setupLocationService();
        createLocationCallback();
        startLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            removeLocationUpdates();
        }
    }

    private void setupLocationService() {
        mLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
    }

    @SuppressLint("VisibleForTests")
    private void startLocationUpdates() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
        }
    }

    private void removeLocationUpdates() {
        mLocationClient.removeLocationUpdates(mLocationCallback);
        mLocationClient = null;
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    String result = "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude();
                    mBinding.tvLocation.setText(result);
                    Log.d(LOCATION, result);
                }
            }
        };
    }
}
