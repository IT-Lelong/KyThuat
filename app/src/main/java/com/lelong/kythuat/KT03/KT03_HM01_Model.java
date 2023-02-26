package com.lelong.kythuat.KT03;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class KT03_HM01_Model implements Serializable {
    private String g_stt;
    private String g_maChiTiet;
    private String g_noidungHM_TH;
    private String g_noidungHM_TV;
    private boolean g_tot_ca1;
    private boolean g_tot_ca2;
    private boolean g_kotot_ca1;
    private boolean g_kotot_ca2;
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

    public String getG_noidungHM_TV() {
        return g_noidungHM_TV;
    }

    public boolean isG_tot_ca1() {
        return g_tot_ca1;
    }

    public boolean isG_tot_ca2() {
        return g_tot_ca2;
    }

    public boolean isG_kotot_ca1() {
        return g_kotot_ca1;
    }

    public boolean isG_kotot_ca2() {
        return g_kotot_ca2;
    }

    public String getG_ghichu() {
        return g_ghichu;
    }

    public void setG_stt(String g_stt) {
        this.g_stt = g_stt;
    }

    public void setG_noidungHM_TH(String g_noidungHM_TH) {
        this.g_noidungHM_TH = g_noidungHM_TH;
    }

    public void setG_tot_ca1(boolean g_tot_ca1) {
        this.g_tot_ca1 = g_tot_ca1;
    }

    public void setG_tot_ca2(boolean g_tot_ca2) {
        this.g_tot_ca2 = g_tot_ca2;
    }

    public void setG_kotot_ca1(boolean g_kotot_ca1) {
        this.g_kotot_ca1 = g_kotot_ca1;
    }

    public void setG_kotot_ca2(boolean g_kotot_ca2) {
        this.g_kotot_ca2 = g_kotot_ca2;
    }

    public void setG_ghichu(String g_ghichu) {
        this.g_ghichu = g_ghichu;
    }


    public void setG_maChiTiet(String g_maChiTiet) {
        this.g_maChiTiet = g_maChiTiet;
    }

    public void setG_noidungHM_TV(String g_noidungHM_TV) {
        this.g_noidungHM_TV = g_noidungHM_TV;
    }

    public KT03_HM01_Model(String g_stt, String g_maChiTiet, String g_noidungHM_TH, String g_noidungHM_TV,
                           boolean g_tot_ca1, boolean g_tot_ca2, boolean g_kotot_ca1, boolean g_kotot_ca2,
                           String g_ghichu) {
        this.g_stt = g_stt;
        this.g_maChiTiet = g_maChiTiet;
        this.g_noidungHM_TH = g_noidungHM_TH;
        this.g_noidungHM_TV = g_noidungHM_TV;
        this.g_tot_ca1 = g_tot_ca1;
        this.g_tot_ca2 = g_tot_ca2;
        this.g_kotot_ca1 = g_kotot_ca1;
        this.g_kotot_ca2 = g_kotot_ca2;
        this.g_ghichu = g_ghichu;
    }
}
