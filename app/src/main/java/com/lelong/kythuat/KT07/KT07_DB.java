package com.lelong.kythuat.KT07;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class KT07_DB {
    private Context mCtx = null;
    //String DATABASE_NAME = "KyThuatDB02.db";
    String DATABASE_NAME = "KyThuatDB.db";
    public static SQLiteDatabase db = null;
    public SQLiteDatabase db2 = null;

    private final static String TABLE_NAME_TC_CEA_IN = "tc_cea_file_in";
    private final static String tc_cea01_in = "tc_cea01_in"; ////Loai
    private final static String tc_cea02_in = "tc_cea02_in"; ////Tên Loai
    private final static String tc_cea03_in = "tc_cea03_in"; ////STT
    private final static String tc_cea04_in = "tc_cea04_in"; ////Nhà máy
    private final static String tc_cea05_in = "tc_cea05_in"; ////Mã số
    private final static String tc_cea06_in = "tc_cea06_in"; ////Chỉ tiêu ngày
    private final static String tc_cea07_in = "tc_cea07_in"; ////Ghi chú
    private final static String tc_ceb03_in = "tc_ceb03_in"; ////Ngày tiêu hao
    private final static String tc_ceb06_in = "tc_ceb06_in"; ////Khoảng thời gian
    private final static String tc_ceb04_in = "tc_ceb04_in"; ////Bảng số đo
    private final static String tc_ceb05_in = "tc_ceb05_in"; ////Lượng sử dụng
    private final static String tc_cebdate_in = "tc_cebdate_in"; ////Ngày nhập
    private final static String tc_cebuser_in = "tc_cebuser_in"; ////Nhân viên nhập
    private final static String tc_cebstatus_in = "tc_cebstatus_in"; ////Nhân viên nhập


    //Bảng ảo lưu biểu KT02 (S)
    String CREATE_TABLE_NAME_TC_CEA_IN = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_CEA_IN + " ("
            + tc_cea01_in + " TEXT," + tc_cea02_in + " TEXT,"
            + tc_cea03_in + " TEXT ," + tc_cea04_in + " TEXT," + tc_cea05_in + " TEXT," + tc_cea06_in + " TEXT,"
            + tc_cea07_in + " TEXT, " + tc_ceb03_in + " TEXT , " + tc_ceb06_in + " TEXT, " + tc_ceb04_in + " TEXT, " + tc_ceb05_in +
            " TEXT, " + tc_cebdate_in + " TEXT, " + tc_cebuser_in + " TEXT, " + tc_cebstatus_in + " TEXT )";
    //Bảng ảo lưu biểu KT02 (E)

    public KT07_DB(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
        try {
            db.execSQL(CREATE_TABLE_NAME_TC_CEA_IN);
        } catch (Exception e) {

        }


    }

    public void close() {
        try {
            final String DROP_TABLE_NAME_TC_CEA_IN = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_CEA_IN;
            db.execSQL(DROP_TABLE_NAME_TC_CEA_IN);
            db.close();
        } catch (Exception e) {

        }
    }

    public void delete_table() {
        db.delete(TABLE_NAME_TC_CEA_IN, null, null);
    }

    public String appendUPDAE(String key,String key2,String chiso,String date,String datecuren,String tencot,String g_tc_ceb06){
        try{
            ContentValues args=new ContentValues();
            //   args.put(no1,xno1);
            //  args.put(ip,xip);
            //  Cursor mCursor=db.query(TABLE_NAME,new String[]{no1},no1+"=?",new String[]{xno1},null,null,null,null);
            //  if(mCursor.getCount()>0){
            db.execSQL("UPDATE " + TABLE_NAME_TC_CEA_IN + " SET "+tencot+"='"+chiso+"' WHERE tc_cea01_in='"+key+"'  AND tc_cea03_in='"+key2+"'" +
                    " AND tc_ceb03_in='"+date+"' AND tc_cebdate_in='"+datecuren+"' AND tc_ceb06_in='"+g_tc_ceb06+"' ");
            return "TRUE";
            //  }else{
            //    db.insert(TABLE_NAME,null,args);
            //     return "TRUE";
            // }
        }catch (Exception e){
            return "FALSE";
        }
    }

    public Cursor getAll_tc_cea_in() {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT tc_cea01_in,tc_cea03_in,tc_ceb03_in,COALESCE(tc_ceb04_in,0) AS tc_ceb04_in,COALESCE(tc_ceb05_in,0) AS tc_ceb05_in,tc_cebdate_in,tc_cebuser_in,tc_ceb06_in " +
                    " FROM tc_cea_file_in WHERE tc_cea01_in='DHS' AND tc_cea03_in='001' AND  (tc_cebstatus_in='Chưa chuyển' OR tc_cebstatus_in IS NULL) " +
                    //" FROM tc_cea_file_in WHERE (tc_cebstatus_in='Chưa chuyển' OR tc_cebstatus_in IS NULL) " +
                    " order by tc_cea01_in,tc_cea03_in ";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public static long updateALL_tc_cea_in() {
        try {
            db.execSQL("UPDATE " + TABLE_NAME_TC_CEA_IN + " SET tc_cebstatus_in ='Chưa chuyển'" +
                    " WHERE tc_cebstatus_in IS NULL OR tc_cebstatus_in ='Chưa chuyển'");
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public void delete_table_kt07(String tc_cebstatus_in) {
        if (db != null) {
            String where_loggin = "tc_cebstatus_in=?";
            String[] strings = new String[]{tc_cebstatus_in};
            db.delete(TABLE_NAME_TC_CEA_IN, where_loggin, strings);
        }

    }
}