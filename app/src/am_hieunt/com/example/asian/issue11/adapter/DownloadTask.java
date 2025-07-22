package com.example.asian.issue11.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.issue11.model.Image;
import com.example.asian.issue11.view.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Integer, Bitmap> {
    private final ImageAdapter.ViewHolder holder;
    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final OnDownloadDone listener;
    private static final String DOWNLOAD_SUCCESS = "SUCCESS";
    private static final String DOWNLOAD_FAIL = "FAIL";

    public interface OnDownloadDone {
        void onDownloadListener(String text);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public DownloadTask(Context context, ImageAdapter.ViewHolder holder, OnDownloadDone listener) {
        super();
        this.holder = holder;
        this.context = context;
        this.listener =listener;
        this.holder.mVImage.setBackground(this.context.getDrawable(R.drawable.my_bg_image));
        this.holder.mTvProgress.setVisibility(View.VISIBLE);
        this.holder.mPbProgress.setVisibility(View.VISIBLE);
        this.holder.mTvProgress.setText("0 %");
        this.holder.mPbProgress.setProgress(0);
        this.holder.mSivImage.setVisibility(View.GONE);
        this.holder.mIvSelect.setVisibility(View.GONE);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        if (!isInternet()) return null;
        URL url = null;
        Bitmap bitmap = null;
        ByteArrayOutputStream outputStream;
        try {
            InputStream inputStream = null;
            url = new URL(this.holder.mImage.getUrl());
            HttpURLConnection con = null;
            con = (HttpURLConnection) url.openConnection();
            con.setDoInput(true);
            con.connect();
            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            inputStream = con.getInputStream();
            int fileLength = con.getContentLength();
            byte[] data = new byte[1024];
            long total = 0;
            int count = 0;
            outputStream = new ByteArrayOutputStream();
            while ((count = inputStream.read(data)) != -1) {
                if (!isInternet()) return null;
                if (isCancelled()) {
                    inputStream.close();
                    return null;
                } else {
                    total += count;
                    if (fileLength > 0) {
                        publishProgress((int) total * 100 / fileLength);
                    }
                    outputStream.write(data, 0, count);
                }
            }
            byte[] imageByte = outputStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bitmap;
    }

    private boolean isInternet() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        this.holder.mTvProgress.setText(values[0] + "%");
        this.holder.mPbProgress.setProgress(values[0]);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        holder.mTvProgress.setVisibility(View.GONE);
        holder.mPbProgress.setVisibility(View.GONE);
        holder.mSivImage.setVisibility(View.VISIBLE);
        if (bitmap != null) {
            holder.mIvSelect.setVisibility(View.VISIBLE);
            holder.mImage.setBitmap(bitmap);
            holder.mImage.setBackground(R.drawable.my_bg_image);
            Glide.with(context)
                    .load(bitmap)
                    .into(holder.mSivImage);
            Glide.with(context)
                    .load(holder.mImage.getSelect())
                    .into(holder.mIvSelect);
            if (listener != null) {
                listener.onDownloadListener(DOWNLOAD_SUCCESS);
            }
        } else {
            holder.mImage.setBackground(R.drawable.my_bg_image_fail);
            holder.mImage.setBitmap(MainActivity.convertDrawableToBitmap(context.getDrawable(R.drawable.ic_load_image_fail)));
            holder.mIvSelect.setVisibility(View.GONE);
            holder.mVImage.setBackground(context.getDrawable(R.drawable.my_bg_image_fail));
            Glide.with(context)
                    .load(R.drawable.ic_load_image_fail)
                    .into(holder.mSivImage);
            if (listener != null) {
                listener.onDownloadListener(DOWNLOAD_FAIL);
            }
        }
    }
}
