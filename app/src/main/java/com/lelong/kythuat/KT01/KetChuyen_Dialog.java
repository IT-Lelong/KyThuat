package com.lelong.kythuat.KT01;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.lelong.kythuat.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KetChuyen_Dialog {
    private KT01_DB db = null;
    private Context context;
    private Dialog dialog;
    private TextView  tv_processing, tv_processMaxValues, tv_updStatus;
    private ProgressBar progBar_transfer;
    private Button btnOk, btnCancel;
    private Spinner sp_bophan, sp_datekt;
    Cursor cur_getdata,cur_getdata1;
    List<String> datekt_List, department_List;
    ArrayAdapter<String> datekt_adapter, department_adapter;
    String g_today;
    public KetChuyen_Dialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.kt01_ketchuyen_dialog);
        dialog.setCancelable(false);

        db = new KT01_DB(context);
        db.open();

        addControls();
        addEvents();
    }

    private void addControls() {
        sp_bophan = dialog.findViewById(R.id.sp_department);
        sp_datekt = dialog.findViewById(R.id.sp_datekt);
        tv_processing = dialog.findViewById(R.id.tv_processing);
        tv_processMaxValues = dialog.findViewById(R.id.tv_processMaxValues);
        tv_updStatus = dialog.findViewById(R.id.tv_updStatus);
        progBar_transfer = dialog.findViewById(R.id.progBar_transfer);
        btnOk = dialog.findViewById(R.id.btnOk);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        g_today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        datekt_List = new ArrayList<>();
        department_List = new ArrayList<>();
        datekt_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, datekt_List);
        department_adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, department_List);
        sp_datekt.setAdapter(datekt_adapter);
        sp_bophan.setAdapter(department_adapter);
        cur_getdata = null;cur_getdata1 = null;
        cur_getdata1 = db.getAll_datekt();
        cur_getdata = db.getAll_bophan();
        if (cur_getdata != null && cur_getdata.moveToFirst()) {
            cur_getdata.moveToFirst();
            department_List.add("");
            for (int i = 0; i < cur_getdata.getCount(); i++) {
                String g_ten = cur_getdata.getString(cur_getdata.getColumnIndexOrThrow("tc_fba009"));
                department_List.add(g_ten);
                cur_getdata.moveToNext();
            }
            department_adapter.notifyDataSetChanged();
            cur_getdata.close();
        }
        if (cur_getdata1 != null && cur_getdata1.moveToFirst()) {
            cur_getdata1.moveToFirst();
            datekt_List.add("");
            for (int i = 0; i < cur_getdata1.getCount(); i++) {
                String g_date = cur_getdata1.getString(cur_getdata1.getColumnIndexOrThrow("tc_faa002"));
                datekt_List.add(g_date);
                cur_getdata1.moveToNext();
            }
            datekt_adapter.notifyDataSetChanged();
            cur_getdata1.close();
        }

        setStatus("0");
    }

    private void addEvents() {

    }

    public void setStatus(String g_value) {
        switch (g_value) {
            case "0" :
                tv_updStatus.setText("Xác nhận cập nhật");
                break;
            case "1" :
                tv_updStatus.setText("Tiến hành cập nhật...");
                break;
            case "2":
                tv_updStatus.setText("Cập nhật hoàn tất");
                break;
            default:
                // Xử lý khi giá trị không khớp với bất kỳ trường hợp nào
                tv_updStatus.setText(g_value);
                break;
        }
    }

    public void setProgressBar(int g_value) {
        setStatus("1");
        tv_processMaxValues.setText(" / " + g_value);
        progBar_transfer.setMax(g_value);
    }

    public void updateProgressBar(int progress) {
        setEnableBtn(false,false);
        tv_processing.setText(String.valueOf(progress));
        progBar_transfer.setProgress(progress);
    }
    public String getSelected_sp_datekt() {
        return sp_datekt.getSelectedItem().toString();
    }

    public String getSelected_sp_department() {
        String selectedDepartment = (sp_bophan.getSelectedItem() != null) ? sp_bophan.getSelectedItem().toString() : "";
        return selectedDepartment;
    }
    public void setOkButtonClickListener(View.OnClickListener listener) {
        btnOk.setOnClickListener(listener);
    }

    public void setCancelButtonClickListener(View.OnClickListener listener) {
        btnCancel.setOnClickListener(listener);
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void setEnableBtn(boolean bool_OK,boolean bool_Cancel) {
        btnOk.setEnabled(bool_OK);
        btnCancel.setEnabled(bool_Cancel);
    }
}
