package issues5;

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

        Home model = getItem(position);

        ImageView ivItemGrid = itemView.findViewById(R.id.ivItemGrid);
        ImageView ivHeart = itemView.findViewById(R.id.ivHeart);
        TextView tvTitle = itemView.findViewById(R.id.tvTitle);
        TextView tvLikeNumber = itemView.findViewById(R.id.tvLikeNumber);

        if (model != null) {
            Glide.with(itemView.getContext())
                    .load(model.getImage())
                    .transform(new CenterCrop(), new RoundedCorners(24))
                    .into(ivItemGrid);

            tvTitle.setText(model.getTitle());
            tvLikeNumber.setText(String.valueOf(model.getLike()));
        }

        return itemView;
    }
}
