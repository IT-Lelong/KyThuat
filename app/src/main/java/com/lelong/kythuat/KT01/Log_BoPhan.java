package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Log_BoPhan extends AppCompatActivity {
    private final String FILENAME = "mydata.txt";
    String g_server = "";
    TextView viewID;
    JSONObject ujobject;
    private static final int REQUEST_CODE = 1;
    String lbophan1, ID, BP;
    String L_BP, g_lang;
    String lbophandelete;
    ListView lis1;
    ListView lv_query;
    JSONArray jsonupload;
    private KT01_DB db = null;
    EditText editText1;
    int Request_Code_Image = 1234;
    Button btnlogin1;
    Button search1;
    Cursor cursor_1, cursor_2;
    Button btnback;
    List parts;
    String realpath = "";
    ArrayList<KT01_Fragment_Model> mangLV;
    Button btnaddnew;
    Button btnaupdate;
    Locale locale;

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setLanguage();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt01_main_menu_layout);
        db = new KT01_DB(this);
        db.open();

        g_server = getString(R.string.server);
        editText1 = findViewById(R.id.editID);
        btnlogin1 = findViewById(R.id.btnlogin);
        //btnaddnew = findViewById(R.id.btn_addnew);
        //btnaupdate = findViewById(R.id.btn_updatesever);
        lis1 = findViewById(R.id.lv_query);
        btnback = findViewById(R.id.btnback);
        viewID = findViewById(R.id.viewID);
        //search1 = findViewById(R.id.search);
        //editText1.setVisibility(View.GONE);
        //btnback.setVisibility(View.GONE);
        //search1.setVisibility(View.GONE);
        //viewID.setVisibility(View.GONE);
        //btnlogin1.setVisibility(View.GONE);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SharedPreferences preferences = getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        if (language == 0) {
            g_lang = "tc_faa008";
        } else {
            g_lang = "tc_faa009";
        }



        btnaupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editText1.setVisibility(View.GONE);
                //btnback.setVisibility(View.GONE);
                //viewID.setVisibility(View.GONE);
                //btnlogin1.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Warning");
                SpannableStringBuilder message = new SpannableStringBuilder();
                //message.append("Xác Nhận kết chuyễn?");


                //AlertDialog dialog = builder.create();
                // dialog.show();

// Define a custom button style
                int buttonStyle = android.R.style.Widget_Button;

// Create a new button with the custom style++
                Button okButton = new Button(v.getContext(), null, buttonStyle);
                okButton.setTextColor(Color.WHITE);
                okButton.setText("Xác Nhận kết chuyễn?");

// Set the button as the positive button of the dialog
                builder.setPositiveButton(null, null);
                builder.setNegativeButton(null, null);
                builder.setView(okButton);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db = new KT01_DB(v.getContext());
                        db.open();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                File dir = new File("/storage/emulated/0/Pictures/"); // thay đổi đường dẫn tới thư mục chứa hình ảnh tương ứng
                                File[] files = dir.listFiles();

                                String imageName = null;
                                for (File file : files) {
                                    String kiemtratenanh = file.getName().toString().trim().substring(0,2);
                                  if  (kiemtratenanh.equals("KT")){
                                      String File_path = file.getAbsolutePath();
                                      String[] mangtenfile = File_path.split("\\.");
                                      File_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                                      RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);
                                      MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", File_path, requestBody);
                                      DataClient dataClient = APIYtils.getData();
                                      Call<String> callback = dataClient.UploadPhot(body);
                                      callback.enqueue(new Callback<String>() {
                                          @Override
                                          public void onResponse(Call<String> call, Response<String> response) {
                                              if (response != null) {
                                                  String message = response.body();
                                                  Log.d("BBB", message);
                                                  // Xóa tấm ảnh sau khi upload thành công
                                                  boolean deleted = file.delete();
                                                  if (deleted) {
                                                      Log.d("BBB", "Deleted file: " + file.getAbsolutePath());
                                                  } else {
                                                      Log.d("BBB", "Failed to delete file: " + file.getAbsolutePath());
                                                  }

                                              }
                                          }

                                          @Override
                                          public void onFailure(Call<String> call, Throwable t) {
                                              Log.d("BBB", t.getMessage());
                                              // Xóa tấm ảnh sau khi upload thành công
                                              boolean deleted = file.delete();
                                              if (deleted) {
                                                  Log.d("BBB", "Deleted file: " + file.getAbsolutePath());
                                              } else {
                                                  Log.d("BBB", "Failed to delete file: " + file.getAbsolutePath());
                                              }
                                          }
                                      });

                                  };

                                }




                                String bien = "A";
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                Date date = new Date();
                                db.getAll_tc_faa();
                                //tham số Y , biểu thị cập nhật dữ liệu tới chương trình gốc, và save đến qrf_file
                                Cursor upl = db.getAll_tc_faa();
                                if (upl.getCount() > 0) {

                                    jsonupload = cur2Json(upl);

                                    try {
                                        ujobject = new JSONObject();
                                        ujobject.put("ujson1", jsonupload);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    final String res = upload_all("http://172.16.40.20/" + g_server + "/TechAPP/uploadtc_fae.php");

                                    runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                                        @Override
                                        public void run() {
                                            if (res.contains("false")) {
                                                //Toast.makeText(getApplicationContext(), getString(R.string.M09), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getApplicationContext(), "Kết chuyễn dữ liệu thất bại: ", Toast.LENGTH_SHORT).show();
                                            } else {
                                                //Toast.makeText(getApplicationContext(), getString(R.string.M08), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getApplicationContext(), "Kết chuyễn dữ liệu thành công: ", Toast.LENGTH_SHORT).show();
                                                db.delete_tenhinh_all();
                                                load();
                                            }
                                        }
                                    });

                                } else {
                                    String res = "false";
                                    runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                                        @Override
                                        public void run() {
                                            if (res.contains("false")) {
                                                //Toast.makeText(getApplicationContext(), getString(R.string.M09), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getApplicationContext(), "Kết chuyễn dữ liệu thất bại: ", Toast.LENGTH_SHORT).show();
                                            } else {
                                                //Toast.makeText(getApplicationContext(), getString(R.string.M08), Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                                }
                                ;


                            }
                        }).start();

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Xử lý khi người dùng chọn "Cancel"
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#669999")));

