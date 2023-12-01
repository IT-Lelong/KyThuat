package com.lelong.kythuat.KT07;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;



public class KT07_Debounced implements TextWatcher {

    private static final long DEBOUNCE_DELAY = 1500;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable runnable;
    private final TextWatcherListener listener;
    private String editable_tmp;

    public KT07_Debounced(TextWatcherListener listener) {
        this.listener = listener;
        this.runnable = () -> listener.onTextChanged(getCurrentText());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Không cần thực hiện gì ở đây
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        // Không cần thực hiện gì ở đây
    }


    @Override
    public void afterTextChanged(Editable editable) {
        if (editable != null) {
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, DEBOUNCE_DELAY);
            editable_tmp = editable.toString();
        }
    }

    private String getCurrentText() {
        return editable_tmp  != null ? editable_tmp .toString() : "";
    }

    public interface TextWatcherListener {
        void onTextChanged(String text);
    }
}


