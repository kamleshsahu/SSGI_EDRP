package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class Details_F extends Fragment {


    View rootView;
    ListView listView;
    detailsAdaptor adaptor =null;
    ArrayList<attend_info_class> datalist=attend_shower.datalist;


    public Details_F() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.details, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview);

        if(datalist != null) {
            adaptor = new detailsAdaptor(getActivity(), datalist);
            listView.setAdapter(adaptor);
        }
        return rootView;
    }
}