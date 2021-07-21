package hu.medev.office.utils.android.qrscan.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import hu.medev.office.utils.android.R;
import hu.medev.office.utils.android.qrscan.shared.BarcodeStorage;
import hu.medev.office.utils.android.qrscan.shared.StorageFactory;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;

public class NewScanTitleDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BarcodeStorage barcodeStorage = StorageFactory.getBarCodeStorage();

        EditText editText = new EditText(this.getContext());

        return new AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.dialog_new_scan_title_message))
                .setView(editText)
                .setPositiveButton(getString(R.string.button_save), (dialog, which) -> {
                    BarcodeScan scan = barcodeStorage.getCurrentScan();
                    scan.setTitle(editText.getText().toString());
                    barcodeStorage.addScan(scan);
                } )
                .setNegativeButton(getString(R.string.button_cancel), (dialog, which) ->  { dialog.cancel();} )
                .create();
    }

}
