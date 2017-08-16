package com.example.riteshagrawal.sstc_edrp;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


public class attend_shower extends AppCompatActivity {
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
        final TextView sem=(TextView)findViewById(R.id.roll_no);
        final  TextView sec =(TextView)findViewById(R.id.sec);

        final TextView rollno=(TextView)findViewById(R.id.roll_no);
        final LinearLayout maindisplay=(LinearLayout) findViewById(R.id.maindisplay);
         final LinearLayout loading=(LinearLayout) findViewById(R.id.loading);
        tabLayout = (TabLayout) findViewById(R.id.sTabLayout);
        tabLayout.setupWithViewPager(simpleViewPager);


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
                            rollno.setText(list.get(2).getValue());
                            branch.setText(list.get(6).getValue());
                            sem.setText(list.get(7).getValue());
                            sec.setText(list.get(8).getValue());
                            name.setText(StudentName);
                        }catch (Exception e){
                            e.fillInStackTrace();
                            System.out.println("here is the error :"+e.toString());
                        }
                    }



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
}
