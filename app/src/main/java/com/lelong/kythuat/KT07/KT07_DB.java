package com.lelong.kythuat.KT07;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Switch;

import com.lelong.kythuat.Constant_Class;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class KT07_DB {
    private Context mCtx = null;
    //String DATABASE_NAME = "KyThuatDB02.db";
    String DATABASE_NAME = "KyThuatDB.db";
    public static SQLiteDatabase db = null;
    SimpleDateFormat dateFormat;
    private final static String TABLE_NAME_TC_CEB = "tc_ceb_file";
    private final static String tc_ceb01 = "tc_ceb01"; ////Loai
    private final static String tc_ceb02 = "tc_ceb02"; ////STT
    private final static String tc_ceb03 = "tc_ceb03"; ////Ngày tiêu hao
    private final static String tc_ceb04 = "tc_ceb04"; ////Bảng số đo
    private final static String tc_ceb05 = "tc_ceb05"; ////Lượng sử dụng
    private final static String tc_ceb06 = "tc_ceb06"; ////Khoảng thời gian (AM/PM)
    private final static String tc_ceb07 = "tc_ceb07";/////Ghi chú
    private final static String tc_cebdate = "tc_cebdate"; ////Ngày nhập
    private final static String tc_cebuser = "tc_cebuser"; ////Nhân viên nhập
    private final static String tc_cebstatus = "tc_cebstatus"; ////Nhân viên nhập

    String CREATE_TABLE_NAME_TC_CEB = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TC_CEB + " ("
            + tc_ceb01 + " TEXT," + tc_ceb02 + " TEXT," + tc_ceb03 + " TEXT ,"
            + tc_ceb04 + " TEXT," + tc_ceb05 + " TEXT," + tc_ceb06 + " TEXT," + tc_ceb07 + " TEXT,"
            + tc_cebdate + " TEXT, " + tc_cebuser + " TEXT," + tc_cebstatus + " TEXT  )";


    public KT07_DB(Context ctx) {
        this.mCtx = ctx;
    }

    public void open() throws SQLException {
        db = mCtx.openOrCreateDatabase(DATABASE_NAME, 0, null);
    }

    public void create_table() {
        try {
            db.execSQL(CREATE_TABLE_NAME_TC_CEB);
        } catch (Exception e) {

        }
    }

    public void drop_table() {
        try {
            final String DROP_TABLE_NAME_TC_CEB = "DROP TABLE IF EXISTS " + TABLE_NAME_TC_CEB;
            db.execSQL(DROP_TABLE_NAME_TC_CEB);
            db.close();
        } catch (Exception e) {

        }
    }

    public void delete_table() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date threeMonthsAgo = calendar.getTime();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String threeMonthsAgoString = dateFormat.format(threeMonthsAgo);

        // Xây dựng điều kiện WHERE
        String whereClause = "substr(tc_cebdate, 1, 10) <= '" + threeMonthsAgoString + "'";

        // Thực hiện xóa dữ liệu

        db.delete(TABLE_NAME_TC_CEB, whereClause, null);


    }

    public Cursor get_menu_data(String ID) {
        Cursor c = db.rawQuery(" SELECT SUBSTR(cpf281,1,1) FROM cpf_file WHERE cpf01 = '" + ID + "' ", null);
        c.moveToFirst();
        String g_cpf281 = c.getString(0);
        if (g_cpf281 == null) {
            g_cpf281 = "D"; // Gán giá trị mặc định nếu chuỗi là null
        }
        c.close();

        String selectQuery = null;
        if (g_cpf281.length() > 0) {
            String g_xuong = Constant_Class.UserFactory;
            if (g_xuong.equals("DH")) {
                selectQuery = " SELECT tc_cea01, tc_cea02, loai FROM ( " +
                        " SELECT DISTINCT tc_cea01, tc_cea02, " +
                        " (CASE WHEN UPPER(tc_cea02) LIKE '%ĐIỆN%' THEN 'dien' " +
                        " WHEN tc_cea01 = 'DHO' THEN 'dien' " +
                        " WHEN UPPER(tc_cea02) LIKE '%NƯỚC%' THEN 'nuoc' " +
                        " WHEN UPPER(tc_cea02) LIKE '%GAS%' THEN 'gas' END) AS loai " +
                        " FROM tc_cea_file WHERE tc_cea01 like '" + g_cpf281 + "%' " +
                        " ) ORDER BY (case when loai = 'nuoc' then 3 when loai = 'dien' then 2 when loai = 'gas' then 1 end)";
            } else {
                selectQuery = " SELECT tc_cea01, tc_cea02, loai FROM ( " +
                        " SELECT DISTINCT tc_cea01, tc_cea02, " +
                        " (CASE WHEN UPPER(tc_cea02) LIKE '%ĐIỆN%' THEN 'dien' " +
                        " WHEN UPPER(tc_cea02) LIKE '%NƯỚC%' THEN 'nuoc' " +
                        " WHEN UPPER(tc_cea02) LIKE '%GAS%' THEN 'gas' END) AS loai " +
                        " FROM tc_cea_file WHERE tc_cea01 like '" + g_cpf281 + "%' " +
                        " ) ORDER BY (case when loai = 'nuoc' then 3 when loai = 'dien' then 2 when loai = 'gas' then 1 end)";
            }

        }
        return db.rawQuery(selectQuery, null);
    }

    public Cursor get_menu_factory(String ID) {
        Cursor c = db.rawQuery("SELECT cpf281 FROM cpf_file WHERE cpf01 = '" + ID + "' ", null);
        c.moveToFirst();
        String g_cpf281 = c.getString(0);
        if (g_cpf281 == null) {
            g_cpf281 = "DH"; // Gán giá trị mặc định nếu chuỗi là null
        }
        c.close();

        String selectQuery = null;
        if (g_cpf281.length() > 0) {
            selectQuery = " select DISTINCT tc_cea09 from tc_cea_file where substr(tc_cea01,1,2) = '" + g_cpf281 + "' AND tc_cea09 != 'null' order by tc_cea09 ";
        }
        return db.rawQuery(selectQuery, null);
    }

    public Integer getCheck(String g_tc_cebdate, String g_tc_ceb06) {
        String selectQuery = null;
        selectQuery = " SELECT count(*) FROM tc_ceb_file WHERE tc_cebdate = '" + g_tc_cebdate + "' AND tc_ceb06 = '" + g_tc_ceb06 + "' ";
        Cursor a = db.rawQuery(selectQuery, null);
        a.moveToFirst();
        Integer count = a.getInt(0);
        return count;
    }

    public Cursor getAll_tc_cea_data(String g_title, String g_tc_cebdate, String g_tc_ceb06, String g_dk, String g_tc_ceb03) {
        String selectQuery = null;
        if (g_title.startsWith("D") || g_title.startsWith("B")) {
            //Fill Data của loại tiêu thụ
            /*selectQuery = " SELECT tc_cea01,tc_cea03,tc_cea04,(tc_cea05||'/'||CASE WHEN tc_cea09 = 'null' THEN '' ELSE tc_cea09 END) AS tc_cea05 ,tc_cea06,tc_cea08,  " +
                    " COALESCE((select tc_ceb04 from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 and  tc_cebdate||tc_ceb06 < '"+g_tc_cebdate+""+g_tc_ceb06+"' AND tc_ceb03 = '"+g_tc_ceb03+"' " +
                    " order by tc_cebdate desc ,tc_ceb06 desc)  LIMIT 1 ),0) AS tc_ceb04_old, " +
                    " COALESCE((select tc_cebdate||'-'||tc_ceb06  from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 AND tc_cebdate||tc_ceb06 < '"+g_tc_cebdate+""+g_tc_ceb06+"' AND tc_ceb03 = '"+g_tc_ceb03+"' "+
                    " order by tc_cebdate desc ,tc_ceb06 desc)  LIMIT 1 ),' ') AS tc_cebdate_ceb06, "+
                    " COALESCE((SELECT tc_ceb04 FROM tc_ceb_file WHERE tc_ceb01 = tc_cea01 AND tc_ceb02 = TC_CEA03 AND tc_cebdate = '" + g_tc_cebdate + "' AND tc_ceb06 = '"+g_tc_ceb06+"' AND tc_ceb03 = '"+g_tc_ceb03+"' ),0) AS  tc_ceb04, " +
                    "((COALESCE((SELECT tc_ceb04 FROM tc_ceb_file WHERE tc_ceb01 = tc_cea01 AND tc_ceb02 = TC_CEA03 AND tc_cebdate = '" + g_tc_cebdate + "' AND tc_ceb06 = '"+g_tc_ceb06+"' AND tc_ceb03 = '"+g_tc_ceb03+"' ),0)) - (COALESCE((select tc_ceb04  from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 and  tc_cebdate||tc_ceb06 < '"+g_tc_cebdate+""+g_tc_ceb06+"' AND tc_ceb03 = '"+g_tc_ceb03+"' order by tc_cebdate desc ,tc_ceb06 desc)  LIMIT 1 ),0) )) AS tc_ceb04_diff" +
                    " FROM tc_cea_file WHERE tc_cea01 = '" + g_title + "' " +

                    " ORDER BY TC_CEA03 ";*/
            selectQuery = " SELECT tc_cea01,tc_cea03,tc_cea04,(tc_cea05||'/'||CASE WHEN tc_cea09 = 'null' THEN '' ELSE tc_cea09 END) AS tc_cea05 ,tc_cea06,tc_cea08,  " +
                    " COALESCE((select tc_ceb04 from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 and  tc_ceb03||tc_ceb06 < '" + g_tc_ceb03 + "" + g_tc_ceb06 + "'  " +
                    " order by tc_ceb03 desc ,tc_ceb06 desc)  LIMIT 1 ),0) AS tc_ceb04_old, " +
                    " COALESCE((select tc_ceb03||'-'||tc_ceb06  from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 AND tc_ceb03||tc_ceb06 < '" + g_tc_ceb03 + "" + g_tc_ceb06 + "'  " +
                    " order by tc_ceb03 desc ,tc_ceb06 desc)  LIMIT 1 ),' ') AS tc_ceb03_ceb06, " +
                    " COALESCE((SELECT tc_ceb04 FROM tc_ceb_file WHERE tc_ceb01 = tc_cea01 AND tc_ceb02 = TC_CEA03  AND tc_ceb06 = '" + g_tc_ceb06 + "' AND tc_ceb03 = '" + g_tc_ceb03 + "' ),0) AS  tc_ceb04, " +
                    "((COALESCE((SELECT tc_ceb04 FROM tc_ceb_file WHERE tc_ceb01 = tc_cea01 AND tc_ceb02 = TC_CEA03 AND tc_ceb06 = '" + g_tc_ceb06 + "' AND tc_ceb03 = '" + g_tc_ceb03 + "' ),0)) - (COALESCE((select tc_ceb04  from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 and  tc_ceb03||tc_ceb06 < '" + g_tc_ceb03 + "" + g_tc_ceb06 + "'  order by tc_ceb03 desc ,tc_ceb06 desc)  LIMIT 1 ),0) )) AS tc_ceb04_diff" +
                    " ,COALESCE((SELECT tc_ceb07 FROM tc_ceb_file WHERE tc_ceb01 = tc_cea01 AND tc_ceb02 = TC_CEA03  AND tc_ceb06 = '" + g_tc_ceb06 + "' AND tc_ceb03 = '" + g_tc_ceb03 + "' ),'') AS  tc_ceb07 " +
                    " ,COALESCE((select tc_ceb07 from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 and  tc_ceb03||tc_ceb06 < '" + g_tc_ceb03 + "" + g_tc_ceb06 + "'  " +
                    " order by tc_ceb03 desc ,tc_ceb06 desc)  LIMIT 1 ),'') AS tc_ceb07_old " +
                    " FROM tc_cea_file WHERE tc_cea01 = '" + g_title + "' " +

                    " ORDER BY TC_CEA03 ";
        }

        if (g_title.length() == 1) {
            //Fill Data của Xưởng
            /*selectQuery = " SELECT tc_cea01,tc_cea03,tc_cea04,(tc_cea05||'/'||CASE WHEN tc_cea09 = 'null' THEN '' ELSE tc_cea09 END ) AS tc_cea05,tc_cea06,tc_cea08, " +
                    " COALESCE((select tc_ceb04  from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 and  tc_cebdate||tc_ceb06 < '"+g_tc_cebdate+""+g_tc_ceb06+"' AND tc_ceb03 = '"+g_tc_ceb03+"' " +
                    " order by tc_cebdate desc ,tc_ceb06 desc)  LIMIT 1 ),0) AS tc_ceb04_old, " +
                    " COALESCE((select tc_cebdate||'-'||tc_ceb06  from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 AND  tc_cebdate||tc_ceb06 < '"+g_tc_cebdate+""+g_tc_ceb06+"' AND tc_ceb03 = '"+g_tc_ceb03+"' "+
                    " order by tc_cebdate desc ,tc_ceb06 desc)  LIMIT 1 ),' ') AS tc_cebdate_ceb06, "+
                    " COALESCE((SELECT tc_ceb04 FROM tc_ceb_file WHERE tc_ceb01 = tc_cea01 AND tc_ceb02 = TC_CEA03 AND tc_cebdate = '" + g_tc_cebdate + "' AND tc_ceb06 = '"+g_tc_ceb06+"' AND tc_ceb03 = '"+g_tc_ceb03+"' ),0) AS  tc_ceb04 ," +
                    "((COALESCE((SELECT tc_ceb04 FROM tc_ceb_file WHERE tc_ceb01 = tc_cea01 AND tc_ceb02 = TC_CEA03 AND tc_cebdate = '" + g_tc_cebdate + "' AND tc_ceb06 = '"+g_tc_ceb06+"' AND tc_ceb03 = '"+g_tc_ceb03+"' ),0))" +
                    " - (COALESCE((select tc_ceb04  from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 and  tc_cebdate||tc_ceb06 < '"+g_tc_cebdate+""+g_tc_ceb06+"' AND tc_ceb03 = '"+g_tc_ceb03+"' order by tc_cebdate desc ,tc_ceb06 desc) " +
                    " LIMIT 1 ),0) )) AS tc_ceb04_diff" +

                    " FROM tc_cea_file WHERE substr(tc_cea01,1,2) IN ('"+ Constant_Class.UserFactory +"')  AND tc_cea09 = '" + g_title + "' " ;*/
            selectQuery = " SELECT tc_cea01,tc_cea03,tc_cea04,(tc_cea05||'/'||CASE WHEN tc_cea09 = 'null' THEN '' ELSE tc_cea09 END ) AS tc_cea05,tc_cea06,tc_cea08, " +
                    " COALESCE((select tc_ceb04  from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 and  tc_ceb03||tc_ceb06 < '" + g_tc_ceb03 + "" + g_tc_ceb06 + "'  " +
                    " order by tc_ceb03 desc ,tc_ceb06 desc)  LIMIT 1 ),0) AS tc_ceb04_old, " +
                    " COALESCE((select tc_ceb03||'-'||tc_ceb06  from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 AND  tc_ceb03||tc_ceb06 < '" + g_tc_ceb03 + "" + g_tc_ceb06 + "'  " +
                    " order by tc_ceb03 desc ,tc_ceb06 desc)  LIMIT 1 ),' ') AS tc_ceb03_ceb06, " +
                    " COALESCE((SELECT tc_ceb04 FROM tc_ceb_file WHERE tc_ceb01 = tc_cea01 AND tc_ceb02 = TC_CEA03  AND tc_ceb06 = '" + g_tc_ceb06 + "' AND tc_ceb03 = '" + g_tc_ceb03 + "' ),0) AS  tc_ceb04 ," +
                    "((COALESCE((SELECT tc_ceb04 FROM tc_ceb_file WHERE tc_ceb01 = tc_cea01 AND tc_ceb02 = TC_CEA03  AND tc_ceb06 = '" + g_tc_ceb06 + "' AND tc_ceb03 = '" + g_tc_ceb03 + "' ),0))" +
                    " - (COALESCE((select tc_ceb04  from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 and  tc_ceb03||tc_ceb06 < '" + g_tc_ceb03 + "" + g_tc_ceb06 + "'  order by tc_ceb03 desc ,tc_ceb06 desc) " +
                    " LIMIT 1 ),0) )) AS tc_ceb04_diff " +
                    " ,COALESCE((SELECT tc_ceb07 FROM tc_ceb_file WHERE tc_ceb01 = tc_cea01 AND tc_ceb02 = TC_CEA03  AND tc_ceb06 = '" + g_tc_ceb06 + "' AND tc_ceb03 = '" + g_tc_ceb03 + "' ),'') AS  tc_ceb07 " +
                    " ,COALESCE((select tc_ceb07 from  (SELECT * FROM tc_ceb_file   WHERE  tc_ceb02 =TC_CEA03 AND tc_ceb01 = tc_cea01 and  tc_ceb03||tc_ceb06 < '" + g_tc_ceb03 + "" + g_tc_ceb06 + "'  " +
                    " order by tc_ceb03 desc ,tc_ceb06 desc)  LIMIT 1 ),'') AS tc_ceb07_old " +
                    " FROM tc_cea_file WHERE substr(tc_cea01,1,2) IN ('" + Constant_Class.UserFactory + "')  AND tc_cea09 = '" + g_title + "' ";

            if (g_dk != null) {
                switch (g_dk) {
                    case "Điện":
                        selectQuery += " AND substr(tc_cea01,3,1) IN('D') ";
                        break;
                    case "Nước":
                        selectQuery += " AND substr(tc_cea01,3,1) IN('M','R','S','N') ";
                        break;
                    case "Gas":
                        selectQuery += " AND substr(tc_cea01,3,1) IN('G') ";
                        break;

                }


            }
            selectQuery += " ORDER BY tc_cea01,tc_cea05,tc_cea03 ";
        }

        return db.rawQuery(selectQuery, null);
    }

    public Cursor getAll_tc_ceb_data(String g_title, String g_tc_ceb03) {
        String selectQuery = null;
        if (g_title == "1=1") {
            selectQuery = " SELECT tc_ceb01,tc_ceb02,tc_ceb03,tc_ceb04,tc_ceb05,tc_cebdate,tc_cebuser,tc_ceb06,tc_ceb07 FROM tc_ceb_file " +
                    "  WHERE  " + g_title + " AND tc_ceb03 <= '" + g_tc_ceb03 + "' AND tc_cebstatus IN ('N','E') ORDER BY tc_ceb03,tc_ceb06 ";
        } else {
            //Fill Data của loại tiêu thụ
            selectQuery = " SELECT tc_ceb01,tc_ceb02,tc_ceb03,tc_ceb04,tc_ceb05,tc_cebdate,tc_cebuser, tc_ceb06,tc_ceb07 FROM tc_ceb_file " +
                    "  WHERE tc_ceb01 IN ('" + g_title + "') AND tc_ceb03 <= '" + g_tc_ceb03 + "' AND tc_cebstatus IN('N','E')  ORDER BY tc_ceb03,tc_ceb06 ";
        }
        return db.rawQuery(selectQuery, null);
    }

    public String ins_tc_ceb_file(String g_tc_ceb01, String g_tc_ceb02, String g_tc_ceb03,
                                  String g_tc_ceb04, String g_tc_ceb05, String g_tc_ceb06, String g_tc_cebdate, String g_tc_cebuser, String g_tc_ceb07) {
        try {

            String selectQ = null;
            selectQ = "SELECT count(*) FROM tc_ceb_file WHERE tc_ceb01 = '" + g_tc_ceb01 + "' " +
                    " AND tc_ceb02 = '" + g_tc_ceb02 + "' AND tc_ceb03 = '" + g_tc_ceb03 + "' " +
                    "  AND tc_ceb06 = '" + g_tc_ceb06 + "' ";
            Cursor a = db.rawQuery(selectQ, null);
            a.moveToFirst();
            Integer count = a.getInt(0);

            if (count == 0) {
                ContentValues args = new ContentValues();
                args.put("tc_ceb01", g_tc_ceb01);
                args.put("tc_ceb02", g_tc_ceb02);
                args.put("tc_ceb03", g_tc_ceb03);
                args.put("tc_ceb04", g_tc_ceb04);
                args.put("tc_ceb05", g_tc_ceb05);
                args.put("tc_ceb06", g_tc_ceb06);
                args.put("tc_cebdate", g_tc_cebdate);
                args.put("tc_cebuser", g_tc_cebuser);
                args.put("tc_cebstatus", "N");
                args.put("tc_ceb07", g_tc_ceb07);
                db.insert(TABLE_NAME_TC_CEB, null, args);
            } else if (count >= 1) {
                String sqlquery = null;
                sqlquery = "SELECT tc_cebstatus FROM tc_ceb_file WHERE tc_ceb01 = '" + g_tc_ceb01 + "' " +
                        " AND tc_ceb02 = '" + g_tc_ceb02 + "' AND tc_ceb03 = '" + g_tc_ceb03 + "' " +
                        "  AND tc_ceb06 = '" + g_tc_ceb06 + "' ";
                Cursor ab = db.rawQuery(sqlquery, null);
                ab.moveToFirst();
                String status = ab.getString(0);
                if (status.equals("Y")) {
                    db.execSQL("UPDATE " + TABLE_NAME_TC_CEB + " SET tc_ceb04 = '" + g_tc_ceb04 + "', tc_cebstatus = 'E' " +
                            "WHERE tc_ceb01 = '" + g_tc_ceb01 + "' AND tc_ceb02 = '" + g_tc_ceb02 + "' " +
                            "AND tc_ceb03 = '" + g_tc_ceb03 + "'  " +
                            "AND tc_ceb06 = '" + g_tc_ceb06 + "' ");
                } else {
                    db.execSQL("UPDATE " + TABLE_NAME_TC_CEB + " SET tc_ceb04 = '" + g_tc_ceb04 + "',tc_ceb07 = '" + g_tc_ceb07 + "' " +
                            "WHERE tc_ceb01 = '" + g_tc_ceb01 + "' AND tc_ceb02 = '" + g_tc_ceb02 + "' " +
                            "AND tc_ceb03 = '" + g_tc_ceb03 + "'  " +
                            "AND tc_ceb06 = '" + g_tc_ceb06 + "' ");
                }
            }
            return "TRUE";

        } catch (Exception e) {
            return "FALSE";
        }
    }

    public String update_status(String g_tc_ceb01, String g_tc_ceb02, String g_tc_ceb03, String g_tc_ceb06, String g_tc_cebdate) {
        try {
            String l_tc_ceb06 = "";
            if (g_tc_ceb06.equals("0")) {
                l_tc_ceb06 = "AM";
            } else {
                l_tc_ceb06 = "PM";
            }
            db.execSQL("UPDATE " + TABLE_NAME_TC_CEB + " SET tc_cebstatus = 'Y' " +
                    "WHERE tc_ceb01 = '" + g_tc_ceb01 + "' AND tc_ceb02 = '" + g_tc_ceb02 + "' " +
                    "AND tc_ceb03 = '" + g_tc_ceb03 + "'  " +
                    "AND tc_ceb06 = '" + l_tc_ceb06 + "'  ");
            return "TRUE";

        } catch (Exception ex) {
            return "FALSE";
        }
    }

    public Integer getCheckKT(String g_tc_cea01, String g_tc_cea03, String g_tc_ceb03, String g_tc_cebdate, String g_tc_cebuser, String g_tc_ceb06) {
        String selectQuery = null;
        selectQuery = " SELECT count(*) FROM tc_ceb_file WHERE tc_ceb01='" + g_tc_cea01 + "' AND tc_ceb02 ='" + g_tc_cea03 + "' AND tc_ceb03='" + g_tc_ceb03 + "' " +
                " AND tc_ceb06 = '" + g_tc_ceb06 + "' AND tc_cebstatus IN ('Y','E')  ";

        Cursor a = db.rawQuery(selectQuery, null);
        a.moveToFirst();
        Integer count = a.getInt(0);
        return count;
    }

    public Cursor getdataKT(String g_tc_cea01, String g_tc_cea03) {
        String selectQuery = null;
        selectQuery = "SELECT tc_ceb03 AS tc_ceb03_old, tc_ceb06 AS tc_ceb06_old  " +
                "FROM (SELECT tc_ceb03, tc_ceb06, tc_ceb04  FROM tc_ceb_file  " +
                "WHERE tc_ceb01='" + g_tc_cea01 + "' AND tc_ceb02='" + g_tc_cea03 + "' AND tc_cebstatus IN ('Y','E') ORDER BY tc_ceb03 DESC, tc_ceb06 DESC)  LIMIT 1";
        return db.rawQuery(selectQuery, null);
    }

    public String update_dataKT(String g_tc_ceb01, String g_tc_ceb02, String g_tc_ceb03, String g_tc_ceb04, String g_tc_ceb05, String g_tc_ceb06, String g_tc_cebdate) {
        try {
            String l_tc_ceb06 = "";
            if (g_tc_ceb06.equals("0")) {
                l_tc_ceb06 = "AM";
            } else {
                l_tc_ceb06 = "PM";
            }
            db.execSQL("UPDATE " + TABLE_NAME_TC_CEB + " SET tc_ceb04 = '" + g_tc_ceb04 + "', tc_ceb05 = '" + g_tc_ceb05 + "' " +
                    " WHERE tc_ceb01 = '" + g_tc_ceb01 + "' AND tc_ceb02 = '" + g_tc_ceb02 + "' " +
                    " AND tc_ceb03 = '" + g_tc_ceb03 + "' AND tc_cebstatus = 'Y' " +
                    " AND tc_ceb06 = '" + l_tc_ceb06 + "'  ");
            return "TRUE";
        } catch (Exception ex) {
            return "FALSE";
        }
    }

    public String update_tc_ceb_file(String g_tc_ceb01, String g_tc_ceb02, String g_tc_ceb03,
                                     String g_tc_ceb04, String g_tc_ceb05, String g_tc_ceb06, String g_tc_cebdate, String g_tc_cebuser) {
        try {
            String l_tc_ceb06 = "";
            if (g_tc_ceb06.equals("0")) {
                l_tc_ceb06 = "AM";
            } else {
                l_tc_ceb06 = "PM";
            }

            String selectQ = null;
            selectQ = "SELECT count(*) FROM tc_ceb_file WHERE tc_ceb01 = '" + g_tc_ceb01 + "' " +
                    " AND tc_ceb02 = '" + g_tc_ceb02 + "' AND tc_ceb03 = '" + g_tc_ceb03 + "' " +
                    " AND tc_ceb06 = '" + l_tc_ceb06 + "' ";
            Cursor a = db.rawQuery(selectQ, null);
            a.moveToFirst();
            Integer count = a.getInt(0);

            if (count == 0) {
                ContentValues args = new ContentValues();
                args.put("tc_ceb01", g_tc_ceb01);
                args.put("tc_ceb02", g_tc_ceb02);
                args.put("tc_ceb03", g_tc_ceb03);
                args.put("tc_ceb04", g_tc_ceb04);
                args.put("tc_ceb05", g_tc_ceb05);
                args.put("tc_ceb06", l_tc_ceb06);
                args.put("tc_cebdate", g_tc_cebdate);
                args.put("tc_cebuser", g_tc_cebuser);
                args.put("tc_cebstatus", "Y");

                db.insert(TABLE_NAME_TC_CEB, null, args);
            } else if (count >= 1) {
                db.execSQL("UPDATE " + TABLE_NAME_TC_CEB + " SET tc_ceb04 = '" + g_tc_ceb04 + "', tc_ceb05 = '" + g_tc_ceb05 + "' " +
                        "WHERE tc_ceb01 = '" + g_tc_ceb01 + "' AND tc_ceb02 = '" + g_tc_ceb02 + "' " +
                        "AND tc_ceb03 = '" + g_tc_ceb03 + "'  " +
                        "AND tc_ceb06 = '" + l_tc_ceb06 + "' ");

            }
            return "TRUE";

        } catch (Exception e) {
            return "FALSE";
        }
    }

    public Cursor getdata_check(String g_tc_cea01, String g_tc_cea03, String g_tc_ceb03, String g_tc_ceb06) {
        String selectQuery = null;
        selectQuery = " SELECT tc_ceb01,tc_ceb02,tc_ceb03,tc_ceb06 FROM " + TABLE_NAME_TC_CEB + "  WHERE tc_ceb01='" + g_tc_cea01 + "' AND tc_ceb02 ='" + g_tc_cea03 + "' AND tc_ceb03='" + g_tc_ceb03 + "' " +
                " AND tc_ceb06 = '" + g_tc_ceb06 + "' ";
        return db.rawQuery(selectQuery, null);
    }

    public String appendUPDAE(String tc_ceb01, String tc_ceb02, String tc_ceb03, String tc_cebdate, String l_giatri, String tc_ceb06, String tencot) {
        try {
            ContentValues args = new ContentValues();
            db.execSQL("UPDATE " + TABLE_NAME_TC_CEB + " SET " + tencot + "='" + l_giatri + "' WHERE tc_ceb01='" + tc_ceb01 + "'  " +
                    " AND tc_ceb02='" + tc_ceb02 + "' AND tc_ceb03='" + tc_ceb03 + "' and tc_cebstatus='N' AND tc_ceb06='" + tc_ceb06 + "' " +
                    " AND tc_cebdate='" + tc_cebdate + "'");
            return "TRUE";
        } catch (Exception e) {
            return "FALSE";
        }
    }

    public Integer Check_insert(String tc_ceb01, String tc_ceb02, String tc_ceb03, String tc_cebdate, String tc_ceb06) {
        String selectQuery = null;
        selectQuery = " SELECT count(*) FROM tc_ceb_file WHERE tc_ceb01='" + tc_ceb01 + "' AND tc_ceb02='" + tc_ceb02 + "' " +
                " AND tc_ceb03='" + tc_ceb03 + "' and tc_cebstatus='N' AND tc_cebdate = '" + tc_cebdate + "' AND tc_ceb06 = '" + tc_ceb06 + "' ";
        Cursor a = db.rawQuery(selectQuery, null);
        a.moveToFirst();
        Integer count = a.getInt(0);
        return count;
    }
}