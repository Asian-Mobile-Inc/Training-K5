package com.example.asian.issue10.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asian.R;
import com.example.asian.issue10.adapter.AppAdapter;
import com.example.asian.issue10.model.App;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTvAll, mTvFavorite;
    private AppAdapter mAppAdapter;
    private List<App> mApps;
    private int selected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_issue10);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initData();
        initView();
        initListener();
    }

    private void initView() {
        mTvAll = findViewById(R.id.tvAll);
        mTvFavorite = findViewById(R.id.tvFavorite);
        RecyclerView mRvApp = findViewById(R.id.rvApp);
        mTvAll.setSelected(true);
        mTvAll.setTextColor(getColor(R.color.blue_1877F2));
        mTvFavorite.setSelected(false);
        mTvFavorite.setTextColor(getColor(R.color.gray_D3D3D3));
        mAppAdapter = new AppAdapter(this, mApps, new AppAdapter.OnAppSelectedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onAppSelected(List<App> apps) {
                mApps = apps;
                if (selected == 1) {
                    mAppAdapter.setApps(getFavoriteApp());
                    mAppAdapter.notifyDataSetChanged();
                }
            }
        });
        mRvApp.setAdapter(mAppAdapter);
        mRvApp.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initData() {
        mApps = new ArrayList<>();
        App app = new App(1, R.drawable.ic_app_store, getString(R.string.app_store), getString(R.string.your_holder_name), false);
        mApps.add(app);
        app = new App(2, R.drawable.ic_apple_music, getString(R.string.apple_music), getString(R.string.your_holder_name), false);
        mApps.add(app);
        app = new App(3, R.drawable.ic_facetime, getString(R.string.facetime), getString(R.string.your_holder_name), false);
        mApps.add(app);
        app = new App(4, R.drawable.ic_messenger, getString(R.string.messenger), getString(R.string.your_holder_name), false);
        mApps.add(app);
        app = new App(5, R.drawable.img_facebook, getString(R.string.facebook), getString(R.string.your_holder_name), false);
        mApps.add(app);
        app = new App(6, R.drawable.ic_voice, getString(R.string.voice_memos), getString(R.string.your_holder_name), false);
        mApps.add(app);
        app = new App(7, R.drawable.ic_netflix, getString(R.string.netflix), getString(R.string.your_holder_name), false);
        mApps.add(app);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void initListener() {
        mTvAll.setOnClickListener(v -> {
            mTvAll.setSelected(true);
            mTvAll.setTextColor(getColor(R.color.blue_1877F2));
            mTvFavorite.setSelected(false);
            mTvFavorite.setTextColor(getColor(R.color.gray_D3D3D3));
            mAppAdapter.setApps(mApps);
            mAppAdapter.notifyDataSetChanged();
            selected = 0;
        });
        mTvFavorite.setOnClickListener(v -> {
            mTvAll.setSelected(false);
            mTvAll.setTextColor(getColor(R.color.gray_D3D3D3));
            mTvFavorite.setSelected(true);
            mTvFavorite.setTextColor(getColor(R.color.blue_1877F2));
            if (selected == 0) {
                mAppAdapter.setApps(getFavoriteApp());
                mAppAdapter.notifyDataSetChanged();
            }
            selected = 1;
        });
    }

    private List<App> getFavoriteApp() {
        List<App> newApps = new ArrayList<>();
        for (int i = 0; i < mApps.size(); i++) {
            if (mApps.get(i).getFavorite()) {
                newApps.add(mApps.get(i));
            }
        }
        return newApps;
    }
}
