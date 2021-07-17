package hu.medev.office.utils.android.qrscan.shared.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import hu.medev.office.utils.android.qrscan.shared.BarcodeStorage;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;

public class SharedPreferencesBarcodeStorage extends BaseBarcodeStorage implements BarcodeStorage {

    private static final String SHARED_PREF_NAME = "Medev-QrScans";
    private final SharedPreferences storage;
    private final Gson serializer;

    public SharedPreferencesBarcodeStorage(Context context) {
        super();
        this.serializer = new Gson();
        this.counter = 1;
        this.storage = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }


    @Override
    public BarcodeScan getNewScan() {
        BarcodeScan scan = super.getNewScan();
        saveScan(scan);
        return scan;
    }

    @Override
    public BarcodeScan getScan(String scanId) {
        if(storage.contains(scanId)){
            return serializer.fromJson(storage.getString(scanId,""), BarcodeScan.class);
        }
        return null;
    }

    @Override
    public void addBarcode(BarcodeScan toScan, String barCode) {
        toScan.getBarCodes().add(barCode);
        saveScan(toScan);
    }

    @Override
    public void removeBarcode(BarcodeScan fromScan, String barCode) {
        fromScan.getBarCodes().remove(barCode);
        saveScan(fromScan);
    }

    @Override
    public void addBarcode(String barCode) {
        addBarcode(getCurrentScan(),barCode);
    }

    @Override
    public void removeBarcode(String barCode) {
        removeBarcode(getCurrentScan(),barCode);
    }

    private void saveScan(BarcodeScan scan){
        storage.edit()
                .putString(scan.getId(),serializer.toJson(scan))
                .apply();
    }
}
