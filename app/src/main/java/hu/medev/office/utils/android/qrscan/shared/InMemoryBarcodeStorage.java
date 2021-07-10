package hu.medev.office.utils.android.qrscan.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InMemoryBarcodeStorage implements BarcodeStorage{

    private final Set<String> storage;
    private final List<BarcodeScanListener> listeners;

    public InMemoryBarcodeStorage() {
        this.storage = new HashSet<>();
        this.listeners = new ArrayList<>();
    }


    @Override
    public Collection<String> getScannedBarcodes() {
        return storage;
    }

    @Override
    public void addBarcode(String barcodeValue) {
        storage.add(barcodeValue);
        for (BarcodeScanListener listener: listeners) {
            listener.onBarcodeScanned(barcodeValue);
        }
    }

    @Override
    public void removeBarcode(String barcodeValue) {
        storage.remove(barcodeValue);
        for (BarcodeScanListener listener: listeners) {
            listener.onBarcodeRemoved(barcodeValue);
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
