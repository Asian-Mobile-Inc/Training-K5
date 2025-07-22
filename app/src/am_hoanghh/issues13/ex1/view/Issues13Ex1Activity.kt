package issues13.ex1.view

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asian.R
import com.example.asian.databinding.ActivityIssues8Binding
import com.example.asian.databinding.DialogConfirmDeleteBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import issues13.ex1.database.DatabaseProvider
import issues13.ex1.model.UserModel
import issues13.ex1.repository.UserRepository
import issues13.ex1.viewmodel.UserAdapter
import issues13.ex1.viewmodel.UserViewModel
import issues13.ex1.viewmodel.UserViewModelFactory

class Issues13Ex1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityIssues8Binding
    private lateinit var viewModel: UserViewModel
    private lateinit var dialog: AlertDialog
    private lateinit var dialogBinding: DialogConfirmDeleteBinding
    private lateinit var userAdapter: UserAdapter
    private var currentAction = UserAction.NONE

    private enum class UserAction {
        NONE, INSERT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIssues8Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomSheet()
        initViewModel()
        initDialog()
        initListeners()
        initAdapter()
        observeUserLists()
    }

    private fun initBottomSheet() {
        val behavior: BottomSheetBehavior<ConstraintLayout> =
            BottomSheetBehavior.from(binding.bottomSheet)
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun initViewModel() {
        val userDao = DatabaseProvider.getDatabase(this).userDao()
        val repository = UserRepository(userDao)
        val factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]
    }

    private fun initDialog() {
        dialogBinding = DialogConfirmDeleteBinding.inflate(layoutInflater)
        dialog = AlertDialog.Builder(this).apply {
            setView(dialogBinding.root)
            setCancelable(false)
            dialogBinding.tvCancel.setOnClickListener {
                dialog.dismiss()
            }
            dialogBinding.tvConfirm.setOnClickListener {
                dialog.dismiss()
                currentAction = UserAction.NONE
                viewModel.deleteAll()
            }
        }.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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
                currentAction = UserAction.INSERT
                viewModel.addUser(UserModel(name = name, age = age))
            }
        }

        binding.flDeleteAll.setOnClickListener {
            dialogBinding.tvExplain.text = getString(
                R.string.textview_text_are_you_sure_you_want_to_delete_all,
                userAdapter.currentList.size
            )
            dialog.show()
        }
    }

    private fun initAdapter() {
        userAdapter = UserAdapter { userId ->
            currentAction = UserAction.NONE
            viewModel.deleteUser(UserModel(userId = userId, name = "", age = ""))
        }
        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = userAdapter
    }

    private fun observeUserLists() {
        viewModel.users.observe(this) { users ->
            users?.let {
                userAdapter.submitList(users)
                when (currentAction) {
                    UserAction.INSERT -> {
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
                    else -> {}
                }
            }
        }
    }
}
