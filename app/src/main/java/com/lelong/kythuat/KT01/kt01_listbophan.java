package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.lelong.kythuat.KT02.List_Bophan;
import com.lelong.kythuat.R;

public class kt01_listbophan extends AppCompatActivity {
    ListView lis1;
    ListView lv_query;
    private KT01_DB db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt01_listbophan);

        db = new KT01_DB(this);
        db.open();
        lis1 = findViewById(R.id.lv_query1);

            Cursor cursor = db.getAll_gem1();

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.kt01_dsbophan, cursor,
                new String[]{"gem01", "gem02"},
                new int[]{R.id.sp_mabp, R.id.sp_tenbp},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lis1.setAdapter(simpleCursorAdapter);

        lis1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                @SuppressLint("Range") String selectedData = cursor.getString(cursor.getColumnIndex("gem01"));

                Intent intent = new Intent();
                intent.putExtra("selectedData", selectedData);
                setResult(RESULT_OK, intent);
                finish();
            }
        });


    }
}
