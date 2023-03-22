package com.lelong.kythuat.KT02;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lelong.kythuat.MainActivity;
import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class List_Bophan extends SimpleCursorAdapter {

    private final Context context;
    private final int layout;
    private final Cursor mCursor;
    private final String[] from;
    private final int[] to;
    Dialog dialog;
    String s_somay,s_bophan,s_tenbp;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    TextView tv_date;
    EditText tv_ghichu;
    Button btn_Date,btn_Insert,btnkt;
    private KT02_DB kt02Db = null;
    Cursor cursor_1;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View view = super.getView(position, convertView, parent);

       /* Button button = view.findViewById(R.id.btnkt);
        button.setTag(position);
        button.setOnClickListener(btnlistener1);


        //Search_List search_list=(Search_List) getItem(position);
        // Example: @listItemLayoutResource: R.layout.spinner_item_layout_resource
        // (File: layout/spinner_item_layout_resourcerce.xml)
        //View rowView = this.flater.inflate(this.listItemLayoutResource, null, true);

        // Example: @textViewItemNameId: R.id.textView_item_name
        // (A TextView in file layout/spinner_item_layout_resourcerce.xml)

        return super.getView(position, convertView, parent);*/

        View view = super.getView(position, convertView, parent);

        btnkt = view.findViewById(R.id.btnkt);
        btnkt.setTag(position);

        s_somay = mCursor.getString(mCursor.getColumnIndexOrThrow("fiaud03"));
        s_bophan = mCursor.getString(mCursor.getColumnIndexOrThrow("fia15"));
        s_tenbp = mCursor.getString(mCursor.getColumnIndexOrThrow("fka02"));

        kt02Db = new KT02_DB(context.getApplicationContext());
        kt02Db.open();
        Boolean chk_qrb = kt02Db.KT_fia_up(s_somay,s_bophan);
        if (chk_qrb == true){
            btnkt.setBackgroundColor(Color.BLUE);
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


                switch (v.getId()) {

                    case R.id.btnkt: {
                        /*Intent QR020 = new Intent();
                        QR020.setClass(v.getContext(), KT02_CheckDate.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("s_somay", s_somay);
                        bundle.putString("s_bophan", s_bophan);
                        QR020.putExtras(bundle);
                        v.getContext().startActivity(QR020);*/
                        openDialog(s_somay,s_bophan,s_tenbp);

                        break;
                    }
                }

            }
        });


        return view;

    }

    private void openDialog(String g_somay,String g_bophan,String g_tenbp) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt02_checkdate);
        tv_date = dialog.findViewById(R.id.tv_date);
        tv_ghichu=dialog.findViewById(R.id.tv_ghichu);
        btn_Date = dialog.findViewById(R.id.btn_Date);
        btn_Insert=dialog.findViewById(R.id.btninsert);

        tv_date.setText(dateFormat.format(new Date()).toString());

        btn_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }

            private void showDatePickerDialog() {
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear,
                                          int dayOfMonth) {
                        //Mỗi lần thay đổi ngày tháng năm thì cập nhật lại TextView Date
                        tv_date.setText(year + "/" + (monthOfYear) + "/" + (dayOfMonth));
                    }
                };
                //các lệnh dưới này xử lý ngày giờ trong DatePickerDialog
                //sẽ giống với trên TextView khi mở nó lên
                String s = tv_date.getText() + "";
                String strArrtmp[] = s.split("/");
                int ngay = Integer.parseInt(strArrtmp[2]);
                int thang = Integer.parseInt(strArrtmp[1]);
                int nam = Integer.parseInt(strArrtmp[0]);
                DatePickerDialog pic = new DatePickerDialog(
                        context, AlertDialog.THEME_DEVICE_DEFAULT_DARK,
                        callback, nam, thang, ngay);
                pic.show();
            }
        });

        btn_Insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ngay=tv_date.getText().toString();
                String g_ghichu=tv_ghichu.getText().toString().trim();
                //kt02Db = new KT02_DB(dialog.getContext());
                //kt02Db.open();
                kt02Db.ins_tc_fia_file(ngay,g_somay,g_bophan,g_tenbp,g_ghichu);

                Boolean chk_qrb = kt02Db.KT_fia_up(s_somay,s_bophan);
                if (chk_qrb == true){
                    btnkt.setBackgroundColor(Color.BLUE);
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public List_Bophan(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.context = context;
        this.layout = layout;
        this.mCursor = c;
        this.from = from;
        this.to = to;
    }
}
