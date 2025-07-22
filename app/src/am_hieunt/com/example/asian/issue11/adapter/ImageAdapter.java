package com.example.asian.issue11.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.issue11.api.ApiService;
import com.example.asian.issue11.model.Image;
import com.example.asian.issue11.view.MainActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageAdapter extends ListAdapter<Image, ImageAdapter.ViewHolder> {
    private final Context mContext;
    private final OnSelectedImage mListener;
    private int mCountDownload = 0;
    private final String DOWNLOAD_SUCCESS = "SUCCESS";
    private final String KEY_DOWNLOAD = "DOWNLOAD";
    private final String KEY_DELETE = "DELETE";
    private final String KEY_FINISH = "FINISH";
    private Boolean mCheck = false;
    private final ActivityResultLauncher<PickVisualMediaRequest> mPickMedia;
    private static final String ACCESS_TOKEN = "Bearer 37NgYdmLpLPbBFla_63tC23jBk9_iJaIxXdm4l9KX68";

    public interface OnSelectedImage {
        void onSelectedListener(int selected);
        void onDownloadState(Boolean state);
        void onDeleteState(Boolean state);
    }

    public ImageAdapter(Context context, ActivityResultLauncher<PickVisualMediaRequest> pickMultipleMedia, OnSelectedImage listener) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.mListener = listener;
        this.mPickMedia = pickMultipleMedia;
    }

    public static final DiffUtil.ItemCallback<Image> DIFF_CALLBACK = new DiffUtil.ItemCallback<Image>() {
        @Override
        public boolean areItemsTheSame(@NonNull Image oldItem, @NonNull Image newItem) {
            return oldItem.getImage_id().equals(newItem.getImage_id());
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Image oldItem, @NonNull Image newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
        Image image = getItem(position);
        holder.mImage = image;
        holder.mVImage.setVisibility(View.VISIBLE);
        holder.mSivImage.setVisibility(View.VISIBLE);
        holder.mIvSelect.setVisibility((mCheck && !image.getUrl().isEmpty()) ? View.VISIBLE : View.GONE);
        holder.mTvProgress.setVisibility(View.GONE);
        holder.mPbProgress.setVisibility(View.GONE);
        holder.mPbDelete.setVisibility(View.GONE);
        holder.mVImage.setBackground(mContext.getDrawable(image.getBackground()));
        Glide.with(mContext)
                .load(image.getBitmap())
                .fitCenter()
                .into(holder.mSivImage);
        Glide.with(mContext)
                .load(image.getSelect())
                .into(holder.mIvSelect);
        holder.mVImage.setOnClickListener(v -> {
            if (image.getUrl().isEmpty()) {
                mPickMedia.launch(new PickVisualMediaRequest.Builder()
                        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                        .build());
            }
        });
        holder.mIvSelect.setOnClickListener(v -> {
            if (image.getSelect() == R.drawable.ic_cancel) {
                image.setStatus(KEY_DELETE);
                deleteImage(holder, image);
            } else {
                image.setSelect((image.getSelect() == R.drawable.ic_selected) ? R.drawable.ic_un_select : R.drawable.ic_selected);
                updateImages(holder.getAdapterPosition());
                if (mListener != null) {
                    mListener.onSelectedListener(image.getSelect());
                }
            }
        });
        if (image.getStatus().equals(KEY_DELETE)) {
            deleteImage(holder, image);
        }
        if (image.getStatus().equals(KEY_DOWNLOAD) && !image.getUrl().isEmpty()) {
            downloadImage(holder, image);
        }
    }

    public void setSelect(int select) {
        List<Image> oldImages = getCurrentList();
        List<Image> newImages = new ArrayList<>();
        for (int  i = 0; i < oldImages.size(); i++) {
            if (!oldImages.get(i).getUrl().isEmpty()) {
                Image image = new Image();
                image.setImage_id(oldImages.get(i).getImage_id());
                image.setUrl(oldImages.get(i).getUrl());
                image.setSelect(oldImages.get(i).getUrl().isEmpty() ? R.drawable.ic_cancel : select);
                image.setBitmap(oldImages.get(i).getBitmap());
                image.setStatus(oldImages.get(i).getStatus());
                newImages.add(image);
            } else {
                newImages.add(oldImages.get(i));
            }
        }
        submitList(newImages);
    }

    public void setDownload() {
        mCountDownload = 0;
        List<Image> oldImages = getCurrentList();
        List<Image> newImages = new ArrayList<>();
        for (int  i = 0; i < oldImages.size(); i++) {
            if (!oldImages.get(i).getUrl().isEmpty()) {
                Image image = new Image();
                image.setImage_id(oldImages.get(i).getImage_id());
                image.setUrl(oldImages.get(i).getUrl());
                image.setSelect(oldImages.get(i).getSelect());
                image.setBitmap(oldImages.get(i).getBitmap());
                image.setStatus(KEY_DOWNLOAD);
                newImages.add(image);
            } else {
                newImages.add(oldImages.get(i));
            }
        }
        submitList(newImages);
    }

    public void setDelete() {
        List<Image> oldImages = getCurrentList();
        List<Image> newImages = new ArrayList<>();
        for (int  i = 0; i < oldImages.size(); i++) {
            if (!oldImages.get(i).getUrl().isEmpty() && oldImages.get(i).getSelect() == R.drawable.ic_selected) {
                Image image = new Image();
                image.setImage_id(oldImages.get(i).getImage_id());
                image.setUrl(oldImages.get(i).getUrl());
                image.setSelect(oldImages.get(i).getSelect());
                image.setBitmap(oldImages.get(i).getBitmap());
                image.setStatus(KEY_DELETE);
                newImages.add(image);
            } else {
                newImages.add(oldImages.get(i));
            }
        }
        submitList(newImages);
    }

    private void downloadImage(ImageAdapter.ViewHolder holder, Image image) {
        image.setStatus("");
        DownloadTask downloadTask = new DownloadTask(mContext, holder, new DownloadTask.OnDownloadDone() {
            @Override
            public void onDownloadListener(String text) {
                if (text.equals(DOWNLOAD_SUCCESS)) {
                    mCountDownload++;
                } else {
                    toastFail(((Activity) mContext).getString(R.string.downloaded_failed));
                    mCountDownload = 0;
                    holder.itemView.setHasTransientState(false);
                    if (mListener != null) {
                        mListener.onDownloadState(false);
                    }
                }
                if (mCountDownload % (getCurrentList().size()-1) == 0 && mCountDownload != 0) {
                    toastSuccess(((Activity) mContext).getString(R.string.downloaded_successfully));
                    mCheck = true;
                    if (mListener != null) {
                        mListener.onDownloadState(true);
                    }
                    holder.itemView.setHasTransientState(false);
                }
            }
        });
        downloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void deleteImage(ImageAdapter.ViewHolder holder, Image image) {
        holder.mPbDelete.setVisibility(View.VISIBLE);
        holder.mIvSelect.setVisibility(View.GONE);
        ApiService.apiService.deleteImage(ACCESS_TOKEN, image.getImage_id()).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                        image.setStatus(KEY_FINISH);
                        if (getQuantityImageDelete() == 0) {
                            toastSuccess(((Activity) mContext).getString(R.string.delete_successfully));
                            updateSelectImageDelete();
                            if (mListener != null) {
                                mListener.onDeleteState(true);
                            }
                        } else {
                            removeImage();
                        }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                holder.mPbDelete.setVisibility(View.GONE);
                holder.mIvSelect.setVisibility(View.VISIBLE);
                image.setStatus("");
                toastFail(((Activity) mContext).getString(R.string.delete_failed));
                mListener.onDeleteState(false);
            }
        });
    }

    public void addImage(Image image) {
        List<Image> oldImages = getCurrentList();
        List<Image> newImages = new ArrayList<>();
        for (int i = 0; i < oldImages.size(); i++) {
            if (i == oldImages.size() - 1) {
                newImages.add(image);
            }
            newImages.add(oldImages.get(i));
        }
        submitList(newImages);
    }

    private void removeImage() {
        List<Image> oldImages = getCurrentList();
        List<Image> newImages = new ArrayList<>();
        for (int i = 0; i < oldImages.size(); i++) {
            if (!oldImages.get(i).getStatus().equals(KEY_FINISH)) {
                newImages.add(oldImages.get(i));
            }
        }
        submitList(newImages);
    }

    private int getQuantityImageDelete() {
        int d = 0;
        for (int i = 0; i < getCurrentList().size(); i++) {
            if (getCurrentList().get(i).getStatus().equals(KEY_DELETE)) {
                d++;
            }
        }
        return d;
    }

    private void updateSelectImageDelete() {
        List<Image> oldImages = getCurrentList();
        List<Image> newImages = new ArrayList<>();
        for (int  i = 0; i < oldImages.size(); i++) {
            if (!oldImages.get(i).getStatus().equals(KEY_FINISH)) {
                if (oldImages.get(i).getSelect() != R.drawable.ic_cancel) {
                    Image image = new Image();
                    image.setImage_id(oldImages.get(i).getImage_id());
                    image.setUrl(oldImages.get(i).getUrl());
                    image.setSelect(R.drawable.ic_cancel);
                    image.setBitmap(oldImages.get(i).getBitmap());
                    image.setStatus(oldImages.get(i).getStatus());
                    newImages.add(image);
                } else {
                    newImages.add(oldImages.get(i));
                }
            }
        }
        submitList(newImages);
    }

    private void updateImages(int position) {
        List<Image> oldImages = getCurrentList();
        List<Image> newImages = new ArrayList<>();
        for (int  i = 0; i < oldImages.size(); i++) {
            if (i == position) {
                Image image = new Image();
                image.setImage_id(oldImages.get(i).getImage_id());
                image.setUrl(oldImages.get(i).getUrl());
                image.setSelect(oldImages.get(i).getSelect());
                image.setBitmap(oldImages.get(i).getBitmap());
                image.setStatus(oldImages.get(i).getStatus());
                newImages.add(image);
            } else newImages.add(oldImages.get(i));
        }
        submitList(newImages);
    }

    private void toastSuccess(String s) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View layout = inflater.inflate(R.layout.item_toast, ((Activity) mContext).findViewById(R.id.toastLayout));
        layout.setBackgroundResource(R.drawable.my_toast_success);
        ImageView image = layout.findViewById(R.id.ivToast);
        image.setImageResource(R.drawable.img_success);
        TextView text = layout.findViewById(R.id.tvToast);
        text.setText(s);
        Toast toast = new Toast(((Activity) mContext).getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void toastFail(String s) {
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View layout = inflater.inflate(R.layout.item_toast, ((Activity) mContext).findViewById(R.id.toastLayout));
        layout.setBackgroundResource(R.drawable.my_toast_fail);
        ImageView image = layout.findViewById(R.id.ivToast);
        image.setImageResource(R.drawable.img_fail);
        TextView text = layout.findViewById(R.id.tvToast);
        text.setText(s);
        Toast toast = new Toast(((Activity) mContext).getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ShapeableImageView mSivImage;
        public ImageView mIvSelect;
        public TextView mTvProgress;
        public ProgressBar mPbProgress, mPbDelete;
        public View mVImage;
        public Image mImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSivImage = itemView.findViewById(R.id.sivImage);
            mIvSelect = itemView.findViewById(R.id.ivSelect);
            mTvProgress = itemView.findViewById(R.id.tvProgress);
            mPbProgress = itemView.findViewById(R.id.pbProgress);
            mVImage = itemView.findViewById(R.id.vImage);
            mPbDelete = itemView.findViewById(R.id.pbDelete);
        }
    }
}
