package com.lelong.kythuat.KT01;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public
class KT01_DB {
    private Context mCtx = null;
    String DATABASE_NAME = "KyThuatDB.db";
    public static SQLiteDatabase db = null;


    String TABLE_NAME_Ten_anh = "Ten_anh";
    String key1 = "key1"; //Tên ảnh
    String ngay = "ngay"; //Tên ảnh
    String bophan = "bophan"; //Tên ảnh
    String stt = "stt"; //Số thứ tự
    String tenanh = "tenanh"; //Tên ảnh

    String TABLE_NAME_Ten_anhCT = "Ten_anhCT";
    String key1CT = "key1CT"; //Tên ảnh
    String ngayCT = "ngayCT"; //Tên ảnh
    String bophanCT = "bophanCT"; //Tên ảnh
    String sttCT = "sttCT"; //Số thứ tự
    String tenanhCT = "tenanhCT"; //Tên ảnh

    String TABLE_NAME_TC_FAA = "tc_faa_file";
    String tc_faa001 = "tc_faa001"; //Mã hạng mục
    String tc_faa002 = "tc_faa002"; //Ngày
    String tc_faa003 = "tc_faa003"; //Bộ Phận
    String tc_faa004 = "tc_faa004"; //Trạng thái

    String tc_faa005 = "tc_faa005"; //Tên ảnh

    String tc_faa006 = "tc_faa006"; //Ghi chép khảo hạch
    String tc_faa007 = "tc_faa007"; //Số điểm
    String tc_faa008 = "tc_faa008"; //
    String tc_faa009 = "tc_faa009"; //
    String tc_faa010 = "tc_faa010"; //
    String tc_faa011 = "tc_faa011"; // ds du lieu
    String tc_faa012 = "tc_faa012"; //Trạng thái1
    String tc_faa013 = "tc_faa013"; //Trạng thái2
    String tc_faa014 = "tc_faa014"; //Trạng thái3
    String tc_faa015 = "tc_faa015"; //Trạng thái4
    String tc_faa016 = "tc_faa016"; //Trạng thái5
    String tc_faa017 = "tc_faa017";//Tên hinh cải thiện
    String tc_faa018 = "tc_faa018";// So hình cải thiện
    String tc_faa019 = "tc_faa019";// Tổ
    String tc_faapost = "tc_faapost";// Tổ

    String TABLE_NAME_GEM = "gem_file";
    String gem01 = "gem01"; //Mã bộ phận
    String gem02 = "gem02"; //Tên bộ phận

    String TABLE_NAME_TC_FBA = "tc_fba_file";
    String tc_fba007 = "tc_fba007"; //Mã bộ phận
    String tc_fba009 = "tc_fba009"; //Tên bộ phận

    String TB_TC_FAR = "tc_far_file";
    String tc_far001 = "tc_far001";//Mã hạng muc
    String tc_far002 = "tc_far002";//Ngày
    String tc_far003 = "tc_far003";//Đơn vị
    String tc_far004 = "tc_far004";//Tổ
    String tc_far005 = "tc_far005";//Tên hình
    String tc_far006 = "tc_far006";//Ghi chu
    String tc_far016 = "tc_far016";//Ngày dự kiến ct
    String tc_farpost = "tc_farpost";//

    String CREATE_TABLE_NAME_TC_FAR = "CREATE TABLE IF NOT EXISTS " + TB_TC_FAR + " ("
            + tc_far001 + " TEXT," + tc_far002 + " TEXT," + tc_far003 + " TEXT," + tc_far004 + " TEXT," +
            "" + tc_far005 + " TEXT ," + tc_far006 + " TEXT," + tc_far016 + " TEXT," + tc_farpost + " TEXT)";

    String CREATE_TABLE_NAME_TC_FBA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FBA + " ("
            + tc_fba007 + " TEXT," + tc_fba009 + " TEXT )";

    String CREATE_TABLE_FAA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAA + " ("
            + tc_faa001 + " TEXT, " + tc_faa002 + " TEXT,"
            + tc_faa003 + " TEXT ," + tc_faa004 + " TEXT," + tc_faa005 + " TEXT,"
            + tc_faa006 + " TEXT," + tc_faa007 + " TEXT," + tc_faa008 + " TEXT,"
            + tc_faa009 + " TEXT," + tc_faa010 + " TEXT," + tc_faa011 + " TEXT,"
            + tc_faa012 + " TEXT," + tc_faa013 + " TEXT," + tc_faa014 + " TEXT," + tc_faa015 + " TEXT," + tc_faa016 + " TEXT," + tc_faa017 + " TEXT," + tc_faa018 + " TEXT," + tc_faa019 + " TEXT," + tc_faapost + " TEXT,"
            + "PRIMARY KEY (" + tc_faa001 + ", " + tc_faa003 + "," + tc_faa002 + "))";

