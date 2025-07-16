package issues13.ex2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.asian.databinding.FragmentAllImageBinding
import issues13.ex2.viewmodel.AllAdapter
import issues13.ex2.viewmodel.ImageViewModel

class AllFragment : Fragment() {
    private lateinit var binding: FragmentAllImageBinding
    private lateinit var viewModel: ImageViewModel
    private lateinit var adapter: AllAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initAdapter()
        initListeners()
    }

    private fun initViewModel() {
        viewModel = context?.let { ImageViewModel(it) }!!
    }

    private fun initAdapter() {
        adapter = AllAdapter { imageId, isFavorite ->
            viewModel.images.observe(viewLifecycleOwner) { images ->
                images?.let {
                    adapter.submitList(images)
                }
            }
            viewModel.updateFavoriteImage(imageId, isFavorite)
        }
        binding.rvAllImages.layoutManager = GridLayoutManager(context, 3)
        binding.rvAllImages.adapter = adapter
        refresh()
    }

    fun initListeners() {
        binding.viewDownload.setOnClickListener {
            viewModel.images.observe(viewLifecycleOwner) { images ->
                images?.let {
                    adapter.submitList(images)
                    binding.viewDownload.visibility = View.GONE
                    binding.rvAllImages.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
            binding.rvAllImages.visibility = View.GONE
            binding.viewDownload.isEnabled = false
            binding.progressBar.visibility = View.VISIBLE
            viewModel.addImagesFromServerApi()
        }
    }

    fun refresh() {
        viewModel.images.observe(viewLifecycleOwner) { images ->
            images?.let {
                adapter.submitList(images)
            }
        }
        viewModel.selectAllImages()
    }
}
