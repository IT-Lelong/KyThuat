package com.lelong.kythuat.KT02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KT02_Signature_List extends AppCompatActivity implements KT02_Interface{
    private Create_Table createTable = null;
    Cursor cursor;
    ListView lv_searchsig;
    String g_tc_faa001,g_tenxe,g_fia15 ;
    SimpleDateFormat dateFormatKT02 = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt02_signature_list);
        lv_searchsig = findViewById(R.id.lv_search_signature);
        createTable = new Create_Table(getApplicationContext());
        createTable.open();
        Bundle getbundle = getIntent().getExtras();
        g_tenxe = getbundle.getString("G_TENXE");

        LV_Detail_sig();
    }
    private void LV_Detail_sig(){
        String g_bp= Constant_Class.UserFactory;
        if (g_bp.equals("DH") ) {
            g_fia15 = "D";
        }
        if (g_bp.equals("BL")){
            g_fia15 = "B";
        }
        String ngaysig = dateFormatKT02.format(new Date()).toString();
        cursor = createTable.getAll_fiaud03_sig(g_tenxe,g_fia15,ngaysig);
        Drawable drawable_blue = getResources().getDrawable(R.drawable.button_kt_blue);
        Drawable drawable_green = getResources().getDrawable(R.drawable.button_kt_green);
        List_Signature listSignature = new List_Signature(this,
                R.layout.kt02_signature_row, cursor,
                new String[]{"_id","fiaud03", "fia15", "fka02","ngaysig"},
                new int[]{R.id.tv_stt, R.id.tv_somay, R.id.tv_mabp,R.id.tv_tenbp,R.id.tv_ngaysig},this,drawable_blue,drawable_green);

        listSignature.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
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
        lv_searchsig.setAdapter(listSignature);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void loadData_Sig() {
        LV_Detail_sig();
    }
}