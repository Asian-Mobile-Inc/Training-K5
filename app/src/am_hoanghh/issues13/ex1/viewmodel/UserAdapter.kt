package issues13.ex1.viewmodel

import android.app.Dialog
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

    class ViewHolder(
        private val binding: ItemRvUserBinding,
        private val onDeleteListener: OnDeleteListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel) {
            val context: Context = itemView.context

            binding.tvCountNumber.text = user.countNumber.toString()
            binding.tvUsername.text = user.name
            binding.tvAge.text = context.getString(R.string.textview_text_years_old, user.age)

            val dialog = Dialog(context)

            binding.ivDeleteUser.setOnClickListener {
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                val dialogBinding: DialogConfirmDeleteBinding = DialogConfirmDeleteBinding.inflate(
                    LayoutInflater.from(context)
                )
                dialog.setContentView(dialogBinding.root)
                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialog.setCancelable(false)

                dialogBinding.tvExplain.text = context.getString(
                    R.string.textview_text_are_you_sure_you_want_to_delete,
                    user.name
                )
                dialogBinding.tvCancel.setOnClickListener {
                    dialog.dismiss()
                }
                dialogBinding.tvConfirm.setOnClickListener {
                    dialog.dismiss()
                    onDeleteListener.onDelete(user.userId)
                }
                dialog.show()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem.userId == newItem.userId
            }

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.age == newItem.age &&
                        oldItem.countNumber == newItem.countNumber
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRvUserBinding =
            ItemRvUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onDeleteListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
