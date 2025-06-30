package com.example.asian.issue8.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import com.example.asian.R;
import com.example.asian.issue8.database.UserDatabase;
import com.example.asian.issue8.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ListAdapter<User, UserAdapter.ViewHolder> {
    private final Context mContext;
    private TextView mTvContent, mTvCancelDialog, mTvDeleteDialog;;
    private AlertDialog mAlertDialog;
    private int mPosition = -1;
    private int mUserId = -1;

    public UserAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.mContext = context;
        initAlertDialog();
        initListener();
    }

    public static final DiffUtil.ItemCallback<User> DIFF_CALLBACK = new DiffUtil.ItemCallback<User>() {
        @Override
        public boolean areItemsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.getId() == newItem.getId();
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull User oldItem, @NonNull User newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"DefaultLocale", "UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user = getItem(position);
        user.setNumber(position);
        holder.tvStt.setText(String.format("%d", position + 1));
        holder.tvUsername.setText(user.getUsername());
        holder.tvAge.setText(String.format("%d %s", user.getAge(), mContext.getString(R.string.years_old)));
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPosition = holder.getAdapterPosition();
                mUserId = user.getId();
                mTvContent.setText(mContext.getString(R.string.are_you_sure_you_want_to_delete) + " " + user.getUsername() + " ?");
                mAlertDialog.show();
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvUsername, tvStt, tvAge;
        public ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStt = itemView.findViewById(R.id.tvStt);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            tvAge = itemView.findViewById(R.id.tvAge);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }

    private List<User> updateUser(int position) {
        int del = 0;
        List<User> users = new ArrayList<>();
        List<User> currentList = getCurrentList();
        for (int i = 0; i<currentList.size(); i++) {
            if (i != position) {
                if (del == 0) {
                    users.add(currentList.get(i));
                } else {
                    User newUser = new User();
                    newUser.setId(currentList.get(i).getId());
                    newUser.setUsername(currentList.get(i).getUsername());
                    newUser.setAge(currentList.get(i).getAge());
                    newUser.setNumber(currentList.get(i).getNumber() - del);
                    users.add(newUser);
                }
            } else del++;

        }
        return users;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View dialogView = inflater.inflate(R.layout.item_dialog, null);
        mTvContent = dialogView.findViewById(R.id.tvContent);
        mTvCancelDialog = dialogView.findViewById(R.id.tvCancel);
        mTvDeleteDialog = dialogView.findViewById(R.id.tvDelete);
        dialogBuilder.setView(dialogView);
        mAlertDialog = dialogBuilder.create();
        if (mAlertDialog.getWindow() != null) {
            mAlertDialog.getWindow().setBackgroundDrawable(mContext.getDrawable(R.drawable.my_dialog));
        }
    }

    private void initListener() {
        mTvCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
            }
        });
        mTvDeleteDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPosition != -1) {
                    submitList(updateUser(mPosition));
                }
                UserDatabase.getInstance(mContext).deleteUser(mUserId);
                mAlertDialog.dismiss();
            }
        });
    }
}
