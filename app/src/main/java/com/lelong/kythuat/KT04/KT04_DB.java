package com.lelong.kythuat.KT04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class KT04_DB {

    private Context mCtx = null;
    String DATABASE_NAME = "KyThuatDB.db";
    public SQLiteDatabase db = null;

    String KT04_TABLE = "KT04_01_file";
    String KT04_01_001 = "KT04_01_001"; //Mã hạng mục
    String KT04_01_002 = "KT04_01_002"; //Đánh giá
    String KT04_01_003 = "KT04_01_003"; //Ghi chú
    String KT04_01_004 = "KT04_01_004"; //Ngày
    String KT04_01_005 = "KT04_01_005"; //Ca
    String KT04_01_006 = "KT04_01_006"; //Nhân viên

    String KT04_03_TABLE = "KT04_03_file";
    String KT04_03_001 = "KT04_03_001"; //Mã hạng mục
    String KT04_03_002 = "KT04_03_002"; //Nồng độ
    String KT04_03_003 = "KT04_03_003"; //Hạn sử dụng
    String KT04_03_004 = "KT04_03_004"; //Giá trị
    String KT04_03_005 = "KT04_03_005"; //Sai số
    String KT04_03_006 = "KT04_03_006"; //Ngày
    String KT04_03_007 = "KT04_03_007"; //Ca
    String KT04_03_008 = "KT04_03_008"; //Nhân viên

    String KT04_04_TABLE = "KT04_04_file";
    String KT04_04_001 = "KT04_04_001"; //Đánh giá
    String KT04_04_002 = "KT04_04_002"; //Ngày
    String KT04_04_003 = "KT04_04_003"; //Ca
    String KT04_04_004 = "KT04_04_004"; //Nhân viên

    String CREATE_TABLE_KT04 = "CREATE TABLE IF NOT EXISTS " + KT04_TABLE + " ("
            + KT04_01_001 + " TEXT," + KT04_01_002 + " TEXT,"
            + KT04_01_003 + " TEXT," + KT04_01_004 + " TEXT,"
            + KT04_01_005 + " TEXT," + KT04_01_006 + " TEXT)";

    String CREATE_TABLE_KT04_03 = "CREATE TABLE IF NOT EXISTS " + KT04_03_TABLE + " ("
            + KT04_03_001 + " TEXT," + KT04_03_002 + " TEXT," + KT04_03_003 + " TEXT," + KT04_03_004 + " TEXT," + KT04_03_005 + " TEXT,"
            + KT04_03_006 + " TEXT," + KT04_03_007 + " TEXT," + KT04_03_008 + " TEXT)";

    String CREATE_TABLE_KT04_04 = "CREATE TABLE IF NOT EXISTS " + KT04_04_TABLE + " ("
            + KT04_04_001 + " TEXT," + KT04_04_002 + " TEXT," + KT04_04_003 + " TEXT," + KT04_04_004 + " TEXT)";

    public KT04_DB(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
    }

    public void openTable() {
        try {
            db.execSQL(CREATE_TABLE_KT04);
            db.execSQL(CREATE_TABLE_KT04_03);
            db.execSQL(CREATE_TABLE_KT04_04);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            String DROP_TABLE_KT04 = "DROP TABLE IF EXISTS " + KT04_TABLE;
            String DROP_TABLE_KT04_03 = "DROP TABLE IF EXISTS " + KT04_03_TABLE;
            String DROP_TABLE_KT04_04 = "DROP TABLE IF EXISTS " + KT04_04_TABLE;
            db.execSQL(DROP_TABLE_KT04);
            db.execSQL(DROP_TABLE_KT04_03);
            db.execSQL(DROP_TABLE_KT04_04);
            db.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete_table(String qry_ngay, String qry_ca) {
        //db.delete(KT04_TABLE, null, null);
        //db.delete(KT04_03_TABLE, null, null);
        String whereClause_hm01 = "KT04_01_004=? AND KT04_01_005=?";
        String whereClause_hm02 = "KT04_01_004=? AND KT04_01_005=?";
        String whereClause_hm03 = "KT04_03_006=? AND KT04_03_007=?";
        String[] strings = new String[]{qry_ngay, qry_ca};
        db.delete(KT04_TABLE, whereClause_hm01, strings);
        db.delete(KT04_TABLE, whereClause_hm02, strings);
        db.delete(KT04_03_TABLE, whereClause_hm03, strings);
    }
    public void delete_all_table() {
        db.delete(KT04_TABLE, null, null);
        db.delete(KT04_03_TABLE, null, null);
        db.delete(KT04_04_TABLE, null, null);
    }

    public String append(String g_KT04_01_001, String g_KT04_01_002, String g_KT04_01_003, String g_KT04_01_004, String g_KT04_01_005, String g_KT04_01_006) {
        try {
            ContentValues args = new ContentValues();
            args.put(KT04_01_001, g_KT04_01_001);
            args.put(KT04_01_002, g_KT04_01_002);
            args.put(KT04_01_003, g_KT04_01_003);
            args.put(KT04_01_004, g_KT04_01_004);
            args.put(KT04_01_005, g_KT04_01_005);
            args.put(KT04_01_006, g_KT04_01_006);
            db.insert(KT04_TABLE, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public String append3(String g_KT04_03_001, String g_KT04_03_002, String g_KT04_03_003, String g_KT04_03_004, String g_KT04_03_005,String g_KT04_03_006,String g_KT04_03_007,String g_KT04_03_008) {
        try {
            ContentValues args = new ContentValues();
            args.put(KT04_03_001, g_KT04_03_001);
            args.put(KT04_03_002, g_KT04_03_002);
            args.put(KT04_03_003, g_KT04_03_003);
            args.put(KT04_03_004, g_KT04_03_004);
            args.put(KT04_03_005, g_KT04_03_005);
            args.put(KT04_03_006, g_KT04_03_006);
            args.put(KT04_03_007, g_KT04_03_007);
            args.put(KT04_03_008, g_KT04_03_008);
            db.insert(KT04_03_TABLE, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    public String append4(String g_KT04_04_001, String g_KT04_04_002, String g_KT04_04_003, String g_KT04_04_004) {
        try {
            ContentValues args = new ContentValues();
            args.put(KT04_04_001, g_KT04_04_001);
            args.put(KT04_04_002, g_KT04_04_002);
            args.put(KT04_04_003, g_KT04_04_003);
            args.put(KT04_04_004, g_KT04_04_004);

            db.insert(KT04_04_TABLE, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

   /* public String ins_hm03() {
        try {
            ContentValues args = new ContentValues();
            args.put(KT03_03_001, g_KT03_01_001);
            args.put(KT03_03_002, g_KT03_01_002);
            args.put(KT03_03_003, g_KT03_01_003);
            args.put(KT03_03_004, g_KT03_01_004);
            args.put(KT03_03_005, g_KT03_01_005);
            args.put(KT03_03_006, g_KT03_01_006);
            args.put(KT03_03_007, g_KT03_01_002);
            args.put(KT03_03_008, g_KT03_01_003);
            args.put(KT03_03_009, g_KT03_01_004);
            args.put(KT03_03_010, g_KT03_01_005);
            db.insert(KT03_TABLE, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }*/

    public Cursor getAll_HM0102(String g_date, String g_ca, String s) {
        String selectQuery = "SELECT KT04_01_001,KT04_01_002,KT04_01_003,tc_fac005,tc_fac006,tc_fac002,tc_fac001,tc_fac003 " +
                " FROM KT04_01_file,tc_fac_file " +
                " WHERE tc_fac004 = KT04_01_001 " +
                " AND KT04_01_004 = '" + g_date + "' " +
                " AND KT04_01_005 = '" + g_ca + "' " +
                " AND tc_fac001 = '" + s + "' " +
                " ORDER BY KT04_01_001 ";
        return db.rawQuery(selectQuery, null);
    }
    public Cursor getAll_HM02(String g_date, String g_ca, String s) {
        String selectQuery = "SELECT KT04_01_001,KT04_01_002,KT04_01_003,tc_fac005,tc_fac006,tc_fac008,tc_fac002,tc_fac001,tc_fac003 " +
                " FROM KT04_01_file,tc_fac_file " +
                " WHERE tc_fac004 = KT04_01_001 " +
                " AND KT04_01_004 = '" + g_date + "' " +
                " AND KT04_01_005 = '" + g_ca + "' " +
                " AND tc_fac001 = '" + s + "' " +
                " ORDER BY KT04_01_001 ";
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getAll_HM03(String g_date, String g_ca,String s) {
        String selectQuery = "SELECT KT04_03_001,KT04_03_002,KT04_03_003,KT04_03_004,KT04_03_005,tc_fac005,tc_fac006,tc_fac011,tc_fac002,tc_fac001,tc_fac003 " +
                "FROM KT04_03_file,tc_fac_file " +
                " WHERE tc_fac004 = KT04_03_001 " +
                " AND KT04_03_006 = '" + g_date + "' " +
                " AND KT04_03_007 = '" + g_ca + "' " +
                " AND tc_fac001 = '" + s + "' " +
                " ORDER BY KT04_03_001 ";
        return db.rawQuery(selectQuery, null);
    }
    public Cursor getAll_HM04(String g_date, String g_ca) {
        String selectQuery = "SELECT KT04_04_001 " +
                "FROM KT04_04_file " +
                " WHERE KT04_04_002 = '" + g_date + "' " +
                " AND KT04_04_003 = '" + g_ca + "' " ;
        return db.rawQuery(selectQuery, null);
    }

    public void upd_HM0102(String g_col, String g_maChiTiet, String g_noidung, String g_date, String g_ca) {
        try {
            db.execSQL("UPDATE KT04_01_file SET " + g_col + "='" + g_noidung + "' " +
                    " WHERE KT04_01_001='" + g_maChiTiet + "' " +
                    " AND KT04_01_004 = '" + g_date + "' " +
                    " AND KT04_01_005 = '" + g_ca + "' ");
        } catch (Exception e) {
            String ex = e.getMessage().toString();
        }

    }
    public void upd_HM03(String g_col, String g_maChiTiet, String g_noidung, String g_date, String g_ca) {
        try {
            db.execSQL("UPDATE KT04_03_file SET " + g_col + "='" + g_noidung + "' " +
                    " WHERE KT04_03_001='" + g_maChiTiet + "' " +
                    " AND KT04_03_006 = '" + g_date + "' " +
                    " AND KT04_03_007 = '" + g_ca + "' ");
        } catch (Exception e) {
            String ex = e.getMessage().toString();
        }

    }
    public void upd_HM04(String g_col, String g_date, String g_ca, String g_noidung) {
        try {
            db.execSQL("UPDATE KT04_04_file SET " + g_col + "='" + g_noidung + "' " +
                    " WHERE KT04_04_002='" + g_date + "' " +
                    " AND KT04_04_003 = '" + g_ca + "' ");
        } catch (Exception e) {
            String ex = e.getMessage().toString();
        }
    }

    public Cursor getAll_lvQuery() {
        String selectQuery = " SELECT COUNT(*) AS _id ,KT04_01_004, KT04_01_005 FROM KT04_01_file" +
                " GROUP BY KT04_01_004, KT04_01_005" +
                " ORDER BY KT04_01_004, KT04_01_005";
        return db.rawQuery(selectQuery, null);

        // Các cột cần lấy trong bảng
        /*String[] projection = {"_id","distinct KT03_01_004", "KT03_01_005"};
        // Các cột dùng để ORDER BY
        String sortOrder = "KT03_01_004, KT03_01_005";
        return db.query(KT03_TABLE, projection, null, null, null, null, sortOrder, null);*/
    }

    public Cursor getDataUpLoad_hm01() {
        try {
            String selectQuery = "SELECT * FROM KT04_01_file  ORDER BY KT04_01_004,KT04_01_005,KT04_01_001 ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getDataUpLoad_hm02() {
        try {
            String selectQuery = "SELECT * FROM KT04_01_file  ORDER BY KT04_01_004,KT04_01_005,KT04_01_001 ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getDataUpLoad_hm03() {
        try {
            String selectQuery = "SELECT * FROM KT04_03_file  ORDER BY KT04_03_006,KT04_03_007,KT04_03_001 ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getDataUpLoad_hm04() {
        try {
            String selectQuery = "SELECT * FROM KT04_04_file  ORDER BY KT04_04_002,KT04_04_003 ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }


}
