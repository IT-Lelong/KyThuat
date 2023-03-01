package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lelong.kythuat.KT01.Retrofit2.APIYtils;
import com.lelong.kythuat.KT01.Retrofit2.DataClient;
import com.lelong.kythuat.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public
class kt01_update extends AppCompatActivity {
    private Button btnEditText;
    private  KT01_DB db=null;
    Cursor cursor_1, cursor_2;
    ImageView imgdangky;
    int Request_Code_Image = 1234;
    String realpath = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt01_update);
        nutchucnang();
        imgdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, Request_Code_Image);


            }
        });
        btnEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = new KT01_DB(v.getContext());
                db.open();
                cursor_1 = db.getAll_tc_faa("KT01");
                cursor_1.moveToFirst();
                int num = cursor_1.getCount();
                for (int i = 0; i < num; i++) {

                    try {
                        @SuppressLint("Range") String tc_faa005 = cursor_1.getString(cursor_1.getColumnIndex("tc_faa005"));
                       // String   a = "/storage/emulated/0/Pictures/KT010103_2023-02-25_13_45_37_ABI3100000.jpg";
                        String   a = "/storage/emulated/0/Pictures/"+ tc_faa005 + "" + ".jpg"+"";
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

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Request_Code_Image && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            realpath = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgdangky.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void nutchucnang() {
        btnEditText = (Button) findViewById(R.id.register1_button);
        imgdangky = findViewById(R.id.imageview2);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }
}

