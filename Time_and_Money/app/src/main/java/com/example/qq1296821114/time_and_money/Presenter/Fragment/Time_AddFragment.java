package com.example.qq1296821114.time_and_money.Presenter.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.Date;
import com.example.qq1296821114.time_and_money.Model.Plan;
import com.example.qq1296821114.time_and_money.Model.Time;
import com.example.qq1296821114.time_and_money.R;
import com.example.qq1296821114.time_and_money.Util.MyUtil;

/**
 *
 * Created by 12968 on 2018/4/10.
 */

public class Time_AddFragment extends Fragment {


    private TextView textView;
    private Button time_add_yes,time_add_no;
    private EditText time_add_body, time_add_name;
    private MyDB myDB;

    public Time_AddFragment() {
        myDB = MyDB.getMyDB(getContext());

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout._time_add_layout, container, false);

        textView = view.findViewById(R.id.time_add_date);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtil.showDatePickerDialog(getActivity(), textView,1);
            }
        });

        time_add_yes = view.findViewById(R.id.time_add_yes);
        time_add_body = view.findViewById(R.id.time_add_body);
        time_add_name = view.findViewById(R.id.time_add_name);
        time_add_no=view.findViewById(R.id.time_add_no);

        time_add_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Time_add_Over) getActivity()).on_time_finish();
            }
        });

        time_add_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!time_add_name.getText().toString().isEmpty() && !time_add_body.getText().toString().isEmpty()) {
                    if (getActivity() instanceof Time_add_Over) {
                        Date date;
                        String string = textView.getText().toString();
                        if (string.equals("今日")) {
                            date = new Date();
                        } else {
                            String[] strings = string.split("-");
                            date = new Date(Integer.valueOf(strings[0]), Integer.valueOf(strings[1]), Integer.valueOf(strings[2]));
                        }
                        myDB.saveTime(new Time(time_add_name.getText().toString(),time_add_body.getText().toString(), date));

                        ((Time_add_Over) getActivity()).on_time_finish();
                    }
                }
            }
        });

        return view;
    }

    public interface Time_add_Over {
        void on_time_finish();
    }

}
