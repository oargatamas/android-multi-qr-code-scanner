package hu.medev.office.utils.android.qrscan.ui.scanlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hu.medev.office.utils.android.R;

public class ScanListAdapter extends RecyclerView.Adapter<ScanListAdapter.ViewHolder> {


    List<String> items;

    public ScanListAdapter(Collection<String> items) {
        this.items = new ArrayList<>(items);
    }


    public List<String> getItems() {
        return items;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String barcode = items.get(position);
        holder.index.setText(MessageFormat.format("#{0}", (position + 1)));
        holder.scannedBarCode.setText(barcode);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void removeItem(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(String item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView index;
        TextView scannedBarCode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.tvIndex);
            scannedBarCode = itemView.findViewById(R.id.tvBarCodeText);
        }
    }
}
