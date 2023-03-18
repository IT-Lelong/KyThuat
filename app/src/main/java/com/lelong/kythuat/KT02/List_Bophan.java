package com.lelong.kythuat.KT02;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import com.lelong.kythuat.Menu;
import com.lelong.kythuat.R;

public class List_Bophan extends SimpleCursorAdapter {

    private final Context context;
    private final int layout;
    private final Cursor mCursor;
    private final String[] from;
    private final int[] to;
    Dialog dialog;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        Button button = view.findViewById(R.id.btnkt);
        button.setTag(position);
        button.setOnClickListener(btnlistener1);

        return super.getView(position, convertView, parent);
    }
    private Button.OnClickListener btnlistener1 = new Button.OnClickListener() {
        public void onClick(View v) {
            //利用switch case方法，之後新增按鈕只需新增case即可
            switch (v.getId()) {

                case R.id.btnkt: {

                    Intent QR020 = new Intent();
                    QR020.setClass(v.getContext(), Menu.class);
                    Bundle bundle = new Bundle();
                    //bundle.putString("somay", g_soxe);
                    ///bundle.putString("bophan", g_bophan);
                    bundle.putString("LAYOUT", "notlogin");
                    QR020.putExtras(bundle);
                    v.getContext().startActivity(QR020);
                    break;
                }
            }
        }
    };
    public List_Bophan(Context context, int layout, Cursor c, String[] from, int[] to, int flagRegisterContentObserver) {
        super(context, layout, c, from, to);
        this.context = context;
        this.layout = layout;
        this.mCursor = c;
        this.from = from;
        this.to = to;
    }
}
