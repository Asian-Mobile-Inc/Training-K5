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
    private ActivityIssues7Binding binding;
    private static final String IMAGE_URL = "https://haycafe.vn/wp-content/uploads/2022/01/hinh-anh-galaxy-vu-tru-dep.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIssues7Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initListeners();
    }

    private void initListeners() {
        binding.viewDownload.setOnClickListener(v -> {
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

                byte data[] = new byte[1024];
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
                Log.e("Error", Objects.requireNonNull(e.getMessage()));
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return bitmap;
        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            binding.progressBarDownload.setProgress(progress[0]);
            binding.tvProgress.setText(progress[0] + "%");
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
                .into(binding.ivDownload);
        binding.viewDownload.setEnabled(false);
        binding.viewDownload.setOnClickListener(null);
        showToastSuccess();
    }

    private void setupLoadFailed() {
        binding.tvDownload.setText(getString(R.string.textview_text_try_again));
        Glide.with(getApplicationContext())
                .load(R.drawable.img_failed)
                .transform(new CenterCrop(), new RoundedCorners(24))
                .override(312, 312)
                .into(binding.ivDownload);
        showToastFailed();
    }

    private void hideProgressBar() {
        binding.progressBarDownload.setVisibility(View.GONE);
        binding.tvProgress.setVisibility(View.GONE);
    }

    private void setupProgressBar() {
        binding.progressBarDownload.setIndeterminate(false);
        binding.progressBarDownload.setMax(100);
        binding.progressBarDownload.setVisibility(View.VISIBLE);
        binding.tvProgress.setVisibility(View.VISIBLE);
    }

    private void hideIvDownload() {
        Glide.with(getApplicationContext())
                .load(0)
                .transform(new CenterCrop(), new RoundedCorners(24))
                .override(312, 312)
                .into(binding.ivDownload);
    }
}
