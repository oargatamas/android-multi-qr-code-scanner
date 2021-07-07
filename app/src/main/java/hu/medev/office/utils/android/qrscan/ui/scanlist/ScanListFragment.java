package hu.medev.office.utils.android.qrscan.ui.scanlist;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hu.medev.office.utils.android.databinding.FragmentScanListBinding;
import hu.medev.office.utils.android.qrscan.ScannerActivity;
import hu.medev.office.utils.android.qrscan.shared.BarcodeStorage;

public class ScanListFragment extends Fragment {

    private ScannerActivity activity;
    private BarcodeStorage barcodeStorage;
    private FragmentScanListBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentScanListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        activity = (ScannerActivity) requireActivity();
        barcodeStorage = activity.getBarcodeStorage();

        RecyclerView barCodeList = binding.rvBarCodeList;

        barCodeList.setLayoutManager(new LinearLayoutManager(activity));
        barCodeList.setAdapter(new ScanListAdapter(barcodeStorage.getScannedBarcodes()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}