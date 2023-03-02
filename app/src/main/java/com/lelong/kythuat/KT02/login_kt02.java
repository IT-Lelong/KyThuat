package com.lelong.kythuat.KT02;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class login_kt02 {
    SimpleDateFormat dateFormatKT02 = new SimpleDateFormat("yyyy/MM/dd");
    private Create_Table createTable = null;
    Cursor cursor_1, cursor_2;
    String[] station = new String[0];
    ArrayAdapter<String> stationlist;
    ArrayList<List_Bophan> mangbp;

    String g_soxe, g_bophan, mabp, tenbp;
    Button btnins, btnsearch;
    private Activity activity;

    public void login_dialogkt02(Context context, String menuID, Activity activity) {
        this.activity = activity;
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt02_activity_loginsetting);

        Spinner cbxsoxe = dialog.findViewById(R.id.cbxsoxe);
        btnins = dialog.findViewById(R.id.btninsert);
        btnins.setOnClickListener(btnlistener1);
        createTable = new Create_Table(dialog.getContext());

        createTable.open();
        cursor_1 = createTable.getAll_fia_02();
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        station = new String[num];
        for (int i = 0; i < num; i++) {

            try {
                @SuppressLint("Range") String fiaud03 = cursor_1.getString(cursor_1.getColumnIndex("fiaud03"));

                String g_fiaud03 = fiaud03;
                station[i] = g_fiaud03;

            } catch (Exception e) {
                String err = e.toString();
            }
            stationlist = new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_spinner_item, station);
            cbxsoxe.setAdapter(stationlist);
            cbxsoxe.setSelection(0);
            cursor_1.moveToNext();
        }
//Bộ phận

        Spinner cbxbophan = dialog.findViewById(R.id.cbxbophan);
        List<List_Bophan> qrReScanIpLists = new ArrayList<>();

        cursor_2 = createTable.getAll_fia_02_bp();
        cursor_2.moveToFirst();
        int num1 = cursor_2.getCount();
        station = new String[num1];
        for (int i = 0; i < num1; i++) {

            try {
                @SuppressLint("Range") String fia11 = cursor_2.getString(cursor_2.getColumnIndex("fia11"));
                @SuppressLint("Range") String gem02 = cursor_2.getString(cursor_2.getColumnIndex("gem02"));
                qrReScanIpLists.add(new List_Bophan(fia11, gem02));

            } catch (Exception e) {
                String err = e.toString();
            }
            cursor_2.moveToNext();
        }
        Bophan_Adapter bophan_adapter = new Bophan_Adapter(this.activity,
                R.layout.kt02_loginsetting_bophan,
                R.id.sp_mabp,
                R.id.sp_tenbp,
                qrReScanIpLists);
        cbxbophan.setAdapter(bophan_adapter);

        cbxbophan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    //get IP
                    List_Bophan res = bophan_adapter.getItem(position);
                    mabp = res.getMabp().toString().trim();
                    tenbp = res.getTenbp().toString().trim();
                    //g_bophan=qrReScanIpLists.get(position).getMabp().trim();
                    //g_bophan=tenbp;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //spinner Scan IP (E)
        cbxsoxe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                g_soxe = cbxsoxe.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cbxbophan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //g_bophan=cbxbophan.getAdapter().toString().trim();
                //g_bophan=cbxbophan.getSelectedItem().toString().trim();
                g_bophan = qrReScanIpLists.get(position).getMabp().trim();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        dialog.show();
    }


    private Button.OnClickListener btnlistener1 = new Button.OnClickListener() {
        public void onClick(View v) {
            //利用switch case方法，之後新增按鈕只需新增case即可
            switch (v.getId()) {

                case R.id.btninsert: {

                    Intent QR020 = new Intent();
                    QR020.setClass(v.getContext(), KT02_activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("somay", g_soxe);
                    bundle.putString("bophan", g_bophan);
                    QR020.putExtras(bundle);
                    v.getContext().startActivity(QR020);
                    break;
                }

                /*case R.id.btnsearch: {
                    Intent QR020 = new Intent();
                    QR020.setClass(Menu.this, Login_setting.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    bundle.putString("SERVER", g_server);
                    QR020.putExtras(bundle);
                    startActivity(QR020);
                    break;*/
            }
        }
    };
}
