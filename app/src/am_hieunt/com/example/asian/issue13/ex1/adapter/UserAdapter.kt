package com.example.asian.issue13.ex1.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asian.R
import com.example.asian.databinding.ItemUserBinding
import com.example.asian.issue13.ex1.model.User

class UserAdapter(private val mContext: Context, private val userDeleteListener: UserClickDeleteListener): ListAdapter<User, UserAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        val binding: ItemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        val user: User = getItem(position)
        user.position = 0;
        holder.binding.tvStt.text = mContext.getString(R.string.position, position + 1)
        holder.binding.tvUserName.text = user.name
        holder.binding.tvAge.text = user.age.toString()
        holder.binding.ivDelete.setOnClickListener {
            userDeleteListener.onDelete(user)
        }
    }

    inner class ViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    interface UserClickDeleteListener {
        fun onDelete(user: User)
    }
}
