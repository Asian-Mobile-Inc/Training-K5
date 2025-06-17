package com.example.asian.issue5.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asian.R;
import com.example.asian.issue5.adapter.DrawTextAdapter;
import com.example.asian.issue5.model.DrawText;

import java.util.ArrayList;
import java.util.List;

public class DrawTextFragment extends Fragment {
    private List<DrawText> mDrawTexts;
    private DrawTextAdapter mDrawTextAdapter;
    private RecyclerView mRvDrawText;

    public DrawTextFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_draw_text, container, false);
        mRvDrawText = view.findViewById(R.id.rvDrawText);
        mDrawTexts = new ArrayList<>();
        mRvDrawText.setLayoutManager(new GridLayoutManager(getContext(), 2));
        createFont();
        mDrawTextAdapter = new DrawTextAdapter(getContext());
        mRvDrawText.setAdapter(mDrawTextAdapter);
        mDrawTextAdapter.submitList(mDrawTexts);
        return view;
    }

    private void createFont() {
        mDrawTexts.add(new DrawText(getString(R.string.font_fuzzybubbles), true));
        mDrawTexts.add(new DrawText(getString(R.string.font_fugazone), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_frijole), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_frederickathegreat), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_frederickathegreat), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_forte), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_forte), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_forte), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_inter), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_baloo2), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_bigshotone), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_boogaloo), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_brushscript), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_lexend), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_montserrat), false));
        mDrawTexts.add(new DrawText(getString(R.string.font_mrdafoe), false));
    }
}
