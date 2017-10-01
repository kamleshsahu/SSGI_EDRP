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


        int val=0;
try{
   // val = Integer.parseInt(MatchingActivity.attend_val.split("\\.")[0]);
    val =(int)Float.parseFloat(MatchingActivity.attend_val);
}catch (Exception e){
    e.fillInStackTrace();
    val=0;
    System.out.println("attendence value changing error ...");
}

        final com.example.riteshagrawal.sstc_edrp.CircularProgressBar attendence =(com.example.riteshagrawal.sstc_edrp.CircularProgressBar)rootView.findViewById(R.id.attendence_p);

        attendence.animateProgressTo(0, val , new CircularProgressBar.ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {
            }

            @Override
            public void onAnimationProgress(int progress) {
                attendence.setTitle(MatchingActivity.attend_val+" %");
            }

            @Override
            public void onAnimationFinish() {
            }
        });





        TextView total_Lectures =(TextView)rootView.findViewById(R.id.total_lectures);
        TextView attended_Lectures=(TextView)rootView.findViewById(R.id.attended_lectures);
        TextView bw_dates=(TextView)rootView.findViewById(R.id.bw_dates);

       //attendence.setText(MatchingActivity.attend_val);
        System.out.println("here is attendence percent : "+MatchingActivity.attend_val);
        attended_Lectures.setText(MatchingActivity.Attended_lectures);
        total_Lectures.setText(MatchingActivity.Total_lectures);
        bw_dates.setText(MatchingActivity.fromdate.getText() +" - "+ MatchingActivity.todate.getText());
        return rootView;
    }
}