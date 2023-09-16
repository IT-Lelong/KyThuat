package com.lelong.kythuat.KT01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.KT01.KT01_DB;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lelong.kythuat.R;

public class Search_Signature_KT01 extends AppCompatActivity implements KT01_Interface{
    private Create_Table createTable = null;
    private KT01_DB kt01Db = null;
    Cursor cursor;
    ListView lv_searchsig;
    String g_tc_faa001,g_tenxe,g_fia15 ;
    SimpleDateFormat dateFormatKT02 = new SimpleDateFormat("yyyy-MM-dd");
    JSONObject ujobject;
    JSONArray jsonupload;
    String g_server = "";
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_signature_kt01);
        lv_searchsig = findViewById(R.id.lv_search_signature);
        createTable = new Create_Table(getApplicationContext());
        createTable.open();
        kt01Db = new KT01_DB(getApplicationContext());
        kt01Db.open();
        Bundle getbundle = getIntent().getExtras();
        g_tenxe = getbundle.getString("G_TENXE");
        g_server = getString(R.string.server);

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
        //cursor = createTable.getAll_bp(g_tenxe,g_fia15,ngaysig);

        if (Constant_Class.UserFactory.equals("DH")) {
            cursor = createTable.getAll_bp_search(g_tenxe,g_fia15,ngaysig);
        } else {
            cursor = createTable.getAll_bp_BL_search(g_tenxe,g_fia15,ngaysig);
        }

        Drawable drawable_blue = getResources().getDrawable(R.drawable.button_kt_blue);
        Drawable drawable_green = getResources().getDrawable(R.drawable.button_kt_green);
        Search_List_Sigture_KT01 kt01ListSignature = new Search_List_Sigture_KT01(this,
                R.layout.kt01_search_sigture_row, cursor,
                new String[]{"_id", "mabp_sig", "tebp_sig","ngay_sig","manv_sig"},
                new int[]{R.id.tv_stt, R.id.tv_mabp,R.id.tv_tenbp,R.id.tv_ngaysig,R.id.tv_manvsig},this,drawable_blue,drawable_green,g_tenxe);

        kt01ListSignature.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
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
        lv_searchsig.setAdapter(kt01ListSignature);
    }

    @Override
    public void takefoto(Context applicationContext, String key) {

    }

    @Override
    public void loadData_Sig() {

    }

    @Override
    public void loadData_Search_Sig() {

    }
}