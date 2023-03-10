package com.lelong.kythuat.KT04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.R;

import java.util.List;

public class KT04_HM03_Adapder extends RecyclerView.Adapter<KT04_HM03_Adapder.DataViewHolder> {
    private final Context context;
    private final int resource;
    private final List<KT04_HM03_Model> objects;
    private final String g_date;
    private final String g_ca;
    KT04_DB kt04Db = null;

    public KT04_HM03_Adapder(@NonNull Context context, int resource, @NonNull List<KT04_HM03_Model> objects, String g_date, String g_ca) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.g_date = g_date;
        this.g_ca = g_ca;
        kt04Db = new KT04_DB(context);
        kt04Db.open();
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KT04_HM03_Adapder.DataViewHolder holder, int position) {

        holder.tv_stt.setText(objects.get(position).getG_stt());
        holder.tv_ten.setText(objects.get(position).getG_noidungHM_TV());
        holder.tv_dayDo.setText(objects.get(position).getG_daydo());
        holder.edt_nongDo.setText(objects.get(position).getKt04_03_002());
        holder.edt_hanSuDung.setText(objects.get(position).getKt04_03_003());
        holder.edt_giaTri.setText(objects.get(position).getKt04_03_004());
        holder.edt_saiSo.setText(objects.get(position).getKt04_03_005());
        String g_machitiet = objects.get(position).getG_maChiTiet();

        holder.edt_nongDo.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // Đây là nơi để thực hiện xử lý khi EditText bị mất trạng thái tập trung
                String nd = holder.edt_nongDo.getText().toString();
                String machitiet = objects.get(holder.getPosition()).getG_maChiTiet();
                kt04Db.upd_HM03("KT04_03_002", machitiet, nd, g_date, g_ca);
                objects.get(holder.getPosition()).setKt04_03_002(nd);
            }
        });

        holder.edt_hanSuDung.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // Đây là nơi để thực hiện xử lý khi EditText bị mất trạng thái tập trung
                String nd = holder.edt_hanSuDung.getText().toString();
                String machitiet = objects.get(holder.getPosition()).getG_maChiTiet();
                kt04Db.upd_HM03("KT04_03_003", machitiet, nd, g_date, g_ca);
                objects.get(holder.getPosition()).setKt04_03_003(nd);
            }
        });

        holder.edt_giaTri.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // Đây là nơi để thực hiện xử lý khi EditText bị mất trạng thái tập trung
                String nd = holder.edt_giaTri.getText().toString();
                String machitiet = objects.get(holder.getPosition()).getG_maChiTiet();
                kt04Db.upd_HM03("KT04_03_004", machitiet, nd, g_date, g_ca);
                objects.get(holder.getPosition()).setKt04_03_004(nd);
            }
        });

        holder.edt_saiSo.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // Đây là nơi để thực hiện xử lý khi EditText bị mất trạng thái tập trung
                String nd = holder.edt_saiSo.getText().toString();
                String machitiet = objects.get(holder.getPosition()).getG_maChiTiet();
                kt04Db.upd_HM03("KT04_03_005", machitiet, nd, g_date, g_ca);
                objects.get(holder.getPosition()).setKt04_03_005(nd);
            }
        });
        /*holder.edt_ghiChu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    kt03Db.upd_HM0102("KT03_01_003",
                            objects.get(holder.getPosition()).getG_maChiTiet(),
                            s.toString(), g_date, g_ca);
                    objects.get(holder.getPosition()).setG_ghichu(s.toString());
                }
            }
        });*/

    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        TextView tv_stt,tv_ten,tv_dayDo;
        EditText edt_nongDo,edt_hanSuDung,edt_giaTri,edt_saiSo;

        public DataViewHolder(View itemView) {
            super(itemView);

            tv_stt = (TextView) itemView.findViewById(R.id.tv4_stt);
            tv_ten = (TextView) itemView.findViewById(R.id.tv4_ten);
            tv_dayDo = (TextView) itemView.findViewById(R.id.tv4_dayDo);
            edt_nongDo = (EditText) itemView.findViewById(R.id.edt4_nongDo);
            edt_hanSuDung = (EditText) itemView.findViewById(R.id.edt4_hanSuDung);
            edt_giaTri = (EditText) itemView.findViewById(R.id.edt4_giaTri);
            edt_saiSo = (EditText) itemView.findViewById(R.id.edt4_saiSo);

        }
    }
}
