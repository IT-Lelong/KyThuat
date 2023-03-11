package com.lelong.kythuat.KT03;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;

public class KT03_HM04 extends Fragment {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private String g_dk = "";
    private String g_date;
    private String g_ca;
    private String g_id;
    private static Context g_context;
    private KT03_DB kt03Db = null;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    public KT03_HM04(Context context) {
        this.g_context = context;
    }

    public static KT03_HM04 newInstance(String qry_cond, Context context, String g_date, String g_ca, String g_id) {
        KT03_HM04 fragment = new KT03_HM04(context);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.kt03_hm04_fragment, container, false);
        EditText edt_danhgia = (EditText) view.findViewById(R.id.edt_danhgia);

        Cursor cursor = kt03Db.getAll_HM04(g_date,g_ca);
        cursor.moveToFirst();
        if(cursor != null && cursor.getCount() > 0){
            String g_noidung = cursor.getString(cursor.getColumnIndexOrThrow("KT03_04_004"));
            edt_danhgia.setText(g_noidung);
        }else{
            kt03Db.ins_hm04(g_date,g_ca,g_id,"");
        }

        edt_danhgia.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus){
                if (edt_danhgia.getText().length() > 0) {
                    String new_nd = edt_danhgia.getText().toString().trim();
                    kt03Db.upd_HM04(g_date,g_ca,g_id,new_nd);
                }
            }
        });

        return view;
    }
}
