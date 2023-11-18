package com.lelong.kythuat.KT07;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.lelong.kythuat.KT02.MyAdappter_KT02;
import com.lelong.kythuat.R;

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

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
                id_menu=1;
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
                id_menu=2;
                TabLayout.Tab firstTab1 = tabLayout.newTab(); // Create a new Tab names
                firstTab1.setText("DHG\n各廠用瓦斯、氧氣度數   Bảng Thống Kê Gas - Oxy Của Toàn Xưởng"); // set the Text for the first Tab
                tabLayout.addTab(firstTab1);
                break;
            case R.id.nav_dien:
                // Xử lý khi chọn Home
                id_menu=3;
                TabLayout.Tab firstTab2 = tabLayout.newTab(); // Create a new Tab names
                firstTab2.setText("DHD\n各廠用電度數Bảng Thống Kê Điện Của Toàn Xưởng"); // set the Text for the first Tab
                tabLayout.addTab(firstTab2);
                break;
            // Thêm xử lý cho các mục khác nếu cần
        }

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        KT07_MyAdappter adapter = new KT07_MyAdappter(this, getSupportFragmentManager(), tabLayout.getTabCount(),id_menu,ID);
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
}