// Hiển thị hộp thoại
                dialog.show();

                // Intent intent = new Intent(Log_BoPhan.this, kt01_update.class);
                // startActivity(intent);


                // Intent intent = new Intent(Log_BoPhan.this, kt01_update.class);
                // startActivity(intent);


            }
        });

        search1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Log_BoPhan.this, kt01_listbophan.class);
                startActivityForResult(intent, REQUEST_CODE);



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
                                                 Intent intent = new Intent(Log_BoPhan.this, KT01_Main_CreateTabLayout.class);
                                                 Bundle bundle = new Bundle();
                                                 bundle.putString("ID1", lbophan1);
                                                 intent.putExtras(bundle);
                                                 startActivity(intent);
                                                 nutchucnang();
                                                 //editText1.setVisibility(View.GONE);
                                                 //search1.setVisibility(View.GONE);
                                                 //viewID.setVisibility(View.GONE);
                                                 //btnlogin1.setVisibility(View.GONE);
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

    private void setLanguage() {
        SharedPreferences preferences = getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        switch (language) {
            case 0:
                configuration.setLocale(Locale.TRADITIONAL_CHINESE);
                break;
            case 1:
                locale = new Locale("vi");
                Locale.setDefault(locale);
                configuration.setLocale(locale);
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    private void load() {

        Cursor cursor = db.getAll_tc_faa1("KT01");

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
                R.layout.kt01_login_dialog_lvrow, cursor,
                new String[]{"_id", "tc_faa002", "tc_faa003"},
                new int[]{R.id.tv_stt, R.id.tv_ngay, R.id.tv_BP},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        simpleCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
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
        lis1.setAdapter(simpleCursorAdapter);
        lis1.setOnItemClickListener((parent, view, position, id) -> {

            // Tạo đối tượng PopupMenu
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view, Gravity.END, 0, R.style.MyPopupMenu);

            // Nạp tệp menu vào PopupMenu
            popupMenu.getMenuInflater().inflate(R.menu.kt01_log_even, popupMenu.getMenu());

            // Show the PopupMenu.
            popupMenu.show();

            // Đăng ký sự kiện Popup Menu
            popupMenu.setOnMenuItemClickListener(item -> {

                TextView qry_ngay = view.findViewById(R.id.tv_ngay);
                TextView qry_BP = view.findViewById(R.id.tv_BP);

                // Xử lý sự kiện khi người dùng chọn một lựa chọn trong menu
                switch (item.getItemId()) {
                    case R.id.openKT01:


                        //   String ID12 = "DK1";
                        Intent KT01 = new Intent();
                        KT01.setClass(this, KT01_Main_CreateTabLayout.class);
                        Bundle bundle = new Bundle();
                        //bundle.putString("BP", BP);
                        bundle.putString("DATE", qry_ngay.getText().toString());
                        bundle.putString("BP", qry_BP.getText().toString());
                        KT01.putExtras(bundle);
                        this.startActivity(KT01);
                        return true;
                    case R.id.clearKT01:
                        db.delete_table1(qry_ngay.getText().toString(), qry_BP.getText().toString());
                        lbophandelete = qry_BP.getText().toString();
                        File dir = new File("/storage/emulated/0/Pictures/"); // thay đổi đường dẫn tới thư mục chứa hình ảnh tương ứng
                        File[] files = dir.listFiles();

                        String imageName = null;
                        for (File file : files) {
                            String kiemtratenanh = file.getName().toString().trim().substring(0,2);
                            if  (kiemtratenanh.equals("KT")) {
                                String fileName = file.getName(); // Lấy tên của tệp
                                String[] parts = fileName.split("_"); // Tách tên thành các phần
                                String result = parts[2]; // Lấy phần tử thứ 3
                                if (result.equals(lbophandelete)) {
                                    boolean deleted = file.delete();
                                    if (deleted) {
                                        Log.d("BBB", "Deleted file: " + file.getAbsolutePath());
                                        finish();
                                    } else {
                                        Log.d("BBB", "Failed to delete file: " + file.getAbsolutePath());
                                    }
                                }
                                ;
                            }

                        }
                        db.delete_tenhinh_all();
                        //  dialog.dismiss();
                        load();
                        return true;
                }
                return true;
            });
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String selectedData = data.getStringExtra("selectedData");
            editText1.setText(selectedData);
        }
    }


}
