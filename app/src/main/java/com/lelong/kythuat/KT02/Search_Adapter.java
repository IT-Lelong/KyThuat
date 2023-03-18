package com.lelong.kythuat.KT02;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Search_Adapter extends BaseAdapter {

    private LayoutInflater flater;
    //private List<List_Bophan> list;
    private List<Search_List> list;
    int listItemLayoutResource;
    public Search_Adapter(Activity context, int listItemLayoutResource, int sp_somay, int sp_mabp, int sp_tenbp, List<Search_List> list) {
        this.flater = context.getLayoutInflater();
        this.list = list;
        this.listItemLayoutResource = listItemLayoutResource;
        this.sp_somay = sp_somay;
        this.sp_mabp = sp_mabp;
        this.sp_tenbp = sp_tenbp;
    }

    int sp_somay;
    int sp_mabp;
    int sp_tenbp;

    @Override
    public int getCount() {
        if (this.list.isEmpty()) {
            return 0;
        }
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
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
        //List_Bophan listBophan = this.getItem(position);
        Search_List search_list= (Search_List) this.getItem(position);
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Search_List search_list=(Search_List) getItem(position);
        // Example: @listItemLayoutResource: R.layout.spinner_item_layout_resource
        // (File: layout/spinner_item_layout_resourcerce.xml)
        View rowView = this.flater.inflate(this.listItemLayoutResource, null, true);

        // Example: @textViewItemNameId: R.id.textView_item_name
        // (A TextView in file layout/spinner_item_layout_resourcerce.xml)
        TextView sp_somay=(TextView) rowView.findViewById(this.sp_somay);
        sp_somay.setText(search_list.getSomay());
        TextView sp_mabp=(TextView) rowView.findViewById(this.sp_mabp);
        sp_mabp.setText(search_list.getMabp());
        TextView sp_tenbp=(TextView) rowView.findViewById(this.sp_tenbp);
        sp_tenbp.setText(search_list.getTenbp());
        return rowView;
    }
}
