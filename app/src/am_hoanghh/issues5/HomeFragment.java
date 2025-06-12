package issues5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.asian.R;
import com.example.asian.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ArrayList<Home> lists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lists = new ArrayList<>();
        lists.add(new Home("#Luffy", R.drawable.img_home_luffy_1, 19425, false));
        lists.add(new Home("#Naruto", R.drawable.img_home_luffy_2, 98271, false));
        lists.add(new Home("#Ronaldo", R.drawable.img_home_luffy_3, 2353, false));
        lists.add(new Home("#Messi", R.drawable.img_home_luffy_4, 253, false));

        HomeGridViewAdapter gridViewAdapter = new HomeGridViewAdapter(getContext(), lists);
        binding.gvHome.setAdapter(gridViewAdapter);

        binding.ivDrawText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.drawTextFragment);
            }
        });

        binding.ivDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.drawFragment);
            }
        });

        binding.ivMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.mineFragment);
            }
        });
    }
}