    String CREATE_Ten_anh = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Ten_anh + " ("
            + stt + " TEXT, " + tenanh + " TEXT, " + key1 + " TEXT, " + bophan + " TEXT, " + ngay + " TEXT," + "PRIMARY KEY (" + ngay + ", " + stt + "))";

    String CREATE_Ten_anhCT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Ten_anhCT + " ("
            + sttCT + " TEXT, " + tenanhCT + " TEXT, " + key1CT + " TEXT, " + bophanCT + " TEXT, " + ngayCT + " TEXT," + "PRIMARY KEY (" + ngayCT + ", " + sttCT + "))";

    //KT02(S)sigture
    private final static String TABLE_NAME_FIA_UP_SIG01 = "fia_up_sigkt01_file";
    //private final static String somay_sig = "somay_sig"; //Số máy
    private final static String mabp_sig = "mabp_sig"; //Bộ phận
    private final static String tebp_sig = "tebp_sig"; //Tên bộ phận
    private final static String ngay_sig = "ngay_sig"; //Ngày
    private final static String ghichu_sig = "ghichu_sig"; //Ghi chú
    private final static String trangthai_sig = "trangthai_sig"; //Trạng thái
    private final static String manv_sig = "manv_sig"; //Số thẻ
    private final static String sogio_sig = "sogio_sig"; //Số giờ hoạt động
    private final static String tenhinh_sig = "tenhinh_sig"; //Số giờ hoạt động

    String CREATE_TABLE_NAME_FIA_UP_SIG01 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FIA_UP_SIG01 + " ("
            + mabp_sig + " TEXT," + tebp_sig + " TEXT,"
            + ngay_sig + " TEXT," + ghichu_sig + " TEXT," + trangthai_sig + " TEXT," + manv_sig + " TEXT," + sogio_sig + " TEXT," + tenhinh_sig + " TEXT )";
    //KT02(E)


    public KT01_DB(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
        try {
            db.execSQL(CREATE_TABLE_FAA);
            db.execSQL(CREATE_Ten_anh);
            db.execSQL(CREATE_Ten_anhCT);
            db.execSQL(CREATE_TABLE_NAME_TC_FBA);
            db.execSQL(CREATE_TABLE_NAME_TC_FAR);
            db.execSQL(CREATE_TABLE_NAME_FIA_UP_SIG01);
        } catch (Exception e) {

        }
    }

    public void close() {
        try {
            String DROP_TABLE_TC_FAA = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_FAA;
            db.execSQL(DROP_TABLE_TC_FAA);
            db.close();
        } catch (Exception e) {

        }
    }

