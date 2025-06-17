package com.example.asian.issue5.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asian.R;
import com.google.android.material.tabs.TabLayout;

public class MineFragment extends Fragment {
    private TextView mTvMyAlbum, mTvFavourite;

    public MineFragment() {
        // Required empty public constructor
    }

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        mTvMyAlbum = view.findViewById(R.id.tvMyAlbum);
        mTvFavourite = view.findViewById(R.id.tvFavourite);
        mTvMyAlbum.setSelected(true);
        mTvMyAlbum.setTextColor(Color.BLACK);
        initListener();
        return view;
    }

    private void initListener() {
        mTvFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvFavourite.setSelected(true);
                mTvFavourite.setTextColor(Color.BLACK);
                mTvMyAlbum.setSelected(false);
                mTvMyAlbum.setTextColor(Color.GRAY);
            }
        });

        mTvMyAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvFavourite.setSelected(false);
                mTvFavourite.setTextColor(Color.GRAY);
                mTvMyAlbum.setSelected(true);
                mTvMyAlbum.setTextColor(Color.BLACK);
            }
        });
    }
}
