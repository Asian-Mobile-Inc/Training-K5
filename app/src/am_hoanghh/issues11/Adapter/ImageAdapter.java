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
import com.example.asian.databinding.ItemRvImageUploadBinding;

import java.util.Objects;

import issues11.Model.Image;

public class ImageAdapter extends ListAdapter<Image, ImageAdapter.ViewHolder> {
    private final Context mContext;
    private static final int IMAGE_SIZE = 103;
    private final OnUploadImageListener listener;

    public ImageAdapter(Context context, OnUploadImageListener listener) {
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
        Log.d("debug", position + " " + item.getStatusType());

        if (item.getStatusType() == Image.TYPE_IMAGE) {
            Glide.with(mContext)
                    .load(item.getUrl())
                    .transform(new CenterCrop(), new RoundedCorners(pxToDp(8, mContext)))
                    .override(pxToDp(IMAGE_SIZE, mContext), pxToDp(IMAGE_SIZE, mContext))
                    .into(holder.mBinding.ivImage);
            holder.mBinding.ivDelete.setVisibility(ViewGroup.VISIBLE);
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
            Log.d("debug", String.valueOf(item.getStatusType()));
            holder.mBinding.getRoot().setBackgroundResource(R.drawable.bg_image_download_item_failed);
            Glide.with(mContext)
                    .load(R.drawable.ic_download_item_failed)
                    .override(pxToDp(IMAGE_SIZE, mContext), pxToDp(IMAGE_SIZE, mContext))
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
                    Objects.equals(oldItem.getStatusType(), newItem.getStatusType());
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
