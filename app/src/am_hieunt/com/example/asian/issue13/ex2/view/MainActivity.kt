package com.example.asian.issue13.ex2.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.asian.R
import com.example.asian.databinding.ActivityIssue13Binding
import com.example.asian.issue13.ex2.adapter.ViewPageAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityIssue13Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mBinding = ActivityIssue13Binding.inflate(layoutInflater)
        setContentView(mBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initView()
        initListener()
    }

    private fun initView() {
        mBinding.vpImage.adapter = ViewPageAdapter(this)
        mBinding.tvAll.isSelected = true
        mBinding.tvFavorite.isSelected = false
    }

    private fun initListener() {
        mBinding.vpImage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 0) {
                    mBinding.tvAll.isSelected = true
                    mBinding.tvFavorite.isSelected = false
                    mBinding.tvAll.setTextColor(getColor(R.color.blue_576CEC))
                    mBinding.tvFavorite.setTextColor(getColor(R.color.blue_C5CDFA))
                } else {
                    mBinding.tvAll.isSelected = false
                    mBinding.tvFavorite.isSelected = true
                    mBinding.tvAll.setTextColor(getColor(R.color.blue_C5CDFA))
                    mBinding.tvFavorite.setTextColor(getColor(R.color.blue_576CEC))
                }
            }
        })
        mBinding.tvAll.setOnClickListener {
            mBinding.vpImage.currentItem = 0
        }
        mBinding.tvFavorite.setOnClickListener {
            mBinding.vpImage.currentItem = 1
        }
    }
}
