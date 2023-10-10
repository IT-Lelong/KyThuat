package com.lelong.kythuat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.common.util.concurrent.ListenableFuture;
import com.lelong.kythuat.KT01.KT01_DB;
import com.lelong.kythuat.KT01.OpenCamera;
import com.lelong.kythuat.KT02.KT02_DB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class KT02_OpenCamera extends AppCompatActivity {

    private Create_Table Cre_db = null;
    String selectedDate, selectedDepartment, selectedDetail, g_User, selectedSomay;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    private ImageButton captureButton;
    private Preview preview;
    private PreviewView viewFind;
    int STT, demso;
    private KT02_DB db = null;

    private boolean canCapture = true;
    private Handler captureHandler = new Handler();
    private static final long CAPTURE_DELAY = 5000; // Độ trễ giữa các lần chụp (5 giây)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt02_open_camera_activity);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("ngay");
        selectedDepartment = intent.getStringExtra("bophan");
        selectedDetail = intent.getStringExtra("hangmuc");
        selectedSomay = intent.getStringExtra("somay");
        g_User = Constant_Class.UserID;

        Cre_db = new Create_Table(getApplicationContext());
        Cre_db.open();

        viewFind = findViewById(R.id.viewFind);
        captureButton = findViewById(R.id.capture_button);
        captureButton.setOnClickListener(view -> takePhoto());

        startCamera();
    }

    private void takePhoto() {
        if (!canCapture) {
            // Không cho phép chụp ảnh liên tục, thoát ra nếu không được phép
            return;
        }


        db = new KT02_DB(this);
        db.open();

        Cursor cursor = db.demsttanh(selectedDetail, selectedDepartment, selectedDate, selectedSomay);
        cursor.moveToFirst();
        int num = cursor.getInt(cursor.getColumnIndexOrThrow("soluong"));
        STT = num + 1;
        String fileName = selectedDetail + "_" + selectedDate + "_" + selectedDepartment + "_" + selectedSomay + "_" + STT + ".png";
        //String fileName_005 = selectedDetail + "_" + selectedDate + "_" + selectedDepartment + "_" + selectedSomay + ".png";
        String fileName_005 = selectedDetail + "_" + selectedDate + "_" + selectedDepartment + "_" + selectedSomay;

        File newDirectory = new File(getExternalMediaDirs()[0], selectedDate.replace("-", ""));
        ///storage/emulated/0/Android/media/com.lelong.kythuat/20230916


        if (!newDirectory.exists()) {
            newDirectory.mkdirs(); //Tạo thư mục
        }

        File photoFile = new File(newDirectory, fileName);
        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        //Cre_db.call_insertPhotoData(selectedDetail, selectedDate, selectedDepartment, g_User, fileName);
                        luudulieuanh(fileName, fileName_005);

                        // Đường dẫn của hình ảnh đã chụp
                        String savedImagePath = photoFile.getAbsolutePath();

                        // Giới hạn kích thước của hình ảnh sau khi chụp
                        Bitmap resizedBitmap = resizeImage(savedImagePath, 800, 800);

                        // Lưu hình ảnh đã giới hạn kích thước
                        saveResizedImage(resizedBitmap, photoFile);

                        // Nén ảnh đã chụp
                        //compressImage(photoFile);

                        runOnUiThread(() -> {
                            Toast.makeText(KT02_OpenCamera.this, "Photo saved: " + fileName, Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {

                    }


                });
        // Sau khi chụp ảnh, đặt canCapture thành false để ngăn chụp ảnh liên tục
        canCapture = false;

        // Đặt thời gian trễ trước khi cho phép chụp ảnh tiếp theo
        captureHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                canCapture = true;
            }
        }, CAPTURE_DELAY);

    }

    private Bitmap resizeImage(String imagePath, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        // Đọc thông tin kích thước của hình ảnh mà không nạp toàn bộ hình ảnh vào bộ nhớ
        BitmapFactory.decodeFile(imagePath, options);

        // Tính toán tỷ lệ thu nhỏ dựa trên kích thước tối đa đã cho
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
        float scaleFactor = Math.min((float) imageWidth / maxWidth, (float) imageHeight / maxHeight);

        // Thiết lập lại options để nạp toàn bộ hình ảnh vào bộ nhớ
        options.inJustDecodeBounds = false;
        options.inSampleSize = (int) scaleFactor;

        // Đọc lại hình ảnh và thay đổi kích thước
        return BitmapFactory.decodeFile(imagePath, options);
    }

    private void saveResizedImage(Bitmap resizedBitmap, File outputFile) {
        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
            resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void luudulieuanh(String g_fileName, String g_fileName_005) {
        db = new KT02_DB(this);
        db.open();
        db.appendUPDAEhinhanh(selectedDetail, selectedDate, selectedDepartment, selectedSomay, "tenhinh", "soluong", g_fileName_005, STT);
    }

    private void startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraUseCases(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void bindCameraUseCases(@NonNull ProcessCameraProvider cameraProvider) {
        /*preview = new Preview.Builder().build();
        imageCapture = new ImageCapture.Builder().build();

        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture);

        // Liên kết Preview với viewFinder
        preview.setSurfaceProvider(viewFind.getSurfaceProvider());*/

        preview = new Preview.Builder().build();
        imageCapture = new ImageCapture.Builder().build();

        cameraProvider.unbindAll();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        // Bind các use case vào lifecycle của ứng dụng
        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, preview, imageCapture);

        // Kiểm tra xem thiết bị có hỗ trợ flash hay không
        CameraInfo cameraInfo = camera.getCameraInfo();
        if (cameraInfo.hasFlashUnit()) {
            // Bật đèn LED
            camera.getCameraControl().enableTorch(true);

            // Sau khi bạn đã hoàn thành việc sử dụng đèn LED, bạn có thể tắt nó bằng cách sử dụng:
            // camera.getCameraControl().enableTorch(false);
        }

        // Liên kết Preview với viewFinder
        preview.setSurfaceProvider(viewFind.getSurfaceProvider());

    }
}