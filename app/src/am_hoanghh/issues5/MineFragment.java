package issues5;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.asian.R;
import com.example.asian.databinding.FragmentDrawBinding;
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

        binding.tvMyAlbum.setOnClickListener(v -> {
//            Glide.with(this)
//                    .load(R.drawable.bg_nav_bar_textview_yellow)
//                    .into(new CustomTarget<Drawable>() {
//                        @Override
//                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                            binding.tvMyAlbum.setBackgroundResource(R.drawable.bg_nav_bar_textview_yellow);
//                        }
//
//                        @Override
//                        public void onLoadCleared(@Nullable Drawable placeholder) {
//
//                        }
//                    });
            binding.tvMyAlbum.setBackgroundResource(R.drawable.bg_nav_bar_textview_yellow);

            binding.tvFavourite.setBackgroundResource(R.drawable.bg_nav_bar_textview);
        });

        binding.tvFavourite.setOnClickListener(v -> {
            binding.tvMyAlbum.setBackgroundResource(R.drawable.bg_nav_bar_textview);

            binding.tvFavourite.setBackgroundResource(R.drawable.bg_nav_bar_textview_yellow);
        });

        binding.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.homeFragment);
            }
        });

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
    }
}
