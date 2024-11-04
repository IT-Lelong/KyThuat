package com.lelong.kythuat.KT04;

import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lelong.kythuat.KT01.CursorToJsonConverter;
import com.lelong.kythuat.KT04.KT04_DB;
import com.lelong.kythuat.KT04.KT04_DataClient;
import com.lelong.kythuat.KT04.KT04_KetChuyenPhoto;
import com.lelong.kythuat.KT04.KT04_KetChuyen_Dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KT04_Transfer {
    private KT04_DB db = null;
    private KT04_KetChuyenPhoto KT04_ketChuyenPhoto = null;
    //MT01_Interface apiService;
    private Context context;
    private KT04_KetChuyen_Dialog KT04_ketchuyenDialog;
    private String input_date, input_department,input_ca;
    KT04_DataClient apiService;

    public KT04_Transfer(Context context, KT04_KetChuyen_Dialog KT04_ketchuyenDialog, String input_date, String input_department, String input_ca) {
        this.context = context;
        this.KT04_ketchuyenDialog = KT04_ketchuyenDialog;
        this.input_department = input_department;
        this.input_date = input_date;
        this.input_ca = input_ca;

        db = new KT04_DB(context);
        db.open();

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.40.20/PHP/TechAPP/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(KT04_DataClient.class);

        Call_transfer();
    }

    private void Call_transfer() {
        //Khi sử dụng Retrofit cần sử dụng thư viện Json của Google , không nên dùng thư viện Json của Java
        Cursor get_tc_faa = db.getAll_tc_faa_new(input_date, input_department, input_ca);
        Cursor get_tc_faa_03 = db.getAll_tc_faa_03_new(input_date, input_department, input_ca);
        Cursor get_tc_faa_04 = db.getAll_tc_faa_04_new(input_date, input_department, input_ca);
        Cursor get_tc_far = db.getAll_tc_far(input_date, input_department, input_ca);
        if (get_tc_faa.getCount() > 0) {

            JsonArray jarray_tc_faa = CursorToJsonConverter.cursorToJson(get_tc_faa);
            JsonArray jarray_tc_faa_03 = CursorToJsonConverter.cursorToJson(get_tc_faa_03);
            JsonArray jarray_tc_faa_04 = CursorToJsonConverter.cursorToJson(get_tc_faa_04);
            JsonArray jarray_tc_far = CursorToJsonConverter.cursorToJson(get_tc_far);

            JsonObject jsonObject = new JsonObject();
            jsonObject.add("ujson_hm01", jarray_tc_faa);
            jsonObject.add("ujson_hm03", jarray_tc_faa_03);
            jsonObject.add("ujson_hm04", jarray_tc_faa_04);
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
                            db.call_upd_tc_faapost3(get_tc_faa);
                            db.call_upd_tc_faapost4(get_tc_faa);
                            //Hàm lấy ảnh và gửi ảnh
                            KT04_ketChuyenPhoto = new KT04_KetChuyenPhoto(context, get_tc_far, KT04_ketchuyenDialog);
                        } else {
                            KT04_ketchuyenDialog.setStatus(message);
                        }
                    } else {
                        // Xử lý lỗi
                        String s = String.valueOf(response.body());
                        KT04_ketchuyenDialog.setStatus(s);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Xử lý khi có lỗi xảy ra trong quá trình gửi dữ liệu
                    String s = String.valueOf(t.toString());
                    KT04_ketchuyenDialog.setStatus(s);
                }
            });

        } else {
            if (get_tc_far.getCount() > 0) {
                KT04_ketChuyenPhoto = new KT04_KetChuyenPhoto(context, get_tc_far, KT04_ketchuyenDialog);
            } else {
                KT04_ketchuyenDialog.setStatus("Không có dữ liệu cập nhật");
            }
        }
    }
}
