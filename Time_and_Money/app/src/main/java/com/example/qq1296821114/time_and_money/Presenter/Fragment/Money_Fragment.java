package com.example.qq1296821114.time_and_money.Presenter.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qq1296821114.time_and_money.Adapter.MoneyCardAdapter;
import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.Money;
import com.example.qq1296821114.time_and_money.R;
import com.example.qq1296821114.time_and_money.Card.SwipeFlingAdapterView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import static android.content.ContentValues.TAG;

/**
 * 卡片显示的碎片，利用了开源库，进行了修改。  https://github.com/Diolor/Swipecards
 * Created by 12968 on 2018/4/1.
 */

public class Money_Fragment extends Fragment {
    private ArrayList<Money> datalist;
    private MoneyCardAdapter moneyCardAdapter;
    private Button moneyadd;
    private MyDB myDB;
    private TextView text_page, _money_card_main;
    private int page = 1;

    private boolean flag = true;
    SwipeFlingAdapterView flingContainer;

    public Money_Fragment() {

        myDB = MyDB.getMyDB(getActivity());
        datalist = (ArrayList<Money>) myDB.loadMoney_Day();
        MoneyCardSort();

    }

    //在构造方法中调用，获取试图
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout._money_card_layout, container, false);


        text_page = view.findViewById(R.id._money_add_page);
        flingContainer = view.findViewById(R.id.frame);
        _money_card_main = view.findViewById(R.id._money_card_main);

        text_page.setText(page + "/" + datalist.size());
        moneyCardAdapter = new MoneyCardAdapter(getContext(), R.layout._money_card_item, datalist);
        flingContainer.setAdapter(moneyCardAdapter);


        //还有一些逻辑需要完善
        final Handler handler = new Handler();


        //设置时间的监听
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)

//                Money money = datalist.get(0);
//                datalist.remove(0);
//                datalist.add(money);
//                moneyCardAdapter.notifyDataSetChanged();
                Log.e(TAG, "removeFirstObjectInAdapter: ");
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Money money = datalist.get(datalist.size() - 1);
                datalist.remove(datalist.size() - 1);
                datalist.add(0, money);

                moneyCardAdapter.notifyDataSetChanged();
                Log.e(TAG, "onLeftCardExit: ");
                if (page - 1 <= 0) {
                    page = datalist.size() + 1;
                }
                text_page.setText(--page + "/" + datalist.size());
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Money money = datalist.get(0);
                datalist.remove(0);
                datalist.add(money);
                moneyCardAdapter.notifyDataSetChanged();

                if (page + 1 > datalist.size()) {
                    page = 0

                    ;
                }
                text_page.setText(++page + "/" + datalist.size());
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                // Ask for more data here
                moneyCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        moneyadd = view.findViewById(R.id._money_add);
        moneyadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof Money_add_Click) {
                    ((Money_add_Click) getActivity()).OneBtnClick();
                }
            }
        });
        show_Money(_money_card_main);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        datalist.clear();
        datalist.addAll(myDB.loadMoney_Day());
        MoneyCardSort();
        text_page.setText(page + "/" + datalist.size());
        show_Money(_money_card_main);
        moneyCardAdapter.notifyDataSetChanged();
    }


    public void show_Money(TextView textView) {
        Money moneyyest = new Money();
        double end = 0;
        for (Money money : datalist) {
            if (money.getDate().equals(moneyyest.getDate())) {
                end += money.getMoney();
            }
        }
        if (end != 0) {
            DecimalFormat decimalFormat=new DecimalFormat("#.00");
            textView.setText("今日支出：" + decimalFormat.format(end) + "元");
        }
    }

    public interface Money_add_Click {
        void OneBtnClick();
    }


    public void MoneyCardSort() {

        Collections.sort(datalist);
    }
}
