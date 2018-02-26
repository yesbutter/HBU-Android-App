package com.example.a12968.myweather.Bean;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a12968.myweather.R;

import java.util.Calendar;


/**
 * Created by t-lidashao on 18-2-26.
 */

public class CityFragment extends android.support.v4.app.Fragment implements Comparable {
    private static final String TAG = "CityFragment";

    private String CityName;
    private TextView textView;
    private long time=0;
    private int top=0;

    public CityFragment() {
    }

    public void setTop(int top)
    {
        this.top=top;
    }

    public String getCityName()
    {
        return CityName;
    }

    public int getTop()
    {
        return top;
    }

    public long getTime()
    {
        return time;
    }

    public void setTextViewText(String text)
    {
        textView.setText(text);
    }

    public static CityFragment newInstance(String CityName,int top,long time) {
        Bundle bundle = new Bundle();
        CityFragment cityFragment = new CityFragment();
        bundle.putString("CityName", CityName);
        bundle.putInt("Top", top);
        bundle.putLong("Time", time);
        cityFragment.setArguments(bundle);
        return cityFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.city_fragment, container, false);

        textView = view.findViewById(R.id.main_text_view);

        textView.setText(CityName);
        return view;
    }

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            CityName = bundle.getString("CityName");
            top = bundle.getInt("Top");
            time = bundle.getLong("Time");
        }
    }


    public int compareTo(Object object) {
        if (object == null || !(object instanceof CityFragment)) {
            return -1;
        }

        CityFragment cityFragment = (CityFragment) object;
        /**置顶判断 ArrayAdapter是按照升序从上到下排序的，就是默认的自然排序
         * 如果是相等的情况下返回0，包括都置顶或者都不置顶，返回0的情况下要
         * 再做判断，拿它们置顶时间进行判断
         * 如果是不相等的情况下，otherSession是置顶的，则当前session是非置顶的，
         * 应该在otherSession下面，所以返回1
         * 同样，session是置顶的，则当前otherSession是非置顶的，
         * 应该在otherSession上面，所以返回-1
         */
        int result=0-(this.top-cityFragment.getTop());
        if(result==0)
        {
            result=0-compareToTime(this.time,cityFragment.getTime());
        }
        return  result;
    }

    private int compareToTime(long lhs,long rhs)
    {
        Calendar cLhs=Calendar.getInstance();
        Calendar cRhs=Calendar.getInstance();
        cLhs.setTimeInMillis(lhs);
        cRhs.setTimeInMillis(rhs);
        return cLhs.compareTo(cRhs);
    }
}
