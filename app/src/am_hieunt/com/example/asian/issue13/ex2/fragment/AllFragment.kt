package com.example.asian.issue13.ex2.fragment

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.asian.databinding.FragmentAllBinding
import com.example.asian.issue13.ex2.adapter.AllImageAdapter
import com.example.asian.issue13.ex2.api.ApiService
import com.example.asian.issue13.ex2.model.Image
import com.example.asian.issue13.ex2.viewmodel.ImageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class AllFragment : Fragment() {
    private lateinit var mBinding: FragmentAllBinding
    private val ACCESS_TOKEN: String = "Bearer 37NgYdmLpLPbBFla_63tC23jBk9_iJaIxXdm4l9KX68"
    private lateinit var mImageAdapter: AllImageAdapter
    private lateinit var mImages: List<Image>
    private lateinit var mImageViewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        mBinding = FragmentAllBinding.inflate(inflater, container, false)
        initView()
        initData()
        initListener()
        return mBinding.root
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun initView() {
        mImageAdapter = AllImageAdapter(context!!)
        mBinding.rvImage.layoutManager = GridLayoutManager(context, 3)
        mBinding.rvImage.adapter = mImageAdapter
        mBinding.rvImage.visibility = View.GONE
        mBinding.btnDownload.visibility = View.GONE
        mBinding.pbLoading.visibility = View.VISIBLE
        mBinding.pbLoading.animateProgress()
        mImageViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[ImageViewModel::class.java]
    }

    private fun initData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val response = ApiService.apiService.getImages(ACCESS_TOKEN)
            if (response.isSuccessful) {
                mImages = response.body()!!
                val downloadImage = mImages.map {
                    async {
                        val url = URL(it.url)
                        it.img = url.readBytes()
                    }
                }
                downloadImage.awaitAll()
                withContext(Dispatchers.Main) {
                    mBinding.rvImage.visibility = View.VISIBLE
                    mBinding.btnDownload.visibility = View.VISIBLE
                    mBinding.pbLoading.visibility = View.GONE
                    mBinding.pbLoading.close()
                    mImageAdapter.submitList(mImages)
                }
            }
        }
    }

    private fun initListener() {
        mBinding.btnDownload.setOnClickListener {
            mBinding.btnDownload.isSelected = true
            mBinding.btnDownload.isEnabled = false
            mBinding.rvImage.visibility = View.GONE
            mBinding.pbLoading.visibility = View.VISIBLE
            mBinding.pbLoading.animateProgress()
            mImages = mImageAdapter.currentList
            lifecycleScope.launch(Dispatchers.IO) {
                val saveImage = mImages.map {
                    async {
                        if (it.favorite) {
                            mImageViewModel.addImage(it)
                        }
                    }
                }
                saveImage.awaitAll()
                withContext(Dispatchers.Main) {
                    mBinding.btnDownload.isSelected = false
                    mBinding.btnDownload.isEnabled = true
                    mBinding.rvImage.visibility = View.VISIBLE
                    mBinding.pbLoading.visibility = View.GONE
                    mBinding.pbLoading.close()
                }
            }
        }
    }
}
