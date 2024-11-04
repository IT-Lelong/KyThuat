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
    String KT04_01_007 = "KT04_01_007"; //sl hinh
    String KT04_01_008 = "KT04_01_008"; //tenhinh
    String KT04_01_009 = "KT04_01_009"; //trangthai

    String KT04_03_TABLE = "KT04_03_file";
    String KT04_03_001 = "KT04_03_001"; //Mã hạng mục
    String KT04_03_002 = "KT04_03_002"; //Nồng độ
    String KT04_03_003 = "KT04_03_003"; //Hạn sử dụng
    String KT04_03_004 = "KT04_03_004"; //Giá trị
    String KT04_03_005 = "KT04_03_005"; //Sai số
    String KT04_03_006 = "KT04_03_006"; //Ngày
    String KT04_03_007 = "KT04_03_007"; //Ca
    String KT04_03_008 = "KT04_03_008"; //Nhân viên
    String KT04_03_009 = "KT04_03_009"; //Trang thai

    String KT04_04_TABLE = "KT04_04_file";
    String KT04_04_001 = "KT04_04_001"; //Đánh giá
    String KT04_04_002 = "KT04_04_002"; //Ngày
    String KT04_04_003 = "KT04_04_003"; //Ca
    String KT04_04_004 = "KT04_04_004"; //Nhân viên
    String KT04_04_005 = "KT04_04_005"; //Trang thai

    String TB_TC_FAR_KT04 = "tc_far_file_04";
    String tc_far001 = "tc_far001";//Mã hạng muc
    String tc_far002 = "tc_far002";//Ngày
    String tc_far003 = "tc_far003";//Manv
    String tc_far004 = "tc_far004";//ca
    String tc_far005 = "tc_far005";//Tên hình
    String tc_far006 = "tc_far006";//Ghi chu
    String tc_far007 = "tc_far007";//Ca
    String tc_far016 = "tc_far016";//Ngày dự kiến ct
    String tc_farpost = "tc_farpost";//

    String TABLE_NAME_Ten_anh_04 = "Ten_anh_04";
    String key1 = "key1"; //Tên ảnh
    String ngay = "ngay"; //Tên ảnh
    String bophan = "bophan"; //Tên ảnh
    String stt = "stt"; //Số thứ tự
    String tenanh = "tenanh"; //Tên ảnh
    String ca = "ca"; //Tên ảnh
    String manv = "manv"; //Tên ảnh

    String TABLE_NAME_Ten_anhCT_04 = "Ten_anhCT_04";
    String key1CT = "key1CT"; //Tên ảnh
    String ngayCT = "ngayCT"; //Tên ảnh
    String bophanCT = "bophanCT"; //Tên ảnh
    String sttCT = "sttCT"; //Số thứ tự
    String tenanhCT = "tenanhCT"; //Tên ảnh
    String caCT = "caCT"; //Tên ảnh
    String manvCT = "manvCT"; //Tên ảnh

    String CREATE_TABLE_KT04 = "CREATE TABLE IF NOT EXISTS " + KT04_TABLE + " ("
            + KT04_01_001 + " TEXT," + KT04_01_002 + " TEXT,"
            + KT04_01_003 + " TEXT," + KT04_01_004 + " TEXT,"
            + KT04_01_005 + " TEXT," + KT04_01_006 + " TEXT," + KT04_01_007 + " TEXT," + KT04_01_008 + " TEXT," + KT04_01_009 + " TEXT)";

    String CREATE_TABLE_KT04_03 = "CREATE TABLE IF NOT EXISTS " + KT04_03_TABLE + " ("
            + KT04_03_001 + " TEXT," + KT04_03_002 + " TEXT," + KT04_03_003 + " TEXT," + KT04_03_004 + " TEXT," + KT04_03_005 + " TEXT,"
            + KT04_03_006 + " TEXT," + KT04_03_007 + " TEXT," + KT04_03_008 + " TEXT," + KT04_03_009 + " TEXT)";

    String CREATE_TABLE_KT04_04 = "CREATE TABLE IF NOT EXISTS " + KT04_04_TABLE + " ("
            + KT04_04_001 + " TEXT," + KT04_04_002 + " TEXT," + KT04_04_003 + " TEXT," + KT04_04_004 + " TEXT," + KT04_04_005 + " TEXT)";

    String CREATE_TABLE_NAME_TC_FAR_KT04  = "CREATE TABLE IF NOT EXISTS " + TB_TC_FAR_KT04 + " ("
            + tc_far001 + " TEXT," + tc_far002 + " TEXT," + tc_far003 + " TEXT," + tc_far004 + " TEXT," +
            tc_far005 + " TEXT ," + tc_far006 + " TEXT," + tc_far016 + " TEXT," + tc_far007 + " TEXT,"  + tc_farpost + " TEXT)";

    String CREATE_Ten_anh_04 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Ten_anh_04 + " ("
            + stt + " TEXT, " + tenanh + " TEXT, " + key1 + " TEXT, " + bophan + " TEXT, " + ngay + " TEXT," + ca + " TEXT," + manv + " TEXT," + "PRIMARY KEY (" + ngay + ", " + stt + "))";

    String CREATE_Ten_anhCT_04 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Ten_anhCT_04 + " ("
            + sttCT + " TEXT, " + tenanhCT + " TEXT, " + key1CT + " TEXT, " + bophanCT + " TEXT, " + ngayCT + " TEXT," + caCT + " TEXT," + manvCT + " TEXT," + "PRIMARY KEY (" + ngayCT + ", " + sttCT + "))";

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
            db.execSQL(CREATE_TABLE_NAME_TC_FAR_KT04);
            db.execSQL(CREATE_Ten_anh_04);
            db.execSQL(CREATE_Ten_anhCT_04);
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
        String whereClause_tenanh = "ngay=? AND ca=?";
        String whereClause_far_04 = "tc_far002=? AND tc_far004=?";
        String[] strings = new String[]{qry_ngay, qry_ca};
        db.delete(KT04_TABLE, whereClause_hm01, strings);
        db.delete(KT04_TABLE, whereClause_hm02, strings);
        db.delete(KT04_03_TABLE, whereClause_hm03, strings);
        db.delete(TABLE_NAME_Ten_anh_04, whereClause_tenanh, strings);
        db.delete(TB_TC_FAR_KT04, whereClause_far_04, strings);
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
            args.put(KT04_03_001, g_KT04_01_001);
            args.put(KT04_03_002, g_KT04_01_002);
            args.put(KT04_03_003, g_KT04_01_003);
            args.put(KT04_03_004, g_KT04_01_004);
            args.put(KT04_03_005, g_KT04_01_005);
            args.put(KT04_03_006, g_KT04_01_006);
            args.put(KT04_03_007, g_KT04_01_002);
            args.put(KT04_03_008, g_KT04_01_003);
            args.put(KT04_03_009, g_KT04_01_004);
            args.put(KT04_03_010, g_KT04_01_005);
            db.insert(KT04_TABLE, null, args);
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
        /*String[] projection = {"_id","distinct KT04_01_004", "KT04_01_005"};
        // Các cột dùng để ORDER BY
        String sortOrder = "KT04_01_004, KT04_01_005";
        return db.query(KT04_TABLE, projection, null, null, null, null, sortOrder, null);*/
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

    public Boolean KT_ndhinh(String key, String l_ca, String manv, String l_ngay) {
        try {
            int count = 0;
            String selectQuery = "SELECT count(*) as dem FROM " + KT04_TABLE + " WHERE KT04_01_001='" + key + "' AND KT04_01_005='" + l_ca + "' AND KT04_01_004='" + l_ngay + "' AND KT04_01_006='" + manv + "' AND (KT04_01_007 >0 ) ";
            Cursor mCount = db.rawQuery(selectQuery, null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public String updateGhichu(String hm,  String donvi,String ngay, String to, String ghichu) {
        try {

            ContentValues args = new ContentValues();
            db.execSQL("UPDATE " + TB_TC_FAR_KT04 + " SET tc_far006 ='" + ghichu + "' WHERE tc_far001='" + hm + "'  AND tc_far002='" + ngay + "' AND tc_far003='" + donvi + "' AND tc_far004='" + to + "'");
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public Boolean getcountAnhpost(String tenanh1) {
        try {
            Cursor mCursor= db.rawQuery("SELECT  tc_far005 " + " FROM " + TB_TC_FAR_KT04 + " WHERE tc_far005 = '"+tenanh1+"' AND tc_farpost != 'Y'  ", null);
            if (mCursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public void delete_tenanh(String tenanh1) {
        String whereClause_hm0102 = "tenanh=? ";
        String[] strings = new String[]{tenanh1};
        db.delete(TABLE_NAME_Ten_anh_04, whereClause_hm0102, strings);
    }
    public void delete_tenanh_tc_far(String tenanh1) {
        String whereClause_hm0102 = "tc_far005=? ";
        String[] strings = new String[]{tenanh1};
        db.delete(TB_TC_FAR_KT04, whereClause_hm0102, strings);
    }
    public Cursor demsttanh(String KEY, String bp, String ngay, String hm) {
        try {

            return db.rawQuery("SELECT  KT04_01_007 FROM " + KT04_TABLE + " " +
                    " WHERE KT04_01_001='" + hm + "' AND KT04_01_005='" + bp + "' AND KT04_01_004='" + ngay + "' AND KT04_01_006='" + KEY + "'  ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public String appendUPDAEhinhanh(String key, String l_check, int stt, String date, String bp, String tencothinh, String tencotstt, String ca) {
        try {
            ContentValues args = new ContentValues();
            //   args.put(no1,xno1);
            //  args.put(ip,xip);
            //  Cursor mCursor=db.query(TABLE_NAME,new String[]{no1},no1+"=?",new String[]{xno1},null,null,null,null);
            //  if(mCursor.getCount()>0){
            db.execSQL("UPDATE " + KT04_TABLE + " SET " + tencothinh + "='" + l_check + "'," + tencotstt + "='" + stt + "', KT04_01_009 = 'N' " +
                    " WHERE KT04_01_001='" + key + "'  AND KT04_01_006='" + bp + "' AND KT04_01_004='" + date + "' AND KT04_01_005='" + ca + "'");
            //db.execSQL("UPDATE " + TABLE_NAME_TC_FAA + " SET "+tencotstt+"='"+stt+"' WHERE tc_faa001='"+key+"'  AND tc_faa003='"+bp+"'");
            return "TRUE";
            //  }else{
            //    db.insert(TABLE_NAME,null,args);
            //     return "TRUE";
            // }
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public Cursor getTen_Anh(String g_manv,String g_ngay,String g_ca,String g_hm) {
        try {
            String selectQuery = "SELECT count(*) as l_count FROM " + TABLE_NAME_Ten_anh_04 + " WHERE key1= '"+g_hm+"' " +
                    " and ngay = '"+g_ngay+"' and ca = '"+g_ca+"' and manv = '"+g_manv+"' ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor xuatghichu(String hm, String ngay, String donvi,String to) {
        try {

            return db.rawQuery("SELECT tc_far006 "

                    + " FROM " + TB_TC_FAR_KT04 + "  WHERE tc_far001='" + hm + "' AND tc_far002 ='" + ngay + "' AND tc_far003='" + donvi + "' AND tc_far004='" + to + "'", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor lananh(String key, String l_ngay, String l_bp , String l_to) {
        try {
            return db.rawQuery("SELECT stt,tenanh"

                    + " FROM " + TABLE_NAME_Ten_anh_04 + "  WHERE ngay='" + l_ngay + "' AND manv='" + l_bp + "' AND key1='" + key + "' AND ca='" + l_to + "'", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor lananh2(String key, String l_ngay, String l_bp, String l_to) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT stt,tenanh FROM " + TABLE_NAME_Ten_anh_04 + " WHERE ngay='" + l_ngay + "' AND manv='" + l_bp + "'AND key1='" + key + "'  AND ca='" + l_to + "' ORDER BY stt  ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }

    }
    public Cursor getstt(String KEY, String bp, String ngay) {
        try {
            return db.rawQuery("SELECT  max(stt+0) AS stt FROM " + TABLE_NAME_Ten_anh_04 + " ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public String ins_img_tc_far(String g_key, String g_ngay, String g_bp, String g_to, String g_tenanh) {
        try {
            ContentValues args = new ContentValues();
            args.put("tc_far001", g_key);
            args.put("tc_far002", g_ngay);
            args.put("tc_far003", g_bp);
            args.put("tc_far004", g_to);
            args.put("tc_far005", g_tenanh);
            args.put("tc_farpost", "N");

            db.insert(TB_TC_FAR_KT04, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public String append1(String g_key, String g_ngay, String g_bp, String g_stt, String g_tenanh, String g_ca) {
        try {
            ContentValues args = new ContentValues();
            args.put(key1, g_key);
            args.put(ngay, g_ngay);
            args.put(manv, g_bp);
            args.put(stt, g_stt);
            args.put(tenanh, g_tenanh);
            args.put(ca, g_ca);
            db.insert(TABLE_NAME_Ten_anh_04, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public Cursor getAll_datekt() {
        try {
            return db.rawQuery("SELECT DISTINCT KT04_01_004 FROM " + KT04_TABLE + " WHERE KT04_01_009 is null OR KT04_01_009='N'  ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_bophan() {
        try {
            return db.rawQuery("SELECT DISTINCT KT04_01_006  FROM " + KT04_TABLE + " WHERE KT04_01_009 is null OR KT04_01_009='N' ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_ca() {
        try {
            return db.rawQuery("SELECT DISTINCT KT04_01_005 FROM " + KT04_TABLE + " WHERE KT04_01_009 is null OR KT04_01_009='N' ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public void update_tc_farpost(String image_no, String image_date, String image_dept, String image_to, String image_name) {
        try {
            db.execSQL(" UPDATE tc_far_file_03 SET tc_farpost = 'Y' " +
                    " WHERE tc_far001 ='" + image_no + "' " +
                    " AND tc_far002 ='" + image_date + "'" +
                    " AND tc_far003 = '" + image_dept + "'" +
                    " AND tc_far004 = '" + image_to + "' " +
                    " AND tc_far005 = '" + image_name + "' ");
        } catch (Exception e) {
        }
    }
    public Cursor getAll_tc_faa_new(String g_date,String g_manv,String g_ca) {
        try {
            String selectQuery = null;
            //return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa007,tc_faa008,tc_faa011"

            //+ " FROM " + TABLE_NAME_TC_FAA + "", null);
            selectQuery = "SELECT * "

                    + " FROM " + KT04_TABLE + " WHERE (KT04_01_009 != 'Y' OR KT04_01_009 IS NULL)  ";
            if(!g_date.equals(""))
            {
                selectQuery += " AND KT04_01_004= '"+g_date+"' ";
            }
            if(!g_manv.equals("")){
                selectQuery += " AND KT04_01_006 = '"+g_manv+"' ";
            }
            if(!g_ca.equals("")){
                selectQuery += " AND KT04_01_005 = '"+g_ca+"' ";
            }
            //return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa008,IFNULL(tc_faa011,0) tc_faa011,tc_faa012,tc_faa013" +
            //    ",tc_faa014,tc_faa015,tc_faa016,IFNULL(tc_faa018,0) tc_faa018,tc_faa019"

            //    + " FROM " + TABLE_NAME_TC_FAA + "," + TABLE_NAME_TC_FBA + " WHERE tc_faa003= tc_fba007 AND tc_faa005 is not null and tc_faa011 > 0 and tc_faapost = 'N' AND tc_faa002 = '"+g_date+"' AND tc_fba009 = '"+g_bophan+"' ", null);
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_tc_faa_03_new(String g_date,String g_manv,String g_ca) {
        try {
            String selectQuery = null;
            //return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa007,tc_faa008,tc_faa011"

            //+ " FROM " + TABLE_NAME_TC_FAA + "", null);
            selectQuery = "SELECT * "

                    + " FROM " + KT04_03_TABLE  + " WHERE ( KT04_03_009 != 'Y'  OR KT04_03_009 is null)  ";
            if(!g_date.equals(""))
            {
                selectQuery += " AND KT04_03_006 = '"+g_date+"' ";
            }
            if(!g_manv.equals("")){
                selectQuery += " AND KT04_03_008 = '"+g_manv+"' ";
            }
            if(!g_ca.equals("")){
                selectQuery += " AND KT04_03_007 = '"+g_ca+"' ";
            }
            //return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa008,IFNULL(tc_faa011,0) tc_faa011,tc_faa012,tc_faa013" +
            //    ",tc_faa014,tc_faa015,tc_faa016,IFNULL(tc_faa018,0) tc_faa018,tc_faa019"

            //    + " FROM " + TABLE_NAME_TC_FAA + "," + TABLE_NAME_TC_FBA + " WHERE tc_faa003= tc_fba007 AND tc_faa005 is not null and tc_faa011 > 0 and tc_faapost = 'N' AND tc_faa002 = '"+g_date+"' AND tc_fba009 = '"+g_bophan+"' ", null);
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_tc_faa_04_new(String g_date,String g_manv,String g_ca) {
        try {
            String selectQuery = null;
            //return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa007,tc_faa008,tc_faa011"

            //+ " FROM " + TABLE_NAME_TC_FAA + "", null);
            selectQuery = "SELECT * "

                    + " FROM " + KT04_04_TABLE   + " WHERE  (KT04_04_005 != 'Y' OR KT04_04_005 is null ) ";
            if(!g_date.equals(""))
            {
                selectQuery += " AND KT04_04_002 = '"+g_date+"' ";
            }
            if(!g_manv.equals("")){
                selectQuery += " AND KT04_04_004 = '"+g_manv+"' ";
            }
            if(!g_ca.equals("")){
                selectQuery += " AND KT04_04_003 = '"+g_ca+"' ";
            }
            //return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa008,IFNULL(tc_faa011,0) tc_faa011,tc_faa012,tc_faa013" +
            //    ",tc_faa014,tc_faa015,tc_faa016,IFNULL(tc_faa018,0) tc_faa018,tc_faa019"

            //    + " FROM " + TABLE_NAME_TC_FAA + "," + TABLE_NAME_TC_FBA + " WHERE tc_faa003= tc_fba007 AND tc_faa005 is not null and tc_faa011 > 0 and tc_faapost = 'N' AND tc_faa002 = '"+g_date+"' AND tc_fba009 = '"+g_bophan+"' ", null);
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_tc_far(String g_date,String g_manv,String g_ca) {
        try {
            String selectQuery = null;
            selectQuery = "SELECT tc_far001,tc_far002,tc_far003,tc_far004,tc_far005,IFNULL(tc_far006,' ') tc_far006 FROM " + TB_TC_FAR_KT04 + " WHERE tc_farpost != 'Y'  ";
            //return db.rawQuery("SELECT tc_far001,tc_far002,tc_far003,tc_far004,tc_far005,IFNULL(tc_far006,' ') tc_far006 FROM " + TB_TC_FAR + "," + TABLE_NAME_TC_FBA + " WHERE tc_far003= tc_fba007 AND tc_farpost != 'Y' AND tc_far002 = '"+g_date+"' AND tc_fba009='"+g_bophan+"' ", null);
            if(!g_date.equals(""))
            {
                selectQuery += " AND tc_far002 = '"+g_date+"' ";
            }
            if(!g_manv.equals("")){
                selectQuery += " AND tc_far003 = '"+g_manv+"' ";
            }
            if(!g_ca.equals("")){
                selectQuery += " AND tc_far004 = '"+g_ca+"' ";
            }
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }
    public void call_upd_tc_faapost (Cursor c_getTc_faa) {
        if (c_getTc_faa.getCount() > 0) {
            c_getTc_faa.moveToFirst();
            for (int i = 0; i < c_getTc_faa.getCount(); i++) {
                String g_tc_faa001 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT04_01_005"));
                String g_tc_faa002 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT04_01_004"));
                String g_tc_faa003 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT04_01_006"));
                //String g_tc_faa005 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT03_01_008"));

                db.execSQL(" UPDATE KT04_01_file SET KT04_01_009 = 'Y' " +
                        " WHERE KT04_01_005 ='" + g_tc_faa001 + "' " +
                        " AND KT04_01_004 ='" + g_tc_faa002 + "'" +
                        " AND KT04_01_006 = '" + g_tc_faa003 + "'");
                c_getTc_faa.moveToNext();
            }
        }
    }
    public void call_upd_tc_faapost3 (Cursor c_getTc_faa) {
        if (c_getTc_faa.getCount() > 0) {
            c_getTc_faa.moveToFirst();
            for (int i = 0; i < c_getTc_faa.getCount(); i++) {
                String g_tc_faa001 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT04_01_005"));
                String g_tc_faa002 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT04_01_004"));
                String g_tc_faa003 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT04_01_006"));

                db.execSQL(" UPDATE KT04_03_file SET KT04_03_009 = 'Y' " +
                        " WHERE " +
                        " KT04_03_007 ='" + g_tc_faa001 + "'" +
                        " AND KT04_03_006 = '" + g_tc_faa002 + "'" +
                        " AND KT04_03_008 = '" + g_tc_faa003 + "' ");
                c_getTc_faa.moveToNext();
            }
        }
    }
    public void call_upd_tc_faapost4 (Cursor c_getTc_faa) {
        if (c_getTc_faa.getCount() > 0) {
            c_getTc_faa.moveToFirst();
            for (int i = 0; i < c_getTc_faa.getCount(); i++) {
                String g_tc_faa001 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT04_01_005"));
                String g_tc_faa002 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT04_01_004"));
                String g_tc_faa003 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT04_01_006"));


                db.execSQL(" UPDATE KT04_04_file SET KT04_04_005 = 'Y' " +
                        " WHERE KT04_04_003 ='" + g_tc_faa001 + "' " +
                        " AND KT04_04_002 ='" + g_tc_faa002 + "'" +
                        " AND KT04_04_004 = '" + g_tc_faa003 + "'" );
                c_getTc_faa.moveToNext();
            }
        }
    }
}
