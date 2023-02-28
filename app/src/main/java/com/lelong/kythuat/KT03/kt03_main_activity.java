package com.lelong.kythuat.KT03;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;
import com.lelong.kythuat.SetLanguage;

public class kt03_main_activity extends AppCompatActivity {
    private SetLanguage setLanguage = null;
    private Create_Table createTable = null;
    private KT03_DB kt03Db  = null;
    String ID, g_server, g_date, g_ca, g_lang;
    TabLayout tabLayout;
    ViewPager viewPager;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setLanguage.setLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt03_main_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SharedPreferences preferences = getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        if (language == 0) {
            g_lang = "tc_fab003";
        } else {
            g_lang = "tc_fab004";
        }

        Bundle getbundle = getIntent().getExtras();
        g_server = getbundle.getString("SERVER");
        g_date = getbundle.getString("DATE");
        g_ca = getbundle.getString("CA");
        ID = getbundle.getString("ID");

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        kt03Db= new KT03_DB(this);
        kt03Db.open();
        createTable = new Create_Table(this);
        createTable.open();
        Cursor cursor_tc_fab = createTable.getAll_tc_fab("KT03");
        cursor_tc_fab.moveToFirst();
        for (int i = 0; i < cursor_tc_fab.getCount(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(cursor_tc_fab.getString(cursor_tc_fab.getColumnIndex(g_lang))));
            cursor_tc_fab.moveToNext();
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final mainAdapter adapter = new mainAdapter(this, getSupportFragmentManager(),tabLayout.getTabCount(),g_date,g_ca,ID);
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

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.test, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                KT03_HM0102 kt03_hm01 = new KT03_HM0102(this);
                kt03_hm01.clear_data();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}