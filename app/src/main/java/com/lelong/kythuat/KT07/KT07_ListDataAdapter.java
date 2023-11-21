package com.lelong.kythuat.KT07;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.lelong.kythuat.KT01.KT01_DB;
import com.lelong.kythuat.KT01.ListDataAdapter;
import com.lelong.kythuat.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class KT07_ListDataAdapter extends RecyclerView.Adapter<KT07_ListDataAdapter.ViewHolder> {
    private Context applicationContext;
    private int resource;
    private ArrayList<TabLayout_KT07> mangLV;
    private String g_tc_cea01;
    private String tc_ceb03;
    private String tc_cebdate;
    private String tc_ceb06;
    private static final int CAMERA_REQUEST = 1888;
    private KT07_DB db = null;
    private List<String> mData;
    String g_tc_ceb04;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kt07_listdata_item, parent, false);
        // Passing view to ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setData(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tc_cea03.setText(mangLV.get(position).getTc_cea03_in());
        holder.tc_cea04.setText(mangLV.get(position).getTc_cea04_in());
        holder.tc_cea05.setText(mangLV.get(position).getTc_cea05_in());
        holder.tc_cea06.setText(mangLV.get(position).getTc_cea06_in());
        holder.tc_cea07.setText(mangLV.get(position).getTc_cea07_in());
        //holder.tc_ceb05.setText(mangLV.get(position).getTc_ceb05_in());
        holder.tc_ceb04.setText(mangLV.get(position).getTc_ceb04_in());




        /*update cột ghi chu (S)*/
        holder.tc_ceb04.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.tc_ceb04.getText().toString().trim().length() > 0) {
                    getUserCode(holder.tc_ceb04.getText().toString().trim(),
                            holder.getPosition());
                }
                //holder.tc_fac009.getText().toString();
            }

            private void getUserCode(String kt02_tc_fac009, int position) {
                try {
                    final String qr_val = kt02_tc_fac009.trim();
                    String tc_cea03 = mangLV.get(position).getTc_cea03_in();
                    g_tc_ceb04 = null;
                    g_tc_ceb04 = holder.tc_ceb04.getText().toString();
                    db.appendUPDAE(g_tc_cea01,tc_cea03, g_tc_ceb04, tc_ceb03, tc_cebdate, "tc_ceb04_in",tc_ceb06);
                    mangLV.get(position).setTc_ceb04_in(g_tc_ceb04);
                    //holder.ghichu1.setText("");
                } catch (Exception e) {
                    String err = e.toString();
                }
            }
        });
        /*update cột ghi chu (E)*/
    }

    @Override
    public int getItemCount() {
        return mangLV.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        EditText tc_ceb04;

        TextView tc_cea03, tc_cea04, tc_cea05,tc_cea06,tc_cea07,tc_ceb05;
        public ViewHolder(@NonNull View view) {
            super(view);
            tc_cea03 = (TextView) view.findViewById(R.id.tc_cea03);
            tc_cea04 = (TextView) view.findViewById(R.id.tc_cea04);
            tc_cea05 = (TextView) view.findViewById(R.id.tc_cea05);
            tc_cea06 = (TextView) view.findViewById(R.id.tc_cea06);
            tc_cea07 = (TextView) view.findViewById(R.id.tc_cea07);
            tc_ceb04 = (EditText) view.findViewById(R.id.tc_ceb04);
            //tc_ceb05 = (TextView) view.findViewById(R.id.tc_ceb05);
        }
    }

    public KT07_ListDataAdapter (Context applicationContext, int resource, ArrayList<TabLayout_KT07> mangLV,String g_tc_cea01,String tc_ceb03,String tc_cebdate,String tc_ceb06) {
        this.applicationContext = applicationContext;
        this.resource = resource;
        this.mangLV = mangLV;
        this.g_tc_cea01 = g_tc_cea01;
        this.tc_ceb03 = tc_ceb03;
        this.tc_cebdate = tc_cebdate;
        this.tc_ceb06 = tc_ceb06;
        //this.g_to = g_to;
        db = new KT07_DB(this.applicationContext);
        db.open();
    }
}
