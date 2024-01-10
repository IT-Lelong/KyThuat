package com.lelong.kythuat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lelong.kythuat.KT01.KT01_Main_Menu;
import com.lelong.kythuat.KT02.login_kt02;
import com.lelong.kythuat.KT03.KT03_login;
import com.lelong.kythuat.KT04.KT04_login;
import com.lelong.kythuat.KT07.KT07_Main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Menu extends AppCompatActivity {
    private CheckAppUpdate checkAppUpdate = null;
    private Create_Table Cre_db = null;
    private KT03_login loginKt03 = null;
    private login_kt02 loginkt02 = null;
    private KT04_login loginKt04 = null;
    private KT01_Main_Menu KT01_Main_Menu = null;
    private ProgressDialog progressDialog;

    Button btn_KT01, btn_KT02, btn_KT03, btn_KT04, btn_KT05, btn_KT06, btn_KT07;
    TextView menuID;
    String ID;
    Locale locale;
    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle getbundle = getIntent().getExtras();
        ID = getbundle.getString("ID");
        addControls();
        addEvents();
        getIDname();
    }

    private void addEvents() {
        btn_KT01.setOnClickListener(btnlistener);
        btn_KT02.setOnClickListener(btnlistener);
        btn_KT03.setOnClickListener(btnlistener);
        btn_KT04.setOnClickListener(btnlistener);
        btn_KT05.setOnClickListener(btnlistener);
        btn_KT06.setOnClickListener(btnlistener);
        btn_KT07.setOnClickListener(btnlistener);
    }

    private void addControls() {
        menuID = (TextView) findViewById(R.id.menuID);
        btn_KT01 = findViewById(R.id.btn_KT01);
        btn_KT02 = findViewById(R.id.btn_KT02);
        btn_KT03 = findViewById(R.id.btn_KT03);
        btn_KT04 = findViewById(R.id.btn_KT04);
        btn_KT05 = findViewById(R.id.btn_KT05);
        btn_KT06 = findViewById(R.id.btn_KT06);
        btn_KT07 = findViewById(R.id.btn_KT07);

        Cre_db = new Create_Table(this);
        Cre_db.open();
        Cre_db.openTable();

        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        loginkt02 = new login_kt02();
        loginKt03 = new KT03_login();
        loginKt04 = new KT04_login();
        KT01_Main_Menu = new KT01_Main_Menu();
    }

    private void call_user_controls(String userControls) {
        String[] mangChuoi = userControls.split(",");

        for (String phanTu : mangChuoi) {
            if (phanTu.startsWith("btn_KT")) {
                switch (phanTu) {
                    case "btn_KT01":
                        btn_KT01.setEnabled(true); // Thiết lập trạng thái cho button
                        break;
                    case "btn_KT02":
                        btn_KT02.setEnabled(true); // Thiết lập trạng thái cho button
                        break;
                    case "btn_KT03":
                        btn_KT03.setEnabled(true); // Thiết lập trạng thái cho button
                        break;
                    case "btn_KT04":
                        btn_KT04.setEnabled(true); // Thiết lập trạng thái cho button
                        break;
                    case "btn_KT05":
                        btn_KT05.setEnabled(true); // Thiết lập trạng thái cho button
                        break;
                    case "btn_KT06":
                        btn_KT06.setEnabled(true); // Thiết lập trạng thái cho button
                        break;
                    case "btn_KT07":
                        btn_KT07.setEnabled(true); // Thiết lập trạng thái cho button
                        break;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAppUpdate = new CheckAppUpdate(this);
        checkAppUpdate.checkVersion();
    }

    private void getIDname() {
        Cursor cursor = Cre_db.getUserData(ID);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Constant_Class.UserID = ID;
            Constant_Class.UserName_zh = cursor.getString(cursor.getColumnIndexOrThrow("cpf02"));
            Constant_Class.UserName_vn = cursor.getString(cursor.getColumnIndexOrThrow("ta_cpf001"));
            Constant_Class.UserDepID = cursor.getString(cursor.getColumnIndexOrThrow("cpf29"));
            Constant_Class.UserDepName = cursor.getString(cursor.getColumnIndexOrThrow("gem02"));
            Constant_Class.UserFactory = cursor.getString(cursor.getColumnIndexOrThrow("cpf281"));
            Constant_Class.UserControls = cursor.getString(cursor.getColumnIndexOrThrow("user_control"));
            Constant_Class.UserKhau = cursor.getString(cursor.getColumnIndexOrThrow("user_group"));
            menuID.setText(ID + " " + Constant_Class.UserName_vn + "\n" + Constant_Class.UserDepName);

            call_user_controls(Constant_Class.UserControls);
        }
    }

    private final Button.OnClickListener btnlistener = new Button.OnClickListener() {
        public void onClick(View v) {
            //利用switch case方法，之後新增按鈕只需新增case即可
            switch (v.getId()) {

                case R.id.btn_KT01: {
                    Activity activity = Menu.this;
                    KT01_Main_Menu.login_dialogkt01(v.getContext(), activity);
                    break;
                }

                case R.id.btn_KT02: {
                    Activity activity = Menu.this;
                    loginkt02.login_dialogkt02(v.getContext(), menuID.getText().toString(), activity, "KT02");
                    break;
                }

                case R.id.btn_KT05: {
                    Activity activity = Menu.this;
                    loginkt02.login_dialogkt02(v.getContext(), menuID.getText().toString(), activity, "KT05");
                    break;
                }

                case R.id.btn_KT06: {
                    Activity activity = Menu.this;
                    loginkt02.login_dialogkt02(v.getContext(), menuID.getText().toString(), activity, "KT06");
                    break;
                }

                case R.id.btn_KT03: {
                    loginKt03.login_dialog(v.getContext(), menuID.getText().toString(), ID);
                    break;
                }

                case R.id.btn_KT04: {
                    loginKt04.KT04_login_dialog(v.getContext(), menuID.getText().toString(), ID);
                    break;
                }

                case R.id.btn_KT07: {
                    Intent intent_KT07 = new Intent();
                    intent_KT07.setClass(Menu.this, KT07_Main.class);
                    startActivity(intent_KT07);
                    break;
                }
            }
        }
    };

    //Khởi tạo menu trên thanh tiêu đề (S)
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opt, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_datatable:
                Cre_db.delete_table();
                Refresh_Datatable();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Refresh_Datatable() {
        progressDialog = new ProgressDialog(Menu.this);
        progressDialog.setMessage("Loading..."); // Thông điệp hiển thị trong ProgressDialog
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // Loại hiển thị ProgressBar
        progressDialog.setCancelable(false); // Không cho phép người dùng hủy bỏ ProgressDialog

        // Hiển thị ProgressDialog
        progressDialog.show();

        Thread api = new Thread(new Runnable() {
            @Override
            public void run() {
                get_fab();
                get_fac();
                get_gem();
                get_fia();
                get_tc_fba();
                get_tc_cea();
                get_cpf();
            }

            private void get_fab() {
                String res_fab = get_DataTable("http://172.16.40.20/" + Constant_Class.server + "/TechAPP/getDataTable.php?item=fapb");
                if (!res_fab.equals("FALSE")) {
                    try {
                        JSONArray jsonarray = new JSONArray(res_fab);
                        runOnUiThread(() -> {
                            progressDialog.setMessage("Fetching Data : 1/7");
                            progressDialog.setMax(jsonarray.length());
                        });
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            String g_tc_fab001 = jsonObject.getString("TC_FAB001"); //Mã báo biểu
                            String g_tc_fab002 = jsonObject.getString("TC_FAB002"); //Mã hạng mục
                            String g_tc_fab003 = jsonObject.getString("TC_FAB003"); //Tên hạng mục( tiếng hoa)
                            String g_tc_fab004 = jsonObject.getString("TC_FAB004"); //Tên hạng mục( tiếng việt)

                            Cre_db.append(g_tc_fab001, g_tc_fab002, g_tc_fab003, g_tc_fab004);

                            // Cập nhật giá trị tiến trình của ProgressDialog
                            final int progress = i + 1; // i bắt đầu từ 0, cần + 1 để điều chỉnh
                            runOnUiThread(() -> progressDialog.setProgress(progress));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void get_fac() {
                String res_fac = get_DataTable("http://172.16.40.20/" + Constant_Class.server + "/TechAPP/getDataTable.php?item=fac");
                if (!res_fac.equals("FALSE")) {
                    try {
                        JSONArray jsonarray = new JSONArray(res_fac);
                        runOnUiThread(() -> {
                            progressDialog.setMessage("Fetching Data : 2/7");
                            progressDialog.setMax(jsonarray.length());
                        });
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            String g_tc_fac001 = jsonObject.getString("TC_FAC001"); //Mã hạng mục
                            String g_tc_fac002 = jsonObject.getString("TC_FAC002"); //Mã báo biểu
                            String g_tc_fac003 = jsonObject.getString("TC_FAC003"); //Mã hạng mục chi tiết
                            String g_tc_fac004 = jsonObject.getString("TC_FAC004"); //Mã tổng
                            String g_tc_fac005 = jsonObject.getString("TC_FAC005"); //Tên hạng mục chi tiết( tiếng hoa)
                            String g_tc_fac006 = jsonObject.getString("TC_FAC006"); //Tên hạng mục chi tiết( tiếng việt)
                            String g_tc_fac007 = jsonObject.getString("TC_FAC007"); //Điểm số
                            String g_tc_fac008 = jsonObject.getString("TC_FAC008"); //Hãng sản xuất
                            String g_tc_fac011 = jsonObject.getString("TC_FAC011"); //Dãy đo thiết bị

                            Cre_db.append(g_tc_fac001, g_tc_fac002, g_tc_fac003,
                                    g_tc_fac004, g_tc_fac005, g_tc_fac006,
                                    g_tc_fac007, g_tc_fac008, g_tc_fac011);

                            // Cập nhật giá trị tiến trình của ProgressDialog
                            final int progress = i + 1; // i bắt đầu từ 0, cần + 1 để điều chỉnh
                            runOnUiThread(() -> progressDialog.setProgress(progress));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void get_gem() {
                String res_gem = get_DataTable("http://172.16.40.20/" + Constant_Class.server + "/TechAPP/getDataTable.php?item=gem");
                if (!res_gem.equals("FALSE")) {
                    try {
                        JSONArray jsonarray = new JSONArray(res_gem);
                        runOnUiThread(() -> {
                            progressDialog.setMessage("Fetching Data : 3/7");
                            progressDialog.setMax(jsonarray.length());
                        });
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            String g_gem01 = jsonObject.getString("GEM01"); //Mã bộ phận
                            String g_gem02 = jsonObject.getString("GEM02"); //Tên bộ phận
                            Cre_db.append(g_gem01, g_gem02);
                            // Cập nhật giá trị tiến trình của ProgressDialog
                            final int progress = i + 1; // i bắt đầu từ 0, cần + 1 để điều chỉnh
                            runOnUiThread(() -> progressDialog.setProgress(progress));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void get_fia() {
                String res_fia = get_DataTable("http://172.16.40.20/" + Constant_Class.server + "/TechAPP/getDataTable.php?item=fia");
                if (!res_fia.equals("FALSE")) {
                    try {
                        JSONArray jsonarray = new JSONArray(res_fia);
                        runOnUiThread(() -> {
                            progressDialog.setMessage("Fetching Data : 4/7");
                            progressDialog.setMax(jsonarray.length());
                        });
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            String g_fia01 = jsonObject.getString("FIA01"); //Mã số thiết bị
                            String g_ta_fia02_1 = jsonObject.getString("TA_FIA02_1"); //Mã số thiết bị
                            String g_fiaud03 = jsonObject.getString("FIAUD03"); //Số máy
                            String g_fia15 = jsonObject.getString("FIA15"); //Vị trí
                            String g_fka02 = jsonObject.getString("FKA02"); //Tên bộ phận
                            Cre_db.append(g_fia01, g_ta_fia02_1, g_fiaud03, g_fia15, g_fka02);
                            final int progress = i + 1; // i bắt đầu từ 0, cần + 1 để điều chỉnh
                            runOnUiThread(() -> progressDialog.setProgress(progress));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void get_tc_fba() {
                String res_tc_fba = get_DataTable("http://172.16.40.20/" + Constant_Class.server + "/TechAPP/getDataTable.php?item=tc_fba");
                if (!res_tc_fba.equals("FALSE")) {
                    try {
                        JSONArray jsonarray = new JSONArray(res_tc_fba);
                        runOnUiThread(() -> {
                            progressDialog.setMessage("Fetching Data : 5/7");
                            progressDialog.setMax(jsonarray.length());
                        });
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            String g_tc_fba007 = jsonObject.getString("TC_FBA007"); //Mã bộ phận
                            String g_tc_fba009 = jsonObject.getString("TC_FBA009"); //Tên bộ phận
                            Cre_db.append1(g_tc_fba007, g_tc_fba009);
                            final int progress = i + 1; // i bắt đầu từ 0, cần + 1 để điều chỉnh
                            runOnUiThread(() -> progressDialog.setProgress(progress));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void get_tc_cea() {
                String res_tc_cea = get_DataTable("http://172.16.40.20/" + Constant_Class.server + "/TechAPP/getDataTable.php?item=tc_cea");
                if (!res_tc_cea.equals("FALSE")) {
                    try {
                        JSONArray jsonarray = new JSONArray(res_tc_cea);
                        runOnUiThread(() -> {
                            progressDialog.setMessage("Fetching Data : 6/7");
                            progressDialog.setMax(jsonarray.length());
                        });
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            String g_tc_cea01 = jsonObject.getString("TC_CEA01"); //Loai
                            String g_tc_cea02 = jsonObject.getString("TC_CEA02"); //Tên Loai
                            String g_tc_cea03 = jsonObject.getString("TC_CEA03"); //STT
                            String g_tc_cea04 = jsonObject.getString("TC_CEA04"); //Nhà máy
                            String g_tc_cea05 = jsonObject.getString("TC_CEA05"); //Mã số
                            String g_tc_cea06 = jsonObject.getString("TC_CEA06"); //Chỉ tiêu ngày
                            String g_tc_cea07 = jsonObject.getString("TC_CEA07"); //Ghi chú
                            String g_tc_cea08 = jsonObject.getString("TC_CEA08"); //Đơn vị
                            String g_tc_cea09 = jsonObject.getString("TC_CEA09"); //Xưởng

                            Cre_db.append2(g_tc_cea01, g_tc_cea02, g_tc_cea03, g_tc_cea04,
                                    g_tc_cea05, g_tc_cea06, g_tc_cea07, g_tc_cea08, g_tc_cea09);
                            final int progress = i + 1; // i bắt đầu từ 0, cần + 1 để điều chỉnh
                            runOnUiThread(() -> progressDialog.setProgress(progress));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            private void get_cpf() {
                String res_cpf = get_DataTable("http://172.16.40.20/" + Constant_Class.server + "/TechAPP/getDataTable.php?item=cpf");
                if (!res_cpf.equals("FALSE")) {
                    try {
                        JSONArray jsonarray = new JSONArray(res_cpf);
                        runOnUiThread(() -> {
                            progressDialog.setMessage("Fetching Data : 7/7");
                            progressDialog.setMax(jsonarray.length());
                        });
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            String g_cpf01 = jsonObject.getString("CPF01"); //Mã bộ phận
                            String g_cpf02 = jsonObject.getString("CPF02"); //Tên bộ phận
                            String g_ta_cpf001 = jsonObject.getString("TA_CPF001"); //Mã bộ phận
                            String g_cpf29 = jsonObject.getString("CPF29"); //Tên bộ phận
                            String g_gem02 = jsonObject.getString("GEM02"); //Mã bộ phận
                            String g_cpf281 = jsonObject.getString("CPF281"); //Tên bộ phận
                            String g_usercontrols = jsonObject.getString("TC_QRS007"); //Tài khoản
                            String g_group = jsonObject.getString("TC_QRS004"); //Tổ
                            Cre_db.ins_cpf_file(g_cpf01, g_cpf02, g_ta_cpf001, g_cpf29, g_gem02, g_cpf281, g_usercontrols , g_group);
                            final int progress = i + 1; // i bắt đầu từ 0, cần + 1 để điều chỉnh
                            runOnUiThread(() -> progressDialog.setProgress(progress));

                            if(i == jsonarray.length()-1){
                                progressDialog.dismiss();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Menu.this, "Cập nhật hoàn thành", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        api.start();
    }


    private String get_DataTable(String s) {
        try {
            HttpURLConnection conn = null;
            URL url = new URL(s);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(999999);
            conn.setReadTimeout(999999);
            conn.setDoInput(true); //允許輸入流，即允許下載
            conn.setDoOutput(true); //允許輸出流，即允許上傳
            conn.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String jsonstring = reader.readLine();
            reader.close();
            if (!jsonstring.equals("FALSE")) {
                return jsonstring;
            } else {
                return "FALSE";
            }
        } catch (Exception e) {
            return "FALSE";
        }
    }
    //Khởi tạo menu trên thanh tiêu đề (E)

    private void setLanguage() {
        SharedPreferences preferences = getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        switch (language) {
            case 0:
                configuration.setLocale(Locale.TRADITIONAL_CHINESE);
                break;
            case 1:
                locale = new Locale("vi");
                Locale.setDefault(locale);
                configuration.setLocale(locale);
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }
}