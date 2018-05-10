package com.example.qq1296821114.time_and_money.Model;

/**
 *
 * Created by 12968 on 2018/4/6.
 */

public class Money_Icon {

    private int id;
    private String name;

    public Money_Icon(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Money_Icon(String name, int id) {
        this.id = id;
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    /*
    private Money_Icon() {
        if (hashMap.isEmpty()) {
            hashMap.put("书", R.drawable.ic_book);
            hashMap.put("蚂蚁花呗", R.drawable.ic_aunt);
            hashMap.put("沙发", R.drawable.ic_bed);
            hashMap.put("车", R.drawable.ic_bus);
            hashMap.put("吃", R.drawable.ic_eat);
            hashMap.put("礼物", R.drawable.ic_gift);
            hashMap.put("日常用品", R.drawable.ic_dayuse);
            hashMap.put("学习", R.drawable.ic_learning);
            hashMap.put("红包", R.drawable.ic_redbag);
            hashMap.put("购物", R.drawable.ic_redbag);
            hashMap.put("零食", R.drawable.ic_redbag);
            hashMap.put("淘宝", R.drawable.ic_redbag);
            hashMap.put("微信", R.drawable.ic_redbag);
            hashMap.put("现金", R.drawable.ic_redbag);
            hashMap.put("时间", R.drawable.ic_time);
        }
    }*/


}
