package issues10.Favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.asian.databinding.FragmentFavoriteBinding;

import java.util.List;

import issues10.Model.AppModel;
import issues10.Util.DBHandler;

public class FavoriteFragment extends Fragment {
    private FragmentFavoriteBinding mBinding;
    private DBHandler mDbHandler;
    private FavoriteAppAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDbHandler();
        initAdapter();
    }

    private void initDbHandler() {
        mDbHandler = new DBHandler(getContext());
    }

    private void initAdapter() {
        mAdapter = new FavoriteAppAdapter(getContext(), mDbHandler);
        mBinding.rvFavoriteApp.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvFavoriteApp.setAdapter(mAdapter);
        refresh();
    }

    public void refresh() {
        List<AppModel> favoriteAppModelLists = mDbHandler.readAppsByFavorite(1);
        mAdapter.submitList(favoriteAppModelLists);
    }
}