    public String append(String g_tc_faa001, String g_tc_faa002, String g_tc_faa003, String g_tc_faa004, String g_tc_faa005,
                         String g_tc_faa006, String g_tc_faa007, String g_tc_faa008, String g_tc_faa009, String g_tc_faa010, String g_tc_faa011
            , String g_tc_faa012, String g_tc_faa013, String g_tc_faa014, String g_tc_faa015, String g_tc_faa016, String g_tc_faa017, String g_tc_faa018, String g_tc_faa019) {
        try {
            ContentValues args = new ContentValues();
            args.put(tc_faa001, g_tc_faa001);
            args.put(tc_faa002, g_tc_faa002);
            args.put(tc_faa003, g_tc_faa003);
            args.put(tc_faa004, g_tc_faa004);
            args.put(tc_faa005, g_tc_faa005);
            args.put(tc_faa006, g_tc_faa006);
            args.put(tc_faa007, g_tc_faa007);
            args.put(tc_faa008, g_tc_faa008);
            args.put(tc_faa009, g_tc_faa009);
            args.put(tc_faa010, g_tc_faa010);
            args.put(tc_faa011, g_tc_faa011);
            args.put(tc_faa012, g_tc_faa012);
            args.put(tc_faa013, g_tc_faa013);
            args.put(tc_faa014, g_tc_faa014);
            args.put(tc_faa015, g_tc_faa015);
            args.put(tc_faa016, g_tc_faa016);
            args.put(tc_faa017, g_tc_faa017);
            args.put(tc_faa018, g_tc_faa018);
            args.put(tc_faa019, g_tc_faa019);
            args.put(tc_faapost, "N");
            db.insert(TABLE_NAME_TC_FAA, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    public String append1(String g_key, String g_ngay, String g_bp, String g_stt, String g_tenanh) {
        try {
            ContentValues args = new ContentValues();
            args.put(key1, g_key);
            args.put(ngay, g_ngay);
            args.put(bophan, g_bp);
            args.put(stt, g_stt);
            args.put(tenanh, g_tenanh);
            db.insert(TABLE_NAME_Ten_anh, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
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

            db.insert(TB_TC_FAR, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }


    public String append2(String g_key, String g_ngay, String g_bp, String g_stt, String g_tenanh) {
        try {
            ContentValues args = new ContentValues();
            args.put(key1CT, g_key);
            args.put(ngayCT, g_ngay);
            args.put(bophanCT, g_bp);
            args.put(sttCT, g_stt);
            args.put(tenanhCT, g_tenanh);
            db.insert(TABLE_NAME_Ten_anhCT, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    public String appendUPDAE1(String stt, String tenanh) {
        try {
            ContentValues args = new ContentValues();
            //   args.put(no1,xno1);
            //  args.put(ip,xip);
            //  Cursor mCursor=db.query(TABLE_NAME,new String[]{no1},no1+"=?",new String[]{xno1},null,null,null,null);
            //  if(mCursor.getCount()>0){
            db.execSQL("UPDATE " + TABLE_NAME_Ten_anh + " SET " + stt + "='" + stt + "' WHERE tenanh='" + tenanh + "' ");
            return "TRUE";
            //  }else{
            //    db.insert(TABLE_NAME,null,args);
            //     return "TRUE";
            // }
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public String updateDiem(String hm, String ngay, String donvi) {
        try {
            ContentValues args = new ContentValues();
            //   args.put(no1,xno1);
            //  args.put(ip,xip);
            //  Cursor mCursor=db.query(TABLE_NAME,new String[]{no1},no1+"=?",new String[]{xno1},null,null,null,null);
            //  if(mCursor.getCount()>0){
            db.execSQL("UPDATE " + TABLE_NAME_TC_FAA + " SET tc_faa004 ='false',tc_faa012 ='false',tc_faa013 ='false'," +
                    " tc_faa014 ='true',tc_faa015 ='false',tc_faa016 ='false' WHERE tc_faa001='" + hm + "' AND tc_faa002 = '"+ngay+"'" +
                    "AND tc_faa003 = '"+donvi+"' ");
            return "TRUE";
            //  }else{
            //    db.insert(TABLE_NAME,null,args);
            //     return "TRUE";
            // }
        } catch (Exception e) {
            return "FALSE";
        }
    }

    public String appendUPDAE(String key, String l_check, String date, String bp, String tencot) {
        try {
            ContentValues args = new ContentValues();
            db.execSQL("UPDATE " + TABLE_NAME_TC_FAA + " SET " + tencot + "='" + l_check + "' WHERE tc_faa001='" + key + "'  AND tc_faa003='" + bp + "' AND tc_faa002 = '"+ date +"'");
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public String updateGhichu(String hm, String ngay, String donvi, String to, String ghichu) {
        try {
            if (to.equals("ToA")){
                to ="Tổ A";
            }
            if (to.equals("ToB")){
                to="Tổ B";
            }
            if (to.equals("ToC")){
                to="Tổ C";
            }
            if (to.equals("ToD")){
                to="Tổ D";
            }
            if (to.equals("XBL")){
                to="Bến Lức";
            }
            ContentValues args = new ContentValues();
            db.execSQL("UPDATE " + TB_TC_FAR + " SET tc_far006 ='" + ghichu + "' WHERE tc_far001='" + hm + "'  AND tc_far002='" + ngay + "' AND tc_far003='" + donvi + "' AND tc_far004='" + to + "'");
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public void call_upd_tc_faapost (Cursor c_getTc_faa) {
        if (c_getTc_faa.getCount() > 0) {
            c_getTc_faa.moveToFirst();
            for (int i = 0; i < c_getTc_faa.getCount(); i++) {
                String g_tc_faa001 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("tc_faa001"));
                String g_tc_faa002 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("tc_faa002"));
                String g_tc_faa003 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("tc_faa003"));
                String g_tc_faa005 = c_getTc_faa.getString(c_getTc_faa.getColumnIndexOrThrow("tc_faa005"));

                db.execSQL(" UPDATE tc_faa_file SET tc_faapost = 'Y' " +
                        " WHERE tc_faa001 ='" + g_tc_faa001 + "' " +
                        " AND tc_faa002 ='" + g_tc_faa002 + "'" +
                        " AND tc_faa003 = '" + g_tc_faa003 + "'" +
                        " AND tc_faa005 = '" + g_tc_faa005 + "' ");
                c_getTc_faa.moveToNext();
            }
        }
    }

    //public Cursor getAll_tc_faa(String app) {
    public Cursor getAll_tc_faa() {
        try {
            //return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa007,tc_faa008,tc_faa011"

            //+ " FROM " + TABLE_NAME_TC_FAA + "", null);
            return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa008,IFNULL(tc_faa011,0) tc_faa011,tc_faa012,tc_faa013" +
                    ",tc_faa014,tc_faa015,tc_faa016,IFNULL(tc_faa018,0) tc_faa018,tc_faa019"

                    + " FROM " + TABLE_NAME_TC_FAA + " WHERE tc_faa005 is not null and tc_faa011 > 0 and tc_faapost = 'N' ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_tc_faa_new(String g_date,String g_bophan) {
        try {
            String selectQuery = null;
            //return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa007,tc_faa008,tc_faa011"

            //+ " FROM " + TABLE_NAME_TC_FAA + "", null);
            selectQuery = "SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa008,IFNULL(tc_faa011,0) tc_faa011,tc_faa012,tc_faa013" +
                    ",tc_faa014,tc_faa015,tc_faa016,IFNULL(tc_faa018,0) tc_faa018,tc_faa019"

                    + " FROM " + TABLE_NAME_TC_FAA + "," + TABLE_NAME_TC_FBA + " WHERE tc_faa003= tc_fba007 AND tc_faa005 is not null and tc_faa011 > 0 and tc_faapost = 'N'  ";
            if(!g_date.equals(""))
            {
                selectQuery += " AND tc_faa002 = '"+g_date+"' ";
            }
            if(!g_bophan.equals("")){
                selectQuery += " AND tc_fba009 = '"+g_bophan+"' ";
            }
            //return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa008,IFNULL(tc_faa011,0) tc_faa011,tc_faa012,tc_faa013" +
                //    ",tc_faa014,tc_faa015,tc_faa016,IFNULL(tc_faa018,0) tc_faa018,tc_faa019"

                //    + " FROM " + TABLE_NAME_TC_FAA + "," + TABLE_NAME_TC_FBA + " WHERE tc_faa003= tc_fba007 AND tc_faa005 is not null and tc_faa011 > 0 and tc_faapost = 'N' AND tc_faa002 = '"+g_date+"' AND tc_fba009 = '"+g_bophan+"' ", null);
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_tc_far(String g_date,String g_bophan) {
        try {
            String selectQuery = null;
            selectQuery = "SELECT tc_far001,tc_far002,tc_far003,tc_far004,tc_far005,IFNULL(tc_far006,' ') tc_far006 FROM " + TB_TC_FAR + "," + TABLE_NAME_TC_FBA + " WHERE tc_far003= tc_fba007 AND tc_farpost != 'Y'  ";
            //return db.rawQuery("SELECT tc_far001,tc_far002,tc_far003,tc_far004,tc_far005,IFNULL(tc_far006,' ') tc_far006 FROM " + TB_TC_FAR + "," + TABLE_NAME_TC_FBA + " WHERE tc_far003= tc_fba007 AND tc_farpost != 'Y' AND tc_far002 = '"+g_date+"' AND tc_fba009='"+g_bophan+"' ", null);
            if(!g_date.equals(""))
            {
                selectQuery += " AND tc_far002 = '"+g_date+"' ";
            }
            if(!g_bophan.equals("")){
                selectQuery += " AND tc_fba009 = '"+g_bophan+"' ";
            }
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_tc_faa1(String app) {
        try {
            //return db.rawQuery("SELECT  COUNT(*)  as _id ,tc_faa002,tc_faa003"

            //+ " FROM " + TABLE_NAME_TC_FAA + " group by tc_faa003 ", null);
            return db.rawQuery("SELECT  COUNT(*)  as _id ,tc_faa002,tc_faa003,tc_fba009"

                    + " FROM  tc_faa_file,tc_fba_file WHERE tc_faa003=tc_fba007 group by tc_faa003 ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_tc_faa2(String date, String bp, String dk) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE_NAME_TC_FAA + " WHERE tc_faa002='" + date + "' AND tc_faa003='" + bp + "'AND tc_faa010='" + dk + "'";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean getAll_gem(String g_gem01) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            Cursor mCursor = db.query(TABLE_NAME_GEM, new String[]{gem01}, gem01 + "=?", new String[]{g_gem01}, null, null, null, null);
            if (mCursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }

            //String selectQuery = "SELECT count(*) FROM " + TABLE_NAME_GEM + " WHERE gem01 like (% " + g_gem01 + "%) ";
            // return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return false;


        }
    }
    public Cursor getTen_Anh(String g_hm,String g_ngay,String g_donvi) {
        try {
            String selectQuery = "SELECT count(*) as l_count FROM " + TABLE_NAME_Ten_anh + " WHERE key1= '"+g_hm+"' " +
                    " and ngay = '"+g_ngay+"' and bophan = '"+g_donvi+"' ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_gem1() {
        try {
            return db.rawQuery("SELECT  COUNT(*)  as _id, gem01,gem02" + " FROM " + TABLE_NAME_GEM + " WHERE gem01 LIKE 'ABJD%' group by gem01,gem02  ", null);
        } catch (Exception e) {
            return null;
        }
    }

    /*public Cursor getAll_tc_fba() {
        try {
            return db.rawQuery("SELECT  COUNT(*)  as _id, tc_fba007,tc_fba009" + " FROM " + TABLE_NAME_TC_FBA + " group by tc_fba007  ", null);
        } catch (Exception e) {
            return null;
        }
    }*/

    public Cursor getAll_tc_fba() {
        try {
            return db.rawQuery("SELECT  COUNT(*)  as _id, tc_fba007,tc_fba009" + " FROM " + TABLE_NAME_TC_FBA + " WHERE substr(tc_fba007,1,2) not in ('04','05') group by tc_fba007  ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_tc_fbaBL() {
        try {
            return db.rawQuery("SELECT  COUNT(*)  as _id, tc_fba007,tc_fba009" + " FROM " + TABLE_NAME_TC_FBA + " WHERE substr(tc_fba007,1,2) in ('04','05') group by tc_fba007  ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public void delete_table() {
        db.delete(TABLE_NAME_TC_FAA, null, null);
    }

    public void delete_tenhinh(String qry_ngay, String qry_bp) {
        String whereClause_hm0102 = "ngay=? AND bophan=?";
        String[] strings = new String[]{qry_ngay, qry_bp};
        db.delete(TABLE_NAME_Ten_anh, whereClause_hm0102, strings);

    }


    public void delete_tenhinhCT(String qry_ngay, String qry_bp) {
        String whereClause_hm0102 = "ngayCT=? AND bophanCT=?";
        String[] strings = new String[]{qry_ngay, qry_bp};
        db.delete(TABLE_NAME_Ten_anhCT, whereClause_hm0102, strings);
    }

    public void delete_tenhinh_all() {
        db.delete(TABLE_NAME_Ten_anh, null, null);

    }

    public void delete_table_faa_kt(String l_tc_faa001) {
        String where_loggin = "substr(tc_faa001,1,4)=? ";
        String[] strings = new String[]{l_tc_faa001};
        db.delete(TABLE_NAME_TC_FAA, where_loggin, strings);
    }

    public void delete_table1(String qry_ngay, String qry_bp) {
        String whereClause_hm0102 = "tc_faa002=? AND tc_faa003=?";
        String[] strings = new String[]{qry_ngay, qry_bp};
        db.delete(TABLE_NAME_TC_FAA, whereClause_hm0102, strings);
    }

    public void delete_tenanh(String tenanh1) {
        String whereClause_hm0102 = "tenanh=? ";
        String[] strings = new String[]{tenanh1};
        db.delete(TABLE_NAME_Ten_anh, whereClause_hm0102, strings);
    }
    public Boolean getcountAnhpost(String tenanh1) {
        try {
            Cursor mCursor= db.rawQuery("SELECT  tc_far005 " + " FROM " + TB_TC_FAR + " WHERE tc_far005 = '"+tenanh1+"' AND tc_farpost != 'Y'  ", null);
            if (mCursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    public void delete_tenanh_tc_far(String tenanh1) {
        String whereClause_hm0102 = "tc_far005=? ";
        String[] strings = new String[]{tenanh1};
        db.delete(TB_TC_FAR, whereClause_hm0102, strings);
    }

    public void delete_tenanhCT(String tenanh1) {
        String whereClause_hm0102 = "tenanhCT=? ";
        String[] strings = new String[]{tenanh1};
        db.delete(TABLE_NAME_Ten_anhCT, whereClause_hm0102, strings);
    }

    public Cursor checkxemdacochupdanhcua(String ngay, String bp, String key) {
        try {
            return db.rawQuery("SELECT  tc_faa005  FROM " + TABLE_NAME_TC_FAA + " WHERE tc_faa002='" + ngay + "' AND tc_faa003='" + bp + "'AND tc_faa001='" + key + "'  ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor demsttanh(String KEY, String bp, String ngay) {
        try {

            return db.rawQuery("SELECT  tc_faa011 FROM " + TABLE_NAME_TC_FAA + " WHERE tc_faa001='" + KEY + "' AND tc_faa003='" + bp + "' AND tc_faa002='" + ngay + "' ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getstt(String KEY, String bp, String ngay) {
        try {
            return db.rawQuery("SELECT  max(stt+0) AS stt FROM " + TABLE_NAME_Ten_anh + " ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getngay() {
        try {
            return db.rawQuery("SELECT  DISTINCT  tc_faa002 FROM " + TABLE_NAME_TC_FAA + " ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getsttCT(String KEY, String bp, String ngay) {
        try {

            return db.rawQuery("SELECT  max(sttCT) AS stt FROM " + TABLE_NAME_Ten_anhCT + " ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor demsttanhCT(String KEY, String bp, String ngay) {
        try {

            return db.rawQuery("SELECT  tc_faa018 FROM " + TABLE_NAME_TC_FAA + " WHERE tc_faa001='" + KEY + "' AND tc_faa003='" + bp + "' AND tc_faa002='" + ngay + "' ", null);
        } catch (Exception e) {
            return null;
        }
    }

    Cursor demsttanhold(String KEY, String bp, String ngay) {
        try {

            return db.rawQuery("SELECT  tc_faa018 FROM " + TABLE_NAME_TC_FAA + " WHERE tc_faa001='" + KEY + "' AND tc_faa003='" + bp + "' AND tc_faa002='" + ngay + "' ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public String appendUPDAEhinhanh(String key, String l_check, int stt, String date, String bp, String tencothinh, String tencotstt) {
        try {
            ContentValues args = new ContentValues();
            //   args.put(no1,xno1);
            //  args.put(ip,xip);
            //  Cursor mCursor=db.query(TABLE_NAME,new String[]{no1},no1+"=?",new String[]{xno1},null,null,null,null);
            //  if(mCursor.getCount()>0){
            db.execSQL("UPDATE " + TABLE_NAME_TC_FAA + " SET " + tencothinh + "='" + l_check + "'," + tencotstt + "='" + stt + "', tc_faapost = 'N' WHERE tc_faa001='" + key + "'  AND tc_faa003='" + bp + "' AND tc_faa002='" + date + "'");
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

    public Cursor xuathinhanh(String key, String l_ngay, String l_bp) {
        try {
            return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa007,tc_faa008,tc_faa011,tc_faa017,IFNULL(tc_faa018,0) tc_faa018"
                    + " FROM " + TABLE_NAME_TC_FAA + "  WHERE tc_faa002='" + l_ngay + "' AND tc_faa003='" + l_bp + "'AND tc_faa001='" + key + "'", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor lananh(String key, String l_ngay, String l_bp) {
        try {
            return db.rawQuery("SELECT stt,tenanh"

                    + " FROM " + TABLE_NAME_Ten_anh + "  WHERE ngay='" + l_ngay + "' AND bophan='" + l_bp + "'AND key1='" + key + "'", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor xuatghichu(String hm, String ngay, String donvi,String to) {
        try {
            if (to.equals("ToA")){
                to ="Tổ A";
            }
            if (to.equals("ToB")){
                to="Tổ B";
            }
            if (to.equals("ToC")){
                to="Tổ C";
            }
            if (to.equals("ToD")){
                to="Tổ D";
            }
            if (to.equals("XBL")){
                to="Bến Lức";
            }
            return db.rawQuery("SELECT tc_far006 "

                    + " FROM " + TB_TC_FAR + "  WHERE tc_far001='" + hm + "' AND tc_far002 ='" + ngay + "' AND tc_far003='" + donvi + "' AND tc_far004='" + to + "'", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor lananh3(String key, String l_ngay, String l_bp) {
        try {
            return db.rawQuery("SELECT sttCT,tenanhCT"

                    + " FROM " + TABLE_NAME_Ten_anhCT + "  WHERE ngayCT='" + l_ngay + "' AND bophanCT='" + l_bp + "'AND key1CT='" + key + "'", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor lananh1(String key, String l_ngay, String l_bp, int bien) {
        try {
            return db.rawQuery("SELECT (max(stt) - " + bien + " ) as stt,tenanh"

                    + " FROM " + TABLE_NAME_Ten_anh + "  WHERE ngay='" + l_ngay + "' AND bophan='" + l_bp + "'AND key1='" + key + "'  ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor lananh2(String key, String l_ngay, String l_bp) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT stt,tenanh FROM " + TABLE_NAME_Ten_anh + " WHERE ngay='" + l_ngay + "' AND bophan='" + l_bp + "'AND key1='" + key + "' ORDER BY stt  ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }

    }

    public Cursor lananh4(String key, String l_ngay, String l_bp) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT sttCT,tenanhCT FROM " + TABLE_NAME_Ten_anhCT + " WHERE ngayCT='" + l_ngay + "' AND bophanCT='" + l_bp + "'AND key1CT='" + key + "' ORDER BY sttCT  ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }

    }
    public void update_tc_farpost(String image_no, String image_date, String image_dept, String image_to, String image_name) {
        try {
            db.execSQL(" UPDATE tc_far_file SET tc_farpost = 'Y' " +
                    " WHERE tc_far001 ='" + image_no + "' " +
                    " AND tc_far002 ='" + image_date + "'" +
                    " AND tc_far003 = '" + image_dept + "'" +
                    " AND tc_far004 = '" + image_to + "' " +
                    " AND tc_far005 = '" + image_name + "' ");
        } catch (Exception e) {
        }
    }

    public Boolean KT_ndhinh(String key, String l_bp, String xsomay, String l_ngay) {
        try {
            int count = 0;
            String selectQuery = "SELECT count(*) as dem FROM " + TABLE_NAME_TC_FAA + " WHERE tc_faa001='" + key + "' AND tc_faa003='" + l_bp + "' AND tc_faa002='" + l_ngay + "' AND (tc_faa011 >0 or tc_faa018 >0 ) ";
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

    public Boolean KT_fia_up_sig01(String is_soxe, String is_bophan, String is_ngay) {
        try {
            int count = 0;
            //String selectQuery = "SELECT count(*) as dem FROM " + TABLE_NAME_FIA_UP_SIG + " WHERE somay_sig='" + is_soxe + "' AND mabp_sig='" + is_bophan + "' AND ngay_sig='" + is_ngay + "' ";
            String selectQuery = "SELECT count(*) as dem FROM " + TABLE_NAME_FIA_UP_SIG01 + " WHERE mabp_sig='" + is_bophan + "' and ngay_sig='" + is_ngay + "' ";
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

    public long ins_sig(
            String is_ngay, String is_soxe, String is_bophan, String is_tenbp, String is_ghichu, String is_manv, String is_sogio, String is_tenhinh) {
        try {
            int count = 0;
            ContentValues argsA = new ContentValues();
            Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_FIA_UP_SIG01 + " WHERE manv_sig= '"+is_manv+"' AND mabp_sig='" + is_bophan + "' AND ngay_sig='" + is_ngay + "'", null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count <= 0) {
                //argsA.put(somay_sig, is_soxe);
                argsA.put(mabp_sig, is_bophan);
                argsA.put(tebp_sig, is_tenbp);
                argsA.put(ngay_sig, is_ngay);
                argsA.put(ghichu_sig, is_ghichu);
                argsA.put(manv_sig, is_manv);
                argsA.put(sogio_sig, is_sogio);
                argsA.put(tenhinh_sig, is_tenhinh);
                db.insert(TABLE_NAME_FIA_UP_SIG01, null, argsA);
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    public Cursor getAll_fiaupnot_sig(String tenxe) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT ' ' AS somay_sig,mabp_sig,tebp_sig,ngay_sig,ghichu_sig,'Đã chuyển' AS trangthai_sig,manv_sig,IFNULL(sogio_sig,' ') sogio_sig,IFNULL(tenhinh_sig,' ') tenhinh_sig FROM fia_up_sigkt01_file " +
                    " WHERE trangthai_sig is null OR trangthai_sig='Chưa chuyển' " +
                    " order by mabp_sig,ngay_sig ";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public void del_fiaup_sig() {
        String where_loggin = "trangthai_sig=? ";
        String[] strings = new String[]{"Đã chuyển"};
        //String[] strings = new String[]{"Chưa chuyển"};
        db.delete(TABLE_NAME_FIA_UP_SIG01, where_loggin, strings);
    }

    public static long update_tc_fiaup_sig(String ud_soxe, String ud_bophan, String ud_ngay, String ud_trangthai) {
        try {
            db.execSQL("UPDATE " + TABLE_NAME_FIA_UP_SIG01 + " SET trangthai_sig ='" + ud_trangthai + "'" +
                    " WHERE mabp_sig='" + ud_bophan + "' AND ngay_sig='" + ud_ngay + "' ");
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public static long update_tc_fiaup1_sig() {
        try {
            db.execSQL("UPDATE " + TABLE_NAME_FIA_UP_SIG01 + " SET trangthai_sig ='Chưa chuyển'" +
                    " WHERE trangthai_sig IS NULL OR trangthai_sig ='Chưa chuyển'");
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public Cursor getAll_Sigture(String tenxe, String bophan, String ngay, String manv) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT manv_sig,sogio_sig,ghichu_sig,tenhinh_sig FROM fia_up_sigkt01_file WHERE ";
            if (!(bophan == null)) {
                selectQuery = selectQuery + " mabp_sig = '" + bophan + "' AND ngay_sig= '" + ngay + "' AND manv_sig= '" + manv + "' ";
            }
            selectQuery = selectQuery + " ORDER BY manv_sig ";
            //String selectQuery = "SELECT distinct fiaud03 FROM fia_file WHERE ta_fia02_1='" + tenxe + "' ORDER BY fiaud03";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getDepartmetData(String userFactory) {
        String selectQuery = " SELECT COUNT(*) AS _id,tc_fba007,tc_fba009,date('now') AS ngaysig,(select GROUP_CONCAT(manv_sig, ', ') AS manv_sig from fia_up_sigkt01_file where mabp_sig = tc_fba007 and ngay_sig = date('now')) as manv_sig FROM tc_fba_file  ";
        if(userFactory.equals("DH")){
            selectQuery += " WHERE SUBSTR(tc_fba007,1,2) NOT IN ('04','05') GROUP BY tc_fba007,tc_fba009 ORDER BY tc_fba007 ";
        }else {
            selectQuery += " WHERE SUBSTR(tc_fba007,1,2) IN ('04','05') GROUP BY tc_fba007,tc_fba009 ORDER BY tc_fba007 ";
        }

        return db.rawQuery(selectQuery, null);
    }
    public Cursor getAll_datekt() {
        try {
            return db.rawQuery("SELECT DISTINCT tc_faa002 FROM " + TABLE_NAME_TC_FAA + " WHERE tc_faa005 is not null and tc_faa011 > 0  ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_bophan() {
        try {
            return db.rawQuery("SELECT DISTINCT tc_faa003,tc_fba009 FROM " + TABLE_NAME_TC_FAA + "," + TABLE_NAME_TC_FBA + " WHERE tc_faa003 = tc_fba007 AND  tc_faa005 is not null and tc_faa011 > 0  ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_kyten(String mabp, String tenbp,String ngay) {
        try {
            return db.rawQuery("SELECT manv_sig, sogio_sig, ghichu_sig from " + TABLE_NAME_FIA_UP_SIG01 + " where mabp_sig = '"+mabp+"' and tebp_sig = '"+tenbp+"' and ngay_sig = '"+ngay+"'  ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Integer check_kyten(String manv, String mabp, String tenbp,String ngay) {
        try {
            String selectQuery = null;
            selectQuery = "SELECT count(*)  from " + TABLE_NAME_FIA_UP_SIG01 + " where manv_sig = '"+manv+"' AND mabp_sig = '"+mabp+"' and tebp_sig = '"+tenbp+"' and ngay_sig = '"+ngay+"'  ";
            Cursor a = db.rawQuery(selectQuery, null);
            a.moveToFirst();
            Integer count = a.getInt(0);
            return count;
        } catch (Exception e) {
            return null;
        }
    }
    public void delete_kyten(String manv, String mabp, String tenbp,String ngay) {
        String whereClause_kyten = "manv_sig=? AND mabp_sig=? AND tebp_sig=? AND ngay_sig=?";
        String[] strings = new String[]{manv, mabp,tenbp,ngay};
        db.delete(TABLE_NAME_FIA_UP_SIG01, whereClause_kyten, strings);
    }

    public static long update_GhiChuKT(String manv, String ghichu, String bophan, String ngay) {
        try {
            db.execSQL("UPDATE " + TABLE_NAME_FIA_UP_SIG01 + " SET ghichu_sig ='" + ghichu + "' " +
                    " WHERE manv_sig = '" + manv + "' AND mabp_sig='" + bophan + "' AND ngay_sig='" + ngay + "' ");
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
    public static long update_SogioKT(String manv, String sogio, String bophan, String ngay) {
        try {
            db.execSQL("UPDATE " + TABLE_NAME_FIA_UP_SIG01 + " SET sogio_sig ='" + sogio + "' " +
                    " WHERE manv_sig = '" + manv + "' AND mabp_sig='" + bophan + "' AND ngay_sig='" + ngay + "' ");
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }



}
