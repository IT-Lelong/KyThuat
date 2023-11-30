package com.lelong.kythuat.KT07;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
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
    public KT07_GroupAdapter(Context context, List<String> groupList,
                             Map<String, List<String>> contentCollection){
        this.context = context;
        this.contentCollection = contentCollection;
        this.groupList = groupList;
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

        item.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setMessage("You choose "+model+"");
                AlertDialog alertDialog = builder.create();
             alertDialog.show();
            }
        });
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                builder.setMessage("Do you want to remove?");
//                builder.setCancelable(true);
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int id) {
//                        List<String> child = mobileCollection.get(groupList.get(i));
//                        child.remove(i1);
//                        notifyDataSetChanged();
//                    }
//                });
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });
//                AlertDialog alertDialog = builder.create();
//                alertDialog.show();
//            }
//        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
