package issues13.ex2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.asian.databinding.FragmentFavoriteImageBinding
import issues13.ex2.database.DatabaseProvider
import issues13.ex2.repository.ImageRepository
import issues13.ex2.retrofit.ImageApi
import issues13.ex2.retrofit.RetrofitClient
import issues13.ex2.viewmodel.FavoriteAdapter
import issues13.ex2.viewmodel.ImageViewModel
import issues13.ex2.viewmodel.ImageViewModelFactory
import issues13.ex2.viewmodel.OnFavoriteListener

class FavoriteFragment : Fragment(), OnFavoriteListener {
    private var _binding: FragmentFavoriteImageBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ImageViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initAdapter()
        observeViewModel()
        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as? FragmentCallback)?.onFragmentReady(this)
    }

    private fun initViewModel() {
        val context = requireContext().applicationContext
        val imageDao = DatabaseProvider.getDatabase(context).imageDao()
        val imageApi = RetrofitClient.getClient().create(ImageApi::class.java)
        val repository = ImageRepository(imageDao, imageApi)
        val factory = ImageViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ImageViewModel::class.java]
    }

    private fun initAdapter() {
        adapter = FavoriteAdapter(this)
        binding.rvFavoriteImages.layoutManager = GridLayoutManager(context, 3)
        binding.rvFavoriteImages.adapter = adapter
        refresh()
    }

    private fun initListeners() {
        binding.ivImageFullScreen.setOnClickListener {
            binding.ivImageFullScreen.visibility = View.GONE
        }
    }

    private fun observeViewModel() {
        viewModel.images.observe(viewLifecycleOwner) { images ->
            images?.let {
                adapter.submitList(images)
            }
        }
    }

    fun refresh() {
        if (::viewModel.isInitialized) {
            viewModel.selectFavoriteImages(true)
        }
    }

    override fun onFavorite(imageId: String, isFavorite: Boolean) {
        viewModel.updateNotFavoriteImage(imageId, isFavorite)
    }

    override fun onItemViewClick(url: String?) {
        Glide.with(this)
            .load(url)
            .apply(
                RequestOptions()
                    .transform(
                        RoundedCorners(ROUNDING_RADIUS)
                    )
            )
            .into(binding.ivImageFullScreen)
        binding.ivImageFullScreen.visibility = View.VISIBLE
        val anim = RotateAnimation(FROM_DEGREES, TO_DEGREES, PIVOT_X, PIVOT_Y).apply {
            interpolator = LinearInterpolator()
            repeatCount = Animation.ABSOLUTE
            duration = DURATION
        }
        binding.ivImageFullScreen.startAnimation(anim)
    }

    companion object {
        private const val FROM_DEGREES = 0f
        private const val TO_DEGREES = 350f
        private const val PIVOT_X = 15f
        private const val PIVOT_Y = 15f
        private const val DURATION = 700L
        private const val ROUNDING_RADIUS = 18
    }
}
