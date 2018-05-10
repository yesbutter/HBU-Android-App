package com.example.qq1296821114.time_and_money.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qq1296821114.time_and_money.Model.Money_Icon;
import com.example.qq1296821114.time_and_money.R;

import java.util.ArrayList;


/**
 *
 * Created by 12968 on 2018/4/7.
 */

public class Money_AddAdapter extends RecyclerView.Adapter<Money_AddAdapter.MyHolder> {

    private int resourceId;
    private ArrayList<Money_Icon> money_icon;
    private MyOnItemClickListener mListener;

    public Money_AddAdapter(MyOnItemClickListener mListener) {
        money_icon = new ArrayList<>();

        this.mListener = mListener;

        if (money_icon.isEmpty()) {
           // money_icon.add(new Money_Icon("书", R.drawable.ic_book));
            money_icon.add(new Money_Icon("支付宝", R.drawable.ic_aunt));
            money_icon.add(new Money_Icon("微信", R.drawable.ic_weixin));
            money_icon.add(new Money_Icon("现金", R.drawable.ic_xianjin));
            money_icon.add(new Money_Icon("淘宝", R.drawable.ic_taobao));
            //money_icon.add(new Money_Icon("沙发", R.drawable.ic_bed));
            money_icon.add(new Money_Icon("出行", R.drawable.ic_bus));
            money_icon.add(new Money_Icon("吃", R.drawable.ic_eat));
            //money_icon.add(new Money_Icon("礼物", R.drawable.ic_gift));
            money_icon.add(new Money_Icon("日常用品", R.drawable.ic_dayuse));
            money_icon.add(new Money_Icon("学习", R.drawable.ic_learning));
            money_icon.add(new Money_Icon("红包", R.drawable.ic_redbag));
            //money_icon.add(new Money_Icon("购物", R.drawable.ic_shopping));
            //money_icon.add(new Money_Icon("零食", R.drawable.ic_snake));
            //money_icon.add(new Money_Icon("时间", R.drawable.ic_time));
        }

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout._moeny_add_item, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.icon.setImageResource(money_icon.get(position).getId());
        holder.textView.setText(money_icon.get(position).getName());

        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(money_icon.get(position).getId(), money_icon.get(position).getName());
            }
        });
    }


    @Override
    public int getItemCount() {
        return money_icon.size();
    }

    protected class MyHolder extends RecyclerView.ViewHolder {
        protected ImageView icon;
        protected TextView textView;

        //实现的方法
        public MyHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id._money_add_image);
            textView = (TextView) itemView.findViewById(R.id._money_add_text);


        }
    }

    public interface MyOnItemClickListener {
        void onItemClick(int id, String name);
    }

}

