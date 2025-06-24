package issues5.Mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.asian.R;
import com.example.asian.databinding.FragmentMineBinding;

public class MineFragment extends Fragment {
    private FragmentMineBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMineBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListeners();
    }

    private void initListeners() {
        binding.tvMyAlbum.setOnClickListener(v -> {
            binding.tvMyAlbum.setBackgroundResource(R.drawable.bg_nav_bar_textview_yellow);
            binding.tvFavourite.setBackgroundResource(R.drawable.bg_nav_bar_textview);
        });

        binding.tvFavourite.setOnClickListener(v -> {
            binding.tvMyAlbum.setBackgroundResource(R.drawable.bg_nav_bar_textview);
            binding.tvFavourite.setBackgroundResource(R.drawable.bg_nav_bar_textview_yellow);
        });
    }
}
