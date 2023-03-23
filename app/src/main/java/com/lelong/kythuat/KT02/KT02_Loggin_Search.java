package com.lelong.kythuat.KT02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.MainActivity;
import com.lelong.kythuat.Menu;
import com.lelong.kythuat.R;

import java.util.ArrayList;
import java.util.List;

public class KT02_Loggin_Search extends AppCompatActivity implements KT02_Interface{
    private Create_Table createTable = null;
    Cursor cursor_1, cursor_2;
    private Activity activity;
    ListView lv_search02;
    private Context context;
    Dialog dialog;
    Button btnkt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt02_loggin_search);
        lv_search02 = findViewById(R.id.lv_search02);
        this.activity = activity;
        /*this.context=context;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt02_loggin_search);*/
        dialog = new Dialog(KT02_Loggin_Search.this);
        dialog.setContentView(R.layout.kt02_loggin_search);
        //btnkt = dialog.findViewById(R.id.btnkt);
        //btnkt.setOnClickListener(btnlistener1);
        createTable = new Create_Table(getApplicationContext());

        createTable.open();
        LV_Detail();
    }

    private void LV_Detail(){

        //List<Search_List> search_lists = new ArrayList<>();


        //cbxbophan.setAdapter(bophan_adapter);

        cursor_1 = createTable.getAll_fiaud03();
        List_Bophan listBophan = new List_Bophan(dialog.getContext(),
                R.layout.kt02_search_row, cursor_1,
                new String[]{"_id","fiaud03", "fia15", "fka02"},
                new int[]{R.id.tv_stt, R.id.tv_somay, R.id.tv_mabp,R.id.tv_tenbp},this);



        listBophan.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.tv_stt) {
                    int rowNumber = cursor.getPosition() + 1;
                    ((TextView) view).setText(String.valueOf(rowNumber));
                    return true;
                }

                return false;
            }

        });
        lv_search02.setAdapter(listBophan);
    }

    @Override
    public void loadData() {
        LV_Detail();
    }
}
