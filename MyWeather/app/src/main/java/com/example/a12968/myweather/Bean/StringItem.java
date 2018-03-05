package com.example.a12968.myweather.Bean;

import java.util.Calendar;

/**
 * ListView Item!
 * Comparable sort!
 * Created by t-lidashao on 18-2-24.
 */

public class StringItem implements Comparable{
    private int top=0;
    private long time=0;
    private String string;

    private int PAGE;

    public StringItem(String string,Long time)
    {
        this.string=string;
        this.time=time;
        top=0;
    }

    public StringItem(String string,Long time,int top)
    {
        this.string=string;
        this.time=time;
        this.top=top;
    }

    public void setPAGE(int PAGE)
    {
        this.PAGE=PAGE;
    }

    public int getPAGE()
    {
        return PAGE;
    }

    public long getTime()
    {
        return time;
    }

    public String getString()
    {
        return string;
    }

    public int getTop()
    {
        return top;
    }

    public void setTop(int top)
    {
        this.top=top;
    }

    public void setTime(long time)
    {
        this.time=time;
    }

    public void setString(String string)
    {
        this.string=string;
    }

    public String toString()
    {
        return "City_name:"+string+" Time:"+time+" Top:"+top;
    }
    public int compareTo(Object object) {
        if (object == null || !(object instanceof StringItem)) {
            return -1;
        }

        StringItem stringItem = (StringItem) object;
        /**置顶判断 ArrayAdapter是按照升序从上到下排序的，就是默认的自然排序
         * 如果是相等的情况下返回0，包括都置顶或者都不置顶，返回0的情况下要
         * 再做判断，拿它们置顶时间进行判断
         * 如果是不相等的情况下，otherSession是置顶的，则当前session是非置顶的，
         * 应该在otherSession下面，所以返回1
         * 同样，session是置顶的，则当前otherSession是非置顶的，
         * 应该在otherSession上面，所以返回-1
         */
        int result=0-(this.top-stringItem.getTop());
        if(result==0)
        {
            result=0-compareToTime(this.time,stringItem.getTime());
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
