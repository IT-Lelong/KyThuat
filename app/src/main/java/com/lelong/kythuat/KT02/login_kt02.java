package com.lelong.kythuat.KT02;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.KT03.KT03_DB;
import com.lelong.kythuat.KT03.KT03_main_activity;
import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class login_kt02 {
    SimpleDateFormat dateFormatKT02 = new SimpleDateFormat("yyyy/MM/dd");
    private Create_Table createTable = null;
    private KT02_DB kt02Db = null;
    //private KT03_DB kt03Db = null;
    Cursor cursor_1, cursor_2;
    String[] station = new String[0];
    ArrayAdapter<String> stationlist;
    ArrayList<List_Bophan> mangbp;

    String g_soxe, g_bophan, mabp, tenbp;
    Button btnins, btnsearch;
    private Activity activity;
    ListView lv_query02;

    public void login_dialogkt02(Context context, String menuID, Activity activity) {
        this.activity = activity;
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt02_activity_loginsetting);

        Spinner cbxsoxe = dialog.findViewById(R.id.cbxsoxe);
        btnins = dialog.findViewById(R.id.btninsert);
        btnins.setOnClickListener(btnlistener1);
        createTable = new Create_Table(dialog.getContext());
        createTable.open();
        kt02Db = new KT02_DB(dialog.getOwnerActivity());
        //kt03Db = new KT03_DB(dialog.getContext());

        //Danh sach đã kiểm tra (S)
        kt02Db.open();
        Cursor cursor = kt02Db.getAll_lvQuery();
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.kt02_login_lvrow, cursor,
                new String[]{"_id", "ngay", "somay", "user"},
                new int[]{R.id.tv_stt, R.id.tv_ngay, R.id.tv_soxe,R.id.tv_bophan},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.tv_stt) {
                    int rowNumber = cursor.getPosition() + 1;
                    ((TextView) view).setText(String.valueOf(rowNumber));
                    return true;
                }
                return false;
            }
        });

        lv_query02.setAdapter(simpleCursorAdapter);


        lv_query02.setOnItemClickListener((parent, view, position, id) -> {

            // Tạo đối tượng PopupMenu
            PopupMenu popupMenu = new PopupMenu(context.getApplicationContext(), view, Gravity.END, 0, R.style.MyPopupMenu);

            // Nạp tệp menu vào PopupMenu
            popupMenu.getMenuInflater().inflate(R.menu.kt02_login_lv, popupMenu.getMenu());

            // Show the PopupMenu.
            popupMenu.show();

            // Đăng ký sự kiện Popup Menu
            popupMenu.setOnMenuItemClickListener(item -> {

                TextView qry_ngay = view.findViewById(R.id.tv_ngay);
                TextView qry_somay = view.findViewById(R.id.tv_soxe);
                TextView qry_user = view.findViewById(R.id.tv_bophan);

                // Xử lý sự kiện khi người dùng chọn một lựa chọn trong menu
                switch (item.getItemId()) {
                    case R.id.openKT02:
                        Intent KT02 = new Intent();
                        KT02.setClass(context, KT03_main_activity.class);
                        Bundle bundle = new Bundle();
                        //bundle.putString("ID", ID);
                        bundle.putString("DATE", qry_ngay.getText().toString());
                        bundle.putString("SOMAY", qry_somay.getText().toString());
                        bundle.putString("USER", qry_user.getText().toString());
                        KT02.putExtras(bundle);
                        context.startActivity(KT02);
                        dialog.dismiss();
                        return true;

                    case R.id.clearKT02:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(context.getString(R.string.M05))
                                .setPositiveButton(context.getString(R.string.btn_ok), null)
                                .setNegativeButton(context.getString(R.string.btn_cancel), null);


                        AlertDialog al_dialog = builder.create();
                        al_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                TextView messageView = ((AlertDialog) dialogInterface).findViewById(android.R.id.message);
                                messageView.setTextSize(30);

                                Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                                positiveButton.setTextColor(ContextCompat.getColor(context, R.color.blue));
                                positiveButton.setTextSize(15);
                                //positiveButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                                Button negativeButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_NEGATIVE);
                                negativeButton.setTextColor(ContextCompat.getColor(context, R.color.red));
                                negativeButton.setTextSize(15);
                                //negativeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

                                positiveButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        kt02Db.delete_table(qry_ngay.getText().toString(), qry_somay.getText().toString(),qry_user.getText().toString());
                                        al_dialog.dismiss();
                                    }
                                });
                            }
                        });

                        al_dialog.show();

                        return true;
                }
                return true;
            });
        });

        //Danh sach đã kiểm tra (E)



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
                if (position >= 0) {
                    //get IP
                    List_Bophan res = bophan_adapter.getItem(position);
                    mabp = res.getMabp().toString().trim();
                    tenbp = res.getTenbp().toString().trim();
                    g_bophan = qrReScanIpLists.get(position).getMabp().trim();
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
