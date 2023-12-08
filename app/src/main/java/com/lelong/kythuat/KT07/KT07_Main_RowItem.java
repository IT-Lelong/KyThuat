package com.lelong.kythuat.KT07;

public class KT07_Main_RowItem {
    private final String g_TC_CEA01;
    private final String g_tc_cea03;
    private final String g_tc_cea04;
    private final String g_tc_cea05;
    private final String g_tc_cea06;
    private final String g_tc_cea08;
    private String g_tc_ceb04_old;
    private final String G_TC_CEBDATE_CEB06;
    private String g_tc_ceb04;

    public String getG_TC_CEBDATE_CEB06() {
        return G_TC_CEBDATE_CEB06;
    }
    public String getG_tc_cea03() {
        return g_tc_cea03;
    }

    public String getG_tc_cea04() {
        return g_tc_cea04;
    }

    public String getG_tc_cea05() {
        return g_tc_cea05;
    }

    public String getG_tc_cea06() {
        return g_tc_cea06;
    }

    public String getG_TC_CEA01() {
        return g_TC_CEA01;
    }

    public String getG_tc_ceb04() {
        return g_tc_ceb04;
    }

    public void setG_tc_ceb04(String g_tc_ceb04) {
        this.g_tc_ceb04 = g_tc_ceb04;
    }

    public String getG_tc_cea08() {
        return g_tc_cea08;
    }

    public String getG_tc_ceb04_old() {
        return g_tc_ceb04_old;
    }

    public void setG_tc_ceb04_old(String g_tc_ceb04_old) {
        this.g_tc_ceb04_old = g_tc_ceb04_old;
    }

    public KT07_Main_RowItem(String g_TC_CEA01, String g_tc_cea03, String g_tc_cea04, String g_tc_cea05, String g_tc_cea06, String g_tc_cea08, String g_tc_ceb04_old, String g_tc_ceb04, String g_TC_CEBDATE_CEB06) {
        this.g_TC_CEA01 = g_TC_CEA01;
        this.g_tc_cea03 = g_tc_cea03;
        this.g_tc_cea04 = g_tc_cea04;
        this.g_tc_cea05 = g_tc_cea05;
        this.g_tc_cea06 = g_tc_cea06;
        this.g_tc_cea08 = g_tc_cea08;
        this.g_tc_ceb04_old = g_tc_ceb04_old;
        this.g_tc_ceb04 = g_tc_ceb04;
        this.G_TC_CEBDATE_CEB06 = g_TC_CEBDATE_CEB06  ;
    }
}
