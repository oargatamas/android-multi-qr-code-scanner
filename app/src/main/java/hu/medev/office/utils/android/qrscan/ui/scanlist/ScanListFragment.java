package hu.medev.office.utils.android.qrscan.ui.scanlist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.format.DateTimeFormatter;

import hu.medev.office.utils.android.R;
import hu.medev.office.utils.android.databinding.FragmentScanListBinding;
import hu.medev.office.utils.android.qrscan.ScannerActivity;
import hu.medev.office.utils.android.qrscan.shared.BarcodeStorage;
import hu.medev.office.utils.android.qrscan.shared.StorageFactory;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;
import hu.medev.office.utils.android.qrscan.ui.utils.SwipeToDeleteCallback;

public class ScanListFragment extends Fragment {

    private FragmentScanListBinding binding;
    private RecyclerView scanList;
    private ScanListAdapter adapter;
    private View rootView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentScanListBinding.inflate(inflater, container, false);
        rootView = binding.getRoot();

        ScannerActivity activity = (ScannerActivity) requireActivity();
        BarcodeStorage barcodeStorage = StorageFactory.getBarCodeStorage();

        scanList = binding.rvBarCodeList;

        scanList.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new ScanListAdapter(barcodeStorage);
        scanList.setAdapter(adapter);
        ItemTouchHelper swipeToDelete = new ItemTouchHelper(initSwipeCallback());
        swipeToDelete.attachToRecyclerView(scanList);

        FloatingActionButton fab = binding.fab;

        fab.setOnClickListener(v -> {
            BarcodeScan scan = barcodeStorage.getCurrentScan();

            Intent email = new Intent(Intent.ACTION_SEND);
            //email.putExtra(Intent.EXTRA_EMAIL, new String[]{});
            email.putExtra(Intent.EXTRA_SUBJECT, "QR Scan - " + scan.getTitle() + " (" + scan.getId() + ")");
            email.putExtra(Intent.EXTRA_TEXT, renderScan(scan));

            email.setType("message/rfc822");

            startActivity(Intent.createChooser(email, "Choose an Email client :"));
        });

        return rootView;
    }

    private String renderScan(BarcodeScan scan) {
        return new StringBuilder()
                .append("Id: ").append(scan.getId())
                .append("\n")
                .append("title: ").append(scan.getTitle())
                .append("\n")
                .append("date: ").append(DateTimeFormatter.ofPattern(getString(R.string.app_datetime_format)).format(scan.getScanDate()))
                .append("\n\n")
                .append("Scanned codes: \n")
                .append("\n")
                .append(String.join("\n",scan.getBarCodes())
                )
                .toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private ItemTouchHelper.SimpleCallback initSwipeCallback(){
        return new SwipeToDeleteCallback(requireContext()) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final String item = adapter.getItems().get(position);

                adapter.removeItem(position);


                Snackbar snackbar = Snackbar
                        .make(rootView, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter.restoreItem(item, position);
                        scanList.scrollToPosition(position);
                    }
                });

                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        };
    }
}