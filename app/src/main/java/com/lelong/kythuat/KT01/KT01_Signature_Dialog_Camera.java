package com.lelong.kythuat.KT01;

import static org.chromium.base.ThreadUtils.runOnUiThread;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.lelong.kythuat.KT07.KT07_CheckList_Row;
import com.lelong.kythuat.KT07.KT07_Check_Adapter;
import com.lelong.kythuat.KT07.KT07_DB;
import com.lelong.kythuat.KT07.KT07_Main_RowItem;
import com.lelong.kythuat.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KT01_Signature_Dialog_Camera {
    private KT01_DB db = null;
    private Context context;
    private Dialog dialog;
    RecyclerView recyclerView;
    List<com.lelong.kythuat.KT07.KT07_CheckList_Row> KT07_CheckList_Row;
    Cursor cur_getdata;
    String g_today;
    KT01_Signature_Dialog_Adapter kt01SignatureDialogAdapter;
    List<KT01_Signature_Dialog_Model> kt01SignatureDialogModels;
    SurfaceView suv_qr;
    String s_bophan, s_tenbp, s_ngay;
    int position;
    TextView tv_bpname;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    boolean firstDetected = true;
    Cursor mCursor;

    public KT01_Signature_Dialog_Camera() {
        // Empty constructor required for DialogFragment
    }

    public KT01_Signature_Dialog_Camera(Context context,String s_bophan, String s_tenbp, String s_ngay, int position) {
        this.context = context;
        this.s_bophan = s_bophan;
        this.s_tenbp = s_tenbp;
        this.s_ngay = s_ngay;
        this.position = position;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.kt01_signature_main_camera);
        dialog.setCancelable(false);
        // Cố định chiều rộng và chiều cao của dialog
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; // Hoặc kích thước bạn muốn
            window.setAttributes(layoutParams);
        }
        db = new KT01_DB(context);
        db.open();

        addControls();
        addEvents();
    }
    private void setupDialogSize() {
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Window window = dialog.getWindow();
                if (window != null) {
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                    layoutParams.copyFrom(window.getAttributes());
                    int width = WindowManager.LayoutParams.MATCH_PARENT;
                    int height = WindowManager.LayoutParams.WRAP_CONTENT;
                    layoutParams.width = width;
                    layoutParams.height = height;
                    window.setAttributes(layoutParams);
                }
            }
        });
    }

    private void addControls() {
        firstDetected = true;
        recyclerView = dialog.findViewById(R.id.rvc_kyten);
        tv_bpname = dialog.findViewById(R.id.tv_bpname);
        suv_qr = (SurfaceView) dialog.findViewById(R.id.suv_qr);
        g_today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        kt01SignatureDialogModels = new ArrayList<KT01_Signature_Dialog_Model>();
        kt01SignatureDialogModels.add(new KT01_Signature_Dialog_Model("H32687", " ", " "));
        kt01SignatureDialogAdapter = new KT01_Signature_Dialog_Adapter(context,
                R.layout.kt01_sign_rowitem,
                mCursor);
        recyclerView.setAdapter(kt01SignatureDialogAdapter);
        tv_bpname.setText(s_tenbp);
        barcodeDetector = new BarcodeDetector.Builder(dialog.getContext()).setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(dialog.getContext(), barcodeDetector).setRequestedPreviewSize(300, 300).build();
        cameraSource = new CameraSource.Builder(dialog.getContext(), barcodeDetector).setAutoFocusEnabled(true).build();
        cameraSource = new CameraSource.Builder(dialog.getContext(), barcodeDetector).setFacing(CameraSource.CAMERA_FACING_FRONT).build();
    }

    private void addEvents() {
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
                    final String qrcode = qrCodes.valueAt(0).displayValue;
                    if (qrcode.trim().startsWith("H") && qrcode.trim().length() == 6) {
                        //tv_manvsig.setText(qrcode.trim());
                        kt01SignatureDialogModels.add(new KT01_Signature_Dialog_Model(qrcode.trim(), " ", " "));
                        db.ins_sig(s_ngay,
                                null,
                                s_bophan,
                                s_tenbp,
                                " ",
                                qrcode.trim(),
                                "",
                                null);
                        firstDetected = false;
                        //tv_bpname.setText(qrcode.trim());
                        if (kt01SignatureDialogAdapter != null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        //kt01SignatureDialogAdapter.notifyDataSetChanged();
                                        mCursor = db.getAll_kyten(s_bophan,s_tenbp,s_ngay);
                                        kt01SignatureDialogAdapter = new KT01_Signature_Dialog_Adapter(dialog.getContext(),
                                                R.layout.kt01_sign_rowitem,
                                                mCursor);
                                        recyclerView.setAdapter(kt01SignatureDialogAdapter);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                    }
                }


            }
        });
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
