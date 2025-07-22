package issues11.Adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.asian.R;
import com.example.asian.databinding.ItemRvImageDownloadBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import issues11.Model.Image;

public class ImageAdapter extends ListAdapter<Image, ImageAdapter.ViewHolder> {
    private final Context mContext;
    private static final int IMAGE_SIZE_WIDTH = 104;
    private static final int IMAGE_SIZE_HEIGHT = 104;
    private final OnImageListener listener;
    private static final String UPLOAD_ITEM_ID = "UPLOAD_ITEM_ID";

    public ImageAdapter(Context context, OnImageListener listener) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvImageDownloadBinding binding = ItemRvImageDownloadBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ImageAdapter.ViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        Image item = getItem(position);
        return item.getStatusType();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image item = getItem(position);

        if (item.getStatusType() == Image.TYPE_IMAGE) {
            Glide.with(mContext)
                    .load(item.getUrl())
                    .transform(new CenterCrop(), new RoundedCorners(pxToDp(8, mContext)))
                    .override(pxToDp(IMAGE_SIZE_WIDTH, mContext), pxToDp(IMAGE_SIZE_HEIGHT, mContext))
                    .into(holder.mBinding.ivImage);
            if (item.isLoading()) {
                // Deleting
                holder.mBinding.progressBar.setVisibility(View.VISIBLE);
                holder.mBinding.viewGrayOverlay.setVisibility(View.VISIBLE);
            } else {
                holder.mBinding.progressBar.setVisibility(View.GONE);
                holder.mBinding.viewGrayOverlay.setVisibility(View.GONE);
            }
            if (item.isSelected()) {
                // Selecting delete item
                holder.mBinding.ivRemove.setVisibility(View.VISIBLE);
                holder.mBinding.ivDelete.setVisibility(View.GONE);
                holder.mBinding.ivRemove.setSelected(item.isChecked());
                holder.itemView.setOnClickListener(v -> {
                    int countCheckedItem = 0;
                    List<Image> oldLists = new ArrayList<>(getCurrentList());
                    List<Image> newLists = new ArrayList<>();
                    Image uploadImage = null;
                    for (int i = 0; i < oldLists.size(); i++) {
                        Image image = oldLists.get(i);
                        if (UPLOAD_ITEM_ID.equals(image.getId())) {
                            uploadImage = image;
                        } else {
                            boolean newCheckedValue = !image.isChecked();
                            if (i == holder.getAdapterPosition()) {
                                newLists.add(new Image(image.getId(), image.getUrl(), image.getStatusType(), image.isSelected(), newCheckedValue));
                                if (newCheckedValue) {
                                    ++countCheckedItem;
                                }
                            } else {
                                newLists.add(image);
                                if (image.isChecked()) {
                                    ++countCheckedItem;
                                }
                            }
                        }
                    }
                    if (uploadImage != null) {
                        newLists.add(uploadImage);
                    }
                    int finalCountCheckedItem = countCheckedItem;
                    submitList(newLists, () -> listener.onSubtractIcon(finalCountCheckedItem));
                });
            } else {
                // Enable select delete item
                holder.mBinding.ivRemove.setVisibility(View.GONE);
                holder.mBinding.ivDelete.setVisibility(View.VISIBLE);
                holder.mBinding.ivDelete.setOnClickListener(v -> {
                    List<Image> oldLists = new ArrayList<>(getCurrentList());
                    List<Image> newLists = new ArrayList<>();
                    Image uploadImage = null;
                    for (int i = 0; i < oldLists.size(); i++) {
                        Image image = oldLists.get(i);
                        if (UPLOAD_ITEM_ID.equals(image.getId())) {
                            uploadImage = image;
                        } else {
                            if (i == holder.getAdapterPosition()) {
                                newLists.add(new Image(image.getId(), image.getUrl(), image.getStatusType(), true, true));
                            } else {
                                newLists.add(new Image(image.getId(), image.getUrl(), image.getStatusType(), true, false));
                            }
                        }
                    }
                    if (uploadImage != null) {
                        newLists.add(uploadImage);
                    }
                    submitList(newLists, () -> listener.onSubtractIcon(1));
                });
            }
        }

        if (item.getStatusType() == Image.TYPE_UPLOAD) {
            holder.mBinding.ivImage.setVisibility(View.GONE);
            holder.mBinding.ivDelete.setVisibility(View.GONE);
            holder.mBinding.tvUpload.setVisibility(View.VISIBLE);
            holder.mBinding.tvUpload.setOnClickListener(v -> {
                listener.onUploadImage();
            });
        }

        if (item.getStatusType() == Image.TYPE_FAILED) {
            holder.mBinding.getRoot().setBackgroundResource(R.drawable.bg_image_download_item_failed);
            Glide.with(mContext)
                    .load(R.drawable.ic_download_item_failed)
                    .override(pxToDp(IMAGE_SIZE_WIDTH, mContext), pxToDp(IMAGE_SIZE_WIDTH, mContext))
                    .into(holder.mBinding.ivImage);
        }
    }

    public static final DiffUtil.ItemCallback<Image> DIFF_CALLBACK = new DiffUtil.ItemCallback<Image>() {
        @Override
        public boolean areItemsTheSame(@NonNull Image oldItem, @NonNull Image newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Image oldItem, @NonNull Image newItem) {
            return Objects.equals(oldItem.getUrl(), newItem.getUrl()) &&
                    Objects.equals(oldItem.isSelected(), newItem.isSelected()) &&
                    Objects.equals(oldItem.getStatusType(), newItem.getStatusType()) &&
                    Objects.equals(oldItem.isChecked(), newItem.isChecked()) &&
                    Objects.equals(oldItem.isLoading(), newItem.isLoading());
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRvImageDownloadBinding mBinding;

        public ViewHolder(ItemRvImageDownloadBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }

    public static int pxToDp(int px, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
    }
}
