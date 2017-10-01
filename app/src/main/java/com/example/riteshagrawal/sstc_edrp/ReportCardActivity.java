package com.example.riteshagrawal.sstc_edrp;


import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import android.widget.FrameLayout;





public class ReportCardActivity extends baseactivity {
    FrameLayout dynamicContent;
    static int tabindex=-1;
    PagerTestReportAdapter adapter;
    ViewPager simpleViewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        navigation.getMenu().getItem(1).setChecked(true);
        dynamicContent = (FrameLayout) findViewById(R.id.content);
        View wizard = getLayoutInflater().inflate(R.layout.activity_reportcard, null);
        dynamicContent.addView(wizard);

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
        tabLayout.getTabAt(0).setText("Monday Test I");
        tabLayout.getTabAt(1).setText("CT I");
        tabLayout.getTabAt(2).setText("Monday Test II");
        tabLayout.getTabAt(3).setText("CT II");
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
    }


    public void RetryTask(View view) {
        recreate();
    }

}