package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.lelong.kythuat.KT01.Retrofit2.APIYtils;
import com.lelong.kythuat.KT01.Retrofit2.DataClient;
import com.lelong.kythuat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public
class Log_BoPhan extends AppCompatActivity {
    private final String FILENAME = "mydata.txt";
    String g_server = "";
    TextView viewID;
    JSONObject ujobject;
    String lbophan1;
    String L_BP;
    JSONArray jsonupload;
    private KT01_DB db = null;
    EditText editText1;
    int Request_Code_Image = 1234;
    Button btnlogin1;
    Cursor cursor_1, cursor_2;
    Button btnback;
    List parts;
    String realpath = "";
    Button btnaddnew;
    Button btnketchuyenanh;
    Button btnketchuyendl;
    Button btnaupdate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt01_bophan);
        g_server = getString(R.string.server);

        editText1 = findViewById(R.id.editID);
        btnlogin1 = findViewById(R.id.btnlogin);
        btnaddnew = findViewById(R.id.btn_addnew);
        btnaupdate = findViewById(R.id.btn_updatesever);
        btnketchuyenanh = findViewById(R.id.btn_kcanh);
        btnketchuyendl = findViewById(R.id.btn_kcdl);
        btnback = findViewById(R.id.btnback);
        viewID = findViewById(R.id.viewID);
        editText1.setVisibility(View.GONE);
        btnback.setVisibility(View.GONE);
        viewID.setVisibility(View.GONE);
        btnlogin1.setVisibility(View.GONE);
        btnketchuyenanh.setVisibility(View.GONE);
        btnketchuyendl.setVisibility(View.GONE);
        btnaddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnketchuyenanh.setVisibility(View.GONE);
                btnketchuyendl.setVisibility(View.GONE);
                editText1.setVisibility(View.VISIBLE);
                viewID.setVisibility(View.VISIBLE);
                btnlogin1.setVisibility(View.VISIBLE);
                btnback.setVisibility(View.VISIBLE);
            }
        });
        btnaupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText1.setVisibility(View.GONE);
                btnback.setVisibility(View.GONE);
                viewID.setVisibility(View.GONE);
                btnlogin1.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Warning");
                SpannableStringBuilder message = new SpannableStringBuilder();
                message.append("Xác Nhận kết chuyễn?");
                message.setSpan(new AbsoluteSizeSpan(25, true), 0, message.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

                builder.setMessage(message);

                //AlertDialog dialog = builder.create();
                // dialog.show();
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db = new KT01_DB(v.getContext());
                        db.open();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                cursor_1 = db.getAll_tc_faa("KT01");
                                cursor_1.moveToFirst();
                                int num = cursor_1.getCount();
                                for (int i = 0; i < num; i++) {
                                    try {
                                        @SuppressLint("Range") String tc_faa005 = cursor_1.getString(cursor_1.getColumnIndex("tc_faa005"));
                                        // String   a = "/storage/emulated/0/Pictures/KT010103_2023-02-25_13_45_37_ABI3100000.jpg";
                                        String a = "/storage/emulated/0/Pictures/" + tc_faa005 + "" + ".jpg" + "";
                                        File file = new File(a);
                                        String File_path = file.getAbsolutePath();
                                        String[] mangtenfile = File_path.split("\\.");
                                        File_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);
                                        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", File_path, requestBody);
                                        DataClient dataClient = APIYtils.getData();
                                        retrofit2.Call<String> callback = dataClient.UploadPhot(body);
                                        callback.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                                                if (response != null) {
                                                    String message = response.body();
                                                    Log.d("BBB", message);
                                                }
                                            }

                                            @Override
                                            public void onFailure(retrofit2.Call<String> call, Throwable t) {
                                                Log.d("BBB", t.getMessage());
                                            }
                                        });
                                    } catch (Exception e) {
                                        String err = e.toString();
                                    }

                                    cursor_1.moveToNext();
                                }
                                String bien = "A";
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                Date date = new Date();
                                db.getAll_tc_faa(bien);
                                //tham số Y , biểu thị cập nhật dữ liệu tới chương trình gốc, và save đến qrf_file
                                Cursor upl = db.getAll_tc_faa(bien);
                                jsonupload = cur2Json(upl);

                                try {
                                    ujobject = new JSONObject();
                                    ujobject.put("ujson", jsonupload);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                final String res = upload_all("http://172.16.40.20/" + g_server + "/TechAPP/uploadtc_fae.php");


                        /*  runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                          @Override
                            public void run() {
                                if (res.contains("false")) {
                                    //Toast.makeText(getApplicationContext(), getString(R.string.M09), Toast.LENGTH_SHORT).show();
                                    if (chk_dialog == 1) {
                                        tvStatus.setText(getString(R.string.M09));
                                        tvStatus.setTextColor(getResources().getColor(R.color.RED));
                                    }
                                } else {
                                    //Toast.makeText(getApplicationContext(), getString(R.string.M08), Toast.LENGTH_SHORT).show();
                                    if (chk_dialog == 1) {
                                        tvStatus.setText(getString(R.string.M08));
                                        tvStatus.setTextColor(getResources().getColor(R.color.PURPLE));
                                        db210.del_db(prog);
                                    }
                                }
                            }
                        });*/

                            }
                        }).start();

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng chọn "Cancel"
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00CCFF")));

                // Hiển thị hộp thoại
                dialog.show();

                // Intent intent = new Intent(Log_BoPhan.this, kt01_update.class);
                // startActivity(intent);


                // Intent intent = new Intent(Log_BoPhan.this, kt01_update.class);
                // startActivity(intent);


            }
        });

        btnketchuyenanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Log_BoPhan.this, kt01_update.class);
                startActivity(intent);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText1.setVisibility(View.GONE);
                btnback.setVisibility(View.GONE);
                viewID.setVisibility(View.GONE);
                btnlogin1.setVisibility(View.GONE);
            }
        });

        btnlogin1.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             L_BP = editText1.getText().toString();
                                             db = new KT01_DB(view.getContext());
                                             db.open();
                                             db.getAll_gem(L_BP);
                                             boolean result = db.getAll_gem(L_BP);

                                             if (result) {
                                                 lbophan1 = editText1.getText().toString();
                                                 Intent intent = new Intent(Log_BoPhan.this, login_kt01.class);
                                                 Bundle bundle = new Bundle();
                                                 bundle.putString("ID1", lbophan1);
                                                 intent.putExtras(bundle);
                                                 startActivity(intent);
                                                 nutchucnang();
                                                 editText1.setVisibility(View.GONE);
                                                 btnback.setVisibility(View.GONE);
                                                 viewID.setVisibility(View.GONE);
                                                 btnlogin1.setVisibility(View.GONE);
                                             } else {
                                                 Toast.makeText(view.getContext(), "Mã bộ phận không đúng vui lòng kiểm tra lại: ", Toast.LENGTH_SHORT).show();
                                             }
                                         }

                                         private void nutchucnang() {
                                             try {
                                                 FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                                                 fos.write(lbophan1.getBytes());
                                                 fos.close();
                                             } catch (IOException e) {
                                                 e.printStackTrace();
                                             }
                                         }
                                     }

        );


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
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String result = reader.readLine();
            reader.close();
            return result;
        } catch (Exception ex) {
            return "false";
        } finally {
            if (conn != null) {

                db.delete_table();
                conn.disconnect();
            }
        }
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

}
