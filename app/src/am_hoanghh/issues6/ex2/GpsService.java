package issues6.ex2;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.asian.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.Objects;

public class GpsService extends Service {
    private FusedLocationProviderClient locationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private static final String LOCATION = "Location";
    private static final String TRACKING_LOCATION = "Tracking location";
    private static final String GPS_IS_RUNNING_IN_BACKGROUND = "GPS is running in background";
    private static final String GPS_TRACKING = "GPS Tracking";
    private static final String NETWORK = "Network";
    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;

    @Override
    public void onCreate() {
        super.onCreate();
        createFilter();
        createReceiver();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification notification = createNotification();
        startForeground(1, notification);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationClient != null) {
            removeLocationUpdates();
        }
        unregisterReceiver(receiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setupLocationService() {
        locationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
    }

    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(20000);
        locationRequest.setFastestInterval(20000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // High priority

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        }
    }

    private void removeLocationUpdates() {
        locationClient.removeLocationUpdates(locationCallback);
        locationClient = null;
    }

    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    Log.d(LOCATION, "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
                }
            }
        };
    }

    private Notification createNotification() {
        String channelId = "gps_channel";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, GPS_TRACKING, NotificationManager.IMPORTANCE_LOW);
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }

        return new NotificationCompat.Builder(this, channelId)
                .setContentTitle(TRACKING_LOCATION)
                .setContentText(GPS_IS_RUNNING_IN_BACKGROUND)
                .setSmallIcon(R.drawable.ic_warning)
                .build();
    }

    private void createFilter() {
        intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    /*
        Check Internet Connection in Android (API Level 29) Using Network Callback
        https://gist.github.com/PasanBhanu/730a32a9eeb180ec2950c172d54bb06a
     */

    private void createReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Objects.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
                    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                    if (activeNetwork != null && activeNetwork.isConnected()) {
                        Log.d(NETWORK, "Internet");
                        if (locationClient == null) {
                            setupLocationService();
                            createLocationCallback();
                            startLocationUpdates();
                        }
                    } else {
                        Log.d(NETWORK, "No internet");
                        if (locationClient != null) {
                            removeLocationUpdates();
                        }
                    }
                }
            }
        };
    }
}
