package com.example.asian.issue12.ex1;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asian.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DownloadActivity extends AppCompatActivity {
    private Disposable mDisposable;
    private ShapeableImageView mSivImage;
    private Button mBtnDownload;
    private final String URL_IMAGE = "https://haycafe.vn/wp-content/uploads/2022/01/hinh-anh-galaxy-vu-tru-dep.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_download_ex1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
        initListener();

    }

    private void initView() {
        mSivImage = findViewById(R.id.sivDownload);
        mBtnDownload = findViewById(R.id.btnDownload);
    }

    private void initListener() {
        mBtnDownload.setOnClickListener(v -> {
            mBtnDownload.setSelected(true);
            mBtnDownload.setEnabled(false);
            Single<Bitmap> imageObservable = downloadImageObservable();
            SingleObserver<Bitmap> imageObserver = downloadImageSingleObserver();
            imageObservable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(imageObserver);
        });
    }

    private SingleObserver<Bitmap> downloadImageSingleObserver() {
        return new SingleObserver<Bitmap>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onSuccess(@NonNull Bitmap b) {
                mSivImage.setImageBitmap(b);
                toastSuccess();
                mBtnDownload.setSelected(false);
                mBtnDownload.setEnabled(true);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                mBtnDownload.setSelected(false);
                mBtnDownload.setEnabled(true);
            }
        };
    }

    private Single<Bitmap> downloadImageObservable() {
        return Single.create(new SingleOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Bitmap> emitter) throws Throwable {
                Bitmap bitmap = null;
                URL url = null;
                int responseCode = -1;
                InputStream inputStream = null;
                url = new URL(URL_IMAGE);
                HttpURLConnection con = null;
                con = (HttpURLConnection)url.openConnection();
                con.setDoInput(true);
                con.connect();
                responseCode = con.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream = con.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                }
                emitter.onSuccess(bitmap);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }
    
    private void toastSuccess() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.item_toast, findViewById(R.id.toastLayout));
        layout.setBackgroundResource(R.drawable.my_toast_success);
        ImageView image = layout.findViewById(R.id.ivToast);
        image.setImageResource(R.drawable.img_success);
        TextView text = layout.findViewById(R.id.tvToast);
        text.setText(getString(R.string.downloaded_successfully));
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
