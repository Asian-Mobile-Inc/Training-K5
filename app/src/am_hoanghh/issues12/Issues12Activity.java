package issues12;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.asian.R;
import com.example.asian.databinding.ActivityIssues7Binding;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Issues12Activity extends AppCompatActivity {
    private ActivityIssues7Binding mBinding;
    private static final String IMAGE_URL = "https://haycafe.vn/wp-content/uploads/2022/01/hinh-anh-galaxy-vu-tru-dep.jpg";
    private static final String ERROR = "ERROR";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityIssues7Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        final Bitmap[] bitmap = {null};

        Observable.just(IMAGE_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        setupProgressBar();
                        hideIvDownload();
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        HttpURLConnection urlConnection = null;
                        try {
                            URL url = new URL(s);
                            urlConnection = (HttpURLConnection) url.openConnection();
                            InputStream inputStream = urlConnection.getInputStream();
                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                            int fileLength = urlConnection.getContentLength();

                            byte[] data = new byte[1024];
                            long total = 0;
                            int count;
                            while ((count = inputStream.read(data)) != -1) {
                                total += count;

                                outputStream.write(data, 0, count);

                                if (fileLength > 0) {
                                    int progress = (int) (total * 100 / fileLength);
                                    mBinding.progressBarDownload.setProgress(progress);
                                    mBinding.tvProgress.setText(getString(R.string.text_progress, progress));
                                }
                            }

                            byte[] imageData = outputStream.toByteArray();
                            bitmap[0] = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

                            inputStream.close();
                            outputStream.close();

                        } catch (Exception e) {
                            Log.e(ERROR, e.getMessage());
                        } finally {
                            if (urlConnection != null) {
                                urlConnection.disconnect();
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(ERROR, Objects.requireNonNull(e.getMessage()));
                        hideProgressBar();
                        setupLoadFailed();
                    }

                    @Override
                    public void onComplete() {
                        hideProgressBar();
                        setupLoadSuccess(bitmap[0]);
                    }
                });
    }

    private void showToastSuccess() {
        Toast toast = new Toast(this);
        View view = LayoutInflater.from(this).inflate(R.layout.toast_download_success, null);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    private void showToastFailed() {
        Toast toast = new Toast(this);
        View view = LayoutInflater.from(this).inflate(R.layout.toast_download_failed, null);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }

    private void setupLoadSuccess(Bitmap bitmap) {
        Glide.with(getApplicationContext())
                .load(bitmap)
                .transform(new CenterCrop(), new RoundedCorners(24))
                .override(312, 312)
                .into(mBinding.ivDownload);
        mBinding.viewDownload.setEnabled(false);
        mBinding.viewDownload.setOnClickListener(null);
        showToastSuccess();
    }

    private void setupLoadFailed() {
        mBinding.tvDownload.setText(getString(R.string.textview_text_try_again));
        Glide.with(getApplicationContext())
                .load(R.drawable.img_failed)
                .transform(new CenterCrop(), new RoundedCorners(24))
                .override(312, 312)
                .into(mBinding.ivDownload);
        showToastFailed();
    }

    private void hideProgressBar() {
        mBinding.progressBarDownload.setVisibility(View.GONE);
        mBinding.tvProgress.setVisibility(View.GONE);
    }

    private void setupProgressBar() {
        mBinding.progressBarDownload.setIndeterminate(false);
        mBinding.progressBarDownload.setMax(100);
        mBinding.progressBarDownload.setVisibility(View.VISIBLE);
        mBinding.tvProgress.setVisibility(View.VISIBLE);
    }

    private void hideIvDownload() {
        Glide.with(getApplicationContext())
                .load(0)
                .transform(new CenterCrop(), new RoundedCorners(24))
                .override(312, 312)
                .into(mBinding.ivDownload);
    }
}
