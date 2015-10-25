package me.codeboy.android.cycleviewpager.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.codeboy.android.cycleviewpager.CycleViewPager;
import me.codeboy.android.cycleviewpager.base.CycleViewPagerIdleListener;
import me.codeboy.android.cycleviewpager.example.util.ViewFactory;

/**
 * 循环模式
 * 
 * @author YD
 *
 */
public class EventCycleTextView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cycleviewpager_cycle_textview);

		List<View> views = new ArrayList<View>();

		// 将最后一个view添加进来
		views.add(ViewFactory.getImageView(this, "Hello 3", "title3"));
		for (int i = 0; i < 4; i++) {
			views.add(ViewFactory.getImageView(this, "Hello " + i, "title" + i));
		}
		// 将第一个view添加进来
		views.add(ViewFactory.getImageView(this, "Hello 0", "title0"));

		final CycleViewPager cycleViewPager = (CycleViewPager) getFragmentManager()
				.findFragmentById(R.id.cycleViewPager);

		// 设置循环，在调用setData方法前调用
		cycleViewPager.setCycle(true);

		// 在加载数据前设置是否循环
		cycleViewPager.setData(views);

		cycleViewPager
				.setOnCycleViewPagerListener(new CycleViewPagerIdleListener() {

					@Override
					public void onPagerSelected(View view, int position) {
						if (cycleViewPager.isCycle())
							position = position - 1;
						Toast.makeText(EventCycleTextView.this,
								"position " + position, Toast.LENGTH_SHORT)
								.show();
					}
				});

	}
}