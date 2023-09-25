package com.lelong.kythuat.KT01.Retrofit2;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient {
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

