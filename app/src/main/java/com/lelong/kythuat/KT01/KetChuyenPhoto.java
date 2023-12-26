package com.lelong.kythuat.KT01;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.lelong.kythuat.KT01.Retrofit2.DataClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KetChuyenPhoto {
    DataClient apiService;
    KT01_DB db;
    private final Context context;
    private final Cursor c_getTc_far;
    private KetChuyen_Dialog ketChuyenDialog;

    public KetChuyenPhoto(Context context, Cursor c_getTc_far, KetChuyen_Dialog ketChuyenDialog) {
        this.context = context;
        this.c_getTc_far = c_getTc_far;
        this.ketChuyenDialog = ketChuyenDialog;

        db = new KT01_DB(context);
        db.open();

        Gson gson = new GsonBuilder().create();

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS) // Ví dụ: timeout sau 60 giây
                .build();

                Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.40.20/PHP/Retrofit/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(DataClient.class);

        if (c_getTc_far.getCount() > 0) {
            Call_transPhoto();
        }
    }

    private void Call_transPhoto() {
        c_getTc_far.moveToFirst();
        ketChuyenDialog.setProgressBar(c_getTc_far.getCount());
        // Sử dụng một danh sách các tệp tin cần tải lên
        List<FileInfo> filesToUpload = new ArrayList<>();
        for (int i = 0; i < c_getTc_far.getCount(); i++) {
            String image_no = c_getTc_far.getString(c_getTc_far.getColumnIndexOrThrow("tc_far001"));
            String image_date = c_getTc_far.getString(c_getTc_far.getColumnIndexOrThrow("tc_far002"));
            String image_dept = c_getTc_far.getString(c_getTc_far.getColumnIndexOrThrow("tc_far003"));
            String image_to = c_getTc_far.getString(c_getTc_far.getColumnIndexOrThrow("tc_far004"));
            String image_name = c_getTc_far.getString(c_getTc_far.getColumnIndexOrThrow("tc_far005"));
            String image_note = c_getTc_far.getString(c_getTc_far.getColumnIndexOrThrow("tc_far006"));
            File newDirectory = new File(context.getExternalMediaDirs()[0], image_date.replace("-", ""));

            //File dir = new File(newDirectory + "/"); // thay đổi đường dẫn tới thư mục chứa hình ảnh tương ứng
            String image_path = "/storage/emulated/0/Android/media/com.lelong.kythuat/" + image_date.replace("-", "") + "/" + image_name;
            File file = new File(image_path);
            //filesToUpload.add(file);

            // Tạo một đối tượng FileInfo từ thông tin tên tệp và ngày
            FileInfo fileInfo = new FileInfo(image_no,image_date,image_dept,image_to,image_name,image_note,file);

            // Thêm FileInfo vào danh sách
            filesToUpload.add(fileInfo);
            c_getTc_far.moveToNext();
        }
        // Bắt đầu quá trình tải lên bằng cách gọi hàm đệ quy
        uploadFileRecursive(filesToUpload, 0);
    }

    public class FileInfo {

        private final String image_no;
        private final String image_date;
        private final String image_dept;
        private final String image_to;
        private final String image_name;
        private final String image_note;
        private final File file;

        public String getImage_no() {
            return image_no;
        }

        public String getImage_date() {
            return image_date;
        }

        public String getImage_dept() {
            return image_dept;
        }

        public String getImage_to() {
            return image_to;
        }
        public String getImage_note() {
            return image_note;
        }

        public String getImage_name() {
            return image_name;
        }

        public File getFilePath() {
            return file;
        }

        public FileInfo(String image_no, String image_date, String image_dept, String image_to, String image_name,String image_note, File file) {
            this.image_no = image_no;
            this.image_date = image_date;
            this.image_dept = image_dept;
            this.image_to = image_to;
            this.image_note = image_note;
            this.image_name = image_name;
            this.file = file;
        }

    }

    // Hàm đệ quy để tải lên từng tệp tin một
    void uploadFileRecursive(final List<FileInfo> files, final int currentIndex) {
        if (currentIndex >= files.size()) {
            ketChuyenDialog.setStatus("2");
            //db.delete_tenhinh_all();
            //db.delete_table_faa_kt("KT01");
            //Call_Refresh_Data();
            ketChuyenDialog.setEnableBtn(false,true);
            // Tất cả tệp tin đã được tải lên
            return;
        }

        FileInfo fileToUpload = files.get(currentIndex);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileToUpload.getFilePath());
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", fileToUpload.getImage_name(), requestFile);

        Call<ResponseBody> callImage = apiService.uploadImage1(imagePart, null);
        callImage.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    // Xử lý kết quả ở đây
                    InputStream inputStream = response.body().byteStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sb = new StringBuilder();
                    String line;

                    while (true) {
                        try {
                            if (!((line = reader.readLine()) != null)) break;
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        sb.append(line);
                    }

                    String responseData = sb.toString(); // Dữ liệu JSON
                    // Sử dụng Gson để phân tích dữ liệu JSON thành đối tượng
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(responseData, JsonObject.class);

                    // Trích xuất các trường từ JSON
                    String status = jsonObject.get("status").getAsString();
                    String message = jsonObject.get("message").getAsString();

                    if (status.equals("OK")) {
                        db.update_tc_farpost(fileToUpload.image_no,fileToUpload.image_date,fileToUpload.image_dept,fileToUpload.image_to,fileToUpload.image_name);
                        ketChuyenDialog.updateProgressBar(currentIndex + 1);
                        // Gọi đệ quy để tải lên tệp tin tiếp theo
                        uploadFileRecursive(files, currentIndex + 1);
                    } else {
                        Toast.makeText(context, "Lỗi : " + message, Toast.LENGTH_LONG).show();
                    }


                } else {
                    // Xử lý lỗi ở đây
                    String abc;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Xử lý khi có lỗi xảy ra trong quá trình gửi dữ liệu

                // Gọi đệ quy để tải lên tệp tin tiếp theo
                uploadFileRecursive(files, currentIndex + 1);
            }
        });
    }
}
