package me.codeboy.android.cycleviewpager.example.example;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.codeboy.android.cycleviewpager.CycleViewPager;
import me.codeboy.android.cycleviewpager.example.R;
import me.codeboy.android.cycleviewpager.example.base.BaseActivity;
import me.codeboy.android.cycleviewpager.example.util.ViewFactory;

/**
 * 自定义指示器颜色位置
 *
 * @author YD
 */
public class IndicatorCustom1CycleTextView extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_cycleviewpager);
        setTitle(R.string.indicator_custom__cycle_style_1);

        List<View> views = new ArrayList<View>();

        // 将最后一个view添加进来
        views.add(ViewFactory.getView(this, "Hello 3", "title3"));
        for (int i = 0; i < 4; i++) {
            views.add(ViewFactory.getView(this, "Hello " + i, "title" + i));
        }
        // 将第一个view添加进来
        views.add(ViewFactory.getView(this, "Hello 0", "title0"));

        final CycleViewPager cycleViewPager = (CycleViewPager) findViewById(R.id.cycleViewPager);

        //设置指示器间距和位置
        cycleViewPager.setIndicatorsSpace(4);
        cycleViewPager.setIndicatorCenter();
//        cycleViewPager.setIndicatorLeft();

        //设置指示器样式和颜色
        cycleViewPager.setIndicatorBackground(R.drawable.indicator_selected, R.drawable.indicator_unselected);

        cycleViewPager.setData(views, true, false, 2000);
    }
}