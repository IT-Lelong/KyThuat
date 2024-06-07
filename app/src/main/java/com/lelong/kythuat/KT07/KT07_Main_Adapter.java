package com.lelong.kythuat.KT07;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.MainActivity;
import com.lelong.kythuat.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class KT07_Main_Adapter extends RecyclerView.Adapter<KT07_Main_Adapter.DataViewHolder>{
    private final Context applicationContext;
    private final int layout_resource;
    private final List<KT07_Main_RowItem> kt07MainRowItems_list;

    private KT07_DB kt07Db = null;
    private final TextView tv_tc_ceb03;
    private final TextView tv_tc_ceb06;
    private final TextView tv_tc_cebuser;
    private final TextView tv_tc_cebdate;
    private final String model;
    private int editingPosition = RecyclerView.NO_POSITION;
    private int lastFocusedPosition = RecyclerView.NO_POSITION;
    private KT07_Main_FillData kt07MainFillData;
    private AlertDialog alertDialog;
    private KT07_Main kt07_Main;
    AtomicReference<String> lResultReference = new AtomicReference<>();

    public KT07_Main_Adapter(Context applicationContext,
                             int kt07_listdata_item,
                             List<KT07_Main_RowItem> kt07MainRowItems_list,
                             TextView tv_tc_ceb03,
                             TextView tv_tc_ceb06,
                             TextView tv_tc_cebdate,
                             TextView tv_tc_cebuser,
                             KT07_Main_FillData kt07MainFillData, String modeltmp, KT07_Main kt07_Main) {
        this.applicationContext = applicationContext;
        this.layout_resource = kt07_listdata_item;
        this.kt07MainRowItems_list = kt07MainRowItems_list;
        this.kt07MainFillData = kt07MainFillData;
        this.kt07_Main = kt07_Main;
        this.tv_tc_ceb03 = tv_tc_ceb03;
        this.tv_tc_ceb06 = tv_tc_ceb06;
        this.tv_tc_cebdate = tv_tc_cebdate;
        this.tv_tc_cebuser = tv_tc_cebuser;
        this.model = modeltmp;
        kt07Db = new KT07_DB(applicationContext);
        kt07Db.open();
    }

    @NonNull
    @Override
    public KT07_Main_Adapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout_resource, parent, false);
        return new KT07_Main_Adapter.DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KT07_Main_Adapter.DataViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        holder.tv_tc_cea03.setText(kt07MainRowItems_list.get(adapterPosition).getG_tc_cea03());
        holder.tv_tc_cea04.setText(kt07MainRowItems_list.get(adapterPosition).getG_tc_cea04());
        holder.edt_tc_ceb07.setText(kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb07());
        holder.tv_tc_cea04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.tv_tc_cea04, adapterPosition);
            }
        });


        holder.tv_tc_cea05.setText(kt07MainRowItems_list.get(adapterPosition).getG_tc_cea05());
        holder.tv_tc_cea05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu_v2(holder.tv_tc_cea05, adapterPosition,model);
            }
        });

        DecimalFormat decimalFormat = new DecimalFormat("###,###.00", DecimalFormatSymbols.getInstance(Locale.US));
        String formattedTcCeb04Old = decimalFormat.format(BigDecimal.valueOf(Double.parseDouble(kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb04_old())));
        holder.tv_tc_ceb04_old.setText(formattedTcCeb04Old +"\n"+kt07MainRowItems_list.get(adapterPosition).getG_TC_CEB03_CEB06());
        String formattedTcCeb04 = decimalFormat.format(Double.parseDouble(kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb04()));
        holder.tv_tc_ceb04.setText(formattedTcCeb04);
        //holder.tv_tc_ceb04_diff.setText(kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb04_diff());
        String formattedtv_tc_ceb04_diff = decimalFormat.format(Double.parseDouble(String.valueOf(Double.parseDouble(kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb04())-Double.parseDouble(kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb04_old()))));
        holder.tv_tc_ceb04_diff.setText(formattedtv_tc_ceb04_diff);
        //Insert dữ liệu vào bảng ảo tc_ceb (S)
        holder.tv_tc_ceb04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition_tmp = holder.getAdapterPosition();
                editingPosition = adapterPosition_tmp;
                //check kết chuyển
                String tc_cea01=kt07MainRowItems_list.get(adapterPosition).getG_TC_CEA01();
                String tc_cea03=kt07MainRowItems_list.get(adapterPosition).getG_tc_cea03();
                String tc_ceb03 = tv_tc_ceb03.getText().toString();
                String tc_ceb06 = tv_tc_ceb06.getText().toString();
                String tc_cebdate = tv_tc_cebdate.getText().toString();
                String tc_cebuser = tv_tc_cebuser.getText().toString();

                int l_count = kt07Db.getCheckKT(tc_cea01,tc_cea03,tc_ceb03,tc_cebdate,tc_cebuser,tc_ceb06);
                if(l_count > 0){
                    Cursor cursor_check = kt07Db.getdataKT(tc_cea01,tc_cea03);
                    cursor_check.moveToFirst();
                    int num = cursor_check.getCount();
                    if (num == 0){
                        showInputDialog( holder.tv_tc_ceb04, editingPosition,kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb04());
                    }
                    else{
                        String G_TC_CEB03_OLD = "";
                        String G_TC_CEB06_OLD = "";
                        for (int i = 0; i < num; i++) {
                            try {
                                G_TC_CEB03_OLD = cursor_check.getString(cursor_check.getColumnIndexOrThrow("tc_ceb03_old"));
                                G_TC_CEB06_OLD = cursor_check.getString(cursor_check.getColumnIndexOrThrow("tc_ceb06_old"));
                            } catch (Exception e) {
                                String err = e.toString();
                            }
                            cursor_check.moveToNext();
                        }
                        if (G_TC_CEB03_OLD.equals(tc_ceb03) &&  G_TC_CEB06_OLD.equals(tc_ceb06)) {
                            showInputDialog( holder.tv_tc_ceb04, editingPosition,kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb04());
                        }
                        else{
                            Toast.makeText(view.getContext(), "Dữ liệu đã kết chuyển lâu, không thể sửa đổi!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else{
                    showInputDialog( holder.tv_tc_ceb04, editingPosition,kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb04());
                }

            }
        });
