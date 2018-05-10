package com.example.qq1296821114.time_and_money.Presenter.Fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qq1296821114.time_and_money.DataBase.MyDB;
import com.example.qq1296821114.time_and_money.Model.Date;
import com.example.qq1296821114.time_and_money.Model.Money;
import com.example.qq1296821114.time_and_money.Model.Money_Day;
import com.example.qq1296821114.time_and_money.R;
import com.example.qq1296821114.time_and_money.View.DayAxisValueFormatter;
import com.example.qq1296821114.time_and_money.View.DemoBase;
import com.example.qq1296821114.time_and_money.View.MyAxisValueFormatter;
import com.example.qq1296821114.time_and_money.View.XYMarkerView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**撒啊啊啊啊啊啊啊啊啊啊啊顶顶顶顶顶顶顶
 * Created by 12968 on 2018/4/1.
 */

public class My_Fragment extends DemoBase  implements
        OnChartValueSelectedListener {
    private ArrayList<Money> datalist=new ArrayList<>();
    private ArrayList<Money_Day> daylist=new ArrayList<>();
    private MyDB myDB;
    private Map<String, Double> hashMap = new HashMap<>();

    private PieChart mChart;
    protected BarChart mChart2;
    public My_Fragment()
    {
        myDB = MyDB.getMyDB(getActivity());
        datalist.addAll(myDB.loadMoney_Day());
        for (int i = 0; i < datalist.size(); i++) {
            Money money = (Money) datalist.get(i);
            if (hashMap.get(money.getDate()) != null) {
                hashMap.put(money.getDate(), money.getMoney() + hashMap.get(money.getDate()));
            } else {
                hashMap.put(money.getDate(), money.getMoney());
            }
        }
        for (Map.Entry<String, Double> entry : hashMap.entrySet()) {
            String[] string=entry.getKey().split("-");
            Date date=new Date(Integer.valueOf(string[0]),Integer.valueOf(string[1]),Integer.valueOf(string[2]));
            daylist.add(new Money_Day(entry.getValue(),date));
        }
        Collections.sort(daylist);
    }



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout._my_layout, container, false);
        mChart=view.findViewById(R.id.chart1);
        mChart2=view.findViewById(R.id.chart2);
        init();
        init2();
        return view;
    }

    private void init2() {
        mChart2.setOnChartValueSelectedListener(this);
        mChart2.setDrawBarShadow(false);
        mChart2.setDrawValueAboveBar(true);
        mChart2.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart2.setMaxVisibleValueCount(60);
        // scaling can now only be done on x- and y-axis separately
        mChart2.setPinchZoom(false);
        mChart2.setDrawGridBackground(false);
        // mChart.setDrawYLabels(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mChart2);

        XAxis xAxis = mChart2.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTfLight);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

        IAxisValueFormatter custom = new MyAxisValueFormatter();

        YAxis leftAxis = mChart2.getAxisLeft();
        leftAxis.setTypeface(mTfLight);
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mChart2.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setTypeface(mTfLight);
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        Legend l = mChart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
//        l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
//         "def", "ghj", "ikl", "mno" });
//         l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
//         "def", "ghj", "ikl", "mno" });

        XYMarkerView mv = new XYMarkerView(getContext(), xAxisFormatter);
        mv.setChartView(mChart2); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        setData2(daylist.size());
    }

    private void init()
   {
       mChart.setUsePercentValues(true);
       mChart.getDescription().setEnabled(false);
       mChart.setExtraOffsets(5, 10, 5, 5);

       mChart.setDragDecelerationFrictionCoef(0.95f);

       mChart.setCenterTextTypeface(mTfLight);
       mChart.setCenterText(generateCenterSpannableText());

       mChart.setDrawHoleEnabled(true);
       mChart.setHoleColor(Color.WHITE);

       mChart.setTransparentCircleColor(Color.WHITE);
       mChart.setTransparentCircleAlpha(110);

       mChart.setHoleRadius(58f);
       mChart.setTransparentCircleRadius(61f);

       mChart.setDrawCenterText(true);

       mChart.setRotationAngle(0);
       // enable rotation of the chart by touch
       mChart.setRotationEnabled(true);
       mChart.setHighlightPerTapEnabled(true);

       // mChart.setUnit(" €");
       // mChart.setDrawUnitsInChart(true);

       // add a selection listener
       mChart.setOnChartValueSelectedListener(this);

       setData(4, 100);

       mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
       // mChart.spin(2000, 0, 360);


       Legend l = mChart.getLegend();
       l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
       l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
       l.setOrientation(Legend.LegendOrientation.VERTICAL);
       l.setDrawInside(false);
       l.setXEntrySpace(7f);
       l.setYEntrySpace(0f);
       l.setYOffset(0f);

       // entry label styling
       mChart.setEntryLabelColor(Color.WHITE);
       mChart.setEntryLabelTypeface(mTfRegular);
       mChart.setEntryLabelTextSize(12f);
   }
    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5),
                    mParties[i % mParties.length],
                    getResources().getDrawable(R.drawable.star)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
    private void setData2(int count) {
        ArrayList<BarEntry> yVals1 = new ArrayList<>();
        for (int i = 0; i <  count ; i++) {
            float val= (float) daylist.get(i).getMoney();
            if (Math.random() * 100 < 25) {
                yVals1.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.star)));
            } else {
                yVals1.add(new BarEntry(i, val));
            }
        }
        BarDataSet set1;
        if (mChart2.getData() != null &&
                mChart2.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mChart2.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mChart2.getData().notifyDataChanged();
            mChart2.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "The year 2017");
            set1.setDrawIcons(false);
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);
            mChart2.setData(data);
        }

        //动画
        mChart2.animateY(3000);
    }
    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("MPAndroidChart");
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {
    }

    @Override
    public void onResume() {
        super.onResume();
        daylist.clear();
        datalist.clear();
        hashMap.clear();
        datalist.addAll(myDB.loadMoney_Day());
        for (int i = 0; i < datalist.size(); i++) {
            Money money =datalist.get(i);
            if (hashMap.get(money.getDate()) != null) {
                hashMap.put(money.getDate(), money.getMoney() + hashMap.get(money.getDate()));
            } else {
                hashMap.put(money.getDate(), money.getMoney());
            }
        }
        for (Map.Entry<String, Double> entry : hashMap.entrySet()) {
            String[] string=entry.getKey().split("-");
            Date date=new Date(Integer.valueOf(string[0]),Integer.valueOf(string[1]),Integer.valueOf(string[2]));
            daylist.add(new Money_Day(entry.getValue(),date));
        }
        Collections.sort(daylist);
        setData2(daylist.size());
    }
}
