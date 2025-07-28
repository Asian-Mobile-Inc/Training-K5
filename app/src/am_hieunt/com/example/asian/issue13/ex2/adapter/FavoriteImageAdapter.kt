package com.example.asian.issue13.ex2.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.asian.R
import com.example.asian.databinding.ItemExpandImageBinding
import com.example.asian.databinding.ItemPhotoBinding
import com.example.asian.issue13.ex2.model.Image
import com.example.asian.issue13.ex2.viewmodel.ImageViewModel

class FavoriteImageAdapter(private val mContext: Context, private val mViewModel: ImageViewModel): ListAdapter<Image, FavoriteImageAdapter.ViewHolder>(DIFF_CALLBACK) {
    private lateinit var mBindingDialog: ItemExpandImageBinding
    private lateinit var mDialog: Dialog

    companion object {
        var DIFF_CALLBACK = object: DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.url == newItem.url && oldItem.favorite == newItem.favorite && oldItem.img.contentEquals(newItem.img)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteImageAdapter.ViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        initDialog()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteImageAdapter.ViewHolder, position: Int) {
        val image = getItem(position)
        holder.bind(image)
    }

    inner class ViewHolder(var binding: ItemPhotoBinding):  RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Image) {
            Glide.with(mContext)
                .load(R.drawable.ic_heart_selected)
                .into(binding.ivFavorite)
            val img = image.img?.let { convertByteToBitmap(it) }
            binding.sivPhoto.setImageBitmap(img)
            binding.ivFavorite.setOnClickListener {
                mViewModel.deleteImage(image)
            }
            binding.sivPhoto.setOnClickListener {
                showImage(img!!)
            }
        }
    }

    private fun convertByteToBitmap(byte: ByteArray): Bitmap = BitmapFactory.decodeByteArray(byte, 0, byte.size)

    private fun initDialog() {
        mDialog = Dialog(mContext)
        mBindingDialog = ItemExpandImageBinding.inflate(LayoutInflater.from(mContext))
        mDialog.setContentView(mBindingDialog.root)
        mDialog.window?.setBackgroundDrawableResource(R.color.transparent)
        mBindingDialog.ivExpandImage.setOnClickListener {
            mDialog.cancel()
        }
    }

    private fun showImage(img: Bitmap) {
        mBindingDialog.ivExpandImage.setImageBitmap(img)
        mDialog.show()
        mDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}
