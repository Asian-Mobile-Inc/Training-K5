package issues5;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.asian.R;
import com.example.asian.databinding.FragmentDrawTextBinding;
import com.example.asian.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class DrawTextFragment extends Fragment {
    private FragmentDrawTextBinding binding;
    private ArrayList<DrawText> lists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDrawTextBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lists = new ArrayList<>();
        lists.add(new DrawText("Draw Anime", R.font.fuzzy_bubbles));
        lists.add(new DrawText("Draw Anime", R.font.fugaz_one));
        lists.add(new DrawText("Draw Anime", R.font.frijole));
        lists.add(new DrawText("Draw Anime", R.font.fredericka_the_great));
        lists.add(new DrawText("Draw Anime", R.font.fredericka_the_great));
        lists.add(new DrawText("Draw Anime", R.font.fontdiner_swanky));
        lists.add(new DrawText("Draw Anime", R.font.inter_thin));
        lists.add(new DrawText("Draw Anime", R.font.bigshot_one));
        lists.add(new DrawText("Draw Anime", R.font.boogaloo));
        lists.add(new DrawText("Draw Anime", R.font.scriptmtbold));
        lists.add(new DrawText("Draw Anime", R.font.montserrat_thin));
        lists.add(new DrawText("Draw Anime", R.font.mr_dafoe));

        DrawTextGridViewAdapter gridViewAdapter = new DrawTextGridViewAdapter(getContext(), lists);
        binding.gvDrawText.setAdapter(gridViewAdapter);

        binding.ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.homeFragment);
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
