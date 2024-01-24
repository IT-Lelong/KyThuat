package com.lelong.kythuat.KT02;

import static org.chromium.base.ThreadUtils.runOnUiThread;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.lelong.kythuat.KT02.KT02_DB;
import com.lelong.kythuat.KT02.KT02_Interface;
import com.lelong.kythuat.KT02.KT02_Signature_Dialog_Adapter;
import com.lelong.kythuat.KT02.KT02_Signature_Dialog_Camera;
import com.lelong.kythuat.KT02.KT02_Signature_Dialog_Model;
import com.lelong.kythuat.KT07.KT07_CheckList_Row;
import com.lelong.kythuat.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KT02_Signature_Dialog_Camera extends DialogFragment {
    private KT02_DB db = null;
    private Context context;
    private Dialog dialog;
    RecyclerView recyclerView;
    List<com.lelong.kythuat.KT07.KT07_CheckList_Row> KT07_CheckList_Row;
    Cursor cur_getdata;
    String g_today;
    KT02_Signature_Dialog_Adapter kt02SignatureDialogAdapter;
    List<KT02_Signature_Dialog_Model> kt02SignatureDialogModels;
    SurfaceView suv_qr;
    String s_bophan, s_tenbp, s_ngay,s_somay;
    int position;
    TextView tv_bpname;
    CameraSource cameraSource;
    BarcodeDetector barcodeDetector;
    boolean firstDetected = true;
    Cursor mCursor;
    Button btninsert,btnthoat;
    String tv_barcode;
    KT02_Interface kt02Interface;
    private KT02_Signature_Dialog_Camera.OnDialogDismissListener dismissListener;
    public KT02_Signature_Dialog_Camera() {
        // Empty constructor required for DialogFragment
    }

    public KT02_Signature_Dialog_Camera(Context context,String s_somay,String s_bophan, String s_tenbp, String s_ngay, int position) {
        this.context = context;
        this.s_bophan = s_bophan;
        this.s_tenbp = s_tenbp;
        this.s_ngay = s_ngay;
        this.position = position;
        this.s_somay = s_somay;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.kt02_signature_main_camera);
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
        db = new KT02_DB(context);
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
        btninsert = dialog.findViewById(R.id.btninsert);
        btnthoat = dialog.findViewById(R.id.btnthoat);
        g_today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        kt02SignatureDialogModels = new ArrayList<KT02_Signature_Dialog_Model>();
        mCursor = db.getAll_kyten(s_somay,s_bophan,s_tenbp,s_ngay);
        mCursor.moveToFirst();
        int num = mCursor.getCount();
        kt02SignatureDialogModels.clear();
        for (int i = 0; i < num; i++) {
            try {
                String G_MANV = mCursor.getString(mCursor.getColumnIndexOrThrow("manv_sig"));
                String G_SOGIO = mCursor.getString(mCursor.getColumnIndexOrThrow("sogio_sig"));
                String G_GHICHU = mCursor.getString(mCursor.getColumnIndexOrThrow("ghichu_sig"));
                kt02SignatureDialogModels.add(new KT02_Signature_Dialog_Model(G_MANV, G_SOGIO, G_GHICHU));
            } catch (Exception e) {
                String err = e.toString();
            }
            mCursor.moveToNext();
        }
        kt02SignatureDialogAdapter = new KT02_Signature_Dialog_Adapter(context,
                R.layout.kt01_sign_rowitem,
                kt02SignatureDialogModels,s_bophan,s_tenbp,s_ngay,s_somay);
        LinearLayoutManager layoutManager = new LinearLayoutManager(dialog.getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(kt02SignatureDialogAdapter);
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
                        int l_sl = db.check_kyten(s_somay,qrcode.trim(),s_bophan,s_tenbp,s_ngay);
                        if (l_sl == 0 ){
                            kt02SignatureDialogModels.add(new KT02_Signature_Dialog_Model(qrcode.trim(), " ", " "));
                        }
                        tv_barcode = qrcode.trim();
                        firstDetected = false;
                        //tv_bpname.setText(qrcode.trim());
                        if (kt02SignatureDialogAdapter != null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        //kt01SignatureDialogAdapter.notifyDataSetChanged();
                                        //mCursor = db.getAll_kyten(s_bophan,s_tenbp,s_ngay);
                                        kt02SignatureDialogAdapter.notifyDataSetChanged();
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
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firstDetected = false;
                dismiss();
                if (dismissListener != null) {
                    dismissListener.onDialogDismissed();
                }

            }
        });
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long kq = db.ins_sig(s_ngay,
                        s_somay,
                        s_bophan,
                        s_tenbp,
                        " ",
                        tv_barcode,
                        "",
                        null);
                if(kq == 1){
                    Toast.makeText(dialog.getContext(), "Lưu dữ liệu thành công!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(dialog.getContext(), "Lưu dữ liệu không thành công!", Toast.LENGTH_SHORT).show();
                }

                firstDetected = true;
            }
        });

    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
    public interface OnDialogDismissListener {
        void onDialogDismissed();
    }

    public void setOnDialogDismissListener(KT02_Signature_Dialog_Camera.OnDialogDismissListener listener) {
        this.dismissListener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDialogDismissed();
        }
    }
}
