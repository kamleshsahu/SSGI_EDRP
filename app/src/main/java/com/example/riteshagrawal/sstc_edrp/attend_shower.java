package com.example.riteshagrawal.sstc_edrp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class attend_shower extends AppCompatActivity {
    final static Gson gson = new Gson();
   static long millis= 0;
    Handler logout_handler,after_login,after_gotCookies,after_gotUsersInfo,after_fetchAttendence;
    static SharedPreferences sd=MainActivity.sd;
    static int tabindex=-1;
    static TabLayout tabLayout;
    static String attend_val="";
    static TabLayout.Tab secondTab;
    PagerAdapter adapter;
    ViewPager simpleViewPager;
    static String StudentName ="";
    static ArrayList<Users_info_Object> list = new ArrayList<>();
   static String sem_start_date="";
   static String todays_date="", todays_Date="";


   static int flag =0;
    static Boolean logged_in=false;
    static Boolean cookie_generated=false;
    TextView error_msg_disp;


    ArrayList<Users_info_Object> saver_list =new ArrayList();

    static ArrayList<attend_info_class> datalist = new ArrayList<>();
     DatePickerDialog datePickerDialog;
   static  TextView fromdate,todate;
     LinearLayout maindisplay;
     LinearLayout loading,error_disp;
     static String Total_lectures="";
     static String Attended_lectures="";
    Button retryButton;
    ProgressBar progressBar,progressBar_first;

    LinearLayout firstlogin;



   static String months[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    static String fromDate="",toDate="";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menuitems,menu);
        MenuItem logout =menu.findItem(R.id.logout);
        MenuItem change_sem_startdate =menu.findItem(R.id.change_sem_startdate);
        MenuItem rc =menu.findItem(R.id.rc);
         logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem menuItem) {
                 sd.edit().putString("loginParams", "").apply();
                 new Thread( new Worker(attend_shower.this,"logout",sd,logout_handler)).start();
                 return false;
             }
         });

        change_sem_startdate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent( attend_shower.this,sem_startday_setter.class);
                startActivity(i);
                attend_shower.this.finish();

                return false;
            }
        });

        rc.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent( attend_shower.this,testmarks.class);
                startActivity(i);
                attend_shower.this.finish();
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
        setContentView(R.layout.popup_float);


