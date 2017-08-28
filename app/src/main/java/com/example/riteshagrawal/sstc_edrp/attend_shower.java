package com.example.riteshagrawal.sstc_edrp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
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

    long millis= (long) 1501732200000f;
    Handler handler,handler2,log_in_handler;
    SharedPreferences sd=MainActivity.sd;
    static int tabindex=-1;
    static TabLayout tabLayout;
    static String attend_val="";
    static TabLayout.Tab secondTab;
    PagerAdapter adapter;
    ViewPager simpleViewPager;
    static String StudentName ="";
    static ArrayList<key_val> list = new ArrayList<>();
   static String sem_start_date="";
    String todays_date="";
    int flag =0;

    ArrayList<key_val> saver_list =new ArrayList();

    static ArrayList<attend_info_class> datalist = new ArrayList<>();
     DatePickerDialog datePickerDialog;
   static  TextView fromdate,todate;
     LinearLayout maindisplay;
     LinearLayout loading,error_disp;
     static String Total_lectures="";
     static String Attended_lectures="";

    String months[] = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
    String monthsD[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    static String fromDate="",toDate="";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.back,menu);
        MenuItem logout =menu.findItem(R.id.logout);

         logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem menuItem) {
                 sd.edit().putString("loginParams", "").apply();
                 Intent i = new Intent( attend_shower.this,MainActivity.class);
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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attend_shower);

        final TextView name =(TextView)findViewById(R.id.name);
        final TextView branch=(TextView)findViewById(R.id.branch);
        final TextView sem=(TextView)findViewById(R.id.sem);
        final  TextView sec =(TextView)findViewById(R.id.sec);
        final TextView rollno=(TextView)findViewById(R.id.roll_no);
        final TextView batch=(TextView)findViewById(R.id.batch);
        maindisplay = (LinearLayout) findViewById(R.id.maindisplay);
        loading = (LinearLayout) findViewById(R.id.loading);
        error_disp = (LinearLayout) findViewById(R.id.error_disp);
        final TextView error_msg=(TextView)findViewById(R.id.error_msg);

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy ");
        Date date = new Date();
        todays_date=dateFormat.format(date);
        System.out.println("here is todays date :"+dateFormat.format(date));



        fromdate = (TextView) findViewById(R.id .fromDate);
        // perform click event on edit text
        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker(1);
            }
        });

        todate = (TextView) findViewById(R.id .toDate);
        // perform click event on edit text
        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePicker(2);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.sTabLayout);
         tabLayout.setupWithViewPager(simpleViewPager);


