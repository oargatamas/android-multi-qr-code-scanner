package hu.medev.office.utils.android.qrscan.shared.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class BarcodeScan {
    private String id;
    private LocalDateTime scanDate;
    private Set<String> barCodes = new HashSet<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getScanDate() {
        return scanDate;
    }

    public void setScanDate(LocalDateTime scanDate) {
        this.scanDate = scanDate;
    }

    public Set<String> getBarCodes() {
        return barCodes;
    }

    public void setBarCodes(Set<String> barCodes) {
        this.barCodes = barCodes;
    }
}
