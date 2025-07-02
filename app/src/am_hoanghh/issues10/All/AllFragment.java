package issues10.All;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.asian.R;
import com.example.asian.databinding.FragmentAllBinding;

import java.util.List;

import issues10.Model.AppModel;
import issues10.Util.DBHandler;

public class AllFragment extends Fragment {
    private FragmentAllBinding mBinding;
    private DBHandler mDbHandler;
    private AllAppAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAllBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDbHandler();
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new AllAppAdapter(getContext(), mDbHandler);
        mBinding.rvAllApp.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvAllApp.setAdapter(mAdapter);
        refresh();
    }

    public void refresh() {
        List<AppModel> appModelLists = mDbHandler.readApps();
        mAdapter.submitList(appModelLists);
    }

    private void initDbHandler() {
        mDbHandler = new DBHandler(getContext());
    }

    private void initAppLists() {
        mDbHandler.addNewApp(getString(R.string.app_item_text_app_store), R.drawable.ic_app_store, 0);
        mDbHandler.addNewApp(getString(R.string.app_item_text_apple_music), R.drawable.ic_apple_music, 0);
        mDbHandler.addNewApp(getString(R.string.app_item_text_facetime), R.drawable.ic_facetime, 0);
        mDbHandler.addNewApp(getString(R.string.app_item_text_messenger), R.drawable.ic_messenger, 0);
        mDbHandler.addNewApp(getString(R.string.app_item_text_facebook), R.drawable.img_facebook, 0);
        mDbHandler.addNewApp(getString(R.string.app_item_text_voice_memos), R.drawable.ic_voice_memos, 0);
        mDbHandler.addNewApp(getString(R.string.app_item_text_netflix), R.drawable.ic_netflix, 0);
    }
}
