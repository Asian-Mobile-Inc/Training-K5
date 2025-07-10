package issues10.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import issues10.All.AllFragment;
import issues10.Favorite.FavoriteFragment;

public class MyPagerAdapter extends FragmentStateAdapter {
    private final Fragment[] mFragments;

    public MyPagerAdapter(FragmentActivity fa) {
        super(fa);
        mFragments = new Fragment []{
            new AllFragment(),
            new FavoriteFragment()
        };
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments[position];
    }

    @Override
    public int getItemCount() {
        return mFragments.length;
    }

    public Fragment getFragment(int position) {
        return mFragments[position];
    }
}
