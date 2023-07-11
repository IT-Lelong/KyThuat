package com.lelong.kythuat.KT01;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class TabLayout extends RecyclerView.ViewHolder implements Serializable {

    private boolean checkBox;
    private boolean checkBox1;
    private boolean checkBox2;
    private boolean checkBox3;
    private boolean checkBox4;
    private boolean checkBox5;
    private String ghichu;
    // private String  tc_fac001;
    //private String tc_fac002;
    private String ngay;
    private String bophan;
    private String tc_fac003;
    private String tc_fac004;
    private  String dkcamera;
    private  String tobp;
    // private String tc_fac005;
    private String tc_fac006;
    //private String tc_fac007;
    //private String tc_fac008;
    //private String tc_fac009;
    //private String tc_fac010;
    //private String tc_fac011;
    // private String tc_fac012;

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getBophan() {
        return bophan;
    }

    public void setBophan(String bophan) {
        this.bophan = bophan;
    }

    public boolean getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public boolean getCheckBox1() {
        return checkBox1;
    }

    public void setCheckBox1(boolean checkBox1) {
        this.checkBox1 = checkBox1;
    }

    public boolean getCheckBox2() {
        return checkBox2;
    }

    public void setCheckBox2(boolean checkBox2) {
        this.checkBox2 = checkBox2;
    }

    public boolean getCheckBox3() {
        return checkBox3;
    }

    public void setCheckBox3(boolean checkBox3) {
        this.checkBox3 = checkBox3;
    }

    public boolean getCheckBox4() {
        return checkBox4;
    }

    public void setCheckBox4(boolean checkBox4) {
        this.checkBox4 = checkBox4;
    }

    public boolean getCheckBox5() {
        return checkBox5;
    }

    public void setCheckBox5(boolean checkBox5) {
        this.checkBox5 = checkBox5;
    }

    public String getTobp() {
        return tobp;
    }

    public void setTobp(String tobp) {
        this.tobp = tobp;
    }

    public TabLayout(@NonNull View itemView, String ngay, String bophan, String tc_fac003, String tc_fac004, String tc_fac006, String ghichu, boolean checkBox, boolean checkBox1, boolean checkBox2, boolean checkBox3, boolean checkBox4, boolean checkBox5, String dkcamera, String tobp) {
        super(itemView);
        this.ngay = ngay;
        this.bophan = bophan;
        this.tc_fac003 = tc_fac003;
        this.tc_fac004 = tc_fac004;
        this.tc_fac006 = tc_fac006;
        //this.tc_fac007 = tc_fac007;
        this.dkcamera =dkcamera;
        this.tobp =tobp;
        this.ghichu = ghichu;
        this.checkBox = checkBox;
        this.checkBox1 = checkBox1;
        this.checkBox2 = checkBox2;
        this.checkBox3 = checkBox3;
        this.checkBox4 = checkBox4;
        this.checkBox5 = checkBox5;
    }

    public String getTc_dkcamera() {
        return dkcamera;
    }

    public void setTc_dkcamera(String tc_fac003) {
        this.dkcamera = dkcamera;
    }

    public String getTc_fac003() {
        return tc_fac003;
    }

    public void setTc_fac003(String tc_fac003) {
        this.tc_fac003 = tc_fac003;
    }


    public String getTc_fac004() {
        return tc_fac004;
    }

    public void setTc_fac004(String tc_fac004) {
        this.tc_fac004 = tc_fac004;
    }

    /*public String getTc_fac005() {
        return tc_fac005;
    }

    public void setTc_fac005(String tc_fac005) {
        this.tc_fac005 = tc_fac005;
    }*/

    public String getTc_fac006() {
        return tc_fac006;
    }

    public void setTc_fac006(String tc_fac006) {
        this.tc_fac006 = tc_fac006;
    }

    /*public String getTc_fac007() {
        return tc_fac007;
    }

    public void getTc_fac007(String tc_fac007) {
        this.tc_fac007 = tc_fac007;
    }*/

    public String getTc_ghichu() {
        return ghichu;
    }

    public void getTc_ghichu(String ghichu) {
        this.ghichu = ghichu;
    }

    /*public String getTc_fac008() {
        return tc_fac008;
    }

    public void setTc_fac008(String tc_fac008) {
        this.tc_fac008 = tc_fac008;
    }

    public String getTc_fac009() {
        return tc_fac009;
    }

    public void setTc_fac009(String tc_fac009) {
        this.tc_fac009 = tc_fac009;
    }

    public String getTc_fac010() {
        return tc_fac010;
    }

    public void setTc_fac010(String tc_fac010) {
        this.tc_fac010 = tc_fac010;
    }

    public String getTc_fac011() {
        return tc_fac011;
    }

    public void setTc_fac011(String tc_fac011) {
        this.tc_fac011 = tc_fac011;
    }

    public String getTc_fac012() {
        return tc_fac012;
    }

    public void setTc_fac012(String tc_fac012) {
        this.tc_fac012 = tc_fac012;
    }
*/


}
