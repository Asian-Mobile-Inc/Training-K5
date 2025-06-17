package com.example.asian.issue5.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asian.R;
import com.example.asian.issue5.adapter.PhotoAdapter;
import com.example.asian.issue5.model.Photo;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private ArrayList<Photo> mPhotos;
    private RecyclerView mRvHome;
    private PhotoAdapter mPhotoAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mRvHome = view.findViewById(R.id.rvHome);
        mRvHome.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mPhotos = new ArrayList<>();
        createPhotoList();
        mPhotoAdapter = new PhotoAdapter(getContext());
        mRvHome.setAdapter(mPhotoAdapter);
        mPhotoAdapter.submitList(mPhotos);
        return view;
    }

    private void createPhotoList() {
        mPhotos.add(new Photo(R.drawable.img_tanjiro, "#Tanjiro", 19425, true));
        mPhotos.add(new Photo(R.drawable.img_zenitsu, "#Zenitsu", 19425, false));
        mPhotos.add(new Photo(R.drawable.img_cover, "#Luffy", 19425, false));
        mPhotos.add(new Photo(R.drawable.img_naruto, "#Naruto", 19425, true));
    }
}
