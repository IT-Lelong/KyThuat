package com.lelong.kythuat.KT07;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.lelong.kythuat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class KT07_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener , KT07_Main_FillData {
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> contentCollection;
    ExpandableListView expandableListView;
    KT07_GroupAdapter KT07_GroupAdapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    TextView tc_cebuser, tc_ceb06, tv_tc_ceb03, tv_tc_cebdate;
    String ID;
    int id_menu;
    SimpleDateFormat dateFormat;
    private KT07_DB kt07Db = null;
    JSONObject ujobject;
    JSONArray jsonupload;
    String g_server = "";
    String a;
    NavigationView navigationView;
    RecyclerView rcv_hangmuc;
    KT07_Main_Adapter kt07MainAdapter;
    List<KT07_Main_RowItem> kt07MainRowItems_list;
    String modelUdate;
    String timeUpdate;
    private Spinner waterSpinner;
    private Spinner gasSpinner;
    private Spinner electricitySpinner;
    private ProgressBar progressBar;
    private String selectedDate ;
    private String tc_ceb06_old;
    private String modeltmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kt07_fragment);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Bundle getbundle = getIntent().getExtras();
        ID = getbundle.getString("ID");
        g_server = getString(R.string.server);
        kt07Db = new KT07_DB(this);
        kt07Db.open();
        kt07Db.create_table();
        kt07Db.delete_table();


        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        //tabLayout.setTabMode(TabLayout.MODE_FIXED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.navigation_view);
        createGroupList();
        Call_navigationView_menu();
        addControls();
        //Call_navigationView_menu();
        navigationView.setNavigationItemSelectedListener(this);
        RelativeLayout relativeLayout = findViewById(R.id.kt07_relative);
        View headerView = relativeLayout.findViewById(R.id.nav_header);
        //View headerView = navigationView.getHeaderView(0); // Lấy reference đến header của NavigationView

        tc_cebuser = headerView.findViewById(R.id.tv_tc_cebuser);
        tc_ceb06 = headerView.findViewById(R.id.tv_tc_ceb06);
        tv_tc_ceb03 = headerView.findViewById(R.id.tv_tc_ceb03);
        tv_tc_cebdate = headerView.findViewById(R.id.tv_tc_cebdate);
        tc_cebuser.setText(ID);
        Log.d("TAG", "Value of tc_cebuser: " + tc_cebuser.getText().toString());
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        tv_tc_ceb03.setText(dateFormat.format(new Date()).toString());
        tv_tc_cebdate.setText(dateFormat.format(new Date()).toString());

        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date previousDate = calendar.getTime();

        String previousDateString = dateFormat.format(previousDate);
        String currentDateString = dateFormat.format(currentDate);
        tv_tc_ceb03.setText(previousDateString);
        tv_tc_cebdate.setText(currentDateString);

        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();

        String amPm = (hour < 12) ? "AM" : "PM";

        tc_ceb06.setText(amPm);
        kt07MainRowItems_list = new ArrayList<KT07_Main_RowItem>();
        kt07MainAdapter = new KT07_Main_Adapter(getApplicationContext(),
                R.layout.kt07_listdata_item,
                kt07MainRowItems_list,
                tv_tc_ceb03,
                tc_ceb06,
                tv_tc_cebdate,
                tc_cebuser,
                (KT07_Main_FillData) this);
        rcv_hangmuc.setAdapter(kt07MainAdapter);

        addEvents();
    }

    private void addControls() {
        rcv_hangmuc = findViewById(R.id.rcv_hangmuc);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(KT07_Main.this);
        rcv_hangmuc.setLayoutManager(linearLayoutManager);
        expandableListView = findViewById(R.id.navMenu);
        KT07_GroupAdapter = new KT07_GroupAdapter(this, groupList, contentCollection, this);
        expandableListView.setAdapter(KT07_GroupAdapter);
    }

    private void Call_navigationView_menu() {
        String g_title = null;
        String g_check = null;


//        Menu menu = navigationView.getMenu();


        contentCollection = new HashMap<String, List<String>>();

        Cursor cursorXuong = kt07Db.get_menu_factory(ID);
        Cursor cursorLoaiTieuThu = kt07Db.get_menu_data(ID);
        // Tạo hạng mục "Xưởng" từ Cursor
//        SubMenu xuongSubMenu = menu.addSubMenu("Xưởng");
        for (String group : groupList) {
            if (group.equals("Xưởng")) {
                if (cursorXuong != null && cursorXuong.moveToFirst()) {
                    childList = new ArrayList<>();
                    do {
                        String tenXuong = cursorXuong.getString(cursorXuong.getColumnIndexOrThrow("tc_cea09"));

                        childList.add(tenXuong);
                        //xuongSubMenu.add(tenXuong);
                    } while (cursorXuong.moveToNext());
                    cursorXuong.close();
                    //xuongSubMenu.setGroupDividerEnabled(true);

                }
            } else if (group.equals("Điện")) {
                if (cursorLoaiTieuThu != null && cursorLoaiTieuThu.moveToFirst()) {
                    childList = new ArrayList<>();
                    do {
                        String g_tc_cea01 = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("tc_cea01"));
                        String loai = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("loai"));

                        if (loai.equals("dien")) {

                            childList.add(g_tc_cea01);
                        }

                    } while (cursorLoaiTieuThu.moveToNext());

                }
            } else if (group.equals("Nước")) {
                if (cursorLoaiTieuThu != null && cursorLoaiTieuThu.moveToFirst()) {
                    childList = new ArrayList<>();
                    do {
                        String g_tc_cea01 = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("tc_cea01"));
                        String loai = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("loai"));

                        if (loai.equals("nuoc")) {
                            childList.add(g_tc_cea01);
                        }
                    } while (cursorLoaiTieuThu.moveToNext());


                }
            } else if (group.equals("Gas")) {
                if (cursorLoaiTieuThu != null && cursorLoaiTieuThu.moveToFirst()) {
                    childList = new ArrayList<>();
                    do {
                        String g_tc_cea01 = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("tc_cea01"));
                        String loai = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("loai"));

                        if (loai.equals("gas")) {
                            childList.add(g_tc_cea01);
                        }
                    } while (cursorLoaiTieuThu.moveToNext());
                    cursorLoaiTieuThu.close();

                }
            }
            contentCollection.put(group, childList);
        }

        // Tạo hạng mục "Loại tiêu thụ" với các submenu "Điện", "Nước", "Gas" từ Cursor