//        Toast.makeText(attend_shower.this,"Yehh "+data.getResult()+" % Attendence",Toast.LENGTH_LONG).show();


        TabLayout.Tab firstTab;
        firstTab = tabLayout.newTab();

        tabLayout.addTab(firstTab);
        secondTab = tabLayout.newTab();

        tabLayout.addTab(secondTab);

        simpleViewPager = (ViewPager) findViewById(R.id.simpleViewPager);

        handler2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("under pre dnld handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("error")){
                    System.out.println("here is error msg :"+data.getErrorMsg());

                    Intent i = new Intent(attend_shower.this,MainActivity.class);
                    i.putExtra("error_msg",data.getErrorMsg());
                    startActivity(i);
                    attend_shower.this.finish();
 //                   loading.setVisibility(View.GONE);
//                    error_disp.setVisibility(View.VISIBLE);
//                    error_msg.setText(data.getErrorMsg());
                }else{
                    loading.setVisibility(View.GONE);
                    maindisplay.setVisibility(View.VISIBLE);

                    if(list != null){
                        try {
                            key_val obj = new key_val(
                                    sd.getString("c_uname", ""),
                                    sd.getString("c_pass", ""),
                                    StudentName,
                                    list.get(6).getValue(),
                                    list.get(7).getValue(),
                                    list.get(8).getValue(),
                                    list.get(2).getValue(),
                                    list.get(3).getValue()
                            );

                            Thread t = new Thread(new Users_Data_Saver(sd, obj));
                            t.start();
                        }catch (Exception e){
                            e.fillInStackTrace();
                            System.out.println("saving user data error ");
                        }
                        try {
                            rollno.setText(""+list.get(2).getValue()+"");
                            batch.setText("Batch :"+list.get(3).getValue()+"");
                            branch.setText("Branch :"+list.get(6).getValue()+"");
                            sem.setText("Sem :"+list.get(7).getValue()+"");
                            sec.setText("Sec :"+list.get(8).getValue()+"");
                            name.setText(StudentName);
                            attend_val=data.getResult();
                        }catch (Exception e){
                            e.fillInStackTrace();
                            System.out.println("here is the error :"+e.toString());
                        }
                    }

                    adapter = new PagerAdapter
                            (getSupportFragmentManager(), tabLayout.getTabCount());
                    simpleViewPager.setAdapter(adapter);
                    tabLayout.getTabAt(0).setText("DashBoard ");
                    tabLayout.getTabAt(1).setText("Details");

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


                }
            }
        };



        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("under pre dnld handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("error")){
                    System.out.println("here is error msg :"+data.getErrorMsg());
                }else{

                    System.out.println("yeh got the cookie :"+sd.getString("cookie",""));
                    if(flag!=0){
                        new Thread( new Worker(attend_shower.this,"hey baby",sd,handler2)).start();
                    }else{
                        datePicker(3);
                    }

                }
            }
        };

        final Gson gson = new Gson();

        if(!sd.getString("Users_Data_Saver", "").equals("")) {
            String json1 = sd.getString("Users_Data_Saver", "");
            System.out.println("yeh user data reading ..........");

            UserDataSaverObject obj = gson.fromJson(json1, UserDataSaverObject.class);

            for(key_val item0:obj.getList()){
                if(item0.getUname().equals(sd.getString("c_uname","")) && item0.getPass().equals(sd.getString("c_pass",""))){
                    rollno.setText(item0.getRollno());
                    batch.setText("Batch :"+item0.getBatch()+"");
                    branch.setText("Branch :"+item0.getBranch()+"");
                    sem.setText("Sem :"+item0.getSem()+"");
                    sec.setText("Sec :"+item0.getSec()+"");
                    name.setText(item0.getName());
                    sem_start_date= item0.getSem_start_date();
                    //System.out.println("element added :"+item);
                    flag=1;
                    break;
                }
            }

            if(flag==1){
                fromDate=sem_start_date;
                fromdate.setText(sem_start_date);
                toDate=todays_date;
                todate.setText(todays_date);


            }else if(flag==0){

              //  datePicker(3);
            }


        }else if(sd.getString("Users_Data_Saver", "").equals("")){
            System.out.println("else if part ........user data daver not created yet....");
        //       datePicker(4);

//                    fromDate="01-AUG-2017";
//                    toDate="22-AUG-2017";
//                    fromdate.setText("01-AUG-2017");
//                    todate.setText("23-AUG-2017");

        }

//





        key_pass_generator key_pass_generator= new key_pass_generator(handler,sd);
        key_pass_generator.start();
    }



    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    public void datePicker(final int tag) {


     //   System.out.println("hello its working :");
    //    Toast.makeText(getApplicationContext(),"tag :"+tag,Toast.LENGTH_LONG).show();

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
                            fromdate.setText(dayOfMonth+ " "+monthsD[(monthOfYear)]);
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
                                key_pass_generator key_pass_generator= new key_pass_generator(handler,sd);
                                key_pass_generator.start();
                            }

                        }else if(tag==2){
                            todate.setText(dayOfMonth + " " + monthsD[(monthOfYear)]);
                            toDate = dayOfMonth + "-" + months[(monthOfYear)] + "-" + year;
                            System.out.println("to date : " + dayOfMonth + "-" + months[(monthOfYear)] + "-" + year);

                            loading.setVisibility(View.VISIBLE);
                            maindisplay.setVisibility(View.GONE);

                            key_pass_generator key_pass_generator= new key_pass_generator(handler,sd);
                            key_pass_generator.start();
                        }else if(tag==3 || tag==4){
                            fromdate.setText(dayOfMonth+ " "+monthsD[(monthOfYear)]);
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

                            todate.setText(todays_date);
                            toDate = todays_date;
                            System.out.println("to date : " +todays_date);

                            loading.setVisibility(View.VISIBLE);
                            maindisplay.setVisibility(View.GONE);


                                key_val obj = new key_val(
                                        sd.getString("c_uname", ""),
                                        sd.getString("c_pass", ""),
                                        fromDate
                                );

                                Thread t = new Thread(new Users_Data_Saver(sd, obj));
                                t.start();


//                            key_pass_generator key_pass_generator= new key_pass_generator(handler,sd);
//                            key_pass_generator.start();

                            new Thread( new Worker(attend_shower.this,"hey baby",sd,handler2)).start();
                        }else{
                            Toast.makeText(getApplicationContext(),"unkwown tag",Toast.LENGTH_LONG).show();
                        }


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        if(tag==2) {
            datePickerDialog.getDatePicker().setMinDate(millis);
        }

        datePickerDialog.show();
    }



}
