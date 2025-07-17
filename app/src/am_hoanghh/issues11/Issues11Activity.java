package issues11;

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
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Collections;
import java.util.Comparator;
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
    private static boolean sIsSelectedBtn = false;
    private static boolean sIsOnSubtractIcon = false;
    private static final int Y_OFFSET = 100;
    private static final String ERROR = "ERROR";
    private static final String BASE_URL_UPLOAD = "https://upload.gyazo.com/api/";
    private static final String BASE_URL_GET = "https://api.gyazo.com/api/";
    private static final String UPLOAD_ITEM_ID = "UPLOAD_ITEM_ID";
    private static final String IMAGE_DATA = "imagedata";
    private static final String DEBUG = "debug";
    private static final String DATA = "data";
    private static final String IMAGES = "images";
    private static final String NEW_FILE = "new_file";

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

        viewDeleteListeners();
        ivImageListeners();
    }

    private void initAdapter() {
        mImageAdapter = new ImageAdapter(this, this);
        mBinding.rvImages.setLayoutManager(new GridLayoutManager(this, 3));
        mBinding.rvImages.setAdapter(mImageAdapter);
    }

    private void initDefaultImageLists() {
        List<Image> defaultLists = new ArrayList<>();
        defaultLists.add(new Image(null, null, Image.TYPE_DEFAULT, false, false));
        defaultLists.add(new Image(null, null, Image.TYPE_DEFAULT, false, false));
        defaultLists.add(new Image(null, null, Image.TYPE_DEFAULT, false, false));
        defaultLists.add(new Image(UPLOAD_ITEM_ID, null, Image.TYPE_UPLOAD, false, false));
        mImageLists = new ArrayList<>(defaultLists);
        mBinding.rvImages.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        showDeleteButton(false);
        mBinding.viewDownload.setEnabled(true);
        mBinding.viewDelete.setEnabled(false);
        sIsSelectedBtn = false;
        mBinding.ivSelect.setSelected(false);
        mImageAdapter.submitList(null, () -> mImageAdapter.submitList(new ArrayList<>(defaultLists)));
    }

    private void ivImageListeners() {
        mBinding.ivSelect.setOnClickListener(v -> {
            if (!mBinding.viewDownload.isEnabled()) {
                sIsSelectedBtn = !sIsSelectedBtn;
                if (!sIsSelectedBtn) {
                    // ivSelect is unselected
                    if (sIsOnSubtractIcon) {
                        // subtract icon -> convert to iv selector
                        sIsOnSubtractIcon = false;
                        Glide.with(this)
                                .load(R.drawable.ic_select_selector)
                                .into(mBinding.ivSelect);
                    } else {
                        // not subtract icon -> delete all
                        mBinding.tvDelete.setText(getString(R.string.textview_text_delete_all));
                        showDeleteButton(true);
                    }
                } else {
                    // ivSelect is selected -> delete all
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

    private void viewDeleteListeners() {
        // delete image item
        mBinding.viewDelete.setOnClickListener(v -> {
            // count selected item to delete
            List<Image> selectedLists = new ArrayList<>();
            for (Image item : mImageAdapter.getCurrentList()) {
                if (item.isSelected() && item.isChecked()) {
                    selectedLists.add(item);
                }
            }

            if (selectedLists.isEmpty()) {
                showToastDelete(false);
                return;
            }

            // add loading progress to delete item
            List<Image> newLists = new ArrayList<>();
            for (Image item : mImageAdapter.getCurrentList()) {
                boolean loading = item.isLoading();
                if (item.isSelected() && item.isChecked()) {
                    loading = true;
                }
                newLists.add(new Image(item.getId(), item.getUrl(), item.getCreatedAt(), item.getStatusType(), item.isSelected(), item.isChecked(), loading));
            }
            mImageAdapter.submitList(newLists);

            final int totalRequest = selectedLists.size(); // total selected item to delete
            final int[] successImage = {0}; // count item delete success
            final int[] completedImage = {0}; // count complete item
            for (Image item : selectedLists) {
                RetrofitInstance.getApiInterface(BASE_URL_GET).deleteImageById(item.getId()).enqueue(new Callback<Image>() {
                    @Override
                    public void onResponse(@NonNull Call<Image> call, @NonNull Response<Image> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            successImage[0]++; // success
                        }
                        completedImage[0]++; // complete
                        checkComplete();
                    }

                    @Override
                    public void onFailure(@NonNull Call<Image> call, @NonNull Throwable t) {
                        Log.e(ERROR, Objects.requireNonNull(t.getMessage()));
                        completedImage[0]++; // complete and not success
                        checkComplete();
                    }

                    private void checkComplete() {
                        // if complete total item
                        if (completedImage[0] == totalRequest) {
                            runOnUiThread(() -> {
                                boolean isDeleteSuccess = successImage[0] > 0; // delete success at least 1 item
                                showToastDelete(isDeleteSuccess);
                                if (isDeleteSuccess) {
                                    updateImageLists(); // update list adapter
                                }
                                reloadLoadingStatus(); // hide loading progress
                            });
                        }
                    }
                });
            }
        });
    }

    private void reloadLoadingStatus() {
        List<Image> newLists = new ArrayList<>();
        for (Image item : mImageAdapter.getCurrentList()) {
            newLists.add(new Image(item.getId(), item.getUrl(), item.getCreatedAt(), item.getStatusType(), item.isSelected(), item.isChecked(), false));
        }
        mImageAdapter.submitList(newLists);
    }

    private @NonNull List<Image> getImages() {
        List<Image> newLists = new ArrayList<>();
        Image uploadItem = null;
        for (Image item : mImageAdapter.getCurrentList()) {
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
                    if (mImageLists.isEmpty()) {
                        runOnUiThread(() -> {
                            Toast.makeText(getApplicationContext(), getString(R.string.toast_text_have_no_images), Toast.LENGTH_SHORT).show();
                            initDefaultImageLists();
                        });
                    } else {
                        // sort by createdAt
                        Collections.sort(mImageLists, new Comparator<Image>() {
                            @Override
                            public int compare(Image a, Image b) {
                                return a.getCreatedAt().compareTo(b.getCreatedAt());
                            }
                        });
                        mImageLists.add(new Image(UPLOAD_ITEM_ID, null, Image.TYPE_UPLOAD, false, false));

                        runOnUiThread(() -> {
                            mImageAdapter.submitList(mImageLists);
                            mBinding.viewDownload.setEnabled(false);
                            showToastDownload(true);
                        });
                    }
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
                    // if loading item failed -> add failed item
                    mImageAdapter.submitList(mImageLists);
                    mBinding.tvDownload.setText(getString(R.string.textview_text_try_again));
                    showToastDownload(false);
                });
            }
        });
    }

    private void uploadImage(File file) {
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(
                IMAGE_DATA,
                file.getName(),
                RequestBody.create(MediaType.parse("image/*"), file)
        );

        RetrofitInstance.getApiInterface(BASE_URL_UPLOAD)
                .uploadImage(filePart)
                .enqueue(new Callback<Image>() {
                    @Override
                    public void onResponse(@NonNull Call<Image> call, @NonNull Response<Image> response) {
                        if (response.isSuccessful() && response.body() != null) {

                            runOnUiThread(() -> {
                                updateImageLists();
                                showToastUpload(true);
                                mBinding.viewDownload.setEnabled(false);
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Image> call, @NonNull Throwable t) {
                        Log.e(DEBUG, Objects.requireNonNull(t.getMessage()));
                        runOnUiThread(() -> showToastUpload(false));
                    }
                });
    }

    private void updateImageLists() {
        RetrofitInstance.getApiInterface(BASE_URL_GET).getAllImages().enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(@NonNull Call<List<Image>> call, @NonNull Response<List<Image>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isEmpty()) {
                        runOnUiThread(() -> {
                            initDefaultImageLists();
                            Glide.with(getApplicationContext())
                                    .load(R.drawable.ic_select_selector)
                                    .into(mBinding.ivSelect);
                        });
                    } else {
                        List<Image> newLists = new ArrayList<>();
                        for (Image item : response.body()) {
                            newLists.add(new Image(item.getId(), item.getUrl(), item.getCreatedAt(), item.getStatusType(), item.isSelected(), item.isChecked()));
                        }

                        Collections.sort(newLists, new Comparator<Image>() {
                            @Override
                            public int compare(Image a, Image b) {
                                return a.getCreatedAt().compareTo(b.getCreatedAt());
                            }
                        });
                        newLists.add(new Image(UPLOAD_ITEM_ID, null, Image.TYPE_UPLOAD, false, false));
                        mImageLists = new ArrayList<>(newLists);

                        runOnUiThread(() -> mImageAdapter.submitList(mImageLists));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Image>> call, @NonNull Throwable t) {
                Log.e(ERROR, Objects.requireNonNull(t.getMessage()));
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
                            Bitmap imageBitmap = (Bitmap) extras.get(DATA);
                            try {
                                File f = new File(this.getCacheDir(), IMAGES);
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
        File file = new File(context.getCacheDir(), NEW_FILE);
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
             OutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            if (inputStream != null) {
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
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

        dialogPickImageBinding.tvCamera.setOnClickListener(v -> {
            mDialog.dismiss();
            openCameraActivityForResult();
        });
        dialogPickImageBinding.tvGallery.setOnClickListener(v -> {
            mDialog.dismiss();
            openPhotoPickerActivityForResult();
        });
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.show();
    }

    private void showToastDownload(boolean status) {
        Toast toast = new Toast(this);
        View view = View.inflate(this, status ? R.layout.toast_download_success : R.layout.toast_download_failed, null);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, Y_OFFSET);
        toast.show();
    }

    private void showToastUpload(boolean status) {
        Toast toast = new Toast(this);
        View view = View.inflate(this, status ? R.layout.toast_upload_success : R.layout.toast_upload_failed, null);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, Y_OFFSET);
        toast.show();
    }

    private void showToastDelete(boolean status) {
        Toast toast = new Toast(this);
        View view = View.inflate(this, status ? R.layout.toast_delete_success : R.layout.toast_delete_failed, null);
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
        mBinding.viewDelete.setEnabled(true);
    }

    private void showDeleteButton(boolean condition) {
        mBinding.viewDelete.setVisibility(condition ? View.VISIBLE : View.GONE);
        mBinding.viewDownload.setVisibility(condition ? View.GONE : View.VISIBLE);
        mBinding.ivDownloadIcon.setVisibility(condition ? View.GONE : View.VISIBLE);
        mBinding.tvDownload.setVisibility(condition ? View.GONE : View.VISIBLE);
    }
}
