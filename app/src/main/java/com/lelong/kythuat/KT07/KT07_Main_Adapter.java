package com.lelong.kythuat.KT07;

import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.R;

import java.util.List;
import java.util.Objects;

public class KT07_Main_Adapter extends RecyclerView.Adapter<KT07_Main_Adapter.DataViewHolder>{
    private final Context applicationContext;
    private final int layout_resource;
    private final List<KT07_Main_RowItem> kt07MainRowItems_list;
    private KT07_DB kt07Db = null;
    private final TextView tv_tc_ceb03;
    private final TextView tv_tc_ceb06;
    private final TextView tv_tc_cebuser;
    private final TextView tv_tc_cebdate;
    private int editingPosition = RecyclerView.NO_POSITION;
    public KT07_Main_Adapter(Context applicationContext, int kt07_listdata_item, List<KT07_Main_RowItem> kt07MainRowItems_list, TextView tv_tc_ceb03,TextView tv_tc_ceb06,TextView tv_tc_cebdate,TextView tv_tc_cebuser) {
        this.applicationContext = applicationContext;
        this.layout_resource = kt07_listdata_item;
        this.kt07MainRowItems_list = kt07MainRowItems_list;

        this.tv_tc_ceb03 = tv_tc_ceb03;
        this.tv_tc_ceb06 = tv_tc_ceb06;
        this.tv_tc_cebdate = tv_tc_cebdate;
        this.tv_tc_cebuser = tv_tc_cebuser;

        kt07Db = new KT07_DB(applicationContext);
        kt07Db.open();
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

        //Insert dữ liệu vào bảng ảo tc_ceb (S)
        KT07_Debounced debouncedTextWatcher = new KT07_Debounced(new KT07_Debounced.TextWatcherListener() {
            @Override
            public void onTextChanged(String text) {
                int sodo = Integer.parseInt(text);

                if(sodo>0) {

                    text = String.valueOf(sodo);
                    int adapterPosition_tmp = holder.getAdapterPosition();
                    editingPosition = adapterPosition_tmp;
                    if(editingPosition >= 0 ) {
                        handleTextChanged(editingPosition, text);
                        kt07MainRowItems_list.get(editingPosition).setG_tc_ceb04(text);
                    }

                }

            }
        });

        holder.edt_tc_ceb04.addTextChangedListener(debouncedTextWatcher);
        //Insert dữ liệu vào bảng ảo tc_ceb (E)

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
    //Insert dữ liệu vào bảng ảo tc_ceb (S)
    private void handleTextChanged(int adapterPosition, String text) {
        kt07Db.ins_tc_ceb_file(kt07MainRowItems_list.get(adapterPosition).getG_TC_CEA01(),
                kt07MainRowItems_list.get(adapterPosition).getG_tc_cea03(),
                tv_tc_ceb03.getText().toString(),
                text,
                "0",
                tv_tc_ceb06.getText().toString(),
                tv_tc_cebdate.getText().toString(),
                tv_tc_cebuser.getText().toString());
    }
    //Insert dữ liệu vào bảng ảo tc_ceb (E)
}
