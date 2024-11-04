package com.lelong.kythuat.KT04;

import android.content.Context;
import android.content.Intent;
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

import com.lelong.kythuat.R;

import java.util.List;

public class KT04_HM02_Adapder extends RecyclerView.Adapter<KT04_HM02_Adapder.DataViewHolder> {
    private final Context context;
    private final int resource;
    private final List<KT04_HM02_Model> objects;
    private final String g_date;
    private final String g_ca,g_id;
    KT04_DB kt04Db = null;

    public KT04_HM02_Adapder(@NonNull Context context, int resource, @NonNull List<KT04_HM02_Model> objects, String g_date, String g_ca, String g_id) {
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.g_date = g_date;
        this.g_ca = g_ca;
        this.g_id = g_id;
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
    public void onBindViewHolder(@NonNull KT04_HM02_Adapder.DataViewHolder holder, int position) {
        holder.tv_stt.setText(objects.get(position).getG_stt());
        holder.tv_hangmuc.setText(objects.get(position).getG_noidungHM_TV());
        holder.tv_hangsanxuat.setText(objects.get(position).getG_hangsanxuat());
        holder.cb_tot.setChecked(objects.get(position).isG_tot());
        holder.cb_kotot.setChecked(objects.get(position).isG_kotot());
        holder.edt_ghiChu.setText(objects.get(position).getG_ghichu());
        String g_machitiet = objects.get(position).getG_maChiTiet();

        holder.cb_tot.setOnClickListener(v -> {
            if (holder.cb_tot.isChecked()) {
                kt04Db.upd_HM0102("KT04_01_002", g_machitiet, "1", g_date, g_ca);
                update_checkbox(position, 1,holder);
            } else {
                kt04Db.upd_HM0102("KT04_01_002", g_machitiet, "0", g_date, g_ca);
                update_checkbox(position, 0, holder);
            }

        });


        holder.cb_kotot.setOnClickListener(v -> {
            if (holder.cb_kotot.isChecked()) {
                kt04Db.upd_HM0102("KT04_01_002", g_machitiet, "2", g_date, g_ca);
                update_checkbox(position, 2, holder);
            } else {
                kt04Db.upd_HM0102("KT04_01_002", g_machitiet, "0", g_date, g_ca);
                update_checkbox(position, 0, holder);
            }
        });

        holder.edt_ghiChu.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // Đây là nơi để thực hiện xử lý khi EditText bị mất trạng thái tập trung
                String nd = holder.edt_ghiChu.getText().toString();
                String machitiet = objects.get(holder.getPosition()).getG_maChiTiet();
                kt04Db.upd_HM0102("KT04_01_003", machitiet, nd, g_date, g_ca);
                objects.get(holder.getPosition()).setG_ghichu(nd);
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
        Boolean chk_qrb = kt04Db.KT_ndhinh(g_machitiet, g_ca, g_id,g_date);
        if (chk_qrb == true) {
            if(g_ca.equals("1")){
                holder.cb_kotot.setChecked(false);
                holder.cb_tot.setChecked(true);
            }else {
                holder.cb_tot.setChecked(false);
                holder.cb_kotot.setChecked(true);
            }
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
                Intent intent = new Intent(context, KT04_Camera.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", g_id);
                bundle.putString("l_ngay", g_date);
                bundle.putString("l_ca", g_ca);
                bundle.putString("l_hm", g_machitiet);
                intent.putExtras(bundle);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // add this line
                context.startActivity(intent);
                //openDialog(DULIEU,g_to);
            }
        });
    }

    private void update_checkbox(int position, int i, DataViewHolder holder) {
        boolean bol_1 = false, bol_2 = false;

        switch (i) {
            case 1:
                bol_1 = true;
                break;
            case 2:
                bol_2 = true;
                break;
        }
        objects.get(position).setG_tot(bol_1);
        objects.get(position).setG_kotot(bol_2);
        holder.cb_tot.setChecked(bol_1);
        holder.cb_kotot.setChecked(bol_2);
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {

        TextView tv_stt, tv_hangmuc,tv_hangsanxuat;
        CheckBox cb_tot, cb_kotot;
        EditText edt_ghiChu;
        Button btn1;
        public DataViewHolder(View itemView) {
            super(itemView);

            tv_stt = (TextView) itemView.findViewById(R.id.tv4_stt);
            tv_hangmuc = (TextView) itemView.findViewById(R.id.tv4_hangmuc);
            tv_hangsanxuat=(TextView) itemView.findViewById(R.id.tv4_hang);
            cb_tot = (CheckBox) itemView.findViewById(R.id.cb4_tot);
            cb_kotot = (CheckBox) itemView.findViewById(R.id.cb4_kotot);
            edt_ghiChu = (EditText) itemView.findViewById(R.id.edt4_ghiChu);
            btn1 = (Button) itemView.findViewById(R.id.btn1);
        }
    }
}
