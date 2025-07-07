package issues11;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresExtension;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ActivityIssues11Binding;
import com.example.asian.databinding.DialogConfirmDeleteBinding;
import com.example.asian.databinding.DialogPickImageBinding;

import java.util.List;
import java.util.Objects;

import issues11.Model.Image;
import issues11.Retrofit.RetrofitInstance;
import issues11.Util.ImagePicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Issues11Activity extends AppCompatActivity {
    private ActivityIssues11Binding mBinding;
    private Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityIssues11Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initToolbar();
        initRetrofit();
        initDialog();

        mBinding.viewDownload.setOnClickListener(v -> {
            initDialogListeners();
        });
    }

    private void initToolbar() {
        mBinding.toolbar.setTitle("");
        setSupportActionBar(mBinding.toolbar);
    }

    private void initRetrofit() {
        RetrofitInstance.getApiInterface().getAllImages().enqueue(new Callback<List<Image>>() {
            @Override
            public void onResponse(@NonNull Call<List<Image>> call, @NonNull Response<List<Image>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Image> imageLists = response.body();
                    for (Image image : imageLists) {
                        Log.d("debug", image.getId() + " " + image.getUrl());
                    }
                } else {
                    Log.d("debug", String.valueOf(response.isSuccessful()));
                    Log.d("debug", String.valueOf(response.body()));
                    Log.d("debug", "Response failed " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Image>> call, @NonNull Throwable t) {
                Log.e("ERROR", Objects.requireNonNull(t.getMessage()));
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
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData() != null) {
                            Bundle extras = result.getData().getExtras();
                            if (extras != null) {
                                Bitmap imageBitmap = (Bitmap) extras.get("data");
                                Glide.with(getApplicationContext())
                                        .load(imageBitmap)
                                        .override(200, 200)
                                        .into(mBinding.test);
                            }
                        }
                    }
                }
            });

    private final ActivityResultLauncher<Intent> mPhotoPickerCameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent selectedImage = result.getData();
                        if (selectedImage != null) {
                            Glide.with(getApplicationContext())
                                    .load(selectedImage.getData())
                                    .override(200, 200)
                                    .into(mBinding.test);
                        }
                    }
                }
            });

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
        mDialog.show();
    }
}
