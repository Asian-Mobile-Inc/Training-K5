package com.example.asian.issue13.ex1.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.asian.R
import com.example.asian.databinding.ActivityUserBinding
import com.example.asian.databinding.ItemDialogBinding
import com.example.asian.issue13.ex1.adapter.UserAdapter
import com.example.asian.issue13.ex1.model.User
import com.example.asian.issue13.ex1.viewmodel.UserViewModel

class MainActivity : AppCompatActivity(), UserAdapter.UserClickDeleteListener {
    private lateinit var mViewModel: UserViewModel
    private lateinit var mBinding: ActivityUserBinding
    private lateinit var mBindingDialog: ItemDialogBinding
    private lateinit var mUserAdapter: UserAdapter
    private lateinit var mAlertDialog: AlertDialog
    private var mUserDelete: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initAlertDialog()
        initView()
        initListener()
    }

    private fun initView() {
        mBinding.rvListUser.layoutManager = LinearLayoutManager(this)
        mViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[UserViewModel::class.java]
        mUserAdapter = UserAdapter(this, this)
        mBinding.rvListUser.adapter = mUserAdapter
    }

    private fun initData() {
        mViewModel.allUser.observe(this) { list ->
            list?.let {
                var index: Int = 0;
                if (mUserDelete != null) {
                    val newUsers = mutableListOf<User>()
                    for (user in it) {
                        if (user.id > mUserDelete!!.id) {
                            val us = User(user.name, user.age)
                            us.id = user.id
                            us.position = index
                            newUsers.add(us)
                        } else {
                            newUsers.add(user)
                        }
                        index++
                    }
                    mUserAdapter.submitList(newUsers)
                    mUserDelete = null
                } else {
                    mUserAdapter.submitList(it)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initListener() {
        mBinding.btnShow.setOnClickListener {
            mBinding.btnShow.visibility = View.GONE
            mBinding.tvLabel.visibility = View.GONE
            mBinding.ivList.visibility = View.GONE
            mBinding.btnDeleteAll.visibility = View.VISIBLE
            initData()
        }
        mBinding.btnDeleteAll.setOnClickListener {
            if (mUserAdapter.currentList.isNotEmpty()) {
                mBindingDialog.tvContent.text = getString(R.string.are_you_sure_you_want_to_delete_all) + " " +
                        mUserAdapter.currentList.size + " " + getString(R.string.users) + " ?"
                mAlertDialog.show()
            }
        }
        mBindingDialog.tvCancel.setOnClickListener {
            mAlertDialog.dismiss()
        }
        mBindingDialog.tvDelete.setOnClickListener {
            mAlertDialog.dismiss()
            mUserDelete?.let {
                mViewModel.deleteUser(it)
            } ?: mViewModel.deleteAllUser()
        }
        mBinding.edtUserName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mBinding.edtUserName.isSelected = s.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        mBinding.edtAge.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mBinding.edtAge.isSelected = s.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        mBinding.btnAddUser.setOnClickListener {
            if (mBinding.edtUserName.length() != 0 && mBinding.edtAge.length() != 0) {
                val user : User? = mBinding.edtAge.text.toString().toIntOrNull()
                    ?.let { it1 -> User(mBinding.edtUserName.text.toString(), it1) }
                if (user != null) {
                    mViewModel.addUser(user)
                }
            }
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initAlertDialog() {
        val dialogBuilder : AlertDialog.Builder = AlertDialog.Builder(this)
        mBindingDialog = ItemDialogBinding.inflate(LayoutInflater.from(this))
        dialogBuilder.setView(mBindingDialog.root)
        mAlertDialog = dialogBuilder.create()
        mAlertDialog.window?.setBackgroundDrawable(getDrawable(R.drawable.my_dialog))
    }

    @SuppressLint("SetTextI18n")
    override fun onDelete(user: User) {
        mUserDelete = user
        mBindingDialog.tvContent.text = getString(R.string.are_you_sure_you_want_to_delete) + " " + user.name + " ?"
        mAlertDialog.show()
    }
}
