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

    String CREATE_TABLE_NAME_FIA_UP_SIG = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FIA_UP_SIG + " ("
            + somay_sig + " TEXT," + mabp_sig + " TEXT," + tebp_sig + " TEXT,"
            + ngay_sig + " TEXT," + ghichu_sig + " TEXT," + trangthai_sig + " TEXT," + manv_sig + " TEXT," + sogio_sig + " TEXT," + tenhinh_sig +" TEXT )";
    //KT02(E)

    //Bảng ảo lưu biểu KT02 (S)
    String CREATE_TABLE_NAME_TC_FAC_KT02 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAC_KT02 + " ("
            + tc_fac004 + " TEXT," + tc_fac009 + " TEXT,"
            + checkbox1 + " TEXT ," + checkbox2 + " TEXT," + checkbox3 + " TEXT," + checkbox4 + " TEXT,"
            + checkbox5 + " TEXT," + checkbox6 + " TEXT," + user + " TEXT," + ngay + " TEXT," + somay + " TEXT,"
            + tc_fac003 + " TEXT," + tc_fac005 + " TEXT," + tc_fac006 + " TEXT," + tenhinh + " TEXT," + soluong + " TEXT," + ghichu + " TEXT )";
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
            String selectQuery = "SELECT count(*) as dem FROM " + TABLE_NAME_TC_FAC_KT02 + " WHERE tc_fac004='" + key + "' AND user='" + l_bp + "' AND somay='" + xsomay  + "' AND ngay='" + l_ngay + "' AND tenhinh is not null";
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
                                 " group by fiaud03,fia15,fka02,ngay_up " +
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
            String selectQuery = "SELECT DISTINCT somay_sig,mabp_sig,tebp_sig,ngay_sig,ghichu_sig,'Đã chuyển' AS trangthai_sig,manv_sig,sogio_sig,tenhinh_sig FROM fia_up_sig_file,fia_file " +
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

                    + " FROM " + TABLE_NAME_TC_FAC_KT02 + "  WHERE tc_fac004='" + key + "' AND user='" + l_bp + "' AND somay='" + xsomay  + "' AND ngay='" + l_ngay + "'", null);
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
}