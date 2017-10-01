package com.example.riteshagrawal.sstc_edrp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;


public class Details_F extends Fragment {


    View rootView;
    GridView listView;
    detailsAdaptor adaptor =null;
    ArrayList<attend_info_class> datalist= AttendenceActivity.datalist;


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
        listView = (GridView) rootView.findViewById(R.id.listview);

        if(datalist != null) {
            adaptor = new detailsAdaptor(getActivity(), datalist);
            listView.setAdapter(adaptor);
        }
        return rootView;
    }
}