package hu.medev.office.utils.android.qrscan.ui.scanlist;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import hu.medev.office.utils.android.databinding.FragmentScanListBinding;
import hu.medev.office.utils.android.qrscan.ScannerActivity;

public class ScanListFragment extends Fragment {

    private FragmentScanListBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentScanListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ScannerActivity activity = (ScannerActivity) requireActivity();

        String codeList = activity.getBarcodeStorage().getScannedBarcodes().stream().reduce("",(str1,str2) -> str1 + "\n\n\n" + str2);

        binding.textNotifications.setText(codeList);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}