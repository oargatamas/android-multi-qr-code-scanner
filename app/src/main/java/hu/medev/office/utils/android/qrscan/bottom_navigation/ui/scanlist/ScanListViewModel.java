package hu.medev.office.utils.android.qrscan.bottom_navigation.ui.scanlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScanListViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ScanListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}