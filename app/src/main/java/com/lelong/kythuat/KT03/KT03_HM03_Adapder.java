package com.lelong.kythuat.KT03;

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

public class KT03_HM03_Adapder extends RecyclerView.Adapter<KT03_HM03_Adapder.DataViewHolder> {
    private final Context context;
    private final int resource;
    private final List<KT03_HM03_Model> objects;
    private final String g_date;
    private final String g_ca;
    KT03_DB kt03Db = null;
    private String g_id;
    private KT03_Interface listener;

    public KT03_HM03_Adapder(Context context, int resource, List<KT03_HM03_Model> objects, String g_date, String g_ca, String g_id, KT03_Interface listener) {

        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.g_date = g_date;
        this.g_ca = g_ca;
        this.g_id = g_id;
        this.listener = listener;
        kt03Db = new KT03_DB(context);
        kt03Db.open();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new KT03_HM03_Adapder.DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KT03_HM03_Adapder.DataViewHolder holder, int position) {

        holder.tv_stt.setText(String.valueOf(position + 1));
        holder.edt_ten.setText(objects.get(position).getKt03_03_002());
        holder.edt_dayDo.setText(objects.get(position).getKt03_03_003());
        holder.edt_nongDo.setText(objects.get(position).getKt03_03_004());
        holder.edt_hanSuDung.setText(objects.get(position).getKt03_03_005());
        holder.edt_giaTri.setText(objects.get(position).getKt03_03_006());
        holder.edt_saiSo.setText(objects.get(position).getKt03_03_007());

        //Sự kiện lắng nghe khi click vào recycleview Item (S)
        // holder.itemView.setOnClickListener(v -> listener.HM03_rcv_onItemClick(position));
        //Sự kiện lắng nghe khi click vào recycleview Item (E)

        holder.edt_ten.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (holder.edt_ten.getText().length() > 0) {
                    String new_key = listener.HM03_ins_tablerow(position);
                    upd_Data("KT03_03_002",
                            new_key,
                            holder.edt_ten.getText().toString().trim(),
                            g_id);
                    objects.get(holder.getPosition()).setKt03_03_002(holder.edt_ten.getText().toString().trim());
                    //listener.HM03_add_newRow(position);
                }
            }
        });

        holder.edt_dayDo.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (holder.edt_dayDo.getText().length() > 0) {
                    String new_key = listener.HM03_ins_tablerow(position);
                    upd_Data("KT03_03_003",
                            new_key,
                            holder.edt_dayDo.getText().toString().trim(),
                            g_id);
                    objects.get(holder.getPosition()).setKt03_03_003(holder.edt_dayDo.getText().toString().trim());
                    //listener.HM03_add_newRow(position);
                }
            }
        });

        holder.edt_nongDo.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (holder.edt_nongDo.getText().length() > 0) {
                    String new_key = listener.HM03_ins_tablerow(position);
                    upd_Data("KT03_03_004",
                            new_key,
                            holder.edt_nongDo.getText().toString().trim(),
                            g_id);
                    objects.get(holder.getPosition()).setKt03_03_004(holder.edt_nongDo.getText().toString().trim());
                    //listener.HM03_add_newRow(position);
                }
            }
        });

        holder.edt_hanSuDung.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (holder.edt_hanSuDung.getText().length() > 0) {
                    String new_key = listener.HM03_ins_tablerow(position);
                    upd_Data("KT03_03_005",
                            new_key,
                            holder.edt_hanSuDung.getText().toString().trim(),
                            g_id);
                    objects.get(holder.getPosition()).setKt03_03_005(holder.edt_hanSuDung.getText().toString().trim());
                    //listener.HM03_add_newRow(position);
                }
            }
        });

        holder.edt_giaTri.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (holder.edt_giaTri.getText().length() > 0) {
                    String new_key = listener.HM03_ins_tablerow(position);
                    upd_Data("KT03_03_006",
                            new_key,
                            holder.edt_giaTri.getText().toString().trim(),
                            g_id);
                    objects.get(holder.getPosition()).setKt03_03_006(holder.edt_giaTri.getText().toString().trim());
                    //listener.HM03_add_newRow(position);
                }
            }
        });

        holder.edt_saiSo.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (holder.edt_saiSo.getText().length() > 0) {
                    String new_key = listener.HM03_ins_tablerow(position);
                    upd_Data("KT03_03_007",
                            new_key,
                            holder.edt_saiSo.getText().toString().trim(),
                            g_id);
                    objects.get(holder.getPosition()).setKt03_03_007(holder.edt_saiSo.getText().toString().trim());
                    //listener.HM03_add_newRow(position);
                }
            }
        });
    }

    private void upd_Data(String g_col, String g_key, String g_noidung, String g_id) {
        kt03Db.upd_HM03(g_col, g_key, g_date, g_ca, g_noidung, g_id);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView tv_stt;
        EditText edt_ten, edt_dayDo, edt_nongDo, edt_hanSuDung, edt_giaTri, edt_saiSo;

        public DataViewHolder(View itemView) {
            super(itemView);

            tv_stt = (TextView) itemView.findViewById(R.id.tv_stt);
            edt_ten = (EditText) itemView.findViewById(R.id.edt_ten);
            edt_dayDo = (EditText) itemView.findViewById(R.id.edt_dayDo);
            edt_nongDo = (EditText) itemView.findViewById(R.id.edt_nongDo);
            edt_hanSuDung = (EditText) itemView.findViewById(R.id.edt_hanSuDung);
            edt_giaTri = (EditText) itemView.findViewById(R.id.edt_giaTri);
            edt_saiSo = (EditText) itemView.findViewById(R.id.edt_saiSo);
        }
    }
}
