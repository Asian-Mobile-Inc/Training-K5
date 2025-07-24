package com.example.asian.issue10.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
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
import com.example.asian.issue10.model.App;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends ListAdapter<App, AppAdapter.ViewHolder> {
    private final Context mContext;
    private final OnAppSelectedListener mListener;
    private final List<App> mApps;

    public interface OnAppSelectedListener {
        void onAppSelected(List<App> apps);
    }

    public AppAdapter(Context context, List<App> apps, OnAppSelectedListener listener) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.mListener = listener;
        this.mApps = new ArrayList<>(apps);
    }

    public static final DiffUtil.ItemCallback<App> DIFF_CALLBACK = new DiffUtil.ItemCallback<App>() {
        @Override
        public boolean areItemsTheSame(@NonNull App oldItem, @NonNull App newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull App oldItem, @NonNull App newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public AppAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        App app = getItem(position);
        Log.d("SSSS", app.getFavorite()+"");
        Glide.with(mContext)
                .load(app.getFavorite() ? R.drawable.ic_favorite_selected : R.drawable.ic_favorite)
                .dontAnimate()
                .into(holder.mIvFavorite);
        Glide.with(mContext)
                .load(app.getIcon())
                .dontAnimate()
                .into(holder.mIvApp);
        holder.mTvName.setText(app.getName());
        holder.mTvHolder.setText(app.getHolder());
        holder.mIvFavorite.setOnClickListener(v -> {
            app.setSelected(true);
            updateApp(app.getId());
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTvName, mTvHolder;
        public ImageView mIvApp, mIvFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvName = itemView.findViewById(R.id.tvName);
            mTvHolder = itemView.findViewById(R.id.tvHolder);
            mIvApp = itemView.findViewById(R.id.ivApp);
            mIvFavorite = itemView.findViewById(R.id.ivFavorite);
        }
    }

    private void updateApp(int id) {
        List<App> newApps = new ArrayList<>();
        List<App> oldApps = getCurrentList();
        for (int i = 0; i < oldApps.size(); i++) {
            App app = oldApps.get(i);
            if (app.getId() == id || app.getSelected()) {
                App a = new App(app.getId(), app.getIcon(), app.getName(), app.getHolder(), !app.getFavorite());
                newApps.add(a);
                mApps.set(app.getId(), a);
            } else {
                newApps.add(app);
                mApps.set(app.getId(), app);
            }
        }
        submitList(newApps);
        if (mListener != null) {
            mListener.onAppSelected(mApps);
        }
    }
}
