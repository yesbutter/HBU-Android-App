package com.example.qq1296821114.time_and_money.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qq1296821114.time_and_money.Model.Plan;
import com.example.qq1296821114.time_and_money.R;

import java.util.List;

/**
 * Plan的适配器
 * Created by 12968 on 2018/3/30.
 */

public class PlanAdapter extends ArrayAdapter<Plan> {

    private  int resourseID;
    private ImageView imageView;
    public PlanAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        resourseID=resource;
    }
    public PlanAdapter(@NonNull Context context, int resource, List<Plan> list) {
        super(context, resource,list);
        resourseID=resource;
    }

    /**
     * 重写getview方法
     * @param position
     * @param converView
     * @param parent
     * @return
     */
    public View getView(int position, View converView, ViewGroup parent) {

        View view=null;
        ViewHolder viewHolder;
        Plan plan=getItem(position);
        if(converView==null)
        {
            view= LayoutInflater.from(getContext()).inflate(resourseID,null,false);
            viewHolder=new ViewHolder();
            viewHolder.item=view.findViewById(R.id._plan_item_text);

            view.setTag(viewHolder);
        }
        else
        {
            view=converView;
            viewHolder=(ViewHolder)view.getTag();
        }

        viewHolder.item.setText(plan.getPlanname());
        return view;
    }

    protected  class ViewHolder{
        protected TextView item;
        protected ImageView logo;
    }
}
