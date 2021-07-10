package hu.medev.office.utils.android.qrscan.shared.data;

import java.time.LocalDateTime;
import java.util.Set;

public class BarcodeScan {
    private String id;
    private int numberOfItems;
    private LocalDateTime scanDate;
    private Set<String> barCodes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
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
