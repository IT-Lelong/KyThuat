package com.lelong.kythuat.KT07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KT07_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    TextView tc_cebuser, tc_ceb06, tv_tc_ceb03, tv_tc_cebdate;
    String ID;
    int id_menu;
    SimpleDateFormat dateFormat;
    private KT07_DB kt07Db = null;
    JSONObject ujobject;
    JSONArray jsonupload;
    String g_server = "";
    String a;
    NavigationView navigationView;
    RecyclerView rcv_hangmuc;
    KT07_Main_Adapter kt07MainAdapter;
    List<KT07_Main_RowItem> kt07MainRowItems_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt07_fragment);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Bundle getbundle = getIntent().getExtras();
        ID = getbundle.getString("ID");
        g_server = getString(R.string.server);
        kt07Db = new KT07_DB(this);
        kt07Db.open();
        kt07Db.create_table();

        addControls();

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //tabLayout.setTabMode(TabLayout.MODE_FIXED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigation_view);
        Call_navigationView_menu();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0); // Lấy reference đến header của NavigationView

        tc_cebuser = headerView.findViewById(R.id.tv_tc_cebuser);
        tc_ceb06 = headerView.findViewById(R.id.tv_tc_ceb06);
        tv_tc_ceb03 = headerView.findViewById(R.id.tv_tc_ceb03);
        tv_tc_cebdate = headerView.findViewById(R.id.tv_tc_cebdate);
        tc_cebuser.setText(ID);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        tv_tc_ceb03.setText(dateFormat.format(new Date()).toString());
        tv_tc_cebdate.setText(dateFormat.format(new Date()).toString());

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

        addEvents();
    }

    private void addControls() {
        rcv_hangmuc = findViewById(R.id.rcv_hangmuc);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(KT07_Main.this);
        rcv_hangmuc.setLayoutManager(linearLayoutManager);
        kt07MainRowItems_list = new ArrayList<KT07_Main_RowItem>();
        kt07MainAdapter = new KT07_Main_Adapter(getApplicationContext(), R.layout.kt07_listdata_item, kt07MainRowItems_list);
        rcv_hangmuc.setAdapter(kt07MainAdapter);
    }

    private void Call_navigationView_menu() {
        String g_title = null;
        String g_check = null;
        Menu menu = navigationView.getMenu();

        Cursor cursorXuong = kt07Db.get_menu_factory(ID);
        // Tạo hạng mục "Xưởng" từ Cursor
        SubMenu xuongSubMenu = menu.addSubMenu("Xưởng");
        if (cursorXuong != null && cursorXuong.moveToFirst()) {
            do {
                String tenXuong = cursorXuong.getString(cursorXuong.getColumnIndexOrThrow("tc_cea09"));
                xuongSubMenu.add(tenXuong);
            } while (cursorXuong.moveToNext());
            cursorXuong.close();
        }

        // Tạo hạng mục "Loại tiêu thụ" với các submenu "Điện", "Nước", "Gas" từ Cursor
        SubMenu loaiTieuThuSubMenu = menu.addSubMenu("Loại tiêu thụ");
        SubMenu dienSubMenu = loaiTieuThuSubMenu.addSubMenu("Điện");
        SubMenu nuocSubMenu = loaiTieuThuSubMenu.addSubMenu("Nước");
        SubMenu gasSubMenu = loaiTieuThuSubMenu.addSubMenu("Gas");
        Cursor cursorLoaiTieuThu = kt07Db.get_menu_data(ID);
        if (cursorLoaiTieuThu != null && cursorLoaiTieuThu.moveToFirst()) {
            do {
                String loai = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("loai"));
                String g_tc_cea01 = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("tc_cea01"));

                // Thêm các hạng mục vào submenu tương ứng
                if (loai.equals("dien")) {
                    dienSubMenu.add(g_tc_cea01);
                } else if (loai.equals("nuoc")) {
                    nuocSubMenu.add(g_tc_cea01);
                } else if (loai.equals("gas")) {
                    gasSubMenu.add(g_tc_cea01);
                }
            } while (cursorLoaiTieuThu.moveToNext());
            cursorLoaiTieuThu.close();
        }

