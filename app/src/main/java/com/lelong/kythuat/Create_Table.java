package com.lelong.kythuat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Create_Table {

    private Context mCtx = null;
    String DATABASE_NAME = "KyThuatDB.db";
    public SQLiteDatabase db = null;
    private final static String TABLE_NAME_TC_FAC_KT02 = "tc_fac_table_kt02";

    String TABLE_NAME_TC_FAB = "tc_fab_file";
    String tc_fab001 = "tc_fab001"; //Mã hạng mục
    String tc_fab002 = "tc_fab002"; //Mã báo biểu
    String tc_fab003 = "tc_fab003"; //Mã hạng mục chi tiết
    String tc_fab004 = "tc_fab004"; //Mã tổng

    String TABLE_NAME_TC_FAC = "tc_fac_file";
    String tc_fac001 = "tc_fac001"; //Mã hạng mục
    String tc_fac002 = "tc_fac002"; //Mã báo biểu
    String tc_fac003 = "tc_fac003"; //Mã hạng mục chi tiết
    String tc_fac004 = "tc_fac004"; //Mã tổng
    String tc_fac005 = "tc_fac005"; //tên tiếng hoa
    String tc_fac006 = "tc_fac006"; //tên tiếng việt
    String tc_fac007 = "tc_fac007"; //Điểm số
    String tc_fac008 = "tc_fac008"; //Hãng sản xuất
    String tc_fac011 = "tc_fac011"; //Dãy đo thiết bị

    String TABLE_NAME_GEM = "gem_file";
    String gem01 = "gem01"; //Mã bộ phận
    String gem02 = "gem02"; //Tên bộ phận

    //KT02(S)
    String TABLE_NAME_FIA = "fia_file";
    String fia01 = "fia01"; //Mã số thiết bị
    String ta_fia02_1 = "ta_fia02_1"; //Tên thiết bị
    String fiaud03 = "fiaud03"; //Số máy
    String fia15 = "fia15"; //Bộ phận
    String fka02 = "fka02"; //Tên bộ phận
    //KT02(E)
    String CREATE_TABLE_FAB = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAB + " ("
            + tc_fab001 + " TEXT," + tc_fab002 + " TEXT,"
            + tc_fab003 + " TEXT," + tc_fab004 + " TEXT)";

    String CREATE_TABLE_FAC = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAC + " ("
            + tc_fac001 + " TEXT," + tc_fac002 + " TEXT," + tc_fac003 + " TEXT,"
            + tc_fac004 + " TEXT," + tc_fac005 + " TEXT," + tc_fac006 + " TEXT,"
            + tc_fac007 + " TEXT," + tc_fac008 + " TEXT," + tc_fac011 + " TEXT )";

    String CREATE_TABLE_GEM = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_GEM + " ("
            + gem01 + " TEXT," + gem02 + " TEXT ) ";

    //KT02(S)
    String CREATE_TABLE_FIA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FIA + " ("
            + fia01 + " TEXT," + ta_fia02_1 + " TEXT," + fiaud03 + " TEXT,"
            + fia15 + " TEXT," + fka02 + " TEXT )";
    //KT02(E)
    public Create_Table(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
        //nối DATABASE(S)
        /*String dbPath = mCtx.getDatabasePath("KyThuatDB").getPath();
        // dbPath = /data/user/0/com.lelong.kythuat/databases/KyThuatDB.db
        String attachQuery = "ATTACH DATABASE '" + dbPath +"' AS KyThuatDB";
        db.execSQL(attachQuery);*/
        //nối DATABASE(E)

    }

    public void openTable() {
        try {
            db.execSQL(CREATE_TABLE_FAB);
            db.execSQL(CREATE_TABLE_FAC);
            db.execSQL(CREATE_TABLE_GEM);
            db.execSQL(CREATE_TABLE_FIA);
        } catch (Exception e) {

        }
    }

    public void close() {
        try {
            String DROP_TABLE_TC_FAB = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_FAB;
            String DROP_TABLE_TC_FAC = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_FAC;
            String DROP_TABLE_GEM= "DROP TABLE IF EXISTS " + TABLE_NAME_GEM;
            String DROP_TABLE_TC_FIA = "DROP TABLE IF EXISTS " + TABLE_NAME_FIA;
            db.execSQL(DROP_TABLE_TC_FAB);
            db.execSQL(DROP_TABLE_TC_FAC);
            db.execSQL(DROP_TABLE_GEM);
            db.execSQL(DROP_TABLE_TC_FIA);
            db.close();
        } catch (Exception e) {

        }
    }

    public String append(String g_tc_fab001, String g_tc_fab002, String g_tc_fab003, String g_tc_fab004) {
        try {
            ContentValues args = new ContentValues();
            args.put(tc_fab001, g_tc_fab001);
            args.put(tc_fab002, g_tc_fab002);
            args.put(tc_fab003, g_tc_fab003);
            args.put(tc_fab004, g_tc_fab004);
            db.insert(TABLE_NAME_TC_FAB, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    public String append(String g_tc_fac001, String g_tc_fac002, String g_tc_fac003,
                       String g_tc_fac004, String g_tc_fac005, String g_tc_fac006,
                       String g_tc_fac007, String g_tc_fac008, String g_tc_fac011) {
        try {
            ContentValues args = new ContentValues();
            args.put(tc_fac001, g_tc_fac001);
            args.put(tc_fac002, g_tc_fac002);
            args.put(tc_fac003, g_tc_fac003);
            args.put(tc_fac004, g_tc_fac004);
            args.put(tc_fac005, g_tc_fac005);
            args.put(tc_fac006, g_tc_fac006);
            args.put(tc_fac007, g_tc_fac007);
            args.put(tc_fac008, g_tc_fac008);
            args.put(tc_fac011, g_tc_fac011);
            db.insert(TABLE_NAME_TC_FAC, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }

    }

    public String append(String g_gem01, String g_gem02) {
        try {
            ContentValues args = new ContentValues();
            args.put(gem01, g_gem01);
            args.put(gem02, g_gem02);
            db.insert(TABLE_NAME_GEM, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }

    }

    /*KT02(S)*/
    public String append(String g_fia01,String g_ta_fia02_1,String g_fiaud03,String g_fia15,String g_fka02) {
        try {
            ContentValues args = new ContentValues();
            args.put(fia01, g_fia01);
            args.put(ta_fia02_1, g_ta_fia02_1);
            args.put(fiaud03, g_fiaud03);
            args.put(fia15, g_fia15);
            args.put(fka02, g_fka02);
            db.insert(TABLE_NAME_FIA, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }

    }
    /*KT02(E)*/

    public void delete_table() {
        db.delete(TABLE_NAME_TC_FAB, null  , null);
        db.delete(TABLE_NAME_TC_FAC, null  , null);
        db.delete(TABLE_NAME_GEM, null  , null);
        db.delete(TABLE_NAME_FIA, null, null);
    }

    public Cursor getAll_tc_fab(String qry_cond) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE_NAME_TC_FAB + " WHERE tc_fab001 = '" + qry_cond + "' ORDER BY tc_fab001 ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_tc_fac(String g_kind, String g_kind1) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE_NAME_TC_FAC + " WHERE tc_fac002='" + g_kind + "' AND tc_fac001='" + g_kind1+"'";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    public
    boolean getAll_gem(String g_gem01) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            Cursor mCursor=db.query(TABLE_NAME_GEM,new String[]{gem01},gem01+"=?",new String[]{g_gem01},null,null,null,null);
            if(mCursor.getCount()>0){
                return true;
            }else{
                return false;
            }

            //String selectQuery = "SELECT count(*) FROM " + TABLE_NAME_GEM + " WHERE gem01 like (% " + g_gem01 + "%) ";
            // return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return false;
        }
    }

    //KT02(S)

    /*KT02_chitiet*/

    public Cursor getAll_tc_fac_02(String g_kind, String g_kind1, String bophan,String somay,String ngay ) {
        Cursor a;
        try {
            int count = 0;
            ContentValues argsA = new ContentValues();
            Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_TC_FAC + ","+TABLE_NAME_TC_FAC_KT02+" " +
                    " WHERE tc_fac_table_kt02.tc_fac004=tc_fac_file.tc_fac004 and  tc_fac002='" + g_kind + "' " +
                    "AND tc_fac001='" + g_kind1+"' AND user='" + bophan+"' AND somay='" + somay+"' AND ngay='" + ngay+"'", null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count > 0) {
                String selectQuery = "SELECT  tc_fac_file.tc_fac003 as tc_fac003,tc_fac_file.tc_fac004 as tc_fac004,tc_fac_file.tc_fac006 as tc_fac006 ,tc_fac_table_kt02.checkbox1 as checkbox1,tc_fac_table_kt02.checkbox2 as checkbox2," +
                        "tc_fac_table_kt02.checkbox3 as checkbox3,tc_fac_table_kt02.checkbox4 as checkbox4,tc_fac_table_kt02.checkbox5 as checkbox5,tc_fac_table_kt02.checkbox6 as checkbox6," +
                        "tc_fac_table_kt02.tc_fac009 as tc_fac009 ,tc_fac_table_kt02.tc_fac005 as tc_fac005 " +
                        " FROM " + TABLE_NAME_TC_FAC + ","+TABLE_NAME_TC_FAC_KT02+" " +
                        " WHERE tc_fac_table_kt02.tc_fac004=tc_fac_file.tc_fac004 and  tc_fac002='" + g_kind + "' " +
                        " AND tc_fac001='" + g_kind1+"' AND user='" + bophan+"' AND somay='" + somay+"' " +
                        " AND ngay='" + ngay+"'";
                return db.rawQuery(selectQuery, null);
            }else {
                String selectQuery1 = "SELECT tc_fac003,tc_fac006,tc_fac004,'false' as checkbox1,'false' as checkbox2,'true' as checkbox3,'false' as checkbox4,'false' as checkbox5,'false' as checkbox6,'' as tc_fac009 ,tc_fac005 FROM " + TABLE_NAME_TC_FAC + " WHERE tc_fac002='" + g_kind + "' AND tc_fac001='" + g_kind1+"'";
                return db.rawQuery(selectQuery1, null);
            }
            //SQLiteDatabase db = this.getWritableDatabase();
            //String selectQuery = "SELECT tc_fac003,tc_fac006,tc_fac004 FROM " + TABLE_NAME_TC_FAC + " WHERE tc_fac002='" + g_kind + "' AND tc_fac001='" + g_kind1+"'";

        } catch (Exception e) {
            return null;
        }
    }

    /*KT02_loggin_somay*/

    public Cursor getAll_fia_02() {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT distinct fiaud03 FROM fia_file WHERE ta_fia02_1='Xe nâng dầu' ORDER BY fiaud03";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    /*KT02_loggin_bophan*/

    public Cursor getAll_fia_02_bp(String g_fiaud03) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT distinct fia15,fka02 FROM fia_file WHERE fiaud03='" + g_fiaud03+"' AND ta_fia02_1='Xe nâng dầu' ORDER BY fia15";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }
    /*Lấy dl bảng ảo đổ lên table tc_fad_file*/
    public Cursor getAll_instc_fad(String g_user, String g_somay,String g_ngay) {
        try {
            String selectQuery = "SELECT  tc_fac_file.tc_fac001 as tc_fac001,tc_fac_file.tc_fac002 as tc_fac002,tc_fac_file.tc_fac003 as tc_fac003,tc_fac_file.tc_fac004 as tc_fac004,tc_fac_table_kt02.tc_fac005 as tc_fac005 ,tc_fac_file.tc_fac006 as tc_fac006 ,tc_fac_table_kt02.checkbox1 as checkbox1,tc_fac_table_kt02.checkbox2 as checkbox2," +
                    "tc_fac_table_kt02.checkbox3 as checkbox3,tc_fac_table_kt02.checkbox4 as checkbox4,tc_fac_table_kt02.checkbox5 as checkbox5,tc_fac_table_kt02.checkbox6 as checkbox6,tc_fac_table_kt02.tc_fac009 as tc_fac009," +
                    "tc_fac_table_kt02.user as user,tc_fac_table_kt02.ngay as ngay,tc_fac_table_kt02.somay as somay " +
                    " FROM " + TABLE_NAME_TC_FAC + ","+TABLE_NAME_TC_FAC_KT02+" " +
                    " WHERE tc_fac_table_kt02.tc_fac004=tc_fac_file.tc_fac004 AND user='" + g_user+"' AND somay='" + g_somay+"' " +
                    " AND ngay='" + g_ngay+"'";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    //insert
    public boolean ins_fac_02(String g_kind,String xuser,String xngay,String xsomay){
        //db=mCtx.openOrCreateDatabase(DATABASE_NAME,0,null);
        //將上傳資料彙整至json_table
        int count = 0;
        //ContentValues argsA = new ContentValues();
        Cursor mCount = db.rawQuery("SELECT count(*) FROM "+TABLE_NAME_TC_FAC_KT02+" " +
                " WHERE user='" + xuser +"' AND somay='" + xsomay +"' AND ngay='" + xngay +"' ", null);
        mCount.moveToFirst();
        count = mCount.getInt(0);
        if (count == 0) {
            ContentValues argsC = new ContentValues();
            Cursor c=db.rawQuery("SELECT tc_fac004,'' as tc_fac009 ,'false' as checkbox1,'false' as checkbox2,'true' as checkbox3," +
                    " 'false' as checkbox4,'false' as checkbox5,'false' as checkbox6,tc_fac003,tc_fac005,tc_fac006 FROM " + TABLE_NAME_TC_FAC +
                    " WHERE tc_fac002='" + g_kind + "'",null);
            if(c.getCount()>0){
                c.moveToFirst();
                do{
                    argsC.put("tc_fac004", c.getString(0));
                    argsC.put("tc_fac009", c.getString(1));
                    argsC.put("checkbox1", c.getString(2));
                    argsC.put("checkbox2", c.getString(3));
                    argsC.put("checkbox3", c.getString(4));
                    argsC.put("checkbox4", c.getString(5));
                    argsC.put("checkbox5", c.getString(6));
                    argsC.put("checkbox6", c.getString(7));
                    argsC.put("user", xuser);
                    argsC.put("ngay", xngay);
                    argsC.put("somay", xsomay);
                    argsC.put("tc_fac003", c.getString(8));
                    argsC.put("tc_fac005", c.getString(9));
                    argsC.put("tc_fac006", c.getString(10));
                    db.insert(TABLE_NAME_TC_FAC_KT02, null, argsC);
                }while (c.moveToNext());
            }else {
                return false;
            }
            return true;
        }else {
            return false;
        }
    }

    public Cursor getAll_fiaud03() {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "select count(*) AS _id,fiaud03,fia15,fka02 from fia_file " +
                    " where fiaud03 not in (select distinct somay from tc_fac_table_kt02) AND ta_fia02_1='Xe nâng dầu' group by fiaud03,fia15,fka02 order by fiaud03";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }
   //KT02(E)
}
