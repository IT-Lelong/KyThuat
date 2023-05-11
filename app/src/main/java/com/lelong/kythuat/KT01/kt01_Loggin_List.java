package com.lelong.kythuat.KT01;

public class kt01_Loggin_List {
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

    public kt01_Loggin_List(String IDbp, String tenbp) {
        this.IDbp = IDbp;
        this.Tenbp = tenbp;
    }
}
