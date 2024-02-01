package com.lelong.kythuat.CheckConnects;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lelong.kythuat.R;

// NetworkStatusView.java
public class NetworkStatusView extends LinearLayout {
    static TextView  textSpeed;
    static TextView textPing;
    static TextView textStability;

    public NetworkStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NetworkStatusView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.network_status_layout, this);
        textSpeed = findViewById(R.id.text_speed);
        textPing = findViewById(R.id.text_ping);
        textStability = findViewById(R.id.text_stability);
    }

    public static void setNetworkStatus(String speed, String ping, String stability) {
        textSpeed.setText("Tốc độ Kết nối: " + speed);
        textPing.setText("Ping: " + ping);
        textStability.setText("Độ ổn định: " + stability);
    }
}

