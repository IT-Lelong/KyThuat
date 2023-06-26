package com.lelong.kythuat;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
//import com.github.gcacace.signaturepad.views.SignaturePad;
//com.github.gcacace.signaturepad.views.SignaturePad
//import com.github.gcacace.signaturepad.view.*;

public class SignaturePad extends AppCompatActivity {
    private Button saveButton;
    private Button clearButton;
    private View signaturePad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature_pad);
        //SignaturePad signaturePad = findViewById(R.id.signature_pad);
        saveButton = findViewById(R.id.save_button);
        //clearButton = findViewById(R.id.clear_button);
        signaturePad = findViewById(R.id.signaturePad);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap signatureBitmap = getTransparentSignatureBitmap();
                //Bitmap signatureBitmap = null;
                // Lưu chữ ký vào hệ thống hoặc xử lý theo nhu cầu của bạn
                // Ví dụ: lưu ảnh chữ ký vào thư mục ứng dụng
                //String filePath = getExternalFilesDir(null).getAbsolutePath() + "/signature.png";
                String filePath = ("/storage/emulated/0/Pictures" + "/signature10.png");
                //String a = "/storage/emulated/0/Pictures/" + tenanh + ".jpg" + "";
                try {
                    FileOutputStream fos = new FileOutputStream(filePath);
                    signatureBitmap.compress(Bitmap.CompressFormat.PNG, 80, fos);
                    fos.flush();
                    fos.close();
                    Toast.makeText(SignaturePad.this, "Chữ ký đã được lưu", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        /*clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap blankBitmap = Bitmap.createBitmap(signaturePad.getWidth(), signaturePad.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(blankBitmap);
                canvas.drawColor(Color.WHITE);
                signaturePad.invalidate();
            }
        });

         */
    }

    public Bitmap getTransparentSignatureBitmap() {
        // Tạo một bitmap mới với kích thước tương đương với kích thước của SignaturePad
        //Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(signaturePad.getWidth(), signaturePad.getHeight(), Bitmap.Config.ARGB_8888);
        // Tạo một Canvas để vẽ chữ ký lên bitmap
        Canvas canvas = new Canvas(bitmap);

        // Vẽ nền trong suốt trên bitmap
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        // Vẽ chữ ký lên bitmap
        signaturePad.draw(canvas);

        return bitmap;
    }


}