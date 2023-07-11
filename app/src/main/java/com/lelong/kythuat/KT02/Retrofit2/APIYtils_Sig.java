package com.lelong.kythuat.KT02.Retrofit2;

public class APIYtils_Sig {
    public static final String Base_Url="http://172.16.40.20/PHP/Retrofit/";
    public static DataClient_Sig getData(){
        return Retrofit2Clear_Sig.getRetrofit(Base_Url).create(DataClient_Sig.class);
    }
}
