package com.example.riteshagrawal.sstc_edrp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerTestReportAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public PagerTestReportAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "";
    }

    @Override
    public Fragment getItem(int position) {
 
        switch (position) {
            case 0:
                M1 tab1 = new M1();

                return tab1;
            case 1:
                CT1 tab2 = new CT1();

                return tab2;
            case 2:
                M2 tab3 = new M2();
                return tab3;
            case 3:
                CT2 tab4 = new CT2();
                return tab4;

            default:
                return null;
        }
    }


 
    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}