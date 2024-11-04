package com.lelong.kythuat.KT03;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.KT01.KT01_DB;
import com.lelong.kythuat.KT01.OpenCamera;
import com.lelong.kythuat.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class KT03_OpenCamera extends AppCompatActivity {
    private Create_Table Cre_db = null;
    String selectedDate, selectedID, selectedDetail, g_User, selectedCa;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private ImageCapture imageCapture;
    private ImageButton captureButton;
    private Preview preview;
    private PreviewView viewFind;
    int STT, demso;
    private KT03_DB db = null;

    private boolean canCapture = true;
    private Handler captureHandler = new Handler();
    private static final long CAPTURE_DELAY = 5000; // Độ trễ giữa các lần chụp (5 giây)
    private File lastCapturedPhotoFile;
    private String fileName, fileName_005;
    private File photoFile;
    private String _to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.kt03_activity_open_camera);
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("ngay");
        selectedID = intent.getStringExtra("manv");
        selectedDetail = intent.getStringExtra("hangmuc");
        selectedCa = intent.getStringExtra("ca");
        g_User = Constant_Class.UserID;

        Cre_db = new Create_Table(getApplicationContext());
        Cre_db.open();

        viewFind = findViewById(R.id.viewFind);
        captureButton = findViewById(R.id.capture_button);
        captureButton.setOnClickListener(view -> takePhoto());

        startCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        lastCapturedPhotoFile = null;
    }

    @Override
    public void onBackPressed() {
        if (photoFile != null) {
            saveImgLast(photoFile, fileName, fileName_005);
        }
        super.onBackPressed();
    }

    private void takePhoto() {
        if (!canCapture) {
            // Không cho phép chụp ảnh liên tục, thoát ra nếu không được phép
            return;
        }

        fileName = "";
        fileName_005 = "";
        db = new KT03_DB(this);
        db.open();
        String timestamp = new SimpleDateFormat("HHmmss", Locale.getDefault()).format(new Date());
        Cursor cursor = db.demsttanh(selectedID, selectedCa, selectedDate,selectedDetail);
        //Cursor cursor = db.getstt(ID, ID1, l_ngay);
        cursor.moveToFirst();
        //int num = cursor.getInt(cursor.getColumnIndexOrThrow("KT03_01_007"));
        int num =0;
        //int num = cursor.getInt(cursor.getColumnIndexOrThrow("stt"));
        STT = num + 1;

        fileName = selectedDetail + "_" + selectedCa + "_" + selectedDate + "_" + selectedID + "_" + STT + ".png";
        fileName_005 = selectedDetail + "_" + selectedCa + "_" + selectedDate + "_" + selectedID + ".png";
        File newDirectory = new File(getExternalMediaDirs()[0], selectedDate.replace("-", ""));
        /*File mediaDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "KyThuatFolder");
        if (!mediaDir.exists()) {
            mediaDir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }

        File newDirectory = new File(mediaDir, selectedDate.replace("-", ""));*/

        ///storage/emulated/0/Android/media/com.lelong.kythuat/20230916
        if (!newDirectory.exists()) {
            newDirectory.mkdirs(); //Tạo thư mục
        }
        photoFile = null;
        photoFile = new File(newDirectory, fileName);
        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(photoFile).build();

        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        String savedImagePath = photoFile.getAbsolutePath();
                        lastCapturedPhotoFile = photoFile;
                        // Nếu bạn muốn cố định hướng ảnh ở đây:
                        fixImageOrientation(savedImagePath);
                        //Cre_db.call_insertPhotoData(selectedDetail, selectedDate, selectedDepartment, g_User, fileName);
                        //luudulieuanh(fileName, fileName_005);

                        // Đường dẫn của hình ảnh đã chụp
                        //String savedImagePath = photoFile.getAbsolutePath();

                        // Giới hạn kích thước của hình ảnh sau khi chụp
                        //Bitmap resizedBitmap = resizeImage(savedImagePath, 800, 800);

                        // Lưu hình ảnh đã giới hạn kích thước
                        //saveResizedImage(resizedBitmap, photoFile);

                        // Nén ảnh đã chụp
                        //compressImage(photoFile);

                        runOnUiThread(() -> {
                            Toast.makeText(KT03_OpenCamera.this, "Photo saved: " + fileName, Toast.LENGTH_SHORT).show();
                        });
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(KT03_OpenCamera.this, "Photo saved: ", Toast.LENGTH_SHORT).show();
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
        lastCapturedPhotoFile = null;
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
    private void fixImageOrientation(String imagePath) {
        try {
            // Đọc thông tin Exif của ảnh
            ExifInterface exifInterface = new ExifInterface(imagePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    // Không cần xoay nếu hướng ban đầu đã là đúng
                    break;
            }

            // Tạo bitmap mới với hướng đã điều chỉnh (nếu cần)
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            // Ghi đè tệp ảnh gốc với bitmap đã điều chỉnh hướng
            File imageFile = new File(imagePath);
            FileOutputStream fos = new FileOutputStream(imageFile);
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            // Hiển thị thông báo đã lưu ảnh
            runOnUiThread(() -> {
                Toast.makeText(KT03_OpenCamera.this, "Photo saved: " + fileName, Toast.LENGTH_SHORT).show();
            });

            // Xoá bitmap khỏi bộ nhớ
            bitmap.recycle();
            rotatedBitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveImgLast(File photofile, String filename, String filename_005) {
        luudulieuanh(filename, filename_005);

        // Đường dẫn của hình ảnh đã chụp
        String savedImagePath = photofile.getAbsolutePath();

        // Giới hạn kích thước của hình ảnh sau khi chụp
        Bitmap resizedBitmap = resizeImage(savedImagePath, 800, 800);

        // Lưu hình ảnh đã giới hạn kích thước
        saveResizedImage(resizedBitmap, photofile);
    }
    private void luudulieuanh(String g_fileName, String g_fileName_005) {
        db = new KT03_DB(this);
        db.open();
        Cursor cursor = db.getstt(selectedDetail, selectedID, selectedDate);
        cursor.moveToFirst();
        int num = cursor.getInt(cursor.getColumnIndexOrThrow("stt"));
        demso = num + 1;
        //insert hình vào tc_far_file

        db.ins_img_tc_far(selectedDetail, selectedDate, selectedID, selectedCa, g_fileName);
        db.append1(selectedDetail, selectedDate, selectedID, String.valueOf(demso), g_fileName,selectedCa);
        db.appendUPDAEhinhanh(selectedDetail, g_fileName_005, STT, selectedDate, selectedID, "KT03_01_008", "KT03_01_010",selectedCa);
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
}