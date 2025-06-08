package issues4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.asian.R;

import java.util.ArrayList;
import java.util.List;

public class LanguageAdapter extends RecyclerView.Adapter<LanguageAdapter.ViewHolder> {
    private List<LanguageItem> languageLists;
    private int selectedPosition = 0;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
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

    /**
     * Initialize the dataset of the Adapter
     */
    public LanguageAdapter(List<LanguageItem> languageLists) {
        if (languageLists == null) {
            languageLists = new ArrayList<>();
        }

        this.languageLists = languageLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_rv_language, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        LanguageItem languageItem = languageLists.get(viewHolder.getAdapterPosition());

        Glide.with(viewHolder.itemView.getContext())
                .load(languageItem.getIcResId())
                .transform(new CenterCrop(), new RoundedCorners(9999))
                .into(viewHolder.getImageView());

        viewHolder.getTextView().setText(languageItem.getLanguageName());
        viewHolder.getRadioButton().setChecked(viewHolder.getAdapterPosition() == selectedPosition);

        viewHolder.getRadioButton().setOnClickListener(view -> {
            int previousPosition = selectedPosition;
            selectedPosition = viewHolder.getAdapterPosition();

            notifyItemChanged(previousPosition);
            notifyItemChanged(selectedPosition);
        });
    }

    @Override
    public int getItemCount() {
        return languageLists.size();
    }

    public LanguageItem getSelectedItem() {
        if (selectedPosition >= 0 && selectedPosition < languageLists.size()) {
            return languageLists.get(selectedPosition);
        }

        return null;
    }
}
