package com.example.asian.issue7.ex2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Integer, Bitmap> {
    @SuppressLint("StaticFieldLeak")
    private final Activity contextParent;
    public final String URL_IMAGE = "https://haycafe.vn/wp-content/uploads/2022/01/hinh-anh-galaxy-vu-tru-dep.jpg";
    private Bitmap mBitmap = null;
    @SuppressLint("StaticFieldLeak")
    private final TextView mTvProgress;
    @SuppressLint("StaticFieldLeak")
    private final TextView mTvSpeed;
    @SuppressLint("StaticFieldLeak")
    private final ProgressBar mPbDownload;
    @SuppressLint("StaticFieldLeak")
    private final ShapeableImageView mSivDownload;
    @SuppressLint("StaticFieldLeak")
    private final Button mBtnDownload;
    @SuppressLint("StaticFieldLeak")
    private final LinearLayout mLlDownload;
    private final long mStartTime;

    public DownloadTask(Activity contextParent) {
        this.contextParent = contextParent;
        mTvProgress = this.contextParent.findViewById(R.id.tvProgress);
        mTvSpeed = this.contextParent.findViewById(R.id.tvSpeed);
        mPbDownload = this.contextParent.findViewById(R.id.pbDownload);
        mSivDownload = contextParent.findViewById(R.id.sivDownload);
        mBtnDownload = contextParent.findViewById(R.id.btnDownload);
        mLlDownload = contextParent.findViewById(R.id.llDownload);
        mSivDownload.setVisibility(View.GONE);
        mTvProgress.setVisibility(View.VISIBLE);
        mTvSpeed.setVisibility(View.VISIBLE);
        mPbDownload.setVisibility(View.VISIBLE);
        mStartTime = System.currentTimeMillis();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        if (!isInternet()) return null;
        URL url = null;
        ByteArrayOutputStream outputStream;
        try {
            InputStream inputStream = null;
            url = new URL(URL_IMAGE);
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
                        publishProgress((int) total * 100 / fileLength, count);
                    }
                    outputStream.write(data, 0, count);
                }
            }
            byte[] imageByte = outputStream.toByteArray();
            mBitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return mBitmap;
    }

    private boolean isInternet() {
        ConnectivityManager cm = (ConnectivityManager) contextParent.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        long mEndTime = System.currentTimeMillis();
        double time = Math.max(1, mEndTime - mStartTime) / 1000.0;
        double speed = values[1] / 1024.0 / time;
        @SuppressLint("DefaultLocale") String speedDownload = String.format("%.2f", speed) + "kB/s";
        mTvSpeed.setText(speedDownload);
        mTvProgress.setText(values[0] + "%");
        mPbDownload.setProgress(values[0]);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mSivDownload.setVisibility(View.VISIBLE);
        mPbDownload.setVisibility(View.GONE);
        mTvProgress.setVisibility(View.GONE);
        mTvSpeed.setVisibility(View.GONE);
        if (bitmap != null) {
            mBtnDownload.setText(contextParent.getString(R.string.download));
            mSivDownload.setImageBitmap(mBitmap);
            toastSuccess();
        } else {
            mLlDownload.setBackground(contextParent.getDrawable(R.drawable.my_dotted_fail));
            mBtnDownload.setClickable(true);
            mBtnDownload.setSelected(false);
            Glide.with(contextParent)
                    .load(R.drawable.ic_download_fail)
                    .into(mSivDownload);
            mBtnDownload.setText(contextParent.getString(R.string.try_again));
            toastFail();
        }
    }

    private void toastSuccess() {
        LayoutInflater inflater = contextParent.getLayoutInflater();
        View layout = inflater.inflate(R.layout.item_toast, contextParent.findViewById(R.id.toastLayout));
        layout.setBackgroundResource(R.drawable.my_toast_success);
        ImageView image = layout.findViewById(R.id.ivToast);
        image.setImageResource(R.drawable.img_success);
        TextView text = layout.findViewById(R.id.tvToast);
        text.setText(contextParent.getString(R.string.downloaded_successfully));
        Toast toast = new Toast(contextParent.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    private void toastFail() {
        LayoutInflater inflater = contextParent.getLayoutInflater();
        View layout = inflater.inflate(R.layout.item_toast, contextParent.findViewById(R.id.toastLayout));
        layout.setBackgroundResource(R.drawable.my_toast_fail);
        ImageView image = layout.findViewById(R.id.ivToast);
        image.setImageResource(R.drawable.img_fail);
        TextView text = layout.findViewById(R.id.tvToast);
        text.setText(contextParent.getString(R.string.downloaded_failed));
        Toast toast = new Toast(contextParent.getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
