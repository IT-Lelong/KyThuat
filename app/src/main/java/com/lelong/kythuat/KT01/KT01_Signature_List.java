package com.lelong.kythuat.KT01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.KT02.KT02_DB;
import com.lelong.kythuat.KT02.KT02_Interface;
import com.lelong.kythuat.KT02.List_Signature;
import com.lelong.kythuat.KT02.Retrofit2.APIYtils_Sig;
import com.lelong.kythuat.KT02.Retrofit2.DataClient_Sig;
import com.lelong.kythuat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KT01_Signature_List extends AppCompatActivity implements KT01_Interface {
    private Create_Table createTable = null;
    private KT01_DB kt01Db = null;
    Cursor cursor;
    ListView lv_searchsig;
    String g_tc_faa001,g_tenxe,g_fia15 ;
    SimpleDateFormat dateFormatKT02 = new SimpleDateFormat("yyyy-MM-dd");
    JSONObject ujobject;
    JSONArray jsonupload;
    String g_server = "";
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt01_signature_list);
        lv_searchsig = findViewById(R.id.lv_search_signature);
        createTable = new Create_Table(getApplicationContext());
        createTable.open();
        kt01Db = new KT01_DB(getApplicationContext());
        kt01Db.open();
        Bundle getbundle = getIntent().getExtras();
        g_tenxe = getbundle.getString("G_TENXE");
        g_server = getString(R.string.server);

        LV_Detail_sig();
    }
    private void LV_Detail_sig(){
        String g_bp= Constant_Class.UserFactory;
        if (g_bp.equals("DH") ) {
            g_fia15 = "D";
        }
        if (g_bp.equals("BL")){
            g_fia15 = "B";
        }
        String ngaysig = dateFormatKT02.format(new Date()).toString();
        cursor = createTable.getAll_bp(g_tenxe,g_fia15,ngaysig);
        Drawable drawable_blue = getResources().getDrawable(R.drawable.button_kt_blue);
        Drawable drawable_green = getResources().getDrawable(R.drawable.button_kt_green);
        KT01_List_Signature kt01ListSignature = new KT01_List_Signature(this,
                R.layout.kt01_signature_row, cursor,
                new String[]{"_id", "tc_fba007", "tc_fba009","ngaysig"},
                new int[]{R.id.tv_stt, R.id.tv_mabp,R.id.tv_tenbp,R.id.tv_ngaysig},this,drawable_blue,drawable_green,g_tenxe);

        kt01ListSignature.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if (view.getId() == R.id.tv_stt) {
                    int rowNumber = cursor.getPosition() + 1;
                    ((TextView) view).setText(String.valueOf(rowNumber));
                    return true;
                }
                return false;
            }
        });
        lv_searchsig.setAdapter(kt01ListSignature);
    }

    @Override
    public void takefoto(Context applicationContext, String key) {

    }

    @Override
    public void loadData_Sig() {
        LV_Detail_sig();
    }

    //Khởi tạo menu trên thanh tiêu đề (S)
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kt02_xe, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu2:
                Update_tc_faiup();
                //getLVData();
                break;
            case R.id.menu3:
                delete_data();
                //getLVData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Update_tc_faiup() {
        Thread UpLoad_fia_up = new Thread(new Runnable() {
            @Override
            public void run() {
                //INSERT HINH ANH
                File dir = new File("/storage/emulated/0/Pictures/"); // thay đổi đường dẫn tới thư mục chứa hình ảnh tương ứng
                File[] files = dir.listFiles();

                String imageName = null;


                for (File file : files) {
                    String kiemtratenanh = file.getName().toString().trim().substring(0, 2);
                    if (kiemtratenanh.equals("TB")) {
                        String File_path = file.getAbsolutePath();
                        String[] mangtenfile = File_path.split("\\.");
                        //File_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                        File_path = mangtenfile[0] + "." + mangtenfile[1];
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", File_path, requestBody);


                        DataClient_Sig dataClient_sig = APIYtils_Sig.getData();
                        Call<String> callback = dataClient_sig.UploadPhot(body);
                        callback.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if (response != null) {
                                    String message = response.body();
                                    Log.d("BBB", message);
                                    // Xóa tấm ảnh sau khi upload thành công
                                    /*boolean deleted = file.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + file.getAbsolutePath());
                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + file.getAbsolutePath());
                                    }*/
                                    //File file = new File(filePath); // thay đổi đường dẫn tới file hình cần xóa
                                    //if (file.exists()) {

                                    //}

                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                //Log.d("onFailurePIC", t.getMessage());
                                Log.d("onFailurePIC", "Timeout error: " + t.getMessage());
                                //String a =t.getMessage().toString();
                                // Xóa tấm ảnh sau khi upload thành công
                                /*boolean deleted = file.delete();
                                if (deleted) {
                                    Log.d("BBB", "Deleted file: " + file.getAbsolutePath());
                                } else {
                                    Log.d("BBB", "Failed to delete file: " + file.getAbsolutePath());
                                }*/
                                //file.delete(); // xóa file
                                //Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file));
                                //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file))); // thông báo đến hệ thống để quét lại thư viện media
                                   /* Context context1 = dialog.getContext();
                                    if (context1 != null) {
                                        context1.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                                    }
                                    file.delete(); // xóa file*/

                                //File fileToDelete = new File(filePath);
                                    /*Context context1 = dialog.getContext();
                                    boolean deleted = file.delete();
                                    if (deleted) {
                                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                        Uri contentUri = Uri.fromFile(file);
                                        mediaScanIntent.setData(contentUri);
                                        mediaScanIntent.putExtra(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, true);
                                        context1.sendBroadcast(mediaScanIntent);
                                    }*/


                                //File file = new File("Đường_dẫn_đến_file_hình_ảnh");
                                    /*File trashDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.Trash");
// Sử dụng FileUtils.moveToDirectory() để di chuyển file đến thùng rác lưu tạm
                                    boolean isFileMoved = false;
                                    try {
                                        FileUtils.moveToDirectory(file, trashDir, true);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                    if (isFileMoved) {
                                        // File đã được di chuyển vào thùng rác lưu tạm thành công
                                    } else {
                                        // File chưa được di chuyển vào thùng rác lưu tạm
                                    }*/

                            }
                        });

                    }
                    ;

                }
//INSERT TB TC_FAO
                Cursor upl = kt01Db.getAll_fiaupnot_sig(g_tenxe);
                jsonupload = cur2Json(upl);

                try {
                    ujobject = new JSONObject();
                    //ujobject.put("docNum", edt_maCT.getText().toString());
                    ujobject.put("ujson", jsonupload);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String res = upload_all("http://172.16.40.20/" + g_server + "/TechAPP/upload_tc_fao.php");
                if (!res.equals("FALSE")) {
                    if (res.length() > 6) {
                        runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                            @Override
                            public void run() {
                                try {

                                    JSONArray jsonarray = new JSONArray(res);
                                    for (int i = 0; i < jsonarray.length(); i++) {
                                        JSONObject jsonObject = jsonarray.getJSONObject(i);
                                        String g_tc_faO001 = jsonObject.getString("TC_FAO001"); //Số máy
                                        String g_tc_faO002 = jsonObject.getString("TC_FAO002"); //Mã bộ phận
                                        String g_tc_faO004 = jsonObject.getString("TC_FAO004"); //Ngay

                                        kt01Db.update_tc_fiaup_sig(g_tc_faO001, g_tc_faO002, g_tc_faO004, "Đã chuyển");

                                        //trangthai.setText("Chưa chuyển");
                                    }
                                    Toast.makeText(getApplicationContext(), R.string.ERRORtvStatus_errorins, Toast.LENGTH_SHORT).show();
                                    kt01Db.update_tc_fiaup1_sig();
                                    a = "ok";
                                } catch (JSONException e) {
                                    String abc = e.toString();
                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {
                        //Toast.makeText(getApplicationContext(), R.string.ERRORtvStatus_false_sig, Toast.LENGTH_SHORT).show();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), R.string.ERRORtvStatus_false_sig, Toast.LENGTH_SHORT).show();
                            }
                        });
                        a = "ok";
                    }
                }
            }
        });

        Thread Load_fia_up = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare(); // Chuẩn bị luồng để chạy vòng lặp sự kiện
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LV_Detail_sig();
                    }
                });

                Looper.loop(); // Bắt đầu vòng lặp sự kiện
            }
        });
        //dialog.dismiss();

        new Thread() {
            @Override
            public void run() {
                UpLoad_fia_up.start();
                try {
                    UpLoad_fia_up.join();
                } catch (InterruptedException e) {
                }
                if(a == "ok"){
                    Load_fia_up.start();
                    try {
                        Load_fia_up.join();
                    } catch (InterruptedException e) {
                    }
                }

            }
        }.start();
    }

    private void delete_data() {
        kt01Db.del_fiaup_sig();
        LV_Detail_sig();
    }

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


    public String upload_all(String apiUrl) {
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
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(is));
            String result = reader1.readLine();
            reader1.close();
            return result;
        } catch (Exception ex) {
            return "FALSE";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
    //Khởi tạo menu trên thanh tiêu đề (E)
}