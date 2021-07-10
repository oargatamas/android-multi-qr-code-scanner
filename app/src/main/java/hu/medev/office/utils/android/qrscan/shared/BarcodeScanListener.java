package hu.medev.office.utils.android.qrscan.shared;

public interface BarcodeScanListener {

    void onBarcodeScanned(String barcodeValue);

    void onBarcodeRemoved(String barcodeValue);
}