//        holder.edt_tc_ceb04.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus) {
//                    // Mất tập trung, người dùng đã kết thúc chỉnh sửa
//                    lastFocusedPosition = holder.getAdapterPosition();
//                    KT07_Debounced debouncedTextWatcher = new KT07_Debounced(new KT07_Debounced.TextWatcherListener() {
//                        @Override
//                        public void onTextChanged(String text) {
//
//                            int sodo = Integer.parseInt(text);
//                            if(kt07MainRowItems_list.get(lastFocusedPosition).getG_tc_cea04() ==text ){
//
//
//                            }else {
//                                if(sodo>0) {
//
//
//
//                                    text = String.valueOf(sodo);
//                                    int adapterPosition_tmp = holder.getAdapterPosition();
//                                    int sodo_old = 0;
//
//                                    editingPosition = adapterPosition_tmp;
//                                    if (editingPosition >= 0) {
//                                        sodo_old = Integer.parseInt(kt07MainRowItems_list.get(editingPosition).getG_tc_ceb04_old());
//                                        if (sodo < sodo_old) {
//
//                                            showAlertDialog(holder.itemView, "Cảnh báo", "Số đo hiện tại không thể nhỏ hơn số đo lần trước !");
//                                            kt07MainFillData.notifydata();
//                                        } else {
//                                            handleTextChanged(editingPosition, text);
//                                            kt07MainRowItems_list.get(editingPosition).setG_tc_ceb04(text);
//                                            kt07MainFillData.notifydata();
//                                        }
//                                    }
//                                }
//                            }
//
//                        }
//                    });
//                }
//            }
//        });

        //Insert dữ liệu vào bảng ảo tc_ceb (E)

        holder.edt_tc_ceb07.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (holder.edt_tc_ceb07.getText().toString().trim().length() >= 0) {
                    getUserCode(holder.edt_tc_ceb07.getText().toString().trim(), holder.getPosition());
                }
                //holder.tc_fac009.getText().toString();
            }

            private void getUserCode(String kt02_tc_fac009, int position) {
                try {
                    final String qr_val = kt02_tc_fac009.trim();
                    String g_tc_ceb07 = null;
                    g_tc_ceb07 = holder.edt_tc_ceb07.getText().toString();
                    kt07Db.appendUPDAE(kt07MainRowItems_list.get(adapterPosition).getG_TC_CEA01(),
                            kt07MainRowItems_list.get(adapterPosition).getG_tc_cea03(),
                            tv_tc_ceb03.getText().toString(),tv_tc_cebdate.getText().toString(),g_tc_ceb07,tv_tc_ceb06.getText().toString());

                    kt07MainRowItems_list.get(position).setG_tc_ceb07(g_tc_ceb07);
                } catch (Exception e) {
                    String err = e.toString();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return kt07MainRowItems_list.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView tv_tc_cea03, tv_tc_cea04, tv_tc_cea05, tv_tc_ceb04_old,tv_tc_ceb04_diff,tv_tc_ceb04;
        EditText edt_tc_ceb07;

        public DataViewHolder(View itemView) {
            super(itemView);
            tv_tc_cea03 = itemView.findViewById(R.id.tv_tc_cea03);
            tv_tc_cea04 = itemView.findViewById(R.id.tv_tc_cea04);
            tv_tc_cea05 = itemView.findViewById(R.id.tv_tc_cea05);
            tv_tc_ceb04_old = itemView.findViewById(R.id.tv_tc_ceb04_old);
            tv_tc_ceb04 = itemView.findViewById(R.id.tv_tc_ceb04);
            tv_tc_ceb04_diff =itemView.findViewById(R.id.tv_tc_ceb04_diff);
            edt_tc_ceb07 = itemView.findViewById(R.id.tv_tc_ceb07);
        }
    }



    private void showInputDialog(View anchorView, int adapterPosition, String tc_ceb04now) {
        AlertDialog.Builder builder = new AlertDialog.Builder(anchorView.getContext());
        TextView title = new TextView(applicationContext);
        title.setText("Nhập giá trị");
        title.setPadding(15, 15, 15, 15);
        title.setTextSize(24);
        title.setTypeface(null, Typeface.BOLD);
        title.setTextColor(ContextCompat.getColor(applicationContext, R.color.black));
        builder.setCustomTitle(title);

        DecimalFormat decimalFormat = new DecimalFormat("###,###.00",DecimalFormatSymbols.getInstance(Locale.US));

        LinearLayout layout = new LinearLayout(applicationContext);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        TextView tvInputLabel = new TextView(applicationContext);
        tvInputLabel.setText("Nhập số đo của đồng hồ");
        tvInputLabel.setTypeface(null, Typeface.BOLD);
        tvInputLabel.setTextColor(ContextCompat.getColor(applicationContext, R.color.black));
        tvInputLabel.setTextSize(21);
        if (tc_ceb04now.equals("0")) {
            tc_ceb04now = "";
        }
        layout.addView(tvInputLabel);
        EditText edtInput = new EditText(applicationContext);
        edtInput.setText(tc_ceb04now);
        edtInput.setHint("Nhập số do");
        edtInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        edtInput.setTextSize(20);
        layout.addView(edtInput);

        String tvTcCeb04OldValue = decimalFormat.format(Double.parseDouble(kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb04_old()));
        String tcCeb04OldValueFormat = tvTcCeb04OldValue.replace(",","");
        String tvTcCeb04NowValue = decimalFormat.format(Double.parseDouble (kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb04()));
        String tcCeb04NowalueFormat = tvTcCeb04OldValue.replace(",","");
        String tcCeb04DiffValue = decimalFormat.format(
                Double.parseDouble(String.valueOf(
                        (Double.parseDouble(tcCeb04NowalueFormat) - Double.parseDouble(tcCeb04OldValueFormat))
                ))
        );


        TextView tvOldValue = new TextView(applicationContext);
        tvOldValue.setText("Số đo lần trước là: " + tvTcCeb04OldValue);
        tvOldValue.setTextColor(ContextCompat.getColor(applicationContext, R.color.black));
        tvOldValue.setTextSize(20);
        layout.addView(tvOldValue);

        TextView tvDiffValue = new TextView(applicationContext);
        tvDiffValue.setText("Lượng tiêu thụ: " + tcCeb04DiffValue);
        tvDiffValue.setTextSize(20);
        tvDiffValue.setTextColor(ContextCompat.getColor(applicationContext, R.color.black));
        layout.addView(tvDiffValue);
        TextView report = new TextView(applicationContext);
        report.setText("");
        report.setTextSize(20);
        report.setTextColor(ContextCompat.getColor(applicationContext, R.color.black));
        layout.addView(report);

        builder.setView(layout);

        KT07_Debounced debouncedTextWatcher = new KT07_Debounced(new KT07_Debounced.TextWatcherListener() {
            @Override
            public void onTextChanged(String userInput) {
                BigDecimal InputValue = new BigDecimal(userInput);
                BigDecimal oldValue = new BigDecimal(tvTcCeb04OldValue.replace(",",""));


                String tcCeb04Diff = decimalFormat.format( InputValue.subtract(oldValue));
                tvDiffValue.setText("Lượng tiêu thụ: " + tcCeb04Diff);
                double sodo_old = Double.parseDouble(kt07MainRowItems_list.get(editingPosition).getG_tc_ceb04_old());
                double sodo = Double.parseDouble(userInput);
                if (sodo < sodo_old) {
                    report.setText("Số do hiện tại không thể nhỏ hơn số đo cũ !");
                }else {
                    report.setText("");
                }
                if (userInput.equals("")){
                }
                else {
                    if (alertDialog != null && alertDialog.getButton(DialogInterface.BUTTON_POSITIVE) != null) {
                        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(Double.parseDouble(userInput) >= Double.parseDouble(tvTcCeb04OldValue.replace(",", "")));
                    }
                }
            }
        });

        edtInput.addTextChangedListener(debouncedTextWatcher);

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userInput = edtInput.getText().toString();
                if (userInput.equals("")){

                }else {
                    double sodo = Double.parseDouble(userInput);
                    if (sodo > 0) {
                        userInput = String.valueOf(sodo);
                        handleUserInput(adapterPosition, userInput);
                        kt07MainRowItems_list.get(adapterPosition).setG_tc_ceb04(userInput);
                        kt07MainFillData.notifydata();

                    }
                }
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


        builder.setCancelable(false);

        alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                if (alertDialog != null && alertDialog.getButton(DialogInterface.BUTTON_POSITIVE) != null) {
                    String edtInput_tmp = edtInput.getText().toString();
                    if(!edtInput_tmp.equals("") ){
                        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(Double.parseDouble(edtInput.getText().toString()) >= Double.parseDouble(tvTcCeb04OldValue.replace(",","")));

                    }else {
                        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(0>= Double.parseDouble(tvTcCeb04OldValue.replace(",","")));
                    }

                }
            }
        });

        alertDialog.show();
        edtInput.requestFocus();
    }

    //Insert dữ liệu vào bảng ảo tc_ceb (S)
    private void  handleUserInput(int adapterPosition, String text) {
        if (text.equals(null)){
            return;
        }else {
            /*kt07Db.ins_tc_ceb_file(kt07MainRowItems_list.get(adapterPosition).getG_TC_CEA01(),
                    kt07MainRowItems_list.get(adapterPosition).getG_tc_cea03(),
                    tv_tc_ceb03.getText().toString(),
                    text,
                    "0",
                    tv_tc_ceb06.getText().toString(),
                    tv_tc_cebdate.getText().toString(),
                    tv_tc_cebuser.getText().toString()
            ,kt07MainRowItems_list.get(adapterPosition).getG_tc_ceb07());*/

            kt07Db.ins_tc_ceb_file(kt07MainRowItems_list.get(adapterPosition).getG_TC_CEA01(),
                    kt07MainRowItems_list.get(adapterPosition).getG_tc_cea03(),
                    tv_tc_ceb03.getText().toString(),
                    text,
                    "0",
                    tv_tc_ceb06.getText().toString(),
                    tv_tc_cebdate.getText().toString(),
                    tv_tc_cebuser.getText().toString());
        }
    }
    //Insert dữ liệu vào bảng ảo tc_ceb (E)
    private void showAlertDialog(View view,String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when the user clicks OK
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void showPopupMenu(View anchorView, final int adapterPosition) {
        PopupMenu popupMenu = new PopupMenu(applicationContext, anchorView);

        // Thêm các mục menu vào mã nguồn
        popupMenu.getMenu().add("Thay Đổi Đồng Hồ/Đặt lại đồng hồ");


        // Đặt lắng nghe cho mỗi mục menu
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(adapterPosition>=0){
                    kt07MainRowItems_list.get(adapterPosition).setG_tc_ceb04_old("0");
                    kt07MainFillData.notifydata();
                }

                return true;
            }
        });

        // Hiển thị menu popup
        popupMenu.show();
    }
    private void showPopupMenu_v2(View anchorView, final int adapterPosition,String model_tmp) {
        PopupMenu popupMenu = new PopupMenu(applicationContext, anchorView);

        // Thêm các mục menu vào mã nguồn
        popupMenu.getMenu().add("Điện");
        popupMenu.getMenu().add("Nước");
        popupMenu.getMenu().add("Gas");

        // Đặt lắng nghe cho mỗi mục menu
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(adapterPosition>=0){
                    String mainCategory = item.getTitle().toString();
                    kt07MainFillData.fill_data(null,mainCategory);
                }

                return true;
            }
        });

        // Hiển thị menu popup
        popupMenu.show();
    }
}


