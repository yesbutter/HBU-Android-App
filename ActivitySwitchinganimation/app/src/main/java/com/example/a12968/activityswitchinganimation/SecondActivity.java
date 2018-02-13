package com.example.a12968.activityswitchinganimation;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by 12968 on 2018/2/12.
 */

public class SecondActivity extends Activity {

    Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.refresh_layout);
        initView();

       // button=findViewById(R.id.back_button);
    }

    private void initView()
    {
        WaveSwipeRefreshLayout mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.my_wave_swipe);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                // Do work to refresh the list here.
                //new Task().execute();
            }
        });
    }

    private class Task {
        public void execute() {
        }
    }


//    public void backclick(View view)
//    {
//        finish();
//    }
}
