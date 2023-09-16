package com.lelong.kythuat.KT02;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Search_Signature_KT02 extends AppCompatActivity implements KT02_Interface {
    private Create_Table createTable = null;
    private KT02_DB kt02Db = null;
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
        setContentView(R.layout.search_signature_kt02);
        lv_searchsig = findViewById(R.id.lv_search_signature);
        createTable = new Create_Table(getApplicationContext());
        createTable.open();
        kt02Db = new KT02_DB(getApplicationContext());
        kt02Db.open();
        Bundle getbundle = getIntent().getExtras();
        g_tenxe = getbundle.getString("G_TENXE");
        //g_tenxe = "Xe nâng dầu";
        g_server = getString(R.string.server);

        LV_Search_Detail_sig();

        lv_searchsig.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
    private void LV_Search_Detail_sig(){
        String g_bp= Constant_Class.UserFactory;
        if (g_bp.equals("DH") ) {
            g_fia15 = "D";
        }
        if (g_bp.equals("BL")){
            g_fia15 = "B";
        }
        String ngaysig = dateFormatKT02.format(new Date()).toString();
        cursor = createTable.getAll_fiaud_sig_search(g_tenxe,g_fia15,ngaysig);
        Drawable drawable_blue = getResources().getDrawable(R.drawable.button_kt_blue);
        Drawable drawable_green = getResources().getDrawable(R.drawable.button_kt_green);
        Search_List_Sigture listSignature = new Search_List_Sigture(this,
                R.layout.kt02_search_signature_row, cursor,
                new String[]{"_id","somay_sig","mabp_sig","tebp_sig","ngay_sig","manv_sig"},
                new int[]{R.id.tv_stt, R.id.tv_somay, R.id.tv_mabp,R.id.tv_tenbp,R.id.tv_ngaysig,R.id.tv_manvsig},this,drawable_blue,drawable_green,g_tenxe);

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

        lv_searchsig.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) listSignature.getItem(position);
                // Ở đây, bạn có thể thực hiện các hành động liên quan đến dòng đã chọn trong dialog

                // Ví dụ: hiển thị thông báo khi chọn dòng
                Toast.makeText(getApplicationContext(), "Bạn đã chọn: " + selectedItem, Toast.LENGTH_SHORT).show();


            }
        });
    }


    @Override
    public void loadData() {

    }

    @Override
    public void loadData_Sig() {

    }

    @Override
    public void loadData_Search_Sig() {
        LV_Search_Detail_sig();
    }
}