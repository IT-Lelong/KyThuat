package com.lelong.kythuat.KT01;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    private Context context1;
    private String data;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
        this.context1 = context1;
        this.data = data;
    }
    public void setData(String data) {
        this.data = data;
        notifyDataSetChanged();
    }
    public interface MyAdapterListener {
        void onDataChanged(String data);
    }



    public Fragment getItem(int position) {

        /*switch (position) {
            case 0:
                Fragmen_KT01 fragmen_kt01 = new Fragmen_KT01();
                return fragmen_kt01;
            /*case 1:
                Fragment_KT02 fragment_kt02 = new Fragment_KT02();
                return fragment_kt02;
            /*case 2:
                Fragmen_KT03 fragmen_kt03 = new Fragmen_KT03();
                return fragmen_kt03;
            default:
                return null;
        }*/
        Fragmen_KT01 fragmen_kt01 = new Fragmen_KT01(String.valueOf(position));

        return fragmen_kt01;


    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
