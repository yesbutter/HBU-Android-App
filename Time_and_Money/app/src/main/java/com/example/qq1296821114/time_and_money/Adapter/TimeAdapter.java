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

import java.util.ArrayList;


/**
 *Time的适配器
 * Created by 12968 on 2018/4/4.
 */

public class TimeAdapter extends ArrayAdapter<Time> {

    private int resourceID;



    public TimeAdapter(@NonNull Context context, int resource, ArrayList<Time> list) {
        super(context, resource,list);
        resourceID=resource;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        View view ;
        ViewHolder viewHolder;
        Time time=getItem(position);
        if(convertView==null) {
            view=LayoutInflater.from(getContext()).inflate(resourceID,null,false);
            viewHolder=new ViewHolder();
            viewHolder.textView1=view.findViewById(R.id._time_text1);
            viewHolder.textView2=view.findViewById(R.id._time_text2);
            view.setTag(viewHolder);
        }
        else
        {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }

        viewHolder.textView1.setText(time.getDate());
        viewHolder.textView2.setText(time.getText2());
        return view;
    }

    class ViewHolder
    {
        private TextView textView1,textView2;
    }

}
