package com.example.asian.issue4.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.issue4.model.Language;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class LanguageAdapter extends ListAdapter<Language, LanguageAdapter.ViewHolder> {
    private Context mContext;

    public interface OnLanguageSelectedListen {
        void onLanguageSelected(Language language);
    }

    private OnLanguageSelectedListen mListener;

    public LanguageAdapter(Context context, OnLanguageSelectedListen listener) {
        super(DIFF_CALLBACK);
        this.mListener = listener;
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<Language> DIFF_CALLBACK = new DiffUtil.ItemCallback<Language>() {
        @Override
        public boolean areItemsTheSame(@NonNull Language oldLang, @NonNull Language newLang) {
            return oldLang.getCode().equals(newLang.getCode());
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Language oldLang, @NonNull Language newLang) {
            return oldLang.equals(newLang);
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_language, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Language language = getItem(position);
        Glide.with(mContext)
                        .load(language.getFlagId())
                        .into(holder.sivFlag);
        holder.tvName.setText(language.getName());
        holder.rbSelect.setChecked(language.getSelected());
        holder.clLanguage.setSelected(language.getSelected());

        holder.rbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onLanguageSelected(language);
                }
                holder.clLanguage.setSelected(true);
                submitList(updateAdapter(holder.getAdapterPosition()));
            }
        });

        holder.clLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onLanguageSelected(language);
                }
                holder.clLanguage.setSelected(true);
                submitList(updateAdapter(holder.getAdapterPosition()));
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ShapeableImageView sivFlag;
        public TextView tvName;
        public RadioButton rbSelect;
        public ConstraintLayout clLanguage;
        public ViewHolder(View view) {
            super(view);
            sivFlag = view.findViewById(R.id.sivFlag);
            tvName = view.findViewById(R.id.tvName);
            rbSelect = view.findViewById(R.id.rbSelect);
            clLanguage = view.findViewById(R.id.clLanguage);
        }
    }

    private List<Language> updateAdapter(int selectedPosition) {
        List<Language> updatedList = new ArrayList<>();
        List<Language> currentList = getCurrentList();
        for (int i = 0; i < currentList.size(); i++) {
            Language oldItem = currentList.get(i);
            boolean isSelected = (i == selectedPosition);
            if (oldItem.getSelected() != isSelected) {
                Language newItem = new Language(oldItem.getFlagId(), oldItem.getCode(), oldItem.getName(), isSelected);
                updatedList.add(newItem);
            } else {
                updatedList.add(oldItem);
            }
        }
        return updatedList;
    }

}
