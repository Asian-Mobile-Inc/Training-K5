package com.example.asian.issue5.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asian.R;
import com.example.asian.issue5.model.DrawText;

import java.util.ArrayList;
import java.util.List;

public class DrawTextAdapter extends ListAdapter<DrawText, DrawTextAdapter.ViewHolder> {
    private Context mContext;

    public DrawTextAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }
    public static final DiffUtil.ItemCallback<DrawText> DIFF_CALLBACK = new DiffUtil.ItemCallback<DrawText>() {
        @Override
        public boolean areItemsTheSame(@NonNull DrawText oldDrawText, @NonNull DrawText newDrawText) {
            return oldDrawText.getFont().equals(newDrawText.getFont());
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull DrawText oldDrawText, @NonNull DrawText newDrawText) {
            return oldDrawText.equals(newDrawText);
        }
    };

    @NonNull
    @Override
    public DrawTextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_draw_text, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrawTextAdapter.ViewHolder holder, int position) {
        DrawText drawText = getItem(position);
        holder.tvTitle.setSelected(drawText.getSelected());
        holder.tvTitle.setTextColor(drawText.getSelected() ? ContextCompat.getColor(mContext, R.color.black) : ContextCompat.getColor(mContext, R.color.gray_9B9B9B));
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), drawText.getFont());
        holder.tvTitle.setTypeface(typeface);
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitList(updateDrawText(holder.getAdapterPosition()));
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ViewHolder(@NonNull View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tvTitle);
        }
    }

    private List<DrawText> updateDrawText(int position) {
        List<DrawText> updateList = new ArrayList<>();
        List<DrawText> currentList = getCurrentList();
        for (int i = 0; i < currentList.size(); i++) {
            DrawText oldeDrawText = currentList.get(i);
            if (i == position || oldeDrawText.getSelected()) {
                DrawText newDrawText = new DrawText(oldeDrawText.getFont(), !oldeDrawText.getSelected());
                updateList.add(newDrawText);
            } else {
                updateList.add(oldeDrawText);
            }
        }
        return updateList;
    }
}
