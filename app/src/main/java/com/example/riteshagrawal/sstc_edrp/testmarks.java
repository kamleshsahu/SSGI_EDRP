package com.example.riteshagrawal.sstc_edrp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;


public class testmarks extends AppCompatActivity {

//details.............
//    boolean showingDetails = false;
//    TextView lessMore;
//    View v;

    final static Gson gson = new Gson();



    Handler logout_handler,after_login,after_gotCookies,after_gotUsersInfo,after_fetchRCdetails;
    static SharedPreferences sd=MainActivity.sd;

    static String StudentName =attend_shower.StudentName;
    static ArrayList<Users_info_Object> list =attend_shower.list;

    ListView listview;
    rcAdaptor adaptor =null;

    static int flag =0;
    static Boolean logged_in=false;
    static Boolean cookie_generated=false;
    TextView error_msg_disp;


    ArrayList<Users_info_Object> saver_list =new ArrayList();

    static ArrayList<attend_info_class> datalist = new ArrayList<>();
     DatePickerDialog datePickerDialog;

     LinearLayout maindisplay;
     LinearLayout loading,error_disp;

    Button retryButton;
    ProgressBar progressBar,progressBar_first;

    LinearLayout firstlogin;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menuitems,menu);
        MenuItem logout =menu.findItem(R.id.logout);
        MenuItem change_sem_startdate =menu.findItem(R.id.change_sem_startdate);
         logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem menuItem) {
                 sd.edit().putString("loginParams", "").apply();
                 new Thread( new Worker(testmarks.this,"logout",sd,logout_handler)).start();
                 return false;
             }
         });

        change_sem_startdate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent( testmarks.this,sem_startday_setter.class);
                startActivity(i);
                testmarks.this.finish();

                return false;
            }
        });

        MenuItem item =menu.findItem(R.id.refresh);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                recreate();

                return false;
            }

        });

//        MenuItem share =menu.findItem(R.id.share);
//        share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//                String shareBodyText = "http://play.google.com/store/apps/details?id=com.SahuAppsPvtLtd.myTrainEnquiryApp";
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
//                startActivity(Intent.createChooser(sharingIntent, "Share GooglePlay link"));
//
//                return true;
//            }
//
//        });
//        MenuItem rate = menu.findItem(R.id.rate);
//        rate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.SahuAppsPvtLtd.myTrainEnquiryApp"));
//                startActivity(intent);
//
//                return true;
//            }
//        });
//        MenuItem about_us = menu.findItem(R.id.about_us);
//        about_us.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Intent o=new Intent(attend_shower.this,about_us.class);
//                startActivity(o);
//
//                return true;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testmarks);

