package com.lelong.kythuat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Create_Table {

    private Context mCtx = null;
    String DATABASE_NAME = "KyThuatDB.db";
    public SQLiteDatabase db = null;
    private final static String TABLE_NAME_TC_FAC_KT02 = "tc_fac_table_kt02";
    private final static String TABLE_NAME_TC_FAA = "tc_faa_file";
    private final static String TABLE_NAME_TC_CEA_IN = "tc_cea_file_in";
    private final static String TABLE_NAME_CPF_FILE = "cpf_file";
    //private final static String TABLE_NAME_TC_FBA = "tc_fba_file";

    String TABLE_NAME_TC_FAB = "tc_fab_file";
    String tc_fab001 = "tc_fab001"; //Mã hạng mục
    String tc_fab002 = "tc_fab002"; //Mã báo biểu
    String tc_fab003 = "tc_fab003"; //Mã hạng mục chi tiết
    String tc_fab004 = "tc_fab004"; //Mã tổng

    String TABLE_NAME_TC_FAC = "tc_fac_file";
    String tc_fac001 = "tc_fac001"; //Mã hạng mục
    String tc_fac002 = "tc_fac002"; //Mã báo biểu
    String tc_fac003 = "tc_fac003"; //Mã hạng mục chi tiết
    String tc_fac004 = "tc_fac004"; //Mã tổng
    String tc_fac005 = "tc_fac005"; //tên tiếng hoa
    String tc_fac006 = "tc_fac006"; //tên tiếng việt
    String tc_fac007 = "tc_fac007"; //Điểm số
    String tc_fac008 = "tc_fac008"; //Hãng sản xuất
    String tc_fac011 = "tc_fac011"; //Dãy đo thiết bị

    String TABLE_NAME_GEM = "gem_file";
    String gem01 = "gem01"; //Mã bộ phận
    String gem02 = "gem02"; //Tên bộ phận

    //KT02(S)
    String TABLE_NAME_FIA = "fia_file";
    String fia01 = "fia01"; //Mã số thiết bị
    String ta_fia02_1 = "ta_fia02_1"; //Tên thiết bị
    String fiaud03 = "fiaud03"; //Số máy
    String fia15 = "fia15"; //Bộ phận
    String fka02 = "fka02"; //Tên bộ phận
    //KT02(E)
    //KT01(S)
    String TABLE_NAME_TC_FBA = "tc_fba_file";
    String tc_fba007 = "tc_fba007"; //Mã bộ phận
    String tc_fba009 = "tc_fba009"; //Tên bộ phận

    String CREATE_TABLE_NAME_TC_FBA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FBA + " ("
            + tc_fba007 + " TEXT," + tc_fba009 + " TEXT )";
    //KT01(E)

    //KT07(S)
    String TABLE_NAME_TC_CEA = "tc_cea_file";
    String tc_cea01 = "tc_cea01"; ////Loai
    String tc_cea02 = "tc_cea02"; ////Tên Loai
    String tc_cea03 = "tc_cea03"; ////STT
    String tc_cea04 = "tc_cea04"; ////Nhà máy
    String tc_cea05 = "tc_cea05"; ////Mã số
    String tc_cea06 = "tc_cea06"; ////Chỉ tiêu ngày
    String tc_cea07 = "tc_cea07"; ////Ghi chú
    String tc_cea08 = "tc_cea08"; ////Đơn vị (0:khác, 1:SXL, 2:LR, 3:KUNG TAY, 4:Văn Phòng, 5:Bến Lức, 6:SXL6)
    String tc_cea09 = "tc_cea09"; ////Xưởng (A-K)
    //KT07(E)

    String CREATE_TABLE_CPF = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_CPF_FILE + " (cpf01 TEXT, cpf02 TEXT, ta_cpf001 TEXT, cpf29 TEXT , gem02 TEXT , cpf281 TEXT , user_control TEXT , user_group TEXT)";

    String CREATE_TABLE_FAB = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAB + " ("
            + tc_fab001 + " TEXT," + tc_fab002 + " TEXT,"
            + tc_fab003 + " TEXT," + tc_fab004 + " TEXT)";

    String CREATE_TABLE_FAC = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_FAC + " ("
            + tc_fac001 + " TEXT," + tc_fac002 + " TEXT," + tc_fac003 + " TEXT,"
            + tc_fac004 + " TEXT," + tc_fac005 + " TEXT," + tc_fac006 + " TEXT,"
            + tc_fac007 + " TEXT," + tc_fac008 + " TEXT," + tc_fac011 + " TEXT )";

    String CREATE_TABLE_GEM = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_GEM + " ("
            + gem01 + " TEXT," + gem02 + " TEXT ) ";

    //KT02(S)
    String CREATE_TABLE_FIA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_FIA + " ("
            + fia01 + " TEXT," + ta_fia02_1 + " TEXT," + fiaud03 + " TEXT,"
            + fia15 + " TEXT," + fka02 + " TEXT )";
    //KT02(E)

    //KT07(S)
    String CREATE_TABLE_NAME_TC_CEA = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_CEA + " ("
            + tc_cea01 + " TEXT," + tc_cea02 + " TEXT,"
            + tc_cea03 + " TEXT ," + tc_cea04 + " TEXT," + tc_cea05 + " TEXT," + tc_cea06 + " TEXT,"
            + tc_cea07 + " TEXT, " + tc_cea08 + " TEXT," + tc_cea09 + " TEXT )";
    //KT07(S)

    public Create_Table(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
    }

    public void openTable() {
        try {
            db.execSQL(CREATE_TABLE_CPF);
            db.execSQL(CREATE_TABLE_FAB);
            db.execSQL(CREATE_TABLE_FAC);
            db.execSQL(CREATE_TABLE_GEM);
            db.execSQL(CREATE_TABLE_FIA);
            db.execSQL(CREATE_TABLE_NAME_TC_FBA);
            db.execSQL(CREATE_TABLE_NAME_TC_CEA);
        } catch (Exception e) {

        }
    }

    public void close() {
        try {
            String DROP_TABLE_TC_FAB = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_FAB;
            String DROP_TABLE_TC_FAC = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_FAC;
            String DROP_TABLE_GEM = "DROP TABLE IF EXISTS " + TABLE_NAME_GEM;
            String DROP_TABLE_TC_FIA = "DROP TABLE IF EXISTS " + TABLE_NAME_FIA;
            String DROP_TABLE_NAME_TC_FBA = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_FBA;
            String DROP_TABLE_NAME_TC_CEA = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_CEA;
            String DROP_TABLE_NAME_CPF = "DROP TABLE IF EXISTS " + TABLE_NAME_CPF_FILE;
            db.execSQL(DROP_TABLE_TC_FAB);
            db.execSQL(DROP_TABLE_TC_FAC);
            db.execSQL(DROP_TABLE_GEM);
            db.execSQL(DROP_TABLE_TC_FIA);
            db.execSQL(DROP_TABLE_NAME_TC_FBA);
            db.execSQL(DROP_TABLE_NAME_TC_CEA);
            db.execSQL(DROP_TABLE_NAME_CPF);
            db.close();
        } catch (Exception e) {

        }
    }

    public String ins_cpf_file(String g_cpf01, String g_cpf02, String g_ta_cpf001,
                               String g_cpf29, String g_gem02, String g_cpf281,
                               String g_control, String g_group) {
        try {
            ContentValues args = new ContentValues();
            args.put("cpf01", g_cpf01);
            args.put("cpf02", g_cpf02);
            args.put("ta_cpf001", g_ta_cpf001);
            args.put("cpf29", g_cpf29);
            args.put("gem02", g_gem02);
            args.put("cpf281", g_cpf281);
            args.put("user_control", g_control);
            args.put("user_group", g_group);
            db.insert(TABLE_NAME_CPF_FILE, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    public String append(String g_tc_fab001, String g_tc_fab002, String g_tc_fab003, String g_tc_fab004) {
        try {
            ContentValues args = new ContentValues();
            args.put(tc_fab001, g_tc_fab001);
            args.put(tc_fab002, g_tc_fab002);
            args.put(tc_fab003, g_tc_fab003);
            args.put(tc_fab004, g_tc_fab004);
            db.insert(TABLE_NAME_TC_FAB, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    public String append(String g_tc_fac001, String g_tc_fac002, String g_tc_fac003,
                         String g_tc_fac004, String g_tc_fac005, String g_tc_fac006,
                         String g_tc_fac007, String g_tc_fac008, String g_tc_fac011) {
        try {
            ContentValues args = new ContentValues();
            args.put(tc_fac001, g_tc_fac001);
            args.put(tc_fac002, g_tc_fac002);
            args.put(tc_fac003, g_tc_fac003);
            args.put(tc_fac004, g_tc_fac004);
            args.put(tc_fac005, g_tc_fac005);
            args.put(tc_fac006, g_tc_fac006);
            args.put(tc_fac007, g_tc_fac007);
            args.put(tc_fac008, g_tc_fac008);
            args.put(tc_fac011, g_tc_fac011);
            db.insert(TABLE_NAME_TC_FAC, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }

    }

    public String append(String g_gem01, String g_gem02) {
        try {
            ContentValues args = new ContentValues();
            args.put(gem01, g_gem01);
            args.put(gem02, g_gem02);
            db.insert(TABLE_NAME_GEM, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }

    }

    public String append1(String g_tc_fba007, String g_tcfba009) {
        try {
            ContentValues args = new ContentValues();
            args.put(tc_fba007, g_tc_fba007);
            args.put(tc_fba009, g_tcfba009);
            db.insert(TABLE_NAME_TC_FBA, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }

    }

    /*KT02(S)*/
    public String append(String g_fia01, String g_ta_fia02_1, String g_fiaud03, String g_fia15, String g_fka02) {
        try {
            ContentValues args = new ContentValues();
            args.put(fia01, g_fia01);
            args.put(ta_fia02_1, g_ta_fia02_1);
            args.put(fiaud03, g_fiaud03);
            args.put(fia15, g_fia15);
            args.put(fka02, g_fka02);
            db.insert(TABLE_NAME_FIA, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }

    }
    /*KT02(E)*/

    /*KT07*/
    public String append2(String g_tc_cea01, String g_tc_cea02, String g_tc_cea03, String g_tc_cea04,
                          String g_tc_cea05, String g_tc_cea06, String g_tc_cea07, String g_tc_cea08, String g_tc_cea09) {
        try {
            ContentValues args = new ContentValues();
            args.put(tc_cea01, g_tc_cea01);
            args.put(tc_cea02, g_tc_cea02);
            args.put(tc_cea03, g_tc_cea03);
            args.put(tc_cea04, g_tc_cea04);
            args.put(tc_cea05, g_tc_cea05);
            args.put(tc_cea06, g_tc_cea06);
            args.put(tc_cea07, g_tc_cea07);
            args.put(tc_cea08, g_tc_cea08);
            args.put(tc_cea09, g_tc_cea09);
            db.insert(TABLE_NAME_TC_CEA, null, args);
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }

    }

    /*KT07*/
    public void delete_table() {
        db.delete(TABLE_NAME_TC_FAB, null, null);
        db.delete(TABLE_NAME_TC_FAC, null, null);
        db.delete(TABLE_NAME_GEM, null, null);
        db.delete(TABLE_NAME_FIA, null, null);
        db.delete(TABLE_NAME_TC_CEA, null, null);
        db.delete(TABLE_NAME_CPF_FILE, null, null);
    }

    public Cursor getAll_tc_fab(String qry_cond) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT * FROM " + TABLE_NAME_TC_FAB + " WHERE tc_fab001 = '" + qry_cond + "' ORDER BY tc_fab001 ";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_tc_fac(String g_kind, String g_kind1) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            String g_xuong = Constant_Class.UserFactory;
            String selectQuery = "";
            if(g_xuong.equals("DH")){
                selectQuery = "SELECT * FROM " + TABLE_NAME_TC_FAC + " WHERE tc_fac002='" + g_kind + "' AND tc_fac001='" + g_kind1 + "'";
            }else{
                if(g_kind1.equals("02")){
                    selectQuery = "SELECT * FROM " + TABLE_NAME_TC_FAC + " WHERE tc_fac002='" + g_kind + "' AND tc_fac001='" + g_kind1 + "' AND tc_fac003 != '17' ";
                }
                else {
                    selectQuery = "SELECT * FROM " + TABLE_NAME_TC_FAC + " WHERE tc_fac002='" + g_kind + "' AND tc_fac001='" + g_kind1 + "'";
                }
            }

            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_tc_fac_01(String g_kind, String g_kind1, String bophan, String ngay) {
        Cursor a;
        try {
            int count = 0;
            ContentValues argsA = new ContentValues();
            Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_TC_FAC + "," + TABLE_NAME_TC_FAA + " " +
                    " WHERE tc_faa001=tc_fac004 and  tc_fac002='" + g_kind + "' " +
                    "AND tc_fac001='" + g_kind1 + "' AND tc_faa003='" + bophan + "' AND tc_faa002='" + ngay + "'", null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count > 0) {
                String selectQuery = "SELECT tc_fac001,tc_fac003,tc_fac004,tc_fac005,tc_fac006,tc_faa006,tc_faa004 as checkbox,tc_faa012 as checkbox1" +
                        ",tc_faa013 as checkbox2,tc_faa014 as checkbox3,tc_faa015 as checkbox4,tc_faa016 as checkbox5" +
                        " FROM " + TABLE_NAME_TC_FAC + "," + TABLE_NAME_TC_FAA + " " +
                        " WHERE tc_faa001=tc_fac004 and  tc_fac002='" + g_kind + "' " +
                        " AND tc_fac001='" + g_kind1 + "' AND tc_faa003='" + bophan + "' " +
                        " AND tc_faa002='" + ngay + "'";
                return db.rawQuery(selectQuery, null);
            } else {
                String selectQuery1 = "SELECT tc_fac001,tc_fac003,tc_fac004,tc_fac005,tc_fac006,'false' as checkbox,'false' as checkbox1,'true' as checkbox2,'false' as checkbox3,'false' as checkbox4,'false' as checkbox5,'' as tc_faa006  FROM " + TABLE_NAME_TC_FAC + " " +
                        " WHERE tc_fac002='" + g_kind + "' AND tc_fac001='" + g_kind1 + "'";
                return db.rawQuery(selectQuery1, null);
            }
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

    //KT02(S)

    /*KT02_chitiet*/

    public Cursor getAll_tc_fac_02(String g_kind, String g_kind1, String bophan, String somay, String ngay) {
        Cursor a;
        try {
            int count = 0;
            ContentValues argsA = new ContentValues();
            Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_TC_FAC + "," + TABLE_NAME_TC_FAC_KT02 + " " +
                    " WHERE tc_fac_table_kt02.tc_fac004=tc_fac_file.tc_fac004 and  tc_fac002='" + g_kind + "' " +
                    "AND tc_fac001='" + g_kind1 + "' AND user='" + bophan + "' AND somay='" + somay + "' AND ngay='" + ngay + "'", null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count > 0) {
                String selectQuery = "SELECT  tc_fac_file.tc_fac003 as tc_fac003,tc_fac_file.tc_fac004 as tc_fac004,tc_fac_file.tc_fac006 as tc_fac006 ,tc_fac_table_kt02.checkbox1 as checkbox1,tc_fac_table_kt02.checkbox2 as checkbox2," +
                        "tc_fac_table_kt02.checkbox3 as checkbox3,tc_fac_table_kt02.checkbox4 as checkbox4,tc_fac_table_kt02.checkbox5 as checkbox5,tc_fac_table_kt02.checkbox6 as checkbox6," +
                        "tc_fac_table_kt02.tc_fac009 as tc_fac009 ,tc_fac_table_kt02.tc_fac005 as tc_fac005,tc_fac_table_kt02.tenhinh as tenhinh " +
                        " FROM " + TABLE_NAME_TC_FAC + "," + TABLE_NAME_TC_FAC_KT02 + " " +
                        " WHERE tc_fac_table_kt02.tc_fac004=tc_fac_file.tc_fac004 and  tc_fac002='" + g_kind + "' " +
                        " AND tc_fac001='" + g_kind1 + "' AND user='" + bophan + "' AND somay='" + somay + "' " +
                        " AND ngay='" + ngay + "'";
                return db.rawQuery(selectQuery, null);
            } else {
                String selectQuery1 = "SELECT tc_fac003,tc_fac006,tc_fac004,'false' as checkbox1,'false' as checkbox2,'true' as checkbox3,'false' as checkbox4,'false' as checkbox5,'false' as checkbox6,'' as tc_fac009 ,tc_fac005,tenhinh FROM " + TABLE_NAME_TC_FAC + " WHERE tc_fac002='" + g_kind + "' AND tc_fac001='" + g_kind1 + "'";
                return db.rawQuery(selectQuery1, null);
            }
            //SQLiteDatabase db = this.getWritableDatabase();
            //String selectQuery = "SELECT tc_fac003,tc_fac006,tc_fac004 FROM " + TABLE_NAME_TC_FAC + " WHERE tc_fac002='" + g_kind + "' AND tc_fac001='" + g_kind1+"'";

        } catch (Exception e) {
            return null;
        }
    }

    /*KT02_loggin_somay*/

    public Cursor getAll_fia_02(String tenxe, String bophan) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT distinct fiaud03 FROM fia_file WHERE ta_fia02_1='" + tenxe + "' ";
            if (!(bophan == null)) {
                selectQuery = selectQuery + " AND Substr(fia15,1,1) = '" + bophan + "' ";
            }
            selectQuery = selectQuery + " ORDER BY fiaud03 ";
            //String selectQuery = "SELECT distinct fiaud03 FROM fia_file WHERE ta_fia02_1='" + tenxe + "' ORDER BY fiaud03";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }



    /*KT02_loggin_bophan*/

    public Cursor getAll_fia_02_bp(String g_fiaud03, String tenxe, String bophan) {
        Cursor a;
        try {

            //SQLiteDatabase db = this.getWritableDatabase();
            String selectQuery = "SELECT distinct fia15,fka02 FROM fia_file WHERE fiaud03='" + g_fiaud03 + "' AND ta_fia02_1='" + tenxe + "' ";
            //String selectQuery = "SELECT distinct fia15,fka02 FROM fia_file WHERE fiaud03='" + g_fiaud03+"' AND ta_fia02_1='" + tenxe + "' ORDER BY fia15";
            if (!(bophan == null)) {
                selectQuery = selectQuery + " AND Substr(fia15,1,1) = '" + bophan + "' ";
            }
            selectQuery = selectQuery + " ORDER BY fia15 ";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    /*Lấy dl bảng ảo đổ lên table tc_fad_file*/
    /*public Cursor getAll_instc_fad(String g_user, String g_somay,String g_ngay) {
        try {
            String selectQuery = "SELECT  tc_fac_file.tc_fac001 as tc_fac001,tc_fac_file.tc_fac002 as tc_fac002,tc_fac_file.tc_fac003 as tc_fac003,tc_fac_file.tc_fac004 as tc_fac004,tc_fac_table_kt02.tc_fac005 as tc_fac005 ,tc_fac_file.tc_fac006 as tc_fac006 ,tc_fac_table_kt02.checkbox1 as checkbox1,tc_fac_table_kt02.checkbox2 as checkbox2," +
                    "tc_fac_table_kt02.checkbox3 as checkbox3,tc_fac_table_kt02.checkbox4 as checkbox4,tc_fac_table_kt02.checkbox5 as checkbox5,tc_fac_table_kt02.checkbox6 as checkbox6,tc_fac_table_kt02.tc_fac009 as tc_fac009," +
                    "tc_fac_table_kt02.user as user,tc_fac_table_kt02.ngay as ngay,tc_fac_table_kt02.somay as somay " +
                    " FROM " + TABLE_NAME_TC_FAC + ","+TABLE_NAME_TC_FAC_KT02+" " +
                    " WHERE tc_fac_table_kt02.tc_fac004=tc_fac_file.tc_fac004 AND user='" + g_user+"' AND somay='" + g_somay+"' " +
                    " AND ngay='" + g_ngay+"'";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }*/
    public Cursor getAll_instc_fad(String tenxe, String somay) {
        try {
            /*String selectQuery = "SELECT  tc_fac_file.tc_fac001 as tc_fac001,tc_fac_file.tc_fac002 as tc_fac002,tc_fac_file.tc_fac003 as tc_fac003,tc_fac_file.tc_fac004 as tc_fac004,tc_fac_table_kt02.tc_fac005 as tc_fac005 ,tc_fac_file.tc_fac006 as tc_fac006 ,tc_fac_table_kt02.checkbox1 as checkbox1,tc_fac_table_kt02.checkbox2 as checkbox2," +
                    "tc_fac_table_kt02.checkbox3 as checkbox3,tc_fac_table_kt02.checkbox4 as checkbox4,tc_fac_table_kt02.checkbox5 as checkbox5,tc_fac_table_kt02.checkbox6 as checkbox6,tc_fac_table_kt02.tc_fac009 as tc_fac009," +
                    "tc_fac_table_kt02.user as user,tc_fac_table_kt02.ngay as ngay,tc_fac_table_kt02.somay as somay " +
                    " FROM " + TABLE_NAME_TC_FAC + ","+TABLE_NAME_TC_FAC_KT02+",fia_file " +
                    " WHERE tc_fac_table_kt02.tc_fac004=tc_fac_file.tc_fac004 and somay=fiaud03 and fia15=user and ta_fia02_1='" + tenxe + "' ";*/
            String selectQuery = "SELECT  tc_fac_file.tc_fac001 as tc_fac001,tc_fac_file.tc_fac002 as tc_fac002,tc_fac_file.tc_fac003 as tc_fac003,tc_fac_file.tc_fac004 as tc_fac004,tc_fac_table_kt02.tc_fac005 as tc_fac005 ,tc_fac_file.tc_fac006 as tc_fac006 ,tc_fac_table_kt02.checkbox1 as checkbox1,tc_fac_table_kt02.checkbox2 as checkbox2," +
                    "tc_fac_table_kt02.checkbox3 as checkbox3,tc_fac_table_kt02.checkbox4 as checkbox4,tc_fac_table_kt02.checkbox5 as checkbox5,tc_fac_table_kt02.checkbox6 as checkbox6,tc_fac_table_kt02.tc_fac009 as tc_fac009," +
                    "tc_fac_table_kt02.user as user,tc_fac_table_kt02.ngay as ngay,tc_fac_table_kt02.somay as somay " +
                    " FROM " + TABLE_NAME_TC_FAC + "," + TABLE_NAME_TC_FAC_KT02 + ",fia_file " +
                    " WHERE tc_fac_table_kt02.tc_fac004=tc_fac_file.tc_fac004 and somay=fiaud03 and fia15=user and ta_fia02_1='" + tenxe + "' AND somay='" + somay + "'";
            return db.rawQuery(selectQuery, null);
        } catch (Exception e) {
            return null;
        }
    }

    //insert
    public boolean ins_fac_02(String g_kind, String xuser, String xngay, String xsomay) {
        //db=mCtx.openOrCreateDatabase(DATABASE_NAME,0,null);
        //將上傳資料彙整至json_table
        int count = 0;
        //ContentValues argsA = new ContentValues();
        Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_TC_FAC_KT02 + " " +
                " WHERE user='" + xuser + "' AND somay='" + xsomay + "' AND ngay='" + xngay + "' ", null);
        mCount.moveToFirst();
        count = mCount.getInt(0);
        if (count == 0) {
            ContentValues argsC = new ContentValues();
            Cursor c = db.rawQuery("SELECT tc_fac004,'' as tc_fac009 ,'false' as checkbox1,'false' as checkbox2,'true' as checkbox3," +
                    " 'false' as checkbox4,'false' as checkbox5,'false' as checkbox6,tc_fac003,tc_fac005,tc_fac006 FROM " + TABLE_NAME_TC_FAC +
                    " WHERE tc_fac002='" + g_kind + "'", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    argsC.put("tc_fac004", c.getString(0));
                    argsC.put("tc_fac009", c.getString(1));
                    argsC.put("checkbox1", c.getString(2));
                    argsC.put("checkbox2", c.getString(3));
                    argsC.put("checkbox3", c.getString(4));
                    argsC.put("checkbox4", c.getString(5));
                    argsC.put("checkbox5", c.getString(6));
                    argsC.put("checkbox6", c.getString(7));
                    argsC.put("user", xuser);
                    argsC.put("ngay", xngay);
                    argsC.put("somay", xsomay);
                    argsC.put("tc_fac003", c.getString(8));
                    argsC.put("tc_fac005", c.getString(9));
                    argsC.put("tc_fac006", c.getString(10));
                    argsC.put("tc_facpost", "N");
                    db.insert(TABLE_NAME_TC_FAC_KT02, null, argsC);
                } while (c.moveToNext());
            } else {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public Cursor getAll_fiaud03(String tenxe, String bophan) {
        try {
           /* String selectQuery = "select count(*) AS _id,fiaud03,fia15,fka02 from fia_file " +
                    " where fiaud03 not in (select distinct somay from tc_fac_table_kt02) AND ta_fia02_1='" + tenxe + "' AND fia15 NOT LIKE 'B%' " +
                    " group by fia15,fiaud03,fka02 order by fia15,fiaud03";*/
            String selectQuery = " select count(*) AS _id,fiaud03,fia15,fka02 from fia_file " +
                    " where fiaud03 not in (select distinct somay from tc_fac_table_kt02,fia_file where fiaud03 =  somay " +
                    " AND fia15 = user AND  ta_fia02_1='" + tenxe + "' AND ngay = date('now') ) AND ta_fia02_1='" + tenxe + "' ";
            if (!(bophan == null)) {
                selectQuery = selectQuery + " AND Substr(fia15,1,1) = '" + bophan + "' ";
            }
            selectQuery = selectQuery + " group by fia15,fiaud03,fka02 order by fiaud03 ";

            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    //KT02(E)
    public Cursor getAll_fiaud03_sig(String tenxe, String bophan, String ngay) {
        try {
           /* String selectQuery = "select count(*) AS _id,fiaud03,fia15,fka02 from fia_file " +
                    " where fiaud03 not in (select distinct somay from tc_fac_table_kt02) AND ta_fia02_1='" + tenxe + "' AND fia15 NOT LIKE 'B%' " +
                    " group by fia15,fiaud03,fka02 order by fia15,fiaud03";*/

            String selectQuery = " select count(*) AS _id,fiaud03,fia15,fka02,'" + ngay + "' AS ngaysig from fia_file " +
                    " where ta_fia02_1='" + tenxe + "' ";
            if (!(bophan == null)) {
                selectQuery = selectQuery + " AND Substr(fia15,1,1) = '" + bophan + "' ";
            }
            selectQuery = selectQuery + " group by fia15,fiaud03,fka02 order by fiaud03,fia15 ";

            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_fiaud_sig_search(String tenxe, String bophan, String ngay) {
        try {
           /* String selectQuery = "select count(*) AS _id,fiaud03,fia15,fka02 from fia_file " +
                    " where fiaud03 not in (select distinct somay from tc_fac_table_kt02) AND ta_fia02_1='" + tenxe + "' AND fia15 NOT LIKE 'B%' " +
                    " group by fia15,fiaud03,fka02 order by fia15,fiaud03";*/

            String selectQuery = " select count(*) AS _id,somay_sig,mabp_sig,tebp_sig,ngay_sig,manv_sig from fia_up_sig_file " +
                    " where tenhinh_sig LIKE '%" + tenxe + "%' ";
            if (!(bophan == null)) {
                selectQuery = selectQuery + " AND mabp_sig LIKE '" + bophan + "%' ";
            }
            selectQuery = selectQuery + " group by mabp_sig,somay_sig,tebp_sig,tenhinh_sig order by somay_sig,mabp_sig ";

            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }



    public Cursor getAll_bp_search(String tenxe, String bophan, String ngay) {
        try {
           /* String selectQuery = "select count(*) AS _id,fiaud03,fia15,fka02 from fia_file " +
                    " where fiaud03 not in (select distinct somay from tc_fac_table_kt02) AND ta_fia02_1='" + tenxe + "' AND fia15 NOT LIKE 'B%' " +
                    " group by fia15,fiaud03,fka02 order by fia15,fiaud03";*/

            String selectQuery = " select count(*) AS _id,mabp_sig,tebp_sig,ngay_sig,manv_sig from fia_up_sigkt01_file WHERE substr(mabp_sig,1,2) not in ('04','05') group by mabp_sig,manv_sig order by mabp_sig";

            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_bp(String tenxe, String bophan, String ngay) {
        try {
            String selectQuery = " select count(*) AS _id,tc_fba007,tc_fba009,'" + ngay + "' AS ngaysig from tc_fba_file " +
                    " WHERE substr(tc_fba007,1,2) not in ('04','05') group by tc_fba007 order by tc_fba007";
            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_bp_BL(String tenxe, String bophan, String ngay) {
        try {
            String selectQuery = " select count(*) AS _id,tc_fba007,tc_fba009,'" + ngay + "' AS ngaysig from tc_fba_file " +
                    "WHERE substr(tc_fba007,1,2) in ('04','05') group by tc_fba007 order by tc_fba007 ";

            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public Cursor getAll_bp_BL_search(String tenxe, String bophan, String ngay) {
        try {
           /* String selectQuery = "select count(*) AS _id,fiaud03,fia15,fka02 from fia_file " +
                    " where fiaud03 not in (select distinct somay from tc_fac_table_kt02) AND ta_fia02_1='" + tenxe + "' AND fia15 NOT LIKE 'B%' " +
                    " group by fia15,fiaud03,fka02 order by fia15,fiaud03";*/

            String selectQuery = " select count(*) AS _id,mabp_sig,tebp_sig,ngay_sig,manv_sig from fia_up_sigkt01_file WHERE substr(mabp_sig,1,2) in ('04','05') group by mabp_sig,manv_sig order by mabp_sig";

            return db.rawQuery(selectQuery, null);

        } catch (Exception e) {
            return null;
        }
    }

    public boolean ins_fac_01(String g_kind, String xuser, String xngay, String xto) {
        //db=mCtx.openOrCreateDatabase(DATABASE_NAME,0,null);
        //將上傳資料彙整至json_table
        int count = 0;
        //ContentValues argsA = new ContentValues();
        Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_TC_FAA + " " +
                " WHERE tc_faa003='" + xuser + "' AND tc_faa002='" + xngay + "' ", null);
        mCount.moveToFirst();
        count = mCount.getInt(0);
        if (count == 0) {
            ContentValues argsC = new ContentValues();
            Cursor c = db.rawQuery("SELECT tc_fac004,'false' as tc_faa004 ,'' as tc_faa005,'' as tc_faa006,tc_fac007," +
                    " tc_fac006,tc_fac003,tc_fac001,0 as tc_faa011,'false' as tc_faa0012,'true' as tc_faa0013,'false' as tc_faa0014,'false' as tc_faa0015,'false' as tc_faa0016  FROM " + TABLE_NAME_TC_FAC +
                    " WHERE tc_fac002='" + g_kind + "'", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    argsC.put("tc_faa001", c.getString(0));
                    argsC.put("tc_faa002", xngay);
                    argsC.put("tc_faa003", xuser);
                    argsC.put("tc_faa004", c.getString(1));
                    argsC.put("tc_faa005", c.getString(2));
                    argsC.put("tc_faa006", c.getString(3));
                    argsC.put("tc_faa007", c.getString(4));
                    argsC.put("tc_faa008", c.getString(5));
                    argsC.put("tc_faa009", c.getString(6));
                    argsC.put("tc_faa010", c.getString(7));
                    argsC.put("tc_faa011", c.getString(8));
                    argsC.put("tc_faa012", c.getString(9));
                    argsC.put("tc_faa013", c.getString(10));
                    argsC.put("tc_faa014", c.getString(11));
                    argsC.put("tc_faa015", c.getString(12));
                    argsC.put("tc_faa016", c.getString(13));
                    argsC.put("tc_faa019", xto);
                    db.insert(TABLE_NAME_TC_FAA, null, argsC);
                } while (c.moveToNext());
            } else {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }


    public void call_insertPhotoData(String selectedDetail, String selectedDate, String selectedDepartment, String g_user, String fileName) {

        try {
            ContentValues args = new ContentValues();
            args.put("tc_fcf001", selectedDetail);
            args.put("tc_fcf002", selectedDate);
            args.put("tc_fcf003", selectedDepartment);
            args.put("tc_fcf004", g_user);
            args.put("tc_fcf005", fileName);
            args.put("tc_fcfpost", "N");
            //db.insert(TABLE_NAME_TC_FCF, null, args);

            //Cập nhật table chứ dữ liệu đã kiểm tra
            //call_update_tc_fce(selectedDetail, selectedDate, selectedDepartment, g_user);
        } catch (Exception e) {
        }
    }

    /*kt07*/

    public boolean ins_tc_cea(String g_tc_cea01, String g_tc_ceb03, String g_tc_ceb06, String g_tc_cebdate, String g_tc_cebuser) {
        //db=mCtx.openOrCreateDatabase(DATABASE_NAME,0,null);
        //將上傳資料彙整至json_table
        int count = 0;
        //ContentValues argsA = new ContentValues();
        Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_TC_CEA_IN + " " +
                " WHERE tc_cea01_in='" + g_tc_cea01 + "' AND tc_ceb06_in='" + g_tc_ceb06 + "' AND tc_ceb03_in='" + g_tc_ceb03 + "' AND tc_cebdate_in='" + g_tc_cebdate + "' ", null);
        mCount.moveToFirst();
        count = mCount.getInt(0);
        if (count == 0) {
            ContentValues argsC = new ContentValues();
            Cursor c = db.rawQuery("SELECT tc_cea03,tc_cea05,tc_cea06,tc_cea07  FROM " + TABLE_NAME_TC_CEA +
                    " WHERE tc_cea01='" + g_tc_cea01 + "' ", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    argsC.put("tc_cea01_in", g_tc_cea01);
                    argsC.put("tc_cea03_in", c.getString(0));
                    argsC.put("tc_cea05_in", c.getString(1));
                    argsC.put("tc_cea06_in", c.getString(2));
                    argsC.put("tc_cea07_in", c.getString(3));
                    argsC.put("tc_ceb03_in", g_tc_ceb03);
                    argsC.put("tc_ceb06_in", g_tc_ceb06);
                    //argsC.put("tc_ceb04_in", c.getString(5));
                    //argsC.put("tc_ceb05_in", c.getString(6));
                    argsC.put("tc_cebdate_in", g_tc_cebdate);
                    argsC.put("tc_cebuser_in", g_tc_cebuser);
                    db.insert(TABLE_NAME_TC_CEA_IN, null, argsC);
                } while (c.moveToNext());
            } else {
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public Cursor getUserData(String g_UserID) {
        String selectQuery = "SELECT * FROM cpf_file WHERE cpf01 = '" + g_UserID + "' ";
        return db.rawQuery(selectQuery, null);
    }

    public Cursor getAll_tc_cea_kt07(String g_tc_cea01, String g_tc_ceb03, String g_tc_ceb06, String g_tc_cebdate, String g_tc_cebuser) {
        Cursor a;
        try {
            int count = 0;
            ContentValues argsA = new ContentValues();
            /*Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_TC_CEA_IN + " " +
                    " WHERE tc_cea01_in='" + g_tc_cea01 + "'", null);*/
            Cursor mCount = db.rawQuery("SELECT count(*) FROM " + TABLE_NAME_TC_CEA_IN + " " +
                    " WHERE tc_cea01_in='" + g_tc_cea01 + "' AND tc_ceb06_in='" + g_tc_ceb06 + "' AND tc_ceb03_in='" + g_tc_ceb03 + "' AND tc_cebdate_in='" + g_tc_cebdate + "' ", null);
            mCount.moveToFirst();
            count = mCount.getInt(0);
            if (count > 0) {
                String selectQuery = "SELECT tc_cea03_in,tc_cea04,tc_cea05_in,tc_cea06_in,tc_cea07_in,tc_ceb04_in,tc_ceb05_in " +
                        " FROM " + TABLE_NAME_TC_CEA_IN + "," + TABLE_NAME_TC_CEA + " " +
                        " WHERE tc_cea01_in='" + g_tc_cea01 + "' AND tc_cea03_in=tc_cea03 AND tc_cea01_in=tc_cea01 AND tc_ceb06_in='" + g_tc_ceb06 + "' AND tc_ceb03_in='" + g_tc_ceb03 + "' AND tc_cebdate_in='" + g_tc_cebdate + "'";
                return db.rawQuery(selectQuery, null);
            } else {
                String selectQuery1 = "SELECT tc_cea03 AS tc_cea03_in,tc_cea04,tc_cea05 AS tc_cea05_in,tc_cea06 AS tc_cea06_in,tc_cea07 AS tc_cea07_in,0 AS tc_ceb04_in,0 AS tc_ceb05_in  FROM " + TABLE_NAME_TC_CEA + " " +
                        " WHERE tc_cea01='" + g_tc_cea01 + "' ";
                return db.rawQuery(selectQuery1, null);
            }
            //SQLiteDatabase db = this.getWritableDatabase();
            //String selectQuery = "SELECT tc_fac003,tc_fac006,tc_fac004 FROM " + TABLE_NAME_TC_FAC + " WHERE tc_fac002='" + g_kind + "' AND tc_fac001='" + g_kind1+"'";

        } catch (Exception e) {
            return null;
        }
    }
}
