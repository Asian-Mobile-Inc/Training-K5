package issues13.ex2.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.asian.databinding.ActivityIssues13Binding
import issues13.ex2.viewpager.MyPagerAdapter

class Issues13Ex2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityIssues13Binding
    private lateinit var adapter: MyPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIssues13Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewPager()
        initListeners()
    }

    private fun initListeners() {
        binding.btnAll.setOnClickListener {
            binding.viewPager.currentItem = ALL_POSITION
        }
        binding.btnFavorite.setOnClickListener {
            binding.viewPager.currentItem = FAVORITE_POSITION
        }
    }

    private fun initViewPager() {
        adapter = MyPagerAdapter(this)
        binding.viewPager.adapter = adapter
        binding.viewPager.offscreenPageLimit = 2
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setSelectedItem(position)

                val fragment: Fragment = adapter.getFragment(position)
                if (fragment is AllFragment) {
                    fragment.refresh()
                } else if (fragment is FavoriteFragment) {
                    fragment.refresh()
                }
            }
        })
    }

    private fun setSelectedItem(position: Int) {
        binding.btnAll.isSelected = position == ALL_POSITION
        binding.btnFavorite.isSelected = position == FAVORITE_POSITION
    }

    companion object {
        private const val ALL_POSITION: Int = 0
        private const val FAVORITE_POSITION: Int = 1
    }
}
