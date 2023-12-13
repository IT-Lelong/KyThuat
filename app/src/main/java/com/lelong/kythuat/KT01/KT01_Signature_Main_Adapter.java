package com.lelong.kythuat.KT01;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
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

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.lelong.kythuat.R;

import java.io.IOException;

public class KT01_Signature_Main_Adapter extends SimpleCursorAdapter {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        btnkt = view.findViewById(R.id.btnkt);
        btnkt.setTag(position);

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

                UpdateDialog(s_bophan, s_tenbp, s_ngay);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        kt01Interface.loadData_Search_Sig();
                    }
                });
            }
        });

        return view;
    }

    private void UpdateDialog(String s_bophan, String s_tenbp, String s_ngay) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt01_signature_main_camera);
        firstDetected = true;

        TextView tv_qrcode = dialog.findViewById(R.id.tv_qrcode);
        SurfaceView suv_qr = (SurfaceView) dialog.findViewById(R.id.suv_qr);
        TextView tv_bpname = dialog.findViewById(R.id.tv_bpname);
        TextView tv_manvsig = dialog.findViewById(R.id.tv_manvsig);
        EditText edt_sogio =  dialog.findViewById(R.id.edt_sogio);
        EditText edt_ghichu =  dialog.findViewById(R.id.edt_ghichu);
        Button btninsert = dialog.findViewById(R.id.btninsert);

        tv_bpname.setText(s_tenbp);

        barcodeDetector = new BarcodeDetector.Builder(dialog.getContext()).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(dialog.getContext(), barcodeDetector).setRequestedPreviewSize(300, 300).build();
        cameraSource = new CameraSource.Builder(dialog.getContext(), barcodeDetector).setAutoFocusEnabled(true).build();
        cameraSource = new CameraSource.Builder(dialog.getContext(), barcodeDetector).setFacing(CameraSource.CAMERA_FACING_FRONT).build(); //camera trước

        suv_qr.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(dialog.getContext(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
                    return;
                try {

                    cameraSource.start(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> qrCodes = detections.getDetectedItems();
                if (qrCodes.size() != 0 && firstDetected) {
                    firstDetected = false;
                    final String qrcode = qrCodes.valueAt(0).displayValue;

                }
            }
        });

        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kt01Db.ins_sig(g_ngay, g_somay, g_bophan, g_tenbp, g_ghichu,g_manv,g_sogio,g_tenhinh);
            }
        });

        tv_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_manvsig.setText("H23275");
            }
        });

        dialog.show();
    }

}
