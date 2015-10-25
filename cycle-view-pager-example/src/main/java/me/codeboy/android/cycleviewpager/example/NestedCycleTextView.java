package me.codeboy.android.cycleviewpager.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.codeboy.android.cycleviewpager.BaseViewPager;
import me.codeboy.android.cycleviewpager.CycleViewPager;
import me.codeboy.android.cycleviewpager.example.util.ViewFactory;

/**
 * 循环轮播模式
 * 
 * @author YD
 *
 */
public class NestedCycleTextView extends Activity {

	List<View> views = new ArrayList<View>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cycleviewpager_nested_cycle_textview);

		BaseViewPager parentViewPager = (BaseViewPager) findViewById(R.id.parentViewPager);

		views.add(getCycleViewPagerView(parentViewPager));
		for (int i = 1; i < 3; i++) {
			views.add(ViewFactory.getImageView(this, "Parent " + i));
		}

		parentViewPager.setAdapter(new ViewPagerAdapter());
	}

	/**
	 * 获取CycleViewPager所在页面视图
	 * 
	 * @param parentViewPager 父viewpager
	 * @return
	 */
	private View getCycleViewPagerView(BaseViewPager parentViewPager) {
		View v = LayoutInflater.from(this).inflate(
				R.layout.cycleviewpager_nested_cycle_textview2, null);

		List<View> views = new ArrayList<View>();
		// 将最后一个view添加进来
		views.add(ViewFactory.getImageView(this, "Child 3", "title 3"));
		for (int i = 0; i < 4; i++) {
			views.add(ViewFactory.getImageView(this, "Child " + i, "title" + i));
		}
		// 将第一个view添加进来
		views.add(ViewFactory.getImageView(this, "Child 0", "title0"));

		CycleViewPager cycleViewPager = (CycleViewPager) getFragmentManager()
				.findFragmentById(R.id.cycleViewPager);

		// 设置循环，在调用setData方法前调用
		cycleViewPager.setCycle(true);

		// 禁止在滚动CycleViewPager时外层ViewPager滚动
		cycleViewPager.disableParentViewPagerTouchEvent(parentViewPager);

		// 在加载数据前设置是否循环
		cycleViewPager.setData(views);

		// 设置轮播
		cycleViewPager.setWheel(true);

		// 设置初始高度为屏幕的一半高度
		cycleViewPager.getView().getLayoutParams().height = getResources()
				.getDisplayMetrics().heightPixels / 2;

		// 设置轮播时间，默认5000ms
		cycleViewPager.setTime(3000);
		return v;
	}

	/**
	 * 页面适配器 返回对应的view
	 * 
	 * @author Yuedong Li
	 * 
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