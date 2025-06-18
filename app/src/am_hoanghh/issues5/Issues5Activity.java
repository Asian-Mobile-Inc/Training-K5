package issues5;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ActivityIssues5Binding;

import issues5.DrawText.OnDrawTextSelectedListener;

public class Issues5Activity extends AppCompatActivity implements OnDrawTextSelectedListener {
    private ActivityIssues5Binding binding;
    private String previousFragmentName = "";

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
                setImageViewActive(position);
                updateNewFragmentName();
            }
        });
    }

    private void initListeners() {
        binding.ivHome.setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(0);
        });

        binding.ivDrawText.setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(1);
        });

        binding.ivDraw.setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(2);
        });

        binding.ivMine.setOnClickListener(v -> {
            binding.viewPager.setCurrentItem(3);
        });
    }

    private void setImageViewActive(int position) {
        Glide.with(this)
                .load(position == 0 ? R.drawable.ic_home_yellow : R.drawable.ic_home)
                .into(binding.ivHome);
        binding.ivYellowBarHome.setVisibility(position == 0 ? View.VISIBLE : View.GONE);

        Glide.with(this)
                .load(position == 1 ? R.drawable.ic_draw_text_yellow : R.drawable.ic_draw_text)
                .into(binding.ivDrawText);
        binding.ivYellowBarDrawText.setVisibility(position == 1 ? View.VISIBLE : View.GONE);

        Glide.with(this)
                .load(position == 2 ? R.drawable.ic_draw_yellow : R.drawable.ic_draw)
                .into(binding.ivDraw);
        binding.ivYellowBarDraw.setVisibility(position == 2 ? View.VISIBLE : View.GONE);

        Glide.with(this)
                .load(position == 3 ? R.drawable.ic_mine_yellow : R.drawable.ic_mine)
                .into(binding.ivMine);
        binding.ivYellowBarMine.setVisibility(position == 3 ? View.VISIBLE : View.GONE);
    }

    private void updateNewFragmentName() {
        binding.tvPreviousFragment.setText(previousFragmentName);

        int currentItem = binding.viewPager.getCurrentItem();
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("f" + currentItem);
        if (currentFragment != null) {
            previousFragmentName = currentFragment.getClass().getSimpleName().replace("Fragment", "");
        } else {
            previousFragmentName = "Home";
        }
    }

    @Override
    public void onDrawTextSelected(Typeface typeface) {
        binding.tvPreviousFragment.setTypeface(typeface);
    }
}
