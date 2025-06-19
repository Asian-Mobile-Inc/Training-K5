package issues7.ex1;

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

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Issues7Ex1Activity extends AppCompatActivity {
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
            String url = IMAGE_URL;
            String fileName = url.substring(url.lastIndexOf('/') + 1);
            File file = new File(getFilesDir(), fileName);

            ExecutorService executorService = Executors.newFixedThreadPool(3);
            executorService.execute(new FileDownloader(url, file.getAbsolutePath(), () -> {
                Log.d("debug", "Download success: " + file.getAbsolutePath());

                runOnUiThread(() -> {
                    loadIntoImageView(file);
                    binding.viewDownload.setEnabled(false);
                    binding.viewDownload.setOnClickListener(null);
                    showToast();
                });
            }));
            executorService.shutdown();
        });
    }

    private void loadIntoImageView(File file) {
        if (file.exists()) {
            Glide.with(this)
                    .load(file)
                    .transform(new CenterCrop(), new RoundedCorners(24))
                    .override(312, 312)
                    .into(binding.ivDownload);
        }
    }

    private void showToast() {
        Toast toast = new Toast(this);
        View view = LayoutInflater.from(this).inflate(R.layout.toast_download_success, null);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
        toast.show();
    }
}
