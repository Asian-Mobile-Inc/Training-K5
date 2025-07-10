package issues11;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ActivityIssues11Binding;
import com.example.asian.databinding.DialogPickImageBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import issues11.Adapter.ImageAdapter;
import issues11.Adapter.OnImageListener;
import issues11.Model.Image;
import issues11.Retrofit.RetrofitInstance;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Issues11Activity extends AppCompatActivity implements OnImageListener {
    private ActivityIssues11Binding mBinding;
    private Dialog mDialog;
    private List<Image> mImageLists = new ArrayList<>();
    private ImageAdapter mImageAdapter;
    private static final int Y_OFFSET = 100;
    private static final String ERROR = "ERROR";
    private static final String BASE_URL_UPLOAD = "https://upload.gyazo.com/api/";
    private static final String BASE_URL_GET = "https://api.gyazo.com/api/";
    private static boolean sIsSelectedBtn = false;
    private static boolean sIsOnSubtractIcon = false;
    private static final String UPLOAD_ITEM_ID = "UPLOAD_ITEM_ID";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityIssues11Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initToolbar();
        initAdapter();
        initDefaultImageLists();
        initDialog();
        initListeners();
    }

    private void initToolbar() {
        mBinding.toolbar.setTitle("");
        setSupportActionBar(mBinding.toolbar);
    }

    private void initListeners() {
        mBinding.viewDownload.setOnClickListener(v -> {
            getImageLists();
        });

        mBinding.viewDelete.setOnClickListener(v -> {
            int cnt = 0;
            for (Image item : mImageLists) {
                Log.d("debug", cnt++ + " " + item.isSelected() + " " + item.isChecked() + " " + item.getStatusType());
            }
        });

        ivImageListeners();
    }

    private void initAdapter() {
        mImageAdapter = new ImageAdapter(this, this);
        mBinding.rvImages.setLayoutManager(new GridLayoutManager(this, 3));
        mBinding.rvImages.setAdapter(mImageAdapter);
    }

    private void initDefaultImageLists() {
        mImageLists.add(new Image(Image.TYPE_DEFAULT));
        mImageLists.add(new Image(Image.TYPE_DEFAULT));
        mImageLists.add(new Image(Image.TYPE_DEFAULT));
        mImageLists.add(new Image(UPLOAD_ITEM_ID, null, Image.TYPE_UPLOAD, false, false));
        mImageAdapter.submitList(mImageLists);
    }

    private void ivImageListeners() {
        mBinding.ivSelect.setOnClickListener(v -> {
            if (!mBinding.viewDownload.isEnabled()) {
                sIsSelectedBtn = !sIsSelectedBtn;
                if (!sIsSelectedBtn) {
                    if (sIsOnSubtractIcon) {
                        sIsOnSubtractIcon = false;
                        Glide.with(this)
                                .load(R.drawable.ic_select_selector)
                                .into(mBinding.ivSelect);
                    } else {
                        mBinding.tvDelete.setText(getString(R.string.textview_text_delete_all));
                        showDeleteButton(true);
                    }
                } else {
                    mBinding.tvDelete.setText(getString(R.string.textview_text_delete_all));
                    showDeleteButton(true);
                }
                mBinding.ivSelect.setSelected(sIsSelectedBtn);
                mBinding.viewDelete.setEnabled(sIsSelectedBtn);
                mImageLists = getImages();
                mImageAdapter.submitList(mImageLists);
            }
        });
    }

    private @NonNull List<Image> getImages() {
        List<Image> newLists = new ArrayList<>();
        Image uploadItem = null;
//        for (int i = 0; i < mImageLists.size(); i++) {
//            Image item = mImageLists.get(i);
//            if (item.getStatusType() != Image.TYPE_UPLOAD) {
//                Image newItem = new Image(item.getId(), item.getUrl(), item.getStatusType(), sIsSelectedBtn, sIsSelectedBtn);
//                newLists.add(newItem);
//            } else {
//                uploadItem = item;
//            }
//        }
        for (Image item: mImageLists) {
            if (UPLOAD_ITEM_ID.equals(item.getId())) {
                uploadItem = item;
            } else {
                Image newItem = new Image(item.getId(), item.getUrl(), item.getStatusType(), sIsSelectedBtn, sIsSelectedBtn);
                newLists.add(newItem);
            }
        }
        if (uploadItem != null) {
            newLists.add(uploadItem);
        }
        return newLists;
    }

    private void getImageLists() {
        RetrofitInstance.getApiInterface(BASE_URL_GET).getAllImages().enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(@NonNull Call<List<Image>> call, @NonNull Response<List<Image>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mImageLists = new ArrayList<>(response.body());
                    mImageLists.add(new Image(UPLOAD_ITEM_ID, null, Image.TYPE_UPLOAD, false, false));

                    runOnUiThread(() -> {
                        mImageAdapter.submitList(mImageLists);
                        mBinding.viewDownload.setEnabled(false);
                        showToast(true);
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Image>> call, @NonNull Throwable t) {
                Log.e(ERROR, Objects.requireNonNull(t.getMessage()));
                List<Image> tmpLists = new ArrayList<>();
                tmpLists.add(new Image(Image.TYPE_FAILED));
                tmpLists.add(new Image(Image.TYPE_FAILED));
                tmpLists.add(new Image(Image.TYPE_FAILED));
                tmpLists.add(new Image(UPLOAD_ITEM_ID, null, Image.TYPE_UPLOAD, false, false));
                mImageLists = new ArrayList<>(tmpLists);
                runOnUiThread(() -> {
                    mImageAdapter.submitList(mImageLists);
                    mBinding.tvDownload.setText(getString(R.string.textview_text_try_again));
                    showToast(false);
                });
            }
        });
//        RetrofitInstance.getApiInterface().getImageById("8e658e75fad0d20ee073ab44d58a9545").enqueue(new Callback<Image>() {
//            @Override
//            public void onResponse(Call<Image> call, Response<Image> response) {
//                Image image = response.body();
//                Log.d("debug", image.getId() + " " + image.getUrl());
//            }
//
//            @Override
//            public void onFailure(Call<Image> call, Throwable t) {
//
//            }
//        });
    }

    private void uploadImage(File file) {
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                "imagedata",
                file.getName(),
                RequestBody.create(MediaType.parse("image/*"), file)
        );

        RetrofitInstance.getApiInterface(BASE_URL_UPLOAD)
                .uploadImage(filePart)
                .enqueue(new Callback<Image>() {
                    @Override
                    public void onResponse(@NonNull Call<Image> call, @NonNull Response<Image> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Image item = response.body();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Image> call, @NonNull Throwable t) {
                        Log.e("debug", Objects.requireNonNull(t.getMessage()));
                    }
                });
    }

    private void openPhotoPickerActivityForResult() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mPhotoPickerCameraActivityResultLauncher.launch(intent);
    }

    private void openCameraActivityForResult() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCameraActivityResultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> mCameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        if (extras != null) {
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            try {
                                File f = new File(this.getCacheDir(), "images");
                                f.createNewFile();

                                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
                                byte[] bitmapData = bos.toByteArray();

                                FileOutputStream fos = new FileOutputStream(f);
                                fos.write(bitmapData);
                                fos.flush();
                                fos.close();
                                uploadImage(f);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            });

    private final ActivityResultLauncher<Intent> mPhotoPickerCameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent selectedImage = result.getData();
                    if (selectedImage != null) {
                        if (selectedImage.getData() != null) {
                            Uri uri = selectedImage.getData();
                            if (uri.getPath() != null) {
                                try {
                                    File file = uriToFile(uri, getApplicationContext());
                                    uploadImage(file);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }
            });

    public File uriToFile(Uri uri, Context context) throws IOException {
        File file = new File(context.getCacheDir(), "new_file");
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
             OutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }
        return file;
    }

    private void initDialog() {
        mDialog = new Dialog(this);
    }

    private void initDialogListeners() {
        Objects.requireNonNull(mDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        DialogPickImageBinding dialogPickImageBinding = DialogPickImageBinding.inflate(getLayoutInflater());
        mDialog.setContentView(dialogPickImageBinding.getRoot());
        Objects.requireNonNull(mDialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDialog.setCancelable(false);

        dialogPickImageBinding.ivCamera.setOnClickListener(v -> {
            mDialog.dismiss();
            openCameraActivityForResult();
        });
        dialogPickImageBinding.ivGallery.setOnClickListener(v -> {
            mDialog.dismiss();
            openPhotoPickerActivityForResult();
        });
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }

    private void showToast(boolean status) {
        Toast toast = new Toast(this);
        View view = View.inflate(this, status ? R.layout.toast_download_success : R.layout.toast_download_failed, null);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, Y_OFFSET);
        toast.show();
    }

    @Override
    public void onUploadImage() {
        initDialogListeners();
    }

    @Override
    public void onSubtractIcon(int itemDelete) {
        sIsOnSubtractIcon = true;
        Glide.with(this)
                .load(R.drawable.ic_subtract_purple)
                .into(mBinding.ivSelect);
        showDeleteButton(true);
        mBinding.tvDelete.setText(getString(R.string.textview_text_delete_image_item, itemDelete));
        Log.d("debug", String.valueOf(itemDelete));
        mBinding.viewDelete.setEnabled(true);
    }

    @Override
    public void onUpdateImageLists(List<Image> imageLists) {
        mImageLists.clear();
        mImageLists.addAll(imageLists);
    }

    private void showDeleteButton(boolean condition) {
        mBinding.viewDelete.setVisibility(condition ? View.VISIBLE : View.GONE);
//        mBinding.ivDelete.setVisibility(condition ? View.VISIBLE : View.GONE);
//        mBinding.tvDelete.setVisibility(condition ? View.VISIBLE : View.GONE);
        mBinding.viewDownload.setVisibility(condition ? View.GONE : View.VISIBLE);
        mBinding.ivDownloadIcon.setVisibility(condition ? View.GONE : View.VISIBLE);
        mBinding.tvDownload.setVisibility(condition ? View.GONE : View.VISIBLE);
    }
}
