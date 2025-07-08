package issues12;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
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
    private static final int IMAGE_VIEW_WIDTH = 312;
    private static final int IMAGE_VIEW_HEIGHT = 312;
    private static final int ROUNDING_RADIUS = 24;
    private static final int Y_OFFSET = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityIssues7Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initListeners();
    }

    private void initListeners() {
        mBinding.viewDownload.setOnClickListener(mOnClickListener);
    }

    private final View.OnClickListener mOnClickListener = view -> {
        Observable.fromCallable(() -> {
                    HttpURLConnection urlConnection = null;
                    Bitmap bitmap = null;
                    try {
                        URL url = new URL(IMAGE_URL);
                        urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream inputStream = urlConnection.getInputStream();
                        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                        int fileLength = urlConnection.getContentLength();

                        Handler handler = new Handler(Looper.getMainLooper());

                        byte[] data = new byte[1024];
                        long total = 0;
                        int count;
                        while ((count = inputStream.read(data)) != -1) {
                            total += count;
                            outputStream.write(data, 0, count);

                            if (fileLength > 0) {
                                long finalTotal = total;
                                handler.post(() -> {
                                    int progress = (int) (finalTotal * 100 / fileLength);
                                    mBinding.progressBarDownload.setProgress(progress);
                                    mBinding.tvProgress.setText(getString(R.string.text_progress, progress));
                                });
                            }
                        }

                        byte[] imageData = outputStream.toByteArray();
                        bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                        inputStream.close();
                        outputStream.close();
                    } catch (Exception e) {
                        Log.e(ERROR, Log.getStackTraceString(e));
                    } finally {
                        if (urlConnection != null) {
                            urlConnection.disconnect();
                        }
                    }

                    return bitmap;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        setupProgressBar();
                        hideIvDownload();
                    }

                    @Override
                    public void onNext(@NonNull Bitmap bitmap) {
                        if (bitmap != null) {
                            hideProgressBar();
                            setupLoadSuccess(bitmap);
                        } else {
                            hideProgressBar();
                            setupLoadFailed();
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

                    }
                });
    };

    private void showToast(boolean status) {
        Toast toast = new Toast(this);
        View view = View.inflate(this, status ? R.layout.toast_download_success : R.layout.toast_download_failed, null);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, Y_OFFSET);
        toast.show();
    }

    private void setupLoadSuccess(Bitmap bitmap) {
        Glide.with(this)
                .load(bitmap)
                .transform(new CenterCrop(), new RoundedCorners(ROUNDING_RADIUS))
                .override(IMAGE_VIEW_WIDTH, IMAGE_VIEW_HEIGHT)
                .into(mBinding.ivDownload);
        mBinding.viewDownload.setEnabled(false);
        mBinding.viewDownload.setOnClickListener(null);
        showToast(true);
    }

    private void setupLoadFailed() {
        mBinding.tvDownload.setText(getString(R.string.textview_text_try_again));
        Glide.with(this)
                .load(R.drawable.img_failed)
                .transform(new CenterCrop(), new RoundedCorners(ROUNDING_RADIUS))
                .override(IMAGE_VIEW_WIDTH, IMAGE_VIEW_HEIGHT)
                .into(mBinding.ivDownload);
        showToast(false);
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
        Glide.with(this)
                .load(0)
                .transform(new CenterCrop(), new RoundedCorners(ROUNDING_RADIUS))
                .override(IMAGE_VIEW_WIDTH, IMAGE_VIEW_HEIGHT)
                .into(mBinding.ivDownload);
    }
}
