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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;


public class attend_shower extends AppCompatActivity {

    long millis= (long) 1501732200000f;
    Handler handler,handler2;
    SharedPreferences sd=MainActivity.sd;
    static int tabindex=-1;
    static TabLayout tabLayout;
    static String attend_val="";
    static TabLayout.Tab secondTab;
    PagerAdapter adapter;
    ViewPager simpleViewPager;
    static String StudentName ="";
    static ArrayList<key_val> list = new ArrayList<>();
    static ArrayList<attend_info_class> datalist = new ArrayList<>();
    DatePickerDialog datePickerDialog;
    TextView fromdate=null,todate=null;
     LinearLayout maindisplay;
     LinearLayout loading;

    String months[] = {"JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"};
    String monthsD[] = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    static String fromDate="",toDate="";
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.back,menu);
        MenuItem item =menu.findItem(R.id.logout);

         item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem menuItem) {
                 sd.edit().putString("loginParams", "").apply();
                 Intent i = new Intent( attend_shower.this,MainActivity.class);
                 startActivity(i);
                 attend_shower.this.finish();
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


//
//        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy ");
//        Date date = new Date();
//        System.out.println("here is todays date :"+dateFormat.format(date));



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


        handler2 = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //System.out.println("under pre dnld handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("error")){
                    System.out.println("here is error msg :"+data.getErrorMsg());
                }else{
                    loading.setVisibility(View.GONE);
                    maindisplay.setVisibility(View.VISIBLE);

                    if(list != null){
                        try {
                            rollno.setText("Roll No:"+list.get(2).getValue()+"");
                            batch.setText("Batch :"+list.get(3).getValue()+"");
                            branch.setText("Branch :"+list.get(6).getValue()+"");
                            sem.setText("Sem :"+list.get(7).getValue()+"");
                            sec.setText("Sec :"+list.get(8).getValue()+"");
                            name.setText(StudentName);
                        }catch (Exception e){
                            e.fillInStackTrace();
                            System.out.println("here is the error :"+e.toString());
                        }
                    }

                    tabLayout = (TabLayout) findViewById(R.id.sTabLayout);
                    tabLayout.setupWithViewPager(simpleViewPager);


                    Toast.makeText(attend_shower.this,"Yehh "+data.getResult()+" % Attendence",Toast.LENGTH_LONG).show();
                    attend_val=data.getResult()+" %";

                    TabLayout.Tab firstTab;
                    firstTab = tabLayout.newTab();

                    tabLayout.addTab(firstTab);
                    secondTab = tabLayout.newTab();

                    tabLayout.addTab(secondTab);

                    simpleViewPager = (ViewPager) findViewById(R.id.simpleViewPager);
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
                    new Thread( new Worker(attend_shower.this,"hey baby",sd,handler2)).start();
                }
            }
        };



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
                        }else if(tag==2){
                            todate.setText(dayOfMonth + " " + monthsD[(monthOfYear)]);
                            toDate = dayOfMonth + "-" + months[(monthOfYear)] + "-" + year;
                            System.out.println("to date : " + dayOfMonth + "-" + months[(monthOfYear)] + "-" + year);

                            loading.setVisibility(View.VISIBLE);
                            maindisplay.setVisibility(View.GONE);

                            key_pass_generator key_pass_generator= new key_pass_generator(handler,sd);
                            key_pass_generator.start();
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
