package com.example.asian.issue5.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.asian.issue5.fragment.DrawFragment;
import com.example.asian.issue5.fragment.DrawTextFragment;
import com.example.asian.issue5.fragment.HomeFragment;
import com.example.asian.issue5.fragment.MineFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new HomeFragment();
            case 1: return new DrawTextFragment();
            case 2: return new DrawFragment();
            case 3: return new MineFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
