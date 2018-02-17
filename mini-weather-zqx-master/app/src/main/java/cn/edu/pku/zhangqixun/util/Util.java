package cn.edu.pku.zhangqixun.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by 12968 on 2018/2/16.
 */

public class Util {

    private static Toast toast;

    public static void showToast(Context context,String string)
    {
        if(toast==null)
        {
            toast=Toast.makeText(context, string,Toast.LENGTH_SHORT);
        }
        else
        {
            toast.setText(string);
        }
        toast.show();
    }


}
