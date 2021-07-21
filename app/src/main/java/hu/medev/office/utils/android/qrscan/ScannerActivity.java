package hu.medev.office.utils.android.qrscan;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
import hu.medev.office.utils.android.qrscan.helper.ContextHolder;
import hu.medev.office.utils.android.qrscan.shared.BarcodeScanListener;
import hu.medev.office.utils.android.qrscan.shared.BarcodeStorage;
import hu.medev.office.utils.android.qrscan.shared.StorageFactory;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;
import hu.medev.office.utils.android.qrscan.ui.dialog.NewScanTitleDialog;

public class ScannerActivity extends AppCompatActivity {

    private ActivityScannerBinding binding;
    private BarcodeStorage barcodeStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextHolder.initial(getApplicationContext());

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
        barcodeStorage.addScanListener(getBarcodeListener());
        if (getIntent().hasExtra(getString(R.string.intent_scan_id))) {
            String scanId = getIntent().getStringExtra(getString(R.string.intent_scan_id));
            barcodeStorage.getScan(scanId);
        } else {
            barcodeStorage.getNewScan();
        }

        getSupportActionBar().setTitle(barcodeStorage.getCurrentScan().getTitle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.scanner_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.change_scan_title) {
            new NewScanTitleDialog().show(getSupportFragmentManager(), "NEW_SCAN_TITLE_DIALOG");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private BarcodeScanListener getBarcodeListener() {
        return new BarcodeScanListener() {

            @Override
            public void onBarcodeScanned(BarcodeScan scan, String value) {
                updateView();
            }

            @Override
            public void onBarcodeRemoved(BarcodeScan scan, String barcodeValue) {
                updateView();
            }

            @Override
            public void onBarcodeScanChanged(BarcodeScan scan) {
                updateView();
            }
        };
    }

    private void updateView() {

        BottomNavigationView navView = binding.navView;
        BarcodeScan scan = barcodeStorage.getCurrentScan();
        Collection<String> scannedBarcodes = scan.getBarCodes();

        getSupportActionBar().setTitle(scan.getTitle());

        BadgeDrawable scanBadge = navView.getOrCreateBadge(R.id.navigation_scanned_barcodes_list);
        scanBadge.setNumber(scannedBarcodes.size());
        scanBadge.setVisible(scannedBarcodes.size() > 0);
    }

}