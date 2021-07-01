package hu.medev.office.utils.android.qrscan.shared;

import java.util.Collection;

public interface BarcodeStorage {

    Collection<String> getScannedBarcodes();

    String getBarcodeAt(int barcodeIndex);

    void addBarcode(String barcodeValue);

    void removeBarcode(String barcodeValue);

    void removeBarcodeAt(int barcodeIndex);
}
