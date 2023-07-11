package com.lelong.kythuat.KT01;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lelong.kythuat.KT02.Fragment_KT02;

public class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    private Context context1;
    private String data,g_date,g_BP,g_to_BP;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs, String g_date, String g_BP, String g_to_BP) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
        this.data = data;
        this.g_date = g_date;
        this.g_BP = g_BP;
        this.g_to_BP=g_to_BP;
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
        //Fragmen_KT01 fragmen_kt01 = Fragmen_KT01.newInstance(context,String.valueOf(position), g_date, g_BP);
        Fragmen_KT01 fragmen_kt01 = new Fragmen_KT01(position, g_date, g_BP,g_to_BP);
        return fragmen_kt01;


    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
