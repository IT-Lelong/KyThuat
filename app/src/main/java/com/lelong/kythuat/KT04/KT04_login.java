package com.lelong.kythuat.KT04;

import static android.graphics.Color.parseColor;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.lelong.kythuat.KT03.KT03_DB;
import com.lelong.kythuat.KT03.kt03_main_activity;
import com.lelong.kythuat.R;
import com.lelong.kythuat.SetLanguage;
import com.lelong.kythuat.UploadToServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KT04_login {
    private SetLanguage setLanguage = null;
    private UploadToServer uploadToServer = null;
    String g_server = "";
    JSONObject ujobject;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public void KT04_login_dialog(Context context, String menuID, String ID) {
        //setLanguage.setLanguage();
        TextView tv_name, tv_date;
        RadioButton radio_a, radio_b;
        Button btn_Date, btn_Insert, btn_Upload;
        ListView lv_query;
        final String[] g_ca = {""};

        this.g_server = context.getString(R.string.server);


        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt04_login_dialog);

        tv_name = dialog.findViewById(R.id.tv4_name);
        tv_date = dialog.findViewById(R.id.tv4_date);
        radio_a = dialog.findViewById(R.id.radio4_a);
        radio_b = dialog.findViewById(R.id.radio4_b);
        btn_Date = dialog.findViewById(R.id.btn4_Date);
        btn_Insert = dialog.findViewById(R.id.btn4_Insert);
        btn_Upload = dialog.findViewById(R.id.btn4_Upload);
        lv_query = dialog.findViewById(R.id.lv4_query);

        tv_name.setText(menuID);
        tv_date.setText(dateFormat.format(new Date()).toString());

        KT04_DB kt04Db = new KT04_DB(context);
        kt04Db.open();
        kt04Db.openTable();

        //Danh sach đã kiểm tra (S)
        Cursor cursor = kt04Db.getAll_lvQuery();
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.kt04_login_lvrow, cursor,
                new String[]{"_id", "KT04_01_004", "KT04_01_005"},
                new int[]{R.id.tv4_stt, R.id.tv4_ngay, R.id.tv4_ca},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.tv4_stt) {
                    int rowNumber = cursor.getPosition() + 1;
                    ((TextView) view).setText(String.valueOf(rowNumber));
                    return true;
                }
                return false;
            }
        });
        lv_query.setAdapter(simpleCursorAdapter);


        lv_query.setOnItemClickListener((parent, view, position, id) -> {

            // Tạo đối tượng PopupMenu
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END, 0, R.style.MyPopupMenu);

            // Nạp tệp menu vào PopupMenu
            popupMenu.getMenuInflater().inflate(R.menu.kt03_login_lv, popupMenu.getMenu());

            // Show the PopupMenu.
            popupMenu.show();

            // Đăng ký sự kiện Popup Menu
            popupMenu.setOnMenuItemClickListener(item -> {

                TextView qry_ngay = view.findViewById(R.id.tv4_ngay);
                TextView qry_ca = view.findViewById(R.id.tv4_ca);

                // Xử lý sự kiện khi người dùng chọn một lựa chọn trong menu
                switch (item.getItemId()) {
                    case R.id.openKT03:
                        Intent KT04 = new Intent();
                        KT04.setClass(context, KT04_main_activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", ID);
                        bundle.putString("DATE", qry_ngay.getText().toString());
                        bundle.putString("CA", qry_ca.getText().toString());
                        KT04.putExtras(bundle);
                        context.startActivity(KT04);
                        dialog.dismiss();
                        return true;

                    case R.id.clearKT03:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(context.getString(R.string.M05))
                                .setPositiveButton(context.getString(R.string.btn_ok), null)
                                .setNegativeButton(context.getString(R.string.btn_cancel), null);


                        AlertDialog al_dialog = builder.create();
                        al_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                TextView messageView = ((AlertDialog) dialogInterface).findViewById(android.R.id.message);
                                messageView.setTextSize(35);

                                Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                                positiveButton.setTextColor(ContextCompat.getColor(context, R.color.black));
                                positiveButton.setTextSize(15);
                                //positiveButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                                Button negativeButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_NEGATIVE);
                                negativeButton.setTextColor(ContextCompat.getColor(context, R.color.black));
                                positiveButton.setTextSize(15);
                                //negativeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

                                positiveButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        kt04Db.delete_table(qry_ngay.getText().toString(), qry_ca.getText().toString());
                                        dialog.dismiss();
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

        //lv_query.setAdapter(simpleCursorAdapter);

        //lv_query.setOnItemClickListener((parent, view, position, id) -> {
        //TextView qry_ngay = view.findViewById(R.id.tv4_ngay);
        //TextView qry_ca = view.findViewById(R.id.tv4_ca);

        //Intent KT04 = new Intent();
        //KT04.setClass(context, KT04_main_activity.class);
        //Bundle bundle = new Bundle();
        //bundle.putString("ID", ID);
        //bundle.putString("CA", qry_ca.getText().toString());
        //bundle.putString("DATE", qry_ngay.getText().toString());
        //KT04.putExtras(bundle);
        //context.startActivity(KT04);
        //dialog.dismiss();
        //});
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
                    Intent KT04 = new Intent();
                    KT04.setClass(context, KT04_main_activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    bundle.putString("CA", g_ca[0].toString());
                    bundle.putString("DATE", tv_date.getText().toString());
                    KT04.putExtras(bundle);
                    context.startActivity(KT04);
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
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Looper.prepare();
                                ujobject = null;
                                uploadToServer = new UploadToServer();
                                Cursor cur_hm01 = kt04Db.getDataUpLoad_hm01();
                                JSONArray json_hm01 = uploadToServer.cur2Json(cur_hm01);
                                //Cursor cur_hm02 = kt04Db.getDataUpLoad_hm02();
                                //JSONArray json_hm02 = uploadToServer.cur2Json(cur_hm02);
                                Cursor cur_hm03 = kt04Db.getDataUpLoad_hm03();
                                JSONArray json_hm03 = uploadToServer.cur2Json(cur_hm03);
                                Cursor cur_hm04 = kt04Db.getDataUpLoad_hm04();
                                JSONArray json_hm04 = uploadToServer.cur2Json(cur_hm04);

                                try {
                                    ujobject = new JSONObject();
                                    ujobject.put("ujson_hm01", json_hm01);
                                    //ujobject.put("ujson_hm02", json_hm02);
                                    ujobject.put("ujson_hm03", json_hm03);
                                    ujobject.put("ujson_hm04", json_hm04);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if (ujobject.length() > 0) {

                                    String uriURL = "http://172.16.40.20/" + g_server + "/TechAPP/upload_KT04.php";
                                    final String res = upload_all(uriURL);

                                    if (res.contains("false")) {
                                        Toast.makeText(view.getContext(), view.getContext().getString(R.string.M04), Toast.LENGTH_SHORT).show();
                                    }
                                    else if(res.contains("ERROINS"))
                                    {
                                        Toast.makeText(view.getContext(), view.getContext().getString(R.string.M04), Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        kt04Db.delete_all_table();
                                        Toast.makeText(view.getContext(), view.getContext().getString(R.string.M03), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }
                                Looper.loop();
                            }
                        }).start();
                    }
                });
                builder.setNegativeButton(context.getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                //builder.show();
                AlertDialog dialog = builder.create();
                dialog.show();
                Button b = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                b.setTextColor(Color.RED);
                Button a = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                a.setTextColor(Color.BLUE);

            }
        });
        dialog.show();
    }

    public String upload_all(String apiUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(999999);
            conn.setReadTimeout(999999);
            conn.setDoInput(true); //允許輸入流，即允許下載
            conn.setDoOutput(true); //允許輸出流，即允許上傳

            OutputStream os = conn.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);
            writer.write(ujobject.toString().getBytes("UTF-8"));
            writer.flush();
            writer.close();
            os.close();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String result = reader.readLine();
            reader.close();
            return result;
        } catch (Exception ex) {
            return "false";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
