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
        /*switch (position) {
            case 0:
                mainData mainDataFragment = new mainData(position);
                return mainDataFragment;
            default:
                return null;
        }*/
        mainData mainDataFragment = new mainData(position);
        return mainDataFragment;
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
