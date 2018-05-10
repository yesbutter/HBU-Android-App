package com.example.qq1296821114.alarmmanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by 12968 on 2018/4/10.
 */

public class RemindActivity extends Activity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.text_view);
        textView.setText("闹钟");
    }
}
