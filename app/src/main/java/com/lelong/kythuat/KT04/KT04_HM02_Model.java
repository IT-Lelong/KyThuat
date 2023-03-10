package com.lelong.kythuat.KT04;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class KT04_HM02_Model implements Serializable {
    private String g_stt;
    private String g_maChiTiet;
    private String g_noidungHM_TH;
    private String g_noidungHM_TV;
    private String g_hangsanxuat;
    private boolean g_tot;
    private boolean g_kotot;
    private String g_ghichu;

    public String getG_stt() {
        return g_stt;
    }

    public String getG_maChiTiet() {
        return g_maChiTiet;
    }

    public String getG_noidungHM_TH() {

        return g_noidungHM_TH;
    }

    public String getG_noidungHM_TV() {return g_noidungHM_TV;}

    public String getG_hangsanxuat() {

        return g_hangsanxuat;
    }

    public boolean isG_tot() {return g_tot;}

    public boolean isG_kotot() {return g_kotot;}

    public String getG_ghichu() {return g_ghichu;}

    public void setG_stt(String g_stt) {
        this.g_stt = g_stt;
    }

    public void setG_noidungHM_TH(String g_noidungHM_TH) {
        this.g_noidungHM_TH = g_noidungHM_TH;
    }

    public void setG_tot(boolean g_tot) {
        this.g_tot = g_tot;
    }

    public void setG_kotot(boolean g_kotot) {
        this.g_kotot = g_kotot;
    }

    public void setG_ghichu(String g_ghichu) {this.g_ghichu = g_ghichu;}

    public void setG_maChiTiet(String g_maChiTiet) {
        this.g_maChiTiet = g_maChiTiet;
    }

    public void setG_noidungHM_TV(String g_noidungHM_TV) {
        this.g_noidungHM_TV = g_noidungHM_TV;
    }

    public void setG_hangsanxuat(String g_tc_hangsanxuat) {this.g_hangsanxuat = g_hangsanxuat;}

    public KT04_HM02_Model(String g_stt, String g_maChiTiet, String g_noidungHM_TV,String g_hangsanxuat,
                           boolean g_tot, boolean g_kotot, String g_ghichu) {
        this.g_stt = g_stt;
        this.g_maChiTiet = g_maChiTiet;
        this.g_noidungHM_TH = g_noidungHM_TH;
        this.g_noidungHM_TV = g_noidungHM_TV;
        this.g_hangsanxuat= g_hangsanxuat;
        this.g_tot = g_tot;
        this.g_kotot = g_kotot;
        this.g_ghichu = g_ghichu;
    }
}

