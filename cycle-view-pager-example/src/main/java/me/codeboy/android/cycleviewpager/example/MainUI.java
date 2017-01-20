package me.codeboy.android.cycleviewpager.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import me.codeboy.android.cycleviewpager.example.adapter.MenuListAdapter;
import me.codeboy.android.cycleviewpager.example.example.IndicatorCustom2CycleTextView;
import me.codeboy.android.cycleviewpager.example.example.CycleTextView;
import me.codeboy.android.cycleviewpager.example.example.NoCycleTextView;
import me.codeboy.android.cycleviewpager.example.example.EventCycleTextView;
import me.codeboy.android.cycleviewpager.example.example.IndicatorCustom1CycleTextView;
import me.codeboy.android.cycleviewpager.example.example.NestCycleTextView;
import me.codeboy.android.cycleviewpager.example.example.WheelCycleTextView;

/**
 * 主页面
 *
 * @author YD
 */
public class MainUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_cycleviewpager_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menu_container);
        String[] menus = new String[]{
                getString(R.string.no_cycle_style),
                getString(R.string.cycle_style),
                getString(R.string.wheel_cycle_style),
                getString(R.string.event_cycle_style),
                getString(R.string.indicator_custom__cycle_style_1),
                getString(R.string.indicator_custom__cycle_style_2),
                getString(R.string.nest_cycle_style)
        };

        Class[] actions = new Class[]{
                NoCycleTextView.class,
                CycleTextView.class,
                WheelCycleTextView.class,
                EventCycleTextView.class,
                IndicatorCustom1CycleTextView.class,
                IndicatorCustom2CycleTextView.class,
                NestCycleTextView.class
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new MenuListAdapter(this, menus, actions));
    }
}