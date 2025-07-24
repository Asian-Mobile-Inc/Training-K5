package com.example.asian.issue13.ex2.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.asian.R
import com.example.asian.databinding.ItemPhotoBinding
import com.example.asian.issue13.ex2.model.Image


class AllImageAdapter(private val mContext: Context): ListAdapter<Image, AllImageAdapter.ViewHolder>(DIFF_CALLBACK){

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.url == newItem.url && oldItem.favorite == newItem.favorite && oldItem.img.contentEquals(newItem.img)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllImageAdapter.ViewHolder {
        val binding: ItemPhotoBinding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllImageAdapter.ViewHolder, position: Int) {
        val img: Image = getItem(position)
        holder.binding.sivPhoto.setImageBitmap(img.img?.let { convertByteToBitmap(it) })
        Glide.with(mContext)
            .load(if (img.favorite) R.drawable.ic_heart_selected else R.drawable.ic_heart_default)
            .into(holder.binding.ivFavorite)
        holder.binding.ivFavorite.setOnClickListener {
            updateImage(holder.adapterPosition)
        }
    }

    inner class ViewHolder(var binding: ItemPhotoBinding): RecyclerView.ViewHolder(binding.root) {
    }

    private fun updateImage(position: Int) {
        val newImages = currentList.toMutableList()
        val img = Image(currentList[position].url)
        img.favorite = !currentList[position].favorite
        img.img = currentList[position].img
        newImages[position] = img
        submitList(newImages)
    }

    private fun convertByteToBitmap(byte: ByteArray): Bitmap = BitmapFactory.decodeByteArray(byte, 0, byte.size)
}
