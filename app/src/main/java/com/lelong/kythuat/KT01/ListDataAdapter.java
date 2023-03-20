package com.lelong.kythuat.KT01;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.KT01.Retrofit2.APIYtils;
import com.lelong.kythuat.KT01.Retrofit2.DataClient;
import com.lelong.kythuat.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ViewHolder> {
    //List<TabLayout> mangLV;
    //ArrayList tc_fab003, tc_fab006,tc_fab007;
    private Context applicationContext;
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;
    private Button loginButton;
    private List<String> mData;
    private  KT01_DB db=null;
    KT01_Interface kt01Interface;
    String currentDate;
    String lbophan1;
    String bien;
    String value;
    String bienngay;
    String ID2;
    String DULIEU;
    String DULIEUnew,DULIEUnew1;
    String g_date;
    String g_bp;
    private int resource;
    private ArrayList<TabLayout> mangLV;
    private Object buttonView;

    public void setData(List<String> data) {
        mData = data;
        notifyDataSetChanged();
    }
    public ListDataAdapter(Context applicationContext, int resource, ArrayList<TabLayout> mangLV, String g_date, String g_bp, KT01_Interface kt01Interface) {
        this.applicationContext = applicationContext;
        this.resource = resource;
        this.mangLV = mangLV;
        this.g_date = g_date;
        this.g_bp = g_bp;
        db=new KT01_DB(this.applicationContext);
        db.open();
        this.kt01Interface = (KT01_Interface) kt01Interface;
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

        if (mangLV.get(position).getTc_dkcamera() == "TRUE"){
            Drawable[] layers = new Drawable[2];
            layers[0] = applicationContext.getDrawable(R.drawable.camera1); // replace R.drawable.button_image with your button image
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setStroke(4, Color.RED); // set border color and width
            gradientDrawable.setCornerRadius(20); // set border corner radius
            layers[1] = gradientDrawable;
            LayerDrawable layerDrawable = new LayerDrawable(layers);
            holder.btn1.setBackground(layerDrawable);
        }


        if (g_date != null){
            ID2 = g_bp;
            bienngay  = g_date;
        }else
        {
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
        }
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
                bundle.putString("l_ngay", bienngay);
                bundle.putString("l_bp", ID2);
                intent.putExtras(bundle);


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // add this line
                applicationContext.startActivity(intent);


            }
        });

       /* holder.btn1.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("Range")
            @Override
            public
            void onClick(View v) {
                ;
                // Xóa giá trị của EditText
              //  holder.ghichu1.setText("");
                DULIEU =mangLV.get(position).getTc_fac004();
               // Intent intent = new Intent(applicationContext, kt01_camera.class);
              //  Bundle bundle = new Bundle();
               // bundle.putString("ID", DULIEU);
               // bundle.putString("l_bp", ID2);
               // bundle.putString("l_ngay", bienngay);
               // intent.putExtras(bundle);


                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // add this line
                //applicationContext.startActivity(intent);
                Cursor cursor =    db.checkxemdacochupdanhcua(bienngay,ID2,DULIEU);
                cursor.moveToFirst();
                int num = cursor.getCount();
                if ( cursor.getString(cursor.getColumnIndex("tc_faa005")).equals("")) {
                    kt01Interface.takefoto(applicationContext,DULIEU);

                }else
                {


                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Warning");
                    SpannableStringBuilder message = new SpannableStringBuilder();
                    //message.append("Xác Nhận kết chuyễn?");

                   //
                    //AlertDialog dialog = builder.create();
                    // dialog.show();

// Define a custom button style
                    int buttonStyle = android.R.style.Widget_Button;

// Create a new button with the custom style
                    Button okButton = new Button(v.getContext(), null, buttonStyle);
                    okButton.setTextColor(Color.WHITE);
                    okButton.setTextSize(20);
                    okButton.setGravity(Gravity.CENTER); // Căn giữa chuỗi văn bản
                    okButton.setText("Ảnh đã được chụp,bạn muốn chụp lại");

// Set the button as the positive button of the dialog
                    builder.setPositiveButton(null, null);
                    builder.setNegativeButton(null, null);
                    builder.setView(okButton);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            kt01Interface.takefoto(applicationContext,DULIEU);
                        }
                    });;

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });


                    AlertDialog dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#669999")));

// Hiển thị hộp thoại
                    dialog.show();



                    return;
                }




            }
        });*/




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
                            value = "N";
                            //value = "Y";
                            DULIEUnew =mangLV.get(position).getTc_fac004();
                            db.appendUPDAE(DULIEUnew,value,bienngay,ID2,"tc_faa004");
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

        @SuppressLint("UseCompatLoadingForDrawables")
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
