package com.lelong.kythuat.KT03;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
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
import com.lelong.kythuat.R;
import com.lelong.kythuat.KT02.Search_Signature_KT02;
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

public class KT03_login {
    private UploadToServer uploadToServer = null;
    String g_server = "";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    JSONObject ujobject = null;
    KT03_DB kt03Db;
    ListView lv_query;
    TextView tv_name, tv_date;
    RadioButton radio_a, radio_b;
    Button btn_Date, btn_Insert, btn_Upload,btn_Camera;
    final String[] g_ca = {""};
    private Context context;

    public void login_dialog(Context context, String menuID, String ID) {
        this.context = context;
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

        btn_Camera = dialog.findViewById(R.id.btn_camera);

        tv_name.setText(menuID);
        tv_date.setText(dateFormat.format(new Date()).toString());

        kt03Db = new KT03_DB(context);
        kt03Db.open();
        kt03Db.openTable();

        //Danh sach đã kiểm tra (S)
        getLVData();

        lv_query.setOnItemClickListener((parent, view, position, id) -> {

            // Tạo đối tượng PopupMenu
            PopupMenu popupMenu = new PopupMenu(context.getApplicationContext(), view, Gravity.END, 0, R.style.MyPopupMenu);

            // Nạp tệp menu vào PopupMenu
            popupMenu.getMenuInflater().inflate(R.menu.kt03_login_lv, popupMenu.getMenu());

            // Show the PopupMenu.
            popupMenu.show();

            // Đăng ký sự kiện Popup Menu
            popupMenu.setOnMenuItemClickListener(item -> {

                TextView qry_ngay = view.findViewById(R.id.tv_ngay);
                TextView qry_ca = view.findViewById(R.id.tv_ca);

                // Xử lý sự kiện khi người dùng chọn một lựa chọn trong menu
                switch (item.getItemId()) {
                    case R.id.openKT03:
                        Intent KT03 = new Intent();
                        KT03.setClass(context, KT03_main_activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("ID", ID);
                        bundle.putString("DATE", qry_ngay.getText().toString());
                        bundle.putString("CA", qry_ca.getText().toString());
                        KT03.putExtras(bundle);
                        context.startActivity(KT03);
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
                                        kt03Db.delete_table(qry_ngay.getText().toString(), qry_ca.getText().toString());
                                        al_dialog.dismiss();
                                        getLVData();
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
                        //tv_date.setText(year + "-" + (monthOfYear) + "-" + (dayOfMonth));
                        String formattedDate = String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        tv_date.setText(formattedDate);
                    }
                };
                //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
                //sẽ giống với trên TextView khi mở nó lên
                String s = tv_date.getText() + "";
                String strArrtmp[] = s.split("-");
                int ngay = Integer.parseInt(strArrtmp[2]);
                int thang = Integer.parseInt(strArrtmp[1])-1;
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
                    KT03.setClass(context, KT03_main_activity.class);
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

        /*btn_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent KT03 = new Intent();
                    //KT03.setClass(context, MainActivity2.class);
                    KT03.setClass(context, Search_Signature_KT02.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    bundle.putString("CA", g_ca[0].toString());
                    bundle.putString("DATE", tv_date.getText().toString());
                    KT03.putExtras(bundle);
                    context.startActivity(KT03);
                    dialog.dismiss();
            }
        });*/

        btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KT03_KetChuyen_Dialog KT03_ketChuyenDialog = new KT03_KetChuyen_Dialog(v.getContext());
                KT03_ketChuyenDialog.setEnableBtn(true,true);
                KT03_ketChuyenDialog.setOkButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        KT03_ketChuyenDialog.setEnableBtn(false,true);
                        String input_datekt = KT03_ketChuyenDialog.getSelected_sp_datekt();
                        String input_department = KT03_ketChuyenDialog.getSelected_sp_department();
                        String input_ca = KT03_ketChuyenDialog.getSelected_sp_ca();
                        new KT03_Transfer(view.getContext(),KT03_ketChuyenDialog,input_datekt,input_department,input_ca);
                    }
                });
                KT03_ketChuyenDialog.setCancelButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        KT03_ketChuyenDialog.dismiss();
                    }
                });
                KT03_ketChuyenDialog.show();
            }
        });
        /*btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("UPLOAD")
                        .setMessage(context.getString(R.string.M02))
                        .setPositiveButton(context.getString(R.string.btn_ok), null)
                        .setNegativeButton(context.getString(R.string.btn_cancel), null);

                AlertDialog al_dialog = builder.create();
                al_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        TextView messageView = al_dialog.findViewById(android.R.id.message);
                        messageView.setTextSize(30);

                        Button positiveButton = al_dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        positiveButton.setTextColor(ContextCompat.getColor(context, R.color.blue));
                        positiveButton.setTextSize(15);
                        //positiveButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        Button negativeButton = al_dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                        negativeButton.setTextColor(ContextCompat.getColor(context, R.color.red));
                        negativeButton.setTextSize(15);
                        //negativeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

                        positiveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Looper.prepare();
                                        uploadToServer = new UploadToServer();
                                        Cursor cur_hm0102 = kt03Db.getDataUpLoad_hm0102();
                                        JSONArray json_hm0102 = uploadToServer.cur2Json(cur_hm0102);
                                        Cursor cur_hm03 = kt03Db.getDataUpLoad_hm03();
                                        JSONArray json_hm03 = uploadToServer.cur2Json(cur_hm03);
                                        Cursor cur_hm04 = kt03Db.getDataUpLoad_hm04();
                                        JSONArray json_hm04 = uploadToServer.cur2Json(cur_hm04);

                                        try {
                                            ujobject = new JSONObject();
                                            ujobject.put("ujson_hm0102", json_hm0102);
                                            //ujobject.put("ujson_hm02", json_hm02);
                                            ujobject.put("ujson_hm03", json_hm03);
                                            ujobject.put("ujson_hm04", json_hm04);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        if (ujobject.length() > 0) {
                                            String uriURL = "http://172.16.40.20/" + g_server + "/TechAPP/upload_KT03.php";
                                            String res = upload_all(ujobject, uriURL);
                                            if (res.contains("FALSE")) {
                                                Toast.makeText(view.getContext(), view.getContext().getString(R.string.M04), Toast.LENGTH_SHORT).show();
                                            } else {
                                                kt03Db.delete_all_table();
                                                //kt03Db.call_upd_tc_faapost(get_tc_faa);
                                                //Hàm lấy ảnh và gửi ảnh
                                                //ketChuyenPhoto = new KetChuyenPhoto(context, get_tc_far, ketchuyenDialog);
                                                Toast.makeText(view.getContext(), view.getContext().getString(R.string.M03), Toast.LENGTH_SHORT).show();
                                                al_dialog.dismiss();
                                            }
                                        }
                                        Looper.loop();
                                    }
                                }).start();
                            }
                        });
                    }
                });

                al_dialog.show();
            }
        });*/
        dialog.show();


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialog.dismiss();
            }
        });
    }

    private void getLVData() {
        Cursor cursor = kt03Db.getAll_lvQuery();
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.kt03_login_dialog_lvrow, cursor,
                new String[]{"_id", "KT03_01_004", "KT03_01_005"},
                new int[]{R.id.tv_stt, R.id.tv_ngay, R.id.tv_ca},
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
    }

    private String upload_all(JSONObject ujobject, String uriURL) {
        String res = null;
        if (ujobject.length() > 0) {
            HttpURLConnection conn = null;
            try {
                URL url = new URL(uriURL);
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
                res = result;
            } catch (Exception ex) {
                res = "FALSE";
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        return res;
    }


}
