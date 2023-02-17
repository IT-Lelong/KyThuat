package com.lelong.kythuat.KT03;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class mainAdapter extends FragmentPagerAdapter {

    Context context;
    int totalTabs;
    public mainAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                KT03_HM01 hangmuc01_Fragment = new KT03_HM01("01");
                return hangmuc01_Fragment;
            case 1:
                KT03_HM02 hangmuc02_Fragment = new KT03_HM02("02");
                return hangmuc02_Fragment;
            case 2:
                KT03_HM03 hangmuc03_Fragment = new KT03_HM03("03");
                return hangmuc03_Fragment;
            case 3:
                KT03_HM04 hangmuc04_Fragment = new KT03_HM04("04");
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
