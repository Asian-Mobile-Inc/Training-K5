package issues13.ex1.viewmodel

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asian.R
import com.example.asian.databinding.DialogConfirmDeleteBinding
import com.example.asian.databinding.ItemRvUserBinding
import issues13.ex1.model.UserModel

class UserAdapter(private val onDeleteListener: OnDeleteListener) :
    ListAdapter<UserModel, UserAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(
        private val binding: ItemRvUserBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel) {
            val context: Context = itemView.context

            binding.tvCountNumber.text = user.countNumber.toString()
            binding.tvUsername.text = user.name
            binding.tvAge.text = context.getString(R.string.textview_text_years_old, user.age)

            val dialogBinding: DialogConfirmDeleteBinding = DialogConfirmDeleteBinding.inflate(
                LayoutInflater.from(context)
            )
            val dialog = AlertDialog.Builder(context).apply {
                setView(dialogBinding.root)
                setCancelable(false)
            }.create()
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogBinding.tvCancel.setOnClickListener {
                dialog.dismiss()
            }
            dialogBinding.tvConfirm.setOnClickListener {
                dialog.dismiss()
                onDeleteListener.onDelete(user.userId)
            }

            binding.ivDeleteUser.setOnClickListener {
                dialogBinding.tvExplain.text = context.getString(
                    R.string.textview_text_are_you_sure_you_want_to_delete,
                    user.name
                )
                dialog.show()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
                oldItem.userId == newItem.userId

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
                oldItem.name == newItem.name &&
                        oldItem.age == newItem.age &&
                        oldItem.countNumber == newItem.countNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}
