package com.lelong.kythuat;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;

public class SetLanguage {
    SharedPreferences preferences_Lang;
    Locale locale;
    Context context;

    public SetLanguage(Context applicationContext) {
        this.context= applicationContext;
        this.preferences_Lang = applicationContext.getSharedPreferences("Language", context.MODE_PRIVATE);
    }

    public int getLanguage() {
        return preferences_Lang.getInt("Language", 0);
    }

    public void setLanguage() {
        int language = getLanguage();
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        switch (language) {
            case 0:
                locale = new Locale("zh");
                Locale.setDefault(locale);
                configuration.setLocale(locale);
                break;
            case 1:
                locale = new Locale("vi");
                Locale.setDefault(locale);
                configuration.setLocale(locale);
                break;

        }
        resources.updateConfiguration(configuration, displayMetrics);
    }



}
