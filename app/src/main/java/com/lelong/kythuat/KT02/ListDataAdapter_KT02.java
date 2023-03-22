package com.lelong.kythuat.KT02;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.KT01.kt01_camera;
import com.lelong.kythuat.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;

public class ListDataAdapter_KT02 extends RecyclerView.Adapter<ListDataAdapter_KT02.ViewHolder> {

    private Context applicationContext;
    private int resource;
    private ArrayList<KT02_LIST> mangLV02;
    String ID2;
    String DULIEU;
    String DULIEUnew,DULIEUnew1;
    String g_date;
    String g_bp;
    String bienngay;

    //private TextView user, ngay;
    String g_ghichu,user,ngay,somay,g_checkbox1,g_checkbox2,g_checkbox3,g_checkbox4,g_checkbox5,g_checkbox6,mahangmuc;


    public ListDataAdapter_KT02(Context context, int resource, ArrayList<KT02_LIST> mangLV02) {
        this.applicationContext = context;
        this.resource = resource;
        this.mangLV02 = mangLV02;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_item_kt02, parent, false);
        // Passing view to ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tc_fac003.setText(mangLV02.get(position).getTc_fac003());
        holder.tc_fac006.setText(mangLV02.get(position).getTc_fac006());
        holder.tc_fac004.setText(mangLV02.get(position).getTc_fac004());
        holder.checkBox1.setChecked(mangLV02.get(position).getCheckBox1());
        holder.checkBox2.setChecked(mangLV02.get(position).getCheckBox2());
        holder.checkBox3.setChecked(mangLV02.get(position).getCheckBox3());
        holder.checkBox4.setChecked(mangLV02.get(position).getCheckBox4());
        holder.checkBox5.setChecked(mangLV02.get(position).getCheckBox5());
        holder.checkBox6.setChecked(mangLV02.get(position).getCheckBox6());
        holder.tc_fac009.setText(mangLV02.get(position).getTc_fac009());

