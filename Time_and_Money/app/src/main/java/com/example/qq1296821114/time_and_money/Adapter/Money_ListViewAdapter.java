package com.example.qq1296821114.time_and_money.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qq1296821114.time_and_money.Model.Money;
import com.example.qq1296821114.time_and_money.Model.Money_Day;
import com.example.qq1296821114.time_and_money.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * 设置的显示金额的管理器，用来获取视图
 * Created by 12968 on 2018/4/3.
 */

public abstract class Money_ListViewAdapter extends ArrayAdapter<Money_Day> {
    private int resourseID;
    private static long test = 1;

    private LinearLayout.LayoutParams frameParm = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    public Money_ListViewAdapter(@NonNull Context context, int resource, List<Money_Day> list) {
        super(context, resource, list);
        resourseID = resource;
    }


    @SuppressLint("SetTextI18n")
    public View getView(int position, View converView, ViewGroup parent) {
        Money_Day money_day = getItem(position);
        ViewHolder viewHolder;
        LinearLayout view = null;
        if (converView == null) {

            view = new LinearLayout(getContext());
            viewHolder = new ViewHolder(view);


            view.setTag(viewHolder);
        } else {
            view = (LinearLayout) converView;
            viewHolder = (ViewHolder) view.getTag();
        }

        view.removeAllViews();
        view.setOrientation(LinearLayout.VERTICAL);

        View view2=LinearLayout.inflate(getContext(),R.layout._money_listview_toolbar,null);
        TextView textView=view2.findViewById(R.id._money_listview_text);
        textView.setText(money_day.arrayList.get(0).getDateFormat());
        view.addView(view2, frameParm);
        for (int i = 0; i < 10 && i < money_day.arrayList.size(); i++) {
            final Money money = money_day.arrayList.get(i);

            viewHolder.views.get(i).textshow.setText(money.getType());
            viewHolder.views.get(i).textmoney.setText(String.valueOf(money.getMoney()) + "元");
            viewHolder.views.get(i).textdate.setText(money.getDate());
            viewHolder.views.get(i).imageView.setImageResource(money.getImageId());
            viewHolder.views.get(i).btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn_del(money);
                }
            });
            viewHolder.views.get(i).btn_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn_edit(money);
                }
            });
            view.addView(viewHolder.views.get(i).view);
        }

        if (money_day.arrayList.size() > 10) {
            for (int i = 10; i < money_day.arrayList.size(); i++) {
                Log.e(TAG, "getView: "+test );
                test++;
                final Money money = money_day.arrayList.get(i);
                @SuppressLint("ViewHolder")
                final View view1 = LayoutInflater.from(getContext()).inflate(R.layout._money_show_item, null, false);
                TextView textshow = view1.findViewById(R.id._money_show_type);
                textshow.setText(money.getType());
                TextView textmoney = view1.findViewById(R.id._money_show_money);
                textmoney.setText(String.valueOf(money.getMoney()) + "元");
                TextView textdate = view1.findViewById(R.id._money_show_date);
                textdate.setText(money.getDate());
                ImageView imageView = view1.findViewById(R.id._money_show_image);
                imageView.setImageResource(money.getImageId());
                view.addView(view1);

                view1.findViewById(R.id.btn_Delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_del(money);
                    }
                });

                view1.findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_edit(money);
                    }
                });
            }
        }
        return view;
    }

    class ViewHolder {
        View view;
        ArrayList<MyView> views = new ArrayList<>();

        protected ViewHolder(View view) {
            this.view = view;
            for (int i = 0; i < 10; i++) {
                MyView myView = new MyView();
                myView.view = LayoutInflater.from(getContext()).inflate(R.layout._money_show_item, null, false);
                myView.textshow = myView.view.findViewById(R.id._money_show_type);
                myView.textdate = myView.view.findViewById(R.id._money_show_date);
                myView.textmoney = myView.view.findViewById(R.id._money_show_money);
                myView.imageView = myView.view.findViewById(R.id._money_show_image);
                myView.btn_del = myView.view.findViewById(R.id.btn_Delete);
                myView.btn_edit = myView.view.findViewById(R.id.btn_edit);
                views.add(myView);
            }
        }
    }

    class MyView {
        View view;
        TextView textmoney, textdate, textshow;
        ImageView imageView;
        Button btn_del, btn_edit;
    }

    public abstract void btn_del(Money money);

    public abstract void btn_edit(Money money);
}
