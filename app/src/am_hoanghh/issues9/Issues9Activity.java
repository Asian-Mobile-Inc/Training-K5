package issues9;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.asian.R;
import com.example.asian.databinding.ActivityIssues9Binding;

import java.util.Arrays;
import java.util.List;

public class Issues9Activity extends AppCompatActivity {
    private ActivityIssues9Binding mBinding;
    private List<Hour> mHourLists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityIssues9Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initHourLists();
        Weather weather = new Weather(this, mHourLists, mOnTouchChartListener);
        mBinding.rlWeather.addView(weather);
    }

    private final OnTouchChartListener mOnTouchChartListener = new OnTouchChartListener() {
        @Override
        public void onTouchListener(int selectedIndex) {
            if (selectedIndex >= 0) {
                Hour item = mHourLists.get(selectedIndex);
                mBinding.tvRainFall.setText(getString(R.string.textview_text_number_mm, item.getmRain()));

                // Draw status weather icon
                int resourceId = 0;
                if (item.getmRain() > 0) {
                    resourceId = R.drawable.ic_rain_big;
                } else if (item.getmWintry() > 0) {
                    resourceId = R.drawable.ic_cloud_big;
                } else {
                    resourceId = R.drawable.ic_sun_big;
                }
                mBinding.ivStatusWeather.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext())
                        .load(resourceId)
                        .override(34, 34)
                        .into(mBinding.ivStatusWeather);

                mBinding.tvTemperatureHour.setText(getString(R.string.textview_text_number_temperature, item.getmTemperature()));
                mBinding.tvDescription.setText("");
                mBinding.tvHour.setText(getString(R.string.textview_text_hour, selectedIndex));
            } else {
                mBinding.tvRainFall.setText("");
                mBinding.ivStatusWeather.setVisibility(View.GONE);
                mBinding.tvTemperatureHour.setText("");
                mBinding.tvDescription.setText(getString(R.string.textview_text_total_in_the_past_24_hours));
                mBinding.tvHour.setText("");
            }
        }
    };

    private void initHourLists() {
        mHourLists = Arrays.asList(
                new Hour(2f, 0.5f, 0, 22),
                new Hour(0, 0, 0, 23),
                new Hour(0, 0, 0, 21),
                new Hour(4.5f, 1, 0, 22),
                new Hour(5, 3, 0, 22.5f),
                new Hour(2, 4, 0, 23),
                new Hour(5, 5, 0, 23.5f),
                new Hour(2, 8, 0, 24),
                new Hour(9.5f, 1.5f, 0, 23.5f),
                new Hour(6, 3, 0, 23),
                new Hour(4, 2, 0, 22.5f),
                new Hour(4, 6, 0, 22),
                new Hour(8.5f, 6, 0, 23),
                new Hour(2, 1, 0, 24),
                new Hour(0, 0, 0, 23.5f),
                new Hour(0, 0.5f, 0, 22),
                new Hour(0, 0, 0, 22.5f),
                new Hour(0, 2.5f, 0, 23),
                new Hour(3.5f, 6, 0, 22.5f),
                new Hour(5, 3.5f, 0, 22),
                new Hour(0, 4.5f, 0, 21.7f),
                new Hour(0, 0, 0, 21),
                new Hour(5, 0, 0, 21),
                new Hour(10, 7, 0, 20)
        );
    }
}