//floating button popup menu
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(attend_shower.this,fab);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    public boolean onMenuItemClick(MenuItem item){

                        switch (item.getItemId()) {
                            case R.id.one:
                                Toast.makeText(attend_shower.this, "one", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.two:
                                Toast.makeText(attend_shower.this, "two", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.rc:
                                Intent i = new Intent( attend_shower.this,testmarks.class);
                                startActivity(i);
                                attend_shower.this.finish();
                                return true;
                            default:
                                return true;
                        }
                    }
                });
                popup.show();
            }
        });



        final TextView name =(TextView)findViewById(R.id.name);
        final TextView branch=(TextView)findViewById(R.id.branch);
        final TextView sem=(TextView)findViewById(R.id.sem);
        final  TextView sec =(TextView)findViewById(R.id.sec);
        final TextView rollno=(TextView)findViewById(R.id.roll_no);
        final TextView batch=(TextView)findViewById(R.id.batch);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        final LinearLayout main_layout=(LinearLayout)findViewById(R.id.mainlayout);
        final LinearLayout first_login=(LinearLayout)findViewById(R.id.first_login);


        retryButton =(Button)findViewById(R.id.retryButton);
        maindisplay = (LinearLayout) findViewById(R.id.maindisplay);
        loading = (LinearLayout) findViewById(R.id.loading);

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
        System.out.println("here is todays date :"+dateFormat.format(date));



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

        logout_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("attend_shower,under logout handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("success")){

                    Intent i = new Intent( attend_shower.this,MainActivity.class);
                    startActivity(i);
                    cookie_generated=false;
                    logged_in=false;

                    sem_start_date="";


                    attend_shower.this.finish();


                }else{
                    maindisplay.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    error_msg_disp.setVisibility(View.VISIBLE);
                    error_msg_disp.setText(data.getErrorMsg());
                    retryButton.setVisibility(View.VISIBLE);

                }
            }
        };

        after_fetchAttendence= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("attend_shower,after_fetchAttendence handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                first_login.setVisibility(View.GONE);
                main_layout.setVisibility(View.VISIBLE);
                    if(data.getResult().equals("success")){
                    loading.setVisibility(View.GONE);
                    maindisplay.setVisibility(View.VISIBLE);

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
                            System.out.println("saving user data error ");
                        maindisplay.setVisibility(View.GONE);
                        loading.setVisibility(View.VISIBLE);

                        progressBar.setVisibility(View.GONE);
                        error_msg_disp.setVisibility(View.VISIBLE);
                        error_msg_disp.setText("saving user data error :"+e.toString());
                        retryButton.setVisibility(View.VISIBLE);
                        }
                        try {
                            rollno.setText(""+list.get(2).getValue()+"");
                            batch.setText("Batch :"+list.get(3).getValue()+"");
                            branch.setText("Branch :"+list.get(6).getValue()+"");
                            sem.setText("Sem :"+list.get(7).getValue()+"");
                            sec.setText("Sec :"+list.get(8).getValue()+"");


                        }catch (Exception e){
                            e.fillInStackTrace();
                            System.out.println("here is the error :"+e.toString());
                            maindisplay.setVisibility(View.GONE);
                            loading.setVisibility(View.VISIBLE);

                            progressBar.setVisibility(View.GONE);
                            error_msg_disp.setVisibility(View.VISIBLE);
                            error_msg_disp.setText("putting value in display error :"+e.toString());
                            retryButton.setVisibility(View.VISIBLE);
                        }
                    }

                        name.setText(StudentName);
                        attend_val=data.getMsg();


                    simpleViewPager.setCurrentItem(0);
                    simpleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

                    tabindex=0;

                    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {

                            //System.out.println("selected tab :"+tab.getPosition());
                            tabindex=tab.getPosition();
                            simpleViewPager.setCurrentItem(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {
                            //System.out.println("Reselected tab :"+tab.getPosition());
                            tabindex=tab.getPosition();
                            simpleViewPager.setCurrentItem(tab.getPosition());
                        }
                    });


                }else if(data.getResult().equals("error")){
                    System.out.println("here is error msg :"+data.getErrorMsg());
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
                System.out.println("here is result :"+data.getResult());
                System.out.println("here is msg : "+data.getMsg());
                if(data.getResult().equals("success")){
                   Worker w=new Worker(getApplicationContext(), "fetch_attendence", sd, after_fetchAttendence);
                    w.setUrlParams(data.getMsg());
                    new Thread(w).start();

                }else{

                    System.out.println("here is error msg :"+data.getErrorMsg());
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
                if(data.getResult().equals("success")){


                    if(!sem_start_date.equals("")) {
//                        Toast.makeText(attend_shower.this, "not have user_info", Toast.LENGTH_SHORT).show();
                        System.out.println("after login handler,Not having user_info url");
                        new Thread(new Worker(getApplicationContext(),"fetch_users_info",sd,after_gotUsersInfo)).start();
                    }else{
                        Intent i = new Intent( attend_shower.this,sem_startday_setter.class);
                        startActivity(i);
                        attend_shower.this.finish();
                    }

                }else{
//                    Toast.makeText(getApplicationContext()," Error ",Toast.LENGTH_LONG).show();
                    System.out.println("after got cookies error :"+data.getResult());
                    Intent i = new Intent(attend_shower.this,MainActivity.class);
                    i.putExtra("error_msg",data.getErrorMsg());
                    startActivity(i);
                    attend_shower.this.finish();
                }
            }
        };

        after_gotCookies= new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                System.out.println("after_gotCookies handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("success")){

                        if(logged_in) {
 //                       Toast.makeText(attend_shower.this, "logged in,no usr infos", Toast.LENGTH_SHORT).show();
                        System.out.println("after gotCookies,logged in,no usr infos");
                        new Thread(new Worker(getApplicationContext(),"fetch_users_info",sd,after_gotUsersInfo)).start();
                    }else {
 //                       Toast.makeText(attend_shower.this, "not logged in", Toast.LENGTH_SHORT).show();
                        System.out.println("after gotCookies,not logged in");
                        new Thread(new Worker(getApplicationContext(), "login", sd, after_login)).start();
                    }
                }else{
                    first_login.setVisibility(View.GONE);
                    main_layout.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    error_msg_disp.setVisibility(View.VISIBLE);
                    error_msg_disp.setText(data.getErrorMsg());
                    retryButton.setVisibility(View.VISIBLE);
                }
            }
        };





        if(!sd.getString("Users_Data_Saver", "").equals("")) {
            String json1 = sd.getString("Users_Data_Saver", "");
            System.out.println("yeh user data reading ..........");

            UserDataSaverObject obj = gson.fromJson(json1, UserDataSaverObject.class);

            for(Users_info_Object item0:obj.getList()){
                if(item0.getUname().equals(sd.getString("c_uname","")) && item0.getPass().equals(sd.getString("c_pass",""))){

                    if(item0.getRollno() != null) {
                        rollno.setText(item0.getRollno());
                        batch.setText("Batch :" + item0.getBatch() + "");
                        branch.setText("Branch :" + item0.getBranch() + "");
                        sem.setText("Sem :" + item0.getSem() + "");
                        sec.setText("Sec :" + item0.getSec() + "");
                        name.setText(item0.getName());
                        sem_start_date = item0.getSem_start_date();

                        flag = 1;
                        break;
                    }
                }
            }

            if(flag==1){
                fromDate=sem_start_date;
                fromdate.setText(sem_start_date.split("-")[0]+" "+sem_start_date.split("-")[1]+","+sem_start_date.split("-")[2].substring(2));
                toDate=todays_date;
                todate.setText(todays_Date);

            }else if(flag==0){
                main_layout.setVisibility(View.GONE);
                first_login.setVisibility(View.VISIBLE);
                if(getIntent().getBooleanExtra("sem_startday_set",false)){
                    fromDate=sem_start_date;
                    fromdate.setText(sem_start_date.split("-")[0]+" "+sem_start_date.split("-")[1]+","+sem_start_date.split("-")[2].substring(2));
                    toDate=todays_date;
                    todate.setText(todays_Date);


                }

            }


        }else if(sd.getString("Users_Data_Saver", "").equals("")){
            System.out.println("else if part ........user data daver not created yet....");

            main_layout.setVisibility(View.GONE);
            first_login.setVisibility(View.VISIBLE);

            if(getIntent().getBooleanExtra("sem_startday_set",false)){
                fromDate=sem_start_date;
                fromdate.setText(sem_start_date.split("-")[0]+" "+sem_start_date.split("-")[1]+","+sem_start_date.split("-")[2].substring(2));
                toDate=todays_date;
                todate.setText(todays_Date);

            }


        }


        new Thread(new Worker(getApplicationContext(), "generate_cookie", sd, after_gotCookies)).start();

    }



    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    public void datePicker(final int tag) {


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(attend_shower.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text

                        if(tag==1) {
                            fromdate.setText(dayOfMonth+ " "+months[(monthOfYear)]+","+ String.valueOf(year).substring(2));
                            System.out.println("to date : "+dayOfMonth + "-" + months[(monthOfYear)] + "-" + year);
                            fromDate=dayOfMonth + "-" + months[(monthOfYear)] + "-" + year;

                            String myDate =fromDate +" 00:00:00";
                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                            Date date = null;
                            try {
                                date = sdf.parse(myDate);
                            } catch (ParseException e) {
                                e.printStackTrace();
                                System.out.println("bug in the  simple date format >>"+e.toString());
                            }

                            millis = date.getTime();
                            System.out.println("here is millis baby : "+millis);

                            if(todate != null){
                                loading.setVisibility(View.VISIBLE);
                                maindisplay.setVisibility(View.GONE);

                                new Thread(new Worker(getApplicationContext(), "generate_cookie", sd, after_gotCookies)).start();
                            }

                        }else if(tag==2){
                            todate.setText(dayOfMonth + " " + months[(monthOfYear)]+","+ String.valueOf(year).substring(2));
                            toDate = dayOfMonth + "-" + months[(monthOfYear)] + "-" + year;
                            System.out.println("to date : " + dayOfMonth + "-" + months[(monthOfYear)] + "-" + year);

                            loading.setVisibility(View.VISIBLE);
                            maindisplay.setVisibility(View.GONE);


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
                    System.out.println("bug in the  simple date format " + e.toString());
                }

                millis = date.getTime();
            }
            datePickerDialog.getDatePicker().setMinDate(millis);
        }

        datePickerDialog.show();
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
