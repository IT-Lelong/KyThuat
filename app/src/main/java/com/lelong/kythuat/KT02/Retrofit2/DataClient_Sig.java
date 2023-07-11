package com.lelong.kythuat.KT02.Retrofit2;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DataClient_Sig {
    @Multipart
    @POST("UploadHinhAnhSig.php")
    Call<String> UploadPhot(@Part MultipartBody.Part body);
}
