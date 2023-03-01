package com.lelong.kythuat.KT02;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdappter_KT02 extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    String somay,bophan,tap;
    public MyAdappter_KT02(Context c, FragmentManager fm, int totalTabs, String somay,String bophan) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
        this.somay = somay;
        this.bophan=bophan;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {

        /*switch (position) {
            case 0:
                Fragment_KT02 fragment_kt02 = new Fragment_KT02(somay,bophan);
                return fragment_kt02;
            default:
                return null;
        }*/
        Fragment_KT02 fragment_kt02 = new Fragment_KT02(somay,bophan,position);
        return fragment_kt02;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }


}
