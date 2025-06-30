package issues10;

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

import java.util.ArrayList;
import java.util.List;

public class AllFragment extends Fragment {
    private FragmentAllBinding mBinding;
    private List<App> appLists;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentAllBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initAdapter();
    }

    private void initAdapter() {
        AllAppAdapter adapter = new AllAppAdapter(getContext());
        mBinding.rvAllApp.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvAllApp.setAdapter(adapter);
        initAppLists();
        adapter.submitList(appLists);
    }

    private void initAppLists() {
        appLists = new ArrayList<>();
        appLists.add(new App(R.drawable.ic_app_store, "App Store"));
        appLists.add(new App(R.drawable.ic_apple_music, "Apple music"));
        appLists.add(new App(R.drawable.ic_facetime, "Facetime"));
        appLists.add(new App(R.drawable.ic_messenger, "Messenger"));
        appLists.add(new App(R.drawable.img_facebook, "Facebook"));
        appLists.add(new App(R.drawable.ic_voice_memos, "Voice memos"));
        appLists.add(new App(R.drawable.ic_netflix, "Netflix"));
    }
}
