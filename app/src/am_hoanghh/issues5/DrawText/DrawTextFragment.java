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
    private FragmentDrawTextBinding mBinding;
    private ArrayList<DrawText> mLists;
    private static final String DRAW_ANIME = "Draw Anime";
    private static final int SPAN_COUNT = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDrawTextBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDrawTextLists();
        initDrawTextAdapter();
    }

    private void initDrawTextLists() {
        mLists = new ArrayList<>();
        mLists.add(new DrawText(DRAW_ANIME, R.font.fuzzy_bubbles, true));
        mLists.add(new DrawText(DRAW_ANIME, R.font.fugaz_one));
        mLists.add(new DrawText(DRAW_ANIME, R.font.frijole));
        mLists.add(new DrawText(DRAW_ANIME, R.font.fredericka_the_great));
        mLists.add(new DrawText(DRAW_ANIME, R.font.fredericka_the_great));
        mLists.add(new DrawText(DRAW_ANIME, R.font.fontdiner_swanky));
        mLists.add(new DrawText(DRAW_ANIME, R.font.inter_thin));
        mLists.add(new DrawText(DRAW_ANIME, R.font.bigshot_one));
        mLists.add(new DrawText(DRAW_ANIME, R.font.boogaloo));
        mLists.add(new DrawText(DRAW_ANIME, R.font.scriptmtbold));
        mLists.add(new DrawText(DRAW_ANIME, R.font.montserrat_thin));
        mLists.add(new DrawText(DRAW_ANIME, R.font.mr_dafoe));
    }

    private void initDrawTextAdapter() {
        DrawTextAdapter drawTextAdapter = new DrawTextAdapter(typeface -> {
            if (getActivity() instanceof OnDrawTextSelectedListener) {
                ((OnDrawTextSelectedListener) getActivity()).onDrawTextSelected(typeface);
            }
        });

        mBinding.rvDrawText.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        mBinding.rvDrawText.setAdapter(drawTextAdapter);
        drawTextAdapter.submitList(mLists);
    }
}
