package hu.medev.office.utils.android.qrscan.shared;

import java.util.List;

import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;

public interface ScanStorage<Identifier> {

    BarcodeScan getCurrentScan();

    BarcodeScan getNewScan();

    BarcodeScan getScan(Identifier identifier);

    void addBarcode(BarcodeScan toScan, String barCode);

    void removeBarcode(BarcodeScan fromScan, String barCode);

    void addBarcode(String barCode);

    void removeBarcode(String barCode);

    void addScanListener(BarcodeScanListener listener);

    void removeScanListener(BarcodeScanListener listener);

    List<BarcodeScan> getAllScans();

    void removeScan(BarcodeScan scan);

    void addScan(BarcodeScan scan);
}
