package com.lelong.kythuat.KT01;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.KT07.KT07_ListCheck_Dialog;
import com.lelong.kythuat.R;

import java.io.IOException;
import java.util.ArrayList;

public class KT01_Signature_Main_Adapter extends SimpleCursorAdapter  {
    private final Context context;
    private final int layout;
    private final Cursor mCursor;
    private final String[] from;
    private final int[] to;
    private final Drawable drawable_blue;
    private final Drawable drawable_green;
    private KT01_Interface kt01Interface;
    private KT01_DB kt01Db = null;
    Button btnkt;
    Dialog dialog;
    String s_somay, s_bophan, s_tenbp, s_ngay;
    RecyclerView rvc_kyten;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    boolean firstDetected = true;

    public KT01_Signature_Main_Adapter(Context Context, int layout, Cursor cursor, String[] from, int[] to, Drawable drawable_blue, Drawable drawable_green, KT01_Interface kt01Interface) {
        super(Context, layout, cursor, from, to);
        this.context = Context;
        this.layout = layout;
        this.mCursor = cursor;
        this.from = from;
        this.to = to;
        this.drawable_blue = drawable_blue;
        this.drawable_green = drawable_green;
        this.kt01Interface = kt01Interface;

        kt01Db = new KT01_DB(context);
        kt01Db.open();
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
        s_bophan = mCursor.getString(mCursor.getColumnIndexOrThrow("tc_fba007"));
        s_tenbp = mCursor.getString(mCursor.getColumnIndexOrThrow("tc_fba009"));
        s_ngay = mCursor.getString(mCursor.getColumnIndexOrThrow("ngaysig"));

        Boolean chk_qrb = kt01Db.KT_fia_up_sig01(s_somay, s_bophan, s_ngay);
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
                s_bophan = mCursor.getString(mCursor.getColumnIndexOrThrow("tc_fba007"));
                s_tenbp = mCursor.getString(mCursor.getColumnIndexOrThrow("tc_fba009"));
                s_ngay = mCursor.getString(mCursor.getColumnIndexOrThrow("ngaysig"));

                UpdateDialog(s_bophan, s_tenbp, s_ngay,position);
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

    private void UpdateDialog(String s_bophan, String s_tenbp, String s_ngay, int position) {
        KT01_Signature_Dialog_Camera kt01SignatureDialogCamera = new KT01_Signature_Dialog_Camera(context,s_bophan, s_tenbp, s_ngay, position);
        kt01SignatureDialogCamera.show();
        firstDetected = true;
        kt01SignatureDialogCamera.setOnDialogDismissListener(new KT01_Signature_Dialog_Camera.OnDialogDismissListener() {
            @Override
            public void onDialogDismissed() {
                // Xử lý sự kiện khi dialog được đóng từ Adapter
                Cursor updatedCursor = kt01Db.getDepartmetData(Constant_Class.UserFactory);
                mCursor.moveToPosition(position);
                updateData(updatedCursor) ;
                notifyDataSetChanged();
            }
        });
    }


}
