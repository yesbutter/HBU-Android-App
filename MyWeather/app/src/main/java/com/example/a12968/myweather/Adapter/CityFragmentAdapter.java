package com.example.a12968.myweather.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.example.a12968.myweather.Bean.CityFragment;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by t-lidashao on 18-2-26.
 */

public class CityFragmentAdapter extends FragmentStatePagerAdapter {

    private ArrayList<CityFragment> cityFragments;
    private FragmentManager fragmentManager;

    public CityFragmentAdapter(FragmentManager fm, ArrayList<CityFragment> cityFragments) {
        super(fm);
        this.fragmentManager = fm;
        this.cityFragments = cityFragments;
    }


    public void setCityFragments(ArrayList<CityFragment> cityFragments) {
        if (this.cityFragments != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (Fragment fragment : cityFragments) {
                fragmentTransaction.remove(fragment);
            }
            fragmentTransaction.commit();
            fragmentTransaction = null;
            fragmentManager.executePendingTransactions();
        }
        this.cityFragments = cityFragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return cityFragments.get(position);
    }

    @Override
    public int getCount() {
        return cityFragments.size();
    }

    public void Fragmentsort() {
        Collections.sort(CityFragment.stringItems);
    }

}
