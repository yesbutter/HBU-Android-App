package com.example.qq1296821114.time_and_money.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.qq1296821114.time_and_money.Model.Time;
import com.example.qq1296821114.time_and_money.R;

import java.util.List;

/**
 *
 * Created by 12968 on 2018/4/8.
 */

public class TimeCardAdapter extends ArrayAdapter<Time> {

    private int resourceId;

    public TimeCardAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        resourceId=resource;
    }

    public TimeCardAdapter(@NonNull Context context, int resource, List<Time> list) {
        super(context, resource, list);
        resourceId=resource;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;
        Time time=getItem(position);
        ViewHolder viewHolder;
        if(convertView==null) {
            view= LayoutInflater.from(getContext()).inflate(resourceId,null,false);
            viewHolder=new ViewHolder();
            viewHolder.body=view.findViewById(R.id._time_card_text);
            viewHolder.time=view.findViewById(R.id._time_card_time);
            viewHolder.plan=view.findViewById(R.id._time_card_plan);

            view.setTag(viewHolder);
        }
        else
        {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }

        viewHolder.plan.setText(time.getText1());
        viewHolder.time.setText(time.getDate());
        viewHolder.body.setText(time.getText2());
        return view;
    }


    class ViewHolder
    {
        TextView body,time,plan;
    }


}
