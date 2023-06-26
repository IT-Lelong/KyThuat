package com.lelong.kythuat.KT02;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class List_Signature extends SimpleCursorAdapter {
    private final Context context;
    private final int layout;
    private final Cursor mCursor;
    private final String[] from;
    private final int[] to;
    Dialog dialog;
    String s_somay, s_bophan, s_tenbp,s_ngay;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    TextView tv_date;
    EditText tv_ghichu,tv_manv,tv_sogio;
    Button btn_Date, btn_Insert,btnkt;
    private KT02_DB kt02Db = null;
    private final Drawable drawable_blue;
    private final Drawable drawable_green;
    KT02_Interface kt02_interface;

    public List_Signature(Context context, int layout, Cursor c, String[] from, int[] to, KT02_Interface kt02Interface, Drawable drawable_blue, Drawable drawable_green) {
        super(context, layout, c, from, to);
        this.context = context;
        this.layout = layout;
        this.mCursor = c;
        this.from = from;
        this.to = to;
        this.kt02_interface= kt02Interface;

        kt02Db = new KT02_DB(context);
        this.drawable_blue = drawable_blue;
        this.drawable_green = drawable_green;
        kt02Db.open();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        btnkt = view.findViewById(R.id.btnkt);
        btnkt.setTag(position);

        s_somay = mCursor.getString(mCursor.getColumnIndexOrThrow("fiaud03"));
        s_bophan = mCursor.getString(mCursor.getColumnIndexOrThrow("fia15"));
        s_tenbp = mCursor.getString(mCursor.getColumnIndexOrThrow("fka02"));
        s_ngay = mCursor.getString(mCursor.getColumnIndexOrThrow("ngaysig"));

        Boolean chk_qrb = kt02Db.KT_fia_up(s_somay, s_bophan);
        if (chk_qrb == true) {
            btnkt.setBackground(drawable_green);
        }else{
            btnkt.setBackground(drawable_blue);
        }

        btnkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                mCursor.moveToPosition(position);
                // Do something with the data at the clicked position
                s_somay = mCursor.getString(mCursor.getColumnIndexOrThrow("fiaud03"));
                s_bophan = mCursor.getString(mCursor.getColumnIndexOrThrow("fia15"));
                s_tenbp = mCursor.getString(mCursor.getColumnIndexOrThrow("fka02"));
                s_ngay = mCursor.getString(mCursor.getColumnIndexOrThrow("ngaysig"));

                openDialog(s_somay, s_bophan, s_tenbp,s_ngay,position);
            }
        });

        return view;
    }
    private void openDialog(String g_somay, String g_bophan, String g_tenbp,String g_ngay,int vitri) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt02_insertinfo);
        tv_manv = dialog.findViewById(R.id.tv_manv);
        tv_sogio = dialog.findViewById(R.id.tv_sogio);
        tv_ghichu = dialog.findViewById(R.id.tv_ghichu);
        btn_Insert = dialog.findViewById(R.id.btninsert);

        btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String g_ghichu = tv_ghichu.getText().toString().trim();
                String g_manv = tv_manv.getText().toString().trim();
                String g_sogio = tv_sogio.getText().toString().trim();
                kt02Db.ins_sig(g_ngay, g_somay, g_bophan, g_tenbp, g_ghichu,g_manv,g_sogio);
                dialog.dismiss();
                kt02_interface.loadData_Sig();
            }
        });
        dialog.show();
    }
}