        user=mangLV02.get(position).getUser();
        somay=mangLV02.get(position).getSomay();
        ngay=mangLV02.get(position).getNgay();
//S
        if (mangLV02.get(position).getTenhinh() == "TRUE"){
            Drawable[] layers = new Drawable[2];
            layers[0] = applicationContext.getDrawable(R.drawable.camera1); // replace R.drawable.button_image with your button image
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setStroke(4, Color.RED); // set border color and width
            gradientDrawable.setCornerRadius(20); // set border corner radius
            layers[1] = gradientDrawable;
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            holder.btn1.setBackground(layerDrawable);
        }
        if (g_date != null){
            ID2 = g_bp;
            bienngay  = g_date;
        }else
        {
            try {
                //InputStream is = applicationContext.getApplicationContext().openFileInput("mydata.txt");
                InputStream is=applicationContext.getApplicationContext().openFileInput("mydata.txt");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                ID2 = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            LocalDate currentDate = LocalDate.now();
            bienngay = String.valueOf(currentDate);
        }
        holder.btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public
            void onClick(View v) {
                ;
                // Xóa giá trị của EditText
                //  holder.ghichu1.setText("");
                //String mahangmuc= mangLV02.get(holder.getPosition()).getTc_fac004();
                DULIEU =mangLV02.get(holder.getPosition()).getTc_fac004();
                Intent intent = new Intent(applicationContext, KT02_camera.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", DULIEU);
                bundle.putString("l_ngay", ngay);
                bundle.putString("l_bp", user);
                bundle.putString("l_somay", somay);
                intent.putExtras(bundle);


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // add this line
                applicationContext.startActivity(intent);

            }
        });
        /*update cột ghi chu (S)*/
        holder.tc_fac009.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.tc_fac009.getText().toString().trim().length() > 0) {
                    getUserCode(holder.tc_fac009.getText().toString().trim(),
                            holder.getPosition());
                }
                //holder.tc_fac009.getText().toString();
            }

            private void getUserCode(String kt02_tc_fac009, int position) {
                try {
                    final String qr_val = kt02_tc_fac009.trim();
                    String mahangmuc= mangLV02.get(position).getTc_fac004();
                    g_ghichu=null;
                    g_ghichu=holder.tc_fac009.getText().toString();
                    KT02_DB.updatekt_col("tc_fac009",g_ghichu,mahangmuc,user,somay,ngay);
                    mangLV02.get(position).setTc_fac009(g_ghichu);
                } catch (Exception e) {
                    String err = e.toString();
                }
            }
        });
        /*update cột ghi chu (E)*/

        /*checkbox1*/
        holder.checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahangmuc= mangLV02.get(holder.getPosition()).getTc_fac004();
                if (holder.checkBox1.isChecked()) {
                    g_checkbox1= String.valueOf(holder.checkBox1.isChecked());
                    update_checkbox(holder.getPosition(), 1,holder);
                } else {
                    g_checkbox1= String.valueOf(holder.checkBox1.isChecked());
                    update_checkbox(holder.getPosition(), 0, holder);
                }
            }
        });
        /*checkbox2*/
        holder.checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahangmuc= mangLV02.get(holder.getPosition()).getTc_fac004();
                if (holder.checkBox2.isChecked()) {
                    g_checkbox2= String.valueOf(holder.checkBox2.isChecked());
                    update_checkbox(holder.getPosition(), 2,holder);
                } else {
                    g_checkbox2= String.valueOf(holder.checkBox2.isChecked());
                    update_checkbox(holder.getPosition(), 0, holder);
                }
            }
        });
        /*checkbox3*/
        holder.checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahangmuc= mangLV02.get(holder.getPosition()).getTc_fac004();
                if (holder.checkBox3.isChecked()) {
                    g_checkbox3= String.valueOf(holder.checkBox3.isChecked());
                    update_checkbox(holder.getPosition(), 3,holder);
                } else {
                    g_checkbox3= String.valueOf(holder.checkBox3.isChecked());
                    update_checkbox(holder.getPosition(), 0, holder);
                }
            }
        });
        /*checkbox4*/
        holder.checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahangmuc= mangLV02.get(holder.getPosition()).getTc_fac004();
                if (holder.checkBox4.isChecked()) {
                    g_checkbox4= String.valueOf(holder.checkBox4.isChecked());
                    update_checkbox(holder.getPosition(), 4,holder);
                } else {
                    g_checkbox4= String.valueOf(holder.checkBox4.isChecked());
                    update_checkbox(holder.getPosition(), 0, holder);
                }
            }
        });
        /*checkbox5*/
        holder.checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahangmuc= mangLV02.get(holder.getPosition()).getTc_fac004();
                if (holder.checkBox5.isChecked()) {
                    g_checkbox5= String.valueOf(holder.checkBox5.isChecked());
                    update_checkbox(holder.getPosition(), 5,holder);
                } else {
                    g_checkbox5= String.valueOf(holder.checkBox5.isChecked());
                    update_checkbox(holder.getPosition(), 0, holder);
                }
            }
        });
        /*checkbox6*/
        holder.checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahangmuc= mangLV02.get(holder.getPosition()).getTc_fac004();
                if (holder.checkBox6.isChecked()) {
                    g_checkbox6= String.valueOf(holder.checkBox6.isChecked());
                    update_checkbox(holder.getPosition(), 6,holder);
                } else {
                    g_checkbox6= String.valueOf(holder.checkBox6.isChecked());
                    update_checkbox(holder.getPosition(), 0, holder);
                }
            }
        });
    }

    private void update_checkbox(int position, int i, ViewHolder holder) {

        boolean bol_1 = false, bol_2 = false, bol_3 = false, bol_4 = false, bol_5 = false, bol_6 = false;

        switch (i) {
            case 1:
                bol_1 = true;
                bol_2 =  bol_3 = bol_4 = bol_5 = bol_6 = false;
                break;
            case 2:
                bol_2 = true;
                bol_1 =  bol_3 = bol_4 = bol_5 = bol_6 = false;
                break;
            case 3:
                bol_3 = true;
                bol_2 =  bol_1 = bol_4 = bol_5 = bol_6 = false;
                break;
            case 4:
                bol_4 = true;
                bol_2 =  bol_3 = bol_1 = bol_5 = bol_6 = false;
                break;
            case 5:
                bol_5 = true;
                bol_2 =  bol_3 = bol_4 = bol_1 = bol_6 = false;
                break;
            case 6:
                bol_6 = true;
                bol_2 =  bol_3 = bol_4 = bol_5 = bol_1 = false;
                break;

        }
        mangLV02.get(position).setCheckBox1(bol_1);
        mangLV02.get(position).setCheckBox2(bol_2);
        mangLV02.get(position).setCheckBox3(bol_3);
        mangLV02.get(position).setCheckBox4(bol_4);
        mangLV02.get(position).setCheckBox5(bol_5);
        mangLV02.get(position).setCheckBox6(bol_6);
        holder.checkBox1.setChecked(bol_1);
        holder.checkBox2.setChecked(bol_2);
        holder.checkBox3.setChecked(bol_3);
        holder.checkBox4.setChecked(bol_4);
        holder.checkBox5.setChecked(bol_5);
        holder.checkBox6.setChecked(bol_6);

        String mahangmuc= mangLV02.get(position).getTc_fac004();

        KT02_DB.updatekt_col("checkbox1", String.valueOf(bol_1),mahangmuc,user,somay,ngay);
        KT02_DB.updatekt_col("checkbox2", String.valueOf(bol_2),mahangmuc,user,somay,ngay);
        KT02_DB.updatekt_col("checkbox3", String.valueOf(bol_3),mahangmuc,user,somay,ngay);
        KT02_DB.updatekt_col("checkbox4", String.valueOf(bol_4),mahangmuc,user,somay,ngay);
        KT02_DB.updatekt_col("checkbox5", String.valueOf(bol_5),mahangmuc,user,somay,ngay);
        KT02_DB.updatekt_col("checkbox6", String.valueOf(bol_6),mahangmuc,user,somay,ngay);
    }

    @Override
    public int getItemCount() {
        return mangLV02.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
        ImageView images;
        TextView tc_fac003, tc_fac006, tc_fac004;

        EditText tc_fac009;
        Button btn1;

        public ViewHolder(View view) {
            super(view);
            tc_fac003 = (TextView) view.findViewById(R.id.tc_fac003);
            tc_fac006 = (TextView) view.findViewById(R.id.tc_fac006);
            tc_fac004 = (TextView) view.findViewById(R.id.tc_fac004);
            checkBox1 = (CheckBox) view.findViewById(R.id.checkBox1);
            checkBox2 = (CheckBox) view.findViewById(R.id.checkBox2);
            checkBox3 = (CheckBox) view.findViewById(R.id.checkBox3);
            checkBox4 = (CheckBox) view.findViewById(R.id.checkBox4);
            checkBox5 = (CheckBox) view.findViewById(R.id.checkBox5);
            checkBox6 = (CheckBox) view.findViewById(R.id.checkBox6);
            tc_fac009 = (EditText) view.findViewById(R.id.tc_fac009);
            btn1 = (Button ) view.findViewById(R.id.btn1);
        }
    }
}
