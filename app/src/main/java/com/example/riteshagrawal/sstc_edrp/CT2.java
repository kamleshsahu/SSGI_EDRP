package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;


public class CT2 extends Fragment {

    LinearLayout loading;


    ProgressBar progressbar;
    TextView disp_msg;
    Button retryButton;

    View rootView;
    Handler handler;
    private boolean isViewShown = false;

    TabLayout tabLayout;

    Handler after_login,after_gotCookies,after_gotUsersInfo,after_fetchRCdetails;

    static SharedPreferences sd=MainActivity.sd;
    ListView listview;
    rcAdaptor adaptor =null;
       Boolean oncreateCreated0=false;
    static int flag =0;
    static Boolean logged_in=false;
    static Boolean cookie_generated=false;
    Boolean oncreateCreated3=false;
    public CT2() {

    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.listholder, container, false);
        listview = (ListView)rootView.findViewById(R.id.rc_listview);
        loading =(LinearLayout)rootView.findViewById(R.id.loading);
        progressbar =(ProgressBar)rootView.findViewById(R.id.progressBar);
        disp_msg=(TextView)rootView.findViewById(R.id.disp_msg);
        retryButton=(Button)rootView.findViewById(R.id.retryButton);
        after_fetchRCdetails = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("attend_shower,after_fetchRCdetails handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());

             //   main_layout.setVisibility(View.VISIBLE);
                if (data.getResult().equals("success")) {
                    loading.setVisibility(View.GONE);
                    listview.setVisibility(View.VISIBLE);

                    try {
                        System.out.println("here is the first subject :" + data.getRclist().get(0).getSubject());

                        adaptor = new rcAdaptor(getActivity(), data.getRclist());
                        listview.setAdapter(adaptor);

                    } catch (Exception e) {
                        e.fillInStackTrace();
                        System.out.println("here is the bug :" + e.toString());
                        progressbar.setVisibility(View.GONE);
                        disp_msg.setVisibility(View.VISIBLE);
                        disp_msg.setText(data.getErrorMsg());
                        retryButton.setVisibility(View.VISIBLE);
                    }
                    try {baseactivity.rollno.setText("" + baseactivity.list.get(2).getValue() + "");
                        baseactivity.batch.setText("Batch :" + baseactivity.list.get(3).getValue() + "");
                        baseactivity.branch.setText("Branch :" + baseactivity.list.get(6).getValue() + "");
                        baseactivity.sem.setText("Sem :" + baseactivity.list.get(7).getValue() + "");
                        baseactivity.sec.setText("Sec :" + baseactivity.list.get(8).getValue() + "");
                        baseactivity.name.setText(baseactivity.StudentName);


                    } catch (Exception e) {
                        e.fillInStackTrace();
                        System.out.println("here is the error :" + e.toString());

                        loading.setVisibility(View.VISIBLE);
                        listview.setVisibility(View.GONE);
                        progressbar.setVisibility(View.GONE);
                        disp_msg.setVisibility(View.VISIBLE);
                        disp_msg.setText("putting value in display error :" + e.toString());
                        retryButton.setVisibility(View.VISIBLE);
                    }
                } else if (data.getResult().equals("error")) {
                    System.out.println("here is error msg :" + data.getErrorMsg());
                    progressbar.setVisibility(View.GONE);
                    disp_msg.setVisibility(View.VISIBLE);
                    disp_msg.setText(data.getErrorMsg());
                    retryButton.setVisibility(View.VISIBLE);
                }
            }
        };


        after_gotUsersInfo = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
                System.out.println("after_gotUserinfo handler");
                customObject data = (customObject) msg.obj;
                System.out.println("here is result :" + data.getResult());
                System.out.println("here is msg : " + data.getMsg());
                if (data.getResult().equals("success")) {
                    Worker w = new Worker(getActivity(), "fetch_RCdetails_ct2", sd, after_fetchRCdetails);
                    w.setUrlParams(data.getMsg());
                    new Thread(w).start();

                } else {

                    System.out.println("here is error msg :" + data.getErrorMsg());
                    progressbar.setVisibility(View.GONE);
                    disp_msg.setVisibility(View.VISIBLE);
                    disp_msg.setText(data.getErrorMsg());
                    retryButton.setVisibility(View.VISIBLE);
                }
            }
        };

        after_login = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
                System.out.println("after_login handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if (data.getResult().equals("success")) {

                    new Thread(new Worker(getActivity(), "fetch_users_info", sd, after_gotUsersInfo)).start();


                } else {

                }
            }
        };

        after_gotCookies = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                System.out.println("after_gotCookies handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if (data.getResult().equals("success")) {

                    if (logged_in) {
                        //                       Toast.makeText(attend_shower.this, "logged in,no usr infos", Toast.LENGTH_SHORT).show();
                        System.out.println("after gotCookies,logged in,no usr infos");
                        new Thread(new Worker(getActivity(), "fetch_users_info", sd, after_gotUsersInfo)).start();
                    } else {
                        //                       Toast.makeText(attend_shower.this, "not logged in", Toast.LENGTH_SHORT).show();
                        System.out.println("after gotCookies,not logged in");
                        new Thread(new Worker(getActivity(), "login", sd, after_login)).start();
                    }
                } else {
                    progressbar.setVisibility(View.GONE);
                    disp_msg.setVisibility(View.VISIBLE);
                    disp_msg.setText(data.getErrorMsg());
                    retryButton.setVisibility(View.VISIBLE);
                }
            }
        };


         oncreateCreated3=true;
   //     new Thread(new Worker(getActivity(), "generate_cookie", sd, after_gotCookies)).start();
        return rootView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //System.out.println("SetUserVisible,isVisibleToUser :"+isVisibleToUser+",current tab :"+ trn_bw_2_stn.tabindex);
        if (isVisibleToUser && WatchlistActivity.tabindex == 3) {

            //System.out.println("first if ..........");
            Thread cheaker= new Thread("threadT3"){
                @Override
                public void run() {
                    if(getviewcheck()){
                        //System.out.println("if part(getviewcheck=true)");
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                //System.out.println("main thread :"+Thread.currentThread().getName());
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(adaptor !=null) {

                                        }else {
//                                            Worker worker1 = new Worker(getActivity(),"tbts_upcoming");
//                                            worker1.Input_Details(sd, TBTSLiveHandler, sd.getString("src_code", ""), sd.getString("dstn_code", ""),codeToName);
//                                            loading.setVisibility(View.VISIBLE);
//                                            disp_content.setVisibility(View.INVISIBLE);

                                            new Thread(new Worker(getActivity(), "generate_cookie", sd, after_gotCookies)).start();
//                                            Thread threadu = new Thread(worker1);
//                                            //System.out.println("fragment,coming,worker defined,if part(worker thread start)");
//                                            threadu.start();
                                        }
                                    }
                                });
                            }
                        });
                    }else{
                        //System.out.println(" unable to understand......");

                    }


                }
            };
//
            cheaker.start();
        }else{
            //System.out.println("else part of isVisibleToUser && tbts_test.tabindex :"+ trn_bw_2_stn.tabindex);
        }
    }

    private Boolean getviewcheck() {
        Boolean giveback=false;
        //System.out.println("under getviewcheck fn");
        while(oncreateCreated3 !=true){
            try {
                Thread.currentThread().sleep(200);
                //System.out.println(Thread.currentThread().getName()+",whlie,sleep 100 ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(oncreateCreated3){
            //System.out.println(Thread.currentThread().getName()+","+"getview() != null");
            giveback=true;
        }else if (!oncreateCreated3){
            //System.out.println(Thread.currentThread().getName()+","+"getview() = null");
            giveback=false;
        }
        return giveback;
    }


}