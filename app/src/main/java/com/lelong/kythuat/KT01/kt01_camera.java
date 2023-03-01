package com.lelong.kythuat.KT01;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
    ImageView imageView;
    Button btnTakePicture;
    TextView ttxtview;
    EditText eeditText;
    Button savePicture;
    private  KT01_DB db=null;
    String ID;
    String ID1;
    String lbophan1;
    String currentDate;
    String tenanh;
    TextView menuID;
    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt01_camera);
        Bundle getbundle = getIntent().getExtras();
        //actionBar = getSupportActionBar();
        //actionBar.hide();

        LocalDate currentDate = LocalDate.now();
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
        } catch (IOException e) {
            e.printStackTrace();
        }

        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        String formattedTime = now.format(formatter);
        System.out.println(formattedTime);
        //Bundle bundle = new Bundle();
        //lbophan1 = getbundle.getString("lbophan1");
       // ID1 = getbundle.getString("ID1");
        ID = getbundle.getString("ID");
        tenanh = ID +"_"+currentDate+"_"+formattedTime+"_"+ID1;
        ttxtview  = findViewById(R.id.menuID);
        ttxtview.setText(tenanh);
        imageView = findViewById(R.id.image_view12);
        btnTakePicture = findViewById(R.id.btn_take_picture12);
       // new  IDname().execute(ID);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db=new KT01_DB(v.getContext());
                db.open();
                db.appendUPDAE(ID, tenanh, String.valueOf(currentDate),ID1,"TC_FAA005");
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
            imageView.setImageBitmap(photo);
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




}
