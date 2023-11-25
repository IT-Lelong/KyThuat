package com.lelong.kythuat.KT01;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class KT01_Main_CreateTabLayout_Fragment extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    private Context context1;
    private String data, g_date, g_BP, g_to_BP;

    public KT01_Main_CreateTabLayout_Fragment(Context c, FragmentManager fm, int totalTabs, String g_date, String g_BP, String g_to_BP) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
        this.data = data;
        this.g_date = g_date;
        this.g_BP = g_BP;
        this.g_to_BP = g_to_BP;
    }

    public void setData(String data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public interface MyAdapterListener {
        void onDataChanged(String data);
    }

    public Fragment getItem(int position) {
        KT01_Fragment_Create _kt01FragmenCreate = new KT01_Fragment_Create(position, g_date, g_BP, g_to_BP);
        return _kt01FragmenCreate;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
