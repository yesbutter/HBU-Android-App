package com.example.a12968.myweather.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.a12968.myweather.Bean.StringItem;
import com.example.a12968.myweather.R;

import java.util.List;

/**
 * 继承ArrayAdapter的抽象子类，提供了抽象的方法conver
 * Created by t-lidashao on 18-2-23.
 */

public abstract class MyeditorAdapter extends ArrayAdapter<StringItem> {

    private int resourseID;


    public MyeditorAdapter(Context context, int resource, List<StringItem> list) {
        super(context, resource, list);
        resourseID = resource;
    }


    @NonNull
    public View getView(int position, View converView, ViewGroup parent) {
        StringItem stringItem = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (converView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourseID, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = view.findViewById(R.id.editor_text);
            viewHolder.delButton = view.findViewById(R.id.btnDelete);
            viewHolder.topButton = view.findViewById(R.id.btnTop);

            conver(viewHolder, position);
            view.setTag(viewHolder);

        } else {
            view = converView;
            viewHolder = (ViewHolder) view.getTag();
        }

        /**
         * Judge the Item whether or not top
         */
        if (stringItem.getTop() == 1) {
            viewHolder.topButton.setText("取消置顶");
            viewHolder.textView.setTextColor(Color.BLACK);
        } else {
            viewHolder.topButton.setText("置顶");
            viewHolder.textView.setTextColor(Color.GRAY);
        }

        viewHolder.textView.setText(stringItem.getString());
        return view;
    }

    /**
     * ViewHolder,view的持有者，提供了一个增加监听的方法
     */
    protected class ViewHolder {
        protected TextView textView;
        protected Button delButton, topButton;

        public void setOnClickListener(int ID, View.OnClickListener onClickListener) {
            switch (ID) {
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

    public abstract void conver(ViewHolder viewHolder, final int position);
}
