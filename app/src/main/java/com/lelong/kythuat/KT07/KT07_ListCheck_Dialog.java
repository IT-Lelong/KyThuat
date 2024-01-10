package com.lelong.kythuat.KT07;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.KT01.KT01_DB;
import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class KT07_ListCheck_Dialog extends DialogFragment {
    private KT07_DB db = null;
    private Context context;
    private Dialog dialog;
    RecyclerView recyclerView;
    List<KT07_CheckList_Row> KT07_CheckList_Row;
    Cursor cur_getdata;
    String g_today;
    KT07_Check_Adapter kt07CheckAdapter;
    List<KT07_CheckList_Row> myVariable;

    public KT07_ListCheck_Dialog() {
        // Empty constructor required for DialogFragment
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kt07_listcheck_dialog, container, false);

        recyclerView = view.findViewById(R.id.rcv_hangmuc);
        recyclerView.setLayoutManager(new LinearLayoutManager(dialog.getContext()));

        // Lưu ý: Thay thế "yourList" bằng danh sách bạn muốn hiển thị
        List<KT07_CheckList_Row> yourList = getArguments().getParcelableArrayList("list");
        kt07CheckAdapter = new KT07_Check_Adapter(dialog.getContext(),R.layout.kt07_listcheck_row_dialog,yourList);
        recyclerView.setAdapter(kt07CheckAdapter);

        return view;
    }

    public KT07_ListCheck_Dialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.kt07_listcheck_dialog);
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
        db = new KT07_DB(context);
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
        recyclerView = dialog.findViewById(R.id.rcv_hangmuc);
        g_today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        //KT07_CheckList_Row = new ArrayList<KT07_CheckList_Row>();
        kt07CheckAdapter = new KT07_Check_Adapter(dialog.getContext(),
                R.layout.kt07_listcheck_row_dialog,
                myVariable);
        recyclerView.setAdapter(kt07CheckAdapter);
    }

    private void addEvents() {

    }

}

