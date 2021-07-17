package hu.medev.office.utils.android.qrscan.shared.sharedprefs;

import hu.medev.office.utils.android.qrscan.shared.BarcodeScanListener;
import hu.medev.office.utils.android.qrscan.shared.BarcodeStorage;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;

public class SharedPreferencesBarcodeStorage implements BarcodeStorage {
    @Override
    public BarcodeScan getCurrentScan() {
        return null;
    }

    @Override
    public BarcodeScan getNewScan() {
        return null;
    }

    @Override
    public BarcodeScan getScan(String s) {
        return null;
    }

    @Override
    public void addBarcode(BarcodeScan toScan, String barCode) {

    }

    @Override
    public void removeBarcode(BarcodeScan fromScan, String barCode) {

    }

    @Override
    public void addBarcode(String barCode) {

    }

    @Override
    public void removeBarcode(String barCode) {

    }

    @Override
    public void addScanListener(BarcodeScanListener listener) {

    }

    @Override
    public void removeScanListener(BarcodeScanListener listener) {

    }
}