//Details................
//        final TextView lessMore = (TextView) findViewById(R.id.less_more);
//        RelativeLayout rootView = (RelativeLayout) findViewById(R.id.root_view);
//        LayoutTransition layoutTransition = rootView.getLayoutTransition();
//        layoutTransition.setDuration(300);
//        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
//        lessMore.findViewById(R.id.less_more).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (showingDetails) {
//                    findViewById(R.id.details).setVisibility(View.GONE);
//                    lessMore.setText("More Details");
//                } else {
//                    findViewById(R.id.details).setVisibility(View.VISIBLE);
//                    lessMore.setText("Less Details");
//                }
//                showingDetails = !showingDetails;
//            }
//        });


        final TextView name = (TextView) findViewById(R.id.name);
        final TextView branch = (TextView) findViewById(R.id.branch);
        final TextView sem = (TextView) findViewById(R.id.sem);
        final TextView sec = (TextView) findViewById(R.id.sec);
        final TextView rollno = (TextView) findViewById(R.id.roll_no);
        final TextView batch = (TextView) findViewById(R.id.batch);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final LinearLayout main_layout = (LinearLayout) findViewById(R.id.mainlayout);
        listview = (ListView) findViewById(R.id.rc_listview);


        retryButton = (Button) findViewById(R.id.retryButton);
        maindisplay = (LinearLayout) findViewById(R.id.maindisplay);
        loading = (LinearLayout) findViewById(R.id.loading);

        error_msg_disp = (TextView) findViewById(R.id.disp_msg);


        ArrayList<reportcard> trc = new ArrayList<>();
        trc.add(new reportcard("Subject", "max marks", "min marks", "marks obtained", "percent", "status"));


        adaptor = new rcAdaptor(getApplicationContext(), trc);
        listview.setAdapter(adaptor);


        logout_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("attend_shower,under logout handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if (data.getResult().equals("success")) {

                    Intent i = new Intent(testmarks.this, MainActivity.class);
                    startActivity(i);
                    cookie_generated = false;
                    logged_in = false;


                    testmarks.this.finish();


                } else {
                    maindisplay.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    error_msg_disp.setVisibility(View.VISIBLE);
                    error_msg_disp.setText(data.getErrorMsg());
                    retryButton.setVisibility(View.VISIBLE);

                }
            }
        };

        after_fetchRCdetails = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("attend_shower,after_fetchRCdetails handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());

                main_layout.setVisibility(View.VISIBLE);
                if (data.getResult().equals("success")) {
                    loading.setVisibility(View.GONE);
                    maindisplay.setVisibility(View.VISIBLE);

                    try {
                        System.out.println("here is the first subject :" + data.getRclist().get(0).getSubject());

                        adaptor = new rcAdaptor(getApplicationContext(), data.getRclist());
                        listview.setAdapter(adaptor);

                    } catch (Exception e) {
                        e.fillInStackTrace();
                        System.out.println("here is the bug :" + e.toString());
                    }

                    try {
                        rollno.setText("" + list.get(2).getValue() + "");
                        batch.setText("Batch :" + list.get(3).getValue() + "");
                        branch.setText("Branch :" + list.get(6).getValue() + "");
                        sem.setText("Sem :" + list.get(7).getValue() + "");
                        sec.setText("Sec :" + list.get(8).getValue() + "");
                     //   name.setText(StudentName);


                    } catch (Exception e) {
                        e.fillInStackTrace();
                        System.out.println("here is the error :" + e.toString());
                        maindisplay.setVisibility(View.GONE);
                        loading.setVisibility(View.VISIBLE);

                        progressBar.setVisibility(View.GONE);
                        error_msg_disp.setVisibility(View.VISIBLE);
                        error_msg_disp.setText("putting value in display error :" + e.toString());
                        retryButton.setVisibility(View.VISIBLE);
                    }
                } else if (data.getResult().equals("error")) {
                    System.out.println("here is error msg :" + data.getErrorMsg());
                    progressBar.setVisibility(View.GONE);
                    error_msg_disp.setVisibility(View.VISIBLE);
                    error_msg_disp.setText(data.getErrorMsg());
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
                    Worker w = new Worker(getApplicationContext(), "fetch_RCdetails", sd, after_fetchRCdetails);
                    w.setUrlParams(data.getMsg());
                    new Thread(w).start();

                } else {

                    System.out.println("here is error msg :" + data.getErrorMsg());
                    progressBar.setVisibility(View.GONE);
                    error_msg_disp.setVisibility(View.VISIBLE);
                    error_msg_disp.setText(data.getErrorMsg());
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

                    new Thread(new Worker(getApplicationContext(), "fetch_users_info", sd, after_gotUsersInfo)).start();


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
                        new Thread(new Worker(getApplicationContext(), "fetch_users_info", sd, after_gotUsersInfo)).start();
                    } else {
                        //                       Toast.makeText(attend_shower.this, "not logged in", Toast.LENGTH_SHORT).show();
                        System.out.println("after gotCookies,not logged in");
                        new Thread(new Worker(getApplicationContext(), "login", sd, after_login)).start();
                    }
                } else {

                    main_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    error_msg_disp.setVisibility(View.VISIBLE);
                    error_msg_disp.setText(data.getErrorMsg());
                    retryButton.setVisibility(View.VISIBLE);
                }
            }
        };


        if (!sd.getString("Users_Data_Saver", "").equals("")) {
            String json1 = sd.getString("Users_Data_Saver", "");
            System.out.println("yeh user data reading ..........");

            UserDataSaverObject obj = gson.fromJson(json1, UserDataSaverObject.class);

            for (Users_info_Object item0 : obj.getList()) {
                if (item0.getUname().equals(sd.getString("c_uname", "")) && item0.getPass().equals(sd.getString("c_pass", ""))) {

                    if (item0.getRollno() != null) {
                        rollno.setText(item0.getRollno());
                        batch.setText("Batch :" + item0.getBatch() + "");
                        branch.setText("Branch :" + item0.getBranch() + "");
                        sem.setText("Sem :" + item0.getSem() + "");
                        sec.setText("Sec :" + item0.getSec() + "");
                        name.setText(item0.getName());
                        break;
                    }
                }
            }

        }
        new Thread(new Worker(getApplicationContext(), "generate_cookie", sd, after_gotCookies)).start();
    }


    @Override
    public void onBackPressed() {

        Intent i = new Intent(testmarks.this,attend_shower.class);
        startActivity(i);
        testmarks.this.finish();
    }




    public void RetryTask(View view) {
             cookie_generated=false;
           logged_in=false;
        maindisplay.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
          progressBar.setVisibility(View.VISIBLE);
          error_msg_disp.setVisibility(View.GONE);
          retryButton.setVisibility(View.GONE);
        new Thread(new Worker(getApplicationContext(), "generate_cookie", sd, after_gotCookies)).start();

    }
}
