package com.example.riteshagrawal.sstc_edrp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



public class attend_shower extends AppCompatActivity {
    Handler handler,handler2;
    SharedPreferences sd=MainActivity.sd;
    static int tabindex=-1;
    static TabLayout tabLayout;
    static String attend_val="";
    static TabLayout.Tab secondTab;
    PagerAdapter adapter;
    ViewPager simpleViewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attend_shower);

        TextView name =(TextView)findViewById(R.id.name);
        TextView branch=(TextView)findViewById(R.id.branch);
        TextView sem=(TextView)findViewById(R.id.roll_no);
        TextView sec =(TextView)findViewById(R.id.sec);
        TextView fromDate=(TextView)findViewById(R.id.fromDate);
        TextView toDate=(TextView)findViewById(R.id.toDate);
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
}
