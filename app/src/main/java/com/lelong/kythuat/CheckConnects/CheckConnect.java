package com.lelong.kythuat.CheckConnects;

import android.content.Context;
import android.os.AsyncTask;

import com.lelong.kythuat.Menu;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class CheckConnect extends AsyncTask<Void, Void, String[]> {
    private Context ctx;
    private ConnectionCallback callback;

    public CheckConnect(Context applicationContext, ConnectionCallback callback) {
        this.ctx = applicationContext;
        this.callback = callback;
    }

    @Override
    protected String[] doInBackground(Void... voids) {
        String speed = NetworkUtils_ConnectSpeed.getInternetSpeed(ctx);
        String ping = getPing();
        String stability = NetworkUtils_Stability.checkStability("172.16.40.20"); //IP or WEB URL
        return new String[]{speed, ping, stability};
    }

    @Override
    protected void onPostExecute(String[] result) {
        // Pass connection result to the callback
        callback.onConnectionResult(result[0], result[1], result[2]);
    }

    private String getPing() {
        try {
            InetAddress inet = InetAddress.getByName("172.16.40.20");
            long startTime = System.currentTimeMillis();
            boolean isReachable = inet.isReachable(5000);
            long endTime = System.currentTimeMillis();
            if (isReachable) {
                return (endTime - startTime) + " ms";
            } else {
                return "Không thể kết nối";
            }
        } catch (UnknownHostException e) {
            return "Lỗi DNS";
        } catch (IOException e) {
            return "Lỗi I/O";
        }
    }

}
