package com.example.riteshagrawal.sstc_edrp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;


    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
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
                Dashboard_F tab1 = new Dashboard_F();

                return tab1;
            case 1:
                Details_F tab2 = new Details_F();
                return tab2;

            default:
                return null;
        }
    }


 
    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}