package me.codeboy.android.cycleviewpager.example.example;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import me.codeboy.android.cycleviewpager.CycleViewPager;
import me.codeboy.android.cycleviewpager.example.R;
import me.codeboy.android.cycleviewpager.example.base.BaseActivity;
import me.codeboy.android.cycleviewpager.example.component.IndicatorRect;
import me.codeboy.android.cycleviewpager.example.util.ViewFactory;

/**
 * 自定义指示器
 *
 * @author YD
 */
public class IndicatorCustom2CycleTextView extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_cycleviewpager);
        setTitle(R.string.indicator_custom__cycle_style_2);

        List<View> views = new ArrayList<View>();

        // 将最后一个view添加进来
        views.add(ViewFactory.getView(this, "Hello 3", "title3"));
        for (int i = 0; i < 4; i++) {
            views.add(ViewFactory.getView(this, "Hello " + i, "title" + i));
        }
        // 将第一个view添加进来
        views.add(ViewFactory.getView(this, "Hello 0", "title0"));

        final CycleViewPager cycleViewPager = (CycleViewPager) findViewById(R.id.cycleViewPager);

        //设置指示器居中
        LinearLayout indicatorContainer = cycleViewPager.getIndicatorContainer(false);
        int width = getResources().getDisplayMetrics().widthPixels;
        final int gap = width / 4;
        float scale = getResources().getDisplayMetrics().density;
        final IndicatorRect rect = new IndicatorRect(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, (int) (2 * scale + 0.5f));
        indicatorContainer.addView(rect, params);
        rect.setSelectedPosition(0, gap);

        cycleViewPager.setData(views, true, false);
        cycleViewPager.setOnPageListener(new CycleViewPager.OnPageListener() {
            @Override
            public void onPageSelected(View view, int position) {
                int start = position * gap;
                rect.setSelectedPosition(start, start + gap);
            }

            @Override
            public void onPageScrolled(int position, float offsetPercent, int offsetPixels) {
                int start = gap * position + (int) (offsetPercent * gap);
                rect.setSelectedPosition(start, start + gap);
            }
        });
    }
}