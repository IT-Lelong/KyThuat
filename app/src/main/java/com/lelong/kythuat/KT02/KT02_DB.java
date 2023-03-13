package com.lelong.kythuat.KT02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class KT02_DB {
    private Context mCtx = null;
    //String DATABASE_NAME = "KyThuatDB02.db";
    String DATABASE_NAME = "KyThuatDB.db";
    public static SQLiteDatabase db = null;
    public SQLiteDatabase db2 = null;

    private final static String TABLE_NAME_TC_FAC_KT02 = "tc_fac_table_kt02";
    private final static String checkbox1 = "checkbox1"; //Loại xuất sắc
    private final static String checkbox2 = "checkbox2"; //Loại tốt
    private final static String checkbox3 = "checkbox3"; //Loại được
    private final static String checkbox4 = "checkbox4"; //Loại kém
    private final static String checkbox5 = "checkbox5"; //Loại rất kém
    private final static String checkbox6 = "checkbox6"; //Loại đặc biệt kém
    private final static String tc_fac004 = "tc_fac004"; //Mã tổng
    private final static String tc_fac009 = "tc_fac009"; //Ghi chú
    private final static String user = "user"; //Người lập biểu
    private final static String ngay = "ngay"; //Ngày
    private final static String somay = "somay"; //Số máy

    private final static String tc_fac003 = "tc_fac003"; //STT

    private final static String tc_fac005 = "tc_fac005"; //Tên hạng mục (tiếng hoa)
    private final static String tc_fac006 = "tc_fac006"; //Tên hạng mục (tiếng việt)

    //Bảng ảo lưu biểu KT02 (S)
    String CREATE_TABLE_NAME_TC_FAC_KT02 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAC_KT02 + " ("
            + tc_fac004 + " TEXT," + tc_fac009 + " TEXT,"
            + checkbox1 + " TEXT ," + checkbox2 + " TEXT," + checkbox3 + " TEXT," + checkbox4 + " TEXT,"
            + checkbox5 + " TEXT," + checkbox6 + " TEXT," + user + " TEXT," + ngay + " TEXT," + somay + " TEXT,"
            + tc_fac003 + " TEXT," + tc_fac005 + " TEXT," + tc_fac006 + " TEXT)";
    //Bảng ảo lưu biểu KT02 (E)








    public KT02_DB(Context ctx) {
        this.mCtx = ctx;
    }




    //public Create_Table(Fragmen_KT01 ctx) {
    // this.mCtx = ctx;
    //}

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);

        //db2 = mCtx.openOrCreateDatabase("KyThuatDB.db", 0, null);

        //String dbPath = mCtx.getDatabasePath("KyThuatDB.db").getPath();
        // dbPath = /data/user/0/com.lelong.kythuat/databases/KyThuatDB.db
        //String attachQuery = "ATTACH DATABASE '" + dbPath +"' AS KyThuatDB";
        //db.execSQL(attachQuery);
        try {
            //db.execSQL(CREATE_TABLE_FAB);
            //db.execSQL(CREATE_TABLE_FAC);
            //db.execSQL(attachQuery);
            db.execSQL(CREATE_TABLE_NAME_TC_FAC_KT02);
        } catch (Exception e) {

        }


    }

    public void close() {
        try {
            final String DROP_TABLE_NAME_TC_FAC_KT02 = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_FAC_KT02;
            db.execSQL(DROP_TABLE_NAME_TC_FAC_KT02);
            db.close();
        } catch (Exception e) {

        }
    }


    public void delete_table() {
        db.delete(TABLE_NAME_TC_FAC_KT02, null, null);
    }
    public void del_db(String prog) {
        try {
            final String DEL_TABLE1 = "DELETE FROM " + TABLE_NAME_TC_FAC_KT02 + " WHERE qraprog = '" + prog + "'";
            db.execSQL(DEL_TABLE1);
        } catch (Exception e) {

        }
    }

    /*update tb tc_fac*/
    public long updatekt02(String xtc_fac004, String xtc_fac009, String xcheckbox1, String xcheckbox2, String xcheckbox3,
                           String xcheckbox4, String xcheckbox5, String xcheckbox6, String xuser, String xngay, String xsomay) {
        try {
            int count = 0;
            ContentValues argsA = new ContentValues();
            Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_TC_FAC_KT02 + " WHERE tc_fac004='" + xtc_fac004 + "'", null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count > 0) {
                db.execSQL("UPDATE " + TABLE_NAME_TC_FAC_KT02 + " SET checkbox1='" + xcheckbox1 + "'," +
                        "checkbox2='" + xcheckbox2 + "'," +
                        "checkbox3='" + xcheckbox3 + "'," +
                        "checkbox4='" + xcheckbox4 + "'," +
                        "checkbox5='" + xcheckbox5 + "'," +
                        "checkbox6='" + xcheckbox6 + "'," +
                        "tc_fac009='" + xtc_fac009 + "'," +
                        "user='" + xuser + "'," +
                        "ngay='" + xngay + "'," +
                        "somay='" + xsomay + "' " +
                        " WHERE tc_fac004='" + xtc_fac004 + "' ");
                return 1;
            } else {
                return 0;
            }

        } catch (Exception e) {
            return 0;
        }

    }

    /*insert */
    public long ins_tc_fac_table_kt02(
            String xtc_fac004, String xtc_fac009, Boolean xcheckbox1, Boolean xcheckbox2, Boolean xcheckbox3,
            Boolean xcheckbox4, Boolean xcheckbox5, Boolean xcheckbox6, String xuser, String xngay, String xsomay, String xtc_fac003, String xtc_fac005, String xtc_fac006) {
        try {
            int count = 0;
            ContentValues argsA = new ContentValues();
            Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_TC_FAC_KT02 + " WHERE tc_fac004='" + xtc_fac004 + "' AND user='" + xuser + "' AND somay='" + xsomay  + "' AND ngay='" + xngay + "'", null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count <= 0) {
                argsA.put(tc_fac004, xtc_fac004);
                argsA.put(tc_fac009, xtc_fac009);
                argsA.put(String.valueOf(checkbox1), xcheckbox1);
                argsA.put(checkbox2, xcheckbox2);
                argsA.put(checkbox3, xcheckbox3);
                argsA.put(checkbox4, xcheckbox4);
                argsA.put(checkbox5, xcheckbox5);
                argsA.put(checkbox6, xcheckbox6);
                argsA.put(user, xuser);
                argsA.put(ngay, xngay);
                argsA.put(somay, xsomay);
                argsA.put(tc_fac003, xtc_fac003);
                argsA.put(tc_fac005, xtc_fac005);
                argsA.put(tc_fac006, xtc_fac006);
                db.insert(TABLE_NAME_TC_FAC_KT02, null, argsA);
                return 1;
            }else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
/*update tung cot*/
    public static long updatekt_col(String g_col, String ndung, String xtc_fac004,String xuser,String xsomay,String xngay) {
        try {
            db.execSQL("UPDATE " + TABLE_NAME_TC_FAC_KT02 + " SET "+g_col+"='" + ndung + "'" +
                    " WHERE tc_fac004='" + xtc_fac004 + "' AND user='" + xuser + "' AND somay='" + xsomay  + "' AND ngay='" + xngay + "' ");
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    /*Lấy dl bảng ảo đổ lên table tc_fad_file*/
    /*public Cursor getAll_qra_qrb(String g_user, String g_date, String g_somay,String g_ngay) {
        try {
            String selectQuery = "SELECT  tc_fac_file.tc_fac003 as tc_fac003,tc_fac_file.tc_fac004 as tc_fac004,tc_fac_file.tc_fac006 as tc_fac006 ,tc_fac_table_kt02.checkbox1 as checkbox1,tc_fac_table_kt02.checkbox2 as checkbox2," +
                    "tc_fac_table_kt02.checkbox3 as checkbox3,tc_fac_table_kt02.checkbox4 as checkbox4,tc_fac_table_kt02.checkbox5 as checkbox5,tc_fac_table_kt02.checkbox6 as checkbox6," +
                    "tc_fac_table_kt02.tc_fac009 as tc_fac009 ,tc_fac_table_kt02.tc_fac005 as tc_fac005 " +
                    " FROM " + TABLE_NAME_TC_FAC + ","+TABLE_NAME_TC_FAC_KT02+" " +
                    " WHERE tc_fac_table_kt02.tc_fac004=tc_fac_file.tc_fac004 and  tc_fac002='" + g_kind + "' " +
                    " AND tc_fac001='" + g_kind1+"' AND user='" + bophan+"' AND somay='" + somay+"' " +
                    " AND ngay='" + ngay+"'";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }*/

    public Cursor getAll_lvQuery() {
        String selectQuery = " SELECT COUNT(*) AS _id ,ngay,somay,user FROM " + TABLE_NAME_TC_FAC_KT02 +
                " GROUP BY ngay,somay,user" +
                " ORDER BY ngay,somay,user";
        return db.rawQuery(selectQuery, null);

        // Các cột cần lấy trong bảng
        /*String[] projection = {"_id","distinct KT03_01_004", "KT03_01_005"};
        // Các cột dùng để ORDER BY
        String sortOrder = "KT03_01_004, KT03_01_005";
        return db.query(KT03_TABLE, projection, null, null, null, null, sortOrder, null);*/
    }
    public void delete_table(String qry_ngay, String qry_somay,String qry_user) {
        String where_loggin = "ngay=? AND somay=? AND user=?";
        String[] strings = new String[]{qry_ngay, qry_somay, qry_user};
        db.delete(TABLE_NAME_TC_FAC_KT02, where_loggin, strings);
    }
}
