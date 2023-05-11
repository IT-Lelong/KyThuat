package com.lelong.kythuat.KT01.Retrofit2;

public class APIYtils {
    public static final String Base_Url="http://172.16.40.20/PHP/Retrofit/";
    public static DataClient getData(){
        return Retrofit2Clear.getRetrofit(Base_Url).create(DataClient.class);
    }
}
