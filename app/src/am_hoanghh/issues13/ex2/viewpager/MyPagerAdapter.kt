package issues13.ex2.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import issues13.ex2.view.AllFragment
import issues13.ex2.view.FavoriteFragment

class MyPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> FavoriteFragment()
            else -> AllFragment()
        }
    }
}
