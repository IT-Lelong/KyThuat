package com.lelong.kythuat.KT03;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class mainAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;
    private final String g_date;
    private final String g_ca;
    private final String g_id;

    public mainAdapter(Context c, FragmentManager fm, int totalTabs, String g_date, String g_ca, String ID) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
        this.g_date = g_date;
        this.g_ca = g_ca;
        this.g_id = ID;
    }
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                KT03_HM01 hangmuc01_Fragment = new KT03_HM01("01",context,g_date,g_ca,g_id);
                return hangmuc01_Fragment;
            case 1:
                KT03_HM02 hangmuc02_Fragment = new KT03_HM02("02",context,g_date,g_ca,g_id);
                return hangmuc02_Fragment;
            case 2:
                KT03_HM03 hangmuc03_Fragment = new KT03_HM03("03",context,g_date,g_ca,g_id);
                return hangmuc03_Fragment;
            case 3:
                KT03_HM04 hangmuc04_Fragment = new KT03_HM04("04",context,g_date,g_ca,g_id);
                return hangmuc04_Fragment;
            default:
                return null;
        }

    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
