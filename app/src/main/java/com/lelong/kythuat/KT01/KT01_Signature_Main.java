package com.lelong.kythuat.KT01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class KT01_Signature_Main extends AppCompatActivity implements KT01_Interface {
    private Create_Table createTable = null;
    private KT01_DB kt01Db = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    KT01_Signature_Main_Adapter kt01SignatureMainAdapter;
    ListView lv_signature_main;
    List hangmuc_list;
    Cursor getData_cur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt01_signature_main);

        addControls();
        addEvets();

    }

    private void addEvets() {
        kt01Db = new KT01_DB(getApplicationContext());
        kt01Db.open();
        Call_getData();
    }

    private void Call_getData() {
        hangmuc_list= new ArrayList<KT01_Signature_Main_Item>();
        getData_cur = kt01Db.getDepartmetData(Constant_Class.UserFactory);
        Drawable drawable_blue = getResources().getDrawable(R.drawable.button_kt_blue);
        Drawable drawable_green = getResources().getDrawable(R.drawable.button_kt_green);
        kt01SignatureMainAdapter = new KT01_Signature_Main_Adapter(this,
                R.layout.kt01_signature_main_rowitem,
                getData_cur,
                new String[]{"_id", "tc_fba007", "tc_fba009","ngaysig","manv_sig"},
                new int[]{R.id.tv_stt, R.id.tv_mabp,R.id.tv_tenbp,R.id.tv_ngaysig,R.id.tv_manv},
                drawable_blue,
                drawable_green,
                (KT01_Interface) this ,Constant_Class.UserXuong);

        kt01SignatureMainAdapter.setViewBinder((view, getData_cur, columnIndex) -> {
            if (view.getId() == R.id.tv_stt) {
                int rowNumber = getData_cur.getPosition() + 1;
                ((TextView) view).setText(String.valueOf(rowNumber));
                return true;
            }
            return false;
        });
        lv_signature_main.setAdapter(kt01SignatureMainAdapter);

    }

    private void addControls() {
        lv_signature_main=findViewById(R.id.lv_signature_main);
    }

    @Override
    public void takefoto(Context applicationContext, String key) {

    }

    @Override
    public void loadData_Sig() {

    }

    @Override
    public void loadData_Search_Sig() {
        kt01SignatureMainAdapter.notifyDataSetChanged();
    }
}