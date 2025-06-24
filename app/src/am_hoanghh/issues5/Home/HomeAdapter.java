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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeAdapter extends ListAdapter<Home, HomeAdapter.ViewHolder> {
    private final Context context;
    private final List<Home> homeLists;

    protected HomeAdapter(Context context, List<Home> homeLists) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.homeLists = homeLists;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid_home, parent, false);

        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Home item = getItem(position);

        Glide.with(context)
                .load(item.getImage())
                .transform(new CenterCrop(), new RoundedCorners(24))
                .override(175, 215)
                .into(holder.getIvItemGrid());
        holder.getTvTitle().setText(item.getTitle());
        holder.getTvLikeNumber().setText(String.valueOf(item.getLike()));

        if (item.isFavorite()) {
            Glide.with(context)
                    .load(R.drawable.ic_heart_gradient)
                    .override(45, 45)
                    .into(holder.getIvHeart());
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_heart)
                    .override(45, 45)
                    .into(holder.getIvHeart());
        }

        holder.getIvHeart().setOnClickListener(v -> {
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
        private final ImageView ivItemGrid;
        private final ImageView ivHeart;
        private final TextView tvTitle;
        private final TextView tvLikeNumber;

        public ViewHolder(View view) {
            super(view);
            ivItemGrid = (ImageView) view.findViewById(R.id.ivItemGrid);
            ivHeart = (ImageView) view.findViewById(R.id.ivHeart);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvLikeNumber = (TextView) view.findViewById(R.id.tvLikeNumber);
        }

        public ImageView getIvHeart() {
            return ivHeart;
        }

        public ImageView getIvItemGrid() {
            return ivItemGrid;
        }

        public TextView getTvLikeNumber() {
            return tvLikeNumber;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }
    }
}
