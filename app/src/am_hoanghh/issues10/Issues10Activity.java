package issues10;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.asian.databinding.ActivityIssues10Binding;

import java.util.ArrayList;
import java.util.List;

public class Issues10Activity extends AppCompatActivity {
    private ActivityIssues10Binding mBinding;
    private static final int ALL_POSITION = 0;
    private static final int FAVORITE_POSITION = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityIssues10Binding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        initListeners();
        initViewPager();
//        try {
//            getallapps(mBinding.getRoot());
//        } catch (PackageManager.NameNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void getallapps(View view) throws PackageManager.NameNotFoundException {
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        // get list of all the apps installed
        List<ResolveInfo> ril = getPackageManager().queryIntentActivities(mainIntent, 0);
        List<String> componentList = new ArrayList<String>();
        String name = null;

        // get size of ril and create a list
        for (ResolveInfo ri : ril) {
            if (ri.activityInfo != null) {
                // get package
                Resources res = getPackageManager().getResourcesForApplication(ri.activityInfo.applicationInfo);
                // if activity label res is found
                if (ri.activityInfo.labelRes != 0) {
                    name = res.getString(ri.activityInfo.labelRes);
                } else {
                    name = ri.activityInfo.applicationInfo.loadLabel(
                            getPackageManager()).toString();
                }
                Log.d("debug", name);
            }
        }
    }

    private void initListeners() {
        mBinding.btnAll.setOnClickListener(v -> {
            mBinding.viewPager.setCurrentItem(ALL_POSITION);
        });
        mBinding.btnFavorite.setOnClickListener(v -> {
            mBinding.viewPager.setCurrentItem(FAVORITE_POSITION);
        });
    }

    private void initViewPager() {
        mBinding.viewPager.setAdapter(new MyPagerAdapter(this));
        mBinding.viewPager.setOffscreenPageLimit(2);
        mBinding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setSelectedItem(position);
            }
        });
    }

    private void setSelectedItem(int position) {
        mBinding.btnAll.setSelected(position == ALL_POSITION);
        mBinding.btnFavorite.setSelected(position == FAVORITE_POSITION);
    }
}
