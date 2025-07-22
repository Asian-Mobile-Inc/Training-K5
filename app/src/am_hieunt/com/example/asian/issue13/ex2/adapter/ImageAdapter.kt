package com.example.asian.issue13.ex2.adapter

import android.renderscript.ScriptGroup.Binding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asian.databinding.ItemPhotoBinding
import com.example.asian.databinding.ItemUserBinding
import com.example.asian.issue13.ex2.model.Image

class ImageAdapter: ListAdapter<Image, ImageAdapter.ViewHolder>(DIFF_CALLBACK){

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val binding: ItemPhotoBinding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(var binding: ItemPhotoBinding): RecyclerView.ViewHolder(binding.root) {
    }
}