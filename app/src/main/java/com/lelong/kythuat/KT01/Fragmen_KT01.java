package com.lelong.kythuat.KT01;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;

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
    CheckBox ch;
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    String position;
    String bienngay;
    String ID2;
    String position1;
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


    public Fragmen_KT01(String s) {
        // Required empty public constructor
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FootBall.
     */
    // TODO: Rename and change types and number of parameters
    public
    Fragmen_KT01 newInstance( String param1, String param2) {
        Fragmen_KT01 fragment = new Fragmen_KT01(position);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            ID2 = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mangLV = new ArrayList<TabLayout>();
        LocalDate currentDate = LocalDate.now();
        bienngay = String.valueOf(currentDate);
        createTable_fac = new Create_Table(getContext());
        createTable_fac.open();
        cursor_1 = createTable_fac.getAll_tc_fac("KT01",position1);
        cursor_1.moveToFirst();
        int num = cursor_1.getCount();
        for (int i = 0; i < num; i++) {

            try {
                @SuppressLint("Range") String tc_fac003 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac003"));
                @SuppressLint("Range") String tc_fac004 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac004"));
                @SuppressLint("Range") String tc_fac006 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac006"));
                @SuppressLint("Range") String tc_fac007 = cursor_1.getString(cursor_1.getColumnIndex("tc_fac007"));
                mangLV.add(new TabLayout(view,tc_fac003,tc_fac004, tc_fac006, tc_fac007));
                db.append(tc_fac004,bienngay,ID2,"","","");
            } catch (Exception e) {
                String err = e.toString();
            }

            cursor_1.moveToNext();
        }

        ListDataAdapter adapter = new ListDataAdapter(getContext(), R.layout.listdata_item01, mangLV);

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);

        return view;
    }

    private Context getApplicationContext() {
        return null;
    }
}