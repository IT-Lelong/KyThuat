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
    String KT03_01_007 = "KT03_01_007"; //sl hinh
    String KT03_01_008 = "KT03_01_008"; //tenhinh
    String KT03_01_009 = "KT03_01_009"; //trangthai
    String KT03_01_010 = "KT03_01_010"; //sl hinh

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
    String KT03_03_011 = "KT03_03_011"; //Trang thai

    String KT03_04_TABLE = "KT03_04_file";
    String KT03_04_001 = "KT03_04_001"; //Ngày
    String KT03_04_002 = "KT03_04_002"; //Ca
    String KT03_04_003 = "KT03_04_003"; //Nhân viên
    String KT03_04_004 = "KT03_04_004"; //Đánh giá
    String KT03_04_005 = "KT03_04_005"; //Trang thai



    String TB_TC_FAR_KT03 = "tc_far_file_03";
    String tc_far001 = "tc_far001";//Mã hạng muc
    String tc_far002 = "tc_far002";//Ngày
    String tc_far003 = "tc_far003";//Manv
    String tc_far004 = "tc_far004";//ca
    String tc_far005 = "tc_far005";//Tên hình
    String tc_far006 = "tc_far006";//Ghi chu
    String tc_far007 = "tc_far007";//Ca
    String tc_far016 = "tc_far016";//Ngày dự kiến ct
    String tc_farpost = "tc_farpost";//

    String TABLE_NAME_Ten_anh_03 = "Ten_anh_03";
    String key1 = "key1"; //Tên ảnh
    String ngay = "ngay"; //Tên ảnh
    String bophan = "bophan"; //Tên ảnh
    String stt = "stt"; //Số thứ tự
    String tenanh = "tenanh"; //Tên ảnh
    String ca = "ca"; //Tên ảnh
    String manv = "manv"; //Tên ảnh

    String TABLE_NAME_Ten_anhCT_03 = "Ten_anhCT_03";
    String key1CT = "key1CT"; //Tên ảnh
    String ngayCT = "ngayCT"; //Tên ảnh
    String bophanCT = "bophanCT"; //Tên ảnh
    String sttCT = "sttCT"; //Số thứ tự
    String tenanhCT = "tenanhCT"; //Tên ảnh
    String caCT = "caCT"; //Tên ảnh
    String manvCT = "manvCT"; //Tên ảnh

    String CREATE_TABLE_KT03 = "CREATE TABLE IF NOT EXISTS " + KT03_TABLE + " ("
            + KT03_01_001 + " TEXT," + KT03_01_002 + " TEXT,"
            + KT03_01_003 + " TEXT," + KT03_01_004 + " TEXT,"
            + KT03_01_005 + " TEXT," + KT03_01_006 + " TEXT," + KT03_01_007 + " TEXT," + KT03_01_008 + " TEXT," + KT03_01_009 + " TEXT," + KT03_01_010 + " TEXT)";

    String CREATE_TABLE_KT03_03 = "CREATE TABLE IF NOT EXISTS " + KT03_03_TABLE + " ("
            + KT03_03_001 + " TEXT," + KT03_03_002 + " TEXT," + KT03_03_003 + " TEXT," + KT03_03_004 + " TEXT," + KT03_03_005 + " TEXT,"
            + KT03_03_006 + " TEXT," + KT03_03_007 + " TEXT," + KT03_03_008 + " TEXT," + KT03_03_009 + " TEXT," + KT03_03_010 + " TEXT ," + KT03_03_011 + " TEXT)";

    String CREATE_TABLE_KT03_04 = "CREATE TABLE IF NOT EXISTS " + KT03_04_TABLE + " ("
            + KT03_04_001 + " TEXT," + KT03_04_002 + " TEXT," + KT03_04_003 + " TEXT," + KT03_04_004 + " TEXT," + KT03_04_005 + " TEXT)";


    String CREATE_TABLE_NAME_TC_FAR_KT03  = "CREATE TABLE IF NOT EXISTS " + TB_TC_FAR_KT03 + " ("
            + tc_far001 + " TEXT," + tc_far002 + " TEXT," + tc_far003 + " TEXT," + tc_far004 + " TEXT," +
            tc_far005 + " TEXT ," + tc_far006 + " TEXT," + tc_far016 + " TEXT," + tc_far007 + " TEXT,"  + tc_farpost + " TEXT)";

    String CREATE_Ten_anh_03 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Ten_anh_03 + " ("
            + stt + " TEXT, " + tenanh + " TEXT, " + key1 + " TEXT, " + bophan + " TEXT, " + ngay + " TEXT," + ca + " TEXT," + manv + " TEXT," + "PRIMARY KEY (" + ngay + ", " + stt + "))";

    String CREATE_Ten_anhCT_03 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Ten_anhCT_03 + " ("
            + sttCT + " TEXT, " + tenanhCT + " TEXT, " + key1CT + " TEXT, " + bophanCT + " TEXT, " + ngayCT + " TEXT," + caCT + " TEXT," + manvCT + " TEXT," + "PRIMARY KEY (" + ngayCT + ", " + sttCT + "))";

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
            db.execSQL(CREATE_TABLE_NAME_TC_FAR_KT03);
            db.execSQL(CREATE_Ten_anh_03);
            db.execSQL(CREATE_Ten_anhCT_03);
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
        String whereClause_tenanh = "ngay=? AND ca=?";
        String whereClause_far_03 = "tc_far002=? AND tc_far004=?";
        String[] strings = new String[]{qry_ngay, qry_ca};
        db.delete(KT03_TABLE, whereClause_hm0102, strings);
        db.delete(KT03_03_TABLE, whereClause_hm03, strings);
        db.delete(KT03_04_TABLE, whereClause_hm04, strings);
        db.delete(TABLE_NAME_Ten_anh_03, whereClause_tenanh, strings);
        db.delete(TB_TC_FAR_KT03, whereClause_far_03, strings);
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

    public Boolean KT_ndhinh(String key, String l_ca, String manv, String l_ngay) {
        try {
            int count = 0;
            String selectQuery = "SELECT count(*) as dem FROM " + KT03_TABLE + " WHERE KT03_01_001='" + key + "' AND KT03_01_005='" + l_ca + "' AND KT03_01_004='" + l_ngay + "' AND KT03_01_006='" + manv + "' AND (KT03_01_010 >0 ) ";
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

    /*public String updateDiem(String hm, String ngay, String ca) {
        try {
            ContentValues args = new ContentValues();
            db.execSQL("UPDATE " + TABLE_NAME_TC_FAA + " SET tc_faa004 ='false',tc_faa012 ='false',tc_faa013 ='false'," +
                    " tc_faa014 ='true',tc_faa015 ='false',tc_faa016 ='false' WHERE tc_faa001='" + hm + "' AND tc_faa002 = '"+ngay+"'" +
                    "AND tc_faa003 = '"+ca+"' ");
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }*/

    public String updateGhichu(String hm,  String donvi,String ngay, String to, String ghichu) {
        try {

            ContentValues args = new ContentValues();
            db.execSQL("UPDATE " + TB_TC_FAR_KT03 + " SET tc_far006 ='" + ghichu + "' WHERE tc_far001='" + hm + "'  AND tc_far002='" + ngay + "' AND tc_far003='" + donvi + "' AND tc_far004='" + to + "'");
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    public Boolean getcountAnhpost(String tenanh1) {
        try {
            Cursor mCursor= db.rawQuery("SELECT  tc_far005 " + " FROM " + TB_TC_FAR_KT03 + " WHERE tc_far005 = '"+tenanh1+"' AND tc_farpost != 'Y'  ", null);
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
        db.delete(TABLE_NAME_Ten_anh_03, whereClause_hm0102, strings);
    }
    public void delete_tenanh_tc_far(String tenanh1) {
        String whereClause_hm0102 = "tc_far005=? ";
        String[] strings = new String[]{tenanh1};
        db.delete(TB_TC_FAR_KT03, whereClause_hm0102, strings);
    }
    public Cursor demsttanh(String KEY, String bp, String ngay, String hm) {
        try {

            return db.rawQuery("SELECT  KT03_01_010 FROM " + KT03_TABLE + " " +
                    " WHERE KT03_01_001='" + hm + "' AND KT03_01_005='" + bp + "' AND KT03_01_004='" + ngay + "' AND KT03_01_006='" + KEY + "'  ", null);
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
            db.execSQL("UPDATE " + KT03_TABLE + " SET " + tencothinh + "='" + l_check + "'," + tencotstt + "='" + stt + "', KT03_01_009 = 'N' " +
                    " WHERE KT03_01_001='" + key + "'  AND KT03_01_006='" + bp + "' AND KT03_01_004='" + date + "' AND KT03_01_005='" + ca + "'");
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
            String selectQuery = "SELECT count(*) as l_count FROM " + TABLE_NAME_Ten_anh_03 + " WHERE key1= '"+g_hm+"' " +
                    " and ngay = '"+g_ngay+"' and ca = '"+g_ca+"' and manv = '"+g_manv+"' ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor xuatghichu(String hm, String ngay, String donvi,String to) {
        try {

            return db.rawQuery("SELECT tc_far006 "

                    + " FROM " + TB_TC_FAR_KT03 + "  WHERE tc_far001='" + hm + "' AND tc_far002 ='" + ngay + "' AND tc_far003='" + donvi + "' AND tc_far004='" + to + "'", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor lananh(String key, String l_ngay, String l_bp , String l_to) {
        try {
            return db.rawQuery("SELECT stt,tenanh"

                    + " FROM " + TABLE_NAME_Ten_anh_03 + "  WHERE ngay='" + l_ngay + "' AND manv='" + l_bp + "' AND key1='" + key + "' AND ca='" + l_to + "'", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor lananh2(String key, String l_ngay, String l_bp, String l_to) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT stt,tenanh FROM " + TABLE_NAME_Ten_anh_03 + " WHERE ngay='" + l_ngay + "' AND manv='" + l_bp + "'AND key1='" + key + "'  AND ca='" + l_to + "' ORDER BY stt  ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }

    }
    public Cursor getstt(String KEY, String bp, String ngay) {
        try {
            return db.rawQuery("SELECT  max(stt+0) AS stt FROM " + TABLE_NAME_Ten_anh_03 + " ", null);
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

            db.insert(TB_TC_FAR_KT03, null, args);
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
            db.insert(TABLE_NAME_Ten_anh_03, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public void call_upd_tc_faapost (Cursor c_getTc_faa) {
        if (c_getTc_faa.getCount() > 0) {
            c_getTc_faa.moveToFirst();
            for (int i = 0; i < c_getTc_faa.getCount(); i++) {
                String g_tc_faa001 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT03_01_005"));
                String g_tc_faa002 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT03_01_004"));
                String g_tc_faa003 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT03_01_006"));
                //String g_tc_faa005 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT03_01_008"));

                db.execSQL(" UPDATE KT03_01_file SET KT03_01_009 = 'Y' " +
                        " WHERE KT03_01_005 ='" + g_tc_faa001 + "' " +
                        " AND KT03_01_004 ='" + g_tc_faa002 + "'" +
                        " AND KT03_01_006 = '" + g_tc_faa003 + "'");
                c_getTc_faa.moveToNext();
            }
        }
    }
    public void call_upd_tc_faapost3 (Cursor c_getTc_faa) {
        if (c_getTc_faa.getCount() > 0) {
            c_getTc_faa.moveToFirst();
            for (int i = 0; i < c_getTc_faa.getCount(); i++) {
                String g_tc_faa001 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT03_01_005"));
                String g_tc_faa002 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT03_01_004"));
                String g_tc_faa003 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT03_01_006"));

                db.execSQL(" UPDATE KT03_03_TABLE SET KT03_04_005 = 'Y' " +
                        " WHERE " +
                        " KT03_03_009 ='" + g_tc_faa001 + "'" +
                        " AND KT03_03_008 = '" + g_tc_faa002 + "'" +
                        " AND KT03_03_010 = '" + g_tc_faa003 + "' ");
                c_getTc_faa.moveToNext();
            }
        }
    }
    public void call_upd_tc_faapost4 (Cursor c_getTc_faa) {
        if (c_getTc_faa.getCount() > 0) {
            c_getTc_faa.moveToFirst();
            for (int i = 0; i < c_getTc_faa.getCount(); i++) {
                String g_tc_faa001 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT03_01_005"));
                String g_tc_faa002 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT03_01_004"));
                String g_tc_faa003 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("KT03_01_006"));


                db.execSQL(" UPDATE KT03_04_file SET KT03_04_005 = 'Y' " +
                        " WHERE KT03_04_002 ='" + g_tc_faa001 + "' " +
                        " AND KT03_04_001 ='" + g_tc_faa002 + "'" +
                        " AND KT03_04_003 = '" + g_tc_faa003 + "'" );
                c_getTc_faa.moveToNext();
            }
        }
    }
    public Cursor getAll_tc_faa_new(String g_date,String g_manv,String g_ca) {
        try {
            String selectQuery = null;
            //return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa007,tc_faa008,tc_faa011"

            //+ " FROM " + TABLE_NAME_TC_FAA + "", null);
            selectQuery = "SELECT KT03_01_001,KT03_01_002,KT03_01_003,KT03_01_004,KT03_01_005,KT03_01_006,KT03_01_008,KT03_01_009,KT03_01_010 "

                    + " FROM " + KT03_TABLE + " WHERE (KT03_01_009 != 'Y' OR KT03_01_009 IS NULL)  ";
            if(!g_date.equals(""))
            {
                selectQuery += " AND KT03_01_004 = '"+g_date+"' ";
            }
            if(!g_manv.equals("")){
                selectQuery += " AND KT03_01_006 = '"+g_manv+"' ";
            }
            if(!g_ca.equals("")){
                selectQuery += " AND KT03_01_005 = '"+g_ca+"' ";
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

                    + " FROM " + KT03_03_TABLE  + " WHERE ( KT03_03_011 != 'Y'  OR KT03_03_011 is null)  ";
            if(!g_date.equals(""))
            {
                selectQuery += " AND KT03_03_008 = '"+g_date+"' ";
            }
            if(!g_manv.equals("")){
                selectQuery += " AND KT03_03_010 = '"+g_manv+"' ";
            }
            if(!g_ca.equals("")){
                selectQuery += " AND KT03_03_009 = '"+g_ca+"' ";
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

                    + " FROM " + KT03_04_TABLE   + " WHERE  (KT03_04_005 != 'Y' OR KT03_04_005 is null ) ";
            if(!g_date.equals(""))
            {
                selectQuery += " AND KT03_04_001 = '"+g_date+"' ";
            }
            if(!g_manv.equals("")){
                selectQuery += " AND KT03_04_003 = '"+g_manv+"' ";
            }
            if(!g_ca.equals("")){
                selectQuery += " AND KT03_04_002 = '"+g_ca+"' ";
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
            selectQuery = "SELECT tc_far001,tc_far002,tc_far003,tc_far004,tc_far005,IFNULL(tc_far006,' ') tc_far006 FROM " + TB_TC_FAR_KT03 + " WHERE tc_farpost != 'Y'  ";
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
    public Cursor getAll_datekt() {
        try {
            return db.rawQuery("SELECT DISTINCT KT03_01_004 FROM " + KT03_TABLE + " WHERE KT03_01_009 is null OR KT03_01_009='N'  ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_bophan() {
        try {
            return db.rawQuery("SELECT DISTINCT KT03_01_006  FROM " + KT03_TABLE + " WHERE KT03_01_009 is null OR KT03_01_009='N'", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_ca() {
        try {
            return db.rawQuery("SELECT DISTINCT KT03_01_005 FROM " + KT03_TABLE + " WHERE KT03_01_009 is null OR KT03_01_009='N' ", null);
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
}