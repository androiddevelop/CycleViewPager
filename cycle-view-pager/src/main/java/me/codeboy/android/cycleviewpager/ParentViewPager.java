package me.codeboy.android.cycleviewpager;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * 能够与轮播共存的viewpager
 *
 * @author yuedong.li
 */
public class ParentViewPager extends ViewPager {
    private CycleViewPager mCurrentChildViewPager;
    private Map<CycleViewPager, Boolean> mScrollableMap = new HashMap<CycleViewPager, Boolean>();
    private SparseArray<CycleViewPager> mChildViews = new SparseArray<CycleViewPager>();

    public ParentViewPager(Context context) {
        super(context);
        init();
    }

    public ParentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化viewpager
     */
    private void init() {
        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCurrentChildViewPager = mChildViews.valueAt(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 设置viewpager是否可以滚动
     *
     * @param childViewPager 子viewPager
     * @param scrollable     滚动？
     */
    public void setScrollable(CycleViewPager childViewPager, boolean scrollable) {
        this.mCurrentChildViewPager = childViewPager;
        mScrollableMap.put(childViewPager, scrollable);
        if (mChildViews.indexOfValue(childViewPager) == -1) {
            mChildViews.put(mChildViews.size(), mCurrentChildViewPager);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean tapOnChildViewPager = false;
        if (mCurrentChildViewPager != null) {
            tapOnChildViewPager = isTouchPointInView(mCurrentChildViewPager, new Point((int) event.getX(), (int) event.getY()));
        }

        boolean scrollableSettingFromChild = true;
        if (mScrollableMap.containsKey(mCurrentChildViewPager)) {
            scrollableSettingFromChild = mScrollableMap.get(mCurrentChildViewPager);
        }

        if (!scrollableSettingFromChild && tapOnChildViewPager) {
            //直接将事件分发给子view
            return false;
        } else {
            //默认行为
            return super.onInterceptTouchEvent(event);
        }
    }

    /**
     * 判断一个具体的触摸点是否在 view 上
     *
     * @param view  view
     * @param point 点的坐标
     * @return 点是否能否落在view上
     */
    public static boolean isTouchPointInView(View view, Point point) {
        if (view == null && point == null) {
            return false;
        }

        try {
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];
            int right = left + view.getMeasuredWidth();
            int bottom = top + view.getMeasuredHeight();
            if (point.x >= left && point.x <= right && point.y >= top && point.y <= bottom) {
                return true;
            }
        } catch (Exception e) {
            //ignore
        }

        return false;
    }
}