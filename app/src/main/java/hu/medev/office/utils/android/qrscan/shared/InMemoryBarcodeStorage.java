package hu.medev.office.utils.android.qrscan.shared;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class InMemoryBarcodeStorage implements BarcodeStorage{

    private Set<String> storage;

    public InMemoryBarcodeStorage() {
        this.storage = new HashSet<>();
    }


    @Override
    public Collection<String> getScannedBarcodes() {
        return storage;
    }

    @Override
    public void addBarcode(String barcodeValue) {
        storage.add(barcodeValue);
    }

    @Override
    public void removeBarcode(String barcodeValue) {
        storage.remove(barcodeValue);
    }
}
