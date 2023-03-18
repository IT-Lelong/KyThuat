package com.lelong.kythuat.KT02;

public class Loggin_List {
    private String IDbp;
    private String Tenbp;

    public String getIDbp() {
        return IDbp;
    }

    public void setIDbp(String IDbp) {
        this.IDbp = IDbp;
    }

    public String getTenbp() {
        return Tenbp;
    }

    public void setTenbp(String tenbp) {
        Tenbp = tenbp;
    }

    public Loggin_List(String IDbp, String tenbp) {
        this.IDbp = IDbp;
        this.Tenbp = tenbp;
    }

}
