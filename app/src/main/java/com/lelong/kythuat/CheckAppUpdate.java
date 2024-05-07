package com.lelong.kythuat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckAppUpdate {
    private Context mContext;

    public CheckAppUpdate(Context context) {
        mContext = context;
    }

    public void checkVersion() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.40.20/" + Constant_Class.server + "/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiService = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiService.checkAppVersion(Constant_Class.UpdateName);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject jsonObject = response.body();
                    int newVerCode = jsonObject.get("QRG003").getAsInt();
                    String newVerName = jsonObject.get("QRG005").getAsString();
                    int currentVerCode = getVerCode(mContext);
                    String currentVerName = getVerName(mContext);
                    if (newVerCode > currentVerCode || (newVerCode == currentVerCode && !newVerName.equals(currentVerName))) {
                        showUpdateDialog(newVerName, newVerCode);
                    } else {
                        Toast.makeText(mContext, R.string.ver_no_update, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(mContext, R.string.ver_check_fail, Toast.LENGTH_SHORT).show();
                Log.e("CheckAppUpdate", "Failed to check app version", t);
            }
        });
    }

    private void showUpdateDialog(String newVerName, int newVerCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.ver_soft_update);
        String gmess = String.format(mContext.getString(R.string.ver_new_version), newVerName, newVerCode);
        builder.setMessage(gmess);
        builder.setPositiveButton(R.string.ver_update, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadAndInstallUpdate();
            }
        });
        builder.setNegativeButton(R.string.ver_cancel, null);
        builder.show();
    }

    /*private void downloadAndInstallUpdate() {
        // Show progress dialog
        // Your progress dialog code here

        new AsyncTask<Void, Void, File>() {
            @Override
            protected File doInBackground(Void... voids) {
                try {
                    URL url = new URL("http://172.16.40.20/" + Constant_Class.server + "/AndroidUpdateService/" + Constant_Class.UpdateName + ".apk");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    // Get the file length
                    int lengthOfFile = connection.getContentLength();

                    // Input stream to read file - with 8k buffer
                    InputStream input = connection.getInputStream();
                    File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                            Constant_Class.Update_serverAPK);
                    FileOutputStream output = new FileOutputStream(outputFile);

                    byte[] data = new byte[8192];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // Publishing the progress....
                        // After this onProgressUpdate will be called
                        //publishProgress((int) ((total * 100) / lengthOfFile));

                        // Write data to output stream
                        output.write(data, 0, count);
                    }

                    // Flushing output
                    output.flush();

                    // Closing streams
                    output.close();
                    input.close();

                    return outputFile;
                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                }

                return null;
            }

            @Override
            protected void onPostExecute(File file) {
                // Dismiss progress dialog
                // Your progress dialog dismiss code here

                if (file != null) {
                    // Install APK
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, R.string.ver_download_fail, Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }*/

    // Function to download and install update
    /*private void downloadAndInstallUpdate() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<File> future = executor.submit(new Callable<File>() {
            @Override
            public File call() throws Exception {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://172.16.40.20/") // Đặt base URL của service
                            .client(new OkHttpClient.Builder().build())
                            .build();

                    ApiInterface service = retrofit.create(ApiInterface.class);
                    Call<ResponseBody> call = service.downloadFile(Constant_Class.server + "/AndroidUpdateService/" + Constant_Class.UpdateName + ".apk");

                    Response<ResponseBody> response = call.execute();
                    if (response.isSuccessful()) {
                        InputStream inputStream = response.body().byteStream();
                        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), Constant_Class.Update_serverAPK);
                        FileOutputStream outputStream = new FileOutputStream(outputFile);
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();
                        return outputFile;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        executor.shutdown();

        try {
            File downloadedFile = future.get(); // Get the result from the Callable
            if (downloadedFile != null) {
                // Install APK
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(downloadedFile), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, R.string.ver_download_fail, Toast.LENGTH_SHORT).show();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    private void downloadAndInstallUpdate() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<File> future = executor.submit(new Callable<File>() {
            @Override
            public File call() throws Exception {
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://172.16.40.20/") // Đặt base URL của service
                            .client(new OkHttpClient.Builder().build())
                            .build();

                    ApiInterface service = retrofit.create(ApiInterface.class);
                    Call<ResponseBody> call = service.downloadFile(Constant_Class.server + "/AndroidUpdateService/" + Constant_Class.UpdateName + ".apk");

                    Response<ResponseBody> response = call.execute();
                    if (response.isSuccessful()) {
                        InputStream inputStream = response.body().byteStream();
                        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), Constant_Class.Update_serverAPK);
                        FileOutputStream outputStream = new FileOutputStream(outputFile);
                        byte[] buffer = new byte[8192];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        outputStream.flush();
                        outputStream.close();
                        inputStream.close();
                        return outputFile;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        executor.shutdown();

        try {
            File downloadedFile = future.get(); // Get the result from the Callable
            if (downloadedFile != null) {
                // Install APK
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri apkUri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", downloadedFile);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            } else {
                Toast.makeText(mContext, R.string.ver_download_fail, Toast.LENGTH_SHORT).show();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            Log.e("CheckAppUpdate", "Failed to get version code", e);
        }
        return verCode;
    }

    public String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            Log.e("CheckAppUpdate", "Failed to get version name", e);
        }
        return verName;
    }
}
