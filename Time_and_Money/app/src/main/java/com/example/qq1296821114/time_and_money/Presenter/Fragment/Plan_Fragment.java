package com.example.qq1296821114.time_and_money.Presenter.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.qq1296821114.time_and_money.Adapter.PlanAdapter;
import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.Date;
import com.example.qq1296821114.time_and_money.Model.Plan;
import com.example.qq1296821114.time_and_money.R;
import com.example.qq1296821114.time_and_money.Util.MyUtil;

import java.util.ArrayList;

import uk.co.imallan.jellyrefresh.JellyRefreshLayout;
import uk.co.imallan.jellyrefresh.PullToRefreshLayout;

/**
 * 计划碎片
 * Created by 12968 on 2018/3/31.
 */

public class Plan_Fragment extends Fragment {

    private ListView listView;
    private PlanAdapter planAdapter;
    private JellyRefreshLayout mJellyLayout;
    private ArrayList<Plan> plans;
    private MyDB myDB;


    private TextView  text_data, text_motto, text_hour, text_name, text_planbody;

    public Plan_Fragment() {
        myDB = MyDB.getMyDB(getActivity());


        plans = (ArrayList<Plan>) myDB.loadPlan();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout._plan_layout, container, false);

        text_data = view.findViewById(R.id.plan_Data);
        text_motto = view.findViewById(R.id.plan_motto);
        text_planbody = view.findViewById(R.id.plan_body);
        text_hour = view.findViewById(R.id.plans_hour);
        text_name = view.findViewById(R.id.plan_Name);

        listView = view.findViewById(R.id._plan_listview);
        planAdapter = new PlanAdapter(getContext(), R.layout._plan_item, plans);
        listView.setAdapter(planAdapter);

        mJellyLayout = view.findViewById(R.id.refresh_layout);

        mJellyLayout.setPullToRefreshListener(new PullToRefreshLayout.PullToRefreshListener() {
            public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
                pullToRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder customizeDialog =
                                new AlertDialog.Builder(getContext());
                        final View dialogView = LayoutInflater.from(getContext())
                                .inflate(R.layout._plan_dialog, null);

                        final Button button = dialogView.findViewById(R.id._plan_dailog_data);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                MyUtil.showDatePickerDialog(getActivity(), button);
                            }
                        });
                        customizeDialog.setTitle("我是一个自定义Dialog");
                        customizeDialog.setView(dialogView);
                        customizeDialog.setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 获取EditView中的输入内容
//
                                       /* String date[]=button.getText().toString().split("-");
                                        Log.e("TAG",date.toString());*/

                                        EditText edit_text = dialogView.findViewById(R.id._plan_dialog_edit_text1);
                                        EditText editText2 = dialogView.findViewById(R.id._plan_dialog_edit_text1_5);
                                        EditText editText3 = dialogView.findViewById(R.id._plan_dialog_edit_text2);
                                        Plan plan = new Plan(edit_text.getText().toString(), editText2.getText().toString(), editText3.getText().toString(),
                                                new Date());
                                        plans.add(plan);
                                        myDB.savePlan(plan);
                                        planAdapter.notifyDataSetChanged();
                                    }
                                });
                        customizeDialog.show();
                        mJellyLayout.setRefreshing(false);
                    }
                }, 500);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Plan plan = plans.get(position);
                text_data.setText(plan.getDate());
                text_motto.setText(plan.getPlanMotto());
                text_hour.setText("00-00-00");
                text_name.setText(plan.getPlanname());
                text_planbody.setText(plan.getPlan_body());
            }
        });

        return view;
    }

}
