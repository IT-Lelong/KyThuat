package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lelong.kythuat.Constant_Class;
import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KT01_Fragment_Create#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KT01_Fragment_Create extends Fragment implements KT01_Interface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String g_date;
    private static final int CAMERA_REQUEST = 1888;
    private String g_BP;
    private String g_TO;
    String position;
    private Context mContext;
    String ID2, ID12;
    String ID1;
    String key1;
    String dkcamera;
    String l_ngay;
    String g_lang;
    View view;
    private static Context g_context;
    String position1;
    Locale locale;
    LocalDate currentDate;
    String tenanh;
    private KT01_DB kt01_db = null;
    ArrayList<KT01_Fragment_Model> mangLV;
    Cursor cursor_1;
    private Create_Table createTable_fac = null;
    String bophan;
    String ngay, tobp;
    private int s;
    DecimalFormat decimalFormat = new DecimalFormat("00");

    public KT01_Fragment_Create(int s_position, String ngay, String bophan, String tobp) {
        // Required empty public constructor
        this.bophan = bophan;
        this.ngay = ngay;
        this.s = s_position;
        this.tobp = tobp;
        position1 = decimalFormat.format(s + 1);
    }

    // TODO: Rename and change types and number of parameters
    public KT01_Fragment_Create(Context context) {
        //this.g_context = context;
    }

    public static KT01_Fragment_Create newInstance(Context context, String param2, String g_date1, String g_BP1, String g_toBP1) {
        KT01_Fragment_Create fragment = new KT01_Fragment_Create(context);

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param2);
        args.putString(ARG_PARAM2, g_date1);
        args.putString(ARG_PARAM3, g_BP1);
        args.putString(ARG_PARAM3, g_toBP1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        if (getArguments() != null) {
            position = getArguments().getString(ARG_PARAM1);
            g_date = getArguments().getString(ARG_PARAM2);
            g_BP = getArguments().getString(ARG_PARAM3);
            g_TO = getArguments().getString(ARG_PARAM3);
        }
        setContentView(R.layout.kt01_tablayout_fragment_item);
    }

    private void setContentView(int activity_main) {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.kt01_tablayout_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.hide();
        kt01_db = new KT01_DB(view.getContext());
        kt01_db.open();

        SharedPreferences preferences = getActivity().getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        if (language == 0) {
            g_lang = "tc_fac005";
        } else {
            g_lang = "tc_fac006";
        }

        try {
            //đọc dữ liệu từ tệp tin "mydata.txt" và gán nó vào biến ID2.
            InputStream is = getContext().getApplicationContext().openFileInput("mydata.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                ID2 = sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mangLV = new ArrayList<KT01_Fragment_Model>();
        createTable_fac = new Create_Table(getContext());
        createTable_fac.open();
        if (Constant_Class.UserFactory.equals("DH")) {
            createTable_fac.ins_fac_01("KT01", bophan, ngay, tobp);
        } else {
            createTable_fac.ins_fac_01("KT01", bophan, ngay, "Bến Lức");
        }
        cursor_1 = createTable_fac.getAll_tc_fac_01("KT01", position1, bophan, ngay);
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        for (int i = 0; i < num; i++) {
            try {
                @SuppressLint("Range") String tc_fac003 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac003"));
                @SuppressLint("Range") String tc_fac004 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac004"));
                @SuppressLint("Range") String tc_fac006 = cursor_1.getString(cursor_1.getColumnIndex(g_lang));
                //@SuppressLint("Range") String tc_fac007 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac007"));
                @SuppressLint("Range") String tc_fac001 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac001"));
                @SuppressLint("Range") String tc_faa006 = cursor_1.getString(cursor_1.getColumnIndex("tc_faa006"));
                @SuppressLint("Range") Boolean checkbox = Boolean.valueOf(cursor_1.getString(cursor_1.getColumnIndex("checkbox")));
                @SuppressLint("Range") Boolean checkbox1 = Boolean.valueOf(cursor_1.getString(cursor_1.getColumnIndex("checkbox1")));
                @SuppressLint("Range") Boolean checkbox2 = Boolean.valueOf(cursor_1.getString(cursor_1.getColumnIndex("checkbox2")));
                @SuppressLint("Range") Boolean checkbox3 = Boolean.valueOf(cursor_1.getString(cursor_1.getColumnIndex("checkbox3")));
                @SuppressLint("Range") Boolean checkbox4 = Boolean.valueOf(cursor_1.getString(cursor_1.getColumnIndex("checkbox4")));
                @SuppressLint("Range") Boolean checkbox5 = Boolean.valueOf(cursor_1.getString(cursor_1.getColumnIndex("checkbox5")));
                mangLV.add(new KT01_Fragment_Model(view, ngay, bophan, tc_fac003, tc_fac004, tc_fac006, tc_faa006,
                        checkbox, checkbox1, checkbox2, checkbox3, checkbox4, checkbox5, dkcamera, tobp));
            } catch (Exception e) {
                String err = e.toString();
            }
            cursor_1.moveToNext();
        }

        KT01_Fragment_Adapter adapter = new KT01_Fragment_Adapter(getContext(),
                R.layout.kt01_tablayout_fragment_item,
                mangLV,
                g_date,
                g_BP,
                (KT01_Interface) this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void setLanguage() {
        SharedPreferences preferences = getActivity().getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        switch (language) {
            case 0:
                configuration.setLocale(Locale.TRADITIONAL_CHINESE);
                break;
            case 1:
                locale = new Locale("vi");
                Locale.setDefault(locale);
                configuration.setLocale(locale);
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    @Override
    public void takefoto(Context context, String key) {
        mContext = context;
        key1 = key;
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        String formattedTime = now.format(formatter);
        System.out.println(formattedTime);

        if (g_date != null) {
            ID1 = g_BP;
            l_ngay = g_date;
        } else {
            try {
                InputStream is = mContext.openFileInput("mydata.txt");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                ID1 = sb.toString();
                currentDate = LocalDate.now();
                l_ngay = String.valueOf(currentDate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //   ID = getbundle.getString("ID");
        tenanh = key1 + "_" + l_ngay + "_" + formattedTime + "_" + ID1;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    @Override
    public void loadData_Sig() {

    }

    @Override
    public void loadData_Search_Sig() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            // imageView.setImageBitmap(photo);
            saveImage(photo);
            kt01_db.appendUPDAE(key1, tenanh, l_ngay, ID1, "TC_FAA005");
        }

    }

    private void saveImage(Bitmap finalBitmap) {
        String savedImageURL = MediaStore.Images.Media.insertImage(
                mContext.getContentResolver(),
                finalBitmap,
                tenanh,
                "Image Context"
        );
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(savedImageURL, fname);
        if (file.exists()) file.delete();
        try {

            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}