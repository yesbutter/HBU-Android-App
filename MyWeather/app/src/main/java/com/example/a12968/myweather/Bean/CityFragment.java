package com.example.a12968.myweather.Bean;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a12968.myweather.R;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by t-lidashao on 18-2-26.
 */

public class CityFragment extends android.support.v4.app.Fragment {
    public static ArrayList<StringItem> stringItems=new ArrayList<>();
    private TextView textView;

    public CityFragment() {
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

        stringItems.add(new StringItem(CityName,time,top));
        return cityFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.city_fragment, container, false);
        textView = view.findViewById(R.id.main_text_view);
        return view;
    }

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }
}
