package com.example.a12968.myweather.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.a12968.myweather.R;

import java.util.List;

/**
 *继承ArrayAdapter的抽象子类，提供了抽象的方法conver
 * Created by t-lidashao on 18-2-23.
 */

public abstract class MyeditorAdapter extends ArrayAdapter<String> {

    private int resourseID;


    public MyeditorAdapter(Context context, int resource, List<String> list) {
        super(context, resource, list);
        resourseID = resource;
    }

    public View getView(int position, View converView, ViewGroup parent) {
        String string = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (converView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourseID, null);
            viewHolder=new ViewHolder();
            viewHolder.textView=view.findViewById(R.id.editor_text);
            viewHolder.delButton=view.findViewById(R.id.btnDelete);
            viewHolder.topButton=view.findViewById(R.id.btnTop);
            conver(viewHolder,position);
            view.setTag(viewHolder);

        }
        else {
            view=converView;
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(string);
        return view;
    }

    /**
     * ViewHolder,view的持有者，提供了一个增加监听的方法
     */
    protected class ViewHolder {
        private TextView textView;
        private Button delButton, topButton;

        public void setOnClickListener(int ID, View.OnClickListener onClickListener)
        {
            switch (ID)
            {
                case R.id.editor_text:
                    textView.setOnClickListener(onClickListener);
                    break;
                case R.id.btnDelete:
                    delButton.setOnClickListener(onClickListener);
                    break;
                case R.id.btnTop:
                    topButton.setOnClickListener(onClickListener);
                    break;
                default:
                    break;
            }
        }
    }

    public abstract void conver(ViewHolder viewHolder,final int position);
}
