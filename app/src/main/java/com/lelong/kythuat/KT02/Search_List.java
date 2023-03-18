package com.lelong.kythuat.KT02;

public class Search_List {
    public String getSomay() {
        return somay;
    }

    public void setSomay(String somay) {
        this.somay = somay;
    }

    public String getMabp() {
        return mabp;
    }

    public void setMabp(String mabp) {
        this.mabp = mabp;
    }

    public String getTenbp() {
        return tenbp;
    }

    public void setTenbp(String tenbp) {
        this.tenbp = tenbp;
    }

    public Search_List(String somay, String mabp, String tenbp) {
        this.somay = somay;
        this.mabp = mabp;
        this.tenbp = tenbp;
    }

    private String somay;
    private String mabp;
    private String tenbp;
}
