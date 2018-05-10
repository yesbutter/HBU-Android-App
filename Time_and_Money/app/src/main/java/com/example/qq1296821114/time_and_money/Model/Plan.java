package com.example.qq1296821114.time_and_money.Model;

/**
 * 我的计划对象
 * Created by 12968 on 2018/3/30.
 */

public class Plan {
    private String Planname;
    private String PlanMotto;
    private String Plan_body;
    private Date date;
    private double Need_time;
    private static final String[] strings = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日",};


    public Plan() {

        date = new Date();
        PlanMotto = "这个人很懒什么都没留下";
        Plan_body = "等待填写";
    }

    public Plan(String planname) {
        Planname = planname;
        PlanMotto = "这个人很懒什么都没留下";
        Plan_body = "等待填写";
        date = new Date();
    }

    public Plan(String planname, String planMotto, String plan_body, Date date) {
        this.Planname = planname;
        this.PlanMotto = planMotto;
        this.Plan_body = plan_body;
        this.date = date;
    }

    public String getPlanname() {
        return Planname;
    }

    public void setPlanname(String planname) {
        Planname = planname;
    }

    public String getPlanMotto() {
        return PlanMotto;
    }

    public void setPlanMotto(String planMotto) {
        PlanMotto = planMotto;
    }

    public String getDate() {
        return date.getYear() + "-" + date.getMonth() + "-" + date.getDay() + "-" + date.getHour() + "-" + date.getMinuter() + "-" + date.getSecond() + " " + strings[date.getWeek() - 1];
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getNeed_time() {
        return Need_time;
    }

    public void setNeed_time(double need_time) {
        Need_time = need_time;
    }

    public String getPlan_body() {
        return Plan_body;
    }

    public void setPlan_body(String plan_body) {
        Plan_body = plan_body;
    }

}
