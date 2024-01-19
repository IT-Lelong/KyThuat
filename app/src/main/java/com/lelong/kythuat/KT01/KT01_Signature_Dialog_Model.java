package com.lelong.kythuat.KT01;

public class KT01_Signature_Dialog_Model {
    private String g_manv;
    private String g_sogio;
    private String g_ghichu;

    public String getG_manv() {
        return g_manv;
    }

    public String getG_sogio() {
        return g_sogio;
    }

    public String getG_ghichu() {
        return g_ghichu;
    }

    public void setG_manv(String g_tc_ceb04) {
        this.g_manv = g_manv;
    }
    public void setG_ghichu(String g_tc_ceb04) {
        this.g_ghichu = g_ghichu;
    }

    public void setG_sogio(String g_tc_ceb04_old) {
        this.g_sogio = g_sogio;
    }

    public KT01_Signature_Dialog_Model(String g_manv, String g_sogio, String g_ghichu) {
        this.g_manv = g_manv;
        this.g_sogio = g_sogio;
        this.g_ghichu = g_ghichu;
    }
}
