package hu.medev.office.utils.android.qrscan.ui.home;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.medev.office.utils.android.R;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;

public class PreviousScansAdapter extends RecyclerView.Adapter<PreviousScansAdapter.ViewHolder> {

    SharedPreferences sharedPreferences;
    List<BarcodeScan> items;

    public PreviousScansAdapter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.items = new ArrayList<>();
    }


    public List<BarcodeScan> getItems() {
        return items;
    }


    @NonNull
    @Override
    public PreviousScansAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_list_item, parent, false);
        return new PreviousScansAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousScansAdapter.ViewHolder holder, int position) {
        BarcodeScan item = items.get(position);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(BarcodeScan item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView scanTitle;
        TextView noItems;
        TextView scanDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            scanTitle = itemView.findViewById(R.id.tvTitle);
            noItems = itemView.findViewById(R.id.tvNoItems);
            scanDate = itemView.findViewById(R.id.tvScanDate);
        }
    }
}