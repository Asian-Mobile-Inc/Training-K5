package issues5.Home;

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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.asian.R;
import com.example.asian.databinding.ItemGridHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeAdapter extends ListAdapter<Home, HomeAdapter.ViewHolder> {
    private static final int WIDTH_ITEM_GRID = 175;
    private static final int HEIGHT_ITEM_GRID = 215;
    private static final int ROUNDING_RADIUS = 24;
    private static final int WIDTH_IV_HEART = 45;
    private static final int HEIGHT_IV_HEART = 45;

    protected HomeAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeAdapter.ViewHolder(ItemGridHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Home item = getItem(position);
        Context context = holder.itemView.getContext();

        Glide.with(context)
                .load(item.getImage())
                .transform(new CenterCrop(), new RoundedCorners(ROUNDING_RADIUS))
                .override(WIDTH_ITEM_GRID, HEIGHT_ITEM_GRID)
                .into(holder.mBinding.ivItemGrid);
        holder.mBinding.tvTitle.setText(item.getTitle());
        holder.mBinding.tvLikeNumber.setText(String.valueOf(item.getLike()));

        if (item.isFavorite()) {
            Glide.with(context)
                    .load(R.drawable.ic_heart_gradient)
                    .override(WIDTH_IV_HEART, HEIGHT_IV_HEART)
                    .into(holder.mBinding.ivHeart);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_heart)
                    .override(WIDTH_IV_HEART, HEIGHT_IV_HEART)
                    .into(holder.mBinding.ivHeart);
        }

        holder.mBinding.ivHeart.setOnClickListener(v -> {
            List<Home> currentLists = new ArrayList<>(getCurrentList());
            Home currentItem = currentLists.get(holder.getAdapterPosition());
            currentLists.set(holder.getAdapterPosition(), new Home(currentItem.getTitle(), currentItem.getImage(), currentItem.getLike(), !currentItem.isFavorite()));
            submitList(currentLists);
        });
    }

    public static final DiffUtil.ItemCallback<Home> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Home>() {
                @Override
                public boolean areItemsTheSame(@NonNull Home oldItem, @NonNull Home newItem) {
                    return Objects.equals(oldItem.getTitle(), newItem.getTitle());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Home oldItem, @NonNull Home newItem) {
                    return oldItem.isFavorite() == newItem.isFavorite();
                }
            };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemGridHomeBinding mBinding;

        public ViewHolder(ItemGridHomeBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
