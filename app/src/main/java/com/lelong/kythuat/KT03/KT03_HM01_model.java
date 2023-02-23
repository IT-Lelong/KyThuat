package com.lelong.kythuat.KT03;

import java.io.Serializable;

public class KT03_HM01_model implements Serializable {
    private int g_stt;
    private String g_noidungHM;
    private boolean g_tot_ca1;
    private boolean g_tot_ca2;
    private boolean g_kotot_ca1;
    private boolean g_kotot_ca2;
    private String g_ghichu;

    public int getG_stt() {
        return g_stt;
    }

    public String getG_noidungHM() {
        return g_noidungHM;
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

    public void setG_stt(int g_stt) {
        this.g_stt = g_stt;
    }

    public void setG_noidungHM(String g_noidungHM) {
        this.g_noidungHM = g_noidungHM;
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

    public KT03_HM01_model(int g_stt, String g_noidungHM, boolean g_tot_ca1, boolean g_tot_ca2, boolean g_kotot_ca1, boolean g_kotot_ca2, String g_ghichu) {
        this.g_stt = g_stt;
        this.g_noidungHM = g_noidungHM;
        this.g_tot_ca1 = g_tot_ca1;
        this.g_tot_ca2 = g_tot_ca2;
        this.g_kotot_ca1 = g_kotot_ca1;
        this.g_kotot_ca2 = g_kotot_ca2;
        this.g_ghichu = g_ghichu;
    }
}
