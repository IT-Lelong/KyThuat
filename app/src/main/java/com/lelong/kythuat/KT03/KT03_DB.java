package com.lelong.kythuat.KT03;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class KT03_DB  extends SQLiteOpenHelper {

    private Context mCtx = null;
    String DATABASE_NAME = "KyThuatDB.db";
    public SQLiteDatabase db = null;

    String KT03_TABLE = "KT03_01_file";
    String KT03_01_001 = "KT03_01_001"; //Mã hạng mục
    String KT03_01_002 = "KT03_01_002"; //Đánh giá tốt
    String KT03_01_003 = "KT03_01_003"; //Đánh giá Không tốt
    String KT03_01_004 = "KT03_01_004"; //Ghi chú

    String CREATE_TABLE_KT03 = "CREATE TABLE IF NOT EXISTS " + KT03_TABLE + " ("
            + KT03_01_001 + " TEXT," + KT03_01_002 + " TEXT,"
            + KT03_01_003 + " TEXT," + KT03_01_004 + " TEXT)";

    public KT03_DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mCtx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*public KT03_DB(Context ctx) {
        this.mCtx = ctx;
    }*/

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
        //nối DATABASE(S)
        /*String dbPath = mCtx.getDatabasePath("KyThuatDB").getPath();
        // dbPath = /data/user/0/com.lelong.kythuat/databases/KyThuatDB.db
        String attachQuery = "ATTACH DATABASE '" + dbPath +"' AS KyThuatDB1";
        db.execSQL(attachQuery);*/
        //nối DATABASE(E)

        try {
            db.execSQL(CREATE_TABLE_KT03);
        } catch (Exception e) {

        }
    }

    public void close() {
        try {
            String DROP_TABLE_KT03 = "DROP TABLE IF EXISTS " + KT03_TABLE;
            db.execSQL(DROP_TABLE_KT03);
            db.close();
        } catch (Exception e) {

        }
    }

    public void delete_table() {
        db.delete(KT03_TABLE, null  , null);
    }
    
    public String append(String g_KT03_01_001, String g_KT03_01_002, String g_KT03_01_003, String g_KT03_01_004) {
        try {
            ContentValues args = new ContentValues();
            args.put(KT03_01_001, g_KT03_01_001);
            args.put(KT03_01_002, g_KT03_01_002);
            args.put(KT03_01_003, g_KT03_01_003);
            args.put(KT03_01_004, g_KT03_01_004);
            db.insert(KT03_TABLE, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }
    

    
}
