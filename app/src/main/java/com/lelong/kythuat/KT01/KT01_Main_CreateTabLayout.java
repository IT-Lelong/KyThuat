package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class KT01_Main_CreateTabLayout extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    String ID, g_date, g_BP, g_tobp, g_lang, g_layout;
    Cursor cursor_1;
    SimpleDateFormat dateFormatKT01 = new SimpleDateFormat("yyyy-MM-dd");
    private Create_Table createTable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt01_tablayout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        createTable = new Create_Table(getApplicationContext());
        createTable.open();

        Bundle getbundle = getIntent().getExtras();
        ID = getbundle.getString("ID");
        g_layout = getbundle.getString("LAYOUT");
        if (g_layout.length() < 6) {
            g_date = getbundle.getString("DATE");
            g_BP = getbundle.getString("BP");
            g_tobp = getbundle.getString("TO");
        } else {
            g_date = dateFormatKT01.format(new Date()).toString();
            g_BP = getbundle.getString("BOPHAN");
            g_tobp = getbundle.getString("TO");
        }

        SharedPreferences preferences = getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        if (language == 0) {
            g_lang = "tc_fab003";
        } else {
            g_lang = "tc_fab004";
        }

        //ADD tablayout(S)
        cursor_1 = createTable.getAll_tc_fab("KT01");
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        for (int i = 0; i < num; i++) {
            try {
                String tab_name = cursor_1.getString(cursor_1.getColumnIndexOrThrow(g_lang));
                tabLayout.addTab(tabLayout.newTab().setText(tab_name));
            } catch (Exception e) {
                String err = e.toString();
            }
            cursor_1.moveToNext();
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final KT01_Main_CreateTabLayout_Fragment adapter = new KT01_Main_CreateTabLayout_Fragment(this, getSupportFragmentManager(), tabLayout.getTabCount(), g_date, g_BP, g_tobp);
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
        //ADD tablayout(E)
    }
}