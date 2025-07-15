package issues13.ex1.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asian.R
import com.example.asian.databinding.ActivityIssues8Binding
import com.example.asian.databinding.DialogConfirmDeleteBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import issues13.ex1.model.UserModel
import issues13.ex1.viewmodel.UserAdapter
import issues13.ex1.viewmodel.UserViewModel

class Issues13Ex1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityIssues8Binding
    private lateinit var viewModel: UserViewModel
    private lateinit var dialog: Dialog
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIssues8Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomSheet()
        initViewModel()
        initDialog()
        initListeners()
        initAdapter()
    }

    private fun initBottomSheet() {
        val behavior: BottomSheetBehavior<ConstraintLayout> =
            BottomSheetBehavior.from(binding.bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun initViewModel() {
        viewModel = UserViewModel(this)
    }

    private fun initDialog() {
        dialog = Dialog(this)
    }

    private fun initListeners() {
        binding.viewShowAll.setOnClickListener {
            binding.ivUser.visibility = View.GONE
            binding.tvDescription.visibility = View.GONE
            binding.viewShowAll.visibility = View.GONE
            binding.rvUsers.visibility = View.VISIBLE
            binding.flDeleteAll.visibility = View.VISIBLE
        }

        binding.tvAddUser.setOnClickListener {
            val name: String = binding.edtUsername.text.toString()
            val age: String = binding.edtAge.text.toString()

            if (name.isEmpty() || age.isEmpty()) {
                Toast.makeText(
                    this,
                    getString(R.string.toast_text_please_enter_all_data),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.users.observe(this) { users ->
                    users?.let {
                        userAdapter.submitList(users)
                        Toast.makeText(
                            this,
                            getString(R.string.toast_text_user_has_been_added),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.edtUsername.text.clear()
                        binding.edtAge.text.clear()
                        binding.edtUsername.clearFocus()
                        binding.edtAge.clearFocus()
                    }
                }
                viewModel.addUser(UserModel(name = name, age = age))
            }
        }

        binding.flDeleteAll.setOnClickListener {
            initDialogListeners()
        }
    }

    private fun initAdapter() {
        userAdapter = UserAdapter { userId ->
            observeUserLists()
            viewModel.deleteUser(UserModel(userId = userId, name = "", age = ""))
        }
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = userAdapter
        observeUserLists()
    }

    private fun observeUserLists() {
        viewModel.users.observe(this) { users ->
            users?.let {
                userAdapter.submitList(users)
            }
        }
    }

    private fun initDialogListeners() {
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val dialogConfirmDeleteBinding: DialogConfirmDeleteBinding =
            DialogConfirmDeleteBinding.inflate(layoutInflater)
        dialog.setContentView(dialogConfirmDeleteBinding.root)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.setCancelable(false)

        dialogConfirmDeleteBinding.tvExplain.text = getString(
            R.string.textview_text_are_you_sure_you_want_to_delete_all,
            userAdapter.currentList.size
        )
        dialogConfirmDeleteBinding.tvCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogConfirmDeleteBinding.tvConfirm.setOnClickListener {
            dialog.dismiss()
            observeUserLists()
            viewModel.deleteAll()
        }
        dialog.show()
    }
}
