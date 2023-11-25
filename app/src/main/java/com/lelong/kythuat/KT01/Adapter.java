package com.lelong.kythuat.KT01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    ArrayList maso, noidung, diemso;
    Context context;
    Button btn1;

    // Constructor for initialization
    public Adapter(Context context, ArrayList maso, ArrayList noidung, ArrayList diemso, Button btn1) {

        this.maso = maso;
        this.noidung = noidung;
        this.diemso = diemso;
        this.btn1 = btn1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml
        // layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kt01_tablayout_fragment_item, parent, false);

        // Passing view to ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TypeCast Object to int type

        holder.tx_maso.setText((String) maso.get(position));
        holder.tx_noidung.setText((String) noidung.get(position));
        holder.tx_diemso.setText((String) diemso.get(position));
        holder.mCheckedTextView.setOnCheckedChangeListener(null);
        holder.tx_btn1.setOnClickListener(null);

        holder.mCheckedTextView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setSelected(true);
                } else {
                    buttonView.setSelected(false);
                }
            }
        });

        holder.mCheckedTextView.setChecked(holder.mCheckedTextView.isSelected());
    }


    @Override
    public int getItemCount() {
        return maso.size();
    }

    /*@Override
    public int getItemCount() {
        // Returns number of items
        // currently available in Adapter
        //return maso.size();
        return 0;
    }*/
    // Initializing the Views
    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox mCheckedTextView;
        ImageView images;
        TextView tx_maso, tx_noidung, tx_diemso;
        Button tx_btn1;

        public ViewHolder(View view) {
            super(view);
            //images = (ImageView) view.findViewById(R.id.maso);
            tx_maso = (TextView) view.findViewById(R.id.tc_fac003);
            tx_noidung = (TextView) view.findViewById(R.id.tc_fac006);
            //tx_diemso = (TextView) view.findViewById(R.id.tc_fac007);
            mCheckedTextView = (CheckBox) view.findViewById(R.id.checkBox);
            tx_btn1 = (Button) view.findViewById(R.id.btn1);

            //view.setOnClickListener(this);
        }


    }
}
