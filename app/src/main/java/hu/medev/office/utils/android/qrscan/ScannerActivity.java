package hu.medev.office.utils.android.qrscan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Collection;

import hu.medev.office.utils.android.R;
import hu.medev.office.utils.android.databinding.ActivityScannerBinding;
import hu.medev.office.utils.android.qrscan.shared.BarcodeScanListener;
import hu.medev.office.utils.android.qrscan.shared.BarcodeStorage;
import hu.medev.office.utils.android.qrscan.shared.StorageFactory;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;

public class  ScannerActivity extends AppCompatActivity {

    private ActivityScannerBinding binding;
    private BarcodeStorage barcodeStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_camera_scanner, R.id.navigation_scanned_barcodes_list)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_scanner);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        barcodeStorage = StorageFactory.getBarCodeStorage();
        barcodeStorage.getNewScan();
        barcodeStorage.addScanListener(getBarcodeListener());
    }

    private BarcodeScanListener getBarcodeListener(){
        return new BarcodeScanListener() {

            @Override
            public void onBarcodeScanned(BarcodeScan scan, String value) {
                updateBadges();
            }

            @Override
            public void onBarcodeRemoved(BarcodeScan scan, String barcodeValue) {
                updateBadges();
            }
        };
    }

    private void updateBadges(){
        BottomNavigationView navView = binding.navView;
        Collection<String> scannedBarcodes = barcodeStorage.getCurrentScan().getBarCodes();

        BadgeDrawable scanBadge = navView.getOrCreateBadge(R.id.navigation_scanned_barcodes_list);
        scanBadge.setNumber(scannedBarcodes.size());
        scanBadge.setVisible(scannedBarcodes.size() > 0);
    }

}