package issues5.Home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.asian.R;

import java.util.ArrayList;

public class HomeGridViewAdapter extends ArrayAdapter<Home> {
    public HomeGridViewAdapter(Context context, ArrayList<Home> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid_home, parent, false);
        }

        ImageView ivItemGrid = itemView.findViewById(R.id.ivItemGrid);
        ImageView ivHeart = itemView.findViewById(R.id.ivHeart);
        TextView tvTitle = itemView.findViewById(R.id.tvTitle);
        TextView tvLikeNumber = itemView.findViewById(R.id.tvLikeNumber);

        Home model = getItem(position);

        if (model != null) {
            Glide.with(itemView.getContext())
                    .load(model.getImage())
                    .transform(new CenterCrop(), new RoundedCorners(24))
                    .override(175, 215)
                    .into(ivItemGrid);
            tvTitle.setText(model.getTitle());
            tvLikeNumber.setText(String.valueOf(model.getLike()));

            ivHeart.setOnClickListener(v -> {
                if (model.isFavorite()) {
                    Glide.with(getContext())
                            .load(R.drawable.ic_heart)
                            .into(ivHeart);
                    model.setFavorite(false);
                } else {
                    Glide.with(getContext())
                            .load(R.drawable.ic_heart_gradient)
                            .into(ivHeart);
                    model.setFavorite(true);
                }
            });
        }

        return itemView;
    }
}
