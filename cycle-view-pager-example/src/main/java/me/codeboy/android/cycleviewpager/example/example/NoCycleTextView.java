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
 * 普通模式，不循环
 *
 * @author YD
 */
public class NoCycleTextView extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_cycleviewpager);
        setTitle(R.string.no_cycle_style);

        List<View> views = new ArrayList<View>();
        for (int i = 0; i < 4; i++) {
            views.add(ViewFactory.getView(this, "Hello " + i, "title" + i));
        }

        CycleViewPager cycleViewPager = (CycleViewPager) findViewById(R.id.cycleViewPager);

        cycleViewPager.setData(views, false);
    }
}