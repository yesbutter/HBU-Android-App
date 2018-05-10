package com.example.qq1296821114.time_and_money.Presenter.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.qq1296821114.time_and_money.Adapter.TimeAdapter;
import com.example.qq1296821114.time_and_money.Adapter.TimeCardAdapter;
import com.example.qq1296821114.time_and_money.Card.SwipeFlingAdapterView;
import com.example.qq1296821114.time_and_money.Card.SwipeTimeAdapterView;
import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.Time;
import com.example.qq1296821114.time_and_money.R;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.ContentValues.TAG;

/**
 * 时间碎片
 * Created by 12968 on 2018/4/1.
 */

public class    Time_Fragment extends Fragment {
    private TimeAdapter timeAdapter;
    private ListView listView;
    private ArrayList<Time> datalist = new ArrayList<>();
    private MyDB myDB;
    private ImageView imageView;

    private TimeCardAdapter timeCardAdapter;
    private SwipeTimeAdapterView timeAdapterView;

    public Time_Fragment() {
        myDB = MyDB.getMyDB(getActivity());

        datalist.addAll(myDB.loadTime());
        TimeSort();
    }


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout._time_layout, container, false);
        listView = view.findViewById(R.id._time_list_view);
        timeAdapterView =view.findViewById(R.id._time_frame);
        timeCardAdapter = new TimeCardAdapter(getContext(), R.layout._time_card_item,datalist);
        imageView=view.findViewById(R.id._time_add_button);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof Time_add_Click) {
                    ((Time_add_Click) getActivity()).OneBtnClick2();
                }
            }
        });


        timeAdapter = new TimeAdapter(getContext(), R.layout._time_item, datalist);
        listView.setAdapter(timeAdapter);
        timeAdapterView.setAdapter(timeCardAdapter);

        timeAdapterView.setFlingListener(new SwipeTimeAdapterView.onFlingListener() {

            @Override
            public void removeFirstObjectInAdapter() {
                Time time = datalist.get(0);
                datalist.remove(0);
                datalist.add(time);
                timeCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Log.e(TAG, "onRightCardExit: "+"right" );
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                timeCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }
        });
        timeCardAdapter.notifyDataSetChanged();
        return view;
    }

    public interface Time_add_Click {
        void OneBtnClick2();
    }

    @Override
    public void onResume() {
        super.onResume();
        datalist.clear();
        datalist.addAll(myDB.loadTime());
        TimeSort();
        timeCardAdapter.notifyDataSetChanged();
        timeAdapter.notifyDataSetChanged();
    }

    public void TimeSort()
    {
        Collections.sort(datalist);
    }
}
