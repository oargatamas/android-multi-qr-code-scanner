package hu.medev.office.utils.android.qrscan.shared.memory;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import hu.medev.office.utils.android.qrscan.shared.BarcodeScanListener;
import hu.medev.office.utils.android.qrscan.shared.BarcodeStorage;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;

public class InMemoryBarcodeStorage implements BarcodeStorage {

    private final Collection<BarcodeScan> storage;
    private final List<BarcodeScanListener> listeners;
    private BarcodeScan selectedScan;
    private int counter;


    public InMemoryBarcodeStorage() {
        this.storage = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.counter = 0;
    }

    @Override
    public BarcodeScan getCurrentScan() {
        return selectedScan;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public BarcodeScan getNewScan() {
        BarcodeScan scan = new BarcodeScan();

        scan.setId("Scan #" + counter);
        scan.setScanDate(LocalDateTime.now());
        scan.setNumberOfItems(0);
        scan.setBarCodes(new HashSet<>());

        counter++;

        storage.add(scan);
        selectedScan = scan;

        return scan;
    }


    @Override
    public BarcodeScan getScan(String identifier) {
        for (BarcodeScan item: storage) {
            if(item.getId().equals(identifier)){
                selectedScan = item;
                return item;
            }
        }
        selectedScan = null;
        return null;
    }

    @Override
    public void addBarcode(BarcodeScan toScan, String barCode) {
        toScan.getBarCodes().add(barCode);
        for (BarcodeScanListener listener : listeners) {
            listener.onBarcodeScanned(toScan, barCode);
        }
    }

    @Override
    public void addBarcode(String barCode) {
        addBarcode(getCurrentScan(), barCode);
    }

    @Override
    public void removeBarcode(BarcodeScan fromScan, String barCode) {
        fromScan.getBarCodes().remove(barCode);
        for (BarcodeScanListener listener : listeners) {
            listener.onBarcodeRemoved(fromScan, barCode);
        }
    }

    @Override
    public void removeBarcode(String barCode) {
        removeBarcode(getCurrentScan(),barCode);
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
