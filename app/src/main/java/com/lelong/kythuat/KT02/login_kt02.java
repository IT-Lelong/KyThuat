package com.lelong.kythuat.KT02;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.KT02.Retrofit2.APIYtils;
import com.lelong.kythuat.KT02.Retrofit2.DataClient;
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
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class login_kt02 extends AppCompatActivity {
    SimpleDateFormat dateFormatKT02 = new SimpleDateFormat("yyyy-MM-dd");
    private Create_Table createTable = null;
    private KT02_DB kt02Db = null;
    //private KT03_DB kt03Db = null;
    Cursor cursor_1, cursor_2, cursor_3;
    String[] station = new String[0];
    ArrayAdapter<String> stationlist;
    ArrayList<List_Bophan> mangbp;

    String g_soxe, g_bophan, mabp, tenbp, g_tenxe;
    Button btnins, btnsearch, btnfia, btnuploaddata, btnsignature;
    private Activity activity;
    ListView lv_query02;
    private Context context;
    Dialog dialog;
    boolean firstDetected = true;
    private final String FILENAME = "mydata.txt";
    String g_server = "PHP";
    JSONArray jsonupload;
    JSONObject ujobject;
    String g_tc_faa001 = "";
    String g_fia15;
    List<Loggin_List> qrReScanIpLists;
    Spinner cbxbophan;

    public void login_dialogkt02(Context context, String menuID, Activity activity, String g_tc_faa001) {
        this.activity = activity;
        this.context = context;
        this.g_tc_faa001 = g_tc_faa001;
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.kt02_activity_loginsetting);

        //Bundle getbundle = getIntent().getExtras();
        //g_tc_faa001 = getbundle.getString("G_TC_FAA001");
        //g_server = getString(R.string.server);

        Spinner cbxsoxe = dialog.findViewById(R.id.cbxsoxe);
        btnins = dialog.findViewById(R.id.btninsert);
        btnins.setOnClickListener(btnlistener1);
        btnsearch = dialog.findViewById(R.id.btnsearch);
        btnsearch.setOnClickListener(btnlistener1);
        btnfia = dialog.findViewById(R.id.btnfia);
        btnfia.setOnClickListener(btnlistener1);
        btnuploaddata = dialog.findViewById(R.id.btn_uploaddata);
        btnuploaddata.setOnClickListener(btnlistener1);
        btnsignature = dialog.findViewById(R.id.btn_signature);
        btnsignature.setOnClickListener(btnlistener1);
        lv_query02 = dialog.findViewById(R.id.lv_query02);

        String g_bp= Constant_Class.UserFactory;
        if (g_bp.equals("DH") ) {
            g_fia15 = "D";
        }
        if (g_bp.equals("BL")){
            g_fia15 = "B";
        }

        createTable = new Create_Table(dialog.getContext());
        createTable.open();
        kt02Db = new KT02_DB(dialog.getContext());
        //kt03Db = new KT03_DB(dialog.getContext());
        if (Objects.equals(g_tc_faa001, "KT02")) {
            g_tenxe = "Xe nâng dầu";
        }
        if (Objects.equals(g_tc_faa001, "KT05")) {
            g_tenxe = "Xe nâng tay điện";
        }
        if (Objects.equals(g_tc_faa001, "KT06")) {
            g_tenxe = "Xe nâng điện";
        }
        cursor_1 = createTable.getAll_fia_02(g_tenxe,g_fia15);
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        station = new String[num];
        for (int i = 0; i < num; i++) {

            try {
                @SuppressLint("Range") String fiaud03 = cursor_1.getString(cursor_1.getColumnIndex("fiaud03"));

                String g_fiaud03 = fiaud03;
                station[i] = g_fiaud03;

            } catch (Exception e) {
                String err = e.toString();
            }
            stationlist = new ArrayAdapter<>(dialog.getContext(), android.R.layout.simple_spinner_item, station);
            cbxsoxe.setAdapter(stationlist);
            cbxsoxe.setSelection(0);
            cursor_1.moveToNext();
        }

        //Bộ phận
        Spinner cbxbophan = dialog.findViewById(R.id.cbxbophan);
        qrReScanIpLists = new ArrayList<>();
        Bophan_Adapter bophan_adapter = new Bophan_Adapter(this.activity,
                R.layout.kt02_loginsetting_bophan,
                R.id.sp_mabp,
                R.id.sp_tenbp,
                qrReScanIpLists);
        cbxbophan.setAdapter(bophan_adapter);

        //spinner Scan IP (E)
        cbxsoxe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                g_soxe = cbxsoxe.getSelectedItem().toString().trim();
                qrReScanIpLists.clear();

                cursor_2 = createTable.getAll_fia_02_bp(g_soxe,g_tenxe,g_fia15);
                cursor_2.moveToFirst();
                int num1 = cursor_2.getCount();
                station = new String[num1];
                for (int i = 0; i < num1; i++) {

                    try {
                        @SuppressLint("Range") String fia15 = cursor_2.getString(cursor_2.getColumnIndex("fia15"));
                        @SuppressLint("Range") String fka02 = cursor_2.getString(cursor_2.getColumnIndex("fka02"));
                        qrReScanIpLists.add(new Loggin_List(fia15, fka02));
                        g_bophan = fia15;

                    } catch (Exception e) {
                        String err = e.toString();
                    }
                    cursor_2.moveToNext();
                }
                //qrReScanIpLists.add(new Loggin_List("", ""));
                bophan_adapter.notifyDataSetChanged();
                //g_bophan = cbxbophan.getSelectedItem().toString().trim();
                //cbxbophan.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cbxbophan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int dem = bophan_adapter.getCount()+1;
                if (position + 1 <= dem) {
                    if (position >= 0) {
                        //get IP
                        Loggin_List res = bophan_adapter.getItem(position);
                        mabp = res.getIDbp().toString().trim();
                        tenbp = res.getTenbp().toString().trim();
                        g_bophan = qrReScanIpLists.get(position).getIDbp().trim();
                        //g_bophan=tenbp;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Danh sach đã kiểm tra (S)
        getLVData();

        lv_query02.setOnItemClickListener((parent, view, position, id) -> {

            // Tạo đối tượng PopupMenu
            PopupMenu popupMenu = new PopupMenu(context.getApplicationContext(), view, Gravity.END, 0, R.style.MyPopupMenu);

            // Nạp tệp menu vào PopupMenu
            popupMenu.getMenuInflater().inflate(R.menu.kt02_login_lv, popupMenu.getMenu());

            // Show the PopupMenu.
            popupMenu.show();

            // Đăng ký sự kiện Popup Menu
            popupMenu.setOnMenuItemClickListener(item -> {

                TextView qry_ngay = view.findViewById(R.id.tv_ngay);
                TextView qry_somay = view.findViewById(R.id.tv_soxe);
                TextView qry_user = view.findViewById(R.id.tv_bophan);

                // Xử lý sự kiện khi người dùng chọn một lựa chọn trong menu
                switch (item.getItemId()) {
                    case R.id.openKT02:
                        Intent KT02 = new Intent();
                        KT02.setClass(context, KT02_activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("DATE", qry_ngay.getText().toString());
                        bundle.putString("SOMAY", qry_somay.getText().toString());
                        bundle.putString("USER", qry_user.getText().toString());
                        bundle.putString("LAYOUT", "login");
                        bundle.putString("G_TC_FAA001", g_tc_faa001);
                        KT02.putExtras(bundle);
                        context.startActivity(KT02);
                        dialog.dismiss();
                        return true;

                    case R.id.clearKT02:
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(context.getString(R.string.M05))
                                .setPositiveButton(context.getString(R.string.btn_ok), null)
                                .setNegativeButton(context.getString(R.string.btn_cancel), null);


                        AlertDialog al_dialog = builder.create();
                        al_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                TextView messageView = ((AlertDialog) dialogInterface).findViewById(android.R.id.message);
                                messageView.setTextSize(30);

                                Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                                positiveButton.setTextColor(ContextCompat.getColor(context, R.color.blue));
                                positiveButton.setTextSize(15);
                                //positiveButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                                Button negativeButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_NEGATIVE);
                                negativeButton.setTextColor(ContextCompat.getColor(context, R.color.red));
                                negativeButton.setTextSize(15);
                                //negativeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

                                positiveButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        kt02Db.delete_table(qry_ngay.getText().toString(), qry_somay.getText().toString(), qry_user.getText().toString());
                                        al_dialog.dismiss();
                                        getLVData();
                                    }
                                });
                            }
                        });

                        al_dialog.show();

                        return true;
                }
                return true;
            });
        });

        //Danh sach đã kiểm tra (E)

        dialog.show();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finish();
            }
        });
    }

    private void getLVData() {
        kt02Db.open();
        Cursor cursor = kt02Db.getAll_lvQuery(g_tc_faa001,g_tenxe);
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.kt02_login_lvrow, cursor,
                new String[]{"_id", "ngay", "somay", "user"},
                new int[]{R.id.tv_stt, R.id.tv_ngay, R.id.tv_soxe, R.id.tv_bophan},
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

        lv_query02.setAdapter(simpleCursorAdapter);
    }

    private Button.OnClickListener btnlistener1 = new Button.OnClickListener() {
        public void onClick(View v) {
            //利用switch case方法，之後新增按鈕只需新增case即可
            cbxbophan = dialog.findViewById(R.id.cbxbophan);
            int currentPosition = cbxbophan.getSelectedItemPosition();
            g_bophan = qrReScanIpLists.get(currentPosition).getIDbp();

            switch (v.getId()) {
                case R.id.btninsert: {

                    Intent QR020 = new Intent();
                    QR020.setClass(v.getContext(), KT02_activity.class);
                    //QR020.setClass(v.getContext(), SignaturePad.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("somay", g_soxe);
                    bundle.putString("bophan", g_bophan);
                    bundle.putString("LAYOUT", "notlogin");
                    bundle.putString("G_TC_FAA001", g_tc_faa001);
                    bundle.putString("G_TENXE", g_tenxe);
                    QR020.putExtras(bundle);
                    v.getContext().startActivity(QR020);
                    dialog.dismiss();
                    break;
                }
                case R.id.btnsearch: {

                    Intent QR020 = new Intent();
                    QR020.setClass(v.getContext(), KT02_Loggin_Search.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("somay", g_soxe);
                    bundle.putString("bophan", g_bophan);
                    bundle.putString("LAYOUT", "notlogin");
                    bundle.putString("G_TC_FAA001", g_tc_faa001);
                    bundle.putString("G_TENXE", g_tenxe);
                    QR020.putExtras(bundle);
                    v.getContext().startActivity(QR020);
                    //dialog.dismiss();
                    break;
                }
                case R.id.btnfia: {

                    Intent QR020 = new Intent();
                    QR020.setClass(v.getContext(), KT02_Search_fia.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("somay", g_soxe);
                    bundle.putString("bophan", g_bophan);
                    bundle.putString("LAYOUT", "notlogin");
                    bundle.putString("G_TC_FAA001", g_tc_faa001);
                    bundle.putString("G_TENXE", g_tenxe);
                    //bundle.putString("SERVER", g_server);
                    QR020.putExtras(bundle);
                    v.getContext().startActivity(QR020);
                    dialog.dismiss();
                    break;
                }
                case R.id.btn_uploaddata: {
                    KT02_Ketchuyen_Dialog ketChuyenDialog = new KT02_Ketchuyen_Dialog(v.getContext());
                    ketChuyenDialog.setEnableBtn(true,true);
                    ketChuyenDialog.setOkButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String input_datekt = ketChuyenDialog.getSelected_sp_datekt();
                            String input_department = ketChuyenDialog.getSelected_sp_department();
                            new KT02_Transfer(view.getContext(),ketChuyenDialog,input_datekt,input_department,g_tenxe);
                        }
                    });
                    ketChuyenDialog.setCancelButtonClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ketChuyenDialog.dismiss();
                        }
                    });
                    ketChuyenDialog.show();

                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(context.getString(R.string.M02))
                            .setPositiveButton(context.getString(R.string.btn_ok), null)
                            .setNegativeButton(context.getString(R.string.btn_cancel), null);

                    AlertDialog al_dialog = builder.create();
                    al_dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            TextView messageView = ((AlertDialog) dialogInterface).findViewById(android.R.id.message);
                            messageView.setTextSize(30);

                            Button positiveButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_POSITIVE);
                            positiveButton.setTextColor(ContextCompat.getColor(context, R.color.blue));
                            positiveButton.setTextSize(15);
                            //positiveButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                            Button negativeButton = ((AlertDialog) dialogInterface).getButton(DialogInterface.BUTTON_NEGATIVE);
                            negativeButton.setTextColor(ContextCompat.getColor(context, R.color.red));
                            negativeButton.setTextSize(15);
                            //negativeButton.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));

                            positiveButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Update_tc_fad();
                                    al_dialog.dismiss();

                                }
                            });
                        }
                    });

                    al_dialog.show();*/
                    break;
                }
                case R.id.btn_signature: {

                    /*Intent QR020 = new Intent();
                    QR020.setClass(v.getContext(), KT02_Signature_List.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("somay", g_soxe);
                    bundle.putString("bophan", g_bophan);
                    bundle.putString("LAYOUT", "notlogin");
                    bundle.putString("G_TC_FAA001", g_tc_faa001);
                    bundle.putString("G_TENXE", g_tenxe);
                    //bundle.putString("SERVER", g_server);
                    QR020.putExtras(bundle);
                    v.getContext().startActivity(QR020);
                    dialog.dismiss();*/
                    Intent QR020 = new Intent();
                    QR020.setClass(v.getContext(), KT02_Signature_Main.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("somay", g_soxe);
                    bundle.putString("bophan", g_bophan);
                    bundle.putString("LAYOUT", "notlogin");
                    bundle.putString("G_TC_FAA001", g_tc_faa001);
                    bundle.putString("G_TENXE", g_tenxe);
                    //bundle.putString("SERVER", g_server);
                    QR020.putExtras(bundle);
                    v.getContext().startActivity(QR020);
                    dialog.dismiss();
                    break;
                }
            }
        }

        private void Update_tc_fad() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //File dir = new File("/storage/emulated/0/Pictures/"); // thay đổi đường dẫn tới thư mục chứa hình ảnh tương ứng
                    Cursor cursor = kt02Db.getngay();
                    cursor.moveToFirst();
                    @SuppressLint("Range") String ngay = cursor.getString(cursor.getColumnIndex("ngay"));

                    File newDirectory = new File(context.getExternalMediaDirs()[0],ngay.replace("-",""));

                    File dir = new File(newDirectory + "/"); // thay đổi đường dẫn tới thư mục chứa hình ảnh tương ứng
                    File[] files = dir.listFiles();
                    if (files != null) {
                        Gson gson = new GsonBuilder().create();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://172.16.40.20/PHP/Retrofit/")
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();

                        com.lelong.kythuat.KT02.Retrofit2.DataClient apiService = retrofit.create(com.lelong.kythuat.KT02.Retrofit2.DataClient.class);

                        String imageName = null;


                        for (File file : files) {
                            String kiemtratenanh = file.getName().toString().trim().substring(0, 2);
                            if (kiemtratenanh.equals("KT")) {
                                String File_path = file.getAbsolutePath();
                                //String[] mangtenfile = File_path.split("\\.");
                                String[] mangtenfile = File_path.split("/");
                                //File_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                                //File_path = mangtenfile[0] + "." + mangtenfile[1];
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
                                            //File file = new File(filePath); // thay đổi đường dẫn tới file hình cần xóa
                                            //if (file.exists()) {

                                            //}

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
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
                    }


                    //insert tb tc_fad_file
                    //String ngay = dateFormatKT02.format(new Date()).toString();
                    //tham số Y , biểu thị cập nhật dữ liệu tới chương trình gốc, và save đến qrf_file
                    cursor_3 = kt02Db.Soxe(g_tc_faa001);
                    cursor_3.moveToFirst();
                    int num = cursor_3.getCount();
                    //station = new String[num];
                    for (int i = 0; i < num; i++) {

                        try {
                            @SuppressLint("Range") String g_somay = cursor_3.getString(cursor_3.getColumnIndex("somay"));
                            String l_somay= g_somay;
                            Cursor upl = createTable.getAll_instc_fad(g_tenxe,g_somay);
                            jsonupload = cur2Json(upl);

                            try {
                                ujobject = new JSONObject();
                                //ujobject.put("docNum", edt_maCT.getText().toString());
                                ujobject.put("ujson", jsonupload);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            final String res = upload_all("http://172.16.40.20/" + g_server + "/TechAPP/upload.php");
                            runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                                @Override
                                public void run() {
                                    if (res.contains("FALSE")) {
                                        //tvStatus.setText(getString(R.string.E10));
                                        Toast.makeText(dialog.getContext(), R.string.ERRORtvStatus_false, Toast.LENGTH_SHORT).show();
                                    }
                                    if (res.contains("ERROINS")) {
                                        //tvStatus.setText("đã được insert");
                                        Toast.makeText(dialog.getContext(), R.string.ERRORtvStatus_errorins, Toast.LENGTH_SHORT).show();
                                        //kt02Db.delete_table();
                                        //kt02Db.delete_table_fac_kt(g_tc_faa001);
                                        kt02Db.delete_table_fac_kt(g_tc_faa001,g_somay);
                                        getLVData();
                                    }
                                    if (res.contains("TRUE")) {
                                        //tvStatus.setText(g_server);
                                        Toast.makeText(dialog.getContext(), R.string.ERRORtvStatus_true, Toast.LENGTH_SHORT).show();
                                        //kt02Db.delete_table();
                                        kt02Db.delete_table_fac_kt(g_tc_faa001,g_somay);
                                        getLVData();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            String err = e.toString();
                        }
                        cursor_3.moveToNext();
                    }

                    /*Cursor upl = createTable.getAll_instc_fad(g_tenxe,"01");
                    jsonupload = cur2Json(upl);

                    try {
                        ujobject = new JSONObject();
                        //ujobject.put("docNum", edt_maCT.getText().toString());
                        ujobject.put("ujson", jsonupload);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    final String res = upload_all("http://172.16.40.20/" + g_server + "/TechAPP/upload.php");
                    runOnUiThread(new Runnable() { //Vì Toast không thể chạy đc nếu không phải UI Thread nên sử dụng runOnUIThread.
                        @Override
                        public void run() {
                            if (res.contains("FALSE")) {
                                //tvStatus.setText(getString(R.string.E10));
                                Toast.makeText(dialog.getContext(), R.string.ERRORtvStatus_false, Toast.LENGTH_SHORT).show();
                            }
                            if (res.contains("ERROINS")) {
                                //tvStatus.setText("đã được insert");
                                Toast.makeText(dialog.getContext(), R.string.ERRORtvStatus_errorins, Toast.LENGTH_SHORT).show();
                                //kt02Db.delete_table();
                                //kt02Db.delete_table_fac_kt(g_tc_faa001);
                                getLVData();
                            }
                            if (res.contains("TRUE")) {
                                //tvStatus.setText(g_server);
                                Toast.makeText(dialog.getContext(), R.string.ERRORtvStatus_true, Toast.LENGTH_SHORT).show();
                                //kt02Db.delete_table();
                                kt02Db.delete_table_fac_kt(g_tc_faa001,"01");
                                getLVData();
                            }
                        }
                    });
                    */
                }
            }).start();

        }

    };

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
            return "FALSE";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
                //content.append(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            firstDetected = true;
            e.printStackTrace();
        }
        return content.toString();
    }


}
