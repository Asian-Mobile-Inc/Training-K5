package com.example.asian.issue9.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asian.R;
import com.example.asian.issue9.customview.Weather;
import com.example.asian.issue9.model.Hour;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Hour> mHours;
    private FrameLayout mFlWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_issue9);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, 0);
            return insets;
        });
        mFlWeather = findViewById(R.id.flWeather);
        mHours = new ArrayList<>();
        initData();
        Weather weatherView = new Weather(this, mHours);
        mFlWeather.addView(weatherView);
    }

    private void initData() {
        Hour hour;
        hour = new Hour(1.0F, 0.25F, 0.0F, 6.25F, 6.1F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.1F, 6.3F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.3F, 6.1F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.1F, 6.5F);
        mHours.add(hour);
        hour = new Hour(4.25F, 0.25F, 0.0F, 6.5F, 7.0F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 7.0F, 6.5F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.5F, 6.7F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.7F, 6.7F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.7F, 6.5F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.5F, 6.8F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.8F, 6.3F);
        mHours.add(hour);
        hour = new Hour(9.25F, 0.25F, 0.0F, 6.3F, 6.5F);
        mHours.add(hour);
        hour = new Hour(5.8F, 3.0F, 0.0F, 6.5F, 6.3F);
        mHours.add(hour);
        hour = new Hour(4.0F, 1.0F, 0.0F, 6.3F, 6.5F);
        mHours.add(hour);
        hour = new Hour(4.0F, 6.0F, 0.0F, 6.5F, 7.0F);
        mHours.add(hour);
        hour = new Hour(8.25F, 6.0F, 0.0F, 7.0F, 7.3F);
        mHours.add(hour);
        hour = new Hour(8.25F, 6.0F, 0.0F, 7.3F, 7.6F);
        mHours.add(hour);
        hour = new Hour(2.0F, 1.0F, 0.0F, 7.6F, 7.5F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 7.5F, 7.3F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 7.3F, 7.0F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 7.0F, 6.8F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.8F, 7.0F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 7.0F, 6.8F);
        mHours.add(hour);
        hour = new Hour(0.0F, 0.0F, 0.0F, 6.8F, 6.5F);
        mHours.add(hour);
    }
}