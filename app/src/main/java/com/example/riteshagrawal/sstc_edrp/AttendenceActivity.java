package com.example.riteshagrawal.sstc_edrp;


import android.app.DatePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



//extends our custom BaseActivity
public class AttendenceActivity extends baseactivity {
    FrameLayout dynamicContent;
    static ArrayList<attend_info_class> datalist = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    static  TextView fromdate,todate;
    RelativeLayout attendence_report_display;
    RelativeLayout loading_error_retry;
    static String Total_lectures="";
    static String Attended_lectures="";
    Button retryButton;
    ProgressBar progressBar;
    TextView error_msg_disp;
    static String attend_val="";
    static TabLayout.Tab secondTab;
    PagerAdapter adapter;
    ViewPager simpleViewPager;
    static int tabindex=-1;
    static long millis= 0;
    static TabLayout tabLayout;
    Handler after_login,after_gotCookies,after_gotUsersInfo,after_fetchAttendence;
    static String todays_date="", todays_Date="";
    static String months[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    static String fromDate="",toDate="";
    RelativeLayout error_retry;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dynamicContent = (FrameLayout)  findViewById(R.id.content);
        View root = getLayoutInflater().inflate(R.layout.activity_attendance, null);
        
        dynamicContent.addView(root);
        attendence_report_display = (RelativeLayout) findViewById(R.id.attendence_report_display);
        
         RelativeLayout attendance_activity= (RelativeLayout) findViewById(R.id.activity_attendance);
        loading_error_retry =(RelativeLayout)findViewById(R.id.loading_error_retry);
       View inflate_retryerror =(RelativeLayout) getLayoutInflater().inflate(R.layout.retry_error, null);
        loading_error_retry.addView(inflate_retryerror);
        error_retry=(RelativeLayout)findViewById(R.id.error_retry);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        retryButton =(Button)findViewById(R.id.retryButton);
        error_msg_disp=(TextView)findViewById(R.id.disp_msg);
        fromdate = (TextView) findViewById(R.id .fromDate);
        todate = (TextView) findViewById(R.id .toDate);
        tabLayout = (TabLayout) findViewById(R.id.sTabLayout);
        tabLayout.setupWithViewPager(simpleViewPager);
        TabLayout.Tab firstTab;
        firstTab = tabLayout.newTab();
        tabLayout.addTab(firstTab);
        secondTab = tabLayout.newTab();
        tabLayout.addTab(secondTab);
        simpleViewPager = (ViewPager) findViewById(R.id.simpleViewPager);



        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        Date date = new Date();
        todays_date=dateFormat.format(date);
        DateFormat dateFormat1 = new SimpleDateFormat("dd MMM,yy");
        Date date1 = new Date();

        todays_Date = dateFormat1.format(date1);
        //System.out.println("here is todays date :"+dateFormat.format(date));



        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(1);

            }
        });
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {datePicker(2);

            }
        });

        adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        simpleViewPager.setAdapter(adapter);
        tabLayout.getTabAt(0).setText("DashBoard ");
        tabLayout.getTabAt(1).setText("Details");



        after_fetchAttendence= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("attend_shower,after_fetchAttendence handler");
                customObject data = (customObject) msg.obj;
                //System.out.println(data.getResult());

                adapter = new PagerAdapter
                        (getSupportFragmentManager(), tabLayout.getTabCount());
                simpleViewPager.setAdapter(adapter);
                tabLayout.getTabAt(0).setText("DashBoard ");
                tabLayout.getTabAt(1).setText("Details");
                if(data.getResult().equals("success")){

                    loading_error_retry.setVisibility(View.GONE);
                    attendence_report_display.setVisibility(View.VISIBLE);

                    if(list != null ){

                        try {
                            Users_info_Object obj = new Users_info_Object(

                                    sd.getString("c_uname", ""),
                                    sd.getString("c_pass", ""),
                                    StudentName,
                                    list.get(6).getValue(),
                                    list.get(7).getValue(),
                                    list.get(8).getValue(),
                                    list.get(2).getValue(),
                                    list.get(3).getValue(),
                                    list.get(5).getValue()
                            );

                            Thread t = new Thread(new Users_Data_Saver(sd, obj));
                            t.start();
                        }catch (Exception e){
                            e.fillInStackTrace();
                            //System.out.println("saving user data error ");
                            attendence_report_display.setVisibility(View.GONE);
                            loading_error_retry.setVisibility(View.VISIBLE);

                            progressBar.setVisibility(View.GONE);
                            error_retry.setVisibility(View.VISIBLE);
                            error_msg_disp.setText("saving user data error :"+e.toString());

                        }
                        try {
                            rollno.setText(""+list.get(2).getValue()+"");
                            batch.setText("Batch :"+list.get(3).getValue()+"");
                            branch.setText("Branch :"+list.get(6).getValue()+"");
                            sem.setText("Sem :"+list.get(7).getValue()+"");
                            sec.setText("Sec :"+list.get(8).getValue()+"");
                            name.setText(StudentName);

                        }catch (Exception e){
                            e.fillInStackTrace();
                            //System.out.println("here is the error :"+e.toString());
                            attendence_report_display.setVisibility(View.GONE);
                            loading_error_retry.setVisibility(View.VISIBLE);

                            progressBar.setVisibility(View.GONE);
                            error_retry.setVisibility(View.VISIBLE);
                            error_msg_disp.setText("saving user data error :"+e.toString());
                        }
                    }else{
                        attendence_report_display.setVisibility(View.GONE);
                        loading_error_retry.setVisibility(View.VISIBLE);

                        progressBar.setVisibility(View.GONE);
                        error_retry.setVisibility(View.VISIBLE);
                        error_msg_disp.setText("AttendenceActivity.java,afterfetchAttendance,datalist is null");

                    }




                    simpleViewPager.setCurrentItem(0);
                    simpleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                    tabindex=0;

                    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {

                            ////System.out.println("selected tab :"+tab.getPosition());
                            tabindex=tab.getPosition();
                            simpleViewPager.setCurrentItem(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {
                            ////System.out.println("Reselected tab :"+tab.getPosition());
                            tabindex=tab.getPosition();
                            simpleViewPager.setCurrentItem(tab.getPosition());
                        }
                    });


                }else if(data.getResult().equals("error")){
                    //System.out.println("here is error msg :"+data.getErrorMsg());

                    attendence_report_display.setVisibility(View.GONE);
                    loading_error_retry.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.GONE);
                    error_retry.setVisibility(View.VISIBLE);
                    error_msg_disp.setText(data.getErrorMsg());

                }
            }
        };


        after_gotUsersInfo = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
                //System.out.println("after_gotUserinfo handler");
                customObject data = (customObject) msg.obj;
                //System.out.println("here is result :"+data.getResult());
                //System.out.println("here is msg : "+data.getMsg());
                if(data.getResult().equals("success")){
                    Worker w=new Worker(getApplicationContext(), "fetch_attendence", sd, after_fetchAttendence);
                    w.setUrlParams(data.getMsg());
                    new Thread(w).start();

                }else{

                    //System.out.println("here is error msg :"+data.getErrorMsg());

                    attendence_report_display.setVisibility(View.GONE);
                    loading_error_retry.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.GONE);
                    error_retry.setVisibility(View.VISIBLE);
                    error_msg_disp.setText(data.getErrorMsg());
                }
            }
        };

        after_login = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                super.handleMessage(msg);
                //System.out.println("after_login handler");
                customObject data = (customObject) msg.obj;
                //System.out.println(data.getResult());
                if(data.getResult().equals("success")){


                    if(!sem_start_date.equals("")) {
//                        Toast.makeText(attend_shower.this, "not have user_info", Toast.LENGTH_SHORT).show();
                        //System.out.println("after login handler,Not having user_info url");
                        new Thread(new Worker(getApplicationContext(),"fetch_users_info",sd,after_gotUsersInfo)).start();
                    }else{
                        Intent i = new Intent( AttendenceActivity.this,sem_startday_setter.class);
                        startActivity(i);
                        AttendenceActivity.this.finish();
                    }

                }else{
//                    Toast.makeText(getApplicationContext()," Error ",Toast.LENGTH_LONG).show();
                    //System.out.println("after got cookies error :"+data.getResult());
                    Intent i = new Intent(AttendenceActivity.this,MainActivity.class);
                    i.putExtra("error_msg",data.getErrorMsg());
                    startActivity(i);
                    AttendenceActivity.this.finish();
                }
            }
        };

        after_gotCookies= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                //System.out.println("after_gotCookies handler");
                customObject data = (customObject) msg.obj;
                //System.out.println(data.getResult());
                if(data.getResult().equals("success")){

                    if(logged_in) {
                        //                       Toast.makeText(attend_shower.this, "logged in,no usr infos", Toast.LENGTH_SHORT).show();
                        //System.out.println("after gotCookies,logged in,no usr infos");
                        new Thread(new Worker(getApplicationContext(),"fetch_users_info",sd,after_gotUsersInfo)).start();
                    }else {
                        //                       Toast.makeText(attend_shower.this, "not logged in", Toast.LENGTH_SHORT).show();
                        //System.out.println("after gotCookies,not logged in");
                        new Thread(new Worker(getApplicationContext(), "login", sd, after_login)).start();
                    }
                }else{

                    attendence_report_display.setVisibility(View.GONE);
                    loading_error_retry.setVisibility(View.VISIBLE);

                    progressBar.setVisibility(View.GONE);
                    error_retry.setVisibility(View.VISIBLE);
                    error_msg_disp.setText(data.getErrorMsg());
                }
            }
        };


            if (sem_start_date !=null && sem_start_date !="") {
                fromDate = sem_start_date;
                fromdate.setText(sem_start_date.split("-")[0] + " " + sem_start_date.split("-")[1] + "," + sem_start_date.split("-")[2].substring(2));
                toDate = todays_date;
                todate.setText(todays_Date);
            }

        progressBar.setVisibility(View.VISIBLE);
        error_retry.setVisibility(View.GONE);
        new Thread(new Worker(getApplicationContext(), "generate_cookie", sd, after_gotCookies)).start();

    }

    public void datePicker(final int tag) {


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(AttendenceActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text

                        if(tag==1) {
                            fromdate.setText(dayOfMonth+ " "+months[(monthOfYear)]+","+ String.valueOf(year).substring(2));
                            //System.out.println("to date : "+dayOfMonth + "-" + months[(monthOfYear)] + "-" + year);
                            fromDate=dayOfMonth + "-" + months[(monthOfYear)] + "-" + year;

                            String myDate =fromDate +" 00:00:00";
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                            Date date = null;
                            try {
                                date = sdf.parse(myDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                //System.out.println("bug in the  simple date format >>"+e.toString());
                            }

                            millis = date.getTime();
                            //System.out.println("here is millis baby : "+millis);

                            if(todate != null){
                                loading_error_retry.setVisibility(View.VISIBLE);
                                attendence_report_display.setVisibility(View.GONE);

                                new Thread(new Worker(getApplicationContext(), "generate_cookie", sd, after_gotCookies)).start();
                            }

                        }else if(tag==2){
                            todate.setText(dayOfMonth + " " + months[(monthOfYear)]+","+ String.valueOf(year).substring(2));
                            toDate = dayOfMonth + "-" + months[(monthOfYear)] + "-" + year;
                            //System.out.println("to date : " + dayOfMonth + "-" + months[(monthOfYear)] + "-" + year);

                            loading_error_retry.setVisibility(View.VISIBLE);
                            attendence_report_display.setVisibility(View.GONE);


                            new Thread(new Worker(getApplicationContext(), "generate_cookie", sd, after_gotCookies)).start();

                        } else{
                            Toast.makeText(getApplicationContext(),"unkwown tag",Toast.LENGTH_LONG).show();
                        }


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        if(tag==2) {
            if(millis==0) {
                String myDate = fromDate + " 00:00:00";
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(myDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                    //System.out.println("bug in the  simple date format " + e.toString());
                }


                millis = date != null ? date.getTime() : 0;
            }
            datePickerDialog.getDatePicker().setMinDate(millis);
        }

        datePickerDialog.show();
    }

    public void RetryTask(View view) {
        cookie_generated=false;
        logged_in=false;
        progressBar.setVisibility(View.VISIBLE);
        error_retry.setVisibility(View.GONE);
        new Thread(new Worker(getApplicationContext(), "generate_cookie", sd, after_gotCookies)).start();
    }

}