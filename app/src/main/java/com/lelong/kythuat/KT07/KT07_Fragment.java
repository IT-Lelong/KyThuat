package com.lelong.kythuat.KT07;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.lelong.kythuat.KT02.KT02_DB;
import com.lelong.kythuat.KT02.MyAdappter_KT02;
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
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class KT07_Fragment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    TextView tc_cebuser, tc_ceb06, tv_tc_ceb03, tv_tc_cebdate;
    String ID;
    int id_menu;
    SimpleDateFormat dateFormat;
    TabLayout tabLayout;
    ViewPager viewPager;
    private KT07_DB kt07Db = null;
    JSONObject ujobject;
    JSONArray jsonupload;
    String g_server = "";
    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt07_fragment);

        Bundle getbundle = getIntent().getExtras();
        ID = getbundle.getString("ID");
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        tabLayout = findViewById(R.id.tabLayout);
        //tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager = findViewById(R.id.view_pager);
        g_server = getString(R.string.server);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0); // Lấy reference đến header của NavigationView

        tc_cebuser = headerView.findViewById(R.id.tv_tc_cebuser);
        tc_ceb06 = headerView.findViewById(R.id.tv_tc_ceb06);
        tv_tc_ceb03 = headerView.findViewById(R.id.tv_tc_ceb03);
        tv_tc_cebdate = headerView.findViewById(R.id.tv_tc_cebdate);
        tc_cebuser.setText(ID);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        kt07Db = new KT07_DB(this);

        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date previousDate = calendar.getTime();

        String previousDateString = dateFormat.format(previousDate);
        String currentDateString = dateFormat.format(currentDate);
        tv_tc_ceb03.setText(previousDateString);
        tv_tc_cebdate.setText(currentDateString);

        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();

        String amPm = (hour < 12) ? "AM" : "PM";
        tc_ceb06.setText(amPm);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        /*tc_cebuser = (TextView) findViewById(R.id.tv_tc_cebuser);
        tc_ceb06 = (TextView) findViewById(R.id.tv_tc_ceb06);
        tv_tc_ceb03 = (TextView) findViewById(R.id.tv_tc_ceb03);
        tv_tc_cebdate = (TextView) findViewById(R.id.tv_tc_cebdate);
        tc_cebuser.setText(ID);
        return true;*/


        int id = item.getItemId();
        tabLayout.removeAllTabs();
        viewPager.setAdapter(null);

        switch (id) {

            case R.id.nav_nuoc:
                // Xử lý khi chọn Home
                id_menu = 1;
                TabLayout.Tab firstTab = tabLayout.newTab(); // Create a new Tab names
                firstTab.setText("DHS\n各廠用生活水度數 Bảng Thống Kê Nước Sinh Hoạt Của Toàn Xưởng"); // set the Text for the first Tab
                tabLayout.addTab(firstTab);
                TabLayout.Tab secondTab = tabLayout.newTab(); // Create a new Tab names
                secondTab.setText("DHM\n各廠用軟水度數  Bảng Thống Kê Nước Mềm Của Toàn Xưởng"); // set the Text for the first Tab
                tabLayout.addTab(secondTab);
                TabLayout.Tab thirdTab = tabLayout.newTab(); // Create a new Tab names
                thirdTab.setText("DHR\n各廠用RO水度數 Bảng Thống Kê Nước RO Của Toàn Xưởng"); // set the Text for the first Tab
                tabLayout.addTab(thirdTab);
                break;
            case R.id.nav_gas:
                // Xử lý khi chọn Home
                id_menu = 2;
                TabLayout.Tab firstTab1 = tabLayout.newTab(); // Create a new Tab names
                firstTab1.setText("DHG\n各廠用瓦斯、氧氣度數   Bảng Thống Kê Gas - Oxy Của Toàn Xưởng"); // set the Text for the first Tab
                tabLayout.addTab(firstTab1);
                break;
            case R.id.nav_dien:
                // Xử lý khi chọn Home
                id_menu = 3;
                TabLayout.Tab firstTab2 = tabLayout.newTab(); // Create a new Tab names
                firstTab2.setText("DHD\n各廠用電度數Bảng Thống Kê Điện Của Toàn Xưởng"); // set the Text for the first Tab
                tabLayout.addTab(firstTab2);
                break;
            case R.id.btndown:
                Down_Datatable();
                break;
            case R.id.btndelete:
                Delete_Datatable();
                break;
            // Thêm xử lý cho các mục khác nếu cần
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        KT07_MyAdappter adapter = new KT07_MyAdappter(this, getSupportFragmentManager(), tabLayout.getTabCount(), id_menu, ID);
        viewPager.setAdapter(adapter);
        // Liên kết TabLayout với ViewPager
        //tabLayout.setupWithViewPager(viewPager);
        //adapter.notifyDataSetChanged();
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
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //Khởi tạo menu trên thanh tiêu đề (S)

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kt07, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Down_Datatable() {
        Thread UpLoad_fia = new Thread(new Runnable() {
            @Override
            public void run() {

                Cursor upl = kt07Db.getAll_tc_cea_in();
                jsonupload = cur2Json(upl);

                try {
                    ujobject = new JSONObject();
                    //ujobject.put("docNum", edt_maCT.getText().toString());
                    ujobject.put("ujson", jsonupload);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String res = upload_all("http://172.16.40.20/" + g_server + "/TechAPP/upload_tc_ceb.php");
                if (!res.equals("False")) {
                    if (res.length() > 6) {
                        try {
                            JSONArray jsonarray = new JSONArray(res);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonObject = jsonarray.getJSONObject(i);
                                String g_tc_cea01 = jsonObject.getString("TC_CEB01"); //Loai
                                String g_tc_cea03 = jsonObject.getString("TC_CEB02"); //STT
                                String g_tc_ceb03 = jsonObject.getString("TC_CEB03"); //Ngày tiêu hao
                                String g_tc_cebdate = jsonObject.getString("TC_CEBDATE"); //Ngày nhập
                                String g_tc_ceb06 = jsonObject.getString("TC_CEB06"); //Bảng số đo

                                if (g_tc_ceb06.equals("0")) {
                                    g_tc_ceb06 = "AM";
                                } else {
                                    g_tc_ceb06 = "PM";
                                }
                                kt07Db.appendUPDAE(g_tc_cea01, g_tc_cea03, "Đã chuyển", g_tc_ceb03, g_tc_cebdate, "tc_cebstatus_in", g_tc_ceb06);
                                //trangthai.setText("Chưa chuyển");
                            }
                            kt07Db.updateALL_tc_cea_in();
                            a = "ok";
                        } catch (JSONException e) {
                            String abc = e.toString();
                            e.printStackTrace();
                        }
                    } else {
                        kt07Db.updateALL_tc_cea_in();
                        a = "ok";
                    }
                }
            }
        });

        Thread Load_fia = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare(); // Chuẩn bị luồng để chạy vòng lặp sự kiện
                //getLVData();
                Looper.loop(); // Bắt đầu vòng lặp sự kiện
            }
        });
        //dialog.dismiss();

        new Thread() {
            @Override
            public void run() {
                UpLoad_fia.start();
                try {
                    UpLoad_fia.join();
                } catch (InterruptedException e) {
                }
                if (a == "ok") {
                    Load_fia.start();
                    try {
                        Load_fia.join();
                    } catch (InterruptedException e) {
                    }
                }

            }
        }.start();
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
            return "FAlSE";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private void Delete_Datatable() {
        kt07Db.delete_table_kt07("Đã chuyển");
    }
    //Khởi tạo menu trên thanh tiêu đề (E)
}