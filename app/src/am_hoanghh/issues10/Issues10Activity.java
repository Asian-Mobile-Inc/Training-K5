package issues10;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asian.databinding.ActivityIssues10Binding;

import issues10.All.AllFragment;
import issues10.Favorite.FavoriteFragment;
import issues10.ViewPager.MyPagerAdapter;

public class Issues10Activity extends AppCompatActivity {
    private ActivityIssues10Binding mBinding;
    private static final int ALL_POSITION = 0;
    private static final int FAVORITE_POSITION = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityIssues10Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initListeners();
        initViewPager();
    }

    private void initListeners() {
        mBinding.btnAll.setOnClickListener(v -> mBinding.viewPager.setCurrentItem(ALL_POSITION));
        mBinding.btnFavorite.setOnClickListener(v -> mBinding.viewPager.setCurrentItem(FAVORITE_POSITION));
    }

    private void initViewPager() {
        MyPagerAdapter adapter = new MyPagerAdapter(this);
        mBinding.viewPager.setAdapter(adapter);
        mBinding.viewPager.setOffscreenPageLimit(2);
        mBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setSelectedItem(position);

                // Update rv in fragment when switch tab
                Fragment fragment = adapter.getFragment(position);
                if (fragment instanceof AllFragment) {
                    ((AllFragment) fragment).refresh();
                } else if (fragment instanceof FavoriteFragment) {
                    ((FavoriteFragment) fragment).refresh();
                }
            }
        });
    }

    private void setSelectedItem(int position) {
        mBinding.btnAll.setSelected(position == ALL_POSITION);
        mBinding.btnFavorite.setSelected(position == FAVORITE_POSITION);
    }
}
