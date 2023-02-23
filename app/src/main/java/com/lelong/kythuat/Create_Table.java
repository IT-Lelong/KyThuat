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
    String gem01 = "tc_fac001"; //Mã bộ phận
    String gem02 = "tc_fac002"; //Tên bộ phận


    String CREATE_TABLE_FAB = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAB + " ("
            + tc_fab001 + " TEXT," + tc_fab002 + " TEXT,"
            + tc_fab003 + " TEXT," + tc_fab004 + " TEXT)";

    String CREATE_TABLE_FAC = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAC + " ("
            + tc_fac001 + " TEXT," + tc_fac002 + " TEXT," + tc_fac003 + " TEXT,"
            + tc_fac004 + " TEXT," + tc_fac005 + " TEXT," + tc_fac006 + " TEXT,"
            + tc_fac007 + " TEXT," + tc_fac008 + " TEXT," + tc_fac011 + " TEXT )";

    String CREATE_TABLE_GEM = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_GEM + " ("
            + gem01 + " TEXT," + gem02 + " TEXT ) ";

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
        } catch (Exception e) {

        }
    }

    public void close() {
        try {
            String DROP_TABLE_TC_FAB = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_FAB;
            String DROP_TABLE_TC_FAC = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_FAC;
            String DROP_TABLE_GEM= "DROP TABLE IF EXISTS " + TABLE_NAME_GEM;
            db.execSQL(DROP_TABLE_TC_FAB);
            db.execSQL(DROP_TABLE_TC_FAC);
            db.execSQL(DROP_TABLE_GEM);
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

    public void delete_table() {
        db.delete(TABLE_NAME_TC_FAB, null  , null);
        db.delete(TABLE_NAME_TC_FAC, null  , null);
        db.delete(TABLE_NAME_GEM, null  , null);
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

    public Cursor getAll_gem(String g_gem01) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE_NAME_GEM + " WHERE gem01 like (% " + g_gem01 + "%) ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }
}
