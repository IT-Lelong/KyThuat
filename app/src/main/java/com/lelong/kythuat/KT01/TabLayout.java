package com.lelong.kythuat.KT01;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class TabLayout extends RecyclerView.ViewHolder implements Serializable {

    private boolean checkBox;
    private String ghichu;
    // private String  tc_fac001;
    //private String tc_fac002;
    private String tc_fac003;
    private String tc_fac004;
    // private String tc_fac005;
    private String tc_fac006;
    private String tc_fac007;
    //private String tc_fac008;
    //private String tc_fac009;
    //private String tc_fac010;
    //private String tc_fac011;
    // private String tc_fac012;

    public TabLayout(@NonNull View itemView, String tc_fac003, String tc_fac004, String tc_fac006, String tc_fac007, String ghichu, boolean checkBox) {
        super(itemView);
        this.tc_fac003 = tc_fac003;
        this.tc_fac004 = tc_fac004;
        this.tc_fac006 = tc_fac006;
        this.tc_fac007 = tc_fac007;
        this.ghichu = ghichu;
        this.checkBox = checkBox;
    }

    //public TabLayout(String tc_fac001, String tc_fac002, String tc_fac003, String tc_fac004, String tc_fac005, String tc_fac006, String tc_fac007, String tc_fac008, String tc_fac009, String tc_fac010, String tc_fac011, String tc_fac012) {
    /*public TabLayout(String tc_fac003,  String tc_fac006, String tc_fac007) {
        super(null);
        //this.tc_fac001 = tc_fac001;
        //this.tc_fac002 = tc_fac002;
        this.tc_fac003 = tc_fac003;
        //this.tc_fac004 = tc_fac004;
        //this.tc_fac005 = tc_fac005;
        this.tc_fac006 = tc_fac006;
        this.tc_fac007 = tc_fac007;
        //this.tc_fac008 = tc_fac008;
        //this.tc_fac009 = tc_fac009;
        //this.tc_fac010 = tc_fac010;
        //this.tc_fac011 = tc_fac011;
        //this.tc_fac012 = tc_fac012;
    }*/


   /* public String getTc_fac001() {
        return tc_fac001;
    }

    public void setTc_fac001(String tc_fac001) {
        this.tc_fac001 = tc_fac001;
    }

    public String getTc_fac002() {
        return tc_fac002;
    }

    public void setTc_fac002(String tc_fac002) {
        this.tc_fac002 = tc_fac002;
    }*/

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

    public String getTc_fac007() {
        return tc_fac007;
    }

    public void getTc_fac007(String tc_fac007) {
        this.tc_fac007 = tc_fac007;
    }

    public String getTc_ghichu() {
        return ghichu;
    }

    public void getTc_ghichu(String ghichu) {
        this.ghichu = ghichu;
    }
    public boolean getTc_checkbox() {
        if (checkBox) {
            return true;
        }
        else{
            return false;
        }

    }

    public void getTc_checkbox( boolean checkBox) {
        this.checkBox = checkBox;
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
