package issues13.ex2.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.asian.databinding.ItemRvImageAllFavoriteBinding
import issues13.ex2.model.ImageModel
import kotlin.math.roundToInt
import com.bumptech.glide.load.resource.bitmap.CenterCrop

class AllAdapter(private val onFavoriteListener: OnFavoriteListener) :
    ListAdapter<ImageModel, AllAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(
        private val binding: ItemRvImageAllFavoriteBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: ImageModel) {
            val context: Context = itemView.context

            Glide.with(context)
                .load(image.url)
                .apply(
                    RequestOptions()
                        .transform(
                            CenterCrop(),
                            RoundedCorners(dpToPx(RADIUS, context))
                        )
                )
                .into(binding.ivImage)
            binding.ivFavorite.isSelected = image.isFavorite
            binding.ivFavorite.setOnClickListener {
                onFavoriteListener.onFavorite(image.imageId, !image.isFavorite)
            }
        }

        private fun dpToPx(dp: Int, context: Context): Int =
            (dp * context.resources.displayMetrics.density).roundToInt()
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageModel>() {
            override fun areItemsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean =
                oldItem.imageId == newItem.imageId

            override fun areContentsTheSame(oldItem: ImageModel, newItem: ImageModel): Boolean =
                oldItem.url == newItem.url &&
                        oldItem.isFavorite == newItem.isFavorite
        }

        private const val RADIUS = 8
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        ItemRvImageAllFavoriteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}
