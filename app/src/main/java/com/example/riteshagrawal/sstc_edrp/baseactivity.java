package com.example.riteshagrawal.sstc_edrp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;


public class baseactivity extends AppCompatActivity {
    static SharedPreferences sd;
    Handler logout_handler;
    static Boolean logged_in=false;
    static Boolean cookie_generated=false;
    final static Gson gson = new Gson();
    static String sem_start_date="";
    static int flag =0;
   static FrameLayout content;


  static  BottomNavigationView navigation;
    static String StudentName ="";
    static Context appContext;
   static TextView name , branch , sem , sec , rollno , batch ;
    Intent in;
    static ArrayList<Users_info_Object> list = new ArrayList<>();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:


                    in=new Intent(getApplicationContext(),AttendenceActivity.class);
                    startActivity(in);
                    finish();
                    overridePendingTransition(0, 0);

                    return true;
                case R.id.navigation_dashboard:

                    in = new Intent(getApplicationContext(), ReportCardActivity.class);
                    startActivity(in);
                    finish();
                    overridePendingTransition(0, 0);

                    return true;

            }
            return false;
        }

    };

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
                new Thread( new Worker(baseactivity.this,"logout",sd,logout_handler)).start();
                return false;
            }
        });

        change_sem_startdate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent i = new Intent( getBaseContext(),sem_startday_setter.class);
                startActivity(i);
                baseactivity.this.finish();

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

        MenuItem share =menu.findItem(R.id.share);
        share.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "https://play.google.com/store/apps/details?id=com.SSTC.EDRP";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Share App's PlayStore link"));

                return true;
            }

        });
        MenuItem rate = menu.findItem(R.id.rate);
        rate.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.SSTC.EDRP"));
                startActivity(intent);

                return true;
            }
        });
        MenuItem about_us = menu.findItem(R.id.about_us);
        about_us.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent o=new Intent(getBaseContext(),about_us.class);
                startActivity(o);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseactivity);
   
        name = (TextView)findViewById(R.id.name);
       
        branch = (TextView)findViewById(R.id.branch);
     
        sem = (TextView)findViewById(R.id.sem);
      
        sec = (TextView)findViewById(R.id.sec);
       
        rollno = (TextView)findViewById(R.id.roll_no);
       
        batch = (TextView)findViewById(R.id.batch);

        //  main_layout = (LinearLayout)findViewById(R.id.mainlayout);
        content=(FrameLayout)findViewById(R.id.content);


        sd = this.getSharedPreferences("com.example.riteshagrawal.sstc_edrp", Context.MODE_APPEND);
        appContext=getApplicationContext();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        logout_handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("attend_shower,under logout handler");
                customObject data = (customObject) msg.obj;
                System.out.println(data.getResult());
                if(data.getResult().equals("success")){

                    Intent i = new Intent( getApplicationContext(),MainActivity.class);
                    startActivity(i);
                    cookie_generated=false;
                    logged_in=false;
                    flag=0;
                    sem_start_date="";


                    baseactivity.this.finish();


                }else{
//                    maindisplay.setVisibility(View.GONE);
//                    loading.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);
//                    error_msg_disp.setVisibility(View.VISIBLE);
//                    error_msg_disp.setText(data.getErrorMsg());
//                    retryButton.setVisibility(View.VISIBLE);

                }
            }
        };

        if(!sd.getString("Users_Data_Saver", "").equals("")) {
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
                        sem_start_date = item0.getSem_start_date();

                        flag = 1;
                        break;
                    }
                }
            }
        }

    }


    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }
}
