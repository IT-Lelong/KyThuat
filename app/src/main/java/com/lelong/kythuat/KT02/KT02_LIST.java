package com.lelong.kythuat.KT02;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class KT02_LIST extends RecyclerView.ViewHolder implements Serializable {

    private String tc_fac009;
    private String tc_fac003;
    private String tc_fac006;

    private String tc_fac004;
    private Boolean checkBox1;

    private Boolean checkBox2;
    private Boolean checkBox3;
    private Boolean checkBox4;
    private Boolean checkBox5;

    public String getTc_fac009() {
        return tc_fac009;
    }

    public void setTc_fac009(String tc_fac009) {
        this.tc_fac009 = tc_fac009;
    }

    private Boolean checkBox6;
    private String user;
    private String ngay;

    private String somay;
    //private String tc_fac009;


    /*public String getCheckBox1() {
        return checkBox1;
    }

    public void setCheckBox1(String checkBox1) {
        this.checkBox1 = checkBox1;
    }*/

    public String getTc_fac004() {
        return tc_fac004;
    }

    public void setTc_fac004(String tc_fac004) {
        this.tc_fac004 = tc_fac004;
    }

    public Boolean getCheckBox2() {
        return checkBox2;
    }

    public void setCheckBox2(Boolean checkBox2) {
        this.checkBox2 = checkBox2;
    }

    public Boolean getCheckBox3() {
        return checkBox3;
    }

    public void setCheckBox3(Boolean checkBox3) {
        this.checkBox3 = checkBox3;
    }

    public Boolean getCheckBox4() {
        return checkBox4;
    }

    public void setCheckBox4(Boolean checkBox4) {
        this.checkBox4 = checkBox4;
    }

    public Boolean getCheckBox5() {
        return checkBox5;
    }

    public void setCheckBox5(Boolean checkBox5) {
        this.checkBox5 = checkBox5;
    }

    public Boolean getCheckBox6() {
        return checkBox6;
    }

    public void setCheckBox6(Boolean checkBox6) {
        this.checkBox6 = checkBox6;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getSomay() {
        return somay;
    }

    public void setSomay(String somay) {
        this.somay = somay;
    }

    public Boolean getCheckBox1() {
        return checkBox1;
    }

    public void setCheckBox1(Boolean checkBox1) {
        this.checkBox1 = checkBox1;
    }

    public KT02_LIST(@NonNull View itemView, String tc_fac003, String tc_fac006, String tc_fac004, Boolean checkBox1, Boolean checkBox2,
                             Boolean checkBox3, Boolean checkBox4, Boolean checkBox5,Boolean checkBox6, String user, String ngay,
                             String tc_fac009, String somay) {
        super(itemView);
        this.tc_fac003 = tc_fac003;
        this.tc_fac006 = tc_fac006;
        this.tc_fac004 = tc_fac004;
        this.checkBox1 = checkBox1;
        this.checkBox2= checkBox2;
        this.checkBox3 = checkBox3;
        this.checkBox4 = checkBox4;
        this.checkBox5 = checkBox5;
        this.checkBox6 = checkBox6;
        this.user = user;
        this.ngay = ngay;
        this.tc_fac009 = tc_fac009;
        this.somay = somay;
    }

    public String getTc_fac003() {
        return tc_fac003;
    }

    public void setTc_fac003(String tc_fac003) {
        this.tc_fac003 = tc_fac003;
    }

    public String getTc_fac006() {
        return tc_fac006;
    }

    public void setTc_fac006(String tc_fac006) {
        this.tc_fac006 = tc_fac006;
    }
}
