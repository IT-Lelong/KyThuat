package com.lelong.kythuat.CheckConnects;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

public class NetworkUtils_ConnectSpeed {

    public static String getInternetSpeed(Context context) {
        String speed = "Unknown";

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());
                if (nc != null) {
                    if (nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        speed = wifiInfo.getLinkSpeed() + " Mbps";
                    } else if (nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        // Xử lý cho kết nối dữ liệu di động (3G, 4G, ...)
                        speed = "Depends on cellular network";
                    }
                }
            } else {
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (activeNetwork != null && activeNetwork.isConnected()) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        speed = wifiInfo.getLinkSpeed() + " Mbps";
                    } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        // Xử lý cho kết nối dữ liệu di động (3G, 4G, ...)
                        speed = "Depends on cellular network";
                    }
                }
            }
        }

        return speed;
    }

}
