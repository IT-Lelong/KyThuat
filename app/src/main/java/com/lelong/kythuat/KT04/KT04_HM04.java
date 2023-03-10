package com.lelong.kythuat.KT04;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.KT03.KT03_HM03_Model;
import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class KT04_HM04 extends Fragment {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private String g_dk = "";
    private String g_date;
    private String g_ca;
    private String g_id;
    private static Context g_context;
    private Create_Table Cre_db = null;
    private KT04_DB kt04Db = null;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    RecyclerView recyclerView;
    KT04_HM03_Adapder adapter;
    List list_hm03;

    /*public KT03_HM01(String qry_cond, String g_date, String g_ca, String g_id) {
        g_dk = qry_cond;
        this.g_date = g_date;
        this.g_ca = g_ca;
        this.g_id = g_id;
    }*/

    public KT04_HM04(Context context) {
        this.g_context = context;
    }

    public static KT04_HM04 newInstance(String qry_cond, Context context, String g_date, String g_ca, String g_id) {
        //KT03_HM01 fragment = new KT03_HM01(qry_cond, g_date,g_ca,g_id);
        KT04_HM04 fragment = new KT04_HM04(context);
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
        kt04Db = new KT04_DB(g_context);
        kt04Db.open();
        Cre_db = new Create_Table(g_context);
        Cre_db.open();
    }
    private void upd_Data(String g_col, String g_noidung) {
        kt04Db.upd_HM04(g_col, g_date, g_ca, g_noidung);
    }

    //private void setOnFocusChangeListener(EditText text){
        //text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            //@Override
            //public void onFocusChange(View v, boolean hasFocus) {
                //if(!hasFocus) {
                    //try{
                        //if (text.getText().length() > 0) {
                            //final Cursor[] cur_getAll = {null};
                            //cur_getAll[0]= kt04Db.getAll_HM04(g_date, g_ca);
                            ///if (cur_getAll[0].getCount() > 0)
                            //{
                                //kt04Db.append4(text.getText().toString().trim(),g_date,g_ca,g_id);
                            //}
                            //else{
                                //upd_Data("KT03_03_005", text.getText().toString().trim());
                                //text.setText(text.getText().toString().trim());
                            //}

                        //}
                    //}catch (Exception e){

                    //}

                //}
            //}
        //});
    //}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EditText edt_danhgia;
        int g_layout;
        int g_rcv_id;
        g_layout = R.layout.kt04_hm04_fragment;

        View view = inflater.inflate(g_layout, container, false);
        edt_danhgia = (EditText) view.findViewById(R.id.edt4_danhgia);
        final Cursor[] cur_getAll = {null};
        cur_getAll[0]= kt04Db.getAll_HM04(g_date, g_ca);



        if(cur_getAll[0].getCount() >0)
            add(cur_getAll[0], edt_danhgia );
        try{
            edt_danhgia.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(!hasFocus) {
                        if (edt_danhgia.getText().length() > 0) {
                            final Cursor[] cur_getAll04 = {null};
                            cur_getAll04[0]= kt04Db.getAll_HM04(g_date, g_ca);
                            if (cur_getAll[0].getCount() <= 0)
                            {
                                kt04Db.append4(edt_danhgia.getText().toString().trim(),g_date,g_ca,g_id);
                            }
                            else{
                                upd_Data("KT04_04_001", edt_danhgia.getText().toString().trim());
                                edt_danhgia.setText(edt_danhgia.getText().toString().trim());
                            }

                        }
                    }
                }
            });
        } catch (Exception e) {
            //
        }

        return view;
    }

    private  void add(Cursor cur_getAll, EditText text )
    {
        cur_getAll.moveToFirst();
        int num = cur_getAll.getCount();
        for (int i = 0; i < num; i++) {
            try {
                @SuppressLint("Range") String danhgia = cur_getAll.getString(cur_getAll.getColumnIndex("KT04_04_001"));
                text.setText(danhgia);

            } catch (Exception e) {
                String err = e.toString();
            }
            cur_getAll.moveToNext();
        }
    }


}

