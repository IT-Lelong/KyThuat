package com.lelong.kythuat.KT04;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.TextUtils;
import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.KT04.KT04_Camera;
import com.lelong.kythuat.KT04.KT04_DB;
import com.lelong.kythuat.KT04.KT04_OpenCamera;
import com.lelong.kythuat.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class KT04_Camera extends AppCompatActivity {
    Button btnTakePicture;
    TextView ttxtview;
    private KT04_DB db = null;
    String ID, b;
    Cursor cursor_3;
    Cursor cursor_5,cursor_6;
    String ID1;
    int STT, demso;
    String l_bp;
    String l_ngay,l_to,g_to,l_ca,l_hm;
    ImageView imageView;
    LocalDate currentDate;
    String tenanh, luutenanh;
    TextView menuID;
    private static final int CAMERA_REQUEST = 1888;
    String l_checkdk = "";
    int aa;
    String myArray;
    EditText edt_ghichu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.kt04_camera);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        imageView = (ImageView) findViewById(R.id.imageView);
        edt_ghichu = findViewById(R.id.edt_ghichu);
        menuID = findViewById(R.id.menuID);
        edt_ghichu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edt_ghichu.getText().toString().trim().length() > 0) {

                    db.updateGhichu(l_hm,ID,l_ngay,ID1, edt_ghichu.getText().toString());
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l_checkdk = "";
                aa = 0;
                // Create dialog to show enlarged image
                Dialog dialog = new Dialog(KT04_Camera.this);
                dialog.setContentView(R.layout.kt04_dialog_enlarged_image);
                ImageView imageView1 = dialog.findViewById(R.id.imageView1);
                Button btn1 = dialog.findViewById(R.id.btn1);
                Button btn2 = dialog.findViewById(R.id.btn2);
                TextView textView1 = dialog.findViewById(R.id.menuID11);
                imageView1.setImageDrawable(imageView.getDrawable());

                textView1.setText(myArray);
                textView1.setTextColor(Color.parseColor("#669999"));
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (l_checkdk == "TRUE") {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT04_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray;
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("KT04_01_007"));
                                    boolean result = db.getcountAnhpost(tenanh);
                                    if(result == true){
                                        File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                        String a = newDirectory + "/" + tenanh ;
                                        File fileToDelete = new File(a);
                                        boolean deleted = fileToDelete.delete();
                                        if (deleted) {
                                            Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                            db.delete_tenanh(tenanh);
                                            db.delete_tenanh_tc_far(tenanh);
                                            Cursor cursor = db.demsttanh(ID, ID1, l_ngay,l_hm);
                                            cursor.moveToFirst();
                                            int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("KT04_01_007"));
                                            int loadhinh = num23 - 1;
                                        /*if (loadhinh == 0) {
                                            db.appendUPDAEhinhanh(ID, "", loadhinh, l_ngay, ID1, "KT04_01_008", "KT04_01_007");
                                        }*/
                                            db.appendUPDAEhinhanh(l_hm, "", loadhinh, l_ngay, ID, "KT04_01_008", "KT04_01_007",ID1);
                                            imageView.setImageDrawable(null);
                                            menuID.setText("...");
                                            edt_ghichu.setText(" ");
                                            Toast.makeText(KT04_Camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                        }
                                    }
                                    else {
                                        Toast.makeText(KT04_Camera.this, "Anh đã được kết chuyển", Toast.LENGTH_SHORT).show();
                                    }

                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    //onResume();
                                }

                            });
                            dialog.dismiss();
                            //loadanh(ID,l_ngay,ID1);
                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(KT04_Camera.this);
                            builder.setMessage("Bạn có chắc chắn muốn xóa không?");
                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String tenanh = myArray;
                                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                                    //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("KT04_01_007"));
                                    boolean result = db.getcountAnhpost(tenanh);
                                    if(result == true){
                                        File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                                        String a = newDirectory + "/" + tenanh ;
                                        File fileToDelete = new File(a);
                                        boolean deleted = fileToDelete.delete();
                                        if (deleted) {
                                            Log.d("BBB", "Deleted file: " + fileToDelete.getAbsolutePath());
                                            db.delete_tenanh(tenanh);
                                            db.delete_tenanh_tc_far(tenanh);
                                            Cursor cursor = db.demsttanh(ID, ID1, l_ngay,l_hm);
                                            cursor.moveToFirst();
                                            int num23 = cursor.getInt(cursor.getColumnIndexOrThrow("KT04_01_007"));
                                            int loadhinh = num23 - 1;
                                            if (loadhinh == 0) {
                                                db.appendUPDAEhinhanh(l_hm, "", loadhinh, l_ngay, ID, "KT04_01_008", "KT04_01_007",ID1);
                                            }else {
                                                db.appendUPDAEhinhanh(l_hm, luutenanh,loadhinh, l_ngay,ID,"KT04_01_008","KT04_01_007",ID1);
                                            }

                                            imageView.setImageDrawable(null);
                                            menuID.setText("...");
                                            edt_ghichu.setText(" ");
                                            Toast.makeText(KT04_Camera.this, "Ảnh đã được xóa", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Log.d("BBB", "Failed to delete file: " + fileToDelete.getAbsolutePath());
                                        }
                                    }
                                    else {
                                        Toast.makeText(KT04_Camera.this, "Anh đã được kết chuyển", Toast.LENGTH_SHORT).show();
                                    }

                                    // Xóa ở đây
                                    //Toast.makeText(kt01_camera.this, "Anh đã được xóa", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                    onResume();
                                }
                            });
                            dialog.dismiss();
                            //loadanh(ID,l_ngay,ID1,l_to);
                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // dialog.dismiss();
                                }
                            });
                            builder.show();
                        }

