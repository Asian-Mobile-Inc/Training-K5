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
    private FusedLocationProviderClient mLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private static final String LOCATION = "Location";
    private static final String GPS_IS_RUNNING_IN_BACKGROUND = "GPS is running in background";
    private static final String NETWORK = "Network";
    private static final String GPS_CHANNEL = "gps_channel";
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;

    @Override
    public void onCreate() {
        super.onCreate();
        createFilter();
        createReceiver();
        registerReceiver(mReceiver, mIntentFilter);
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
        if (mLocationClient != null) {
            removeLocationUpdates();
        }
        unregisterReceiver(mReceiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void setupLocationService() {
        mLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
    }

    private void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(20000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // High priority

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
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
                    Log.d(LOCATION, "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude());
                }
            }
        };
    }

    private Notification createNotification() {
        String channelId = GPS_CHANNEL;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, getString(R.string.notification_channel_name_gps_tracking), NotificationManager.IMPORTANCE_LOW);
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }

        return new NotificationCompat.Builder(this, channelId)
                .setContentTitle(getString(R.string.notification_content_title_tracking_location))
                .setContentText(GPS_IS_RUNNING_IN_BACKGROUND)
                .setSmallIcon(R.drawable.ic_warning)
                .build();
    }

    private void createFilter() {
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    /*
        Check Internet Connection in Android (API Level 29) Using Network Callback
        https://gist.github.com/PasanBhanu/730a32a9eeb180ec2950c172d54bb06a
     */

    private void createReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Objects.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION)) {
                    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

                    if (activeNetwork != null && activeNetwork.isConnected()) {
                        Log.d(NETWORK, "Internet");
                        if (mLocationClient == null) {
                            setupLocationService();
                            createLocationCallback();
                            startLocationUpdates();
                        }
                    } else {
                        Log.d(NETWORK, "No internet");
                        if (mLocationClient != null) {
                            removeLocationUpdates();
                        }
                    }
                }
            }
        };
    }
}
