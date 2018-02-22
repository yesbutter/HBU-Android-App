package com.example.a12968.myweather.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;


/**
 *
 * Created by t-lidashao on 18-2-18.
 */

public class Util {
    private static Toast toast;

    @SuppressLint("ShowToast")
    public static void makeToast(Context context, String string)
    {
        if(toast==null)
        {
            toast=Toast.makeText(context,string,Toast.LENGTH_SHORT);
        }
        else
        {
            toast.setText(string);
        }
        toast.show();
    }

}
