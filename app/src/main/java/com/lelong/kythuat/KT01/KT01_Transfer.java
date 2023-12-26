package com.lelong.kythuat.KT01;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.KT01.Retrofit2.DataClient;

import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KT01_Transfer {
    private  KT01_DB db = null;
    private KetChuyenPhoto ketChuyenPhoto = null;
    //MT01_Interface apiService;
    private Context context;
    private KetChuyen_Dialog ketchuyenDialog;
    DataClient apiService;

    public KT01_Transfer(Context context, KetChuyen_Dialog ketchuyenDialog) {
        this.context = context;
        this.ketchuyenDialog = ketchuyenDialog;


        db = new KT01_DB(context);
        db.open();

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.40.20/PHP/TechAPP/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(DataClient.class);

        Call_transfer();
    }

    private void Call_transfer() {
        //Khi sử dụng Retrofit cần sử dụng thư viện Json của Google , không nên dùng thư viện Json của Java
        Cursor get_tc_faa = db.getAll_tc_faa();
        Cursor get_tc_far = db.getAll_tc_far();
        if (get_tc_faa.getCount() > 0) {

            JsonArray jarray_tc_faa = CursorToJsonConverter.cursorToJson(get_tc_faa);
            JsonArray jarray_tc_far = CursorToJsonConverter.cursorToJson(get_tc_far);
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("jarr_tc_faa", jarray_tc_faa);
            jsonObject.add("jarr_tc_far", jarray_tc_far);

            Call<ResponseBody> call = apiService.sendDataToServer(jsonObject);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
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

                        if (status.equals("success")) {
                            db.call_upd_tc_faapost(get_tc_faa);
                            //Hàm lấy ảnh và gửi ảnh
                            ketChuyenPhoto = new KetChuyenPhoto(context, get_tc_far, ketchuyenDialog);
                        } else {
                            ketchuyenDialog.setStatus(message);
                        }
                    } else {
                        // Xử lý lỗi
                        String s = String.valueOf(response.body());
                        ketchuyenDialog.setStatus(s);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Xử lý khi có lỗi xảy ra trong quá trình gửi dữ liệu
                    String s = String.valueOf(t.toString());
                    ketchuyenDialog.setStatus(s);
                }
            });

        } else {
            ketchuyenDialog.setStatus("Không có dữ liệu cập nhật");
        }
    }

}
