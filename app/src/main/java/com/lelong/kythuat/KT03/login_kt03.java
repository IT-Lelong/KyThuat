package com.lelong.kythuat.KT03;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class login_kt03 {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    public void login_dialog(Context context, String menuID) {
        TextView tv_name, tv_date;
        RadioButton radio_a, radio_b;
        Button btn_Date, btn_Insert, btn_Query;
        final String[] g_ca = {""};

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt03_login_dialog);

        tv_name = dialog.findViewById(R.id.tv_name);
        tv_date = dialog.findViewById(R.id.tv_date);
        radio_a = dialog.findViewById(R.id.radio_a);
        radio_b = dialog.findViewById(R.id.radio_b);
        btn_Date = dialog.findViewById(R.id.btn_Date);
        btn_Insert = dialog.findViewById(R.id.btn_Insert);
        btn_Query = dialog.findViewById(R.id.btn_Query);

        radio_a.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                g_ca[0] = "Ca 1";
            }
        });
        radio_b.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                g_ca[0] = "Ca 2";
            }
        });

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

        //btn_Insert.setOnClickListener(listenerButton);
        //btn_Query.setOnClickListener(listenerButton);

        tv_name.setText(menuID);
        tv_date.setText(dateFormat.format(new Date()).toString());


        dialog.show();
    }

}
