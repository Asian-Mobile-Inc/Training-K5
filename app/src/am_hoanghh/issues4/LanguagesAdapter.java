package issues4;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.asian.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LanguagesAdapter extends ListAdapter<LanguageItem, LanguagesAdapter.ViewHolder> {
    private static int selectedPosition = 0;
    private List<LanguageItem> languageLists;

    protected LanguagesAdapter(List<LanguageItem> languageLists) {
        super(DIFF_CALLBACK);
        this.languageLists = languageLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_language, parent, false);

        return new LanguagesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LanguageItem item = getItem(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getIcResId())
                .transform(new CenterCrop(), new RoundedCorners(9999))
                .into(holder.getImageView());

        holder.getTextView().setText(item.getLanguageName());
        holder.getRadioButton().setChecked(item.isSelected());
        holder.itemView.setSelected(holder.getAdapterPosition() == selectedPosition);

        holder.itemView.setOnClickListener(view -> {
            selectedPosition = holder.getAdapterPosition();

            submitList(getNewLanguageList(selectedPosition));
        });

        holder.getRadioButton().setOnClickListener(view -> {
            holder.itemView.performClick();
        });
    }

    public static final DiffUtil.ItemCallback<LanguageItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<LanguageItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull LanguageItem oldItem, @NonNull LanguageItem newItem) {
                    return oldItem.getIcResId() == newItem.getIcResId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull LanguageItem oldItem, @NonNull LanguageItem newItem) {
                    return oldItem.isSelected() == newItem.isSelected();
                }
            };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView textView;
        private final RadioButton radioButton;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.ivFlag);
            textView = (TextView) view.findViewById(R.id.tvItemRecyclerView);
            radioButton = (RadioButton) view.findViewById(R.id.rbLanguage);
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public RadioButton getRadioButton() {
            return radioButton;
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setPosition(int position) {
        selectedPosition = position;
    }

    public ArrayList<LanguageItem> getNewLanguageList(int position) {
        ArrayList<LanguageItem> newLanguageLists = new ArrayList<>();
        for (LanguageItem item : languageLists) {
            newLanguageLists.add(new LanguageItem(item.getIcResId(), item.getLanguageName(), item.getLanguageCode()));
        }

        newLanguageLists.get(position).setSelected(true);

        return newLanguageLists;
    }
}
