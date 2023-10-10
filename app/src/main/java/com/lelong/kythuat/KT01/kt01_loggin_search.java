package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.KT01.Retrofit2.APIYtils;
import com.lelong.kythuat.KT01.Retrofit2.DataClient;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class kt01_loggin_search extends AppCompatActivity {
    private Activity activity;
    ListView lv_query02;
    private Context context;
    Dialog dialog;
    ListView lis1;
    private KT01_DB db = null;
    EditText editText1;
    Button btnlogin1;
    Button search1;
    Button btnback;
    Button btnaupdate, btnsignature;
    Locale locale;
    TextView viewID, textView;
    String L_BP;
    JSONArray jsonupload;
    JSONObject ujobject;
    String g_server = "PHP";
    String lbophandelete;
    String lbophan1, ID, BP;
    String g_bp;
    private static final int REQUEST_CODE = 1;
    private final String FILENAME = "mydata.txt";
    Cursor cursor_1, cursor_2;
    String[] station = new String[0];
    String g_soxe, g_bophan, mabp, tenbp, g_tenxe, g_to, g_xuong;

    public void login_dialogkt01(Context context, String menuID, Activity activity) {
        this.activity = activity;
        this.context = context;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt01_bophan);

        editText1 = dialog.findViewById(R.id.editID);
        btnlogin1 = dialog.findViewById(R.id.btnlogin);
        btnaupdate = dialog.findViewById(R.id.btn_updatesever);
        btnsignature = dialog.findViewById(R.id.btn_signature);
        lis1 = dialog.findViewById(R.id.lv_query);
        btnback = dialog.findViewById(R.id.btnback);
        viewID = dialog.findViewById(R.id.viewID);
        TextView textView = dialog.findViewById(R.id.txt_to);
        //search1 = dialog.findViewById(R.id.search);

        db = new KT01_DB(dialog.getContext());
        db.open();

        Spinner cbxbophan = dialog.findViewById(R.id.cbxbophan);
        Spinner cbxto = dialog.findViewById(R.id.cbxto);

        List<kt01_Loggin_List> qrReScanIpLists = new ArrayList<>();

        if (Constant_Class.UserFactory.equals("DH")) {
            cursor_2 = db.getAll_tc_fba();
        } else {
            cursor_2 = db.getAll_tc_fbaBL();
        }

        //cursor_2 = db.getAll_tc_fba();
        cursor_2.moveToFirst();
        int num1 = cursor_2.getCount();
        station = new String[num1];
        for (int i = 0; i < num1; i++) {

            try {
                @SuppressLint("Range") String mabp = cursor_2.getString(cursor_2.getColumnIndex("tc_fba007"));
                @SuppressLint("Range") String tenbp = cursor_2.getString(cursor_2.getColumnIndex("tc_fba009"));
                qrReScanIpLists.add(new kt01_Loggin_List(mabp, tenbp));
            } catch (Exception e) {
                String err = e.toString();
            }
            cursor_2.moveToNext();
        }

        kt01_Bophan_Adapter kt01_bophan_adapter = new kt01_Bophan_Adapter(this.activity,
                R.layout.kt01_dsbophan,
                R.id.sp_mabp,
                R.id.sp_tenbp,
                qrReScanIpLists);
        cbxbophan.setAdapter(kt01_bophan_adapter);


        cbxbophan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                int dem = kt01_bophan_adapter.getCount();
                if (position + 1 <= dem) {
                    if (position >= 0) {
                        //get IP
                        kt01_Loggin_List res = kt01_bophan_adapter.getItem(position);
                        mabp = res.getIDbp().toString().trim();
                        tenbp = res.getTenbp().toString().trim();
                        g_bophan = qrReScanIpLists.get(position).getIDbp().trim();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner cbxto = dialog.findViewById(R.id.cbxto);

        //String[] list_to = {"Tổ A", "Tổ B", "Tổ C", "Tổ D"};
        if (Constant_Class.UserID.equals("H15330")) {
            g_xuong="Tổ A";

        }
        if (Constant_Class.UserID.equals("H14430")) {
            g_xuong="Tổ B";

        }
        if (Constant_Class.UserID.equals("H14398")) {
            g_xuong="Tổ C";

        }
        if (Constant_Class.UserID.equals("H19588")) {
            g_xuong="Tổ D";

        }
        //String[] list_to = {"Tổ A"};
        String[] list_to = {g_xuong};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.activity, android.R.layout.simple_spinner_item, list_to);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cbxto.setAdapter(adapter);

        if (Constant_Class.UserFactory.equals("DH")) {
            cbxto.setVisibility(View.VISIBLE); // Hiện Spinner nếu UserFactory là "DH"
            textView.setVisibility(View.VISIBLE);
        } else {
            cbxto.setVisibility(View.GONE); // Ẩn Spinner nếu UserFactory không phải là "DH"
            textView.setVisibility(View.GONE);
        }

        cbxto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                g_to = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        load();

        lis1.setOnItemClickListener((parent, view, position, id) -> {

            // Tạo đối tượng PopupMenu
            PopupMenu popupMenu = new PopupMenu(context.getApplicationContext(), view, Gravity.END, 0, R.style.MyPopupMenu);

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
                        Intent KT01 = new Intent();
                        KT01.setClass(context, login_kt01.class);
                        Bundle bundle = new Bundle();
                        //bundle.putString("BP", BP);
                        bundle.putString("DATE", qry_ngay.getText().toString());
                        bundle.putString("BP", qry_BP.getText().toString());
                        bundle.putString("LAYOUT", "login");
                        bundle.putString("TO", g_to);
                        KT01.putExtras(bundle);
                        context.startActivity(KT01);
                        dialog.dismiss();
                        //load();
                        return true;
                    case R.id.clearKT01:
                        //db.delete_table1(qry_ngay.getText().toString(), qry_BP.getText().toString());
                        lbophandelete = qry_BP.getText().toString();
                        Cursor cursor = db.getngay();
                        cursor.moveToFirst();
                        @SuppressLint("Range") String ngay = cursor.getString(cursor.getColumnIndex("tc_faa002"));

                        File newDirectory = new File(context.getExternalMediaDirs()[0], ngay.replace("-", ""));
                        File dir = new File(newDirectory + "/"); // thay đổi đường dẫn tới thư mục chứa hình ảnh tương ứng
                        //File dir = new File("/storage/emulated/0/Pictures/"); // thay đổi đường dẫn tới thư mục chứa hình ảnh tương ứng
                        File[] files = dir.listFiles();

                        if (files != null) {
                            String imageName = null;
                            for (File file : files) {
                                String kiemtratenanh = file.getName().toString().trim().substring(0, 2);
                                if (kiemtratenanh.equals("KT")) {
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
                                }
                            }
                        }


                        db.delete_table1(qry_ngay.getText().toString(), qry_BP.getText().toString());
                        db.delete_tenhinh();
                        db.delete_tenhinhCT();
                        //  dialog.dismiss();
                        load();
                        return true;
                }
                return true;
            });
        });

        btnaupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Warning");
                SpannableStringBuilder message = new SpannableStringBuilder();
                //message.append("Xác Nhận kết chuyễn?");


                //AlertDialog dialog = builder.create();
                // dialog.show();

