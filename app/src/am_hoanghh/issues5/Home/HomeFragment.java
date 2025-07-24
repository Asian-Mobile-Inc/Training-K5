package issues5.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.asian.R;
import com.example.asian.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding mBinding;
    private ArrayList<Home> mLists;
    private static final String LUFFY = "#Luffy";
    private static final String NARUTO = "#Naruto";
    private static final String RONALDO = "#Ronaldo";
    private static final String MESSI = "#Messi";
    private static final int LIKE_19425 = 19425;
    private static final int LIKE_98271 = 98271;
    private static final int LIKE_2353 = 2353;
    private static final int LIKE_253 = 253;
    private static final int SPAN_COUNT = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initHomeLists();
        initHomeAdapter();
    }

    private void initHomeLists() {
        mLists = new ArrayList<>();
        mLists.add(new Home(LUFFY, R.drawable.img_home_luffy_1, LIKE_19425, false));
        mLists.add(new Home(NARUTO, R.drawable.img_home_luffy_2, LIKE_98271, false));
        mLists.add(new Home(RONALDO, R.drawable.img_home_luffy_3, LIKE_2353, false));
        mLists.add(new Home(MESSI, R.drawable.img_home_luffy_4, LIKE_253, false));
    }

    private void initHomeAdapter() {
        HomeAdapter homeAdapter = new HomeAdapter();
        mBinding.rvHome.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        mBinding.rvHome.setAdapter(homeAdapter);
        homeAdapter.submitList(mLists);
    }
}
