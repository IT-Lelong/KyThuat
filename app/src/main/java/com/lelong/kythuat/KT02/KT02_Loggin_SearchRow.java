package com.lelong.kythuat.KT02;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.Menu;
import com.lelong.kythuat.R;

public class KT02_Loggin_SearchRow extends AppCompatActivity {
    Button btnkt;
    Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt02_search_row);
        dialog = new Dialog(KT02_Loggin_SearchRow.this);
        dialog.setContentView(R.layout.kt02_search_row);
        btnkt = dialog.findViewById(R.id.btnkt);
        btnkt.setOnClickListener(btnlistener1);
        /*createTable = new Create_Table(getApplicationContext());
        createTable.open();

        cursor_1 = createTable.getAll_fiaud03();
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(dialog.getContext(),
                R.layout.kt02_search_row, cursor_1,
                new String[]{"_id","fiaud03", "fia15", "fka02"},
                new int[]{R.id.tv_stt, R.id.tv_somay, R.id.tv_mabp,R.id.tv_tenbp},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
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
        lv_search02.setAdapter(simpleCursorAdapter);*/
    }
    private Button.OnClickListener btnlistener1 = new Button.OnClickListener() {
        public void onClick(View v) {
            //利用switch case方法，之後新增按鈕只需新增case即可
            switch (v.getId()) {

                case R.id.btnkt: {

                    Intent QR020 = new Intent();
                    QR020.setClass(v.getContext(), Menu.class);
                    Bundle bundle = new Bundle();
                    // bundle.putString("somay", g_soxe);
                    ///bundle.putString("bophan", g_bophan);
                    bundle.putString("LAYOUT", "notlogin");
                    QR020.putExtras(bundle);
                    v.getContext().startActivity(QR020);
                    dialog.dismiss();
                    break;
                }
            }
        }
    };
}
