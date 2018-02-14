package com.example.a12968.activityswitchinganimation;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by 12968 on 2018/2/13.
 */

public class MyRefreshLayout extends ViewGroup {

    private LayoutInflater layoutInflater;
    //用于平滑滑动的Scroller对象
    private Scroller mLayoutScroller;
    //Scroller的滑动速度
    private static final int SCROLL_SPEED = 650;

    /**
     * Header
     */
    private RelativeLayout relativeLayout;
    private TextView refresh_text;
    private ProgressBar progressBar;
    private boolean EnablePullDown = true;

    private int mLayoutContextHeight, mLastMoveY;

   //最小有效滑动距离
    private static int effectiveScroll = 150;

    public MyRefreshLayout(Context context) {
        super(context);
    }

    public MyRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        layoutInflater = LayoutInflater.from(context);
        mLayoutScroller = new Scroller(context);

    }

    protected void onMeasure(int widthMeasureSpec, int hightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, hightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, hightMeasureSpec);
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        if (EnablePullDown) {
            addLayoutHeader();
        }

    }

    private void addLayoutHeader() {
        relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.header, null);
        refresh_text = relativeLayout.findViewById(R.id.refresh_text);
        progressBar = relativeLayout.findViewById(R.id.refresh_progressbar);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams
                (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        addView(relativeLayout, layoutParams);
    }

    @Override
    protected void onLayout(boolean b, int i0, int i1, int i2, int i3) {
        mLayoutContextHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == relativeLayout) {
                child.layout(0, 0 - child.getMeasuredHeight(), child.getMeasuredWidth(), 0);
            } else {
                child.layout(0, mLayoutContextHeight, child.getMeasuredWidth(), mLayoutContextHeight + child.getMeasuredHeight());
                mLayoutContextHeight += child.getMeasuredHeight();
            }
        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        boolean intercept = false;
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (Math.abs(getScrollY()) >= effectiveScroll) {
                    Log.e("Up_Load", "Refresh");
                    mLayoutScroller.startScroll(0, getScrollY(), 0, -(effectiveScroll + getScrollY()));
                    refresh_text.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    Log.e("Up_Load", "Dis_fresh");
                    mLayoutScroller.startScroll(0, getScrollY(), 0, -getScrollY());
                }
                break;
            case MotionEvent.ACTION_DOWN:
                mLastMoveY = y;
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = mLastMoveY - y;
                if (dy < 0) {
                    if (Math.abs(getScrollY()) <= relativeLayout.getMeasuredHeight() / 2) {
                        scrollBy(0, dy);
                        if (Math.abs(getScrollY()) >= effectiveScroll) {
                            Log.e("Refresh_Text", "dy");
                            refresh_text.setText("松开刷新");
                        }
                    }
                }
                break;
        }
        mLastMoveY = y;
        return true;

    }
}
