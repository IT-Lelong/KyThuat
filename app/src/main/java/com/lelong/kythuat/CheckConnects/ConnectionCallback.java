package com.lelong.kythuat.CheckConnects;

public interface ConnectionCallback {
    void onConnectionResult(String speed, String ping, String stability);
}
