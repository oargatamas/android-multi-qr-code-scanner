package hu.medev.office.utils.android.qrscan.shared;

import hu.medev.office.utils.android.qrscan.helper.ContextHolder;
import hu.medev.office.utils.android.qrscan.shared.storage.SharedPreferencesBarcodeStorage;

public class StorageFactory {

    public static BarcodeStorage barcodeStorage;

    public static BarcodeStorage getBarCodeStorage(){
        if(barcodeStorage == null){
            barcodeStorage =  new SharedPreferencesBarcodeStorage(ContextHolder.getContext());
        }
        return barcodeStorage;
    }
}
 