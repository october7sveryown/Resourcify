package com.example.yash.resourcify;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Yash on 5/2/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{
    final int PAGE_COUNT = 3;
    // Tab Titles

    Context context;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            // Open FragmentTab1.java
            case 0:
                first myfirst = new first();
                return myfirst;

            // Open FragmentTab2.java
            case 1:
                second mysecond=new second();
                return mysecond;
            // Open FragmentTab3.java
            case 2:
             //   FragmentTab3 fragmenttab3 = new FragmentTab3();
               // return fragmenttab3;
                third mythirf=new third();
                return mythirf;
        }
        return null;
    }


}
