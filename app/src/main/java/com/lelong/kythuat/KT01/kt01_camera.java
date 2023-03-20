package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public
class kt01_camera  extends AppCompatActivity {
    ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
    Button btnTakePicture;
    TextView ttxtview;
    EditText eeditText;
    Button savePicture;
    private  KT01_DB db=null;
    String ID,b;
    Cursor cursor_3;
    String ID1;
    int STT,demso;
    String l_bp;
    String l_ngay;
    ImageView[] imageViews = new ImageView[6];
    String lbophan1;
    LocalDate  currentDate;
    String tenanh,luutenanh;
    TextView menuID;
    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt01_camera);
        imageViews[0] = (ImageView) findViewById(R.id.imageView1);
        imageViews[1] = (ImageView) findViewById(R.id.imageView2);
        imageViews[2] = (ImageView) findViewById(R.id.imageView3);
        imageViews[3] = (ImageView) findViewById(R.id.imageView4);
        imageViews[4] = (ImageView) findViewById(R.id.imageView5);
        imageViews[5] = (ImageView) findViewById(R.id.imageView6);

        btnTakePicture = findViewById(R.id.btn_take_picture12);
       // if (num >= 1) {
           // loadanh();
       // }

       // new  IDname().execute(ID);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db=new KT01_DB(v.getContext());
                db.open();
                db.appendUPDAEhinhanh(ID, luutenanh,STT, String.valueOf(currentDate),ID1,"TC_FAA005","TC_FAA011");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
           // imageView.setImageBitmap(photo);
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
        String fname = "Image-" + n + ".jpg";
        File file = new File(savedImageURL, fname);
        if (file.exists()) file.delete();
        try {

            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Bundle getbundle = getIntent().getExtras();
        //actionBar = getSupportActionBar();
        //actionBar.hide();




        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        String formattedTime = now.format(formatter);
        System.out.println(formattedTime);
        //Bundle bundle = new Bundle();
        //lbophan1 = getbundle.getString("lbophan1");
        // ID1 = getbundle.getString("ID1");
        l_ngay = getbundle.getString("l_ngay");
        if (l_ngay != null){

            ID1 = getbundle.getString("l_bp");
            l_ngay = getbundle.getString("l_ngay");
        }else
        {
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
        db = new KT01_DB(this);
        db.open();

        ID = getbundle.getString("ID");
        Cursor cursor = db.demsttanh(ID,ID1,l_ngay);
        cursor.moveToFirst();
        int num = cursor.getInt(cursor.getColumnIndexOrThrow("tc_faa011"));
        STT = num + 1;
        tenanh = ID+"_"+l_ngay+"_"+ID1+"_"+STT;
        luutenanh = ID+"_"+l_ngay+"_"+ID1;
        ttxtview  = findViewById(R.id.menuID);
        ttxtview.setText(tenanh);
        if (num >= 1){
            loadanh(ID,l_ngay,ID1);
        }

    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            realpath = getRealPathFromURI(uri);
            File imgFile = new  File(realpath);

            if(imgFile.exists()){
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
                    imageView2.setImageBitmap(myBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        super.onActivityResult(requestCode, resultCode, data);
    }*/
  private void loadanh(String key ,String l_ngay, String l_bp) {
      cursor_3 = db.xuathinhanh(key,l_ngay,l_bp);
      cursor_3.moveToFirst();
      int num2 = cursor_3.getInt(cursor_3.getColumnIndexOrThrow("tc_faa011"));
      //int num = cursor_3.getCount();
      @SuppressLint("Range") String tc_faa005 = cursor_3.getString(cursor_3.getColumnIndex("tc_faa005"));
      int tong = ((num2 - 6) + 1);
      int showanh = 0;
      for (int i = tong; i <= num2 ; i ++) {
          try {

              // String   a = "/storage/emulated/0/Pictures/KT010103_2023-02-25_13_45_37_ABI3100000.jpg";
              String a = "/storage/emulated/0/Pictures/" + tc_faa005 + "_"+ i +"" + ".jpg" + "";
              //num2 = num2 - 1;
              File imgFile = new File(a);

              if (imgFile.exists()) {
                  try {
                      BitmapFactory.Options options = new BitmapFactory.Options();
                      options.inPreferredConfig = Bitmap.Config.RGB_565;
                      Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(), options);
                      imageViews[showanh].setImageBitmap(myBitmap);
                      showanh = showanh +1;
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
          }catch (Exception e) {
              String err = e.toString();
          }
      }

  }


}
