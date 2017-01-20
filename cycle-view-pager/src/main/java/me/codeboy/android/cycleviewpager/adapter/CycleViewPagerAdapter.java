package me.codeboy.android.cycleviewpager.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面适配器 返回对应的view
 * Created by Yuedong Li on 10/01/2017.
 */
public class CycleViewPagerAdapter extends PagerAdapter {
    private List<View> mViewList = new ArrayList<View>();

    public CycleViewPagerAdapter() {
    }

    public void setViewList(List<View> viewList) {
        if (viewList != null && viewList.size() > 0) {
            mViewList.clear();
            mViewList.addAll(viewList);
        }
    }

    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        View v = mViewList.get(position);
        //防止v被添加前存在于另一个父容器中
        if (v.getParent() != null) {
            ((ViewGroup) v.getParent()).removeView(v);
        }
        container.addView(v);
        return v;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}