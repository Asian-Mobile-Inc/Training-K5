package issues10;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ItemRvAppBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AllAppAdapter extends ListAdapter<App, AllAppAdapter.ViewHolder> {
    private final Context mContext;

    protected AllAppAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvAppBinding mBinding = ItemRvAppBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AllAppAdapter.ViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        App item = getItem(position);

        Glide.with(mContext)
                .load(item.getmIcon())
                .override(pxToDp(46, mContext), pxToDp(46, mContext))
                .into(holder.mBinding.ivIconApp);
        holder.mBinding.tvAppName.setText(item.getmName());
        Glide.with(mContext)
                .load(item.ismIsFavorite() ? R.drawable.ic_heart_fill_blue : R.drawable.ic_heart_line_blue)
                .override(pxToDp(24, mContext), pxToDp(24, mContext))
                .into(holder.mBinding.ivFavorite);

        holder.mBinding.ivFavorite.setOnClickListener(v -> {
            List<App> currentLists = new ArrayList<>(getCurrentList());
            App currentItem = currentLists.get(holder.getAdapterPosition());
            currentLists.set(holder.getAdapterPosition(), new App(currentItem.getmIcon(), currentItem.getmName(), !currentItem.ismIsFavorite()));
            submitList(currentLists);
        });
    }

    public static final DiffUtil.ItemCallback<App> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<App>() {
                @Override
                public boolean areItemsTheSame(@NonNull App oldItem, @NonNull App newItem) {
                    return Objects.equals(oldItem.getmIcon(), newItem.getmIcon());
                }

                @Override
                public boolean areContentsTheSame(@NonNull App oldItem, @NonNull App newItem) {
                    return Objects.equals(oldItem.getmIcon(), newItem.getmIcon()) &&
                            Objects.equals(oldItem.getmName(), newItem.getmName()) &&
                            oldItem.ismIsFavorite() == newItem.ismIsFavorite();
                }
            };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRvAppBinding mBinding;

        public ViewHolder(ItemRvAppBinding mBinding) {
            super(mBinding.getRoot());
            this.mBinding = mBinding;
        }
    }
    public static int pxToDp(int px, Context context) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
    }
}
