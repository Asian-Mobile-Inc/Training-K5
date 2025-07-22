package com.example.asian.issue11.view;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.issue11.adapter.ImageAdapter;
import com.example.asian.issue11.api.ApiService;
import com.example.asian.issue11.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private List<Image> mImages;
    private ImageAdapter mImageAdapter;
    private ImageButton mIbSelect;
    private Button mBtnDownload;
    private int mIsSelected = R.drawable.ic_tick;
    private Boolean mCheck = false;
    private ActivityResultLauncher<PickVisualMediaRequest> mPickMedia;
    private final String KEY_IMAGE ="imagedata";
    private final String KEY_UPLOAD = "upload";
    private static final String ACCESS_TOKEN = "Bearer 37NgYdmLpLPbBFla_63tC23jBk9_iJaIxXdm4l9KX68";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_issue10);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initPickMedia();
        initView();
        initData();
        initListener();
    }

    private void initPickMedia() {
        mPickMedia = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                File file = createFile(uri);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part imageData = MultipartBody.Part.createFormData(KEY_IMAGE, file.getName(), requestBody);
                ApiService.apiService.uploadImage(ACCESS_TOKEN, imageData).enqueue(new Callback<Image>() {
                    @Override
                    public void onResponse(Call<Image> call, Response<Image> response) {
                        if (response.isSuccessful()) {
                            mCheck = true;
                            mBtnDownload.setSelected(false);
                            mBtnDownload.setEnabled(true);
                            Image image = response.body();
                            boolean check = true;
                            for (int i = 0; i < mImageAdapter.getCurrentList().size(); i++) {
                                if (image.getImage_id().equals(mImageAdapter.getCurrentList().get(i).getImage_id())) {
                                    check = false;
                                    break;
                                }
                            }
                            if (check) {
                                image.setSelect((mIsSelected == R.drawable.ic_tick) ? R.drawable.ic_cancel : (mIsSelected == R.drawable.ic_tick_all) ? R.drawable.ic_selected : R.drawable.ic_un_select);
                                ContentResolver resolver = getContentResolver();
                                try {
                                    InputStream ipStream = resolver.openInputStream(uri);
                                    Bitmap bitmap = BitmapFactory.decodeStream(ipStream);
                                    image.setBitmap(bitmap);
                                    mImageAdapter.addImage(image);
                                    if (ipStream != null) {
                                        ipStream.close();
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            file.deleteOnExit();
                        }
                    }

                    @Override
                    public void onFailure(Call<Image> call, Throwable t) {
                        mBtnDownload.setSelected(false);
                        mBtnDownload.setEnabled(true);
                    }
                });
            }
        });
    }

    private File createFile(Uri uri) {
        File file;
        ContentResolver resolver = getContentResolver();
        InputStream inputStream = null;
        try {
            inputStream = resolver.openInputStream(uri);
            file = new File(getCacheDir(), KEY_UPLOAD);
            FileOutputStream outputStream = new FileOutputStream(file);
            if (inputStream != null) {
                byte[] buffer = new byte[1024];
                int count;
                while ((count = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, count);
                }
                inputStream.close();
            }
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }

    private void initData() {
        mImages = new ArrayList<>();
        ApiService.apiService.getImages(ACCESS_TOKEN).enqueue(new Callback<List<Image>>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onResponse(Call<List<Image>> call, Response<List<Image>> response) {
                mCheck = response.body().size() != 0;
                mImages = response.body();
                for (int i = 0; i < mImages.size(); i++) {
                    mImages.get(i).setBitmap(convertDrawableToBitmap(getDrawable(R.drawable.ic_load_image)));
                }
                mImages.add(new Image("", ""));
                mImages.get(mImages.size() - 1).setBitmap(convertDrawableToBitmap(getDrawable(R.drawable.ic_upload)));
                mImageAdapter.submitList(mImages);
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onFailure(Call<List<Image>> call, Throwable t) {
            }
        });
    }

    private void initView() {
        RecyclerView rvImage = findViewById(R.id.rvImage);
        mImageAdapter = new ImageAdapter(this, mPickMedia,new ImageAdapter.OnSelectedImage() {
            @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
            @Override
            public void onSelectedListener(int selected) {
                mBtnDownload.setBackground(getDrawable(R.drawable.my_button_delete_image));
                int size = getQuantityImageDelete();
                if (size == mImageAdapter.getCurrentList().size() - 1) {
                    mIsSelected = R.drawable.ic_tick_all;
                    Glide.with(MainActivity.this)
                            .load(R.drawable.ic_tick_all)
                            .into(mIbSelect);
                    mBtnDownload.setText(getString(R.string.delete_all));
                } else {
                    mIsSelected = R.drawable.ic_un_tick;
                    Glide.with(MainActivity.this)
                            .load(R.drawable.ic_un_tick)
                            .into(mIbSelect);
                    mBtnDownload.setText(getString(R.string.delete) + " (" + size + ")");
                }
            }

            @Override
            public void onDownloadState(Boolean state) {
                mBtnDownload.setSelected(false);
                mBtnDownload.setEnabled(true);
                mCheck = true;
                if (!state) {
                    mBtnDownload.setText(getString(R.string.try_again));
                }
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onDeleteState(Boolean state) {
                mBtnDownload.setSelected(false);
                mBtnDownload.setEnabled(true);
                mCheck = mImageAdapter.getCurrentList().size() != 2;
                if (state) {
                    mBtnDownload.setText(getString(R.string.download));
                    mBtnDownload.setBackground(getDrawable(R.drawable.my_button_download));
                    mBtnDownload.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_download), null, null, null);
                    Glide.with(MainActivity.this)
                            .load(R.drawable.ic_tick)
                            .into(mIbSelect);
                    mIsSelected = R.drawable.ic_tick;
                }
            }
        });
        rvImage.setAdapter(mImageAdapter);
        rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        mIbSelect = findViewById(R.id.ibSelect);
        mBtnDownload = findViewById(R.id.btnDownload);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initListener() {
        mIbSelect.setOnClickListener(v -> {
            if (mCheck) {
                if (mIsSelected == R.drawable.ic_tick) {
                    mIsSelected = R.drawable.ic_tick_all;
                    mImageAdapter.setSelect(R.drawable.ic_selected);
                    Glide.with(this)
                            .load(R.drawable.ic_tick_all)
                            .into(mIbSelect);
                    mBtnDownload.setText(getString(R.string.delete_all));
                    mBtnDownload.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_delete_all), null, null, null);
                    mBtnDownload.setBackground(getDrawable(R.drawable.my_button_delete_image));
                } else if (mIsSelected == R.drawable.ic_tick_all || mIsSelected == R.drawable.ic_un_tick) {
                    mIsSelected = R.drawable.ic_tick;
                    mImageAdapter.setSelect(R.drawable.ic_cancel);
                    mBtnDownload.setText(getString(R.string.download));
                    mBtnDownload.setBackground(getDrawable(R.drawable.my_button_download));
                    mBtnDownload.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_download), null, null, null);
                    Glide.with(this)
                            .load(R.drawable.ic_tick)
                            .into(mIbSelect);
                }
            }
        });
        mBtnDownload.setOnClickListener(v -> {
            if (mCheck) {
                if (mBtnDownload.getText().equals(getString(R.string.download)) || mBtnDownload.getText().equals(getString(R.string.try_again))) {
                    mBtnDownload.setSelected(true);
                    mBtnDownload.setEnabled(false);
                    mCheck = false;
                    mBtnDownload.setText(getString(R.string.download));
                    mBtnDownload.setBackground(getDrawable(R.drawable.my_button_download));
                    mBtnDownload.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_download), null, null, null);
                    mImageAdapter.setDownload();
                } else {
                    if (getQuantityImageDelete() > 0) {
                        mImageAdapter.setDelete();
                        mCheck = false;
                        mBtnDownload.setSelected(true);
                        mBtnDownload.setEnabled(false);
                    }
                }
            }
        });
    }

    private int getQuantityImageDelete() {
        List<Image> images = mImageAdapter.getCurrentList();
        int d = 0;
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).getSelect() == R.drawable.ic_selected) {
                d++;
            }
        }
        return d;
    }

    public static Bitmap convertDrawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
