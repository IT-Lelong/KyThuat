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

    String TABLE_NAME_TC_FAA = "tc_faa_file";
    String tc_faa001  = "tc_faa001" ; //Mã hạng mục
    String tc_faa002 = "tc_faa002"; //Nội dung khảo hạch
    String tc_faa003 = "tc_faa003"; //điểm đạt
    String tc_faa004 = "tc_faa004"; //Ghi chép khảo hạch

    String tc_faa005 = "tc_faa005"; //Ghi chép khảo hạch

    String tc_faa006 = "tc_faa006"; //Ghi chép khảo hạch
    String tc_faa007 = "tc_faa007"; //Ghi chép khảo hạch
    String tc_faa008 = "tc_faa008"; //Ghi chép khảo hạch
    String tc_faa009 = "tc_faa009"; //Ghi chép khảo hạch
    String tc_faa010 = "tc_faa010"; //Ghi chép khảo hạch
    String TABLE_NAME_GEM = "gem_file";
    String gem01 = "gem01"; //Mã bộ phận
    String gem02 = "gem02"; //Tên bộ phận


    String CREATE_TABLE_FAA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAA + " ("
            + tc_faa001 + " TEXT, " + tc_faa002 + " TEXT,"
            + tc_faa003 + " TEXT ," + tc_faa004 + " TEXT," + tc_faa005 + " TEXT," + tc_faa006 + " TEXT,"+ tc_faa007 + " TEXT,"+ tc_faa008 + " TEXT,"+ tc_faa009 + " TEXT,"+ tc_faa010 + " TEXT,"
            + "PRIMARY KEY (" + tc_faa001 + ", " + tc_faa003 + "))";


    public KT01_DB(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
        try {
            db.execSQL(CREATE_TABLE_FAA);
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
    public String append(String g_tc_faa001, String g_tc_faa002, String g_tc_faa003, String g_tc_faa004, String g_tc_faa005, String g_tc_faa006, String g_tc_faa007, String g_tc_faa008, String g_tc_faa009, String g_tc_faa010) {
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
            db.insert(TABLE_NAME_TC_FAA, null, args);
            return "TRUE";
        } catch (Exception e) {
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
            return db.rawQuery("SELECT tc_faa001,tc_faa002,tc_faa003,tc_faa004,tc_faa005,tc_faa006,tc_faa007,tc_faa008"

                    + " FROM " + TABLE_NAME_TC_FAA + "", null);
        } catch (Exception e) {
            return null;
        }
    }
    public
    Cursor getAll_tc_faa1(String app) {
        try {
            return db.rawQuery("SELECT  COUNT(*)  as _id,SUM(CASE WHEN tc_faa004='Y' THEN tc_faa007 ELSE 0 END) AS tc_faa007  ,tc_faa002,tc_faa003"

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

}
