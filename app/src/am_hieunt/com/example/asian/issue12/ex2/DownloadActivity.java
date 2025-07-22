package com.example.asian.issue12.ex2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.issue12.ex2.model.DownloadImage;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DownloadActivity extends AppCompatActivity {
    private final String URL_IMAGE = "https://haycafe.vn/wp-content/uploads/2022/01/hinh-anh-galaxy-vu-tru-dep.jpg";
    private View mVDownload;
    private Button mBtnDownload;
    private ShapeableImageView mSivImage;
    private TextView mTvProgress, mTvSpeed;
    private ProgressBar mPbDownload;
    private Disposable mDisposable;
    private Toast mToastSuccess, mToastFail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_download_ex2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initListener();
    }

    private void initView() {
        mBtnDownload = findViewById(R.id.btnDownload);
        mVDownload = findViewById(R.id.vDownload);
        mSivImage = findViewById(R.id.sivDownload);
        mTvProgress = findViewById(R.id.tvProgress);
        mTvSpeed = findViewById(R.id.tvSpeed);
        mPbDownload = findViewById(R.id.pbDownload);
        mTvProgress.setVisibility(View.GONE);
        mTvSpeed.setVisibility(View.GONE);
        mPbDownload.setVisibility(View.GONE);
        mToastSuccess = toastSuccess();
        mToastFail = toastFail();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void initListener() {
        mBtnDownload.setOnClickListener(v -> {
            mBtnDownload.setSelected(true);
            mBtnDownload.setEnabled(false);
            mBtnDownload.setText(getString(R.string.download));
            mVDownload.setBackground(getDrawable(R.drawable.my_dotted_success));
            Glide.with(DownloadActivity.this)
                    .load(R.drawable.ic_download_success)
                    .into(mSivImage);
            mSivImage.setVisibility(View.VISIBLE);
            mSivImage.setVisibility(View.GONE);
            mTvProgress.setVisibility(View.VISIBLE);
            mTvSpeed.setVisibility(View.VISIBLE);
            mPbDownload.setVisibility(View.VISIBLE);
            mPbDownload.setProgress(0);
            mTvProgress.setText("0 %");
            mTvSpeed.setText("0 kB/s");
            Observable<DownloadImage> downloadObservable = downloadImageObservable();
            downloadObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(downloadImageObserver());
        });
    }

    private Observer<DownloadImage> downloadImageObserver() {
        return new Observer<DownloadImage>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
            }

            @SuppressLint({"DefaultLocale", "SetTextI18n", "UseCompatLoadingForDrawables"})
            @Override
            public void onNext(@NonNull DownloadImage image) {
                if (image.getProgress() != -1) {
                    mTvProgress.setText(image.getProgress() + "%");
                    String speedDownload = String.format("%.2f", image.getSpeed()) + "kB/s";
                    mTvSpeed.setText(speedDownload);
                    mPbDownload.setProgress(image.getProgress());
                } else {
                    mToastFail.cancel();
                    mToastSuccess.cancel();
                    mSivImage.setVisibility(View.VISIBLE);
                    mTvProgress.setVisibility(View.GONE);
                    mTvSpeed.setVisibility(View.GONE);
                    mPbDownload.setVisibility(View.GONE);
                    mVDownload.setBackground(getDrawable(R.drawable.my_dotted_fail));
                    Glide.with(DownloadActivity.this)
                            .load(R.drawable.ic_download_fail)
                            .into(mSivImage);
                    mBtnDownload.setText(getString(R.string.try_again));
                    mBtnDownload.setSelected(false);
                    mBtnDownload.setEnabled(true);
                    mToastFail = toastFail();
                    mToastFail.show();
                }
                if (image.getImage() != null) {
                    mToastFail.cancel();
                    mToastSuccess.cancel();
                    mSivImage.setVisibility(View.VISIBLE);
                    mTvProgress.setVisibility(View.GONE);
                    mTvSpeed.setVisibility(View.GONE);
                    mPbDownload.setVisibility(View.GONE);
                    mSivImage.setImageBitmap(image.getImage());
                    mBtnDownload.setSelected(false);
                    mBtnDownload.setEnabled(true);
                    mToastSuccess = toastSuccess();
                    mToastSuccess.show();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                mBtnDownload.setSelected(false);
                mBtnDownload.setEnabled(true);
            }
        };
    }

    private Observable<DownloadImage> downloadImageObservable() {
        return Observable.create(new ObservableOnSubscribe<DownloadImage>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<DownloadImage> emitter) throws Throwable {
                long startTime = System.currentTimeMillis();
                DownloadImage image = new DownloadImage(0,0,null);
                if (isInternetDisconnected()) {
                    image.setProgress(-1);
                    emitter.onNext(image);
                    emitter.onComplete();
                } else {
                    URL url = null;
                    ByteArrayOutputStream outputStream;
                    InputStream inputStream = null;
                    url = new URL(URL_IMAGE);
                    HttpURLConnection con = null;
                    con = (HttpURLConnection) url.openConnection();
                    con.setDoInput(true);
                    con.connect();
                    if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        image.setProgress(-1);
                        emitter.onNext(image);
                        emitter.onComplete();
                    } else {
                        inputStream = con.getInputStream();
                        int fileLength = con.getContentLength();
                        byte[] data = new byte[1024];
                        long total = 0;
                        int count = 0;
                        outputStream = new ByteArrayOutputStream();
                        while ((count = inputStream.read(data)) != -1) {
                            if (isInternetDisconnected()) {
                                image.setProgress(-1);
                                break;
                            }
                            total += count;
                            if (fileLength > 0) {
                                long endTime = System.currentTimeMillis();
                                double time = Math.max(1, endTime - startTime) / 1000.0;
                                double speed = count / 1024.0 / time;
                                image.setProgress((int) Math.floor((double) (total * 100) / fileLength));
                                image.setSpeed(speed);
                                emitter.onNext(image);
                            }
                            outputStream.write(data, 0, count);
                        }
                        byte[] imageByte = outputStream.toByteArray();
                        image.setImage(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length));
                        inputStream.close();
                        outputStream.close();
                    }
                    emitter.onNext(image);
                    emitter.onComplete();
                }
            }
        });
    }

    private boolean isInternetDisconnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo == null || !networkInfo.isConnected();
    }

    private Toast toastSuccess() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.item_toast, findViewById(R.id.toastLayout));
        layout.setBackgroundResource(R.drawable.my_toast_success);
        ImageView image = layout.findViewById(R.id.ivToast);
        image.setImageResource(R.drawable.img_success);
        TextView text = layout.findViewById(R.id.tvToast);
        text.setText(getString(R.string.downloaded_successfully));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        return toast;
    }

    private Toast toastFail() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.item_toast, findViewById(R.id.toastLayout));
        layout.setBackgroundResource(R.drawable.my_toast_fail);
        ImageView image = layout.findViewById(R.id.ivToast);
        image.setImageResource(R.drawable.img_fail);
        TextView text = layout.findViewById(R.id.tvToast);
        text.setText(getString(R.string.downloaded_failed));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        return toast;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }
}
