package com.lelong.kythuat.KT01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.KT01.Retrofit2.DataClient;
import com.lelong.kythuat.KT02.Retrofit2.APIYtils_Sig;
import com.lelong.kythuat.KT02.Retrofit2.DataClient_Sig;
import com.lelong.kythuat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import okhttp3.ResponseBody;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KT01_Signature_Main extends AppCompatActivity implements KT01_Interface {
    private Create_Table createTable = null;
    private KT01_DB kt01Db = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    KT01_Signature_Main_Adapter kt01SignatureMainAdapter;
    ListView lv_signature_main;
    List hangmuc_list;
    Cursor getData_cur;
    JSONObject ujobject;
    JSONArray jsonupload;
    DataClient apiService;
    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt01_signature_main);

        addControls();
        addEvets();

    }
    //Khởi tạo menu trên thanh tiêu đề (S)
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kt01, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu2:
                Update_tc_faiup();
                break;
            case R.id.menu3:
                delete_data();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void Update_tc_faiup() {
        Thread UpLoad_fia_up = new Thread(new Runnable() {
            @Override
            public void run() {
//INSERT TB TC_FAO
                Cursor upl = kt01Db.getAll_fiaupnot_sig("");
                jsonupload = cur2Json(upl);

                try {
                    ujobject = new JSONObject();
                    //ujobject.put("docNum", edt_maCT.getText().toString());
                    ujobject.put("ujson", jsonupload);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String res = upload_all("http://172.16.40.20/" + Constant_Class.server + "/TechAPP/upload_tc_fao.php");
                if (!res.equals("FALSE")) {
                    if (res.length() > 6) {
                        runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                            @Override
                            public void run() {
                                try {

                                    JSONArray jsonarray = new JSONArray(res);
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonObject = jsonarray.getJSONObject(i);
                                        String g_tc_faO001 = jsonObject.getString("TC_FAO001"); //Số máy
                                        String g_tc_faO002 = jsonObject.getString("TC_FAO002"); //Mã bộ phận
                                        String g_tc_faO004 = jsonObject.getString("TC_FAO004"); //Ngay

                                        kt01Db.update_tc_fiaup_sig(g_tc_faO001, g_tc_faO002, g_tc_faO004, "Đã chuyển");

                                        //trangthai.setText("Chưa chuyển");
                                    }
                                    Toast.makeText(getApplicationContext(), R.string.ERRORtvStatus_errorins, Toast.LENGTH_SHORT).show();
                                    kt01Db.update_tc_fiaup1_sig();
                                    a = "ok";
                                } catch (JSONException e) {
                                    String abc = e.toString();
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        //Toast.makeText(getApplicationContext(), R.string.ERRORtvStatus_false_sig, Toast.LENGTH_SHORT).show();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), R.string.ERRORtvStatus_false_sig, Toast.LENGTH_SHORT).show();
                            }
                        });
                        a = "ok";
                    }
                }
            }
        });

        Thread Load_fia_up = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare(); // Chuẩn bị luồng để chạy vòng lặp sự kiện
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadData_Search_Sig();
                        //LV_Detail_sig();
                    }
                });

                Looper.loop(); // Bắt đầu vòng lặp sự kiện
            }
        });
        //dialog.dismiss();

        new Thread() {
            @Override
            public void run() {
                UpLoad_fia_up.start();
                try {
                    UpLoad_fia_up.join();
                } catch (InterruptedException e) {
                }
                if(a == "ok"){
                    Load_fia_up.start();
                    try {
                        Load_fia_up.join();
                    } catch (InterruptedException e) {
                    }
                }

            }
        }.start();
    }

    private void delete_data() {
        kt01Db.del_fiaup_sig();
        loadData_Search_Sig();
        //LV_Detail_sig();
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
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(is));
            String result = reader1.readLine();
            reader1.close();
            return result;
        } catch (Exception ex) {
            return "FALSE";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private void addEvets() {
        kt01Db = new KT01_DB(getApplicationContext());
        kt01Db.open();
        Call_getData();
    }

    private void Call_getData() {
        hangmuc_list= new ArrayList<KT01_Signature_Main_Item>();
        getData_cur = kt01Db.getDepartmetData(Constant_Class.UserFactory);
        Drawable drawable_blue = getResources().getDrawable(R.drawable.button_kt_blue);
        Drawable drawable_green = getResources().getDrawable(R.drawable.button_kt_green);
        kt01SignatureMainAdapter = new KT01_Signature_Main_Adapter(this,
                R.layout.kt01_signature_main_rowitem,
                getData_cur,
                new String[]{"_id", "tc_fba007", "tc_fba009","ngaysig","manv_sig"},
                new int[]{R.id.tv_stt, R.id.tv_mabp,R.id.tv_tenbp,R.id.tv_ngaysig,R.id.tv_manv},
                drawable_blue,
                drawable_green,
                (KT01_Interface) this );
        kt01SignatureMainAdapter.setViewBinder((view, getData_cur, columnIndex) -> {
            if (view.getId() == R.id.tv_stt) {
                int rowNumber = getData_cur.getPosition() + 1;
                ((TextView) view).setText(String.valueOf(rowNumber));
                return true;
            }
            return false;
        });
        lv_signature_main.setAdapter(kt01SignatureMainAdapter);

    }

    private void addControls() {
        lv_signature_main=findViewById(R.id.lv_signature_main);
    }

    @Override
    public void takefoto(Context applicationContext, String key) {

    }

    @Override
    public void loadData_Sig() {

    }

    @Override
    public void loadData_Search_Sig() {
        kt01SignatureMainAdapter.notifyDataSetChanged();
    }
}