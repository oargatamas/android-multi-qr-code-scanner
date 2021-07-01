package hu.medev.office.utils.android.qrscan;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import hu.medev.office.utils.android.databinding.ScannerFragmentBinding;

public class ScannerFragment extends Fragment {
    private static final String TAG = "ScannerFragment";

    private ScannerFragmentBinding binding;
    private SurfaceView cameraSurface;
    private CameraSource cameraSource;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = ScannerFragmentBinding.inflate(inflater, container, false);
        cameraSurface = binding.CameraSurface;
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    public void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void initialiseDetectorsAndSources() {

        Toast.makeText(requireActivity(), "Barcode scanner started", Toast.LENGTH_SHORT).show();

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(requireActivity())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        barcodeDetector.setProcessor(initialiseBarcodeProcessor());

        cameraSource = new CameraSource.Builder(requireActivity(), barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setAutoFocusEnabled(true)
                .build();

        cameraSurface.getHolder().addCallback(initialiseSurfaceHolderCallback());

    }

    private Detector.Processor<Barcode> initialiseBarcodeProcessor() {
        return new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(requireActivity(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    //Todo read out and collect detected QR codes.
                }
            }
        };
    }

    private SurfaceHolder.Callback initialiseSurfaceHolderCallback() {
        return new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(cameraSurface.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(requireActivity(), new
                                String[]{Manifest.permission.CAMERA}, 201);
                    }

                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        };
    }
}