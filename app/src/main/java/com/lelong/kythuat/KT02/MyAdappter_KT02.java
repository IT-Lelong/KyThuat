package com.lelong.kythuat.KT02;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.text.SimpleDateFormat;

public class MyAdappter_KT02 extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    String somay,bophan,tap,ngay,g_tc_faa001;
    public MyAdappter_KT02(Context c, FragmentManager fm, int totalTabs, String somay,String bophan,String ngay,String g_tc_faa001) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
        this.somay = somay;
        this.bophan=bophan;
        this.ngay=ngay;
        this.g_tc_faa001=g_tc_faa001;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment_KT02 fragment_kt02 = new Fragment_KT02(somay,bophan,ngay,g_tc_faa001,position);
        return fragment_kt02;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }


}
