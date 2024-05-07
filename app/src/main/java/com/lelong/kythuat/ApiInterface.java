package com.lelong.kythuat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiInterface {
    // Down bản cập nhật
    @GET
    @Streaming
    Call<ResponseBody> downloadFile(@Url String fileUrl);

    //Kiểm tra phiên bản ứng dụng
    @GET("check_ver.php")
    Call<JsonObject> checkAppVersion(@Query("app") String appName);

    //Lấy thông tin nhân viên
    @GET
    Call<List<JsonObject>> getData(@Url String url);

    //Lấy dữ liệu cơ bản của các table
    @GET
    Call<JsonArray> getDataTable(@Url String url);

    @POST("upload_Data.php") // Thay thế bằng đường dẫn API của bạn
    Call<ResponseBody> sendDataToServer(@Body JsonObject jsonObject);
}


