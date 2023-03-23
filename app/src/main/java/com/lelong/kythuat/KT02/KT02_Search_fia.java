package com.lelong.kythuat.KT02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

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

public class KT02_Search_fia extends AppCompatActivity {

    private KT02_DB kt02Db = null;
    Cursor cursor_1, cursor_2;
    private Activity activity;
    ListView lv_search02;
    private Context context;
    Dialog dialog;
    Button btnkt;
    JSONObject ujobject;
    JSONArray jsonupload;
    String g_server = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kt02_searchfia);
        lv_search02 = findViewById(R.id.lv_search02);
        this.activity = activity;
        /*this.context=context;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt02_loggin_search);*/
        dialog = new Dialog(KT02_Search_fia.this);
        dialog.setContentView(R.layout.kt02_searchfia_row);
        //btnkt = dialog.findViewById(R.id.btnkt);
        //btnkt.setOnClickListener(btnlistener1);
        //Bundle getbundle = getIntent().getExtras();
        g_server  = getString(R.string.server);;

        kt02Db = new KT02_DB(getApplicationContext());

        kt02Db.open();
        //List<Search_List> search_lists = new ArrayList<>();


        //cbxbophan.setAdapter(bophan_adapter);

        cursor_1 = kt02Db.getAll_fiaup();
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(dialog.getContext(),
                R.layout.kt02_searchfia_row, cursor_1,
                new String[]{"_id", "fiaud03", "fia15", "fka02", "ngay_up","trangthai_up"},
                new int[]{R.id.tv_stt, R.id.tv_somay, R.id.tv_mabp, R.id.tv_tenbp, R.id.tv_ngay},
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
        lv_search02.setAdapter(simpleCursorAdapter);

    }

    //Khởi tạo menu trên thanh tiêu đề (S)

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kt02, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Update_tc_faiup();
                break;
            /*case R.id.menu2:
                update_data();
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    private void Update_tc_faiup() {
        new Thread(new Runnable() {
            @Override
            public void run() {
//String ngay = dateFormatKT02.format(new Date()).toString();
                //tham số Y , biểu thị cập nhật dữ liệu tới chương trình gốc, và save đến qrf_file
                Cursor upl = kt02Db.getAll_fiaupnot();
                jsonupload = cur2Json(upl);

                try {
                    ujobject = new JSONObject();
                    //ujobject.put("docNum", edt_maCT.getText().toString());
                    ujobject.put("ujson", jsonupload);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String res = upload_all("http://172.16.40.20/" + g_server + "/TechAPP/upload_tc_fan.php");
                runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                    @Override
                    public void run() {
                        if (res.contains("FALSE")) {
                            //tvStatus.setText(getString(R.string.E10));
                            //tvStatus.setText(getString(R.string.ERRORtvStatus_false));
                            //tvStatus.setTextColor(getResources().getColor(R.color.red));
                            //kt02Db.update_tc_fiaup("","","","Chưa chuyển");
                        }
                        if (res.contains("ERROINS")) {
                            //tvStatus.setText("đã được insert");
                            //tvStatus.setText(getString(R.string.ERRORtvStatus_errorins));
                            //tvStatus.setTextColor(getResources().getColor(R.color.red));
                            //createTable2.delete_table(ngay, somay, bophan);
                        }
                        if (res.contains("TRUE")) {
                            //tvStatus.setText(g_server);
                            //tvStatus.setText(getString(R.string.ERRORtvStatus_true));
                            //tvStatus.setTextColor(getResources().getColor(R.color.purple_200));
                            //createTable2.delete_table(ngay, somay, bophan);
                            //kt02Db.update_tc_fiaup("","","","Đã chuyển");
                        }
                    }
                });
            }
        }).start();

    }

    public JSONArray cur2Json(Cursor cursor) {
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        return resultSet;
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


    //Khởi tạo menu trên thanh tiêu đề (E)
}