//       SubMenu loaiTieuThuSubMenu = menu.addSubMenu("Loại tiêu thụ");
//        SubMenu dienSubMenu = loaiTieuThuSubMenu.addSubMenu("Điện");
//        SubMenu nuocSubMenu = loaiTieuThuSubMenu.addSubMenu("Nước");
//        SubMenu gasSubMenu = loaiTieuThuSubMenu.addSubMenu("Gas");
//        Cursor cursorLoaiTieuThu = kt07Db.get_menu_data(ID);
//        if (cursorLoaiTieuThu != null && cursorLoaiTieuThu.moveToFirst()) {
//            do {
//                String loai = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("loai"));
//                String g_tc_cea01 = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("tc_cea01"));
//
//                // Thêm các hạng mục vào submenu tương ứng
//                if (loai.equals("dien")) {
//                    dienSubMenu.add(g_tc_cea01);
//                } else if (loai.equals("nuoc")) {
//                    nuocSubMenu.add(g_tc_cea01);
//                } else if (loai.equals("gas")) {
//                    gasSubMenu.add(g_tc_cea01);
//                }
//            } while (cursorLoaiTieuThu.moveToNext());
//            cursorLoaiTieuThu.close();
//        }


//        Cursor menu_curs = kt07Db.get_menu_data(ID);
//        if (menu_curs != null && menu_curs.moveToFirst()) {
//            do {
//                // Lấy dữ liệu từ menu_curs
//                String g_tc_cea01 = menu_curs.getString(menu_curs.getColumnIndexOrThrow("tc_cea01"));
//                String loai = menu_curs.getString(menu_curs.getColumnIndexOrThrow("loai"));
//                int iconRes = 0;
//
//                /*iconRes = loai.equals("dien") ? R.drawable.dien :
//                          loai.equals("nuoc") ? R.drawable.nuoc :
//                          loai.equals("gas") ? R.drawable.gas: null ;*/
//
//                switch (loai) {
//                    case "dien":
//                        g_title = "電度數 Điện";
//                        iconRes = R.drawable.dien;
//                        break;
//                    case "nuoc":
//                        g_title = "水度數 Nước";
//                        iconRes = R.drawable.nuoc;
//                        break;
//                    case "gas":
//                        g_title = "瓦斯,氧氣度數  Gas";
//                        iconRes = R.drawable.gas;
//                        break;
//                }
//
//                // Tạo MenuItem cho tiêu đề chính
//                if (g_check == null || (!g_check.equals(loai) && g_check.length() > 0)) {
//                    SpannableString spannable_title = new SpannableString(g_title);
//                    // Thiết lập màu chữ
//                    spannable_title.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spannable_title.length(), 0);
//                    // Thiết lập kiểu chữ in đậm (bold)
//                    spannable_title.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, spannable_title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    //MenuItem menuItem = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, spannable_title).setEnabled(false);
//                    MenuItem menuItem = menu.add(Menu.NONE, Menu.NONE, Menu.NONE, spannable_title).setEnabled(false);
//                    menuItem.setIcon(iconRes);
//                }
//                SpannableString spannable = new SpannableString(g_tc_cea01);
//                spannable.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannable.length(), 0);
//                menu.add(Menu.NONE, Menu.NONE, Menu.NONE, spannable);
//                g_check = loai;
//            } while (menu_curs.moveToNext());
//            menu_curs.close();
//        }

