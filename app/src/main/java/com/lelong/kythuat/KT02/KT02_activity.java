package com.lelong.kythuat.KT02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
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
import java.text.SimpleDateFormat;
import java.util.Date;
public class KT02_activity extends AppCompatActivity {
    String somay, bophan,g_lang;
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView tvStatus;
    Button button;
    Cursor cursor_1, cursor_2;
    private Create_Table createTable = null;
    private KT02_DB createTable2 = null;
    JSONArray jsonupload;
    JSONObject ujobject;
    int chk_dialog = -1;
    String g_server = "";
    SimpleDateFormat dateFormatKT02 = new SimpleDateFormat("yyyy/MM/dd");
    KT02_Interface kt02_interface;
    Fragment_KT02 fragmentKt02=null;
    private Fragment_KT02 context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kt02);

        SharedPreferences preferences = getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        if (language == 0) {
            g_lang = "tc_fab003";
        } else {
            g_lang = "tc_fab004";
        }

        String ngay = dateFormatKT02.format(new Date()).toString();
        kt02_interface = (KT02_Interface) context;



        Bundle getBundle = getIntent().getExtras();
        somay = getBundle.getString("somay");
        bophan = getBundle.getString("bophan");
        tvStatus = findViewById(R.id.tvStatus);
        tvStatus.setText(null);

        fragmentKt02 = new Fragment_KT02(somay,bophan,0);
        //kt02_interface = (KT02_Interface) fragmentKt02.getApplicationContext();;


        g_server = getString(R.string.server);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        createTable = new Create_Table(getApplicationContext());
        createTable2 = new KT02_DB(getApplicationContext());
        //KT01_DB createTable1 = new KT01_DB(this);
        //KT02_DB createTable2 = new KT02_DB(this);

        createTable.open();
        createTable2.open();
        cursor_1 = createTable.getAll_tc_fab("KT02");
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        for (int i = 0; i < num; i++) {
        //for (int i = 1; i <= num; i++) {
            try {
                @SuppressLint("Range") String tab_name = cursor_1.getString(cursor_1.getColumnIndex(g_lang));
                @SuppressLint("Range") String tab_name_TH = cursor_1.getString(cursor_1.getColumnIndex("tc_fab003"));
                @SuppressLint("Range") String matab = cursor_1.getString(cursor_1.getColumnIndex("tc_fab002"));
                tabLayout.addTab(tabLayout.newTab().setText(tab_name));
            } catch (Exception e) {
                String err = e.toString();
            }
            cursor_1.moveToNext();
        }
        //tabLayout.addTab(tabLayout.newTab().setText("Football"));
        //tabLayout.addTab(tabLayout.newTab().setText("Cricket"));
        //tabLayout.addTab(tabLayout.newTab().setText("NBA"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final MyAdappter_KT02 adapter = new MyAdappter_KT02(this, getSupportFragmentManager(), tabLayout.getTabCount(), somay, bophan);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

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
                Update_tc_fad();
                break;
            /*case R.id.menu2:
                update_data();
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    private void Update_tc_fad() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ngay = dateFormatKT02.format(new Date()).toString();
                //tham số Y , biểu thị cập nhật dữ liệu tới chương trình gốc, và save đến qrf_file
                Cursor upl = createTable.getAll_instc_fad(bophan, somay, ngay);
                jsonupload = cur2Json(upl);

                try {
                    ujobject = new JSONObject();
                    //ujobject.put("docNum", edt_maCT.getText().toString());
                    ujobject.put("ujson", jsonupload);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String res = upload_all("http://172.16.40.20/" + g_server + "/TechAPP/upload.php");
                runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                    @Override
                    public void run() {
                        if (res.contains("FALSE")) {
                            //tvStatus.setText(getString(R.string.E10));
                            tvStatus.setText(getString(R.string.ERRORtvStatus_false));
                            tvStatus.setTextColor(getResources().getColor(R.color.red));
                        }
                        if(res.contains("ERROINS")){
                                //tvStatus.setText("đã được insert");
                            tvStatus.setText(getString(R.string.ERRORtvStatus_errorins));
                            tvStatus.setTextColor(getResources().getColor(R.color.red));
                        }
                        if(res.contains("TRUE")) {
                            //tvStatus.setText(g_server);
                            tvStatus.setText(getString(R.string.ERRORtvStatus_true));
                            tvStatus.setTextColor(getResources().getColor(R.color.purple_200));
                            createTable2.delete_table();
                            //kt02_interface.mangLV_clear(true);
                            //Fragment_KT02 fragment_kt02 = new Fragment_KT02();
                            /*KT02_Interface kt02Interface=new KT02_Interface() {
                                @Override
                                public void mangLV_clear(boolean res) {
                                    mangLV_clear(true);
                                }
                            };*/
                            //kt02_interface.mangLV_clear(true);
                            //fragment_kt02.abcClear();

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

   /* @Override
    public void mangLV_clear(boolean res) {
        Fragment_KT02 fragmentKt021= new Fragment_KT02();
        if (res){
            if (fragmentKt021.mangLV02.size() > 0 || fragmentKt021.mangLV02.isEmpty()) {
                fragmentKt021.mangLV02.clear();

                //mangLV02.remove(mangLV02.get(position));
                fragmentKt021.adapter.notifyDataSetChanged();
            }
        }
    }*/

    //Khởi tạo menu trên thanh tiêu đề (E)
}