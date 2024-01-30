package com.lelong.kythuat.KT02;

import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lelong.kythuat.KT01.CursorToJsonConverter;
import com.lelong.kythuat.KT02.KT02_DB;
import com.lelong.kythuat.KT02.KT02_Ketchuyen_Dialog;
import com.lelong.kythuat.KT02.Retrofit2.DataClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KT02_Transfer {
    private KT02_DB db = null;
    private KT02_KetchuyenPhoto ketChuyenPhoto = null;
    //MT01_Interface apiService;
    private Context context;
    private KT02_Ketchuyen_Dialog ketchuyenDialog;
    private String input_date,input_department,input_tenxe;
    DataClient apiService;

    public KT02_Transfer(Context context, KT02_Ketchuyen_Dialog ketchuyenDialog,String input_date, String input_department,String input_tenxe) {
        this.context = context;
        this.ketchuyenDialog = ketchuyenDialog;
        this.input_department = input_department;
        this.input_date = input_date;
        this.input_tenxe = input_tenxe;

        db = new KT02_DB(context);
        db.open();

        Gson gson = new GsonBuilder().create();

        // Tạo một OkHttpClient với thời gian timeout mới
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(20, TimeUnit.SECONDS) // Đặt thời gian timeout ở đây, ví dụ: 20 giây
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.40.20/PHP/TechAPP/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = retrofit.create(DataClient.class);

        Call_transfer();
    }

    private void Call_transfer() {
        //Khi sử dụng Retrofit cần sử dụng thư viện Json của Google , không nên dùng thư viện Json của Java
        Cursor get_tc_fac = db.getAll_tc_fac_new(input_date,input_department,input_tenxe);
        Cursor get_tc_far = db.getAll_tc_far(input_date,input_department,input_tenxe);
        if (get_tc_fac.getCount() > 0) {

            JsonArray jarray_tc_fac = CursorToJsonConverter.cursorToJson(get_tc_fac);
            JsonArray jarray_tc_far = CursorToJsonConverter.cursorToJson(get_tc_far);
            JsonObject jsonObject = new JsonObject();
            jsonObject.add("jarr_tc_fac", jarray_tc_fac);
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
                            db.call_upd_tc_facpost(get_tc_fac);
                            if(get_tc_far.getCount() > 0){
                                //Hàm lấy ảnh và gửi ảnh
                                ketChuyenPhoto = new KT02_KetchuyenPhoto(context, get_tc_far, ketchuyenDialog);
                            }
                            else {
                                ketchuyenDialog.setProgressBar(get_tc_fac.getCount());
                                ketchuyenDialog.updateProgressBar(get_tc_fac.getCount());
                                ketchuyenDialog.setStatus("2");
                                ketchuyenDialog.setEnableBtn(false,true);
                            }

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
            if(get_tc_far.getCount() > 0){
                ketChuyenPhoto = new KT02_KetchuyenPhoto(context, get_tc_far, ketchuyenDialog);
            }else {
                ketchuyenDialog.setStatus("Không có dữ liệu cập nhật");
            }
        }
    }
}
