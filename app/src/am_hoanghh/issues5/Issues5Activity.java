package issues5;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asian.databinding.ActivityIssues5Binding;

import issues5.DrawText.OnDrawTextSelectedListener;
import issues5.Viewpager.MyPagerAdapter;

public class Issues5Activity extends AppCompatActivity implements OnDrawTextSelectedListener {
    private ActivityIssues5Binding mBinding;
    private String previousFragmentName = "";
    private static final int HOME_POSITION = 0;
    private static final int DRAW_TEXT_POSITION = 1;
    private static final int DRAW_POSITION = 2;
    private static final int MINE_POSITION = 3;
    private static final String HOME = "Home";
    private static final String FRAGMENT = "Fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityIssues5Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initToolbar();
        initViewPager();
        initListeners();
        updateNewFragmentName();
    }

    private void initToolbar() {
        mBinding.toolbar.setTitle("");
        setSupportActionBar(mBinding.toolbar);
    }

    private void initViewPager() {
        mBinding.viewPager.setAdapter(new MyPagerAdapter(this));
        mBinding.viewPager.setOffscreenPageLimit(4);
        mBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setSelectedNavBarItem(position);
                updateNewFragmentName();
            }
        });
    }

    private void initListeners() {
        mBinding.tvHome.setOnClickListener(v -> {
            mBinding.viewPager.setCurrentItem(HOME_POSITION);
        });
        mBinding.tvDrawText.setOnClickListener(v -> {
            mBinding.viewPager.setCurrentItem(DRAW_TEXT_POSITION);
        });
        mBinding.tvDraw.setOnClickListener(v -> {
            mBinding.viewPager.setCurrentItem(DRAW_POSITION);
        });
        mBinding.tvMine.setOnClickListener(v -> {
            mBinding.viewPager.setCurrentItem(MINE_POSITION);
        });
    }

    private void setSelectedNavBarItem(int position) {
        mBinding.tvHome.setSelected(position == HOME_POSITION);
        mBinding.tvDrawText.setSelected(position == DRAW_TEXT_POSITION);
        mBinding.tvDraw.setSelected(position == DRAW_POSITION);
        mBinding.tvMine.setSelected(position == MINE_POSITION);
    }

    private void updateNewFragmentName() {
        mBinding.tvPreviousFragment.setText(previousFragmentName);

        int currentItem = mBinding.viewPager.getCurrentItem();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + currentItem);
        if (currentFragment != null) {
            previousFragmentName = currentFragment.getClass().getSimpleName().replace(FRAGMENT, "");
        } else {
            previousFragmentName = HOME;
        }
    }

    @Override
    public void onDrawTextSelected(Typeface typeface) {
        mBinding.tvPreviousFragment.setTypeface(typeface);
    }
}
