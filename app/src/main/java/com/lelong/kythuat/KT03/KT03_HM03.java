package com.lelong.kythuat.KT03;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
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

public class KT03_HM03 extends Fragment implements KT03_Interface {
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
    KT03_HM03_Adapder adapter;
    List list_hm03;
    private Handler mHandler;

    public KT03_HM03(Context context) {
        this.g_context = context;
    }

    public static KT03_HM03 newInstance(String qry_cond, Context context, String g_date, String g_ca, String g_id) {
        KT03_HM03 fragment = new KT03_HM03(context);
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

        kt03Db = new KT03_DB(g_context);
        kt03Db.open();
        Cre_db = new Create_Table(g_context);
        Cre_db.open();

        mHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kt03_hm03_fragment, container, false);

        recyclerView = view.findViewById(R.id.rcv_hm03);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        list_hm03 = new ArrayList<KT03_HM03_Model>();
        adapter = new KT03_HM03_Adapder(getContext(), R.layout.kt03_hm03_fragment_row, list_hm03, g_date, g_ca,g_id , (KT03_Interface) this);
        recyclerView.setAdapter(adapter);

        //Load Data to list
        loadData();

        return view;
    }

    private void loadData() {

        final Cursor[] cur_getAll = {null};
        Thread thread_QRY = new Thread(() -> cur_getAll[0] = kt03Db.getAll_HM03(g_date, g_ca));

        Thread thread_ADD = new Thread(() -> addData_list_hm03(cur_getAll[0]));

        Thread thread_NEWROW = new Thread(() -> HM03_add_newRow(cur_getAll[0].getCount()));

        new Thread() {
            @Override
            public void run() {
                thread_QRY.start();
                try {
                    thread_QRY.join();
                } catch (InterruptedException e) {
                }

                thread_ADD.start();
                try {
                    thread_ADD.join();

                } catch (InterruptedException e) {
                }

                thread_NEWROW.start();
                try {
                    thread_NEWROW.join();
                } catch (InterruptedException e) {
                }

            }
        }.start();

    }

    private void addData_list_hm03(Cursor cur_getAll) {
        recyclerView.post((() -> {
            if (cur_getAll.getCount() > 0) {
                cur_getAll.moveToFirst();
                int num = cur_getAll.getCount();
                list_hm03.clear();
                for (int i = 0; i < num; i++) {
                    try {
                        String KT03_03_001 = cur_getAll.getString(cur_getAll.getColumnIndexOrThrow("KT03_03_001"));
                        String KT03_03_002 = cur_getAll.getString(cur_getAll.getColumnIndexOrThrow("KT03_03_002"));
                        String KT03_03_003 = cur_getAll.getString(cur_getAll.getColumnIndexOrThrow("KT03_03_003"));
                        String KT03_03_004 = cur_getAll.getString(cur_getAll.getColumnIndexOrThrow("KT03_03_004"));
                        String KT03_03_005 = cur_getAll.getString(cur_getAll.getColumnIndexOrThrow("KT03_03_005"));
                        String KT03_03_006 = cur_getAll.getString(cur_getAll.getColumnIndexOrThrow("KT03_03_006"));
                        String KT03_03_007 = cur_getAll.getString(cur_getAll.getColumnIndexOrThrow("KT03_03_007"));

                        list_hm03.add(new KT03_HM03_Model(KT03_03_001,
                                KT03_03_002, KT03_03_003, KT03_03_004,
                                KT03_03_005, KT03_03_006, KT03_03_007, "old"));
                    } catch (Exception e) {
                        String err = e.toString();
                    }
                    cur_getAll.moveToNext();
                }

                adapter.notifyDataSetChanged();
            }
        }));
    }

    @Override
    public void HM03_add_newRow(int position) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                // Thực hiện các thao tác trên các thành phần giao diện người dùng ở đây
                list_hm03.add(new KT03_HM03_Model(String.valueOf(list_hm03.size() + 1),
                        "", "", "",
                        "", "", "", "new"));
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public String HM03_ins_tablerow(int position) {
        KT03_HM03_Model objects = (KT03_HM03_Model) list_hm03.get(position);
        String status = objects.getRow_status(); // Lấy giá trị của trường Status
        String re_max = objects.getKt03_03_001(); // Lấy giá trị của trường Key để update

        if (status.equals("new")) {
            int g_max = kt03Db.query_HM03_max(g_date, g_ca); //đã được +1 từ max
            re_max = String.valueOf(g_max);
            kt03Db.ins_hm03(String.valueOf(g_max),
                    "", "", "",
                    "", "", "",
                    g_date, g_ca, g_id);
            objects.setRow_status("old");
            loadData();
        }
        return String.valueOf(re_max);
    }
}
