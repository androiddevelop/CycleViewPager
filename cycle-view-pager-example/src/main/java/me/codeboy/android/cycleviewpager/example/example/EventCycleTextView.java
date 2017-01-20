package me.codeboy.android.cycleviewpager.example.example;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.codeboy.android.cycleviewpager.CycleViewPager;
import me.codeboy.android.cycleviewpager.example.R;
import me.codeboy.android.cycleviewpager.example.base.BaseActivity;
import me.codeboy.android.cycleviewpager.example.util.ViewFactory;

/**
 * 循环模式
 *
 * @author YD
 */
public class EventCycleTextView extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_cycleviewpager);
        setTitle(R.string.event_cycle_style);

        List<View> views = new ArrayList<View>();

        View view;
        // 将最后一个view添加进来
        view = ViewFactory.getView(this, "Hello 3", "title3");
        views.add(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventCycleTextView.this,
                        "click 3", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        for (int i = 0; i < 4; i++) {
            view = ViewFactory.getView(this, "Hello " + i, "title" + i);
            views.add(view);
            final int index = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(EventCycleTextView.this,
                            "click " + index, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        }
        // 将第一个view添加进来
        view = ViewFactory.getView(this, "Hello 0", "title0");
        views.add(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventCycleTextView.this,
                        "click 0", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        final CycleViewPager cycleViewPager = (CycleViewPager) findViewById(R.id.cycleViewPager);

        cycleViewPager.setData(views, true);

        cycleViewPager.setOnPageListener(new CycleViewPager.OnPageListener() {

            @Override
            public void onPageSelected(View view, int position) {
                if (cycleViewPager.isCycle())
                    Toast.makeText(EventCycleTextView.this,
                            "position " + position, Toast.LENGTH_SHORT)
                            .show();
                Log.d(TAG, "position " + position);
            }

            @Override
            public void onPageScrolled(int position, float offsetPercent, int offsetPixels) {

            }
        });

    }
}