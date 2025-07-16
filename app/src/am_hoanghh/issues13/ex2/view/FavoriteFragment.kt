package issues13.ex2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.asian.databinding.FragmentFavoriteImageBinding
import issues13.ex2.viewmodel.FavoriteAdapter
import issues13.ex2.viewmodel.ImageViewModel

class FavoriteFragment : Fragment() {
    private lateinit var binding : FragmentFavoriteImageBinding
    private lateinit var viewModel: ImageViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initAdapter()
    }

    private fun initViewModel() {
        viewModel = context?.let { ImageViewModel(it) }!!
    }

    private fun initAdapter() {
        adapter = FavoriteAdapter { imageId, isFavorite ->
            viewModel.images.observe(viewLifecycleOwner) { images ->
                images?.let {
                    adapter.submitList(images)
                }
            }
            viewModel.updateNotFavoriteImage(imageId, isFavorite)
        }
        binding.rvFavoriteImages.layoutManager = GridLayoutManager(context, 3)
        binding.rvFavoriteImages.adapter = adapter
        refresh()
    }

    fun refresh() {
        viewModel.images.observe(viewLifecycleOwner) { images ->
            images?.let {
                adapter.submitList(images)
            }
        }
        viewModel.selectFavoriteImages(true)
    }
}
