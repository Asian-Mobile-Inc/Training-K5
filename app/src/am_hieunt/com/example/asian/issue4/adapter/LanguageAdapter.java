package com.example.asian.issue4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asian.R;
import com.example.asian.issue4.model.Language;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {
    private List<Language> mLanguages;
    private int mSelectedPosotion = -1;

    public interface OnLanguageSelectedListen {
        void onLanguageSelected(Language language);
    }

    private OnLanguageSelectedListen mListener;

    public LanguageAdapter(List<Language> languages, OnLanguageSelectedListen listener) {
        this.mLanguages = languages;
        this.mListener = listener;
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
                if (mListener != null) {
                    mListener.onLanguageSelected(language);
                }
                notifyDataSetChanged();
            }
        });

        holder.cvLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedPosotion = holder.getAdapterPosition();
                if (mListener != null) {
                    mListener.onLanguageSelected(language);
                }
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
        public CardView cvLanguage;
        public ViewHolder(View view) {
            super(view);
            sivFlag = view.findViewById(R.id.sivFlag);
            tvName = view.findViewById(R.id.tvName);
            rbSelect = view.findViewById(R.id.rbSelect);
            cvLanguage = view.findViewById(R.id.cvLanguage);
        }
    }
}
