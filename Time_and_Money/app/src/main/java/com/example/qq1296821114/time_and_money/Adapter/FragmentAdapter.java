package com.example.qq1296821114.time_and_money.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.qq1296821114.time_and_money.Presenter.Fragment.Money_AddFragment;
import com.example.qq1296821114.time_and_money.Presenter.Fragment.Money_Fragment;
import com.example.qq1296821114.time_and_money.Presenter.Fragment.Money_Fragment_show;
import com.example.qq1296821114.time_and_money.Presenter.Fragment.My_Fragment;
import com.example.qq1296821114.time_and_money.Presenter.Fragment.Plan_Fragment;
import com.example.qq1296821114.time_and_money.Presenter.Fragment.Time_AddFragment;
import com.example.qq1296821114.time_and_money.Presenter.Fragment.Time_Fragment;

/**
 * 管理碎片，显示那一个碎片，提供刷新。
 * Created by 12968 on 2018/3/31.
 */

public class FragmentAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 7; //总页数
    private Plan_Fragment plan_fragment;
    private My_Fragment my_fragment;
    private Time_Fragment time_fragment;
    private Money_Fragment money_fragment;
    private Money_Fragment_show money_fragment_show;
    private Money_AddFragment money_addFragment;
    private Time_AddFragment time_addFragment;
    private Context context;


    public FragmentAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context=context;
        plan_fragment = new Plan_Fragment();
        my_fragment = new My_Fragment();
        time_fragment = new Time_Fragment();
        money_fragment = new Money_Fragment();
        money_fragment_show = new Money_Fragment_show();
        money_addFragment=new Money_AddFragment();
        time_addFragment=new Time_AddFragment();
    }

    //在获取碎片的时候调用，应该是就调用一次。
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = money_fragment;
                break;
            case 1:
                fragment = my_fragment;
                break;
            case 2:
                fragment = plan_fragment;
                break;
            case 3:
                fragment = time_fragment;
                break;
            case 4:
                fragment = money_fragment_show;
                break;
            case 5:
                fragment=money_addFragment;
                break;
            case 6:
                fragment=time_addFragment;
                break;
            default:
                break;
        }
        return fragment;
    }


    //父类的获取碎片数量
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }



}
