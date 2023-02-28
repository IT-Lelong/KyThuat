package com.lelong.kythuat.KT03;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lelong.kythuat.R;
import com.lelong.kythuat.SetLanguage;
import com.lelong.kythuat.UploadToServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class login_kt03 {
    private SetLanguage setLanguage = null;
    private UploadToServer uploadToServer = null;
    String g_server = "";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public void login_dialog(Context context, String menuID, String ID) {
        //setLanguage.setLanguage();
        TextView tv_name, tv_date;
        RadioButton radio_a, radio_b;
        Button btn_Date, btn_Insert, btn_Upload;
        ListView lv_query;
        final String[] g_ca = {""};

        this.g_server = context.getString(R.string.server);


        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt03_login_dialog);

        tv_name = dialog.findViewById(R.id.tv_name);
        tv_date = dialog.findViewById(R.id.tv_date);
        radio_a = dialog.findViewById(R.id.radio_a);
        radio_b = dialog.findViewById(R.id.radio_b);
        btn_Date = dialog.findViewById(R.id.btn_Date);
        btn_Insert = dialog.findViewById(R.id.btn_Insert);
        btn_Upload = dialog.findViewById(R.id.btn_Upload);
        lv_query = dialog.findViewById(R.id.lv_query);

        tv_name.setText(menuID);
        tv_date.setText(dateFormat.format(new Date()).toString());

        KT03_DB kt03Db = new KT03_DB(context);
        kt03Db.open();
        kt03Db.openTable();

        //Danh sach đã kiểm tra (S)
        Cursor cursor = kt03Db.getAll_lvQuery();
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.kt03_login_dialog_lvrow, cursor,
                new String[]{"_id","KT03_01_004", "KT03_01_005"},
                new int[]{R.id.tv_stt,R.id.tv_ngay, R.id.tv_ca},
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

        lv_query.setAdapter(simpleCursorAdapter);

        lv_query.setOnItemClickListener((parent, view, position, id) -> {
            TextView qry_ngay = view.findViewById(R.id.tv_ngay);
            TextView qry_ca = view.findViewById(R.id.tv_ca);

            Intent KT03 = new Intent();
            KT03.setClass(context, kt03_main_activity.class);
            Bundle bundle = new Bundle();
            bundle.putString("ID", ID);
            bundle.putString("CA", qry_ca.getText().toString());
            bundle.putString("DATE", qry_ngay.getText().toString());
            KT03.putExtras(bundle);
            context.startActivity(KT03);
            dialog.dismiss();
        });
        //Danh sach đã kiểm tra (E)

        radio_a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                g_ca[0] = "1";
            }
        });
        radio_b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                g_ca[0] = "2";
            }
        });

        btn_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }

            private void showDatePickerDialog() {
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear,
                                          int dayOfMonth) {
                        //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                        tv_date.setText(year + "/" + (monthOfYear) + "/" + (dayOfMonth));
                    }
                };
                //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
                //sẽ giống với trên TextView khi mở nó lên
                String s = tv_date.getText() + "";
                String strArrtmp[] = s.split("/");
                int ngay = Integer.parseInt(strArrtmp[2]);
                int thang = Integer.parseInt(strArrtmp[1]);
                int nam = Integer.parseInt(strArrtmp[0]);
                DatePickerDialog pic = new DatePickerDialog(
                        context, AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        callback, nam, thang, ngay);
                pic.show();
            }
        });

        btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!g_ca[0].isEmpty()) {
                    Intent KT03 = new Intent();
                    KT03.setClass(context, kt03_main_activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    bundle.putString("CA", g_ca[0].toString());
                    bundle.putString("DATE", tv_date.getText().toString());
                    KT03.putExtras(bundle);
                    context.startActivity(KT03);
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Xin chọn ca làm việc", Toast.LENGTH_SHORT).show();
                }
            }
        });

        

        btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("UPLOAD");
                builder.setMessage(context.getString(R.string.M02));
                builder.setPositiveButton(context.getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        JSONObject ujobject = null;
                        uploadToServer = new UploadToServer();
                        Cursor cur_hm01 = kt03Db.getDataUpLoad_hm01();
                        JSONArray json_hm01 = uploadToServer.cur2Json(cur_hm01);
                        Cursor cur_hm02 = kt03Db.getDataUpLoad_hm02();
                        JSONArray json_hm02 = uploadToServer.cur2Json(cur_hm02);
                        Cursor cur_hm03 = kt03Db.getDataUpLoad_hm03();
                        JSONArray json_hm03 = uploadToServer.cur2Json(cur_hm03);
                        Cursor cur_hm04 = kt03Db.getDataUpLoad_hm04();
                        JSONArray json_hm04 = uploadToServer.cur2Json(cur_hm04);

                        try {
                            ujobject = new JSONObject();
                            ujobject.put("ujson_hm01", json_hm01);
                            ujobject.put("ujson_hm02", json_hm02);
                            ujobject.put("ujson_hm03", json_hm03);
                            ujobject.put("ujson_hm04", json_hm04);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (ujobject.length() > 0) {
                            String uriURL = "http://172.16.40.20/" + g_server + "/TechAPP/upload_KT03.php";
                            String res = uploadToServer.upload_all(ujobject, uriURL);
                            if (res.contains("false")) {
                                Toast.makeText(view.getContext(), view.getContext().getString(R.string.M04), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(view.getContext(), view.getContext().getString(R.string.M03), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.setNegativeButton(context.getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();

            }
        });
        dialog.show();
    }
}
