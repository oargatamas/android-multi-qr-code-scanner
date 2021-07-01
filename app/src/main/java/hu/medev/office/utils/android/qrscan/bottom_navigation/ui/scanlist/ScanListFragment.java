package hu.medev.office.utils.android.qrscan.bottom_navigation.ui.scanlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import hu.medev.office.utils.android.databinding.FragmentScanListBinding;

public class ScanListFragment extends Fragment {

    private ScanListViewModel notificationsViewModel;
    private FragmentScanListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(ScanListViewModel.class);

        binding = FragmentScanListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}