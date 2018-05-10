package com.example.qq1296821114.time_and_money.Presenter.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qq1296821114.time_and_money.Adapter.Money_ListViewAdapter;
import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.Money;
import com.example.qq1296821114.time_and_money.Model.Money_Day;
import com.example.qq1296821114.time_and_money.Presenter.Activity.Editor_Activity;
import com.example.qq1296821114.time_and_money.R;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * 金额表格显示的碎片
 * Created by 12968 on 2018/4/5.
 */

public class Money_Fragment_show extends Fragment {
    private TextView textView;

    private Money_ListViewAdapter money_listViewAdapter;
    private ListView listView;
    private ArrayList<Money> datalist = new ArrayList<>();
    private ArrayList<Money_Day> daylist = new ArrayList<Money_Day>();
    private MyDB myDB;
    private Map<String, Integer> hashMap = new HashMap();

    private Comparator<Money_Day> comparator = new Comparator<Money_Day>() {

        @Override
        public int compare(Money_Day object, Money_Day b) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(b.getDate(1).getYear(), b.getDate(1).getMonth(), b.getDate(1).getDay(),
                    b.getDate(1).getHour(), b.getDate(1).getMinuter(), b.getDate(1).getSecond());
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(object.getDate(1).getYear(), object.getDate(1).getMonth(), object.getDate(1).getDay(),
                    object.getDate(1).getHour(), object.getDate(1).getMinuter(), object.getDate(1).getSecond());
            return calendar.compareTo(calendar2);
        }
    };

    public Money_Fragment_show() {
        myDB = MyDB.getMyDB(getActivity());
        datalist.addAll(myDB.loadMoney_Day());

        for (int i = 0, cnt = 0; i < datalist.size(); i++) {
            if (hashMap.get(datalist.get(i).getDate()) != null) {
                int position = hashMap.get(datalist.get(i).getDate());
                daylist.get(position).arrayList.add(datalist.get(i));
            } else {
                hashMap.put(datalist.get(i).getDate(), cnt++);
                daylist.add(new Money_Day(datalist.get(i).getDate(1)));

                int position = hashMap.get(datalist.get(i).getDate());
                daylist.get(position).arrayList.add(datalist.get(i));
            }
        }

    }


    //在构造方法中调用，获取试图
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout._money_manger_layout, container, false);

        listView = view.findViewById(R.id._money_listview);
        money_listViewAdapter = new Money_ListViewAdapter(getContext(), R.layout._money_manger_item, daylist) {
            public void btn_del(Money money) {
                myDB.delMoney(money);
                Log.e(TAG, "btn_del: ");
                SwipeMenuLayout.getViewCache().smoothClose();

                datalist.clear();
                hashMap.clear();
                daylist.clear();

                datalist.addAll(myDB.loadMoney_Day());
                for (int i = 0, cnt = 0; i < datalist.size(); i++) {
                    if (hashMap.get(datalist.get(i).getDate()) != null) {
                        int position = hashMap.get(datalist.get(i).getDate());
                        daylist.get(position).arrayList.add(datalist.get(i));
                    } else {
                        hashMap.put(datalist.get(i).getDate(), cnt++);
                        daylist.add(new Money_Day(datalist.get(i).getDate(1)));

                        int position = hashMap.get(datalist.get(i).getDate());
                        daylist.get(position).arrayList.add(datalist.get(i));
                    }
                }

                Collections.sort(daylist,comparator);
                money_listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void btn_edit(Money money) {
                Intent intent = new Intent(getContext(), Editor_Activity.class);//SecondActivity.class是需要写出对应的Java文件来进行跳转Bundle bundle = new Bundle();
                Bundle bundle = new Bundle();
                bundle.putInt("id", money.getId());
                bundle.putDouble("money", money.getMoney());
                bundle.putInt("imageid", money.getImageId());
                bundle.putString("type", money.getType());
                bundle.putString("date", money.getDate(1).toString());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        };
        listView.setAdapter(money_listViewAdapter);

        return view;
    }

    //    public int cmp(Money_Day object,Money_Day b) {
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(b.getDate(1).getYear(), b.getDate(1).getMonth(), b.date.getDay(), b.date.getHour(), b.date.getMinuter(), b.date.getSecond());
//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.set(object.date.getYear(), object.date.getMonth(), object.date.getDay(), object.date.getHour()
//                , object.date.getMinuter(), object.date.getSecond());
//        return calendar.compareTo(calendar2);
//    }
    @Override
    public void onResume() {
        super.onResume();
        datalist.clear();
        hashMap.clear();
        daylist.clear();
        datalist.addAll(myDB.loadMoney_Day());

        for (int i = 0, cnt = 0; i < datalist.size(); i++) {
            if (hashMap.get(datalist.get(i).getDate()) != null) {
                int position = hashMap.get(datalist.get(i).getDate());
                daylist.get(position).arrayList.add(datalist.get(i));
            } else {
                hashMap.put(datalist.get(i).getDate(), cnt++);
                daylist.add(new Money_Day(datalist.get(i).getDate(1)));

                int position = hashMap.get(datalist.get(i).getDate());
                daylist.get(position).arrayList.add(datalist.get(i));
            }
        }
        Collections.sort(daylist,comparator);
        money_listViewAdapter.notifyDataSetChanged();
    }
}
