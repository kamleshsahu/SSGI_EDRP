package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;


public class Dashboard_F extends Fragment {

    LinearLayout disp_content,loading;
    Handler OnCreateHandler;
    String dnlddata=null;
    ProgressBar progressbar;
    TextView disp_msg;
    Button retryButton;
    Thread thread0 = null;
    View rootView;
    Handler handler;
    private boolean isViewShown = false;
    String data1;
    TabLayout tabLayout;

    private Context mContext;
    private PopupWindow mPopupWindow;

    public void setTabLayout(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
    }


       Boolean oncreateCreated0=false;

    public Dashboard_F() {
        // Required empty public constructor
    }
 String filter ="all";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.dashboard, container, false);
        TextView attendence =(TextView)rootView.findViewById(R.id.attendence);


       attendence.setText(attend_shower.attend_val);
        return rootView;
    }
}