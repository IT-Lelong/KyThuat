package com.lelong.kythuat.KT01;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Typeface;
import android.text.InputType;
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
    //private List<KT07_CheckList_Row> datalist = new ArrayList<>();
    // Khai báo các biến dữ liệu khác nếu cần thiết

    public KT01_Signature_Dialog_Adapter(Context context, int layoutResourceId, Cursor mCursor/*, các tham số khác nếu cần*/) {
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.mCursor = mCursor;
        // Khởi tạo các biến dữ liệu khác nếu cần thiết
    }

    // ViewHolder để giữ các thành phần giao diện của mỗi item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_manv;
        EditText tv_sogio,tv_ghichu;
        Button btnxoa;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_manv = itemView.findViewById(R.id.tv_manv);
            tv_sogio = itemView.findViewById(R.id.tv_sogio);
            tv_ghichu = itemView.findViewById(R.id.tv_ghichu);
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
        if (mCursor != null && mCursor.moveToPosition(position)) {
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
        }
        //KT01_Signature_Dialog_Model item = data.get(position);
        //holder.tv_manv.setText(item.getG_manv());
        //holder.tv_sogio.setText(item.getG_sogio());
        //holder.tv_ghichu.setText(item.getG_ghichu());
        // Gắn dữ liệu cho các thành phần giao diện khác nếu cần thiết
    }

    @Override
    public int getItemCount() {
        return (mCursor != null) ? mCursor.getCount() : 0;
    }
}
