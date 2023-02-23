package com.lelong.kythuat.KT03;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KT03_HM01 extends Fragment {
    static String g_dk = "";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private KT03_DB kt03Db = null;
    private Create_Table Cre_db = null;

    ArrayList<KT03_HM01_model> kt03_hm01_models;
    List list_hm01;

    public KT03_HM01(String qry_cond) {
        g_dk = qry_cond;
        kt03Db = new KT03_DB(getContext());
        kt03Db.open();
        Cre_db = new Create_Table(getContext());
        Cre_db.open();

    }

    public static KT03_HM01 newInstance(String param1, String param2) {
        KT03_HM01 fragment = new KT03_HM01(g_dk);
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        // fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.kt03_hm01_fragment, container, false);

        View view = inflater.inflate(R.layout.kt03_hm01_fragment, container, false);

        // Getting reference of recyclerView
        RecyclerView recyclerView = view.findViewById(R.id.rcv_hm01);

        // Setting the layout as linear
        // layout for vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        /*
        // Sending reference and data to Adapter
        Adapter adapter = new Adapter(  getApplicationContext(), maso, noidung,diemso);

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);*/
        //createTable_fac02_detail = new KT02_DB(getContext());
        //createTable_fac02_detail.open();
        list_hm01 = new ArrayList<KT03_HM01_model>();
        //Cre_db = new Create_Table(getContext());
        //Cre_db.open();
        Cursor cur_tc_fac = Cre_db.getAll_tc_fac("KT03", g_dk);
        cur_tc_fac.moveToFirst();


        int num = cur_tc_fac.getCount();
        for (int i = 0; i < num; i++) {

            try {
                @SuppressLint("Range") String tc_fac003 = cur_tc_fac.getString(cur_tc_fac.getColumnIndex("tc_fac003"));
                @SuppressLint("Range") String tc_fac004 = cur_tc_fac.getString(cur_tc_fac.getColumnIndex("tc_fac004"));
                @SuppressLint("Range") String tc_fac005 = cur_tc_fac.getString(cur_tc_fac.getColumnIndex("tc_fac005"));
                @SuppressLint("Range") String tc_fac006 = cur_tc_fac.getString(cur_tc_fac.getColumnIndex("tc_fac006"));

                list_hm01.add(new KT03_HM01_model(tc_fac003, tc_fac005, tc_fac006, tc_fac004,checkbox1, checkbox2,
                        checkbox3, checkbox4, checkbox5, checkbox6, bophan, ngay,tc_fac009, somay));
                createTable_fac02_detail.ins_tc_fac_table_kt02(tc_fac004, tc_fac009, checkbox1, checkbox2, checkbox3, checkbox4, checkbox5,
                        checkbox6, bophan, ngay,somay,tc_fac003,tc_fac005,tc_fac006);
            } catch (Exception e) {
                String err = e.toString();
            }

            cur_tc_fac.moveToNext();
        }

        ListDataAdapter_KT02 adapter = new ListDataAdapter_KT02(getContext(), layout.listdata_item_kt02, list_hm01);

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);


        return view;
    }
}
