package com.lelong.kythuat.KT01;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.KT07.KT07_DB;
import com.lelong.kythuat.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
public class KT01_Signature_Dialog_Adapter extends RecyclerView.Adapter<KT01_Signature_Dialog_Adapter.ViewHolder> {
    private Context context;
    private int layoutResourceId;
    private List<KT01_Signature_Dialog_Model> data;
    private Cursor mCursor;
    private KT01_DB kt01_db;
    String mabp,tenbp,ngay;

    //private List<KT07_CheckList_Row> datalist = new ArrayList<>();
    // Khai báo các biến dữ liệu khác nếu cần thiết

    public KT01_Signature_Dialog_Adapter(Context context, int layoutResourceId,List<KT01_Signature_Dialog_Model> data,String mabp,String tenbp,String ngay /*Cursor mCursor, các tham số khác nếu cần*/) {
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        //this.mCursor = mCursor;
        this.data = data;
        this.mabp = mabp;
        this.tenbp = tenbp;
        this.ngay = ngay;
        kt01_db = new KT01_DB(context);
        kt01_db.open();
        // Khởi tạo các biến dữ liệu khác nếu cần thiết
    }


    // ... các phần khác của lớp

    // ViewHolder để giữ các thành phần giao diện của mỗi item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_manv;
        EditText edt_sogio,edt_ghichu;
        Button btnxoa;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_manv = itemView.findViewById(R.id.tv_manv);
            edt_sogio = itemView.findViewById(R.id.edt_sogio);
            edt_ghichu = itemView.findViewById(R.id.edt_ghichu);
            btnxoa = itemView.findViewById(R.id.btnxoa);
        }
    }
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutResourceId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*if (mCursor != null && mCursor.moveToPosition(position)) {
            // Lấy dữ liệu từ Cursor và gán vào ViewHolder
            String data1 = mCursor.getString(mCursor.getColumnIndexOrThrow("manv"));
            String data2 = mCursor.getString(mCursor.getColumnIndexOrThrow("manv"));
            String data3 = mCursor.getString(mCursor.getColumnIndexOrThrow("manv"));
            holder.tv_manv.setText(data1);
            holder.tv_sogio.setText(data2);
            holder.tv_ghichu.setText(data3);
            //holder.tv_manv.setText(item.getG_manv());
            //holder.tv_sogio.setText(item.getG_sogio());
            //holder.tv_ghichu.setText(item.getG_ghichu());
        }*/
        KT01_Signature_Dialog_Model item = data.get(position);
        holder.tv_manv.setText(item.getG_manv());
        holder.edt_sogio.setText(item.getG_sogio());
        holder.edt_ghichu.setText(item.getG_ghichu());
        // Gắn dữ liệu cho các thành phần giao diện khác nếu cần thiết
        holder.btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    kt01_db.delete_kyten(data.get(adapterPosition).getG_manv(),mabp,tenbp,ngay);
                    data.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                }
            }
        });
        holder.edt_ghichu.addTextChangedListener(new TextWatcher() {
            private String originalText = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Lưu trữ +nội dung gốc của EditText trước khi thay đổi
                originalText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Được gọi khi có sự thay đổi trong EditText
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Được gọi sau khi người dùng đã nhập xong
                int adapterPosition = holder.getAdapterPosition();
                String inputData = s.toString();

                if (!inputData.equals(originalText)) {
                    // Được gọi sau khi người dùng đã nhập xong
                    String g_manv = data.get(adapterPosition).getG_manv();
                    kt01_db.update_GhiChuKT(g_manv,inputData,mabp,ngay);
                }
            }
        });
        holder.edt_sogio.addTextChangedListener(new TextWatcher() {
            private String originalText = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Lưu trữ +nội dung gốc của EditText trước khi thay đổi
                originalText = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Được gọi khi có sự thay đổi trong EditText
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Được gọi sau khi người dùng đã nhập xong
                int adapterPosition = holder.getAdapterPosition();
                String inputData = s.toString();

                if (!inputData.equals(originalText)) {
                    // Được gọi sau khi người dùng đã nhập xong
                    String g_manv = data.get(adapterPosition).getG_manv();
                    kt01_db.update_SogioKT(g_manv,inputData,mabp,ngay);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
        // return (mCursor != null) ? mCursor.getCount() : 0;
    }
}
