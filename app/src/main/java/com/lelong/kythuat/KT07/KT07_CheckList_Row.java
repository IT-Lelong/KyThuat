package com.lelong.kythuat.KT07;

import android.os.Parcel;
import android.os.Parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class KT07_CheckList_Row implements Parcelable {
    private String g_tc_ceb01;
    private String g_tc_ceb02;
    private String g_tc_cea04;
    private String g_tc_cea05;
    private String g_tc_ceb03;
    private String g_tc_ceb06;

    public String getG_tc_ceb01() {
        return g_tc_ceb01;
    }

    public String getG_tc_ceb02() {
        return g_tc_ceb02;
    }

    public String getG_tc_cea04() {
        return g_tc_cea04;
    }

    public String getG_tc_cea05() {
        return g_tc_cea05;
    }

    public String getG_tc_ceb03() {
        return g_tc_ceb03;
    }

    public String getG_tc_ceb06() {
        return g_tc_ceb06;
    }

    public void setG_tc_ceb01(String g_tc_ceb01) {
        this.g_tc_ceb01 = g_tc_ceb01;
    }

    public void setG_tc_ceb02(String g_tc_ceb02) {
        this.g_tc_ceb02 = g_tc_ceb02;
    }

    public void setG_tc_cea04(String g_tc_cea04) {
        this.g_tc_cea04 = g_tc_cea04;
    }

    public void setG_tc_cea05(String g_tc_cea05) {
        this.g_tc_cea05 = g_tc_cea05;
    }

    public void setG_tc_ceb06(String g_tc_ceb06) {
        this.g_tc_ceb06 = g_tc_ceb06;
    }

    public void setG_tc_ceb03(String g_tc_ceb03) {
        this.g_tc_ceb03 = g_tc_ceb03;
    }

    public KT07_CheckList_Row(String g_tc_ceb01, String g_tc_ceb02, String g_tc_cea04, String g_tc_cea05, String g_tc_ceb03,String g_tc_ceb06) {
        this.g_tc_ceb01 = g_tc_ceb01;
        this.g_tc_ceb02 = g_tc_ceb02;
        this.g_tc_cea04 = g_tc_cea04;
        this.g_tc_cea05 = g_tc_cea05;
        this.g_tc_ceb03 = g_tc_ceb03;
        this.g_tc_ceb06 = g_tc_ceb06;
    }

    protected KT07_CheckList_Row(Parcel in) {
        g_tc_ceb01 = in.readString();
        g_tc_ceb02 = in.readString();
        g_tc_cea04 = in.readString();
        g_tc_cea05 = in.readString();
        g_tc_ceb03 = in.readString();
        g_tc_ceb06 = in.readString();
    }

    public static final Creator<KT07_CheckList_Row> CREATOR = new Creator<KT07_CheckList_Row>() {
        @Override
        public KT07_CheckList_Row createFromParcel(Parcel in) {
            return new KT07_CheckList_Row(in);
        }

        @Override
        public KT07_CheckList_Row[] newArray(int size) {
            return new KT07_CheckList_Row[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(g_tc_ceb01);
        dest.writeString(g_tc_ceb02);
        dest.writeString(g_tc_cea04);
        dest.writeString(g_tc_cea05);
        dest.writeString(g_tc_ceb03);
        dest.writeString(g_tc_ceb06);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
