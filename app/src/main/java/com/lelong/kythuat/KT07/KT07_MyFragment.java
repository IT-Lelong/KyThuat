package com.lelong.kythuat.KT07;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class KT07_MyFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String l_tc_cea01,id,g_tc_faa001;
    private int position,itemId;
    RecyclerView recyclerView;
    private String mParam1;
    private String mParam2;
    ArrayList<TabLayout_KT07> mangLV;
    private Create_Table createTable_kt07 = null;
    Cursor cursor_1, cursor_2;
    SimpleDateFormat dateFormat;
    private KT07_DB db = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.kt07_myfragment, container, false);
        db = new KT07_DB(view.getContext());
        db.open();
        // Getting reference of recyclerView
        recyclerView = view.findViewById(R.id.recyclerView1);

        // Setting the layout as linear
        // layout for vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        mangLV = new ArrayList<TabLayout_KT07>();
        LocalDate currentDate = LocalDate.now();

        dateFormat = new SimpleDateFormat("yyyy/MM/dd");


        Date currentDate1 = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date previousDate = calendar.getTime();

        String previousDateString = dateFormat.format(previousDate);
        String currentDateString = dateFormat.format(currentDate1);

        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();

        String amPm = (hour < 12) ? "AM" : "PM";

        createTable_kt07 = new Create_Table(getContext());
        createTable_kt07.open();
        /*if (Constant_Class.UserFactory.equals("DH")) {
            createTable_kt07.ins_fac_01("KT01",bophan,ngay,tobp);
        }else {
            createTable_kt07.ins_fac_01("KT01",bophan,ngay,"Bến Lức");
        }*/
        //createTable_fac.ins_fac_01("KT01",bophan,ngay,tobp);

        if(position==0 && itemId==1){
            l_tc_cea01="DHS";
        }
        if(position==1 && itemId==1){
            l_tc_cea01="DHM";
        }
        if(position==2 && itemId==1){
            l_tc_cea01="DHR";
        }
        if(position==0 && itemId==2){
            l_tc_cea01="DHG";
        }
        if(position==0 && itemId==3){
            l_tc_cea01="DHD";
        }

        createTable_kt07.ins_tc_cea(l_tc_cea01,previousDateString,amPm,currentDateString,id);
        cursor_1 = createTable_kt07.getAll_tc_cea_kt07(l_tc_cea01,previousDateString,amPm,currentDateString,id);
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        for (int i = 0; i < num; i++) {
            try {
                @SuppressLint("Range") String tc_cea03_in = cursor_1.getString(cursor_1.getColumnIndex("tc_cea03_in"));
                @SuppressLint("Range") String tc_cea04_in = cursor_1.getString(cursor_1.getColumnIndex("tc_cea04"));
                @SuppressLint("Range") String tc_cea05_in = cursor_1.getString(cursor_1.getColumnIndex("tc_cea05_in"));
                @SuppressLint("Range") String tc_cea06_in = cursor_1.getString(cursor_1.getColumnIndex("tc_cea06_in"));
                @SuppressLint("Range") String tc_cea07_in = cursor_1.getString(cursor_1.getColumnIndex("tc_cea07_in"));
                @SuppressLint("Range") String tc_ceb04_in = cursor_1.getString(cursor_1.getColumnIndex("tc_ceb04_in"));
                @SuppressLint("Range") String tc_ceb05_in = cursor_1.getString(cursor_1.getColumnIndex("tc_ceb05_in"));

                mangLV.add(new TabLayout_KT07(view,tc_cea03_in, tc_cea04_in, tc_cea05_in, tc_cea06_in, tc_cea07_in, tc_ceb04_in, tc_ceb05_in));
                //db.append(tc_fac004,bienngay,ID2,"","","",tc_fac007,tc_fac006,tc_fac003,tc_fac001,"");
            } catch (Exception e) {
                String err = e.toString();
            }

            cursor_1.moveToNext();
        }


        /*KT07_ListDataAdapter kt07_listDataAdapter = new KT07_ListDataAdapter(getContext(),
                R.layout.kt07_listdata_item,
                mangLV,l_tc_cea01,previousDateString,currentDateString,amPm);

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(kt07_listDataAdapter);*/

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    DecimalFormat decimalFormat = new DecimalFormat("00");
    String matab;

    public KT07_MyFragment(String g_tc_faa001, int position,int itemId,String id) {
        this.g_tc_faa001 = g_tc_faa001;
        this.position = position;
        this.itemId=itemId;
        this.id=id;
        matab = decimalFormat.format(position + 1);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private Context getApplicationContext() {
        return null;
    }
}
