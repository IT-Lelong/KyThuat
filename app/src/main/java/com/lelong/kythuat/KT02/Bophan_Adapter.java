package com.lelong.kythuat.KT02;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Bophan_Adapter extends BaseAdapter {

    private LayoutInflater flater;
    private List<List_Bophan> list;
    int listItemLayoutResource;
    int sp_mabp;
    int sp_tenbp;

    public Bophan_Adapter(Activity context, int listItemLayoutResource, int sp_mabp, int sp_tenbp, List<List_Bophan> list) {
        this.flater = context.getLayoutInflater();
        this.list = list;
        this.listItemLayoutResource = listItemLayoutResource;
        this.sp_mabp = sp_mabp;
        this.sp_tenbp = sp_tenbp;
    }

    @Override
    public int getCount() {
        if (this.list.isEmpty()) {
            return 0;
        }
        return this.list.size();
    }

    @Override
    public List_Bophan getItem(int position) {
        if (this.list.isEmpty()) {
            return null;
        }
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (this.list.isEmpty()) {
            return 0;
        }
        List_Bophan listBophan = this.getItem(position);
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        List_Bophan listBophan = (List_Bophan) getItem(position);
        // Example: @listItemLayoutResource: R.layout.spinner_item_layout_resource
        // (File: layout/spinner_item_layout_resourcerce.xml)
        View rowView = this.flater.inflate(this.listItemLayoutResource, null, true);

        // Example: @textViewItemNameId: R.id.textView_item_name
        // (A TextView in file layout/spinner_item_layout_resourcerce.xml)
        TextView sp_mabp=(TextView) rowView.findViewById(this.sp_mabp);
        sp_mabp.setText(listBophan.getMabp());
        TextView sp_tenbp=(TextView) rowView.findViewById(this.sp_tenbp);
        sp_tenbp.setText(listBophan.getTenbp());
        return rowView;
    }
}
