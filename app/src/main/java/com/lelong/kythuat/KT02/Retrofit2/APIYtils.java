package com.lelong.kythuat.KT02.Retrofit2;

public class APIYtils {
    public static final String Base_Url="http://172.16.40.20/PHPtop/Retrofit/";
    public static DataClient getData(){
        return Retrofit2Clear.getRetrofit(Base_Url).create(DataClient.class);
    }
}