//        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "").setEnabled(false);
        //menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "").setEnabled(false);
        SpannableString spannable = new SpannableString("拋轉資料 Đăng tải dữ liệu   ");
        Drawable uploadIcon = getResources().getDrawable(R.drawable.loaddata);
        ImageSpan imageSpan = new ImageSpan(uploadIcon, ImageSpan.ALIGN_BASELINE);
        spannable.setSpan(imageSpan, spannable.length() - 1, spannable.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        uploadIcon.setBounds(0, 0, 40, 40); // Điều chỉnh kích thước theo ý muốn
        TextView textupload = findViewById(R.id.kt07_textUpload);
        textupload.setText(spannable);

//        SpannableString deleteSpannable = new SpannableString("刪除資料 Xóa dữ liệu   ");
//        Drawable deleteIcon = getResources().getDrawable(R.drawable.trash);
//        ImageSpan imagedelete = new ImageSpan(deleteIcon , ImageSpan.ALIGN_BASELINE);
//        deleteSpannable.setSpan(imagedelete, deleteSpannable.length() - 1, deleteSpannable.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        deleteIcon.setBounds(0, 0, 40, 40); // Điều chỉnh kích thước theo ý muốn
//        TextView textdelete = findViewById(R.id.kt07_textDelete);
//        textdelete .setText(deleteSpannable );
//        SpannableString spannable_del = new SpannableString("刪除資料 Xóa dữ liệu");
//        spannable_del.setSpan(new ForegroundColorSpan(Color.WHITE), 0, spannable_del.length(), 0);
//        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, spannable_del).setIcon(R.drawable.dt);
    }

    private void addEvents() {
        tv_tc_ceb03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(KT07_Main.this);
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = String.format("%04d/%02d/%02d", year, month + 1, dayOfMonth);
                        tv_tc_ceb03.setText(selectedDate);
                    }
                });
                datePickerDialog.show();
            }
        });
        tc_ceb06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tc_ceb06_old =tc_ceb06.getText().toString();
                final CharSequence[] items = {"AM", "PM"};

                // Tạo AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Chọn giờ");

                // Thiết lập danh sách lựa chọn và xử lý sự kiện khi một phần tử được chọn
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Lấy giá trị được chọn từ danh sách
                        String selectedValue = items[which].toString();

                        if (tc_ceb06_old != selectedValue){
                            int count = kt07Db.getCheck(tv_tc_cebdate.getText().toString(),tc_ceb06_old );
                            if(count >0 ) {
                                Toast.makeText(KT07_Main.this, "Có dữ liệu bị thay đổi", Toast.LENGTH_SHORT).show();
                            }
                        }
                        // Cập nhật giá trị của TextView
                        tc_ceb06.setText(selectedValue);
                        if (modeltmp == null){
                            modeltmp =" ";
                        }
                        fill_data(modeltmp);
                    }
                });

                // Hiển thị AlertDialog
                builder.show();
            }

        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        String g_title = item.getTitle().toString();

        if (g_title.startsWith("DH") || g_title.startsWith("BL")) {
            Cursor cursor = kt07Db.getAll_tc_cea_data(g_title, tv_tc_cebdate.getText().toString(),tc_ceb06.getText().toString());
            cursor.moveToFirst();
            int num = cursor.getCount();
            kt07MainRowItems_list.clear();
            for (int i = 0; i < num; i++) {
                try {
                    String G_TC_CEA01 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea01"));
                    String G_TC_CEA03 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea03"));
                    String G_TC_CEA04 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea04"));
                    String G_TC_CEA05 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea05"));
                    String G_TC_CEA06 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea06"));
                    String G_TC_CEA08 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea08"));
                    String G_TC_CEB04_DIFF = cursor.getString(cursor.getColumnIndexOrThrow("tc_ceb04_diff"));
                    String G_TC_CEB04_OLD = cursor.getString(cursor.getColumnIndexOrThrow("tc_ceb04_old"));
                    String G_TC_CEBDATE_CEB06 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cebdate_ceb06"));
                    String G_TC_CEB04 = cursor.getString(cursor.getColumnIndexOrThrow("tc_ceb04"));

                    kt07MainRowItems_list.add(new KT07_Main_RowItem(G_TC_CEA01, G_TC_CEA03, G_TC_CEA04, G_TC_CEA05, G_TC_CEA06, G_TC_CEA08, G_TC_CEB04_OLD, G_TC_CEB04, G_TC_CEBDATE_CEB06, G_TC_CEB04_DIFF));
                } catch (Exception e) {
                    String err = e.toString();
                }

                cursor.moveToNext();
            }
            kt07MainAdapter.notifyDataSetChanged();
        }
      /*  switch (id) {

            case R.id.nav_nuoc:
                // Xử lý khi chọn Home
                id_menu = 1;
                TabLayout.Tab firstTab = tabLayout.newTab(); // Create a new Tab names
                firstTab.setText("DHS\n各廠用生活水度數 Bảng Thống Kê Nước Sinh Hoạt Của Toàn Xưởng"); // set the Text for the first Tab
                tabLayout.addTab(firstTab);
                TabLayout.Tab secondTab = tabLayout.newTab(); // Create a new Tab names
                secondTab.setText("DHM\n各廠用軟水度數  Bảng Thống Kê Nước Mềm Của Toàn Xưởng"); // set the Text for the first Tab
                tabLayout.addTab(secondTab);
                TabLayout.Tab thirdTab = tabLayout.newTab(); // Create a new Tab names
                thirdTab.setText("DHR\n各廠用RO水度數 Bảng Thống Kê Nước RO Của Toàn Xưởng"); // set the Text for the first Tab
                tabLayout.addTab(thirdTab);
                break;
            case R.id.nav_gas:
                // Xử lý khi chọn Home
                id_menu = 2;
                TabLayout.Tab firstTab1 = tabLayout.newTab(); // Create a new Tab names
                firstTab1.setText("DHG\n各廠用瓦斯、氧氣度數   Bảng Thống Kê Gas - Oxy Của Toàn  Xưởng"); // set the Text for the first Tab
                tabLayout.addTab(firstTab1);
                break;
            case R.id.nav_dien:
                // Xử lý khi chọn Home
                id_menu = 3;
                TabLayout.Tab firstTab2 = tabLayout.newTab(); // Create a new Tab names
                firstTab2.setText("DHD\n各廠用電度數Bảng Thống Kê Điện Của Toàn Xưởng"); // set the Text for the first Tab
                tabLayout.addTab(firstTab2);
                break;
            case R.id.btndown:
                Down_Datatable();
                break;
            case R.id.btndelete:
                Delete_Datatable();
                break;
            // Thêm xử lý cho các mục khác nếu cần
        }*/

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //Khởi tạo menu trên thanh tiêu đề (S)

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kt07, menu);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public void onTextDeteleClicked(View view){
//        showConfirmationDialog(model_delete);
//
//
//    }
//    private void showConfirmationDialog(String model) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Xác nhận");
//        builder.setMessage("Bạn có chắc muốn xóa dữ liệu không?");
//
//        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (model==null ){
//                    Toast.makeText(KT07_Main.this, "Hãy lựa chọn dữ liệu bạn muốn xử lý", Toast.LENGTH_SHORT).show();}
//                else {
//                    showDatePicker(model);
//                }
//            }
//        });
//
//        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // Hủy bỏ hành động xóa dữ liệu
//                // Nếu bạn muốn thực hiện một hành động nào đó khi người dùng chọn "Không"
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
//    private void showDatePicker(String model) {
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(
//                this,
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        // Thực hiện hành động với ngày tháng đã chọn ở đây
//                        String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
//
//                        kt07Db.delete_table(selectedDate,model);
//                    }
//                },
//                year,
//                month,
//                day
//        );
//
//        datePickerDialog.show();
//    }


    public void onTextUploadClicked(View view) {

        showConfirmationDialog();
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có muốn đăng tải dữ liệu không ?");
        // Tạo LinearLayout
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // Tạo Spinner cho Nước
        addSpinnerWithTitle(linearLayout, "Nước", getWater());

        // Thêm Spinner cho Gas
        addSpinnerWithTitle(linearLayout, "Gas", getGasOptions());

        // Thêm Spinner cho Điện
        addSpinnerWithTitle(linearLayout, "Điện", getElectricityOptions());





        // Tạo LinearLayout mới để chứa tiêu đề và TextView chọn ngày
        LinearLayout dateLayout = new LinearLayout(this);
        dateLayout.setOrientation(LinearLayout.HORIZONTAL);
        dateLayout.setGravity(Gravity.CENTER);

        // Tạo tiêu đề cho ngày tháng
        TextView dateTitleTextView = new TextView(this);
        dateTitleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        dateTitleTextView.setText("Chọn ngày muốn đăng tải dữ liệu:");


        int paddingInPixels = 16; // Đổi giá trị này theo nhu cầu của bạn
        dateTitleTextView.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);
        dateTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        dateLayout.addView(dateTitleTextView);

        // Tạo TextView cho ngày tháng
        final TextView dateTextView = new TextView(this);
        dateTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Đặt giá trị mặc định là ngày hiện tại
        dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateTextView.setText(dateFormat.format(new Date()).toString());
        dateTextView.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(dateTextView);
            }
        });
        dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        dateLayout.addView(dateTextView);

        // Thêm LinearLayout chứa tiêu đề và TextView vào LinearLayout chính
        linearLayout.addView(dateLayout);



        builder.setView(linearLayout);

        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedWater = waterSpinner.getSelectedItem().toString();
                String selectedGas = gasSpinner.getSelectedItem().toString();
                String selectedElectricity = electricitySpinner.getSelectedItem().toString();
                String selectedDate = dateTextView.getText().toString();
                timeUpdate = "";
                timeUpdate = selectedDate;

                modelUdate = "";
                if ( selectedWater == " "&& selectedGas ==" " && selectedElectricity == " ") {
                    modelUdate = "1=1";
                }else {
                    if (selectedWater != " ") {
                        if (modelUdate == "") {
                            modelUdate = modelUdate + selectedWater;
                        } else {
                            modelUdate = modelUdate + "','" + selectedWater;
                        }
                    }
                    if (selectedGas != " ") {
                        if (modelUdate == "") {
                            modelUdate = modelUdate + selectedGas;
                        } else {
                            modelUdate = modelUdate + "','" + selectedGas;
                        }
                    }
                    if (selectedElectricity != " ") {
                        if (modelUdate == "") {
                            modelUdate = modelUdate + selectedElectricity;
                        } else {
                            modelUdate = modelUdate + "','" + selectedElectricity;
                        }
                    }
                }
                showProgressBarOnDialog(dialog);
                new DataProcessingTask(dialog).execute();
            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void addSpinnerWithTitle(LinearLayout linearLayout, String title, List<String> options) {
        // Tạo LinearLayout để chứa tiêu đề và Spinner trên cùng một hàng
        LinearLayout rowLayout = new LinearLayout(this);
        rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setGravity(Gravity.CENTER); // Đặt chính giữa

        // Tạo TextView làm tiêu đề và đặt thuộc tính cho nó
        TextView titleTextView = new TextView(this);
        int paddingInPixels = 16; // Đổi giá trị này theo nhu cầu của bạn
        titleTextView.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);
        titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        titleTextView.setText(title);
        titleTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        rowLayout.addView(titleTextView);

        // Thêm giá trị null làm tùy chọn mặc định
        options.add(0, " ");

        // Tạo Spinner và đặt thuộc tính cho nó
        Spinner spinner = new Spinner(this);
        spinner.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);

                // Đặt kích thước chữ cho hiển thị trong Spinner
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // Điều chỉnh kích thước chữ theo mong muốn
                textView.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);
                return textView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);

                // Đặt kích thước chữ cho các mục trong danh sách thả xuống
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // Điều chỉnh kích thước chữ theo mong muốn
//                textView.setPadding(paddingInPixels, paddingInPixels, paddingInPixels, paddingInPixels);
                return textView;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        rowLayout.addView(spinner);

        // Thêm LinearLayout vào LinearLayout chính
        linearLayout.addView(rowLayout);

        // Đặt giá trị cho Spinner (chỉ để truy cập sau này)
        setSpinnerReference(title, spinner);
    }


    private void setSpinnerReference(String title, Spinner spinner) {
        switch (title) {
            case "Nước":
                waterSpinner = spinner;
                break;
            case "Gas":
                gasSpinner = spinner;
                break;
            case "Điện":
                electricitySpinner = spinner;
                break;
        }
    }
    // Hàm này trả về danh sách các nước
    private List<String> getWater() {
        // Thêm logic để lấy danh sách các nước từ nguồn dữ liệu của bạn
        Cursor cursorLoaiTieuThu = kt07Db.get_menu_data(ID);
        List<String> waterOptions = new ArrayList<>();
        if (cursorLoaiTieuThu != null && cursorLoaiTieuThu.moveToFirst()) {
            waterOptions = new ArrayList<>();
            do {
                String g_tc_cea01 = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("tc_cea01"));
                String loai = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("loai"));

                if (loai.equals("nuoc")) {

                    waterOptions.add(g_tc_cea01);
                }

            } while (cursorLoaiTieuThu.moveToNext());
            cursorLoaiTieuThu.close();
        }
        // Thêm nước khác vào danh sách
        return waterOptions ;
    }

    // Hàm này trả về danh sách các tùy chọn cho Gas
    private List<String> getGasOptions() {
        // Thêm logic để lấy danh sách tùy chọn cho Gas từ nguồn dữ liệu của bạn
        Cursor cursorLoaiTieuThu = kt07Db.get_menu_data(ID);
        List<String> gasOptions = new ArrayList<>();
        if (cursorLoaiTieuThu != null && cursorLoaiTieuThu.moveToFirst()) {
            gasOptions = new ArrayList<>();
            do {
                String g_tc_cea01 = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("tc_cea01"));
                String loai = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("loai"));

                if (loai.equals("gas")) {

                    gasOptions.add(g_tc_cea01);
                }

            } while (cursorLoaiTieuThu.moveToNext());
            cursorLoaiTieuThu.close();
        }
        // Thêm các tùy chọn khác vào danh sách
        return gasOptions;
    }

    // Hàm này trả về danh sách các tùy chọn cho Điện
    private List<String> getElectricityOptions() {
        // Thêm logic để lấy danh sách tùy chọn cho Điện từ nguồn dữ liệu của bạn
        Cursor cursorLoaiTieuThu = kt07Db.get_menu_data(ID);
        List<String> electricityOptions = new ArrayList<>();
        if (cursorLoaiTieuThu != null && cursorLoaiTieuThu.moveToFirst()) {
            electricityOptions = new ArrayList<>();
            do {
                String g_tc_cea01 = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("tc_cea01"));
                String loai = cursorLoaiTieuThu.getString(cursorLoaiTieuThu.getColumnIndexOrThrow("loai"));

                if (loai.equals("dien")) {

                    electricityOptions.add(g_tc_cea01);
                }

            } while (cursorLoaiTieuThu.moveToNext());
            cursorLoaiTieuThu.close();
        }
        // Thêm các tùy chọn khác vào danh sách
        return electricityOptions;
    }
    // Thêm phương thức này để hiển thị DatePickerDialog
    private void showDatePickerDialog(final TextView dateTextView) {
        // Lấy ngày tháng hiện tại
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Tạo một DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Cập nhật biến selectedDate
                selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);

                // Cập nhật TextView với ngày tháng được chọn
                dateTextView.setText(selectedDate);
            }
        }, year, month, day);

        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }

    private void showProgressBarOnDialog(final DialogInterface dialog) {
        // Tạo LinearLayout để chứa ProgressBar và TextView tiến trình
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);

        // Tạo ProgressBar và thêm vào LinearLayout
        final ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        layout.addView(progressBar);

        // Tạo TextView để hiển thị thông điệp
        final TextView messageTextView = new TextView(this);
        messageTextView.setText("Đang xử lý...");
        messageTextView.setGravity(Gravity.CENTER);
        layout.addView(messageTextView);

        // Tạo LayoutParams để đặt kích thước và vị trí cho LinearLayout chứa ProgressBar và TextView
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = Gravity.CENTER;

        // Thêm LinearLayout vào Dialog
        if (dialog instanceof AlertDialog) {
            ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false); // Vô hiệu hóa nút "Có" khi hiển thị ProgressBar
            ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(false); // Vô hiệu hóa nút "Không" khi hiển thị ProgressBar
            ((AlertDialog) dialog).setView(layout, 0, 0, 0, 0);

            // Chặn việc đóng Dialog khi nhấn nút "OK"
            ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Do nothing
                }
            });
        }
    }

    private class DataProcessingTask extends AsyncTask<Void, Void, Void> {
        private DialogInterface dialog;

        public DataProcessingTask(DialogInterface dialog) {
            this.dialog = dialog;
        }

        @Override
        protected Void doInBackground(Void... params) {
            for (int i =0;i<=100;i++){
                Log.d("DataProcessingTask", "onPostExecute called");
            }
            upLoad_Datatable(modelUdate, timeUpdate);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Ẩn hoặc xử lý ProgressBar khi hoàn thành xử lý
            if (dialog != null && dialog instanceof AlertDialog) {
                ProgressBar progressBar = ((AlertDialog) dialog).findViewById(android.R.id.progress);
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true); // Bật lại nút "Có"
                ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE).setEnabled(true); // Bật lại nút "Không"
                dialog.dismiss(); // Tắt Dialog sau khi hoàn thành xử lý
            }
        }
    }
    private void upLoad_Datatable(String model, String date_upload) {
    Thread UpLoad_fia = new Thread(new Runnable() {
        @Override
        public void run() {

            Cursor upl = kt07Db.getAll_tc_ceb_data(model,date_upload);
            jsonupload = cur2Json(upl);
            if (jsonupload.length() == 0){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(KT07_Main.this, "Không có dữ liệu để upload", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{

                try {
                    ujobject = new JSONObject();
                    //ujobject.put("docNum", edt_maCT.getText().toString());
                    ujobject.put("ujson", jsonupload);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String res = upload_all("http://172.16.40.20/" + g_server + "/TechAPP/upload_tc_ceb.php");
                if (!res.equals("False")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(KT07_Main.this, "Đã upload xong", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
//            if (!res.equals("False")) {
//                if (res.length() > 6) {
//                    try {
//                        JSONArray jsonarray = new JSONArray(res);
//                        for (int i = 0; i < jsonarray.length(); i++) {
//                            JSONObject jsonObject = jsonarray.getJSONObject(i);
//                            String g_tc_cea01 = jsonObject.getString("TC_CEB01"); //Loai
//                            String g_tc_cea03 = jsonObject.getString("TC_CEB02"); //STT
//                            String g_tc_ceb03 = jsonObject.getString("TC_CEB03"); //Ngày tiêu hao
//                            String g_tc_cebdate = jsonObject.getString("TC_CEBDATE"); //Ngày nhập
//                            String g_tc_ceb06 = jsonObject.getString("TC_CEB06"); //Bảng số đo
//
//                            if (g_tc_ceb06.equals("0")) {
//                                g_tc_ceb06 = "AM";
//                            } else {
//                                g_tc_ceb06 = "PM";
//                            }
//                            kt07Db.appendUPDAE(g_tc_cea01, g_tc_cea03, "Đã chuyển", g_tc_ceb03, g_tc_cebdate, "tc_cebstatus_in", g_tc_ceb06);
//                            //trangthai.setText("Chưa chuyển");
//                        }
//                        kt07Db.updateALL_tc_cea_in();
//                        a = "ok";
//                    } catch (JSONException e) {
//                        String abc = e.toString();
//                      e.printStackTrace();
//                    }
//                } else {
//                    kt07Db.updateALL_tc_cea_in();
//                    a = "ok";
//                }
            //}
        }
    });

    Thread Load_fia = new Thread(new Runnable() {
        @Override
        public void run() {
            Looper.prepare(); // Chuẩn bị luồng để chạy vòng lặp sự kiện
            //getLVData();
            Looper.loop(); // Bắt đầu vòng lặp sự kiện
        }
    });
    //dialog.dismiss();

        new

    Thread() {
        @Override
        public void run () {
            UpLoad_fia.start();
            try {
                UpLoad_fia.join();
            } catch (InterruptedException e) {
            }
            if (a == "ok") {
                Load_fia.start();
                try {
                    Load_fia.join();
                } catch (InterruptedException e) {
                }
            }

        }
    }.

    start();

}




   /* private void Down_Datatable() {
        Thread UpLoad_fia = new Thread(new Runnable() {
            @Override
            public void run() {

                Cursor upl = kt07Db.getAll_tc_cea_in();
                jsonupload = cur2Json(upl);

                try {
                    ujobject = new JSONObject();
                    //ujobject.put("docNum", edt_maCT.getText().toString());
                    ujobject.put("ujson", jsonupload);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String res = upload_all("http://172.16.40.20/" + g_server + "/TechAPP/upload_tc_ceb.php");
                if (!res.equals("False")) {
                    if (res.length() > 6) {
                        try {
                            JSONArray jsonarray = new JSONArray(res);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonObject = jsonarray.getJSONObject(i);
                                String g_tc_cea01 = jsonObject.getString("TC_CEB01"); //Loai
                                String g_tc_cea03 = jsonObject.getString("TC_CEB02"); //STT
                                String g_tc_ceb03 = jsonObject.getString("TC_CEB03"); //Ngày tiêu hao
                                String g_tc_cebdate = jsonObject.getString("TC_CEBDATE"); //Ngày nhập
                                String g_tc_ceb06 = jsonObject.getString("TC_CEB06"); //Bảng số đo

                                if (g_tc_ceb06.equals("0")) {
                                    g_tc_ceb06 = "AM";
                                } else {
                                    g_tc_ceb06 = "PM";
                                }
                                kt07Db.appendUPDAE(g_tc_cea01, g_tc_cea03, "Đã chuyển", g_tc_ceb03, g_tc_cebdate, "tc_cebstatus_in", g_tc_ceb06);
                                //trangthai.setText("Chưa chuyển");
                            }
                            kt07Db.updateALL_tc_cea_in();
                            a = "ok";
                        } catch (JSONException e) {
                            String abc = e.toString();
                            e.printStackTrace();
                        }
                    } else {
                        kt07Db.updateALL_tc_cea_in();
                        a = "ok";
                    }
                }
            }
        });

        Thread Load_fia = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare(); // Chuẩn bị luồng để chạy vòng lặp sự kiện
                //getLVData();
                Looper.loop(); // Bắt đầu vòng lặp sự kiện
            }
        });
        //dialog.dismiss();

        new Thread() {
            @Override
            public void run() {
                UpLoad_fia.start();
                try {
                    UpLoad_fia.join();
                } catch (InterruptedException e) {
                }
                if (a == "ok") {
                    Load_fia.start();
                    try {
                        Load_fia.join();
                    } catch (InterruptedException e) {
                    }
                }

            }
        }.start();
    }*/

    public JSONArray cur2Json(Cursor cursor) {
        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        return resultSet;
    }

    public String upload_all(String apiUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(apiUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(999999);
            conn.setReadTimeout(999999);
            conn.setDoInput(true); //允許輸入流，即允許下載
            conn.setDoOutput(true); //允許輸出流，即允許上傳

            OutputStream os = conn.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);
            writer.write(ujobject.toString().getBytes("UTF-8"));
            writer.flush();
            writer.close();
            os.close();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String result = reader.readLine();
            reader.close();
            return result;
        } catch (Exception ex) {
            return "FAlSE";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    //Khởi tạo menu trên thanh tiêu đề (E)

    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add("Xưởng");
        groupList.add("Điện");
        groupList.add("Nước");;
        groupList.add("Gas");


    }

    @Override
    public void fill_data(String model) {
        Cursor cursor = null;
        modeltmp = model;
        if (model.startsWith("DH") || model.startsWith("BL")) {
            //Fill Data của loại tiêu thụ (S)
            cursor  = kt07Db.getAll_tc_cea_data(model,tv_tc_cebdate.getText().toString(),tc_ceb06.getText().toString());
            //Fill Data của loại tiêu thụ (E)
        }

        if(model.length() == 1 ){
            //Fill Data của Xưởng (S)
            cursor  = kt07Db.getAll_tc_cea_data(model,tv_tc_cebdate.getText().toString(),tc_ceb06.getText().toString());
            //Fill Data của Xưởng (E)
        }

        cursor.moveToFirst();
        int num = cursor.getCount();
        kt07MainRowItems_list.clear();
        for (int i = 0; i < num; i++) {
            try {
                String G_TC_CEA01 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea01"));
                String G_TC_CEA03 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea03"));
                String G_TC_CEA04 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea04"));
                String G_TC_CEA05 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea05"));
                String G_TC_CEA06 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea06"));
                String G_TC_CEA08 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cea08"));
                String G_TC_CEB04_OLD = cursor.getString(cursor.getColumnIndexOrThrow("tc_ceb04_old"));
                String G_TC_CEBDATE_CEB06 = cursor.getString(cursor.getColumnIndexOrThrow("tc_cebdate_ceb06"));
                String G_TC_CEB04 = cursor.getString(cursor.getColumnIndexOrThrow("tc_ceb04"));
                String G_TC_CEB04_DIFF = cursor.getString(cursor.getColumnIndexOrThrow("tc_ceb04_diff"));

                kt07MainRowItems_list.add(new KT07_Main_RowItem(G_TC_CEA01, G_TC_CEA03,G_TC_CEA04, G_TC_CEA05, G_TC_CEA06,G_TC_CEA08,G_TC_CEB04_OLD, G_TC_CEB04,G_TC_CEBDATE_CEB06,G_TC_CEB04_DIFF));
            } catch (Exception e) {
                String err = e.toString();
            }

            cursor.moveToNext();
        }
        kt07MainAdapter.notifyDataSetChanged();
    }

    @Override
    public void notifydata() {
        kt07MainAdapter.notifyDataSetChanged();
    }
}