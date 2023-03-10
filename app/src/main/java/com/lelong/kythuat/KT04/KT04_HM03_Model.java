package com.lelong.kythuat.KT04;

import java.io.Serializable;

public class KT04_HM03_Model implements Serializable {
    private String g_stt;
    private String g_noidungHM_TH;
    private String g_noidungHM_TV;
    private String g_maChiTiet;
    private String g_daydo;
    private String kt04_03_001;
    private String kt04_03_002;
    private String kt04_03_003;
    private String kt04_03_004;
    private String kt04_03_005;

    public String getG_maChiTiet() {
        return g_maChiTiet;
    }

    public String getKt04_03_001() {
        return kt04_03_001;
    }

    public void setKt04_03_001(String kt04_03_001) {
        this.kt04_03_001 = kt04_03_001;
    }

    public String getKt04_03_002() {
        return kt04_03_002;
    }

    public void setKt04_03_002(String kt04_03_002) {this.kt04_03_002 = kt04_03_002;}

    public String getKt04_03_003() {
        return kt04_03_003;
    }

    public void setKt04_03_003(String kt04_03_003) {
        this.kt04_03_003 = kt04_03_003;
    }

    public String getKt04_03_004() {
        return kt04_03_004;
    }

    public void setKt04_03_004(String kt04_03_004) {
        this.kt04_03_004 = kt04_03_004;
    }

    public String getKt04_03_005() {
        return kt04_03_005;
    }

    public void setKt04_03_005(String kt04_03_005) {
        this.kt04_03_005 = kt04_03_005;
    }

    public String getG_noidungHM_TH() {
        return g_noidungHM_TH;
    }

    public String getG_noidungHM_TV() {
        return g_noidungHM_TV;
    }

    public void setG_noidungHM_TV(String g_noidungHM_TV) {
        this.g_noidungHM_TV = g_noidungHM_TV;
    }

    public void setG_daydo(String g_daydo) {this.g_daydo = g_daydo;}

    public String getG_daydo() {
        return g_daydo;
    }

    public String getG_stt() {
        return g_stt;
    }

    public void setG_stt(String g_stt) {
        this.g_stt = g_stt;
    }

    public void setG_maChiTiet(String g_maChiTiet) {
        this.g_maChiTiet = g_maChiTiet;
    }

    public KT04_HM03_Model(String g_stt, String g_maChiTiet, String g_noidungHM_TV,String g_daydo,
                           String kt04_03_002, String kt04_03_003, String kt04_03_004, String kt04_03_005) {
        this.kt04_03_001 = kt04_03_001;
        this.kt04_03_002 = kt04_03_002;
        this.kt04_03_003 = kt04_03_003;
        this.kt04_03_004 = kt04_03_004;
        this.kt04_03_005 = kt04_03_005;
        this.g_stt = g_stt;
        this.g_maChiTiet = g_maChiTiet;
        this.g_noidungHM_TH = g_noidungHM_TH;
        this.g_noidungHM_TV = g_noidungHM_TV;
        this.g_daydo= g_daydo;
    }
}
