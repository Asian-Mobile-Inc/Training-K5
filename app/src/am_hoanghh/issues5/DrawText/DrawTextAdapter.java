package issues5.DrawText;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asian.R;

import java.util.ArrayList;
import java.util.List;

public class DrawTextAdapter extends ListAdapter<DrawText, DrawTextAdapter.ViewHolder> {
    private static int selectedPosition = 0;
    private final List<DrawText> drawTextLists;
    private final Context context;
    private final OnDrawTextSelectedListener listener;

    protected DrawTextAdapter(Context context, List<DrawText> drawTextLists, OnDrawTextSelectedListener listener) {
        super(DIFF_CALLBACK);
        this.drawTextLists = drawTextLists;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DrawTextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grid_draw_text, parent, false);

        return new DrawTextAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrawTextAdapter.ViewHolder holder, int position) {
        DrawText item = getItem(position);

        holder.getTextView().setText(item.getContent());
        Typeface typeface = ResourcesCompat.getFont(context, item.getFont());
        holder.getTextView().setTypeface(typeface);

        if (item.isSelected()) {
            holder.getTextView().setTextColor(context.getColor(R.color.black_181818));
            holder.getTextView().setBackgroundResource(R.drawable.bg_draw_text_rounded_textview_focus);
        } else {
            holder.getTextView().setTextColor(context.getColor(R.color.gray_9B9B9B));
            holder.getTextView().setBackgroundResource(R.drawable.bg_draw_text_rounded_textview);
        }

        holder.itemView.setOnClickListener(view -> {
            selectedPosition = holder.getAdapterPosition();
            submitList(getNewDrawTextList(selectedPosition));
            listener.onDrawTextSelected(typeface);
        });
    }

    public static final DiffUtil.ItemCallback<DrawText> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<DrawText>() {
                @Override
                public boolean areItemsTheSame(@NonNull DrawText oldItem, @NonNull DrawText newItem) {
                    return oldItem.getFont() == newItem.getFont();
                }

                @Override
                public boolean areContentsTheSame(@NonNull DrawText oldItem, @NonNull DrawText newItem) {
                    return oldItem.isSelected() == newItem.isSelected();
                }
            };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.tvDrawText);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    public ArrayList<DrawText> getNewDrawTextList(int position) {
        ArrayList<DrawText> newDrawTextLists = new ArrayList<>();
        for (DrawText item : drawTextLists) {
            newDrawTextLists.add(new DrawText(item.getContent(), item.getFont()));
        }
        newDrawTextLists.get(position).setSelected(true);

        return newDrawTextLists;
    }
}
