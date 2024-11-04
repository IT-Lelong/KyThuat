package com.lelong.kythuat.KT03;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.KT01.KT01_Camera;
import com.lelong.kythuat.R;

import java.util.List;

public class KT03_HM0102_Adapder extends RecyclerView.Adapter<KT03_HM0102_Adapder.DataViewHolder> {
    private final Context context;
    private final int resource;
    private final List<KT03_HM0102_Model> objects;
    private final String g_date;
    private final String g_ca;
    KT03_DB kt03Db = null;
    private String g_id;
    private int g_lang;
    String ngay, bophan,mahangmuc;

    public KT03_HM0102_Adapder(@NonNull Context context, int resource, @NonNull List<KT03_HM0102_Model> objects, String g_date, String g_ca, String g_id) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.g_date = g_date;
        this.g_ca = g_ca;
        this.g_id = g_id;
        kt03Db = new KT03_DB(context);
        kt03Db.open();
        SharedPreferences preferences = context.getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        this.g_lang = language;

    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        holder.tv_stt.setText(objects.get(position).getG_stt());
        if(g_lang==0){
            holder.tv_hangmuc.setText(objects.get(position).getG_noidungHM_TH());
        }else{
            holder.tv_hangmuc.setText(objects.get(position).getG_noidungHM_TV());
        }
        holder.cb_tot_ca1.setChecked(objects.get(position).isG_tot_ca1());
        holder.cb_tot_ca2.setChecked(objects.get(position).isG_tot_ca2());
        holder.cb_kotot_ca1.setChecked(objects.get(position).isG_kotot_ca1());
        holder.cb_kotot_ca2.setChecked(objects.get(position).isG_kotot_ca2());
        holder.edt_ghiChu.setText(objects.get(position).getG_ghichu());
        String g_machitiet = objects.get(position).getG_maChiTiet();

        holder.cb_tot_ca1.setOnClickListener(v -> {
            if (holder.cb_tot_ca1.isChecked()) {
                kt03Db.upd_HM0102("KT03_01_002", g_machitiet, "1", g_date, g_ca,g_id);
                update_checkbox(position, 1,holder);
            } else {
                kt03Db.upd_HM0102("KT03_01_002", g_machitiet, "0", g_date, g_ca,g_id);
                update_checkbox(position, 0, holder);
            }

        });

        holder.cb_tot_ca2.setOnClickListener(v -> {
            if (holder.cb_tot_ca2.isChecked()) {
                kt03Db.upd_HM0102("KT03_01_002", g_machitiet, "2", g_date, g_ca,g_id);
                update_checkbox(position, 2, holder);
            } else {
                kt03Db.upd_HM0102("KT03_01_002", g_machitiet, "0", g_date, g_ca,g_id);
                update_checkbox(position, 0, holder);
            }
        });

        holder.cb_kotot_ca1.setOnClickListener(v -> {
            if (holder.cb_kotot_ca1.isChecked()) {
                kt03Db.upd_HM0102("KT03_01_002", g_machitiet, "3", g_date, g_ca,g_id);
                update_checkbox(position, 3, holder);
            } else {
                kt03Db.upd_HM0102("KT03_01_002", g_machitiet, "0", g_date, g_ca,g_id);
                update_checkbox(position, 0, holder);
            }
        });

        holder.cb_kotot_ca2.setOnClickListener(v -> {
            if (holder.cb_kotot_ca2.isChecked()) {
                kt03Db.upd_HM0102("KT03_01_002", g_machitiet, "4", g_date, g_ca,g_id);
                update_checkbox(position, 4, holder);
            } else {
                kt03Db.upd_HM0102("KT03_01_002", g_machitiet, "0", g_date, g_ca,g_id);
                update_checkbox(position, 0, holder);
            }
        });

