package hu.medev.office.utils.android.qrscan.shared.data;

import java.time.LocalDateTime;
import java.util.List;

public class BarcodeScan {
    private String scanTitle;
    private int numberOfItems;
    private LocalDateTime scanDate;
    private List<String> barCodes;

    public String getScanTitle() {
        return scanTitle;
    }

    public void setScanTitle(String scanTitle) {
        this.scanTitle = scanTitle;
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

    public List<String> getBarCodes() {
        return barCodes;
    }

    public void setBarCodes(List<String> barCodes) {
        this.barCodes = barCodes;
    }
}
