package com.lelong.kythuat.KT07;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class TabLayout_KT07 extends RecyclerView.ViewHolder implements Serializable {
    private String tc_cea03_in;
    private String tc_cea04_in;
    private String tc_cea05_in;
    private String tc_cea06_in;
    private String tc_cea07_in;
    private String tc_ceb04_in;
    private String tc_ceb05_in;

    public String getTc_cea03_in() {
        return tc_cea03_in;
    }

    public void setTc_cea03_in(String tc_cea03_in) {
        this.tc_cea03_in = tc_cea03_in;
    }

    public String getTc_cea04_in() {
        return tc_cea04_in;
    }

    public void setTc_cea04_in(String tc_cea04_in) {
        this.tc_cea04_in = tc_cea04_in;
    }

    public String getTc_cea05_in() {
        return tc_cea05_in;
    }

    public void setTc_cea05_in(String tc_cea05_in) {
        this.tc_cea05_in = tc_cea05_in;
    }

    public String getTc_cea06_in() {
        return tc_cea06_in;
    }

    public void setTc_cea06_in(String tc_cea06_in) {
        this.tc_cea06_in = tc_cea06_in;
    }

    public String getTc_cea07_in() {
        return tc_cea07_in;
    }

    public void setTc_cea07_in(String tc_cea07_in) {
        this.tc_cea07_in = tc_cea07_in;
    }


    public String getTc_ceb04_in() {
        return tc_ceb04_in;
    }

    public void setTc_ceb04_in(String tc_ceb04_in) {
        this.tc_ceb04_in = tc_ceb04_in;
    }

    public String getTc_ceb05_in() {
        return tc_ceb05_in;
    }

    public void setTc_ceb05_in(String tc_ceb05_in) {
        this.tc_ceb05_in = tc_ceb05_in;
    }

    public TabLayout_KT07(@NonNull View itemView, String tc_cea03_in, String tc_cea04_in, String tc_cea05_in, String tc_cea06_in, String tc_cea07_in, String tc_ceb04_in, String tc_ceb05_in) {
        super(itemView);

        this.tc_cea03_in = tc_cea03_in;
        this.tc_cea04_in = tc_cea04_in;
        this.tc_cea05_in = tc_cea05_in;
        this.tc_cea06_in = tc_cea06_in;
        this.tc_cea07_in = tc_cea07_in;
        this.tc_ceb04_in = tc_ceb04_in;
        this.tc_ceb05_in = tc_ceb05_in;
    }
}