        holder.edt_ghiChu.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // Đây là nơi để thực hiện xử lý khi EditText bị mất trạng thái tập trung
                String nd = holder.edt_ghiChu.getText().toString();
                String machitiet = objects.get(holder.getPosition()).getG_maChiTiet();
                kt03Db.upd_HM0102("KT03_01_003", machitiet, nd, g_date, g_ca,g_id);
                objects.get(holder.getPosition()).setG_ghichu(nd);
            }
        });

        ngay = g_date;
        bophan = g_ca;
        mahangmuc=g_id;

        String checkca1 = String.valueOf(objects.get(position).isG_kotot_ca1());
        String checkca2 = String.valueOf(objects.get(position).isG_kotot_ca2());
        Boolean chk_qrb = kt03Db.KT_ndhinh(g_machitiet,bophan,mahangmuc,ngay);
        if (chk_qrb == true) {
            /*if(bophan.equals("1")){
                holder.cb_tot_ca1.setChecked(true);
                holder.cb_kotot_ca1.setChecked(false);
            }else {
                holder.cb_tot_ca2.setChecked(true);
                holder.cb_kotot_ca2.setChecked(false);
            }*/
            Drawable[] layers = new Drawable[2];
            layers[0] = context.getDrawable(R.drawable.camera1); // replace R.drawable.button_image with your button image
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setStroke(4, Color.RED); // set border color and width
            gradientDrawable.setCornerRadius(20); // set border corner radius
            layers[1] = gradientDrawable;
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            holder.btn1.setBackground(layerDrawable);
        }else {
            Drawable[] layers = new Drawable[2];
            layers[0] = context.getDrawable(R.drawable.camera1); // replace R.drawable.button_image with your button image
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setStroke(4, Color.WHITE); // set border color and width
            gradientDrawable.setCornerRadius(20); // set border corner radius
            layers[1] = gradientDrawable;
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            holder.btn1.setBackground(layerDrawable);
        }

        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, KT03_Camera.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", g_id);
                bundle.putString("l_ngay", ngay);
                bundle.putString("l_ca", bophan);
                bundle.putString("l_hm", g_machitiet);
                intent.putExtras(bundle);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // add this line
                context.startActivity(intent);
                //openDialog(DULIEU,g_to);
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

    private void update_checkbox(int position, int i, DataViewHolder holder) {
        boolean bol_1 = false, bol_2 = false, bol_3 = false, bol_4 = false;

        switch (i) {
            case 1:
                bol_1 = true;
                break;
            case 2:
                bol_2 = true;
                break;
            case 3:
                bol_3 = true;
                break;
            case 4:
                bol_4 = true;
                break;

        }
        objects.get(position).setG_tot_ca1(bol_1);
        objects.get(position).setG_tot_ca2(bol_2);
        objects.get(position).setG_kotot_ca1(bol_3);
        objects.get(position).setG_kotot_ca2(bol_4);
        holder.cb_tot_ca1.setChecked(bol_1);
        holder.cb_tot_ca2.setChecked(bol_2);
        holder.cb_kotot_ca1.setChecked(bol_3);
        holder.cb_kotot_ca2.setChecked(bol_4);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        TextView tv_stt, tv_hangmuc;
        CheckBox cb_tot_ca1, cb_tot_ca2, cb_kotot_ca1, cb_kotot_ca2;
        EditText edt_ghiChu;
        Button btn1;

        public DataViewHolder(View itemView) {
            super(itemView);

            tv_stt = (TextView) itemView.findViewById(R.id.tv_stt);
            tv_hangmuc = (TextView) itemView.findViewById(R.id.tv_hangmuc);
            cb_tot_ca1 = (CheckBox) itemView.findViewById(R.id.cb_tot_ca1);
            cb_tot_ca2 = (CheckBox) itemView.findViewById(R.id.cb_tot_ca2);
            cb_kotot_ca1 = (CheckBox) itemView.findViewById(R.id.cb_kotot_ca1);
            cb_kotot_ca2 = (CheckBox) itemView.findViewById(R.id.cb_kotot_ca2);
            edt_ghiChu = (EditText) itemView.findViewById(R.id.edt_ghiChu);
            btn1 = (Button) itemView.findViewById(R.id.btn1);
        }
    }


}
