package com.lelong.kythuat.KT01;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lelong.kythuat.KT02.KT02_DB;
import com.lelong.kythuat.KT02.KT02_Interface;
import com.lelong.kythuat.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class KT01_List_Signature extends SimpleCursorAdapter {
    private final Context context;
    private final int layout;
    private final Cursor mCursor;
    private final String[] from;
    private final int[] to;
    Dialog dialog;
    String s_somay, s_bophan, s_tenbp, s_ngay, l_tenxe;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    TextView tv_date;
    EditText tv_ghichu, tv_manv, tv_sogio;
    Button btn_Date, btn_Insert, btnkt;
    private KT01_DB kt01Db = null;
    private final Drawable drawable_blue;
    private final Drawable drawable_green;
    KT01_Interface kt01_interface;
    private View signaturePad;


    public KT01_List_Signature(Context context, int layout, Cursor c, String[] from, int[] to, KT01_Interface kt01Interface, Drawable drawable_blue, Drawable drawable_green, String l_tenxe) {
        super(context, layout, c, from, to);
        this.context = context;
        this.layout = layout;
        this.mCursor = c;
        this.from = from;
        this.to = to;
        this.kt01_interface = kt01Interface;

        kt01Db = new KT01_DB(context);
        kt01Db.open();
        this.drawable_blue = drawable_blue;
        this.drawable_green = drawable_green;
        this.l_tenxe = l_tenxe;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        btnkt = view.findViewById(R.id.btnkt);
        btnkt.setTag(position);

        s_bophan = mCursor.getString(mCursor.getColumnIndexOrThrow("tc_fba007"));
        s_tenbp = mCursor.getString(mCursor.getColumnIndexOrThrow("tc_fba009"));
        s_ngay = mCursor.getString(mCursor.getColumnIndexOrThrow("ngaysig"));

        //Boolean chk_qrb = kt02Db.KT_fia_up_sig(s_somay, s_bophan,s_ngay);
        Boolean chk_qrb = kt01Db.KT_fia_up_sig01(s_somay, s_bophan,s_ngay);
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
                // Do something with the data at the clicked position
                s_bophan = mCursor.getString(mCursor.getColumnIndexOrThrow("tc_fba007"));
                s_tenbp = mCursor.getString(mCursor.getColumnIndexOrThrow("tc_fba009"));
                s_ngay = mCursor.getString(mCursor.getColumnIndexOrThrow("ngaysig"));

                openDialog(s_somay, s_bophan, s_tenbp, s_ngay, position);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        kt01_interface.loadData_Sig();
                    }
                });
            }
        });

        return view;
    }

    private void openDialog(String g_somay, String g_bophan, String g_tenbp, String g_ngay, int vitri) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt02_insertinfo);
        tv_manv = dialog.findViewById(R.id.tv_manv);
        tv_sogio = dialog.findViewById(R.id.tv_sogio);
        tv_ghichu = dialog.findViewById(R.id.tv_ghichu);
        btn_Insert = dialog.findViewById(R.id.btninsert);
        signaturePad = dialog.findViewById(R.id.signaturePad);
        btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String g_ghichu = tv_ghichu.getText().toString().trim();
                String g_manv = tv_manv.getText().toString().trim();
                String g_sogio = tv_sogio.getText().toString().trim();
                String g_tenhinh = "TB_" + g_bophan + "_" + g_ngay + "_" + g_manv;
                /*kt02Db.ins_sig(g_ngay, g_somay, g_bophan, g_tenbp, g_ghichu,g_manv,g_sogio,g_tenhinh);
                dialog.dismiss();
                kt02_interface.loadData_Sig();*/

                //Lưu chữ ký
                Bitmap signatureBitmap = getTransparentSignatureBitmap();
                //Bitmap signatureBitmap = null;
                // Lưu chữ ký vào hệ thống hoặc xử lý theo nhu cầu của bạn
                // Ví dụ: lưu ảnh chữ ký vào thư mục ứng dụng
                //String filePath = getExternalFilesDir(null).getAbsolutePath() + "/signature.png";
                //String filePath = ("/storage/emulated/0/Pictures" + "/signature10.png");
                //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                String filePath = ("/storage/emulated/0/Pictures/" + g_tenhinh + ".jpg");
                try {
                    FileOutputStream fos = new FileOutputStream(filePath);
                    signatureBitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
                    fos.flush();
                    fos.close();
                    Toast.makeText(context.getApplicationContext(), "Chữ ký đã được lưu", Toast.LENGTH_SHORT).show();
                    kt01Db.ins_sig(g_ngay, g_somay, g_bophan, g_tenbp, g_ghichu, g_manv, g_sogio, g_tenhinh);
                    dialog.dismiss();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Lưu chữ ký(E)
            }
        });
        dialog.show();

    }
    public Bitmap getTransparentSignatureBitmap() {
        // Tạo một bitmap mới với kích thước tương đương với kích thước của SignaturePad
        //Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(signaturePad.getWidth(), signaturePad.getHeight(), Bitmap.Config.ARGB_8888);
        // Tạo một Canvas để vẽ chữ ký lên bitmap
        Canvas canvas = new Canvas(bitmap);

        // Vẽ nền trong suốt trên bitmap
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        // Vẽ chữ ký lên bitmap
        signaturePad.draw(canvas);

        return bitmap;
    }
}
