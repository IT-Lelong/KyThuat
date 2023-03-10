package com.lelong.kythuat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lelong.kythuat.KT01.Log_BoPhan;
import com.lelong.kythuat.KT02.login_kt02;
import com.lelong.kythuat.KT03.KT03_login;
import com.lelong.kythuat.KT04.KT04_login;

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
    String g_server = "";
    Button btn_KT01, btn_KT02, btn_KT03, btn_KT04;
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
        //actionBar = getSupportActionBar();
        //actionBar.hide();

        ID = getbundle.getString("ID");
        g_server= getString(R.string.server);
        menuID = (TextView) findViewById(R.id.menuID);
        new IDname().execute("http://172.16.40.20/" + g_server + "/getid.php?ID=" + ID);
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        Cre_db = new Create_Table(this);
        Cre_db.open();
        Cre_db.openTable();

        loginkt02 = new login_kt02();
        loginKt03 = new KT03_login();
        loginKt04 = new KT04_login();

        btn_KT01 = findViewById(R.id.btn_KT01);
        btn_KT02 = findViewById(R.id.btn_KT02);
        btn_KT03 = findViewById(R.id.btn_KT03);
        btn_KT04 = findViewById(R.id.btn_KT04);

        btn_KT01.setOnClickListener(btnlistener);
        btn_KT02.setOnClickListener(btnlistener);
        btn_KT03.setOnClickListener(btnlistener);
        btn_KT04.setOnClickListener(btnlistener);

    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAppUpdate = new CheckAppUpdate(this, g_server);
        checkAppUpdate.checkVersion();
    }

    //?????????????????????
    private class IDname extends AsyncTask<String, Integer, String> {
        String result = "";

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                result = reader.readLine();
                reader.close();
            } catch (Exception e) {
                result = "";
            } finally {
                return result;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        protected void onPostExecute(String result) {
            menuID.setText(result);
        }
    }

    private final Button.OnClickListener btnlistener = new Button.OnClickListener() {
        public void onClick(View v) {
            //??????switch case???????????????????????????????????????case??????
            switch (v.getId()) {

                case R.id.btn_KT01: {
                    Intent KT01 = new Intent();
                    KT01.setClass(Menu.this, Log_BoPhan.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    bundle.putString("SERVER", g_server);
                    KT01.putExtras(bundle);
                    startActivity(KT01);
                    break;
                }

                case R.id.btn_KT02: {
                    Activity activity = Menu.this;
                    loginkt02.login_dialogkt02(v.getContext(),
                            menuID.getText().toString(),activity);
                    break;
                }


                case R.id.btn_KT03: {
                    loginKt03.login_dialog(v.getContext(),
                            menuID.getText().toString(),
                            ID);
                    break;
                }

                case R.id.btn_KT04: {
                    loginKt04.KT04_login_dialog(v.getContext(),
                            menuID.getText().toString(),
                            ID);
                    break;
                }
               /* case R.id.btn_KT04: {
                    Intent QR010 = new Intent();
                    QR010.setClass(Menu.this, KT_1.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", ID);
                    bundle.putString("SERVER", g_server);
                    QR010.putExtras(bundle);
                    startActivity(QR010);
                    break;
                }*/
            }
        }
    };


    //Kh???i t???o menu tr??n thanh ti??u ????? (S)
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
        Thread api = new Thread(new Runnable() {
            @Override
            public void run() {
                String res_fab = get_DataTable("http://172.16.40.20/PHPtest/TechAPP/getDataTable.php?item=fab");
                if (!res_fab.equals("FALSE")) {
                    try {
                        JSONArray jsonarray = new JSONArray(res_fab);
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonObject = jsonarray.getJSONObject(i);
                            String g_tc_fab001 = jsonObject.getString("TC_FAB001"); //M?? b??o bi???u
                            String g_tc_fab002 = jsonObject.getString("TC_FAB002"); //M?? h???ng m???c
                            String g_tc_fab003 = jsonObject.getString("TC_FAB003"); //T??n h???ng m???c( ti???ng hoa)
                            String g_tc_fab004 = jsonObject.getString("TC_FAB004"); //T??n h???ng m???c( ti???ng vi???t)

                            Cre_db.append(g_tc_fab001, g_tc_fab002, g_tc_fab003, g_tc_fab004);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String res_fac = get_DataTable("http://172.16.40.20/PHPtest/TechAPP/getDataTable.php?item=fac");
                    if (!res_fac.equals("FALSE")) {
                        try {
                            JSONArray jsonarray = new JSONArray(res_fac);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonObject = jsonarray.getJSONObject(i);
                                String g_tc_fac001 = jsonObject.getString("TC_FAC001"); //M?? h???ng m???c
                                String g_tc_fac002 = jsonObject.getString("TC_FAC002"); //M?? b??o bi???u
                                String g_tc_fac003 = jsonObject.getString("TC_FAC003"); //M?? h???ng m???c chi ti???t
                                String g_tc_fac004 = jsonObject.getString("TC_FAC004"); //M?? t???ng
                                String g_tc_fac005 = jsonObject.getString("TC_FAC005"); //T??n h???ng m???c chi ti???t( ti???ng hoa)
                                String g_tc_fac006 = jsonObject.getString("TC_FAC006"); //T??n h???ng m???c chi ti???t( ti???ng vi???t)
                                String g_tc_fac007 = jsonObject.getString("TC_FAC007"); //??i???m s???
                                String g_tc_fac008 = jsonObject.getString("TC_FAC008"); //H??ng s???n xu???t
                                String g_tc_fac011 = jsonObject.getString("TC_FAC011"); //D??y ??o thi???t b???

                                Cre_db.append(g_tc_fac001, g_tc_fac002, g_tc_fac003,
                                        g_tc_fac004, g_tc_fac005, g_tc_fac006,
                                        g_tc_fac007, g_tc_fac008, g_tc_fac011);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String res_gem = get_DataTable("http://172.16.40.20/PHPtest/TechAPP/getDataTable.php?item=gem");
                        if (!res_gem.equals("FALSE")) {
                            try {
                                JSONArray jsonarray = new JSONArray(res_gem);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonObject = jsonarray.getJSONObject(i);
                                    String g_gem01 = jsonObject.getString("GEM01"); //M?? b??? ph???n
                                    String g_gem02 = jsonObject.getString("GEM02"); //T??n b??? ph???n
                                    Cre_db.append(g_gem01, g_gem02);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        String res_fia = get_DataTable("http://172.16.40.20/PHPtest/TechAPP/getDataTable.php?item=fia");
                        if (!res_fia.equals("FALSE")) {
                            try {
                                JSONArray jsonarray = new JSONArray(res_fia);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonObject = jsonarray.getJSONObject(i);
                                    String g_fia01 = jsonObject.getString("FIA01"); //M?? s??? thi???t b???
                                    String g_fiaud03 = jsonObject.getString("FIAUD03"); //S??? m??y
                                    String g_fia14 = jsonObject.getString("FIA14"); //V??? tr??
                                    String g_fia10 = jsonObject.getString("FIA10"); //Ng?????i ph??? tr??ch
                                    String g_gen02 = jsonObject.getString("GEN02"); //H??? t??n
                                    String g_fia11 = jsonObject.getString("FIA11"); //B??? ph???n ph??? tr??ch
                                    String g_gem02 = jsonObject.getString("GEM02"); //T??n b??? ph???n
                                    String g_fiaud07 = jsonObject.getString("FIAUD07"); //B??? ph???n qu???n l?? thi???t b???

                                    Cre_db.append(g_fia01,g_fiaud03,g_fia14,g_fia10,g_gen02,g_fia11,g_gem02,g_fiaud07 );
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

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
            conn.setDoInput(true); //?????????????????????????????????
            conn.setDoOutput(true); //?????????????????????????????????
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
    //Kh???i t???o menu tr??n thanh ti??u ????? (E)

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