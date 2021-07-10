package hu.medev.office.utils.android.qrscan.shared;

import java.util.Collection;

public interface BarcodeStorage {

    Collection<String> getScannedBarcodes();

    void addBarcode(String barcodeValue);

    void removeBarcode(String barcodeValue);

    void addScanListener(BarcodeScanListener listener);

    void removeScanListener(BarcodeScanListener listener);
}
