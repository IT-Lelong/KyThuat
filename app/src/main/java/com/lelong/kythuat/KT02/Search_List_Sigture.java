package com.lelong.kythuat.KT02;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lelong.kythuat.KT02.KT02_DB;
import com.lelong.kythuat.KT02.KT02_Interface;
import com.lelong.kythuat.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Search_List_Sigture extends SimpleCursorAdapter {
    private final Context context;
    private final int layout;
    private final Cursor mCursor;
    private final String[] from;
    private final int[] to;
    Dialog dialog;
    String s_somay, s_bophan, s_tenbp, s_ngay, l_tenxe, s_manv;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    TextView tv_tenhinh,tv_manv,tv_sogio,tv_ghichu,tv_manvsig;
    //EditText tv_tenhinh;
    Button btn_Date, btn_Insert, btnkt;
    private KT02_DB kt02Db = null;
    private final Drawable drawable_blue;
    private final Drawable drawable_green;
    KT02_Interface kt02_interface;
    private View signaturePad;
    ImageView imageViews;
    Cursor cursor_1, cursor_2, cursor_3;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        btnkt = view.findViewById(R.id.btnkt);
        btnkt.setTag(position);

        s_somay = mCursor.getString(mCursor.getColumnIndexOrThrow("somay_sig"));
        s_bophan = mCursor.getString(mCursor.getColumnIndexOrThrow("mabp_sig"));
        s_tenbp = mCursor.getString(mCursor.getColumnIndexOrThrow("tebp_sig"));
        s_ngay = mCursor.getString(mCursor.getColumnIndexOrThrow("ngay_sig"));
        s_manv = mCursor.getString(mCursor.getColumnIndexOrThrow("manv_sig"));

        //Boolean chk_qrb = kt02Db.KT_fia_up_sig(s_somay, s_bophan,s_ngay);
        Boolean chk_qrb = kt02Db.KT_fia_up_sig(s_somay, s_bophan);
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
                s_somay = mCursor.getString(mCursor.getColumnIndexOrThrow("somay_sig"));
                s_bophan = mCursor.getString(mCursor.getColumnIndexOrThrow("mabp_sig"));
                s_tenbp = mCursor.getString(mCursor.getColumnIndexOrThrow("tebp_sig"));
                s_ngay = mCursor.getString(mCursor.getColumnIndexOrThrow("ngay_sig"));
                s_manv = mCursor.getString(mCursor.getColumnIndexOrThrow("manv_sig"));

                openDialog(s_somay, s_bophan, s_tenbp, s_ngay, position,s_manv);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        kt02_interface.loadData_Search_Sig();
                    }
                });
            }
        });

        return view;
    }

    public Search_List_Sigture(Context context, int layout, Cursor c, String[] from, int[] to, KT02_Interface kt02Interface, Drawable drawable_blue, Drawable drawable_green, String l_tenxe) {
        super(context, layout, c, from, to);
        this.context = context;
        this.layout = layout;
        this.mCursor = c;
        this.from = from;
        this.to = to;
        this.kt02_interface = kt02Interface;

        kt02Db = new KT02_DB(context);
        this.drawable_blue = drawable_blue;
        this.drawable_green = drawable_green;
        this.l_tenxe = l_tenxe;
        kt02Db.open();
    }

    private void openDialog(String g_somay, String g_bophan, String g_tenbp, String g_ngay, int vitri, String g_manv) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.search_image_kt02);
        tv_tenhinh = dialog.findViewById(R.id.menuID);
        imageViews= dialog.findViewById(R.id.imageView1);
        tv_manv = dialog.findViewById(R.id.tv_manv);
        tv_sogio = dialog.findViewById(R.id.tv_sogio);
        tv_ghichu = dialog.findViewById(R.id.tv_ghichu);

        /*tv_manv = dialog.findViewById(R.id.tv_manv);
        tv_sogio = dialog.findViewById(R.id.tv_sogio);
        tv_ghichu = dialog.findViewById(R.id.tv_ghichu);
        btn_Insert = dialog.findViewById(R.id.btninsert);
        signaturePad = dialog.findViewById(R.id.signaturePad);


        btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String g_ghichu = tv_ghichu.getText().toString().trim();
                String g_manv = tv_manv.getText().toString().trim();
                String g_sogio = tv_sogio.getText().toString().trim();
                String g_tenhinh = l_tenxe + "_" + g_somay + "_" + g_bophan + "_" + g_ngay + "_" + g_manv;
                /*kt02Db.ins_sig(g_ngay, g_somay, g_bophan, g_tenbp, g_ghichu,g_manv,g_sogio,g_tenhinh);
                dialog.dismiss();
                kt02_interface.loadData_Sig();*/

                //Lưu chữ ký
                /*Bitmap signatureBitmap = getTransparentSignatureBitmap();
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
                    kt02Db.ins_sig(g_ngay, g_somay, g_bophan, g_tenbp, g_ghichu, g_manv, g_sogio, g_tenhinh);
                    dialog.dismiss();

                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                //Lưu chữ ký(E)
            //}
        //});

        cursor_1 = kt02Db.getAll_Sigture(g_somay,g_bophan,g_ngay,g_manv);
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        //station = new String[num];
        for (int i = 0; i < num; i++) {

            try {
                @SuppressLint("Range") String manv_sig= cursor_1.getString(cursor_1.getColumnIndex("manv_sig"));
                @SuppressLint("Range") String sogio_sig= cursor_1.getString(cursor_1.getColumnIndex("sogio_sig"));
                @SuppressLint("Range") String ghichu_sig= cursor_1.getString(cursor_1.getColumnIndex("ghichu_sig"));
                @SuppressLint("Range") String tenhinh_sig= cursor_1.getString(cursor_1.getColumnIndex("tenhinh_sig"));
                tv_manv.setText(manv_sig);
                tv_sogio.setText(sogio_sig);
                tv_ghichu.setText(ghichu_sig);
                loadanh(tenhinh_sig);

            } catch (Exception e) {
                String err = e.toString();
            }
            /*stationlist = new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_spinner_item, station);
            cbxsoxe.setAdapter(stationlist);
            cbxsoxe.setSelection(0);*/
            cursor_1.moveToNext();
        }




        dialog.show();

    }

    private void loadanh(String tenhinh) {
        //String tenhinh="Xe nâng dầu_01_BB0500_2023-09-15_H23275";
                String a = "/storage/emulated/0/Pictures/" + tenhinh + ".jpg" + "";
                //num2 = num2 - 1;
                File imgFile = new File(a);

                if (imgFile.exists()) {
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
                        imageViews.setImageBitmap(myBitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


    }
}
