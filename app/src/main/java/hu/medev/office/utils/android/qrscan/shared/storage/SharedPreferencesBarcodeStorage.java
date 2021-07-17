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
import java.util.List;
import java.util.stream.Collectors;

import hu.medev.office.utils.android.R;
import hu.medev.office.utils.android.qrscan.helper.ContextHolder;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;

public class SharedPreferencesBarcodeStorage extends BaseBarcodeStorage {


    private static final String SHARED_PREF_NAME = "Medev-QrScans";
    private static final String COUNTER = "ScanCounter";
    private final SharedPreferences storage;
    private final Gson serializer;

    public SharedPreferencesBarcodeStorage() {
        super();
        this.serializer = getSerializer();
        this.storage = ContextHolder.getContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        this.counter = storage.getInt(COUNTER,1);
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

    @Override
    public List<BarcodeScan> getAllScans() {
        return storage.getAll().entrySet().stream()
                .filter(entry -> !entry.getKey().equals(COUNTER))
                .map(entry -> entry.getValue().toString())
                .map(json -> serializer.fromJson(json, BarcodeScan.class))
                .collect(Collectors.toList());
    }

    @Override
    public void removeScan(BarcodeScan scan) {
        storage.edit().remove(scan.getId()).apply();
    }

    @Override
    public void addScan(BarcodeScan scan) {
        saveScan(scan);
    }

    private void saveScan(BarcodeScan scan) {
        storage.edit()
                .putInt(COUNTER, counter)
                .putString(scan.getId(), serializer.toJson(scan))
                .apply();
    }

    private Gson getSerializer() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                    @Override
                    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext jsonContext) throws JsonParseException {

                        return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern(ContextHolder.getContext().getString(R.string.app_datetime_format)));
                    }

                })
                .registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                    @Override
                    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext jsonContext) {
                        return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(ContextHolder.getContext().getString(R.string.app_datetime_format))));
                    }
                })
                .create();
    }
}
