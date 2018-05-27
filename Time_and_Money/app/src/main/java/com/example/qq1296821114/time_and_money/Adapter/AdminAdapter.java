package com.example.qq1296821114.time_and_money.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.qq1296821114.time_and_money.Model.User;
import com.example.qq1296821114.time_and_money.R;

import java.util.List;

/**
 *
 * Created by t-lidashao on 18-5-27.
 */

public abstract class AdminAdapter extends ArrayAdapter<User> {
    int resourceId;

    public AdminAdapter(@NonNull Context context, int resource, List<User> list) {
        super(context, resource,list);
        resourceId=resource;
    }


    @NonNull
    public View getView(final int position, View converView, ViewGroup parent) {

        View view=null;
        ViewHolder viewHolder;
        final User user=getItem(position);
        if(converView==null)
        {
            view= LayoutInflater.from(getContext()).inflate(resourceId,null,false);
            viewHolder=new ViewHolder();
            viewHolder.name=view.findViewById(R.id._admin_name);
            viewHolder.password=view.findViewById(R.id._admin_password);
            viewHolder.del=view.findViewById(R.id._admin_Delete);
            view.setTag(viewHolder);
        }
        else
        {
            view=converView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.name.setText("用户名:"+user.getUser());
        viewHolder.password.setText("密码:"+user.getPassword());
        viewHolder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_del(position);
            }
        });
        return view;
    }


    public abstract void btn_del(int position);
    class ViewHolder{
        TextView name,password;
        Button del;
    }
}
