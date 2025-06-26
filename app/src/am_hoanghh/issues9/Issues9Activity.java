package issues9;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asian.databinding.ActivityIssues9Binding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Issues9Activity extends AppCompatActivity {
    private ActivityIssues9Binding binding;
    private static final List<Double> DATA_RAINS = Arrays.asList(8.0, 7.0, 8.0, 0.0, 5.0, 2.0, 16.2, 8.1, 9.2, 7.2, 9.3, 10.5, 12.6, 8.7, 8.2, 6.3);
    private static final List<Double> DATA_WINTRYS = Arrays.asList(2.0, 0.0, 3.0, 6.0, 10.0, 2.0, 3.2, 8.0, 1.2, 6.8, 2.0, 4.5, 0.0, 9., 1.2, 3.5);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIssues9Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] graphArray = {8, 7, 8, 2, 5, 2, 18, 8, 9, 7, 9, 10, 12, 8, 8, 6, 8, 9, 11, 7, 4, 5, 8, 10};

        List<Hour> hourLists = Arrays.asList(
                new Hour(2f, 0.5f, 0, 22),
                new Hour(0, 0, 0, 23),
                new Hour(0, 0, 0, 21),
                new Hour(4.5f, 1, 0, 22),
                new Hour(0, 0, 0, 22.5f),
                new Hour(0, 0, 0, 23),
                new Hour(0, 0, 0, 23.5f),
                new Hour(0, 0, 0, 24),
                new Hour(9.5f, 1.5f, 0, 23.5f),
                new Hour(6, 3, 0, 23),
                new Hour(4, 2, 0, 22.5f),
                new Hour(4, 6, 0, 22),
                new Hour(8.5f, 6, 0, 23),
                new Hour(2, 1, 0, 24),
                new Hour(0, 0, 0, 23.5f),
                new Hour(0, 0, 0, 22),
                new Hour(0, 0, 0, 22.5f),
                new Hour(0, 2.5f, 0, 23),
                new Hour(3.5f, 6, 0, 22.5f),
                new Hour(5, 3.5f, 0, 22),
                new Hour(0, 4.5f, 0, 21.7f),
                new Hour(0, 0, 0, 21),
                new Hour(5, 0, 0, 21),
                new Hour(10, 7, 0, 20)
        );

        Weather weather = new Weather(this, hourLists);
        binding.rlWeather.addView(weather);


    }
}
