package issues5.DrawText;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asian.R;
import com.example.asian.databinding.ItemGridDrawTextBinding;

import java.util.ArrayList;

public class DrawTextAdapter extends ListAdapter<DrawText, DrawTextAdapter.ViewHolder> {
    private static int sSelectedPosition = 0;
    private final OnDrawTextSelectedListener mListener;

    protected DrawTextAdapter(OnDrawTextSelectedListener listener) {
        super(DIFF_CALLBACK);
        this.mListener = listener;
    }

    @NonNull
    @Override
    public DrawTextAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DrawTextAdapter.ViewHolder(ItemGridDrawTextBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(DrawTextAdapter.ViewHolder holder, int position) {
        DrawText item = getItem(position);
        Context context = holder.itemView.getContext();

        holder.mBinding.tvDrawText.setText(item.getContent());
        Typeface typeface = ResourcesCompat.getFont(context, item.getFont());
        holder.mBinding.tvDrawText.setTypeface(typeface);

        if (item.isSelected()) {
            holder.mBinding.tvDrawText.setTextColor(context.getColor(R.color.black_181818));
            holder.mBinding.tvDrawText.setBackgroundResource(R.drawable.bg_draw_text_rounded_textview_focus);
        } else {
            holder.mBinding.tvDrawText.setTextColor(context.getColor(R.color.gray_9B9B9B));
            holder.mBinding.tvDrawText.setBackgroundResource(R.drawable.bg_draw_text_rounded_textview);
        }

        holder.itemView.setOnClickListener(view -> {
            sSelectedPosition = holder.getAdapterPosition();
            submitList(getNewDrawTextList(sSelectedPosition));
            mListener.onDrawTextSelected(typeface);
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
        private final ItemGridDrawTextBinding mBinding;

        public ViewHolder(ItemGridDrawTextBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }

    public ArrayList<DrawText> getNewDrawTextList(int position) {
        ArrayList<DrawText> newDrawTextLists = new ArrayList<>();
        for (DrawText item : getCurrentList()) {
            newDrawTextLists.add(new DrawText(item.getContent(), item.getFont()));
        }
        newDrawTextLists.get(position).setSelected(true);

        return newDrawTextLists;
    }
}
