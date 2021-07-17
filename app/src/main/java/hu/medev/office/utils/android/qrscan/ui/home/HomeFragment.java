package hu.medev.office.utils.android.qrscan.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import hu.medev.office.utils.android.databinding.FragmentHomeBinding;
import hu.medev.office.utils.android.qrscan.shared.StorageFactory;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;
import hu.medev.office.utils.android.qrscan.ui.utils.SwipeToDeleteCallback;

public class HomeFragment extends Fragment {


    private FragmentHomeBinding binding;
    private RecyclerView scanList;
    private PreviousScansAdapter adapter;
    private View emptyListView;
    private View rootView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        rootView = binding.getRoot();

        emptyListView = binding.NoItemView;


        scanList = binding.rvBarCodeList;

        scanList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new PreviousScansAdapter(StorageFactory.getBarCodeStorage());
        adapter.registerAdapterDataObserver(getEmptyObserver());
        scanList.setAdapter(adapter);
        ItemTouchHelper swipeToDelete = new ItemTouchHelper(initSwipeCallback());
        swipeToDelete.attachToRecyclerView(scanList);

        return rootView;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                final BarcodeScan item = adapter.getItems().get(position);

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

    private RecyclerView.AdapterDataObserver getEmptyObserver(){
        return new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                if( adapter.items.size() == 0 ){
                    emptyListView.setVisibility(View.VISIBLE);
                }else{
                    emptyListView.setVisibility(View.GONE);
                }
            }
        };
    }

}