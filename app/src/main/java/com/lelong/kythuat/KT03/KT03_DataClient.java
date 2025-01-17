package com.lelong.kythuat.KT03;

import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface KT03_DataClient {
    @POST("upload_KT03.php") // Thay thế bằng đường dẫn API của bạn
    Call<ResponseBody> sendDataToServer(@Body JsonObject jsonObject);

    @Multipart
    @POST("uploadImage3.php")
    Call<ResponseBody> uploadImage3(
            @Part MultipartBody.Part image,  // Một phần của dữ liệu là tệp ảnh
            @Part("description") RequestBody description  // Phần khác của dữ liệu (nếu có)
    );

    @Multipart
    @POST("UploadHinhAnh.php")
    Call<String> UploadPhot(@Part MultipartBody.Part body);

    @Multipart
    @POST("uploadPhoto.php")
    Call<ResponseBody> uploadImage(
            @Part MultipartBody.Part image // Một phần của dữ liệu là tệp ảnh
            //@Part("description") RequestBody description  // Phần khác của dữ liệu (nếu có)
    );
}
