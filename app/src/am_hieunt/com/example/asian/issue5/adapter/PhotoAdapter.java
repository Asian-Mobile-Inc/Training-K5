package com.example.asian.issue5.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.issue5.model.Photo;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends ListAdapter<Photo, PhotoAdapter.ViewHolder> {
    private Context mContext;
    public PhotoAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    @NonNull
    @Override
    public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_home, parent, false);
        return new ViewHolder(view);
    }

    public static final DiffUtil.ItemCallback<Photo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Photo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Photo oldPhoto, @NonNull Photo newPhoto) {
            return oldPhoto.getPhotoId() == newPhoto.getPhotoId();
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Photo oldPhoto, @NonNull Photo newPhoto) {
            return oldPhoto.equals(newPhoto);
        }
    };

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.ViewHolder holder, int position) {
        Photo photo = getItem(position);
        Glide.with(mContext)
                .load(photo.getPhotoId())
                .skipMemoryCache(true)
                .into(holder.sivPhoto);
        holder.tvName.setText(photo.getName());
        holder.tvPen.setText(String.valueOf(photo.getPen()));
        if (photo.getFavorite()) {
            Glide.with(mContext)
                    .load(R.drawable.ic_favourite_selected)
                    .skipMemoryCache(true)
                    .into(holder.ivFavorite);
        } else {
            Glide.with(mContext)
                    .load(R.drawable.ic_favourite)
                    .skipMemoryCache(true)
                    .into(holder.ivFavorite);
        }
        holder.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitList(updatePhoto(holder.getAdapterPosition()));
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ShapeableImageView sivPhoto;
        public TextView tvName, tvPen;
        public ImageView ivFavorite;

        public ViewHolder(@NonNull View view) {
            super(view);
            sivPhoto = view.findViewById(R.id.sivPhoto);
            tvName = view.findViewById(R.id.tvName);
            tvPen = view.findViewById(R.id.tvPen);
            ivFavorite = view.findViewById(R.id.ivFavorite);
        }
    }

    private List<Photo> updatePhoto(int position) {
        List<Photo> updateList = new ArrayList<>();
        List<Photo> currentList = getCurrentList();
        for (int i = 0; i < currentList.size(); i++) {
            Photo oldPhoto = currentList.get(i);
            if (i == position) {
                Photo newPhoto = new Photo(oldPhoto.getPhotoId(), oldPhoto.getName(), oldPhoto.getPen(), !oldPhoto.getFavorite());
                updateList.add(newPhoto);
            } else updateList.add(oldPhoto);
        }
        return updateList;
    }

}
