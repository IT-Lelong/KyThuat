package com.lelong.kythuat.KT02;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.KT02.KT02_DB;
import com.lelong.kythuat.KT02.KT02_Interface;
import com.lelong.kythuat.KT02.KT02_Signature_Dialog_Camera;
import com.lelong.kythuat.R;

public class KT02_Signature_Main_Adapter extends SimpleCursorAdapter {
    private final Context context;
    private final int layout;
    private final Cursor mCursor;
    private final String[] from;
    private final int[] to;
    private final Drawable drawable_blue;
    private final Drawable drawable_green;
    private KT02_Interface kt02Interface;
    private KT02_DB kt02Db = null;
    Button btnkt;
    Dialog dialog;
    String s_somay, s_bophan, s_tenbp, s_ngay,tenxe,g_fia15;
    RecyclerView rvc_kyten;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    boolean firstDetected = true;

    public KT02_Signature_Main_Adapter(Context Context, int layout, Cursor cursor, String[] from, int[] to, Drawable drawable_blue, Drawable drawable_green, KT02_Interface kt02Interface,String tenxe) {
        super(Context, layout, cursor, from, to);
        this.context = Context;
        this.layout = layout;
        this.mCursor = cursor;
        this.from = from;
        this.to = to;
        this.drawable_blue = drawable_blue;
        this.drawable_green = drawable_green;
        this.kt02Interface = kt02Interface;
        this.tenxe = tenxe;

        kt02Db = new KT02_DB(context);
        kt02Db.open();
    }
    public void updateData(Cursor ncursor) {
        swapCursor(ncursor);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        btnkt = view.findViewById(R.id.btnkt);
        btnkt.setTag(position);
        //btnkt = view.findViewById(R.id.btnkt);
        mCursor.moveToPosition(position);
        s_somay = mCursor.getString(mCursor.getColumnIndexOrThrow("fiaud03"));
        s_bophan = mCursor.getString(mCursor.getColumnIndexOrThrow("fia15"));
        s_tenbp = mCursor.getString(mCursor.getColumnIndexOrThrow("fka02"));
        s_ngay = mCursor.getString(mCursor.getColumnIndexOrThrow("ngaysig"));

        Boolean chk_qrb = kt02Db.KT_fia_up_sig01(s_somay, s_bophan, s_ngay);
        if (chk_qrb == true) {
            btnkt.setBackground(drawable_green);
        } else {
            btnkt.setBackground(drawable_blue);
        }

        btnkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                mCursor.moveToPosition(position);
                s_somay = mCursor.getString(mCursor.getColumnIndexOrThrow("fiaud03"));
                s_bophan = mCursor.getString(mCursor.getColumnIndexOrThrow("fia15"));
                s_tenbp = mCursor.getString(mCursor.getColumnIndexOrThrow("fka02"));
                s_ngay = mCursor.getString(mCursor.getColumnIndexOrThrow("ngaysig"));

                UpdateDialog(s_somay,s_bophan, s_tenbp, s_ngay,position);
                /*dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        kt01Interface.loadData_Search_Sig();
                    }
                });*/
            }
        });

        return view;
    }

    private void UpdateDialog(String s_somay,String s_bophan, String s_tenbp, String s_ngay, int position) {
        KT02_Signature_Dialog_Camera kt02SignatureDialogCamera = new KT02_Signature_Dialog_Camera(context,s_somay,s_bophan, s_tenbp, s_ngay, position);
        kt02SignatureDialogCamera.show();
        firstDetected = true;
        kt02SignatureDialogCamera.setOnDialogDismissListener(new KT02_Signature_Dialog_Camera.OnDialogDismissListener() {
            @Override
            public void onDialogDismissed() {
                // Xử lý sự kiện khi dialog được đóng từ Adapter
                String g_bp= Constant_Class.UserFactory;
                if (g_bp.equals("DH") ) {
                    g_fia15 = "D";
                }
                if (g_bp.equals("BL")){
                    g_fia15 = "B";
                }
                Cursor updatedCursor = kt02Db.getDepartmetData(tenxe,g_fia15,s_ngay);
                mCursor.moveToPosition(position);
                updateData(updatedCursor) ;
                notifyDataSetChanged();
            }
        });
    }
}
