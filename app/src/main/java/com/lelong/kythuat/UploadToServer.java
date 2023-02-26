package com.lelong.kythuat;

import android.database.Cursor;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UploadToServer {

    //Cursor 轉 Json
    public JSONArray cur2Json(Cursor cursor) {
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        return resultSet;
    }

    public String upload_all(JSONObject ujobject, String apiUrl) {
        String res = null;
        if (ujobject.length() > 0){
            HttpURLConnection conn = null;
            try {
                URL url = new URL(apiUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(999999);
                conn.setReadTimeout(999999);
                conn.setDoInput(true); //允許輸入流，即允許下載
                conn.setDoOutput(true); //允許輸出流，即允許上傳

                OutputStream os = conn.getOutputStream();
                DataOutputStream writer = new DataOutputStream(os);
                writer.write(ujobject.toString().getBytes("UTF-8"));
                writer.flush();
                writer.close();
                os.close();
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String result = reader.readLine();
                reader.close();
                res = result;
            } catch (Exception ex) {
                res = "false";
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
        return res;
    }
}
