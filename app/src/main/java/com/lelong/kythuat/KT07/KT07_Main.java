package com.lelong.kythuat.KT07;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.lelong.kythuat.KT02.KT02_activity;
import com.lelong.kythuat.R;

public class KT07_Main extends AppCompatActivity {
    private Activity activity;
    private Context context;
    Dialog dialog;
    String g_server = "PHP";
    TabLayout tabLayout;
    ViewPager viewPager;

    public void login_dialog_KT07(Context context, String menuID, String ID) {
        //this.activity = activity;
        this.context = context;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt07_main_login);

        tabLayout = dialog.findViewById(R.id.tabLayout);
        //viewPager = dialog.findViewById(R.id.viewPager);

        TabLayout.Tab firstTab = tabLayout.newTab(); // Create a new Tab names
        firstTab.setText("Điện"); // set the Text for the first Tab
        firstTab.setIcon(R.drawable.dien); // set an icon for the first tab

        tabLayout.addTab(firstTab);

        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Nước"); // set the Text for the second Tab
        secondTab.setIcon(R.drawable.nuoc); // set an icon for the second tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout
// Create a new Tab named "Third"
        TabLayout.Tab thirdTab = tabLayout.newTab();
        thirdTab.setText("Gas"); // set the Text for the first Tab
        thirdTab.setIcon(R.drawable.gas); // set an icon for the first tab
        tabLayout.addTab(thirdTab); // add  the tab at in the TabLayout

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //final MyAdappter_KT07 adapter = new MyAdappter_KT07(this, getSupportFragmentManager(), tabLayout.getTabCount());
        /*viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));*/
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Fragment_KT02 fragment_kt02 = new Fragment_KT02(somay,bophan,ngay,g_tc_faa001,position);
                //KT07_Fragment kt07_fragment=new KT07_Fragment();
                Intent KT07 = new Intent();
                KT07.setClass(context, KT07_Fragment.class);
                Bundle bundle = new Bundle();
                //bundle.putString("ID", ID);
                bundle.putString("tab", String.valueOf(tabLayout.getTabCount()));
                KT07.putExtras(bundle);
                context.startActivity(KT07);
                dialog.dismiss();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        dialog.show();
    }

}
