package com.example.asian.issue6.ex2;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.example.asian.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationService extends Service {
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    private Boolean isNetworkConnected = true;
    private InternetReceiver mInternetReceiver;
    private Handler handler;
    private Runnable locationTask;
    private Location lastLocation;
    private final String NOTIFICATION_CHANEL = "location_chanel";
    private final String LOG_LOCATION = "LOCATION SERVICE";
    @Override
    public void onCreate() {
        super.onCreate();
        mInternetReceiver = new InternetReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mInternetReceiver, filter);
        handler = new Handler();
        initLocationUpdate();
        initLocationTask();
    }

    private Notification createNotification() {
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel(NOTIFICATION_CHANEL, "Location Service", NotificationManager.IMPORTANCE_LOW);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Intent stopIntent = new Intent(this, StopServiceReceiver.class);
        PendingIntent deleteIntent = PendingIntent.getBroadcast(
                this,
                0,
                stopIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );
        return new NotificationCompat.Builder(this, NOTIFICATION_CHANEL)
                .setContentTitle("Location Service")
                .setContentText("Running")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setDeleteIntent(deleteIntent)
                .build();

    }

    public void initLocationUpdate() {
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lastLocation = location;
            }
        };
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.FOREGROUND_SERVICE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    0,
                    mLocationListener
            );
        }
    }

    private void initLocationTask() {
        locationTask = new Runnable() {
            @Override
            public void run() {
                if (isNetworkConnected) {
                    if (lastLocation != null) {
                        Log.d(LOG_LOCATION, "Lat: " + lastLocation.getLatitude() + ", Lon: " + lastLocation.getLongitude());
                    }
                } else Log.d(LOG_LOCATION, "Không có kết nối Internet");
                handler.postDelayed(this,20000);
            }
        };
        handler.post(locationTask);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.hasExtra("connected")) {
            isNetworkConnected = intent.getBooleanExtra("connected", true);
        }
        startForeground(1, createNotification());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(locationTask);
        if (mInternetReceiver != null) {
            unregisterReceiver(mInternetReceiver);
        }
        if (mLocationManager != null && mLocationListener != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
