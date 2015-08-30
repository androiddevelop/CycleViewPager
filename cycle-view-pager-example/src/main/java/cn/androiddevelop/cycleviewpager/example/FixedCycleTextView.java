package cn.androiddevelop.cycleviewpager.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.androiddevelop.cycleviewpager.example.util.ViewFactory;
import cn.androiddevelop.cycleviewpager.lib.CycleViewPager;

/**
 * 循环轮播模式
 * 
 * @author YD
 *
 */
public class FixedCycleTextView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cycleviewpager_fixed_cycle_textview);
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

		// 设置轮播
		cycleViewPager.setWheel(true);

		// 设置初始高度为屏幕的一半高度
		cycleViewPager.getView().getLayoutParams().height = getResources()
				.getDisplayMetrics().heightPixels / 2;

		// 点击按钮释放高度
		findViewById(R.id.releaseBtn).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						cycleViewPager.releaseHeight();

					}
				});
	}
}