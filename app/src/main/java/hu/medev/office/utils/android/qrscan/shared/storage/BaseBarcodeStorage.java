package hu.medev.office.utils.android.qrscan.shared.storage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import hu.medev.office.utils.android.qrscan.shared.BarcodeScanListener;
import hu.medev.office.utils.android.qrscan.shared.BarcodeStorage;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;

public abstract class BaseBarcodeStorage implements BarcodeStorage {

    protected List<BarcodeScanListener> listeners;
    protected BarcodeScan selectedScan;
    protected int counter;

    public BaseBarcodeStorage() {
        listeners = new ArrayList<>();
    }

    @Override
    public BarcodeScan getCurrentScan() {
        if(selectedScan == null){
            return getNewScan();
        }
        return selectedScan;
    }


    @Override
    public BarcodeScan getNewScan() {
        BarcodeScan scan = new BarcodeScan();

        scan.setId("Scan #" + counter);
        scan.setScanDate(LocalDateTime.now());
        scan.setNumberOfItems(0);
        scan.setBarCodes(new HashSet<>());

        counter++;

        selectedScan = scan;

        return scan;
    }

    @Override
    public void addBarcode(BarcodeScan toScan, String barCode) {
        for (BarcodeScanListener listener : listeners) {
            listener.onBarcodeScanned(toScan, barCode);
        }
    }

    @Override
    public void removeBarcode(BarcodeScan fromScan, String barCode) {
        for (BarcodeScanListener listener : listeners) {
            listener.onBarcodeRemoved(fromScan, barCode);
        }
    }

    @Override
    public void addScanListener(BarcodeScanListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeScanListener(BarcodeScanListener listener) {
        listeners.remove(listener);
    }
}
