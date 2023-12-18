package com.lelong.kythuat.KT01;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.Date;


public class KT01_Main_CreateTabLayout extends AppCompatActivity {
    private Create_Table createTable = null;
    TabLayout tabLayout;
    ViewPager viewPager;
    Cursor cur;
    String g_date, g_BP, g_tobp, g_lang, g_layout;
    SimpleDateFormat dateFormatKT01 = new SimpleDateFormat("yyyy-MM-dd");


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

        SharedPreferences preferences = getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        if (language == 0) {
            g_lang = "tc_fab003";
        } else {
            g_lang = "tc_fab004";
        }

        Bundle getbundle = getIntent().getExtras();
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

        //ADD tablayout(S)
        cur = createTable.getAll_tc_fab("KT01");
        cur.moveToFirst();
        int num = cur.getCount();
        for (int i = 0; i < num; i++) {
            try {
                String tab_name = cur.getString(cur.getColumnIndexOrThrow(g_lang));
                tabLayout.addTab(tabLayout.newTab().setText(tab_name));
                tabLayout.setTabTextColors(Color.WHITE, Color.WHITE);
            } catch (Exception e) {
                String err = e.toString();
            }
            cur.moveToNext();
        }
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        final KT01_Main_CreateTabLayout_Fragment adapter = new KT01_Main_CreateTabLayout_Fragment(this,
                getSupportFragmentManager(),
                tabLayout.getTabCount(),
                g_date,
                g_BP,
                g_tobp);
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