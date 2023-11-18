package com.lelong.kythuat.KT07;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class KT07_MyAdappter extends FragmentPagerAdapter {
    Context context;
    int totalTabs,itemId;
    private String data,id;
    public KT07_MyAdappter(Context c, FragmentManager fm, int totalTabs, int itemId,String id) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
        this.itemId=itemId;
        this.id=id;
        getItem(totalTabs);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        KT07_MyFragment kt07_myFragment = new KT07_MyFragment("",position,itemId,id);
        return kt07_myFragment;
    }

    @Override
    public int getCount() {
        return totalTabs;
    }

    public void setData(String data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
