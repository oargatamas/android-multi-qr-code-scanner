package hu.medev.office.utils.android.qrscan.shared;

import java.util.Set;

public interface BarcodeStorage {

    Set<String> getScannedBarcodes();

    void addBarcode(String barcodeValue);

    void removeBarcode(String barcodeValue);

    void removeBarcodeAt(int barcodeIndex);
}
