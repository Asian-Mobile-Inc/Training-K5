package issues8;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asian.R;
import com.example.asian.databinding.DialogConfirmDeleteBinding;
import com.example.asian.databinding.ItemRvUserBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserAdapter extends ListAdapter<UserModel, UserAdapter.ViewHolder> {
    private final DBHandler dbHandler;
    private final Context context;
    private static final String DEBUG = "DEBUG";

    protected UserAdapter(DBHandler dbHandler, Context context) {
        super(DIFF_CALLBACK);
        this.dbHandler = dbHandler;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvUserBinding binding = ItemRvUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel userModel = getItem(position);

        holder.binding.tvCountNumber.setText(String.valueOf(userModel.getCountNumber()));
        holder.binding.tvUsername.setText(userModel.getName());
        holder.binding.tvAge.setText(context.getString(R.string.textview_text_years_old, userModel.getAge()));

        Dialog dialog = new Dialog(context);

        holder.binding.ivDeleteUser.setOnClickListener(v -> {
            Log.d(DEBUG, String.valueOf(userModel.getId()));

            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            DialogConfirmDeleteBinding dialogBinding = DialogConfirmDeleteBinding.inflate(LayoutInflater.from(context));
            dialog.setContentView(dialogBinding.getRoot());
            Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);

            dialogBinding.tvExplain.setText(context.getString(R.string.textview_text_are_you_sure_you_want_to_delete, userModel.getName()));
            dialogBinding.tvCancel.setOnClickListener(view -> {
                dialog.dismiss();
            });
            dialogBinding.tvConfirm.setOnClickListener(view -> {
                dialog.dismiss();
                dbHandler.deleteUserById(userModel.getId());
                List<UserModel> oldLists = new ArrayList<>(getCurrentList());
                oldLists.remove(holder.getAdapterPosition());

                List<UserModel> newLists = new ArrayList<>();
                for (int i = 0; i < oldLists.size(); i++) {
                    UserModel item = oldLists.get(i);
                    newLists.add(new UserModel(item.getId(), item.getName(), item.getAge(), i + 1));
                }
                submitList(newLists);
            });
            dialog.show();
        });
    }

    public static final DiffUtil.ItemCallback<UserModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<UserModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull UserModel oldItem, @NonNull UserModel newItem) {
                    return Objects.equals(oldItem.getId(), newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull UserModel oldItem, @NonNull UserModel newItem) {
                    return Objects.equals(oldItem.getName(), newItem.getName())
                            && Objects.equals(oldItem.getAge(), newItem.getAge())
                            && Objects.equals(oldItem.getCountNumber(), newItem.getCountNumber());
                }
            };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemRvUserBinding binding;

        public ViewHolder(ItemRvUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
