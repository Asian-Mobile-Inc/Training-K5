package com.example.asian.issue5;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asian.R;
import com.example.asian.issue5.adapter.ViewPagerAdapter;
import com.example.asian.issue5.fragment.DrawFragment;
import com.example.asian.issue5.fragment.DrawTextFragment;
import com.example.asian.issue5.fragment.HomeFragment;
import com.example.asian.issue5.fragment.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBNV;
    private TextView mTvPrevious;
    private String mStr = "";
    private ViewPager2 mVpConstraint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mBNV = findViewById(R.id.bnvHome);
        mTvPrevious = findViewById(R.id.tvPrevious);
        mVpConstraint = findViewById(R.id.vpConstraint);
        mVpConstraint.setAdapter(new ViewPagerAdapter(this));
        initListener();
    }

    private void initListener() {
        mBNV.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        mVpConstraint.setCurrentItem(0);
                        break;
                    case R.id.draw_text:
                        mVpConstraint.setCurrentItem(1);
                        break;
                    case R.id.draw:
                        mVpConstraint.setCurrentItem(2);
                        break;
                    case R.id.mine:
                        mVpConstraint.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

        mVpConstraint.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        if (!mStr.equals(getString(R.string.home))) {
                            setTextPrevious(mStr);
                        }
                        mStr = getString(R.string.home);
                        mBNV.getMenu().getItem(0).setChecked(true);
                        break;
                    case 1:
                        if (!mStr.equals(getString(R.string.draw_text))) {
                            setTextPrevious(mStr);
                        }
                        mStr = getString(R.string.draw_text);
                        mBNV.getMenu().getItem(1).setChecked(true);
                        break;
                    case 2:
                        if (!mStr.equals(getString(R.string.draw))) {
                            setTextPrevious(mStr);
                        }
                        mStr = getString(R.string.draw);
                        mBNV.getMenu().getItem(2).setChecked(true);
                        break;
                    case 3:
                        if (!mStr.equals(getString(R.string.mine))) {
                            setTextPrevious(mStr);
                        }
                        mStr = getString(R.string.mine);
                        mBNV.getMenu().getItem(3).setChecked(true);
                        break;
                }
            }
        });
    }

    private void setTextPrevious(String text) {
        if (!mStr.isEmpty()) {
            mTvPrevious.setTextColor(Color.BLACK);
            mTvPrevious.setText(text);
        }
    }
}
