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
    private FragmentMineBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMineBinding.inflate(inflater, container, false);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListeners();
    }

    private void initListeners() {
        mBinding.tvMyAlbum.setOnClickListener(v -> {
            mBinding.tvMyAlbum.setBackgroundResource(R.drawable.bg_nav_bar_textview_yellow);
            mBinding.tvFavourite.setBackgroundResource(R.drawable.bg_nav_bar_textview);
        });

        mBinding.tvFavourite.setOnClickListener(v -> {
            mBinding.tvMyAlbum.setBackgroundResource(R.drawable.bg_nav_bar_textview);
            mBinding.tvFavourite.setBackgroundResource(R.drawable.bg_nav_bar_textview_yellow);
        });
    }
}
