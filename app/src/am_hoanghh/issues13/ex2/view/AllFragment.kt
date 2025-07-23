package issues13.ex2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.asian.databinding.FragmentAllImageBinding
import issues13.ex2.database.DatabaseProvider
import issues13.ex2.repository.ImageRepository
import issues13.ex2.retrofit.ImageApi
import issues13.ex2.retrofit.RetrofitClient
import issues13.ex2.viewmodel.AllAdapter
import issues13.ex2.viewmodel.ImageViewModel
import issues13.ex2.viewmodel.ImageViewModelFactory
import issues13.ex2.viewmodel.OnFavoriteListener
import kotlinx.coroutines.launch

class AllFragment : Fragment(), OnFavoriteListener {
    private var _binding: FragmentAllImageBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ImageViewModel
    private lateinit var adapter: AllAdapter
    private val delayMillis = 1000L
    private var currentAction = ImageAction.NONE

    private enum class ImageAction {
        NONE, DOWNLOAD
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllImageBinding.inflate(inflater, container, false)
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
        adapter = AllAdapter(this)
        binding.rvAllImages.layoutManager = GridLayoutManager(context, 3)
        binding.rvAllImages.adapter = adapter
        refresh()
    }

    fun initListeners() {
        binding.viewDownload.setOnClickListener {
            currentAction = ImageAction.DOWNLOAD
            binding.viewDownload.isEnabled = false
            binding.rvAllImages.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            viewModel.addImagesFromServerApi()
        }
    }

    private fun observeViewModel() {
        viewModel.images.observe(viewLifecycleOwner) { images ->
            images?.let {
                adapter.submitList(images)
                when (currentAction) {
                    ImageAction.DOWNLOAD -> {
                        binding.viewDownload.postDelayed({
                            binding.viewDownload.visibility = View.GONE
                            binding.ivDownloadIcon.visibility = View.GONE
                            binding.tvDownload.visibility = View.GONE
                        }, delayMillis)
                        binding.rvAllImages.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                    else -> {}
                }
            }
        }
    }

    fun refresh() {
        if (::viewModel.isInitialized) {
            currentAction = ImageAction.NONE
            lifecycleScope.launch {
                viewModel.selectAllImages()
            }
        }
    }

    override fun onFavorite(imageId: String, isFavorite: Boolean) {
        currentAction = ImageAction.NONE
        viewModel.updateFavoriteImage(imageId, isFavorite)
    }

    override fun onItemViewClick(url: String?) {

    }
}
