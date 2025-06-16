package issues6.ex1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import issues6.ex1.location.LocationFragment;
import issues6.ex1.permission.PermissionFragment;

public class MyPagerAdapter extends FragmentStateAdapter {
    private final ViewPager2 viewPager;
    public MyPagerAdapter(ViewPager2 viewPager, @NonNull FragmentActivity fa) {
        super(fa);
        this.viewPager = viewPager;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PermissionFragment(viewPager);
            case 1:
                return new LocationFragment();
            default:
                return new PermissionFragment(viewPager);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
