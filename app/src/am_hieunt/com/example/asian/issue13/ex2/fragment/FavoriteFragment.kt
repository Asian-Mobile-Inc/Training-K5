package com.example.asian.issue13.ex2.fragment

import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.RectF
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.request.transition.ViewPropertyTransition.Animator
import com.example.asian.R
import com.example.asian.databinding.FragmentFavoriteBinding
import com.example.asian.issue13.ex2.adapter.FavoriteImageAdapter
import com.example.asian.issue13.ex2.viewmodel.ImageViewModel

class FavoriteFragment : Fragment() {
    private lateinit var mBinding: FragmentFavoriteBinding
    private val mImageViewModel: ImageViewModel by lazy {
        ViewModelProvider (
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ImageViewModel::class.java]
    }
    private val mImageAdapter: FavoriteImageAdapter by lazy {
        FavoriteImageAdapter(requireContext(), mImageViewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        mBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        initView()
        initData()
        return mBinding.root
    }

    private fun initView() {
        mBinding.rvFavorite.layoutManager = GridLayoutManager(context, 3)
        mBinding.rvFavorite.adapter = mImageAdapter
    }

    private fun initData() {
        mImageViewModel.allImage.observe(viewLifecycleOwner) {list ->
            list?.let {
                mImageAdapter.submitList(it)
            }
        }
    }
}
