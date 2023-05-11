package com.lelong.kythuat.KT01;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.KT01.Retrofit2.APIYtils;
import com.lelong.kythuat.KT01.Retrofit2.DataClient;
import com.lelong.kythuat.KT02.KT02_DB;
import com.lelong.kythuat.KT02.ListDataAdapter_KT02;
import com.lelong.kythuat.Menu;
import com.lelong.kythuat.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ViewHolder> {
    //List<TabLayout> mangLV;
    //ArrayList tc_fab003, tc_fab006,tc_fab007;
    private Context applicationContext;
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    private Button loginButton;
    private List<String> mData;
    private KT01_DB db = null;
    KT01_Interface kt01Interface;
    String currentDate;
    String lbophan1;
    String bien;
    String value;
    String bienngay, ngay, bophan;
    String ID2;
    String DULIEU;
    String DULIEUnew, DULIEUnew1;
    String g_date;
    String g_bp;
    private int resource;
    private ArrayList<TabLayout> mangLV;
    private Object buttonView;
    String g_checkbox1, g_ghichu, g_checkbox, g_checkbox2, g_checkbox3, g_checkbox4, g_checkbox5;
    Dialog dialog;
    Button btn_cameranew, btn_cameraold;

    public void setData(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public ListDataAdapter(Context applicationContext, int resource, ArrayList<TabLayout> mangLV, String g_date, String g_bp, KT01_Interface kt01Interface) {
        this.applicationContext = applicationContext;
        this.resource = resource;
        this.mangLV = mangLV;
        this.g_date = g_date;
        this.g_bp = g_bp;
        db = new KT01_DB(this.applicationContext);
        db.open();
        this.kt01Interface = (KT01_Interface) kt01Interface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflating the Layout(Instantiates listdata_item.xml
        // layout file into View object)
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_item, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_item01, parent, false);
        // Passing view to ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }

    private void startActivity(Intent intent) {
    }


    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.tc_fab003.setText(mangLV.get(position).getTc_fac003());
        holder.tc_fab006.setText(mangLV.get(position).getTc_fac006());
        //holder.tc_fab007.setText(mangLV.get(position).getTc_fac007());
        holder.checkBox.setChecked(mangLV.get(position).getCheckBox());
        holder.checkBox1.setChecked(mangLV.get(position).getCheckBox1());
        holder.checkBox2.setChecked(mangLV.get(position).getCheckBox2());
        holder.checkBox3.setChecked(mangLV.get(position).getCheckBox3());
        holder.checkBox4.setChecked(mangLV.get(position).getCheckBox4());
        holder.checkBox5.setChecked(mangLV.get(position).getCheckBox5());
        holder.ghichu1.setText(mangLV.get(position).getTc_ghichu());

        ngay = mangLV.get(position).getNgay();
        bophan = mangLV.get(position).getBophan();

        if (mangLV.get(position).getTc_dkcamera() == "TRUE") {
            Drawable[] layers = new Drawable[2];
            layers[0] = applicationContext.getDrawable(R.drawable.camera1); // replace R.drawable.button_image with your button image
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setStroke(4, Color.RED); // set border color and width
            gradientDrawable.setCornerRadius(20); // set border corner radius
            layers[1] = gradientDrawable;
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            holder.btn1.setBackground(layerDrawable);
        }

        try {
            InputStream is = applicationContext.getApplicationContext().openFileInput("mydata.txt");
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

        holder.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa giá trị của EditText
                //  holder.ghichu1.setText("");
                /*DULIEU = mangLV.get(position).getTc_fac004();
                Intent intent = new Intent(applicationContext, kt01_camera.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", DULIEU);
                bundle.putString("l_ngay", ngay);
                bundle.putString("l_bp", bophan);
                intent.putExtras(bundle);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // add this line
                applicationContext.startActivity(intent);*/
                DULIEU = mangLV.get(position).getTc_fac004();
                openDialog(DULIEU);
            }
        });

        /*holder.checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Nếu checkbox được check, gán giá trị "Y" cho nó
                    value = "Y";
                    DULIEUnew = mangLV.get(position).getTc_fac004();
                    db.appendUPDAE(DULIEUnew, value, bienngay, ID2, "tc_faa004");
                } else {
                    // Nếu checkbox không được check, đặt lại giá trị của nó
                    value = "N";
                    DULIEUnew = mangLV.get(position).getTc_fac004();
                    db.appendUPDAE(DULIEUnew, value, bienngay, ID2, "tc_faa004");
                }
            }
        });*/

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahangmuc = mangLV.get(holder.getPosition()).getTc_fac004();
                if (holder.checkBox.isChecked()) {
                    g_checkbox = String.valueOf(holder.checkBox.isChecked());
                    update_checkbox(holder.getPosition(), 1, holder);
                    //db.appendUPDAE(mahangmuc, "Y", ngay, bophan, "tc_faa004");
                } else {
                    g_checkbox = String.valueOf(holder.checkBox.isChecked());
                    //db.appendUPDAE(mahangmuc, "N", ngay, bophan, "tc_faa004");
                }
            }
        });

        holder.checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahangmuc = mangLV.get(holder.getPosition()).getTc_fac004();
                if (holder.checkBox1.isChecked()) {
                    g_checkbox1 = String.valueOf(holder.checkBox1.isChecked());
                    update_checkbox(holder.getPosition(), 2, holder);
                    //db.appendUPDAE(mahangmuc, "Y", ngay, bophan, "tc_faa004");
                } else {
                    g_checkbox1 = String.valueOf(holder.checkBox1.isChecked());
                    //db.appendUPDAE(mahangmuc, "N", ngay, bophan, "tc_faa004");
                }
            }
        });
        holder.checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahangmuc = mangLV.get(holder.getPosition()).getTc_fac004();
                if (holder.checkBox2.isChecked()) {
                    g_checkbox2 = String.valueOf(holder.checkBox2.isChecked());
                    update_checkbox(holder.getPosition(), 3, holder);
                    //db.appendUPDAE(mahangmuc, "Y", ngay, bophan, "tc_faa004");
                } else {
                    g_checkbox2 = String.valueOf(holder.checkBox2.isChecked());
                    //db.appendUPDAE(mahangmuc, "N", ngay, bophan, "tc_faa004");
                }
            }
        });
        holder.checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahangmuc = mangLV.get(holder.getPosition()).getTc_fac004();
                if (holder.checkBox3.isChecked()) {
                    g_checkbox3 = String.valueOf(holder.checkBox3.isChecked());
                    update_checkbox(holder.getPosition(), 4, holder);
                    //db.appendUPDAE(mahangmuc, "Y", ngay, bophan, "tc_faa004");
                } else {
                    g_checkbox3 = String.valueOf(holder.checkBox3.isChecked());
                    //db.appendUPDAE(mahangmuc, "N", ngay, bophan, "tc_faa004");
                }
            }
        });
        holder.checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahangmuc = mangLV.get(holder.getPosition()).getTc_fac004();
                if (holder.checkBox4.isChecked()) {
                    g_checkbox4 = String.valueOf(holder.checkBox4.isChecked());
                    update_checkbox(holder.getPosition(), 5, holder);
                    //db.appendUPDAE(mahangmuc, "Y", ngay, bophan, "tc_faa004");
                } else {
                    g_checkbox4 = String.valueOf(holder.checkBox4.isChecked());
                    //db.appendUPDAE(mahangmuc, "N", ngay, bophan, "tc_faa004");
                }
            }
        });
        holder.checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mahangmuc = mangLV.get(holder.getPosition()).getTc_fac004();
                if (holder.checkBox5.isChecked()) {
                    g_checkbox5 = String.valueOf(holder.checkBox5.isChecked());
                    update_checkbox(holder.getPosition(), 6, holder);
                    //db.appendUPDAE(mahangmuc, "Y", ngay, bophan, "tc_faa004");
                } else {
                    g_checkbox5 = String.valueOf(holder.checkBox5.isChecked());
                    //db.appendUPDAE(mahangmuc, "N", ngay, bophan, "tc_faa004");
                }
            }
        });

        /*update cột ghi chu (S)*/
        holder.ghichu1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.ghichu1.getText().toString().trim().length() > 0) {
                    getUserCode(holder.ghichu1.getText().toString().trim(),
                            holder.getPosition());
                }
                //holder.tc_fac009.getText().toString();
            }

            private void getUserCode(String kt02_tc_fac009, int position) {
                try {
                    final String qr_val = kt02_tc_fac009.trim();
                    String mahangmuc = mangLV.get(position).getTc_fac004();
                    g_ghichu = null;
                    g_ghichu = holder.ghichu1.getText().toString();
                    //KT02_DB.updatekt_col("tc_fac009",g_ghichu,mahangmuc,user,somay,ngay);
                    db.appendUPDAE(mahangmuc, g_ghichu, ngay, bophan, "tc_faa006");
                    mangLV.get(position).setGhichu(g_ghichu);
                    //holder.ghichu1.setText("");
                } catch (Exception e) {
                    String err = e.toString();
                }
            }
        });
        /*update cột ghi chu (E)*/

    }

    private void openDialog(String DULIEU) {
        dialog = new Dialog(applicationContext);
        dialog.setContentView(R.layout.kt01_checkcamera);
        btn_cameranew = dialog.findViewById(R.id.btncamera);
        btn_cameraold = dialog.findViewById(R.id.btncameramodi);
        btn_cameranew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DULIEU = mangLV.get(position).getTc_fac004();
                Intent intent = new Intent(applicationContext, kt01_camera.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", DULIEU);
                bundle.putString("l_ngay", ngay);
                bundle.putString("l_bp", bophan);
                intent.putExtras(bundle);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // add this line
                applicationContext.startActivity(intent);
                dialog.dismiss();
            }
        });

        btn_cameraold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DULIEU = mangLV.get(position).getTc_fac004();
                Intent intent = new Intent(applicationContext, kt01_cameramodify.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", DULIEU);
                bundle.putString("l_ngay", ngay);
                bundle.putString("l_bp", bophan);
                intent.putExtras(bundle);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // add this line
                applicationContext.startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void update_checkbox(int position, int i, ListDataAdapter.ViewHolder holder) {

        boolean bol_1 = false, bol_2 = false, bol_3 = false, bol_4 = false, bol_5 = false, bol_6 = false;

        switch (i) {
            case 1:
                bol_1 = true;
                bol_2 = bol_3 = bol_4 = bol_5 = bol_6 = false;
                break;
            case 2:
                bol_2 = true;
                bol_1 = bol_3 = bol_4 = bol_5 = bol_6 = false;
                break;
            case 3:
                bol_3 = true;
                bol_2 = bol_1 = bol_4 = bol_5 = bol_6 = false;
                break;
            case 4:
                bol_4 = true;
                bol_2 = bol_3 = bol_1 = bol_5 = bol_6 = false;
                break;
            case 5:
                bol_5 = true;
                bol_2 = bol_3 = bol_4 = bol_1 = bol_6 = false;
                break;
            case 6:
                bol_6 = true;
                bol_2 = bol_3 = bol_4 = bol_5 = bol_1 = false;
                break;
        }
        mangLV.get(position).setCheckBox(bol_1);
        mangLV.get(position).setCheckBox1(bol_2);
        mangLV.get(position).setCheckBox2(bol_3);
        mangLV.get(position).setCheckBox3(bol_4);
        mangLV.get(position).setCheckBox4(bol_5);
        mangLV.get(position).setCheckBox5(bol_6);

        holder.checkBox.setChecked(bol_1);
        holder.checkBox1.setChecked(bol_2);
        holder.checkBox2.setChecked(bol_3);
        holder.checkBox3.setChecked(bol_4);
        holder.checkBox4.setChecked(bol_5);
        holder.checkBox5.setChecked(bol_6);


        String mahangmuc = mangLV.get(position).getTc_fac004();

        db.appendUPDAE(mahangmuc, String.valueOf(bol_1), ngay, bophan, "tc_faa004");
        db.appendUPDAE(mahangmuc, String.valueOf(bol_2), ngay, bophan, "tc_faa012");
        db.appendUPDAE(mahangmuc, String.valueOf(bol_3), ngay, bophan, "tc_faa013");
        db.appendUPDAE(mahangmuc, String.valueOf(bol_4), ngay, bophan, "tc_faa014");
        db.appendUPDAE(mahangmuc, String.valueOf(bol_5), ngay, bophan, "tc_faa015");
        db.appendUPDAE(mahangmuc, String.valueOf(bol_6), ngay, bophan, "tc_faa016");
    }

    @Override
    public int getItemCount() {
        return mangLV.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox, checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
        ImageView images;
        Button btn1;
        EditText ghichu1;

        TextView tc_fab003, tc_fab006, tc_fab007;

        @SuppressLint("UseCompatLoadingForDrawables")
        public ViewHolder(View view) {
            super(view);
            tc_fab003 = (TextView) view.findViewById(R.id.tc_fac003);
            tc_fab006 = (TextView) view.findViewById(R.id.tc_fac006);
            //tc_fab007 = (TextView) view.findViewById(R.id.tc_fac007);
            checkBox = (CheckBox) view.findViewById(R.id.checkBox);
            checkBox1 = (CheckBox) view.findViewById(R.id.checkBox1);
            checkBox2 = (CheckBox) view.findViewById(R.id.checkBox2);
            checkBox3 = (CheckBox) view.findViewById(R.id.checkBox3);
            checkBox4 = (CheckBox) view.findViewById(R.id.checkBox4);
            checkBox5 = (CheckBox) view.findViewById(R.id.checkBox5);
            ghichu1 = (EditText) view.findViewById(R.id.ghichu);
            btn1 = (Button) view.findViewById(R.id.btn1);

        }
    }

}
