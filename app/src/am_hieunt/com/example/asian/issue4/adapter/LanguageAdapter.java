package com.example.asian.issue4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asian.R;
import com.example.asian.issue4.model.Language;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {
    private List<Language> mLanguages;
    private int mSelectedPosotion = -1;

    public LanguageAdapter(List<Language> languages) {
        this.mLanguages = languages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_language, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Language language = mLanguages.get(position);
        holder.sivFlag.setImageResource(language.getFlagId());
        holder.tvName.setText(language.getName());
        holder.rbSelect.setChecked(mSelectedPosotion == position);

        holder.rbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPosotion = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLanguages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ShapeableImageView sivFlag;
        public TextView tvName;
        public RadioButton rbSelect;
        public ViewHolder(View view) {
            super(view);
            sivFlag = view.findViewById(R.id.sivFlag);
            tvName = view.findViewById(R.id.tvName);
            rbSelect = view.findViewById(R.id.rbSelect);
        }
    }
}
