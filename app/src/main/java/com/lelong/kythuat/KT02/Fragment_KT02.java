package com.lelong.kythuat.KT02;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_KT02#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_KT02 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String somay;
    String bophan;
    String ngay,g_tc_faa001;
    String tenhinh;
    private int position;

    String matab, g_lang;
    RecyclerView recyclerView;

    ArrayList<KT02_LIST> mangLV02;
    CheckBox ck;
    Cursor cursor_1, cursor_2;

    ListView list;
    Locale locale;
    private Create_Table createTable_fac02 = null;

    private KT02_DB createTable_fac02_detail = null;
    SimpleDateFormat dateFormatKT02 = new SimpleDateFormat("yyyy-MM-dd");

    DecimalFormat decimalFormat = new DecimalFormat("00");
    ListDataAdapter_KT02 adapter;

    public Fragment_KT02(String somay, String bophan, String ngay,String g_tc_faa001, int position) {

        this.somay = somay;
        this.bophan = bophan;
        this.ngay = ngay;
        this.g_tc_faa001 = g_tc_faa001;
        this.position = position;
        matab = decimalFormat.format(position + 1);
    }

    public Fragment_KT02() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_KT02.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_KT02 newInstance(String param1, String param2) {
        Fragment_KT02 fragment = new Fragment_KT02();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLanguage();
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    /*@Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }*/

    private void setLanguage() {
        SharedPreferences preferences = getActivity().getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        switch (language) {
            case 0:
                configuration.setLocale(Locale.TRADITIONAL_CHINESE);
                break;
            case 1:
                locale = new Locale("vi");
                Locale.setDefault(locale);
                configuration.setLocale(locale);
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment

        SharedPreferences preferences = getActivity().getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        if (language == 0) {
            g_lang = "tc_fac005";
        } else {
            g_lang = "tc_fac006";
        }

        View view = inflater.inflate(R.layout.fragment_kt02, container, false);

        // Getting reference of recyclerView
        recyclerView = view.findViewById(R.id.recyclerView1);

        // Setting the layout as linear
        // layout for vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        createTable_fac02_detail = new KT02_DB(getContext());
        createTable_fac02_detail.open();
        mangLV02 = new ArrayList<KT02_LIST>();
        createTable_fac02 = new Create_Table(getContext());
        createTable_fac02.open();
        createTable_fac02.ins_fac_02(g_tc_faa001, bophan, ngay, somay);
        cursor_1 = createTable_fac02.getAll_tc_fac_02(g_tc_faa001, matab, bophan, somay, ngay);
        if (cursor_1 != null) {
            cursor_1.moveToFirst();
            int num = cursor_1.getCount();
            for (int i = 0; i < num; i++) {
                try {
                    @SuppressLint("Range") String tc_fac003 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac003"));
                    @SuppressLint("Range") String tc_fac006 = cursor_1.getString(cursor_1.getColumnIndex(g_lang));
                    @SuppressLint("Range") String tc_fac004 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac004"));
                    @SuppressLint("Range") Boolean checkbox1 = Boolean.valueOf(cursor_1.getString(cursor_1.getColumnIndex("checkbox1")));
                    @SuppressLint("Range") Boolean checkbox2 = Boolean.valueOf(cursor_1. getString(cursor_1.getColumnIndex("checkbox2")));
                    @SuppressLint("Range") Boolean checkbox3 = Boolean.valueOf(cursor_1.getString(cursor_1.getColumnIndex("checkbox3")));
                    @SuppressLint("Range") Boolean checkbox4 = Boolean.valueOf(cursor_1.getString(cursor_1.getColumnIndex("checkbox4")));
                    @SuppressLint("Range") Boolean checkbox5 = Boolean.valueOf(cursor_1.getString(cursor_1.getColumnIndex("checkbox5")));
                    @SuppressLint("Range") Boolean checkbox6 = Boolean.valueOf(cursor_1.getString(cursor_1.getColumnIndex("checkbox6")));
                    @SuppressLint("Range") String tc_fac009 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac009"));

                    mangLV02.add(new KT02_LIST(view, tc_fac003, tc_fac006, tc_fac004, checkbox1, checkbox2,
                            checkbox3, checkbox4, checkbox5, checkbox6, bophan, ngay, tc_fac009, somay,tenhinh));
                } catch (Exception e) {
                    String err = e.toString();
                }

                cursor_1.moveToNext();
            }
        }
        else{
            String abc = "sdsd";
            Toast.makeText(view.getContext(), g_tc_faa001+ "  " +matab + "  " +bophan+ "  " +somay + " " +ngay, Toast.LENGTH_SHORT).show();
        }




        adapter = new ListDataAdapter_KT02(getContext(), R.layout.listdata_item_kt02, mangLV02);
        recyclerView.setAdapter(adapter);

        return view;

    }

    private Context getApplicationContext() {
        return null;
    }

}