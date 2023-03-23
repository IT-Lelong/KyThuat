package com.lelong.kythuat.KT02;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

public class KT02_Loggin_Search extends AppCompatActivity implements KT02_Interface{
    private Create_Table createTable = null;
    Cursor cursor;
    ListView lv_search02;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt02_loggin_search);
        lv_search02 = findViewById(R.id.lv_search02);
        createTable = new Create_Table(getApplicationContext());
        createTable.open();
        LV_Detail();
    }

    private void LV_Detail(){
        cursor = createTable.getAll_fiaud03();
        Drawable drawable_blue = getResources().getDrawable(R.drawable.button_kt_blue);
        Drawable drawable_green = getResources().getDrawable(R.drawable.button_kt_green);
        List_Bophan listBophan = new List_Bophan(this,
                R.layout.kt02_search_row, cursor,
                new String[]{"_id","fiaud03", "fia15", "fka02"},
                new int[]{R.id.tv_stt, R.id.tv_somay, R.id.tv_mabp,R.id.tv_tenbp},this,drawable_blue,drawable_green);

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
