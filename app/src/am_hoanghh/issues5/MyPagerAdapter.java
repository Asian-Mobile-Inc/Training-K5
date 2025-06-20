package issues5;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import issues5.Draw.DrawFragment;
import issues5.DrawText.DrawTextFragment;
import issues5.Home.HomeFragment;
import issues5.Mine.MineFragment;

public class MyPagerAdapter extends FragmentStateAdapter {
    public MyPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new DrawTextFragment();
            case 2:
                return new DrawFragment();
            case 3:
                return new MineFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
