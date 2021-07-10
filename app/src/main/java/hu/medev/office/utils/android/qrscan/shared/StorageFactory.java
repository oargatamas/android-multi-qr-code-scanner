package hu.medev.office.utils.android.qrscan.shared;

public class StorageFactory {

    public static BarcodeStorage barcodeStorage;

    public static BarcodeStorage getBarCodeStorage(){
        if(barcodeStorage == null){
            return new InMemoryBarcodeStorage();
        }
        return barcodeStorage;
    }
}
