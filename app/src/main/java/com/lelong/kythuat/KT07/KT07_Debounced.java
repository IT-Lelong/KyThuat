package com.lelong.kythuat.KT07;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.Objects;


public class KT07_Debounced implements TextWatcher {

    private static final long DEBOUNCE_DELAY = 1000;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable runnable;
    private final TextWatcherListener listener;
    private int editable_string;
    private String editable_tmp=null;

    public KT07_Debounced(TextWatcherListener listener) {
        this.listener = listener;
        this.runnable = () -> listener.onTextChanged(getCurrentText());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }


    @Override
    public void afterTextChanged(Editable editable) {
        if (editable != null) {
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, DEBOUNCE_DELAY);

            editable_tmp = editable.toString();
            editable_tmp =  editable_tmp.replace(".","");
        }
    }

    private String getCurrentText() {
        if  (!Objects.equals(editable_tmp, "")){
            return editable_tmp .toString();
        }else
            return  "0";
    }

    public interface TextWatcherListener {
        void onTextChanged(String text);
    }
}


