package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ViewHolder> {
    //List<TabLayout> mangLV;
    //ArrayList tc_fab003, tc_fab006,tc_fab007;
    private Context applicationContext;
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    private Button loginButton;
    private List<String> mData;
    private  KT01_DB db=null;
    String currentDate;
    String lbophan1;
    String bien;
    String value;
    String bienngay;
    String ID2;
    String DULIEU;
    String DULIEUnew,DULIEUnew1;
    private int resource;
    private ArrayList<TabLayout> mangLV;
    private Object buttonView;

    public void setData(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }
    public ListDataAdapter(Context applicationContext, int resource, ArrayList<TabLayout> mangLV) {
        this.applicationContext = applicationContext;
        this.resource = resource;
        this.mangLV = mangLV;
        db=new KT01_DB(this.applicationContext);
        db.open();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Inflating the Layout(Instantiates listdata_item.xml
        // layout file into View object)
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_item, parent, false);
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listdata_item01,parent,false);
        // Passing view to ViewHolder
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;


    }

    private
    void startActivity(Intent intent) {
    }



    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.tc_fab003.setText(mangLV.get(position).getTc_fac003());
        holder.tc_fab006.setText(mangLV.get(position).getTc_fac006());
        holder.tc_fab007.setText(mangLV.get(position).getTc_fac007());
        holder.checkBox1.setChecked(mangLV.get(position).getTc_checkbox());
        holder.ghichu1.setText(mangLV.get(position).getTc_ghichu());

        holder.btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public
            void onClick(View v) {
                ;
                // Xóa giá trị của EditText
              //  holder.ghichu1.setText("");
                DULIEU =mangLV.get(position).getTc_fac004();
                Intent intent = new Intent(applicationContext, kt01_camera.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID", DULIEU);
                intent.putExtras(bundle);


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // add this line
                applicationContext.startActivity(intent);


            }
        });
        try {
            InputStream is = applicationContext.getApplicationContext().openFileInput("mydata.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            ID2 = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LocalDate currentDate = LocalDate.now();
        bienngay = String.valueOf(currentDate);
      //  holder.checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          //  @Override
          //  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
           //     if (isChecked) {
                    // Nếu checkbox được check, gán giá trị "Y" cho nó
            //        String value = "Y";
            //        checkBox.setTag(value);
             //   } else {
                    // Nếu checkbox không được check, gán giá trị rỗng cho nó
              //      checkBox.setTag("");
              //  }
           // }
       // });


                holder.checkBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            // Nếu checkbox được check, gán giá trị "Y" cho nó
                            value = "Y";
                            DULIEUnew =mangLV.get(position).getTc_fac004();
                            db.appendUPDAE(DULIEUnew,value,bienngay,ID2,"tc_faa004");
                        } else {
                            // Nếu checkbox không được check, đặt lại giá trị của nó
                            value = "";
                        }
                    }
                });

         holder.ghichu1.addTextChangedListener(new TextWatcher() {
             @Override
             public
             void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

             @Override
             public
             void afterTextChanged(Editable s) {

             }
         }
         );
        holder.ghichu1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    String newText =  holder.ghichu1.getText().toString();
                    // Lấy giá trị mới của EditText
                    //Log.d("EditText", "New text: " + newText);
                    DULIEUnew =mangLV.get(position).getTc_fac004();
                    db.appendUPDAE(DULIEUnew,newText,bienngay,ID2,"tc_faa006");
                    holder.ghichu1.setText("");

                    // Nếu người dùng rời khỏi EditText, thì đặt giá trị của EditText là rỗng
                }
            }
        });






        }




    @Override
    public int getItemCount() {
        return mangLV.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox mCheckedTextView,checkBox1;
        ImageView images;
        Button btn1;
        EditText ghichu1;

        TextView tc_fab003, tc_fab006, tc_fab007;

        public ViewHolder(View view) {
            super(view);
            tc_fab003 = (TextView) view.findViewById(R.id.tc_fac003);
            tc_fab006 = (TextView) view.findViewById(R.id.tc_fac006);
            tc_fab007 = (TextView) view.findViewById(R.id.tc_fac007);
            checkBox1 =  view.findViewById(R.id.checkBox);
            ghichu1  = (EditText ) view.findViewById(R.id.ghichu);
            btn1 = (Button ) view.findViewById(R.id.btn1);




        }
    }
}
