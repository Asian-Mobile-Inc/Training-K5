package issues4;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.asian.databinding.ItemRvLanguageBinding;

import java.util.ArrayList;

public class LanguagesAdapter extends ListAdapter<LanguageItem, LanguagesAdapter.ViewHolder> {
    private static int selectedPosition = 0;
    private static final int ROUNDING_RADIUS = 9999;

    protected LanguagesAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LanguagesAdapter.ViewHolder(ItemRvLanguageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LanguageItem item = getItem(position);

        Glide.with(holder.itemView.getContext())
                .load(item.getIcResId())
                .transform(new CenterCrop(), new RoundedCorners(ROUNDING_RADIUS))
                .into(holder.mBinding.ivFlag);

        holder.mBinding.tvItemRecyclerView.setText(item.getLanguageName());
        holder.mBinding.rbLanguage.setChecked(item.isSelected());
        holder.itemView.setSelected(holder.getAdapterPosition() == selectedPosition);

        holder.itemView.setOnClickListener(view -> {
            selectedPosition = holder.getAdapterPosition();

            submitList(getNewLanguageList(selectedPosition));
        });

        holder.mBinding.rbLanguage.setOnClickListener(view -> {
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
        private final ItemRvLanguageBinding mBinding;

        public ViewHolder(ItemRvLanguageBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
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
        for (LanguageItem item : getCurrentList()) {
            newLanguageLists.add(new LanguageItem(item.getIcResId(), item.getLanguageName(), item.getLanguageCode()));
        }

        newLanguageLists.get(position).setSelected(true);

        return newLanguageLists;
    }
}
