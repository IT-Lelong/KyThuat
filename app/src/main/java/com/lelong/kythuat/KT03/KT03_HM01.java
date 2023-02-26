package com.lelong.kythuat.KT03;

import android.annotation.SuppressLint;
import android.content.Context;
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
import java.util.List;

public class KT03_HM01 extends Fragment implements KT03_Interface {
    static String g_dk = "";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private final Context context;
    private final String g_date;
    private final String g_ca;
    private final String g_id;
    private Create_Table Cre_db = null;
    private KT03_DB kt03Db = null;

    ArrayList<KT03_HM01_Model> kt03_hm01_models;
    KT03_HM01_Adapder adapter;
    List list_hm01;

    public KT03_HM01(String qry_cond, Context context, String g_date, String g_ca, String g_id) {
        g_dk = qry_cond;
        this.context = context;
        this.g_date = g_date;
        this.g_ca = g_ca;
        this.g_id = g_id;
    }

    public static KT03_HM01 newInstance(String param1, String param2) {
        //KT03_HM01 fragment = new KT03_HM01(g_dk, context);
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        // fragment.setArguments(args);
        //return fragment;
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kt03Db = new KT03_DB(context);
        kt03Db.open();
        Cre_db = new Create_Table(context);
        Cre_db.open();

        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.kt03_hm01_fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rcv_hm01);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        list_hm01 = new ArrayList<KT03_HM01_Model>();
        adapter = new KT03_HM01_Adapder(getContext(), this,R.layout.kt03_hm01_fragment_row, list_hm01,g_date, g_ca);
        recyclerView.setAdapter(adapter);

        final Cursor[] cur_getAll = {null};
        Thread thread_CHK = new Thread(new Runnable() {
            @Override
            public void run() {
                cur_getAll[0] = kt03Db.getAll_HM01(g_date, g_ca);
            }
        });

        Thread thread_QRY = new Thread(new Runnable() {
            @Override
            public void run() {
                cur_getAll[0] = kt03Db.getAll_HM01(g_date, g_ca);
                addData_list_hm01(cur_getAll[0]);
            }
        });

        Thread thread_INS = new Thread(new Runnable() {
            @Override
            public void run() {
                ins_KT03_01_file(g_date, g_ca, g_id);
            }
        });

        new Thread() {
            @Override
            public void run() {
                thread_CHK.start();
                try {
                    thread_CHK.join();
                } catch (InterruptedException e) {
                }

                if (cur_getAll[0].getCount() > 0) {
                    thread_QRY.start();
                    try {
                        thread_QRY.join();
                    } catch (InterruptedException e) {
                    }
                } else {
                    thread_INS.start();
                    try {
                        thread_INS.join();

                        thread_QRY.start();
                        try {
                            thread_QRY.join();
                        } catch (InterruptedException e) {
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }
        }.start();


        return view;
    }

    private void addData_list_hm01(Cursor cur_getAll) {
        Boolean cb01 = false, cb02 = false, cb03 = false, cb04 = false;
        cur_getAll.moveToFirst();
        int num = cur_getAll.getCount();
        list_hm01.clear();
        for (int i = 0; i < num; i++) {
            try {
                @SuppressLint("Range") String tc_fac003 = cur_getAll.getString(cur_getAll.getColumnIndex("tc_fac003"));
                @SuppressLint("Range") String KT03_01_001 = cur_getAll.getString(cur_getAll.getColumnIndex("KT03_01_001"));
                @SuppressLint("Range") String tc_fac005 = cur_getAll.getString(cur_getAll.getColumnIndex("tc_fac005"));
                @SuppressLint("Range") String tc_fac006 = cur_getAll.getString(cur_getAll.getColumnIndex("tc_fac006"));
                @SuppressLint("Range") String KT03_01_002 = cur_getAll.getString(cur_getAll.getColumnIndex("KT03_01_002"));
                @SuppressLint("Range") String KT03_01_003 = cur_getAll.getString(cur_getAll.getColumnIndex("KT03_01_003"));

                switch (KT03_01_002) {
                    case "0":
                        cb01 = cb02 = cb03 = cb04 = false;
                        break;
                    case "1":
                        cb01 = true;
                        cb02 = cb03 = cb04 = false;
                        break;
                    case "2":
                        cb02 = true;
                        cb01 = cb03 = cb04 = false;
                        break;
                    case "3":
                        cb03 = true;
                        cb01 = cb02 = cb04 = false;
                        break;
                    case "4":
                        cb04 = true;
                        cb01 = cb02 = cb03 = false;
                        break;
                }
                list_hm01.add(new KT03_HM01_Model(
                        tc_fac003, KT03_01_001, tc_fac005, tc_fac006,
                        cb01, cb02, cb03, cb04, KT03_01_003));
            } catch (Exception e) {
                String err = e.toString();
            }
            cur_getAll.moveToNext();
        }
        adapter.notifyDataSetChanged();
    }

    private void ins_KT03_01_file(String g_date, String g_ca, String g_id) {
        Cursor cur_tc_fac = Cre_db.getAll_tc_fac("KT03", g_dk);
        cur_tc_fac.moveToFirst();
        int num = cur_tc_fac.getCount();
        for (int i = 0; i < num; i++) {
            try {
                @SuppressLint("Range") String tc_fac004 = cur_tc_fac.getString(cur_tc_fac.getColumnIndex("tc_fac004"));
                kt03Db.append(tc_fac004, "0", "", g_date, g_ca, g_id);
            } catch (Exception e) {
                String err = e.toString();
            }
            cur_tc_fac.moveToNext();
        }
    }

    @Override
    public void HM01_Refect(boolean ref) {
        if(ref) {
            Cursor cur_getAll= kt03Db.getAll_HM01(g_date, g_ca);
            addData_list_hm01(cur_getAll);
        }
    }
}
