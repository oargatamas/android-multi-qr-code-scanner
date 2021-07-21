package hu.medev.office.utils.android.qrscan.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import hu.medev.office.utils.android.R;
import hu.medev.office.utils.android.qrscan.helper.ContextHolder;
import hu.medev.office.utils.android.qrscan.shared.BarcodeStorage;
import hu.medev.office.utils.android.qrscan.shared.data.BarcodeScan;
import hu.medev.office.utils.android.qrscan.ui.utils.RecyclerViewItemClickListener;

public class PreviousScansAdapter extends RecyclerView.Adapter<PreviousScansAdapter.ViewHolder> {

    BarcodeStorage barcodeStorage;
    List<BarcodeScan> items;
    RecyclerViewItemClickListener<BarcodeScan> itemClickListener;

    public PreviousScansAdapter(BarcodeStorage barcodeStorage) {
        this.barcodeStorage = barcodeStorage;
        this.items = getScansOrdered();
    }


    public List<BarcodeScan> getItems() {
        return items;
    }

    public void setItemClickListener(RecyclerViewItemClickListener<BarcodeScan> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public PreviousScansAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prev_scan_list_item, parent, false);
        return new PreviousScansAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousScansAdapter.ViewHolder holder, int position) {
        BarcodeScan item = items.get(position);
        holder.scanTitle.setText(item.getTitle());
        holder.scanId.setText(item.getId());
        String datePattern = ContextHolder.getContext().getString(R.string.app_datetime_format);
        holder.scanDate.setText(item.getScanDate().format(DateTimeFormatter.ofPattern(datePattern)));
        holder.noItems.setText(String.valueOf(item.getBarCodes().size()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItem(int position) {
        BarcodeScan scan = items.get(position);
        items.remove(position);
        barcodeStorage.removeScan(scan);
        notifyItemRemoved(position);
    }

    public void restoreItem(BarcodeScan item, int position) {
        items.add(position, item);
        barcodeStorage.addScan(item);
        notifyItemInserted(position);
    }

    public void refresh() {
        items = getScansOrdered();
        notifyDataSetChanged();
    }

    private List<BarcodeScan> getScansOrdered(){
        return barcodeStorage.getAllScans().stream()
                .sorted(Comparator.comparing(BarcodeScan::getScanDate).reversed())
                .collect(Collectors.toList());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView scanId;
        TextView scanTitle;
        TextView noItems;
        TextView scanDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            scanId = itemView.findViewById(R.id.tvId);
            scanTitle = itemView.findViewById(R.id.tvTitle);
            noItems = itemView.findViewById(R.id.tvNoItems);
            scanDate = itemView.findViewById(R.id.tvScanDate);

            itemView.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();
                itemClickListener.onItemClicked(items.get(position),position);
            });
        }
    }
}