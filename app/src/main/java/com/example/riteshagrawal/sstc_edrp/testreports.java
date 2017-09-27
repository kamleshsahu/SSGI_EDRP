package com.example.riteshagrawal.sstc_edrp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;

import static com.example.riteshagrawal.sstc_edrp.R.id.simpleViewPager;
import static com.example.riteshagrawal.sstc_edrp.attend_shower.tabLayout;


public class testreports extends AppCompatActivity{
    PagerTestReportAdapter adapter;
    ViewPager simpleViewPager;
    static SharedPreferences sd=MainActivity.sd;
    final static Gson gson = new Gson();
    static int tabindex=-1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testreports);
        final TextView name =(TextView)findViewById(R.id.name);
        final TextView branch=(TextView)findViewById(R.id.branch);
        final TextView sem=(TextView)findViewById(R.id.sem);
        final  TextView sec =(TextView)findViewById(R.id.sec);
        final TextView rollno=(TextView)findViewById(R.id.roll_no);
        final TextView batch=(TextView)findViewById(R.id.batch);
        tabLayout = (TabLayout) findViewById(R.id.sTabLayout);
        tabLayout.setupWithViewPager(simpleViewPager);
        TabLayout.Tab firstTab;

        firstTab = tabLayout.newTab();
        tabLayout.addTab(firstTab);
        TabLayout.Tab secondTab;
        secondTab = tabLayout.newTab();
        tabLayout.addTab(secondTab);

        TabLayout.Tab thirdTab;
        thirdTab = tabLayout.newTab();
        tabLayout.addTab(thirdTab);

        TabLayout.Tab fourthTab;
        fourthTab = tabLayout.newTab();
        tabLayout.addTab(fourthTab);
        simpleViewPager = (ViewPager) findViewById(R.id.simpleViewPager);

        adapter = new PagerTestReportAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        simpleViewPager.setAdapter(adapter);
        tabLayout.getTabAt(0).setText("MI");
        tabLayout.getTabAt(1).setText("CTI");
        tabLayout.getTabAt(2).setText("MII");
        tabLayout.getTabAt(3).setText("CTII");

        simpleViewPager.setCurrentItem(0);
        simpleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabindex=0;

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                System.out.println("selected tab :"+tab.getPosition());
                tabindex=tab.getPosition();
                simpleViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                System.out.println("Reselected tab :"+tab.getPosition());
                tabindex=tab.getPosition();
                simpleViewPager.setCurrentItem(tab.getPosition());
            }
        });

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

    }



}
