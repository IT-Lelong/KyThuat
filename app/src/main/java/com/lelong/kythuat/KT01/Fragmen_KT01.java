package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
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

import com.lelong.kythuat.Create_Table;
import com.lelong.kythuat.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import com.lelong.kythuat.SetLanguage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragmen_KT01#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragmen_KT01 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    CheckBox ch;
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String g_date;
    private String g_BP;
    String position;
    boolean checkBox ;
    String bienngay;
    String ID2,ID12;
    String g_lang;
    private static Context g_context;
    String position1;
    Locale locale;
    private  KT01_DB db=null;
    String fragment;
    String l_bien ;
    private String mParam2;

    ArrayList<TabLayout> mangLV;
    CheckBox ck;
    Cursor cursor_1, cursor_2;
    ListView list;
    private Create_Table createTable_fac = null;
    private Context Fragmen_KT01;


    public Fragmen_KT01(String s, String g_date1, String g_BP1) {
        // Required empty public constructor
        g_date = g_date1.toString();
        g_BP = g_BP1.toString();
        if (s.equals("1")) {
            position = "02";
         position1 = position;
        } else if (s.equals("2")) {
            position = "03";
              position1 = position;
        } else if (s.equals("3")) {
            position = "04";
             position1 = position;
        }
        else if (s.equals("0")) {
            position = "01";
            position1 = position;
        }
        else if (s.equals("4")) {
            position = "05";
            position1 = position;
        }
        else if (s.equals("5")) {
            position = "06";
            position1 = position;
        }
    }


    // TODO: Rename and change types and number of parameters
    public Fragmen_KT01(Context context) {
        this.g_context = context;
    }
    public static
    Fragmen_KT01 newInstance( Context context,  String param2,String g_date1, String g_BP1) {
        Fragmen_KT01 fragment = new Fragmen_KT01(context);

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param2);
        args.putString(ARG_PARAM2, g_date1);
        args.putString(ARG_PARAM3, g_BP1);

        fragment.setArguments(args);

        return fragment;


    }


    // Using ArrayList to store images data
    /*ArrayList maso = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5",
            "6", "7", "8"));
    ArrayList noidung = new ArrayList<>(Arrays.asList("Data Structure", "C++", "C#", "JavaScript", "Java",
            "C-Language", "HTML 5", "CSS"));

    ArrayList diemso = new ArrayList<>(Arrays.asList("3", "3", "3", "3", "3",
            "3", "3", "3"));*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLanguage();
        if (getArguments() != null) {
            position = getArguments().getString(ARG_PARAM1);
            g_date = getArguments().getString(ARG_PARAM2);
             g_BP = getArguments().getString(ARG_PARAM3);
        }



        setContentView(R.layout.listdata_item01);

    }


    private void setContentView(int activity_main) {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmen_kt01, container, false);
        db=new KT01_DB(view.getContext());
        db.open();



        // Getting reference of recyclerView
        recyclerView = view.findViewById(R.id.recyclerView);

        // Setting the layout as linear
        // layout for vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        if ( position.equals("0")){
            position1 = "01";
        } else if ( position.equals("1")) {
            position1 = "02";
        } else if ( position.equals("2")) {
            position1 = "03";
        }
        else if ( position.equals("3"))  {
            position1 = "04";
        }
        else if ( position.equals("4"))  {
            position1 = "05";
        }
        else if ( position.equals("5"))  {
            position1 = "06";
        }

        /*
        // Sending reference and data to Adapter
        Adapter adapter = new Adapter(  getApplicationContext(), maso, noidung,diemso);

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);*/
        try {
            InputStream is = getContext().getApplicationContext().openFileInput("mydata.txt");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line =null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                ID2 = sb.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.hide();

        SharedPreferences preferences = getActivity().getSharedPreferences("Language", Context.MODE_PRIVATE);
        int language = preferences.getInt("Language", 0);
        if (language == 0) {
            g_lang = "tc_fac005";
        } else {
            g_lang = "tc_fac006";
        }

        mangLV = new ArrayList<TabLayout>();
        LocalDate currentDate = LocalDate.now();
        bienngay = String.valueOf(currentDate);

        if (g_date != null)  {
            cursor_1 = db.getAll_tc_faa2(g_date,g_BP,position1);
            cursor_1.moveToFirst();
            int num = cursor_1.getCount();
            for (int i = 0; i < num; i++) {


                try {
                    @SuppressLint("Range") String tc_fac003 = cursor_1.getString(cursor_1.getColumnIndex("tc_faa009"));
                    @SuppressLint("Range") String tc_fac004 = cursor_1.getString(cursor_1.getColumnIndex("tc_faa001"));
                    @SuppressLint("Range") String tc_fac006 = cursor_1.getString(cursor_1.getColumnIndex("tc_faa008"));
                    @SuppressLint("Range") String tc_fac007 = cursor_1.getString(cursor_1.getColumnIndex("tc_faa007"));
                    @SuppressLint("Range") String ghichu = cursor_1.getString(cursor_1.getColumnIndex("tc_faa006"));
                    @SuppressLint("Range") String checkBox1 = cursor_1.getString(cursor_1.getColumnIndex("tc_faa004"));
                    @SuppressLint("Range") String tc_fac001 = cursor_1.getString(cursor_1.getColumnIndex("tc_faa010"));
                   if (checkBox1.equals("Y"))
                   {
                       checkBox = true;
                    }else{
                       checkBox = false;
                   }
                    mangLV.add(new TabLayout(view,tc_fac003,tc_fac004,tc_fac006, tc_fac007,ghichu,checkBox));
                    db.append(tc_fac004,bienngay,ID2,"","","",tc_fac007,tc_fac006,tc_fac003,tc_fac001);
                } catch (Exception e) {
                    String err = e.toString();
                }

                cursor_1.moveToNext();
            }
        }else
        {
            createTable_fac = new Create_Table(getContext());
            createTable_fac.open();
            cursor_1 = createTable_fac.getAll_tc_fac("KT01",position1);
            cursor_1.moveToFirst();
            int num = cursor_1.getCount();
            for (int i = 0; i < num; i++) {


                try {
                    @SuppressLint("Range") String tc_fac003 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac003"));
                    @SuppressLint("Range") String tc_fac004 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac004"));
                    @SuppressLint("Range") String tc_fac006 = cursor_1.getString(cursor_1.getColumnIndex(g_lang));
                    @SuppressLint("Range") String tc_fac007 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac007"));
                    @SuppressLint("Range") String tc_fac001 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac001"));
                    mangLV.add(new TabLayout(view,tc_fac003,tc_fac004,tc_fac006, tc_fac007,"",false));
                    db.append(tc_fac004,bienngay,ID2,"","","",tc_fac007,tc_fac006,tc_fac003,tc_fac001);
                } catch (Exception e) {
                    String err = e.toString();
                }

                cursor_1.moveToNext();
            }
        }


        ListDataAdapter adapter = new ListDataAdapter(getContext(), R.layout.listdata_item01, mangLV);

        // Setting Adapter to RecyclerView
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


    private Context getApplicationContext() {
        return null;
    }


}