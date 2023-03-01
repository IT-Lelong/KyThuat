package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;


public class login_kt01 extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    String lbophan1;
    String ID;

    Cursor cursor_1, cursor_2;
    private Create_Table createTable = null;
    private KT01_DB createTable1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt01_activity_login);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        Bundle getbundle = getIntent().getExtras();
        //actionBar = getSupportActionBar();
        //actionBar.hide();
        ID = getbundle.getString("ID");

        createTable = new Create_Table(getApplicationContext());
        createTable.open();

         createTable1 = new KT01_DB(this);
        cursor_1=createTable.getAll_tc_fab("KT01");
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        for (int i = 0; i < num; i++)
        {
            try {
                @SuppressLint("Range") String tab_name =cursor_1.getString(cursor_1.getColumnIndex("tc_fab004"));
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
        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
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