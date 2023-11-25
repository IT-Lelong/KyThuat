package com.lelong.kythuat.KT07;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class KT07_DB {
    private Context mCtx = null;
    //String DATABASE_NAME = "KyThuatDB02.db";
    String DATABASE_NAME = "KyThuatDB.db";
    public static SQLiteDatabase db = null;

    private final static String TABLE_NAME_TC_CEB = "tc_ceb_file";
    private final static String tc_ceb01 = "tc_ceb01"; ////Loai
    private final static String tc_ceb02 = "tc_ceb02"; ////STT
    private final static String tc_ceb03 = "tc_ceb03"; ////Ngày tiêu hao
    private final static String tc_ceb04 = "tc_ceb04"; ////Bảng số đo
    private final static String tc_ceb05 = "tc_ceb05"; ////Lượng sử dụng
    private final static String tc_ceb06 = "tc_ceb06"; ////Khoảng thời gian (AM/PM)
    private final static String tc_cebdate = "tc_cebdate"; ////Ngày nhập
    private final static String tc_cebuser = "tc_cebuser"; ////Nhân viên nhập

    String CREATE_TABLE_NAME_TC_CEB = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_CEB + " ("
            + tc_ceb01 + " TEXT," + tc_ceb02 + " TEXT," + tc_ceb03 + " TEXT ,"
            + tc_ceb04 + " TEXT," + tc_ceb05 + " TEXT," + tc_ceb06 + " TEXT,"
            + tc_cebdate + " TEXT, " + tc_cebuser + " TEXT  )";


    public KT07_DB(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
    }

    public void create_table() {
        try {
            db.execSQL(CREATE_TABLE_NAME_TC_CEB);
        } catch (Exception e) {

        }
    }

    public void drop_table() {
        try {
            final String DROP_TABLE_NAME_TC_CEB = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_CEB;
            db.execSQL(DROP_TABLE_NAME_TC_CEB);
            db.close();
        } catch (Exception e) {

        }
    }

    public void delete_table() {
        db.delete(TABLE_NAME_TC_CEB, null, null);
    }

    public Cursor get_menu_data(String ID) {
        Cursor c = db.rawQuery("SELECT cpf281 FROM cpf_file WHERE cpf01 = '" + ID + "' ", null);
        c.moveToFirst();
        String g_cpf281 = c.getString(0);
        if (g_cpf281 == null) {
            g_cpf281 = "DH"; // Gán giá trị mặc định nếu chuỗi là null
        }
        c.close();

        String selectQuery = null;
        if (g_cpf281.length() > 0) {
            selectQuery = " SELECT tc_cea01, tc_cea02, loai FROM ( " +
                    " SELECT DISTINCT tc_cea01, tc_cea02, " +
                    " (CASE WHEN UPPER(tc_cea02) LIKE '%ĐIỆN%' THEN 'dien' " +
                    " WHEN UPPER(tc_cea02) LIKE '%NƯỚC%' THEN 'nuoc' " +
                    " WHEN UPPER(tc_cea02) LIKE '%GAS%' THEN 'gas' END) AS loai " +
                    " FROM tc_cea_file WHERE tc_cea01 like '" + g_cpf281 + "%' " +
                    " ) ORDER BY (case when loai = 'nuoc' then 3 when loai = 'dien' then 2 when loai = 'gas' then 1 end)";
        }
        return db.rawQuery(selectQuery, null);
    }

    public Cursor get_menu_factory(String ID) {
        Cursor c = db.rawQuery("SELECT cpf281 FROM cpf_file WHERE cpf01 = '" + ID + "' ", null);
        c.moveToFirst();
        String g_cpf281 = c.getString(0);
        if (g_cpf281 == null) {
            g_cpf281 = "DH"; // Gán giá trị mặc định nếu chuỗi là null
        }
        c.close();

        String selectQuery = null;
        if (g_cpf281.length() > 0) {
            selectQuery = " select DISTINCT tc_cea09 from tc_cea_file where substr(tc_cea01,1,2) = '" + g_cpf281 + "' AND tc_cea09 != 'null' order by tc_cea09 ";
        }
        return db.rawQuery(selectQuery, null);
    }


    public Cursor getAll_tc_cea_data(String g_title, String g_tc_cebdate) {
        String selectQuery = " SELECT tc_cea03,tc_cea04,tc_cea05,tc_cea06,tc_cea08, CASE WHEN tc_cea09 = 'null' THEN '' ELSE tc_cea09 END AS tc_cea09, " +
                " COALESCE((SELECT tc_ceb04 FROM tc_ceb_file WHERE tc_ceb01 = '" + g_title + "' AND tc_ceb02 = TC_CEA04 AND tc_cebdate = '" + g_tc_cebdate + "' ),0) AS  tc_ceb04 " +
                " FROM tc_cea_file WHERE tc_cea01 = '" + g_title + "' " +
                " ORDER BY TC_CEA03 ";
        return db.rawQuery(selectQuery, null);
    }


}