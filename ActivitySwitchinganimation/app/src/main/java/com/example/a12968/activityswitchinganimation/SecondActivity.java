package com.example.a12968.activityswitchinganimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by 12968 on 2018/2/12.
 */

public class SecondActivity extends Activity {

    Button button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        button=findViewById(R.id.back_button);
    }

    public void backclick(View view)
    {
        finish();
    }
}
