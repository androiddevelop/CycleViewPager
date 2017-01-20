package me.codeboy.android.cycleviewpager.example.example;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.codeboy.android.cycleviewpager.ParentViewPager;
import me.codeboy.android.cycleviewpager.CycleViewPager;
import me.codeboy.android.cycleviewpager.example.R;
import me.codeboy.android.cycleviewpager.example.base.BaseActivity;
import me.codeboy.android.cycleviewpager.example.util.ViewFactory;

/**
 * 循环轮播模式
 *
 * @author YD
 */
public class NestCycleTextView extends BaseActivity {

    List<View> views = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_cycleviewpager_nested_cycle_textview);
        setTitle(R.string.nest_cycle_style);

        final ParentViewPager parentViewPager = (ParentViewPager) findViewById(R.id.parentViewPager);
        views.add(getCycleViewPagerView(parentViewPager, false));
        views.add(getCycleViewPagerView(parentViewPager, true));
        for (int i = 2; i < 4; i++) {
            views.add(ViewFactory.getView(this, "Parent " + i));
        }

        parentViewPager.setAdapter(new ViewPagerAdapter());

//        parentViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 1) {
//                    parentViewPager.setScrollable(false);
//                } else {
//                    parentViewPager.setScrollable(true);
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    /**
     * 获取CycleViewPager所在页面视图
     *
     * @param parentViewPager 父viewpager
     * @return view cycle view pager
     */
    private View getCycleViewPagerView(ParentViewPager parentViewPager, boolean disableParent) {
        View v = disableParent ? LayoutInflater.from(this).inflate(
                R.layout.example_cycleviewpager_nested_cycle_textview3, null) : LayoutInflater.from(this).inflate(
                R.layout.example_cycleviewpager_nested_cycle_textview2, null);

        List<View> views = new ArrayList<View>();
        String text = "Parent can scroll ";
        if (disableParent) {
            text = "Parent can't scroll ";
        }
        for (int i = 0; i < 4; i++) {
            views.add(ViewFactory.getView(this, text + i, "title" + i));
        }

        final CycleViewPager cycleViewPager = (CycleViewPager) v.findViewById(R.id.cycleViewPager);

        cycleViewPager.setData(views, false, false, 3000);

        // disableParent为true是禁止在滚动CycleViewPager时外层ViewPager滚动
        cycleViewPager.setParentViewPagerAndScrollable(parentViewPager, !disableParent);

        // 设置初始高度为屏幕的一半高度
        ViewGroup.LayoutParams params = cycleViewPager.getLayoutParams();
        params.height = getResources()
                .getDisplayMetrics().heightPixels / 2;
        cycleViewPager.setLayoutParams(params);

        return v;
    }

    /**
     * 页面适配器 返回对应的view
     *
     * @author Yuedong Li
     */
    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
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
            View v = views.get(position);
            container.addView(v);
            return v;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }
}