// Define a custom button style
                int buttonStyle = android.R.style.Widget_Button;

// Create a new button with the custom style
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
                                //File dir = new File("/storage/emulated/0/Pictures/"); // thay đổi đường dẫn tới thư mục chứa hình ảnh tương ứng
                                Cursor cursor = db.getngay();
                                cursor.moveToFirst();
                                @SuppressLint("Range") String ngay = cursor.getString(cursor.getColumnIndex("tc_faa002"));

                                File newDirectory = new File(context.getExternalMediaDirs()[0], ngay.replace("-", ""));

                                File dir = new File(newDirectory + "/"); // thay đổi đường dẫn tới thư mục chứa hình ảnh tương ứng
                                File[] files = dir.listFiles();
                                if (files != null) {
                                    Gson gson = new GsonBuilder().create();

                                    Retrofit retrofit = new Retrofit.Builder()
                                            .baseUrl("http://172.16.40.20/PHP/Retrofit/")
                                            .addConverterFactory(GsonConverterFactory.create(gson))
                                            .build();

                                    DataClient apiService = retrofit.create(DataClient.class);

                                    String imageName = null;
                                    for (File file : files) {
                                        String kiemtratenanh = file.getName().toString().trim().substring(0, 2);
                                        if (kiemtratenanh.equals("KT")) {
                                            String File_path = file.getAbsolutePath();
                                            String[] mangtenfile = File_path.split("/");
                                            //File_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                                            File_path = mangtenfile[8];
                                            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);
                                            MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", File_path, requestBody);
                                            DataClient dataClient = APIYtils.getData();
                                            //Call<String> callback = dataClient.UploadPhot(body);
                                            Call<ResponseBody> callback = apiService.uploadImage(body);
                                            callback.enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    if (response != null) {
                                                        String message = String.valueOf(response.body());
                                                        Log.d("BBB", message);
                                                        // Xóa tấm ảnh sau khi upload thành công
                                                    /*boolean deleted = file.delete();
                                                    if (deleted) {
                                                        Log.d("BBB", "Deleted file: " + file.getAbsolutePath());
                                                    } else {
                                                        Log.d("BBB", "Failed to delete file: " + file.getAbsolutePath());
                                                    }*/

                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                                    Log.d("BBB", t.getMessage());
                                                    // Xóa tấm ảnh sau khi upload thành công
                                                /*boolean deleted = file.delete();
                                                if (deleted) {
                                                    Log.d("BBB", "Deleted file: " + file.getAbsolutePath());
                                                } else {
                                                    Log.d("BBB", "Failed to delete file: " + file.getAbsolutePath());
                                                }*/
                                                }
                                            });

                                        }
                                        ;

                                    }
                                }



                                /*String bien = "A";
                                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                Date date = new Date();
                                db.getAll_tc_faa(bien);
                                //tham số Y , biểu thị cập nhật dữ liệu tới chương trình gốc, và save đến qrf_file
                                Cursor upl = db.getAll_tc_faa(bien);
                                if (upl.getCount() > 0) {

                                    jsonupload = cur2Json(upl);

                                    try {
                                        ujobject = new JSONObject();
                                        ujobject.put("ujson", jsonupload);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }*/


                                Cursor upl = db.getAll_tc_faa();
                                jsonupload = cur2Json(upl);

                                try {
                                    ujobject = new JSONObject();
                                    //ujobject.put("docNum", edt_maCT.getText().toString());
                                    ujobject.put("ujson1", jsonupload);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                String res = upload_all("http://172.16.40.20/" + g_server + "/TechAPP/uploadtc_fae.php");

                                runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                                    @Override
                                    public void run() {
                                            /*if (res.contains("false")) {
                                                //Toast.makeText(getApplicationContext(), getString(R.string.M09), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getApplicationContext(), "Kết chuyễn dữ liệu thất bại: ", Toast.LENGTH_SHORT).show();
                                            }*/
                                        if (res.contains("TRUE")) {
                                            //Toast.makeText(getApplicationContext(), getString(R.string.M08), Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(getApplicationContext(), "Kết chuyễn dữ liệu thành công: ", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(v.getContext(), R.string.ERRORtvStatus_errorins, Toast.LENGTH_SHORT).show();
                                            db.delete_tenhinh();
                                            db.delete_table_faa_kt("KT01");
                                            load();
                                        } else {
                                            Toast.makeText(v.getContext(), res.toString(), Toast.LENGTH_SHORT).show();
                                            //Toast.makeText(v.getContext(), R.string.ERRORtvStatus_false, Toast.LENGTH_SHORT).show();
                                        }
                                            /*if (res.contains("ERROINS")) {
                                                //tvStatus.setText("đã được insert");
                                                Toast.makeText(v.getContext(), R.string.ERRORtvStatus_errorins, Toast.LENGTH_SHORT).show();
                                                //kt02Db.delete_table();
                                            }*/
                                    }
                                });

                                /*} else {
                                    String res = "false";
                                    runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                                        @Override
                                        public void run() {
                                            if (res.contains("false")) {
                                                //Toast.makeText(getApplicationContext(), getString(R.string.M09), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(v.getContext(), "Kết chuyễn dữ liệu thất bại: ", Toast.LENGTH_SHORT).show();
                                            } else {
                                                //Toast.makeText(getApplicationContext(), getString(R.string.M08), Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });
                                }*/
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

        btnlogin1.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Intent intent = new Intent(view.getContext(), login_kt01.class);
                                             Bundle bundle = new Bundle();
                                             bundle.putString("BOPHAN", g_bophan);
                                             bundle.putString("LAYOUT", "notlogin");
                                             bundle.putString("TO", g_to);
                                             intent.putExtras(bundle);
                                             view.getContext().startActivity(intent);
                                             dialog.dismiss();
                                         }
                                     }
        );

        btnsignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent QR020 = new Intent();
                QR020.setClass(v.getContext(), KT01_Signature_List.class);
                Bundle bundle = new Bundle();
                bundle.putString("somay", g_soxe);
                bundle.putString("bophan", g_bophan);
                bundle.putString("LAYOUT", "notlogin");
                //bundle.putString("G_TC_FAA001", g_tc_faa001);
                bundle.putString("G_TENXE", g_tenxe);
                //bundle.putString("SERVER", g_server);
                QR020.putExtras(bundle);
                v.getContext().startActivity(QR020);
                dialog.dismiss();
            }
        });

        dialog.show();
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String result = reader.readLine();
            reader.close();
            return result;
        } catch (Exception ex) {
            return "false";
        } finally {
            if (conn != null) {

                //db.delete_table();

                conn.disconnect();

            }
        }
    }

    private void load() {
        db.open();
        Cursor cursor = db.getAll_tc_faa1("KT01");

        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.kt01_login_dialog_lvrow, cursor,
                new String[]{"_id", "tc_faa002", "tc_faa003", "tc_fba009"},
                new int[]{R.id.tv_stt, R.id.tv_ngay, R.id.tv_BP, R.id.tv_tenBP},
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

    }
    /*public void startSecondActivity() {
        Intent intent = new Intent(this, kt01_listbophan.class);
        startActivityForResult(intent, REQUEST_CODE);
    }*/
    /*protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String selectedData = data.getStringExtra("selectedData");
            editText1.setText(selectedData);
        }
    }*/

}
