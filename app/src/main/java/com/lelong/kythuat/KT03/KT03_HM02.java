package com.lelong.kythuat.KT03;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.lelong.kythuat.R;

public class KT03_HM02 extends Fragment {
    static String g_dk = "";

    public KT03_HM02(String qry_cond, Context context, String g_date, String g_ca, String g_id) {
        g_dk = qry_cond;
    }

    public static KT03_HM02 newInstance(String param1, String param2) {
        //KT03_HM02 fragment = new KT03_HM02(g_dk, context, g_date, g_ca, g_id);
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
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.kt03_hm02_fragment, container, false);
    }
}
