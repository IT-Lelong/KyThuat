package com.lelong.kythuat.KT03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class KT03_DB {

    private Context mCtx = null;
    String DATABASE_NAME = "KyThuatDB.db";
    public SQLiteDatabase db = null;

    String KT03_TABLE = "KT03_01_file";
    String KT03_01_001 = "KT03_01_001"; //Mã hạng mục
    String KT03_01_002 = "KT03_01_002"; //Đánh giá
    String KT03_01_003 = "KT03_01_003"; //Ghi chú
    String KT03_01_004 = "KT03_01_004"; //Ngày
    String KT03_01_005 = "KT03_01_005"; //Ca
    String KT03_01_006 = "KT03_01_006"; //Nhân viên

    String CREATE_TABLE_KT03 = "CREATE TABLE IF NOT EXISTS " + KT03_TABLE + " ("
            + KT03_01_001 + " TEXT," + KT03_01_002 + " TEXT,"
            + KT03_01_003 + " TEXT," + KT03_01_004 + " TEXT,"
            + KT03_01_005 + " TEXT," + KT03_01_006 + " TEXT)";

    public KT03_DB(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
    }

    public void openTable() {
        try {
            db.execSQL(CREATE_TABLE_KT03);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            String DROP_TABLE_KT03 = "DROP TABLE IF EXISTS " + KT03_TABLE;
            db.execSQL(DROP_TABLE_KT03);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete_table() {
        db.delete(KT03_TABLE, null, null);
    }

    public String append(String g_KT03_01_001, String g_KT03_01_002, String g_KT03_01_003, String g_KT03_01_004, String g_KT03_01_005, String g_KT03_01_006) {
        try {
            ContentValues args = new ContentValues();
            args.put(KT03_01_001, g_KT03_01_001);
            args.put(KT03_01_002, g_KT03_01_002);
            args.put(KT03_01_003, g_KT03_01_003);
            args.put(KT03_01_004, g_KT03_01_004);
            args.put(KT03_01_005, g_KT03_01_005);
            args.put(KT03_01_006, g_KT03_01_006);
            db.insert(KT03_TABLE, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    public Cursor getAll_HM0102(String g_date, String g_ca, String s) {
        String selectQuery = "SELECT KT03_01_001,KT03_01_002,KT03_01_003,tc_fac005,tc_fac006,tc_fac002,tc_fac001,tc_fac003 " +
                " FROM KT03_01_file,tc_fac_file " +
                " WHERE tc_fac004 = KT03_01_001 " +
                " AND KT03_01_004 = '" + g_date + "' " +
                " AND KT03_01_005 = '" + g_ca + "' " +
                " AND tc_fac001 = '" + s + "' " +
                " ORDER BY KT03_01_001 ";
        return db.rawQuery(selectQuery, null);
    }


    public void upd_HM0102(String g_col, String g_maChiTiet, String g_noidung, String g_date, String g_ca) {
        try {
            db.execSQL("UPDATE KT03_01_file SET " + g_col + "='" + g_noidung + "' " +
                    " WHERE KT03_01_001='" + g_maChiTiet + "' " +
                    " AND KT03_01_004 = '" + g_date + "' " +
                    " AND KT03_01_005 = '" + g_ca + "' ");
        } catch (Exception e) {
            String ex = e.getMessage().toString();
        }

    }

    public Cursor getAll_lvQuery() {
        String selectQuery = " SELECT COUNT(*) AS _id ,KT03_01_004, KT03_01_005 FROM KT03_01_file" +
                " GROUP BY KT03_01_004, KT03_01_005" +
                " ORDER BY KT03_01_004, KT03_01_005";
        return db.rawQuery(selectQuery, null);

        // Các cột cần lấy trong bảng
        /*String[] projection = {"_id","distinct KT03_01_004", "KT03_01_005"};
        // Các cột dùng để ORDER BY
        String sortOrder = "KT03_01_004, KT03_01_005";
        return db.query(KT03_TABLE, projection, null, null, null, null, sortOrder, null);*/
    }

    public Cursor getDataUpLoad_hm01() {
        String selectQuery = "SELECT * FROM KT03_01_file  ORDER BY KT03_01_004,KT03_01_005,KT03_01_001 ";
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getDataUpLoad_hm02() {
        return null;
    }

    public Cursor getDataUpLoad_hm03() {
        return null;
    }

    public Cursor getDataUpLoad_hm04() {
        return null;
    }

}