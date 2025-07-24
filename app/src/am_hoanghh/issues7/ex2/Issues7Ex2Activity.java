package issues7.ex2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.asian.R;
import com.example.asian.databinding.ActivityIssues7Binding;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class Issues7Ex2Activity extends AppCompatActivity {
    private ActivityIssues7Binding mBinding;
    private static final String IMAGE_URL = "https://haycafe.vn/wp-content/uploads/2022/01/hinh-anh-galaxy-vu-tru-dep.jpg";
    private static final String ERROR = "ERROR";
    private static final int WIDTH_IMAGE_VIEW = 312;
    private static final int HEIGHT_IMAGE_VIEW = 312;
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
        mBinding.viewDownload.setOnClickListener(v -> {
            MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
            myAsyncTasks.execute();
        });
    }

    public class MyAsyncTasks extends AsyncTask<Void, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setupProgressBar();
            hideIvDownload();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(IMAGE_URL);
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
                        publishProgress((int) (total * 100 / fileLength));
                    }
                }

                byte[] imageData = outputStream.toByteArray();
                bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

                inputStream.close();
                outputStream.close();

            } catch (Exception e) {
                Log.e(ERROR, Objects.requireNonNull(e.getMessage()));
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return bitmap;
        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            mBinding.progressBarDownload.setProgress(progress[0]);
            mBinding.tvProgress.setText(getString(R.string.text_progress, progress[0]));
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                hideProgressBar();
                setupLoadSuccess(bitmap);
            } else {
                hideProgressBar();
                setupLoadFailed();
            }
        }
    }

    private void showToastSuccess() {
        Toast toast = new Toast(this);
        View view = View.inflate(this, R.layout.toast_download_success, null);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, Y_OFFSET);
        toast.show();
    }

    private void showToastFailed() {
        Toast toast = new Toast(this);
        View view = View.inflate(this, R.layout.toast_download_failed, null);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, Y_OFFSET);
        toast.show();
    }

    private void setupLoadSuccess(Bitmap bitmap) {
        Glide.with(getApplicationContext())
                .load(bitmap)
                .transform(new CenterCrop(), new RoundedCorners(ROUNDING_RADIUS))
                .override(WIDTH_IMAGE_VIEW, HEIGHT_IMAGE_VIEW)
                .into(mBinding.ivDownload);
        mBinding.viewDownload.setEnabled(false);
        mBinding.viewDownload.setOnClickListener(null);
        showToastSuccess();
    }

    private void setupLoadFailed() {
        mBinding.tvDownload.setText(getString(R.string.textview_text_try_again));
        Glide.with(getApplicationContext())
                .load(R.drawable.img_failed)
                .transform(new CenterCrop(), new RoundedCorners(ROUNDING_RADIUS))
                .override(WIDTH_IMAGE_VIEW, HEIGHT_IMAGE_VIEW)
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
                .transform(new CenterCrop(), new RoundedCorners(ROUNDING_RADIUS))
                .override(WIDTH_IMAGE_VIEW, HEIGHT_IMAGE_VIEW)
                .into(mBinding.ivDownload);
    }
}
