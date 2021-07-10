package hu.medev.office.utils.android.qrscan.ui.scanlist;

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

import com.google.android.material.snackbar.Snackbar;

import hu.medev.office.utils.android.databinding.FragmentScanListBinding;
import hu.medev.office.utils.android.qrscan.ScannerActivity;
import hu.medev.office.utils.android.qrscan.shared.BarcodeStorage;

public class ScanListFragment extends Fragment {

    private ScannerActivity activity;
    private BarcodeStorage barcodeStorage;
    private FragmentScanListBinding binding;
    private RecyclerView scanList;
    private ScanListAdapter adapter;
    private View rootView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentScanListBinding.inflate(inflater, container, false);
        rootView = binding.getRoot();

        activity = (ScannerActivity) requireActivity();
        barcodeStorage = activity.getBarcodeStorage();

        scanList = binding.rvBarCodeList;

        scanList.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new ScanListAdapter(barcodeStorage.getScannedBarcodes());
        scanList.setAdapter(adapter);
        ItemTouchHelper swipeToDelete = new ItemTouchHelper(initSwipeCallback());
        swipeToDelete.attachToRecyclerView(scanList);

        return rootView;
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