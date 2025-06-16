package issues5.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asian.R;
import com.example.asian.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ArrayList<Home> lists;
    private static final String LUFFY = "#Luffy";
    private static final String NARUTO = "#Naruto";
    private static final String RONALDO = "#Ronaldo";
    private static final String MESSI = "#Messi";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initHomeLists();
        initHomeAdapter();
    }

    private void initHomeLists() {
        lists = new ArrayList<>();
        lists.add(new Home(LUFFY, R.drawable.img_home_luffy_1, 19425, false));
        lists.add(new Home(NARUTO, R.drawable.img_home_luffy_2, 98271, false));
        lists.add(new Home(RONALDO, R.drawable.img_home_luffy_3, 2353, false));
        lists.add(new Home(MESSI, R.drawable.img_home_luffy_4, 253, false));
    }

    private void initHomeAdapter() {
        HomeGridViewAdapter gridViewAdapter = new HomeGridViewAdapter(getContext(), lists);
        binding.gvHome.setAdapter(gridViewAdapter);
    }
}
