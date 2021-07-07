package hu.medev.office.utils.android.qrscan.ui.camera;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

import hu.medev.office.utils.android.databinding.FragmentCameraScannerBinding;

public class CameraScannerFragment extends Fragment {

    private static final String TAG = "ScannerFragment";


    private CodeScanner mCodeScanner;

    private FragmentCameraScannerBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCameraScannerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        CodeScannerView scannerView = binding.scannerView;
        mCodeScanner = new CodeScanner(requireContext(), scannerView);
        mCodeScanner.setDecodeCallback(result -> {
            requireActivity().runOnUiThread(() -> Toast.makeText(requireActivity(), result.getText(), Toast.LENGTH_SHORT).show());
        });
        scannerView.setOnClickListener(view -> mCodeScanner.startPreview());

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}