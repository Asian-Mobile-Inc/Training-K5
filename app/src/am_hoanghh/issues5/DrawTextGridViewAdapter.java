package issues5;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.asian.R;

import java.util.ArrayList;

public class DrawTextGridViewAdapter extends ArrayAdapter<DrawText> {
    private final Context context;

    public DrawTextGridViewAdapter(Context context, ArrayList<DrawText> list) {
        super(context, 0, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_grid_draw_text, parent, false);
        }

        DrawText model = getItem(position);

        TextView tvDrawText = itemView.findViewById(R.id.tvDrawText);

        if (model != null) {
            tvDrawText.setText(model.getContent());

            Typeface typeface = ResourcesCompat.getFont(context, model.getFont());
            tvDrawText.setTypeface(typeface);
        }

        return itemView;

        // TODO choose grid item
    }
}
