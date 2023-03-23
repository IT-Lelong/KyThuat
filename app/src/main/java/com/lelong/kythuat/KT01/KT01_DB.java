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
    public  SQLiteDatabase db = null;


    String TABLE_NAME_Ten_anh = "Ten_anh";
    String key1  = "key1" ; //Tên ảnh
    String ngay  = "ngay" ; //Tên ảnh
    String bophan  = "bophan" ; //Tên ảnh
    String stt  = "stt" ; //Số thứ tự
    String tenanh  = "tenanh" ; //Tên ảnh

    String TABLE_NAME_TC_FAA = "tc_faa_file";
    String tc_faa001  = "tc_faa001" ; //Mã hạng mục
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

    String TABLE_NAME_GEM = "gem_file";
    String gem01 = "gem01"; //Mã bộ phận
    String gem02 = "gem02"; //Tên bộ phận


    String CREATE_TABLE_FAA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAA + " ("
            + tc_faa001 + " TEXT, " + tc_faa002 + " TEXT,"
            + tc_faa003 + " TEXT ," + tc_faa004 + " TEXT," + tc_faa005 + " TEXT," + tc_faa006 + " TEXT,"+ tc_faa007 + " TEXT,"+ tc_faa008 + " TEXT,"+ tc_faa009 + " TEXT,"+ tc_faa010 + " TEXT,"+ tc_faa011 + " TEXT,"
            + "PRIMARY KEY (" + tc_faa001 + ", " + tc_faa003 + "))";

    String CREATE_Ten_anh = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_Ten_anh + " ("
            + stt + " TEXT, " + tenanh + " TEXT, " + key1 + " TEXT, " + bophan + " TEXT, " + ngay + " TEXT," +"PRIMARY KEY (" + ngay + ", " + stt + "))";

    public KT01_DB(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
        try {
            db.execSQL(CREATE_TABLE_FAA);
            db.execSQL(CREATE_Ten_anh);
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
    public String append(String g_tc_faa001, String g_tc_faa002, String g_tc_faa003, String g_tc_faa004, String g_tc_faa005, String g_tc_faa006, String g_tc_faa007, String g_tc_faa008, String g_tc_faa009, String g_tc_faa010, String g_tc_faa011) {
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
            db.insert(TABLE_NAME_TC_FAA, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }
    public String append1(String g_key,String g_ngay,String g_bp,String g_stt, String g_tenanh) {
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
    public String appendUPDAE1(String stt,String tenanh){
        try{
            ContentValues args=new ContentValues();
            //   args.put(no1,xno1);
            //  args.put(ip,xip);
            //  Cursor mCursor=db.query(TABLE_NAME,new String[]{no1},no1+"=?",new String[]{xno1},null,null,null,null);
            //  if(mCursor.getCount()>0){
            db.execSQL("UPDATE " + TABLE_NAME_Ten_anh + " SET "+stt+"='"+stt+"' WHERE tenanh='"+tenanh+"' ");
            return "TRUE";
            //  }else{
            //    db.insert(TABLE_NAME,null,args);
            //     return "TRUE";
            // }
        }catch (Exception e){
            return "FALSE";
        }
    }
    public String appendUPDAE(String key,String l_check,String date,String bp,String tencot){
        try{
            ContentValues args=new ContentValues();
         //   args.put(no1,xno1);
          //  args.put(ip,xip);
          //  Cursor mCursor=db.query(TABLE_NAME,new String[]{no1},no1+"=?",new String[]{xno1},null,null,null,null);
          //  if(mCursor.getCount()>0){
                db.execSQL("UPDATE " + TABLE_NAME_TC_FAA + " SET "+tencot+"='"+l_check+"' WHERE tc_faa001='"+key+"'  AND tc_faa003='"+bp+"'");
                return "TRUE";
          //  }else{
            //    db.insert(TABLE_NAME,null,args);
           //     return "TRUE";
           // }
        }catch (Exception e){
            return "FALSE";
        }
    }

    public
    Cursor getAll_tc_faa(String app) {
        try {
            return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa007,tc_faa008,tc_faa011"

                    + " FROM " + TABLE_NAME_TC_FAA + "", null);
        } catch (Exception e) {
            return null;
        }
    }
    public
    Cursor getAll_tc_faa1(String app) {
        try {
            return db.rawQuery("SELECT  COUNT(*)  as _id ,tc_faa002,tc_faa003"

                    + " FROM " + TABLE_NAME_TC_FAA + " group by tc_faa003 ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public
    Cursor getAll_tc_faa2(String date,String bp,String dk) {
                try {
                    //SQLiteDatabase db = this.getWritableDatabase();
                    String selectQuery = "SELECT * FROM " + TABLE_NAME_TC_FAA + " WHERE tc_faa002='" +date + "' AND tc_faa003='" +bp+"'AND tc_faa010='" +dk+"'";
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
    public
    Cursor getAll_gem1() {
        try {
            return db.rawQuery("SELECT  COUNT(*)  as _id, gem01,gem02" + " FROM " + TABLE_NAME_GEM + " group by gem01,gem02  ", null);
        } catch (Exception e) {
            return null;
        }
    }



    public void delete_table() {
        db.delete(TABLE_NAME_TC_FAA, null  , null);
    }
    public void delete_tenhinh() {
        db.delete(TABLE_NAME_Ten_anh, null  , null);
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
    public
    Cursor checkxemdacochupdanhcua(String ngay,String bp ,String key) {
        try {
            return db.rawQuery("SELECT  tc_faa005  FROM " + TABLE_NAME_TC_FAA +   " WHERE tc_faa002='" +ngay + "' AND tc_faa003='" +bp+"'AND tc_faa001='" +key+"'  ", null);
        } catch (Exception e) {
            return null;
        }
    }

    public
    Cursor demsttanh(String KEY,String bp,String ngay) {
        try {
            return db.rawQuery("SELECT  tc_faa011 FROM " + TABLE_NAME_TC_FAA + " WHERE tc_faa001='" +KEY + "' AND tc_faa003='" +bp+"' AND tc_faa002='" +ngay+"' ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public String appendUPDAEhinhanh(String key,String l_check,int stt,String date,String bp,String tencothinh,String tencotstt){
        try{
            ContentValues args=new ContentValues();
            //   args.put(no1,xno1);
            //  args.put(ip,xip);
            //  Cursor mCursor=db.query(TABLE_NAME,new String[]{no1},no1+"=?",new String[]{xno1},null,null,null,null);
            //  if(mCursor.getCount()>0){
            db.execSQL("UPDATE " + TABLE_NAME_TC_FAA + " SET "+tencothinh+"='"+l_check+"',"+tencotstt+"='"+stt+"' WHERE tc_faa001='"+key+"'  AND tc_faa003='"+bp+"'");
            return "TRUE";
            //  }else{
            //    db.insert(TABLE_NAME,null,args);
            //     return "TRUE";
            // }
        }catch (Exception e){
            return "FALSE";
        }
    }

    public
    Cursor xuathinhanh(String key,String l_ngay,String l_bp) {
        try {
            return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa007,tc_faa008,tc_faa011"

                    + " FROM " + TABLE_NAME_TC_FAA + "  WHERE tc_faa002='" +l_ngay+ "' AND tc_faa003='" +l_bp+"'AND tc_faa001='" +key+"'", null);
        } catch (Exception e) {
            return null;
        }
    }
    public
    Cursor lananh(String key,String l_ngay,String l_bp) {
        try {
            return db.rawQuery("SELECT stt,tenanh"

                    + " FROM " + TABLE_NAME_Ten_anh + "  WHERE ngay='" +l_ngay+ "' AND bophan='" +l_bp+"'AND key1='" +key+"'", null);
        } catch (Exception e) {
            return null;
        }
    }
    public
    Cursor lananh1(String key,String l_ngay,String l_bp,int bien) {
        try {
            return db.rawQuery("SELECT (max(stt) - "+ bien +" ) as stt,tenanh"

                    + " FROM " + TABLE_NAME_Ten_anh + "  WHERE ngay='" +l_ngay+ "' AND bophan='" +l_bp+"'AND key1='" +key+"'  ", null);
        } catch (Exception e) {
            return null;
        }
    }
    public
    Cursor lananh2(String key,String l_ngay,String l_bp) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT stt,tenanh FROM " + TABLE_NAME_Ten_anh + " WHERE ngay='" +l_ngay+ "' AND bophan='" +l_bp+"'AND key1='" +key+"' ORDER BY stt DESC ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }

    }




}
