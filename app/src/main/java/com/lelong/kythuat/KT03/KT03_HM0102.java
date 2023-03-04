package com.lelong.kythuat.KT03;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class KT03_HM0102 extends Fragment {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private String g_dk = "";
    private String g_date;
    private String g_ca;
    private String g_id;
    private static Context g_context;
    private Create_Table Cre_db = null;
    private KT03_DB kt03Db = null;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    RecyclerView recyclerView;
    KT03_HM0102_Adapder adapter;
    List list_hm01;

    /*public KT03_HM01(String qry_cond, String g_date, String g_ca, String g_id) {
        g_dk = qry_cond;
        this.g_date = g_date;
        this.g_ca = g_ca;
        this.g_id = g_id;
    }*/

    public KT03_HM0102(Context context) {
        this.g_context = context;
    }

    public static KT03_HM0102 newInstance(String qry_cond, Context context, String g_date, String g_ca, String g_id) {
        //KT03_HM01 fragment = new KT03_HM01(qry_cond, g_date,g_ca,g_id);
        KT03_HM0102 fragment = new KT03_HM0102(context);
        //g_context = context;
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, qry_cond);
        args.putString(ARG_PARAM2, g_date);
        args.putString(ARG_PARAM3, g_ca);
        args.putString(ARG_PARAM4, g_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            g_dk = getArguments().getString(ARG_PARAM1);
            g_date = getArguments().getString(ARG_PARAM2);
            g_ca = getArguments().getString(ARG_PARAM3);
            g_id = getArguments().getString(ARG_PARAM4);
        }
        ;
        kt03Db = new KT03_DB(g_context);
        kt03Db.open();
        Cre_db = new Create_Table(g_context);
        Cre_db.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int g_layout;
        int g_rcv_id;
        switch (g_dk) {
            case "01":
                g_layout = R.layout.kt03_hm01_fragment;
                g_rcv_id = R.id.rcv_hm01;
                break;
            case "02":
                g_layout = R.layout.kt03_hm02_fragment;
                g_rcv_id = R.id.rcv_hm02;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + g_dk);
        }
        View view = inflater.inflate(g_layout, container, false);
        //view.setId(R.id.fmID_hm01);

        recyclerView = view.findViewById(g_rcv_id);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        list_hm01 = new ArrayList<KT03_HM0102_Model>();
        adapter = new KT03_HM0102_Adapder(getContext(), R.layout.kt03_hm0102_fragment_row, list_hm01, g_date, g_ca);
        recyclerView.setAdapter(adapter);

        final Cursor[] cur_getAll = {null};
        Thread thread_CHK = new Thread(new Runnable() {
            @Override
            public void run() {
                cur_getAll[0] = kt03Db.getAll_HM0102(g_date, g_ca, g_dk);
            }
        });

        Thread thread_QRY = new Thread(new Runnable() {
            @Override
            public void run() {
                cur_getAll[0] = kt03Db.getAll_HM0102(g_date, g_ca, g_dk);
                addData_list_hm0102(cur_getAll[0]);
            }
        });

        Thread thread_INS = new Thread(new Runnable() {
            @Override
            public void run() {
                ins_KT03_0102_file(g_date, g_ca, g_id);
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

    private void addData_list_hm0102(Cursor cur_getAll) {
        recyclerView.post((new Runnable() {
            @Override
            public void run() {
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
                        list_hm01.add(new KT03_HM0102_Model(
                                tc_fac003, KT03_01_001, tc_fac005, tc_fac006,
                                cb01, cb02, cb03, cb04, KT03_01_003));
                    } catch (Exception e) {
                        String err = e.toString();
                    }
                    cur_getAll.moveToNext();
                }
                adapter.notifyDataSetChanged();
            }
        }));

    }

    private void ins_KT03_0102_file(String g_date, String g_ca, String g_id) {
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


}
