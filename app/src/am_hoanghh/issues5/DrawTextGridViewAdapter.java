package issues5;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.example.asian.R;

import java.util.ArrayList;

public class DrawTextGridViewAdapter extends ArrayAdapter<DrawText> {
    private final Context context;
    private final OnDrawTextSelectedListener listener;
    private static int selectedItem = 0;

    public DrawTextGridViewAdapter(Context context, ArrayList<DrawText> list, OnDrawTextSelectedListener listener) {
        super(context, 0, list);
        this.context = context;
        this.listener = listener;
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

            if (position == selectedItem) {
                tvDrawText.setTextColor(context.getColor(R.color.black_181818));
                tvDrawText.setBackgroundResource(R.drawable.bg_draw_text_rounded_textview_focus);
            } else {
                tvDrawText.setTextColor(context.getColor(R.color.gray_9B9B9B));
                tvDrawText.setBackgroundResource(R.drawable.bg_draw_text_rounded_textview);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    selectedItem = position;

                    notifyDataSetChanged();

                    listener.onDrawTextSelected(typeface);
                }
            });
        }

        return itemView;
    }
}