//        Cursor menu_curs = kt07Db.get_menu_data(ID);
//        if (menu_curs != null && menu_curs.moveToFirst()) {
//            do {
//                // Lấy dữ liệu từ menu_curs
//                String g_tc_cea01 = menu_curs.getString(menu_curs.getColumnIndexOrThrow("tc_cea01"));
//                String loai = menu_curs.getString(menu_curs.getColumnIndexOrThrow("loai"));
//                int iconRes = 0;
//
//                /*iconRes = loai.equals("dien") ? R.drawable.dien :
//                          loai.equals("nuoc") ? R.drawable.nuoc :
//                          loai.equals("gas") ? R.drawable.gas: null ;*/
//
//                switch (loai) {
//                    case "dien":
//                        g_title = "電度數 Điện";
//                        iconRes = R.drawable.dien;
//                        break;
//                    case "nuoc":
//                        g_title = "水度數 Nước";
//                        iconRes = R.drawable.nuoc;
//                        break;
//                    case "gas":
//                        g_title = "瓦斯,氧氣度數  Gas";
//                        iconRes = R.drawable.gas;
//                        break;
//                }
//
//                // Tạo MenuItem cho tiêu đề chính
//                if (g_check == null || (!g_check.equals(loai) && g_check.length() > 0)) {
//                    SpannableString spannable_title = new SpannableString(g_title);
//                    // Thiết lập màu chữ
//                    spannable_title.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannable_title.length(), 0);
//                    // Thiết lập kiểu chữ in đậm (bold)
//                    spannable_title.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, spannable_title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    //MenuItem menuItem = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, spannable_title).setEnabled(false);
//                    MenuItem menuItem = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, spannable_title).setEnabled(false);
//                    menuItem.setIcon(iconRes);
//                }
//                SpannableString spannable = new SpannableString(g_tc_cea01);
//                spannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannable.length(), 0);
//                menu.add(Menu.NONE, Menu.NONE, Menu.NONE, spannable);
//                g_check = loai;
//            } while (menu_curs.moveToNext());
//            menu_curs.close();
//        }

        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "").setEnabled(false);
        //menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "").setEnabled(false);
        SpannableString spannable_up = new SpannableString("拋轉資料 Đăng tải dữ liệu");
        spannable_up.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannable_up.length(), 0);
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, spannable_up).setIcon(R.drawable.loaddata);

        SpannableString spannable_del = new SpannableString("刪除資料 Xóa dữ liệu");
        spannable_del.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannable_del.length(), 0);
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, spannable_del).setIcon(R.drawable.dt);
    }

    private void addEvents() {
        tv_tc_ceb03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(KT07_Main.this);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = String.format("%04d/%02d/%02d", year, month + 1, dayOfMonth);
                        tv_tc_ceb03.setText(selectedDate);
                    }
                });
                datePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String g_title = item.getTitle().toString();

        if (g_title.startsWith("DH") || g_title.startsWith("BL")) {
            Cursor cursor = kt07Db.getAll_tc_cea_data(g_title,tv_tc_cebdate.getText().toString());
            cursor.moveToFirst();
            int num = cursor.getCount();
            kt07MainRowItems_list.clear();
            for (int i = 0; i < num; i++) {
                try {
                    String G_TC_CEA03 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea03"));
                    String G_TC_CEA04 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea04"));
                    String G_TC_CEA05 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea05"));
                    String G_TC_CEA06 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea06"));
                    String G_TC_CEA08 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea08"));
                    String G_TC_CEA09 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea09"));
                    String G_TC_CEB04 = cursor.getString(cursor.getColumnIndexOrThrow("tc_ceb04"));

                    kt07MainRowItems_list.add(new KT07_Main_RowItem(G_TC_CEA03,G_TC_CEA04, G_TC_CEA05, G_TC_CEA06,G_TC_CEA08,G_TC_CEA09, G_TC_CEB04));
                } catch (Exception e) {
                    String err = e.toString();
                }

                cursor.moveToNext();
            }
            kt07MainAdapter.notifyDataSetChanged();
        }
      /*  switch (id) {

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
        }*/

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

   /* private void Down_Datatable() {
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
    }*/

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

    //Khởi tạo menu trên thanh tiêu đề (E)
}