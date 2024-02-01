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
    private final static String tenhinh = "tenhinh"; //Tên hình
    private final static String soluong = "soluong"; //Số lượng hình
    private final static String ghichu = "ghichu"; //
    private final static String tc_facpost = "tc_facpost"; //trang thái


    //KT02(S)
    private final static String TABLE_NAME_FIA_UP = "fia_up_file";
    private final static String somay_up = "somay_up"; //Số máy
    private final static String mabp_up = "mabp_up"; //Bộ phận
    private final static String tebp_up = "tebp_up"; //Tên bộ phận
    private final static String ngay_up = "ngay_up"; //Ngày
    private final static String ghichu_up = "ghichu_up"; //Ghi chú
    private final static String trangthai_up = "trangthai_up"; //Trạng thái

    String CREATE_TABLE_NAME_FIA_UP = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FIA_UP + " ("
            + somay_up + " TEXT," + mabp_up + " TEXT," + tebp_up + " TEXT,"
            + ngay_up + " TEXT," + ghichu_up + " TEXT," + trangthai_up + " TEXT )";
    //KT02(E)

    //KT02(S)sigture
    private final static String TABLE_NAME_FIA_UP_SIG = "fia_up_sig_file";
    private final static String somay_sig = "somay_sig"; //Số máy
    private final static String mabp_sig = "mabp_sig"; //Bộ phận
    private final static String tebp_sig = "tebp_sig"; //Tên bộ phận
    private final static String ngay_sig = "ngay_sig"; //Ngày
    private final static String ghichu_sig = "ghichu_sig"; //Ghi chú
    private final static String trangthai_sig = "trangthai_sig"; //Trạng thái
    private final static String manv_sig = "manv_sig"; //Số thẻ
    private final static String sogio_sig = "sogio_sig"; //Số giờ hoạt động
    private final static String tenhinh_sig = "tenhinh_sig"; //Số giờ hoạt động

    String TABLE_NAME_TC_FAR_KT02 = "tc_far_file_kt02";
    String tc_far001 = "tc_far001";//Mã hạng muc
    String tc_far002 = "tc_far002";//Ngày
    String tc_far003 = "tc_far003";//Đơn vị
    String tc_far012 = "tc_far012";//Số xe
    String tc_far005 = "tc_far005";//Tên hình
    String tc_far006 = "tc_far006";//Ghi chu
    String tc_far016 = "tc_far016";//Ngày dự kiến ct
    String tc_farpost = "tc_farpost";//

    String CREATE_TABLE_NAME_TC_FAR_KT02 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAR_KT02 + " ("
            + tc_far001 + " TEXT," + tc_far002 + " TEXT," + tc_far003 + " TEXT," + tc_far012 + " TEXT," +
            "" + tc_far005 + " TEXT ," + tc_far006 + " TEXT," + tc_far016 + " TEXT," + tc_farpost + " TEXT)";

    String CREATE_TABLE_NAME_FIA_UP_SIG = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FIA_UP_SIG + " ("
            + somay_sig + " TEXT," + mabp_sig + " TEXT," + tebp_sig + " TEXT,"
            + ngay_sig + " TEXT," + ghichu_sig + " TEXT," + trangthai_sig + " TEXT," + manv_sig + " TEXT," + sogio_sig + " TEXT," + tenhinh_sig +" TEXT )";
    //KT02(E)

    //Bảng ảo lưu biểu KT02 (S)
    String CREATE_TABLE_NAME_TC_FAC_KT02 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAC_KT02 + " ("
            + tc_fac004 + " TEXT," + tc_fac009 + " TEXT,"
            + checkbox1 + " TEXT ," + checkbox2 + " TEXT," + checkbox3 + " TEXT," + checkbox4 + " TEXT,"
            + checkbox5 + " TEXT," + checkbox6 + " TEXT," + user + " TEXT," + ngay + " TEXT," + somay + " TEXT,"
            + tc_fac003 + " TEXT," + tc_fac005 + " TEXT," + tc_fac006 + " TEXT," + tenhinh + " TEXT," + soluong + " TEXT," + ghichu + " TEXT," + tc_facpost + " TEXT )";
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
            db.execSQL(CREATE_TABLE_NAME_FIA_UP);
            db.execSQL(CREATE_TABLE_NAME_FIA_UP_SIG);
            db.execSQL(CREATE_TABLE_NAME_TC_FAR_KT02);
        } catch (Exception e) {

        }


    }

    public void close() {
        try {
            final String DROP_TABLE_NAME_TC_FAC_KT02 = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_FAC_KT02;
            db.execSQL(DROP_TABLE_NAME_TC_FAC_KT02);
            final String DROP_TABLE_NAME_FIA_UP = "DROP TABLE IF EXISTS " + TABLE_NAME_FIA_UP;
            db.execSQL(DROP_TABLE_NAME_FIA_UP);
            final String DROP_TABLE_NAME_FIA_UP_SIG = "DROP TABLE IF EXISTS " + TABLE_NAME_FIA_UP_SIG;
            db.execSQL(DROP_TABLE_NAME_FIA_UP_SIG);
            final String DROP_TABLE_NAME_TC_FAR_KT02 = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_FAR_KT02;
            db.execSQL(DROP_TABLE_NAME_TC_FAR_KT02);
            db.close();
        } catch (Exception e) {

        }
    }


    public void delete_table() {
        db.delete(TABLE_NAME_TC_FAC_KT02, null, null);
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
                argsA.put(tc_facpost, "N");
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


    public Cursor getAll_lvQuery(String tenxe) {
        String selectQuery = " select COUNT(*) AS _id ,ngay,somay,user" +
                " from (select ngay,somay,user, " +
                "      (select count(checkbox1)  from tc_fac_table_kt02 where checkbox1='true' and ngay=a.ngay and somay =a.somay and user = a.user ) AS loai1," +
                "      (select count(checkbox2)  from tc_fac_table_kt02 where checkbox2='true' and ngay=a.ngay and somay =a.somay and user = a.user ) AS loai2," +
                "      (select count(checkbox3)  from tc_fac_table_kt02 where checkbox3='true' and ngay=a.ngay and somay =a.somay and user = a.user ) AS loai3," +
                "      (select count(checkbox4)  from tc_fac_table_kt02 where checkbox4='true' and ngay=a.ngay and somay =a.somay and user = a.user ) AS loai4," +
                "      (select count(checkbox5)  from tc_fac_table_kt02 where checkbox5='true' and ngay=a.ngay and somay =a.somay and user = a.user ) AS loai5," +
                "      (select count(checkbox6)  from tc_fac_table_kt02 where checkbox6='true' and ngay=a.ngay and somay =a.somay and user = a.user ) AS loai6" +
                "       from tc_fac_table_kt02 a ,fia_file where a.somay=fiaud03 and fia15=a.user and ta_fia02_1='" + tenxe + "' " +
                "       GROUP BY ngay,somay,user ) " +
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

    public void delete_table_fac_kt(String l_tc_fac004, String somay) {
        String where_loggin = "substr(tc_fac004,1,4)=? AND somay=?";
        String[] strings = new String[]{l_tc_fac004,somay};
        db.delete(TABLE_NAME_TC_FAC_KT02, where_loggin, strings);
    }

    public void del_fiaup() {
        String where_loggin = "trangthai_up=? ";
        String[] strings = new String[]{"Đã chuyển"};
        //String[] strings = new String[]{"Chưa chuyển"};
        db.delete(TABLE_NAME_FIA_UP , where_loggin, strings);
    }

    public void del_fiaup_sig() {
        String where_loggin = "trangthai_sig=? ";
        String[] strings = new String[]{"Đã chuyển"};
        //String[] strings = new String[]{"Chưa chuyển"};
        db.delete(TABLE_NAME_FIA_UP_SIG , where_loggin, strings);
    }

    public long ins_tc_fia_file(
            String is_ngay, String is_soxe, String is_bophan,String is_tenbp, String is_ghichu) {
        try {
            int count = 0;
            ContentValues argsA = new ContentValues();
            Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_FIA_UP + " WHERE somay_up='" + is_soxe + "' AND mabp_up='" + is_bophan + "' AND ngay_up='" + is_ngay + "'", null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count <= 0) {
                argsA.put(somay_up, is_soxe);
                argsA.put(mabp_up, is_bophan);
                argsA.put(tebp_up, is_tenbp);
                argsA.put(ngay_up, is_ngay);
                argsA.put(ghichu_up, is_ghichu);
                db.insert(TABLE_NAME_FIA_UP, null, argsA);
                return 1;
            }else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }

    /*public long ins_sig(
            String is_ngay, String is_soxe, String is_bophan,String is_tenbp, String is_ghichu, String is_manv, String is_sogio, String is_tenhinh) {
        try {
            int count = 0;
            ContentValues argsA = new ContentValues();
            Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_FIA_UP_SIG + " WHERE somay_sig='" + is_soxe + "' AND mabp_sig='" + is_bophan + "' AND ngay_sig='" + is_ngay + "'", null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count <= 0) {
                argsA.put(somay_sig, is_soxe);
                argsA.put(mabp_sig, is_bophan);
                argsA.put(tebp_sig, is_tenbp);
                argsA.put(ngay_sig, is_ngay);
                argsA.put(ghichu_sig, is_ghichu);
                argsA.put(manv_sig, is_manv);
                argsA.put(sogio_sig, is_sogio);
                argsA.put(tenhinh_sig, is_tenhinh);
                db.insert(TABLE_NAME_FIA_UP_SIG, null, argsA);
                return 1;
            }else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }*/

    public long ins_sig(
            String is_ngay, String is_soxe, String is_bophan,String is_tenbp, String is_ghichu, String is_manv, String is_sogio, String is_tenhinh) {
        try {
            int count = 0;
            ContentValues argsA = new ContentValues();

                argsA.put(somay_sig, is_soxe);
                argsA.put(mabp_sig, is_bophan);
                argsA.put(tebp_sig, is_tenbp);
                argsA.put(ngay_sig, is_ngay);
                argsA.put(ghichu_sig, is_ghichu);
                argsA.put(manv_sig, is_manv);
                argsA.put(sogio_sig, is_sogio);
                argsA.put(tenhinh_sig, is_tenhinh);
                db.insert(TABLE_NAME_FIA_UP_SIG, null, argsA);
                return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public Boolean KT_fia_up(String is_soxe, String is_bophan) {
        try {
            int count = 0;
            String selectQuery = "SELECT count(*) as dem FROM " + TABLE_NAME_FIA_UP + " WHERE somay_up='" + is_soxe + "' AND mabp_up='" + is_bophan + "' ";
            Cursor mCount=db.rawQuery(selectQuery, null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count > 0) {
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean KT_fia_up_sig(String is_soxe, String is_bophan) {
        try {
            int count = 0;
            //String selectQuery = "SELECT count(*) as dem FROM " + TABLE_NAME_FIA_UP_SIG + " WHERE somay_sig='" + is_soxe + "' AND mabp_sig='" + is_bophan + "' AND ngay_sig='" + is_ngay + "' ";
            String selectQuery = "SELECT count(*) as dem FROM " + TABLE_NAME_FIA_UP_SIG + " WHERE somay_sig='" + is_soxe + "' AND mabp_sig='" + is_bophan + "' ";
            Cursor mCount=db.rawQuery(selectQuery, null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count > 0) {
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean KT_ndhinh(String key,String l_bp, String xsomay,String l_ngay) {
        try {
            int count = 0;
            String selectQuery = "SELECT count(*) as dem FROM " + TABLE_NAME_TC_FAC_KT02 + " WHERE tc_fac004='" + key + "' AND user='" + l_bp + "' AND somay='" + xsomay  + "' AND ngay='" + l_ngay + "' AND soluong > 0";
            Cursor mCount=db.rawQuery(selectQuery, null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count > 0) {
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public Cursor getAll_fiaup(String tenxe) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT count(*) AS _id,fiaud03,fia15,fka02,ngay_up,ghichu_up,trangthai_up FROM fia_up_file,fia_file WHERE somay_up=fiaud03 AND mabp_up=fia15 AND ta_fia02_1='" + tenxe + "'" +
                                 " AND replace(ngay_up, '/', '-') = date('now') group by fiaud03,fia15,fka02,ngay_up " +
                                 " order by fiaud03,fia15,ngay_up ";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }
    public static long update_tc_fiaup(String ud_soxe, String ud_bophan,String ud_ngay,String ud_trangthai) {
        try {
            db.execSQL("UPDATE " + TABLE_NAME_FIA_UP + " SET trangthai_up ='" + ud_trangthai + "'" +
                    " WHERE somay_up='" + ud_soxe + "' AND mabp_up='" + ud_bophan + "' AND ngay_up='" + ud_ngay + "' ");
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public static long update_tc_fiaup_sig(String ud_soxe, String ud_bophan,String ud_ngay,String ud_trangthai) {
        try {
            db.execSQL("UPDATE " + TABLE_NAME_FIA_UP_SIG + " SET trangthai_sig ='" + ud_trangthai + "'" +
                    " WHERE somay_sig='" + ud_soxe + "' AND mabp_sig='" + ud_bophan + "' AND ngay_sig='" + ud_ngay + "' ");
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public static long update_tc_fiaup1() {
        try {
            db.execSQL("UPDATE " + TABLE_NAME_FIA_UP + " SET trangthai_up ='Chưa chuyển'" +
                    " WHERE trangthai_up IS NULL OR trangthai_up ='Chưa chuyển'");
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public static long update_tc_fiaup1_sig() {
        try {
            db.execSQL("UPDATE " + TABLE_NAME_FIA_UP_SIG + " SET trangthai_sig ='Chưa chuyển'" +
                    " WHERE trangthai_sig IS NULL OR trangthai_sig ='Chưa chuyển'");
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public Cursor getAll_fiaupnot(String tenxe) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT fiaud03,fia15,fka02,ngay_up FROM fia_up_file,fia_file " +
                    " WHERE somay_up=fiaud03 AND mabp_up=fia15 AND (trangthai_up is null OR trangthai_up='Chưa chuyển') AND ta_fia02_1='" + tenxe + "' " +
                    " order by fiaud03,fia15,ngay_up ";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_fiaupnot_sig(String tenxe) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT DISTINCT somay_sig,mabp_sig,tebp_sig,ngay_sig,ghichu_sig,'Đã chuyển' AS trangthai_sig,manv_sig,IFNULL(sogio_sig,' ') sogio_sig,IFNULL(tenhinh_sig,' ') tenhinh_sig FROM fia_up_sig_file,fia_file " +
                    " WHERE somay_sig=fiaud03 AND mabp_sig=fia15 AND (trangthai_sig is null OR trangthai_sig='Chưa chuyển') AND ta_fia02_1='" + tenxe + "' " +
                    " order by somay_sig,mabp_sig,ngay_sig ";

            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public String appendUPDAEhinhanh(String key,String date,String bp,String xsomay,String tencothinh,String tencotstt,String l_check,int stt){
        try{
            ContentValues args=new ContentValues();
            //   args.put(no1,xno1);
            //  args.put(ip,xip);
            //  Cursor mCursor=db.query(TABLE_NAME,new String[]{no1},no1+"=?",new String[]{xno1},null,null,null,null);
            //  if(mCursor.getCount()>0){
            db.execSQL("UPDATE " + TABLE_NAME_TC_FAC_KT02 + " SET "+tencothinh+"='"+l_check+"',"+tencotstt+"='"+stt+"' " +
                    " WHERE tc_fac004='" + key + "' AND user='" + bp + "' AND somay='" + xsomay  + "' AND ngay='" + date + "'");
            return "TRUE";
            //  }else{
            //    db.insert(TABLE_NAME,null,args);
            //     return "TRUE";
            // }
        }catch (Exception e){
            return "FALSE";
        }
    }
    public Cursor demsttanh(String KEY, String bp, String ngay, String xsomay) {
        try {
            return db.rawQuery("SELECT  soluong FROM " + TABLE_NAME_TC_FAC_KT02 + " WHERE tc_fac004='" + KEY + "' AND user='" + bp + "' AND somay='" + xsomay  + "' AND ngay='" + ngay + "' ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public
    Cursor xuathinhanh(String key,String l_ngay,String l_bp,String xsomay) {
        try {
            return db.rawQuery("SELECT * "

                    + " FROM " + TABLE_NAME_TC_FAR_KT02 + "  WHERE tc_far001='" + key + "' AND tc_far003='" + l_bp + "' AND tc_far012='" + xsomay  + "' AND tc_far002='" + l_ngay + "'", null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor Soxe(String tenxe) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT DISTINCT somay FROM tc_fac_table_kt02 WHERE substr(tc_fac004,1,4)='" + tenxe + "' ORDER BY 1";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_Sigture(String tenxe, String bophan, String ngay, String manv) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT manv_sig,sogio_sig,ghichu_sig,tenhinh_sig FROM fia_up_sig_file WHERE somay_sig='" + tenxe + "' ";
            if (!(bophan == null)){
                selectQuery=selectQuery + " AND mabp_sig = '" + bophan + "' AND ngay_sig= '" + ngay + "' AND manv_sig= '" + manv + "' ";
            }
            selectQuery=selectQuery + " ORDER BY manv_sig ";
            //String selectQuery = "SELECT distinct fiaud03 FROM fia_file WHERE ta_fia02_1='" + tenxe + "' ORDER BY fiaud03";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }
    public
    Cursor getngay() {
        try {

            return db.rawQuery("SELECT  DISTINCT  ngay FROM " + TABLE_NAME_TC_FAC_KT02+ " ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public String updateGhichu(String hm, String ngay, String donvi, String soxe, String ghichu) {
        try {
            ContentValues args = new ContentValues();
            db.execSQL("UPDATE " + TABLE_NAME_TC_FAR_KT02 + " SET tc_far006 ='" + ghichu + "' WHERE tc_far001='" + hm + "'  AND tc_far002='" + ngay + "' AND tc_far003='" + donvi + "' AND tc_far012='" + soxe + "' ");
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public Boolean getcountAnhpost(String tenanh1) {
        try {
            Cursor mCursor= db.rawQuery("SELECT  tc_far005 " + " FROM " + TABLE_NAME_TC_FAR_KT02 + " WHERE tc_far005 = '"+tenanh1+"' AND tc_farpost != 'Y'  ", null);
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
        db.delete(TABLE_NAME_TC_FAR_KT02, whereClause_hm0102, strings);
    }
    public Cursor demsttanh(String KEY, String bp, String ngay) {
        try {

            return db.rawQuery("SELECT  soluong FROM " + TABLE_NAME_TC_FAC_KT02 + " WHERE tc_fac004='" + KEY + "' AND user='" + bp + "' AND ngay='" + ngay + "' ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getTen_Anh(String g_hm,String g_ngay,String g_donvi,String g_soxe) {
        try {
            String selectQuery = "SELECT count(*) as l_count FROM " + TABLE_NAME_TC_FAR_KT02 + " WHERE tc_far001= '"+g_hm+"' " +
                    " and tc_far002 = '"+g_ngay+"' and tc_far003 = '"+g_donvi+"' and tc_far012 = '"+g_soxe+"' ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getstt(String KEY, String bp, String ngay) {
        try {
            return db.rawQuery("SELECT  max(stt+0) AS stt FROM " + TABLE_NAME_TC_FAR_KT02 + " ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public String ins_img_tc_far(String g_key, String g_ngay, String g_bp, String g_soxe, String g_tenanh) {
        try {
            ContentValues args = new ContentValues();
            args.put("tc_far001", g_key);
            args.put("tc_far002", g_ngay);
            args.put("tc_far003", g_bp);
            args.put("tc_far012", g_soxe);
            args.put("tc_far005", g_tenanh);
            args.put("tc_farpost", "N");

            db.insert(TABLE_NAME_TC_FAR_KT02, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public Cursor xuatghichu(String hm, String ngay, String donvi,String soxe) {
        try {
            return db.rawQuery("SELECT tc_far006 "

                    + " FROM " + TABLE_NAME_TC_FAR_KT02 + "  WHERE tc_far001='" + hm + "' AND tc_far002 ='" + ngay + "' AND tc_far003='" + donvi + "' AND tc_far012='" + soxe + "'", null);
        } catch (Exception e) {
            return null;
        }
    }
    public String updateDiem(String hm, String ngay, String donvi,String soxe) {
        try {
            ContentValues args = new ContentValues();
            db.execSQL("UPDATE " + TABLE_NAME_TC_FAC_KT02 + " SET checkbox1 ='false',checkbox2 ='false',checkbox3 ='false'," +
                    " checkbox4 ='true',checkbox5 ='false',checkbox6 ='false' WHERE tc_fac004='" + hm + "' AND ngay = '"+ngay+"' " +
                    " AND user = '"+donvi+"' AND somay = '"+soxe+"' ");
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public Cursor getAll_datekt() {
        try {
            return db.rawQuery("SELECT DISTINCT ngay FROM " + TABLE_NAME_TC_FAC_KT02 + " WHERE soluong > 0  ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_bophan() {
        try {
            return db.rawQuery("SELECT DISTINCT user,fka02 FROM " + TABLE_NAME_TC_FAC_KT02 + ",fia_file WHERE user = fia15 AND soluong > 0  ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_tc_fac_new(String g_date,String g_bophan,String tenxe) {
        try {
            String selectQuery = null;
            selectQuery = "SELECT  tc_fac_file.tc_fac001 as tc_fac001,tc_fac_file.tc_fac002 as tc_fac002,tc_fac_file.tc_fac003 as tc_fac003,tc_fac_file.tc_fac004 as tc_fac004,tc_fac_table_kt02.tc_fac005 as tc_fac005 ,tc_fac_file.tc_fac006 as tc_fac006 ,tc_fac_table_kt02.checkbox1 as checkbox1,tc_fac_table_kt02.checkbox2 as checkbox2," +
                    "tc_fac_table_kt02.checkbox3 as checkbox3,tc_fac_table_kt02.checkbox4 as checkbox4,tc_fac_table_kt02.checkbox5 as checkbox5,tc_fac_table_kt02.checkbox6 as checkbox6, IFNULL(tc_fac_table_kt02.tc_fac009,' ') as tc_fac009," +
                    "tc_fac_table_kt02.user as user,tc_fac_table_kt02.ngay as ngay,tc_fac_table_kt02.somay as somay " +
                    " FROM tc_fac_file," + TABLE_NAME_TC_FAC_KT02 + ",fia_file " +
                    " WHERE tc_fac_table_kt02.tc_fac004=tc_fac_file.tc_fac004 and somay=fiaud03 and fia15=user and ta_fia02_1='" + tenxe + "' and (checkbox4 = 'true' or checkbox5 = 'true' or checkbox6 = 'true')  and tc_facpost = 'N' ";

            if(!g_date.equals(""))
            {
                selectQuery += " AND ngay = '"+g_date+"' ";
            }
            if(!g_bophan.equals("")){
                selectQuery += " AND fka02 = '"+g_bophan+"' ";
            }
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }
    public Cursor getAll_tc_far(String g_date,String g_bophan,String tenxe) {
        try {
            String selectQuery = null;
            selectQuery = "SELECT tc_far001,tc_far002,tc_far003,tc_far012,tc_far005,IFNULL(tc_far006,' ') tc_far006 FROM " + TABLE_NAME_TC_FAR_KT02 + ",fia_file " +
                    " WHERE tc_far012=fiaud03 and fia15=tc_far003 and ta_fia02_1='" + tenxe + "' AND tc_farpost != 'Y'   ";

            if(!g_date.equals(""))
            {
                selectQuery += " AND tc_far002 = '"+g_date+"' ";
            }
            if(!g_bophan.equals("")){
                selectQuery += " AND fka02 = '"+g_bophan+"' ";
            }
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }
    public void call_upd_tc_facpost (Cursor c_getTc_fac) {
        if (c_getTc_fac.getCount() > 0) {
            c_getTc_fac.moveToFirst();
            for (int i = 0; i < c_getTc_fac.getCount(); i++) {
                String g_tc_fac004 = c_getTc_fac.getString(c_getTc_fac.getColumnIndexOrThrow("tc_fac004"));
                String g_ngay = c_getTc_fac.getString(c_getTc_fac.getColumnIndexOrThrow("ngay"));
                String g_user = c_getTc_fac.getString(c_getTc_fac.getColumnIndexOrThrow("user"));
                String g_somay = c_getTc_fac.getString(c_getTc_fac.getColumnIndexOrThrow("somay"));

                db.execSQL(" UPDATE tc_fac_table_kt02 SET tc_facpost = 'Y' " +
                        " WHERE tc_fac004 ='" + g_tc_fac004 + "' " +
                        " AND ngay ='" + g_ngay + "'" +
                        " AND user = '" + g_user + "'" +
                        " AND somay = '" + g_somay + "' ");
                c_getTc_fac.moveToNext();
            }
        }
    }
    public void update_tc_farpost(String image_no, String image_date, String image_dept, String image_somay, String image_name) {
        try {
            db.execSQL(" UPDATE tc_far_file_kt02 SET tc_farpost = 'Y' " +
                    " WHERE tc_far001 ='" + image_no + "' " +
                    " AND tc_far002 ='" + image_date + "'" +
                    " AND tc_far003 = '" + image_dept + "'" +
                    " AND tc_far012 = '" + image_somay + "' " +
                    " AND tc_far005 = '" + image_name + "' ");
        } catch (Exception e) {
        }
    }
    public Cursor getDepartmetData(String tenxe,String bophan,String ngay) {
        String selectQuery = " select count(*) AS _id,fiaud03,fia15,fka02,'" + ngay + "' AS ngaysig, " +
                "(select GROUP_CONCAT(manv_sig, ', ') AS manv_sig from fia_up_sig_file where fia15 = mabp_sig " +
                "AND fiaud03 = somay_sig and ngay_sig = date('now') AND ta_fia02_1='" + tenxe + "'  )  as manv_sig from fia_file " +
                " where ta_fia02_1='" + tenxe + "' ";
        if (!(bophan == null)) {
            selectQuery = selectQuery + " AND Substr(fia15,1,1) = '" + bophan + "' ";
        }
        selectQuery = selectQuery + " group by fia15,fiaud03,fka02 order by fiaud03,fia15 ";

        return db.rawQuery(selectQuery, null);
    }
    public Boolean KT_fia_up_sig01(String is_soxe, String is_bophan, String is_ngay) {
        try {
            int count = 0;
            String selectQuery = "SELECT count(*) as dem FROM " + TABLE_NAME_FIA_UP_SIG + " WHERE somay_sig='" + is_soxe + "' AND mabp_sig='" + is_bophan + "' AND ngay_sig='" + is_ngay + "' ";
            //String selectQuery = "SELECT count(*) as dem FROM " + TABLE_NAME_FIA_UP_SIG + " WHERE mabp_sig='" + is_bophan + "' and ngay_sig='" + is_ngay + "' ";
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
    public Cursor getAll_kyten(String somay,String mabp, String tenbp,String ngay) {
        try {
            return db.rawQuery("SELECT manv_sig, sogio_sig, ghichu_sig from " + TABLE_NAME_FIA_UP_SIG + " where somay_sig = '"+somay+"' AND mabp_sig = '"+mabp+"' and tebp_sig = '"+tenbp+"' and ngay_sig = '"+ngay+"'  ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public Integer check_kyten(String somay,String manv, String mabp, String tenbp,String ngay) {
        try {
            String selectQuery = null;
            selectQuery = "SELECT count(*)  from " + TABLE_NAME_FIA_UP_SIG + " where somay_sig = '"+somay+"' AND manv_sig = '"+manv+"' AND mabp_sig = '"+mabp+"' and tebp_sig = '"+tenbp+"' and ngay_sig = '"+ngay+"'  ";
            Cursor a = db.rawQuery(selectQuery, null);
            a.moveToFirst();
            Integer count = a.getInt(0);
            return count;
        } catch (Exception e) {
            return null;
        }
    }
    public void delete_kyten(String somay,String manv, String mabp, String tenbp,String ngay) {
        String whereClause_kyten = "somay_sig=? AND manv_sig=? AND mabp_sig=? AND tebp_sig=? AND ngay_sig=?";
        String[] strings = new String[]{somay,manv, mabp,tenbp,ngay};
        db.delete(TABLE_NAME_FIA_UP_SIG, whereClause_kyten, strings);
    }

    public static long update_GhiChuKT(String somay,String manv, String ghichu, String bophan, String ngay) {
        try {
            db.execSQL("UPDATE " + TABLE_NAME_FIA_UP_SIG + " SET ghichu_sig ='" + ghichu + "' " +
                    " WHERE somay_sig = '"+somay+"' AND manv_sig = '" + manv + "' AND mabp_sig='" + bophan + "' AND ngay_sig='" + ngay + "' ");
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
    public static long update_SogioKT(String somay,String manv, String sogio, String bophan, String ngay) {
        try {
            db.execSQL("UPDATE " + TABLE_NAME_FIA_UP_SIG + " SET sogio_sig ='" + sogio + "' " +
                    " WHERE somay_sig = '"+somay+"' AND manv_sig = '" + manv + "' AND mabp_sig='" + bophan + "' AND ngay_sig='" + ngay + "' ");
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}