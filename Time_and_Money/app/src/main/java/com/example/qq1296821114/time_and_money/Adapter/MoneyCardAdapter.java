package com.example.qq1296821114.time_and_money.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qq1296821114.time_and_money.Model.Money;
import com.example.qq1296821114.time_and_money.R;

import java.util.Collections;
import java.util.List;

/**
 * 卡片的适配器，简单提供了获取视图
 * Created by 12968 on 2018/4/5.
 */

public class MoneyCardAdapter extends ArrayAdapter<Money> {

    private int resourceId;

    public MoneyCardAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        resourceId = resource;
    }


    public MoneyCardAdapter(@NonNull Context context, int resource, List<Money> list) {
        super(context, resource, list);
        resourceId = resource;
    }

    @SuppressLint("SetTextI18n")
    public View getView(int position, View converView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder;
        Money money = getItem(position);
        if (converView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null, false);
            viewHolder = new ViewHolder();
            viewHolder._money_cardtext = view.findViewById(R.id._money_cardtext);
            viewHolder._money_cardtext_type = view.findViewById(R.id._money_cardtext_type);
            viewHolder._money_cardtime = view.findViewById(R.id._money_cardtime);
            viewHolder.imageView = view.findViewById(R.id._money_card_show);

            view.setTag(viewHolder);
        } else {
            view = converView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder._money_cardtime.setText(money.getDate());
        viewHolder._money_cardtext_type.setText("种类 : " + money.getType());
        viewHolder._money_cardtext.setText("支出 : " + String.valueOf(money.getMoney()) + "元");
        viewHolder.imageView.setImageResource(money.getImageId());

        return view;
    }


    class ViewHolder {
        TextView _money_cardtime, _money_cardtext, _money_cardtext_type;
        ImageView imageView;
    }

}
