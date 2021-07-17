package hu.medev.office.utils.android.qrscan.shared.storage;

import java.util.HashMap;
import java.util.Map;

import hu.medev.office.utils.android.qrscan.shared.BarcodeScanListener;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;

public class InMemoryBarcodeStorage extends BaseBarcodeStorage {

    private final Map<String, BarcodeScan> storage;

    public InMemoryBarcodeStorage() {
        super();
        this.storage = new HashMap<>();
        this.counter = 1;
    }

    @Override
    public BarcodeScan getNewScan() {
        BarcodeScan scan = super.getNewScan();
        storage.put(scan.getId(), scan);

        return scan;
    }


    @Override
    public BarcodeScan getScan(String identifier) {
        for (Map.Entry<String, BarcodeScan> item : storage.entrySet()) {
            if (item.getKey().equals(identifier)) {
                selectedScan = item.getValue();
                return selectedScan;
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
        removeBarcode(getCurrentScan(), barCode);
    }
}
