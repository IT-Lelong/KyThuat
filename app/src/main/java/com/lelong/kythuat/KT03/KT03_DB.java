package com.lelong.kythuat.KT03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

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

    String KT03_03_TABLE = "KT03_03_file";
    String KT03_03_001 = "KT03_03_001"; //STT
    String KT03_03_002 = "KT03_03_002"; //Tên máy
    String KT03_03_003 = "KT03_03_003"; //Dãy đo
    String KT03_03_004 = "KT03_03_004"; //Nồng độ
    String KT03_03_005 = "KT03_03_005"; //Hạn sử dụng
    String KT03_03_006 = "KT03_03_006"; //Giá trị
    String KT03_03_007 = "KT03_03_007"; //Sai số
    String KT03_03_008 = "KT03_03_008"; //Ngày
    String KT03_03_009 = "KT03_03_009"; //Ca
    String KT03_03_010 = "KT03_03_010"; //Nhân viên

    String KT03_04_TABLE = "KT03_04_file";
    String KT03_04_001 = "KT03_04_001"; //Ngày
    String KT03_04_002 = "KT03_04_002"; //Ca
    String KT03_04_003 = "KT03_04_003"; //Nhân viên
    String KT03_04_004 = "KT03_04_004"; //Đánh giá

    String CREATE_TABLE_KT03 = "CREATE TABLE IF NOT EXISTS " + KT03_TABLE + " ("
            + KT03_01_001 + " TEXT," + KT03_01_002 + " TEXT,"
            + KT03_01_003 + " TEXT," + KT03_01_004 + " TEXT,"
            + KT03_01_005 + " TEXT," + KT03_01_006 + " TEXT)";

    String CREATE_TABLE_KT03_03 = "CREATE TABLE IF NOT EXISTS " + KT03_03_TABLE + " ("
            + KT03_03_001 + " TEXT," + KT03_03_002 + " TEXT," + KT03_03_003 + " TEXT," + KT03_03_004 + " TEXT," + KT03_03_005 + " TEXT,"
            + KT03_03_006 + " TEXT," + KT03_03_007 + " TEXT," + KT03_03_008 + " TEXT," + KT03_03_009 + " TEXT," + KT03_03_010 + " TEXT)";

    String CREATE_TABLE_KT03_04 = "CREATE TABLE IF NOT EXISTS " + KT03_04_TABLE + " ("
            + KT03_04_001 + " TEXT," + KT03_04_002 + " TEXT," + KT03_04_003 + " TEXT," + KT03_04_004 + " TEXT)";

    public KT03_DB(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
    }

    public void openTable() {
        try {
            db.execSQL(CREATE_TABLE_KT03);
            db.execSQL(CREATE_TABLE_KT03_03);
            db.execSQL(CREATE_TABLE_KT03_04);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            String DROP_TABLE_KT03 = "DROP TABLE IF EXISTS " + KT03_TABLE;
            String DROP_TABLE_KT03_03 = "DROP TABLE IF EXISTS " + KT03_03_TABLE;
            String DROP_TABLE_KT03_04 = "DROP TABLE IF EXISTS " + KT03_04_TABLE;
            db.execSQL(DROP_TABLE_KT03);
            db.execSQL(DROP_TABLE_KT03_03);
            db.execSQL(DROP_TABLE_KT03_04);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete_table(String qry_ngay, String qry_ca) {
        String whereClause_hm0102 = "KT03_01_004=? AND KT03_01_005=?";
        String whereClause_hm03 = "KT03_03_008=? AND KT03_03_009=?";
        String whereClause_hm04 = "KT03_04_001=? AND KT03_04_002=?";
        String[] strings = new String[]{qry_ngay, qry_ca};
        db.delete(KT03_TABLE, whereClause_hm0102, strings);
        db.delete(KT03_03_TABLE, whereClause_hm03, strings);
        db.delete(KT03_04_TABLE, whereClause_hm04, strings);
    }

    public void delete_all_table() {
        db.delete(KT03_TABLE, null, null);
        db.delete(KT03_03_TABLE, null, null);
        db.delete(KT03_04_TABLE, null, null);
    }

    public String ins_hm0102(String g_KT03_01_001, String g_KT03_01_002, String g_KT03_01_003, String g_KT03_01_004, String g_KT03_01_005, String g_KT03_01_006) {
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

    public String ins_hm03(String g_KT03_03_001, String g_KT03_03_002, String g_KT03_03_003, String g_KT03_03_004, String g_KT03_03_005,
                           String g_KT03_03_006, String g_KT03_03_007, String g_KT03_03_008, String g_KT03_03_009, String g_KT03_03_010) {
        try {
            ContentValues args = new ContentValues();
            args.put(KT03_03_001, g_KT03_03_001);
            args.put(KT03_03_002, g_KT03_03_002);
            args.put(KT03_03_003, g_KT03_03_003);
            args.put(KT03_03_004, g_KT03_03_004);
            args.put(KT03_03_005, g_KT03_03_005);
            args.put(KT03_03_006, g_KT03_03_006);
            args.put(KT03_03_007, g_KT03_03_007);
            args.put(KT03_03_008, g_KT03_03_008);
            args.put(KT03_03_009, g_KT03_03_009);
            args.put(KT03_03_010, g_KT03_03_010);
            db.insert(KT03_03_TABLE, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }


    public String ins_hm04(String g_KT03_04_001, String g_KT03_04_002, String g_KT03_04_003, String g_KT03_04_004) {
        try {
            ContentValues args = new ContentValues();
            args.put(KT03_04_001, g_KT03_04_001);
            args.put(KT03_04_002, g_KT03_04_002);
            args.put(KT03_04_003, g_KT03_04_003);
            args.put(KT03_04_004, g_KT03_04_004);
            db.insert(KT03_04_TABLE, null, args);
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

    public Cursor getAll_HM03(String g_date, String g_ca) {
        String selectQuery = "SELECT * FROM KT03_03_file " +
                " WHERE KT03_03_008 = '" + g_date + "' " +
                " AND KT03_03_009 = '" + g_ca + "' " +
                " ORDER BY KT03_03_001 ";
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getAll_HM04(String g_date, String g_ca) {
        String selectQuery = "SELECT * FROM KT03_04_file " +
                " WHERE KT03_04_001 = '" + g_date + "' " +
                " AND KT03_04_002 = '" + g_ca + "' ";

        return db.rawQuery(selectQuery, null);
    }

    public int query_HM03_max(String g_date, String g_ca) {
        int g_max = 0;
        String selection = "KT03_03_008 = ? AND KT03_03_009 = ?";
        String[] selectionArgs = {g_date, g_ca};
        String sortOrder = "KT03_03_001";
        Cursor res_max = db.query(KT03_03_TABLE, new String[]{"MAX(" + KT03_03_001 + ") AS MAX"},
                selection, selectionArgs, null, null, sortOrder);
        res_max.moveToFirst();
        if (res_max != null && res_max.getCount() > 0) {
            g_max = res_max.getInt(0) + 1;
        } else {
            g_max = 1;
        }
        return g_max;
    }

    public void upd_HM0102(String g_col, String g_maChiTiet, String g_noidung, String g_date, String g_ca, String g_id) {
        try {
            db.execSQL("UPDATE KT03_01_file SET " + g_col + "='" + g_noidung + "' ,KT03_01_006 = '" + g_id + "' " +
                    " WHERE KT03_01_001='" + g_maChiTiet + "' " +
                    " AND KT03_01_004 = '" + g_date + "' " +
                    " AND KT03_01_005 = '" + g_ca + "' ");
        } catch (Exception e) {
            String ex = e.getMessage().toString();
        }
    }

    public void upCheckAll(String g_date, String g_ca, String g_dk,String g_id, int i) {
        try {
            db.execSQL("UPDATE KT03_01_file SET KT03_01_002 ='" + i + "' ,KT03_01_006 = '" + g_id + "' " +
                    " WHERE substr(KT03_01_001,1,6) = 'KT03" + g_dk + "' " +
                    " AND KT03_01_004 = '" + g_date + "' " +
                    " AND KT03_01_005 = '" + g_ca + "' ");
        } catch (Exception e) {
            String ex = e.getMessage().toString();
        }
    }

    public void upd_HM03(String g_col, String g_key, String g_date, String g_ca, String g_noidung, String g_id) {
        try {
            db.execSQL("UPDATE KT03_03_file SET " + g_col + "='" + g_noidung + "' , KT03_03_010 = '" + g_id + "' " +
                    " WHERE KT03_03_001='" + g_key + "' " +
                    " AND KT03_03_008 = '" + g_date + "' " +
                    " AND KT03_03_009 = '" + g_ca + "' ");
        } catch (Exception e) {
            String ex = e.getMessage().toString();
        }
    }

    public void upd_HM04(String g_date, String g_ca, String g_id, String new_nd) {
        try {
            db.execSQL("UPDATE KT03_04_file SET KT03_04_004 = '" + new_nd + "' ,KT03_04_003 = '" + g_id + "' " +
                    " WHERE KT03_04_001='" + g_date + "' " +
                    " AND KT03_04_002 = '" + g_ca + "' ");
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

    public Cursor getDataUpLoad_hm0102() {
        String selectQuery = "SELECT * FROM KT03_01_file  ORDER BY KT03_01_004,KT03_01_005,KT03_01_001 ";
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getDataUpLoad_hm03() {
        String selectQuery = "SELECT * FROM KT03_03_file  ORDER BY KT03_03_008,KT03_03_009,KT03_03_001 ";
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getDataUpLoad_hm04() {
        String selectQuery = "SELECT * FROM KT03_04_file  ORDER BY KT03_04_001,KT03_04_002";
        return db.rawQuery(selectQuery, null);
    }

}