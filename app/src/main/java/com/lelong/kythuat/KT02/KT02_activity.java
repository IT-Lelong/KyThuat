package com.lelong.kythuat.KT02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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
import com.lelong.kythuat.KT02.Retrofit2.APIYtils;
import com.lelong.kythuat.KT02.Retrofit2.DataClient;
import com.lelong.kythuat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KT02_activity extends AppCompatActivity {
    String somay, bophan, g_lang;
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
    String g_ngay, g_soxe, g_user, g_layout,ngay,g_tc_faa001 ;
    SimpleDateFormat dateFormatKT02 = new SimpleDateFormat("yyyy-MM-dd");
    KT02_Interface kt02_interface;
    Fragment_KT02 fragmentKt02 = null;
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

        Bundle getbundle = getIntent().getExtras();
        g_server = getbundle.getString("SERVER");
        g_ngay = getbundle.getString("DATE");
        g_soxe = getbundle.getString("SOMAY");
        g_user = getbundle.getString("USER");
        g_layout = getbundle.getString("LAYOUT");
        g_tc_faa001 = getbundle.getString("G_TC_FAA001");
        kt02_interface = (KT02_Interface) context;


        if (g_layout.length() <6) {
            somay = g_soxe;
            bophan = g_user;
            ngay=g_ngay;
            //tvStatus = findViewById(R.id.tvStatus);
            //tvStatus.setText(null);
        }else {
            Bundle getBundle = getIntent().getExtras();
            somay = getBundle.getString("somay");
            bophan = getBundle.getString("bophan");
            ngay = dateFormatKT02.format(new Date()).toString();
            //tvStatus = findViewById(R.id.tvStatus);
            //tvStatus.setText(null);
        }

        fragmentKt02 = new Fragment_KT02(somay, bophan,ngay,g_tc_faa001, 0);
        //kt02_interface = (KT02_Interface) fragmentKt02.getApplicationContext();;


        g_server = getString(R.string.server);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        createTable = new Create_Table(getApplicationContext());
        createTable2 = new KT02_DB(getApplicationContext());

        createTable.open();
        createTable2.open();
        cursor_1 = createTable.getAll_tc_fab(g_tc_faa001);
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        for (int i = 0; i < num; i++) {
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
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final MyAdappter_KT02 adapter = new MyAdappter_KT02(this, getSupportFragmentManager(), tabLayout.getTabCount(), somay, bophan,ngay,g_tc_faa001);
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


}
