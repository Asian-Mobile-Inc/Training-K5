package com.example.asian.issue7.ex1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DownloadActivity extends AppCompatActivity {
    private ShapeableImageView mSivDownload;
    private Button mBtnDownload;
    private InputStream mInputStream = null;
    private Bitmap mBitmap = null;
    private final String URL = "https://haycafe.vn/wp-content/uploads/2022/01/hinh-anh-galaxy-vu-tru-dep.jpg";

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
        mSivDownload = findViewById(R.id.sivDownload);
        mBtnDownload = findViewById(R.id.btnDownload);
        initListener();
    }

    private void initListener() {
        mBtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnDownload.setSelected(true);
                new Thread(() -> {
                    URL url = null;
                    int responseCode = -1;
                    try {
                        url = new URL(URL);
                        HttpURLConnection con = null;
                        con = (HttpURLConnection)url.openConnection();
                        con.setDoInput(true);
                        con.connect();
                        responseCode = con.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            mInputStream = con.getInputStream();
                            mBitmap = BitmapFactory.decodeStream(mInputStream);
                            mInputStream.close();
                            runOnUiThread(() -> {
                                mSivDownload.setImageBitmap(mBitmap);
                                toastSuccess();
                            });
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

            }
        });
    }

    private void toastSuccess() {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.item_toast, (ViewGroup) findViewById(R.id.toastLayout));
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
