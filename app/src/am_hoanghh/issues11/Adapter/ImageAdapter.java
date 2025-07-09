package issues11.Adapter;

import android.content.Context;
import android.util.Log;
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
    private static final int IMAGE_SIZE_WIDTH = 103;
    private static final int IMAGE_SIZE_HEIGHT = 105;
    private final OnImageListener listener;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Image item = getItem(position);

        if (item.getStatusType() == Image.TYPE_IMAGE) {
            Glide.with(mContext)
                    .load(item.getUrl())
                    .transform(new CenterCrop(), new RoundedCorners(pxToDp(8, mContext)))
                    .override(pxToDp(IMAGE_SIZE_WIDTH, mContext), pxToDp(IMAGE_SIZE_HEIGHT, mContext))
                    .into(holder.mBinding.ivImage);
            if (item.isSelected()) {
                holder.mBinding.ivRemove.setVisibility(View.VISIBLE);
                holder.mBinding.ivDelete.setVisibility(View.GONE);
                holder.mBinding.ivRemove.setSelected(item.isChecked());
                holder.itemView.setOnClickListener(v -> {
                    boolean isSubtract = true;
                    List<Image> oldLists = new ArrayList<>(getCurrentList());
                    List<Image> newLists = new ArrayList<>();
                    for (int i = 0; i < oldLists.size() - 1; i++) {
                        Image image = oldLists.get(i);
                        isSubtract = image.isChecked();
                        if (i == holder.getAdapterPosition()) {
                            newLists.add(new Image(image.getId(), image.getUrl(), image.getStatusType(), image.isSelected(), !image.isChecked()));
                        } else {
                            newLists.add(image);
                        }
                    }
                    newLists.add(oldLists.get(oldLists.size() - 1));
                    submitList(newLists);
                    if (isSubtract) {
                        listener.onSubtractIcon();
                    }
                });
            } else {
                holder.mBinding.ivRemove.setVisibility(View.GONE);
                holder.mBinding.ivDelete.setVisibility(View.VISIBLE);
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
                    Objects.equals(oldItem.isChecked(), newItem.isChecked());
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
