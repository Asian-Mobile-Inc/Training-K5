package com.example.asian.issue6.ex2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

public class InternetReceiver extends BroadcastReceiver {
    private final String KEY_CONNECTED = "connected";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = isNetworkConnected(context);
        Intent serviceIntent = new Intent(context, LocationService.class);
        serviceIntent.putExtra(KEY_CONNECTED, isConnected);
        context.startService(serviceIntent);
    }

    private boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
