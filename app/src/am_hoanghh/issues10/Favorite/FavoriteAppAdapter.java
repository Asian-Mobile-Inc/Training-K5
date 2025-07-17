package issues10.Favorite;

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

import java.util.Objects;

import issues10.Model.AppModel;
import issues10.Util.DBHandler;

public class FavoriteAppAdapter extends ListAdapter<AppModel, FavoriteAppAdapter.ViewHolder> {
    private final Context mContext;
    private final DBHandler mDbHandler;
    private static final int WIDTH_ICON = 46;
    private static final int WIDTH_FAV_ICON = 24;
    private static final int IS_FAVORITE_TRUE = 1;

    protected FavoriteAppAdapter(Context context, DBHandler dbHandler) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        this.mDbHandler = dbHandler;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvAppBinding binding = ItemRvAppBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppModel item = getItem(position);

        Glide.with(mContext)
                .load(item.getmIcon())
                .override(pxToDp(WIDTH_ICON, mContext), pxToDp(WIDTH_ICON, mContext))
                .into(holder.mBinding.ivIconApp);
        holder.mBinding.tvAppName.setText(item.getmName());
        Glide.with(mContext)
                .load(item.getmIsFavorite() == IS_FAVORITE_TRUE ? R.drawable.ic_heart_fill_blue : R.drawable.ic_heart_line_blue)
                .override(pxToDp(WIDTH_FAV_ICON, mContext), pxToDp(WIDTH_FAV_ICON, mContext))
                .into(holder.mBinding.ivFavorite);
        holder.mBinding.ivFavorite.setOnClickListener(v -> {
            mDbHandler.updateApp(item.getmIsFavorite() ^ 1, item.getmId());
            submitList(mDbHandler.readAppsByFavorite(IS_FAVORITE_TRUE));
        });
    }

    public static final DiffUtil.ItemCallback<AppModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<AppModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull AppModel oldItem, @NonNull AppModel newItem) {
                    return Objects.equals(oldItem.getmId(), newItem.getmId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull AppModel oldItem, @NonNull AppModel newItem) {
                    return Objects.equals(oldItem.getmIcon(), newItem.getmIcon()) &&
                            Objects.equals(oldItem.getmName(), newItem.getmName()) &&
                            oldItem.getmIsFavorite() == newItem.getmIsFavorite();
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
