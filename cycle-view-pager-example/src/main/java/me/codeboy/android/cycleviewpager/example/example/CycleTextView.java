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
 * 循环模式
 *
 * @author YD
 */
public class CycleTextView extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_cycleviewpager);
        setTitle(R.string.cycle_style);

        List<View> views = new ArrayList<View>();

        views.add(ViewFactory.getView(this, "Hello 3", "title3"));
        for (int i = 0; i < 4; i++) {
            views.add(ViewFactory.getView(this, "Hello " + i, "title" + i));
        }
        views.add(ViewFactory.getView(this, "Hello 0", "title0"));

        CycleViewPager cycleViewPager = (CycleViewPager) findViewById(R.id.cycleViewPager);

        cycleViewPager.setData(views, true);
    }
}