//////////
                    }
                });

                String text = textView1.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    dialog.dismiss();
                } else {
                    dialog.show();
                }

                //loadanh(ID,l_ngay,ID1,l_to);
            }

        });
        btnTakePicture = findViewById(R.id.btn_take_picture12);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor getTenAnh = db.getTen_Anh(ID,l_ngay,ID1,l_hm);
                getTenAnh.moveToFirst();
                String l_count = "";
                for (int i = 0; i < getTenAnh.getCount(); i++) {
                    l_count = getTenAnh.getString(getTenAnh.getColumnIndexOrThrow("l_count"));
                    getTenAnh.moveToNext();
                }
                if(Integer.parseInt(l_count) == 0)
                {
                    Intent intent = new Intent(getApplicationContext(), KT04_OpenCamera.class);
                    intent.putExtra("ngay", l_ngay);
                    intent.putExtra("ca", ID1);
                    intent.putExtra("hangmuc", l_hm);
                    intent.putExtra("manv", ID);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Thêm cờ vào Intent
                    startActivity(intent);
                }
                else {
                    Toast.makeText(KT04_Camera.this, "Xóa ảnh cũ trước khi chụp ảnh mới!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle getbundle = getIntent().getExtras();

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        String formattedTime = now.format(formatter);
        System.out.println(formattedTime);
        l_hm=getbundle.getString("l_hm");
        l_ngay = getbundle.getString("l_ngay");
        l_ca = getbundle.getString("l_ca");
        if (l_ngay != null) {

            ID1 = getbundle.getString("l_ca");
            l_ngay = getbundle.getString("l_ngay");
        } else {
            try {
                InputStream is = getApplicationContext().openFileInput("mydata.txt");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                ID1 = sb.toString();
                currentDate = LocalDate.now();
                l_ngay = String.valueOf(currentDate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        db = new KT04_DB(this);
        db.open();

        if (Constant_Class.UserFactory.equals("DH")) {

            ID = getbundle.getString("ID");
            Cursor cursor = db.demsttanh(ID, ID1, l_ngay,l_hm);
            //Cursor cursor = db.getstt(ID, ID1, l_ngay);
            cursor.moveToFirst();
            int num = cursor.getInt(cursor.getColumnIndexOrThrow("KT04_01_007"));
            //int num = cursor.getInt(cursor.getColumnIndexOrThrow("stt"));
            //STT = num + 1;
            STT = num ;
            //tenanh = ID + "_" + l_ngay + "_" + ID1 + "_" + STT;
            //tenanh = l_to + "_" +ID + "_" + l_ngay + "_" + ID1 + "_" + STT;
            tenanh = l_hm + "_" + ID1 + "_" + l_ngay + "_" + ID + "_" + STT;
            luutenanh = l_hm + "_" + ID1 + "_" + l_ngay + "_" + ID;
            ttxtview = findViewById(R.id.menuID);
            if(STT > 0){
                ttxtview.setText(tenanh);
            }
            else{
                ttxtview.setText("");
            }
            if (num >= 1) {
                loadanh(l_hm,ID, l_ngay, ID1);
            }
        }else {
            l_to="XBL";
            ID = getbundle.getString("ID");
            Cursor cursor = db.demsttanh(ID, ID1, l_ngay,l_hm);
            //Cursor cursor = db.getstt(ID, ID1, l_ngay);
            cursor.moveToFirst();
            int num = cursor.getInt(cursor.getColumnIndexOrThrow("KT04_01_007"));
            //int num = cursor.getInt(cursor.getColumnIndexOrThrow("stt"));
            //STT = num + 1;
            STT = num ;
            //tenanh = ID + "_" + l_ngay + "_" + ID1 + "_" + STT;
            //tenanh = l_to + "_" +ID + "_" + l_ngay + "_" + ID1 + "_" + STT;
            tenanh = ID +"_" + l_ngay + "_" + ID1 + "_" + STT;
            luutenanh = ID +"_" + l_ngay + "_" + ID1;
            ttxtview = findViewById(R.id.menuID);
            if(STT > 0){
                ttxtview.setText(tenanh);
            }
            else{
                ttxtview.setText("");
            }
            if (num >= 1) {
                loadanh(ID, l_ngay, ID1,l_to);
            }
        }
    }
    private void loadanh(String key, String l_bp, String l_ngay,String l_to) {
        cursor_6 =db.xuatghichu(key,l_ngay,l_bp,l_to);
        if (cursor_6 != null && cursor_6.moveToFirst()) {
            int columnIndex = cursor_6.getColumnIndex("tc_far006");
            if (columnIndex != -1) {
                String g_ghichu = cursor_6.getString(columnIndex);
                if (g_ghichu != null) {
                    edt_ghichu.setText(g_ghichu);
                } else {
                    // Nếu giá trị là null, bạn có thể xử lý ở đây hoặc đặt một giá trị mặc định cho EditText
                    edt_ghichu.setText(""); // Đặt một giá trị mặc định (ví dụ: chuỗi rỗng) cho EditText
                }
            }
        }
        cursor_3 = db.lananh(key, l_ngay, l_bp,l_to);
        cursor_3.moveToFirst();
        //int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("KT04_01_007"));
        int num = cursor_3.getCount();
        if (num > 6) {
            num = 6;
        }
        cursor_5 = db.lananh2(key, l_ngay, l_bp,l_to);
        cursor_5.moveToFirst();
        //int tong = ((num2 - 6) + 1);
        int showanh = 0;
        if (num > 0) {
            for (int i = 1; i <= num; i++) {
                try {
                    @SuppressLint("Range") String tenanh = cursor_5.getString(cursor_5.getColumnIndex("tenanh"));
                    // String   a = "/storage/emulated/0/Pictures/KT010103_2023-02-25_13_45_37_ABI3100000.jpg";
                    File newDirectory = new File(getExternalMediaDirs()[0],l_ngay.replace("-",""));
                    //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                    String a = newDirectory + "/" + tenanh ;
                    //num2 = num2 - 1;
                    File imgFile = new File(a);

                    if (imgFile.exists()) {
                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.RGB_565;
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
                            imageView.setImageBitmap(myBitmap);
                            myArray = tenanh;

                            showanh = showanh + 1;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    String err = e.toString();
                }
                cursor_5.moveToNext();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            saveImage(photo);
        }
    }
    private void saveImage(Bitmap finalBitmap) {
        String savedImageURL = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                finalBitmap,
                tenanh,
                "Image Context"
        );
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".png";
        File file = new File(savedImageURL, fname);
        if (file.exists()) file.delete();
        try {

            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}