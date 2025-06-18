package issues5.DrawText;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.asian.R;
import com.example.asian.databinding.FragmentDrawTextBinding;

import java.util.ArrayList;

public class DrawTextFragment extends Fragment {
    private FragmentDrawTextBinding binding;
    private ArrayList<DrawText> lists;
    private static final String DRAW_ANIME = "Draw Anime";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDrawTextBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDrawTextLists();
        initDrawTextAdapter();
    }

    private void initDrawTextLists() {
        lists = new ArrayList<>();
        lists.add(new DrawText(DRAW_ANIME, R.font.fuzzy_bubbles, true));
        lists.add(new DrawText(DRAW_ANIME, R.font.fugaz_one));
        lists.add(new DrawText(DRAW_ANIME, R.font.frijole));
        lists.add(new DrawText(DRAW_ANIME, R.font.fredericka_the_great));
        lists.add(new DrawText(DRAW_ANIME, R.font.fredericka_the_great));
        lists.add(new DrawText(DRAW_ANIME, R.font.fontdiner_swanky));
        lists.add(new DrawText(DRAW_ANIME, R.font.inter_thin));
        lists.add(new DrawText(DRAW_ANIME, R.font.bigshot_one));
        lists.add(new DrawText(DRAW_ANIME, R.font.boogaloo));
        lists.add(new DrawText(DRAW_ANIME, R.font.scriptmtbold));
        lists.add(new DrawText(DRAW_ANIME, R.font.montserrat_thin));
        lists.add(new DrawText(DRAW_ANIME, R.font.mr_dafoe));
    }

    private void initDrawTextAdapter() {
        DrawTextAdapter drawTextAdapter = new DrawTextAdapter(getContext(), lists, typeface -> {
            if (getActivity() instanceof OnDrawTextSelectedListener) {
                ((OnDrawTextSelectedListener) getActivity()).onDrawTextSelected(typeface);
            }
        });

        binding.rvDrawText.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvDrawText.setAdapter(drawTextAdapter);
        drawTextAdapter.submitList(lists);
    }
}
