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
    View rootView;
    Handler handler;





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


        int val=0;
try{
   // val = Integer.parseInt(AttendenceActivity.attend_val.split("\\.")[0]);
    val =(int)Float.parseFloat(AttendenceActivity.attend_val);
    //System.out.println("hre is the attend val,dashboard:"+val);
}catch (Exception e){
    e.fillInStackTrace();
    val=0;
    //System.out.println("attendence value changing error ...");
}

        final com.example.riteshagrawal.sstc_edrp.CircularProgressBar attendence =(com.example.riteshagrawal.sstc_edrp.CircularProgressBar)rootView.findViewById(R.id.attendence_p);

        attendence.animateProgressTo(0, val , new CircularProgressBar.ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                attendence.setTitle(AttendenceActivity.attend_val+" %");
            }

            @Override
            public void onAnimationFinish() {
            }
        });





        TextView total_Lectures =(TextView)rootView.findViewById(R.id.total_lectures);
        TextView attended_Lectures=(TextView)rootView.findViewById(R.id.attended_lectures);
        TextView bw_dates=(TextView)rootView.findViewById(R.id.bw_dates);

       //attendence.setText(AttendenceActivity.attend_val);
        //System.out.println("here is attendence percent : "+ AttendenceActivity.attend_val);
        attended_Lectures.setText(AttendenceActivity.Attended_lectures);
        total_Lectures.setText(AttendenceActivity.Total_lectures);
        bw_dates.setText(AttendenceActivity.fromdate.getText() +" - "+ AttendenceActivity.todate.getText());
        return rootView;
    }
}