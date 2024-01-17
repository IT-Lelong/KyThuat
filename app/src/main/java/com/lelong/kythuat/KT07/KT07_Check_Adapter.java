package com.lelong.kythuat.KT07;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
public class KT07_Check_Adapter extends RecyclerView.Adapter<KT07_Check_Adapter.ViewHolder> {
    private Context context;
    private int layoutResourceId;
    private List<KT07_CheckList_Row> data;
    //private List<KT07_CheckList_Row> datalist = new ArrayList<>();
    // Khai báo các biến dữ liệu khác nếu cần thiết

    public KT07_Check_Adapter(Context context, int layoutResourceId, List<KT07_CheckList_Row> data/*, các tham số khác nếu cần*/) {
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
        // Khởi tạo các biến dữ liệu khác nếu cần thiết
    }

    // ViewHolder để giữ các thành phần giao diện của mỗi item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tc_ceb01, tv_tc_ceb02,tv_tc_ceb03,tv_tc_cea04,tv_tc_cea05,tv_tc_ceb06;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_tc_ceb01 = itemView.findViewById(R.id.tv_tc_ceb01);
            tv_tc_ceb02 = itemView.findViewById(R.id.tv_tc_ceb02);
            tv_tc_ceb03 = itemView.findViewById(R.id.tv_tc_ceb03);
            tv_tc_cea04 = itemView.findViewById(R.id.tv_tc_cea04);
            tv_tc_cea05 = itemView.findViewById(R.id.tv_tc_cea05);
            tv_tc_ceb06 = itemView.findViewById(R.id.tv_tc_ceb06);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(layoutResourceId, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KT07_CheckList_Row item = data.get(position);
        // Gắn dữ liệu từ item vào các thành phần giao diện của ViewHolder
        holder.tv_tc_ceb01.setText(item.getG_tc_ceb01());
        holder.tv_tc_ceb02.setText(item.getG_tc_ceb02());
        holder.tv_tc_ceb03.setText(item.getG_tc_ceb03());
        holder.tv_tc_cea04.setText(item.getG_tc_cea04());
        holder.tv_tc_cea05.setText(item.getG_tc_cea05());
        holder.tv_tc_ceb06.setText(item.getG_tc_ceb06());
        // Gắn dữ liệu cho các thành phần giao diện khác nếu cần thiết
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
