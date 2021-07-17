package hu.medev.office.utils.android.qrscan.shared;

import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;

public interface BarcodeScanListener {

    void onBarcodeScanned(BarcodeScan scan, String value);

    void onBarcodeRemoved(BarcodeScan scan, String barcodeValue);
}
