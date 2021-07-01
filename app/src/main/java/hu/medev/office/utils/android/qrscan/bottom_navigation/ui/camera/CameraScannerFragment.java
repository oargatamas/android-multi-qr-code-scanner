package hu.medev.office.utils.android.qrscan.bottom_navigation.ui.camera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import hu.medev.office.utils.android.databinding.FragmentCameraScannerBinding;

public class CameraScannerFragment extends Fragment {

    private CameraScannerViewModel dashboardViewModel;
    private FragmentCameraScannerBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(CameraScannerViewModel.class);

        binding = FragmentCameraScannerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}