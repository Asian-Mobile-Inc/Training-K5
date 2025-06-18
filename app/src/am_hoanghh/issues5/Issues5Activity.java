package issues5;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ActivityIssues5Binding;

import issues5.DrawText.OnDrawTextSelectedListener;

public class Issues5Activity extends AppCompatActivity implements OnDrawTextSelectedListener {
    private ActivityIssues5Binding binding;
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
        binding = ActivityIssues5Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initToolbar();
        initViewPager();
        initListeners();
        updateNewFragmentName();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void initViewPager() {
        binding.viewPager.setAdapter(new MyPagerAdapter(this));
        binding.viewPager.setOffscreenPageLimit(4);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setSelectedNavBarItem(position);
                updateNewFragmentName();
            }
        });
    }

    private void initListeners() {
        binding.ivHome.setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(HOME_POSITION);
        });

        binding.ivDrawText.setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(DRAW_TEXT_POSITION);
        });

        binding.ivDraw.setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(DRAW_POSITION);
        });

        binding.ivMine.setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(MINE_POSITION);
        });
    }

    private void setSelectedNavBarItem(int position) {
        binding.ivHome.setSelected(position == HOME_POSITION);
        binding.tvHome.setSelected(position == HOME_POSITION);
        binding.tvHome.setCompoundDrawablesWithIntrinsicBounds(
                null,
                ContextCompat.getDrawable(this, position == HOME_POSITION ? R.drawable.ic_bar_nav : R.drawable.transparent_drawable),
                null,
                null
        );
        binding.ivDrawText.setSelected(position == DRAW_TEXT_POSITION);
        binding.tvDrawText.setSelected(position == DRAW_TEXT_POSITION);
        binding.tvDrawText.setCompoundDrawablesWithIntrinsicBounds(
                null,
                ContextCompat.getDrawable(this, position == DRAW_TEXT_POSITION ? R.drawable.ic_bar_nav : R.drawable.transparent_drawable),
                null,
                null
        );
        binding.ivDraw.setSelected(position == DRAW_POSITION);
        binding.tvDraw.setSelected(position == DRAW_POSITION);
        binding.tvDraw.setCompoundDrawablesWithIntrinsicBounds(
                null,
                ContextCompat.getDrawable(this, position == DRAW_POSITION ? R.drawable.ic_bar_nav : R.drawable.transparent_drawable),
                null,
                null
        );
        binding.ivMine.setSelected(position == MINE_POSITION);
        binding.tvMine.setSelected(position == MINE_POSITION);
        binding.tvMine.setCompoundDrawablesWithIntrinsicBounds(
                null,
                ContextCompat.getDrawable(this, position == MINE_POSITION ? R.drawable.ic_bar_nav : R.drawable.transparent_drawable),
                null,
                null
        );
    }

    private void updateNewFragmentName() {
        binding.tvPreviousFragment.setText(previousFragmentName);

        int currentItem = binding.viewPager.getCurrentItem();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + currentItem);
        if (currentFragment != null) {
            previousFragmentName = currentFragment.getClass().getSimpleName().replace(FRAGMENT, "");
        } else {
            previousFragmentName = HOME;
        }
    }

    @Override
    public void onDrawTextSelected(Typeface typeface) {
        binding.tvPreviousFragment.setTypeface(typeface);
    }
}
