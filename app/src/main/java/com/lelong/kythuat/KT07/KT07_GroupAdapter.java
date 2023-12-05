package com.lelong.kythuat.KT07;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.lelong.kythuat.R;

import java.util.List;
import java.util.Map;

public class KT07_GroupAdapter extends BaseExpandableListAdapter {
    private Context context;
    private Map<String, List<String>> contentCollection;
    private List<String> groupList;
    KT07_Main_FillData kt07MainFillData ;
    private int selectedGroup = -1;
    private int selectedChild = -1;
    public KT07_GroupAdapter(Context context, List<String> groupList,
                             Map<String, List<String>> contentCollection,
                             KT07_Main_FillData data_interface){
        this.context = context;
        this.contentCollection = contentCollection;
        this.groupList = groupList;
        this.kt07MainFillData = (KT07_Main_FillData)  data_interface;
    }

    public void setSelectedItem(int groupPosition, int childPosition) {
        selectedGroup = groupPosition;
        selectedChild = childPosition;
        notifyDataSetChanged();
    }
    @Override
    public int getGroupCount() {
        return contentCollection.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return contentCollection.get(groupList.get(i)).size();
    }


    @Override
    public Object getGroup(int i) {
        return groupList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return contentCollection.get(groupList.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String group_item = getGroup(i).toString();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.kt07_groupitem, null);
        }
        TextView item = view.findViewById(R.id.group_kt07);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(group_item);
        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        String model = getChild(i, i1).toString();
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.kt07_listitem, null);
        }
        TextView item = view.findViewById(R.id.tv_content);

        item.setText(model);
        if (i == selectedGroup && i1 == selectedChild) {
            view.setBackgroundResource(R.drawable.selected_background); // Thay đổi thành drawable bạn muốn
        } else {
            view.setBackgroundResource(android.R.color.transparent);
        }
        item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                kt07MainFillData.fill_data(model);
                setSelectedItem(i, i1);
            }
        });

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
