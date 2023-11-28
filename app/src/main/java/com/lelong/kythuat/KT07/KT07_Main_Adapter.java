package com.lelong.kythuat.KT07;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.R;

import java.util.List;
import java.util.Map;

public class KT07_Main_Adapter extends RecyclerView.Adapter<KT07_Main_Adapter.DataViewHolder>{
    private final Context applicationContext;
    private final int layout_resource;
    private final List<KT07_Main_RowItem> kt07MainRowItems_list;

    public KT07_Main_Adapter(Context applicationContext, int kt07_listdata_item, List<KT07_Main_RowItem> kt07MainRowItems_list) {
        this.applicationContext = applicationContext;
        this.layout_resource = kt07_listdata_item;
        this.kt07MainRowItems_list = kt07MainRowItems_list;
    }

    @NonNull
    @Override
    public KT07_Main_Adapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout_resource, parent, false);
        return new KT07_Main_Adapter.DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KT07_Main_Adapter.DataViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        holder.tv_tc_cea03.setText(kt07MainRowItems_list.get(adapterPosition).getG_tc_cea03());
        holder.tv_tc_cea04.setText(kt07MainRowItems_list.get(adapterPosition).getG_tc_cea04());
        holder.tv_tc_cea05.setText(kt07MainRowItems_list.get(adapterPosition).getG_tc_cea05());
        holder.tv_tc_cea09.setText(kt07MainRowItems_list.get(adapterPosition).getG_tc_cea09());
        holder.edt_tc_ceb04.setText(kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb04());
    }

    @Override
    public int getItemCount() {
        return kt07MainRowItems_list.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tc_cea03, tv_tc_cea04, tv_tc_cea05, tv_tc_cea09;
        EditText edt_tc_ceb04;

        public DataViewHolder(View itemView) {
            super(itemView);
            tv_tc_cea03 = itemView.findViewById(R.id.tv_tc_cea03);
            tv_tc_cea04 = itemView.findViewById(R.id.tv_tc_cea04);
            tv_tc_cea05 = itemView.findViewById(R.id.tv_tc_cea05);
            tv_tc_cea09 = itemView.findViewById(R.id.tv_tc_cea09);
            edt_tc_ceb04 = itemView.findViewById(R.id.edt_tc_ceb04);
        }
    }
}
