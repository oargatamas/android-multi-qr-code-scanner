package hu.medev.office.utils.android.qrscan.shared.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import hu.medev.office.utils.android.qrscan.shared.BarcodeStorage;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;

public class SharedPreferencesBarcodeStorage extends BaseBarcodeStorage implements BarcodeStorage {

    private static final String SHARED_PREF_NAME = "Medev-QrScans";
    private final SharedPreferences storage;
    private final Gson serializer;

    public SharedPreferencesBarcodeStorage(Context context) {
        super();
        this.serializer = getSerializer();
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
        if (storage.contains(scanId)) {
            return serializer.fromJson(storage.getString(scanId, ""), BarcodeScan.class);
        }
        return null;
    }

    @Override
    public void addBarcode(BarcodeScan toScan, String barCode) {
        toScan.getBarCodes().add(barCode);
        saveScan(toScan);
        super.addBarcode(toScan, barCode);
    }

    @Override
    public void removeBarcode(BarcodeScan fromScan, String barCode) {
        fromScan.getBarCodes().remove(barCode);
        saveScan(fromScan);
        super.removeBarcode(fromScan, barCode);
    }

    @Override
    public void addBarcode(String barCode) {
        addBarcode(getCurrentScan(), barCode);
    }

    @Override
    public void removeBarcode(String barCode) {
        removeBarcode(getCurrentScan(), barCode);
    }

    private void saveScan(BarcodeScan scan) {
        storage.edit()
                .putString(scan.getId(), serializer.toJson(scan))
                .apply();
    }

    private Gson getSerializer() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

                        return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    }

                })
                .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                    @Override
                    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                    }
                })
                .create();
    }
}
