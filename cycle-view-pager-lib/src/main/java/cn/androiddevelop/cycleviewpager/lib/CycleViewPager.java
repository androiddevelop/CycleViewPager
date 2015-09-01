package cn.androiddevelop.cycleviewpager.lib;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.androiddevelop.cycleviewpager.lib.base.CycleViewPageHandler;
import cn.androiddevelop.cycleviewpager.lib.base.CycleViewPagerIdleListener;

/**
 * 实现可循环，可轮播的viewpager
 * 
 * @author Yuedong Li
 * 
 */
public class CycleViewPager extends Fragment implements OnPageChangeListener, View.OnTouchListener {
	private List<View> views = new ArrayList<View>();
	private TextView[] indicators;
	private FrameLayout viewPagerFragmentLayout;
	private LinearLayout indicatorLayout; // 指示器
	private cn.androiddevelop.cycleviewpager.lib.BaseViewPager viewPager;
	private cn.androiddevelop.cycleviewpager.lib.BaseViewPager parentViewPager;
	private ViewPagerAdapter adapter;
	private CycleViewPageHandler.UnleakHandler handler;
	private int time = 5000; // 轮播时间
	private int currentPosition = 0; // 轮播当前位置
	private boolean isScrollingOrPressed = false; // 滚动框是否滚动着或者被按着
	private LinearLayout viewPagerDefaultBg;
	private boolean isCycle = false; // 是否循环
	private boolean isWheel = false; // 是否轮播
	private int WHEEL_SIGNAL = 100; // 转动信号
	private CycleViewPagerIdleListener listener; // 回调接口

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.cycleviewpager_viewpager_fragment, null);

		viewPager = (cn.androiddevelop.cycleviewpager.lib.BaseViewPager) view.findViewById(R.id.viewPager);
		viewPager.setOnTouchListener(this);
		indicatorLayout = (LinearLayout) view
				.findViewById(R.id.viewpagerIndicatorLayout);

		viewPagerFragmentLayout = (FrameLayout) view
				.findViewById(R.id.viewPagerFragmentLayout);
		viewPagerDefaultBg = (LinearLayout) view
				.findViewById(R.id.viewPagerDefaultBg);

		handler = new CycleViewPageHandler.UnleakHandler(getActivity()) {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == WHEEL_SIGNAL && views.size() != 0) {
					if (!isScrollingOrPressed) {
						int max = views.size() + 1;
						int position = (currentPosition + 1) % views.size();
						viewPager.setCurrentItem(position, true);
						if (position == max) { // 最后一页时回到第一页
							viewPager.setCurrentItem(1, false);
						}
					}
					handler.removeCallbacks(runnable);
					handler.postDelayed(runnable, time);
				}
			}
		};

		return view;
	}

	/**
	 * 初始化viewpager
	 * 
	 * @param views
	 *            要显示的views
	 */
	public void setData(List<View> views) {
		setData(views, 0);
	}

	/**
	 * 初始化viewpager
	 * 
	 * @param views
	 *            要显示的views
	 * @param showPosition
	 *            默认显示位置
	 */
	public void setData(List<View> views, int showPosition) {
		this.views.clear();

		if (views.size() == 0) {
			viewPagerFragmentLayout.setVisibility(View.GONE);
			return;
		}

		for (View item : views) {
			this.views.add(item);
		}

		int ivSize = views.size();

		// 设置指示器
		indicators = new TextView[ivSize];
		if (isCycle) {
			indicators = new TextView[ivSize - 2];
		}
		indicatorLayout.removeAllViews();
		for (int i = 0; i < indicators.length; i++) {
			View view = LayoutInflater.from(getActivity()).inflate(
					R.layout.cycleviewpager_indicator, null);
			indicators[i] = (TextView) view.findViewById(R.id.indicator);
			indicatorLayout.addView(view);
		}

		adapter = new ViewPagerAdapter();

		// 默认指向第一项，下方viewPager.setCurrentItem将触发重新计算指示器指向
		setIndicator(0);

		viewPager.setOffscreenPageLimit(3);
		viewPager.setOnPageChangeListener(this);
		viewPager.setAdapter(adapter);
		if (showPosition < 0 || showPosition >= views.size())
			showPosition = 0;
		if (isCycle) {
			showPosition = showPosition + 1;
		}
		viewPager.setCurrentItem(showPosition);

		viewPagerDefaultBg.setVisibility(View.GONE);
	}

	/**
	 * 设置指示器居中，默认指示器在右方
	 */
	public void setIndicatorCenter() {
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		indicatorLayout.setLayoutParams(params);
	}

	/**
	 * 是否循环，默认不开启，开启前，请将views的最前面与最后面各加入一个视图，用于循环
	 * 
	 * @param isCycle
	 *            是否循环
	 */
	public void setCycle(boolean isCycle) {
		this.isCycle = isCycle;
	}

	/**
	 * 是否处于循环状态
	 * 
	 * @return
	 */
	public boolean isCycle() {
		return isCycle;
	}

	/**
	 * 设置是否轮播，默认不轮播,轮播一定是循环的
	 * 
	 * @param isWheel 是否滚动
	 */
	public void setWheel(boolean isWheel) {
		this.isWheel = isWheel;
		if (isWheel) {
			isCycle = true;
			handler.postDelayed(runnable, time);
		}
	}

	/**
	 * 是否处于轮播状态
	 * 
	 * @return
	 */
	public boolean isWheel() {
		return isWheel;
	}

	final Runnable runnable = new Runnable() {

		@Override
		public void run() {
			if (getActivity() != null && !getActivity().isFinishing()
					&& isWheel) {
				// viewpager依旧静止的话开始滑动
				if (!isScrollingOrPressed) {
					handler.sendEmptyMessage(WHEEL_SIGNAL);
				} else {
					handler.removeCallbacks(runnable);
				}
			}
		}
	};

	/**
	 * 释放指示器高度，可能由于之前指示器被限制了高度，此处释放
	 */
	public void releaseHeight() {
		getView().getLayoutParams().height = RelativeLayout.LayoutParams.MATCH_PARENT;
		refreshData();
	}

	/**
	 * 设置轮播暂停时间，即每多少秒切换到下一张视图.默认5000ms
	 * 
	 * @param time
	 *            毫秒为单位
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * 刷新数据，当外部视图更新后，通知刷新数据
	 */
	public void refreshData() {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 隐藏CycleViewPager
	 */
	public void hide() {
		viewPagerFragmentLayout.setVisibility(View.GONE);
	}

	/**
	 * 返回内置的viewpager
	 * 
	 * @return viewPager
	 */
	public BaseViewPager getViewPager() {
		return viewPager;
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
			//防止v被添加前存在与另一个父容器中
			if(v.getParent() != null){
				((ViewGroup)v.getParent()).removeView(v);
			}
			container.addView(v);
			return v;
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		if (arg0 == ViewPager.SCROLL_STATE_DRAGGING || arg0==ViewPager.SCROLL_STATE_SETTLING) { // viewPager在滚动
			isScrollingOrPressed = true;
			handler.removeCallbacks(runnable);
			return;
		} else if (arg0 == ViewPager.SCROLL_STATE_IDLE) { // viewPager滚动结束
			if (parentViewPager != null) {
				parentViewPager.setScrollable(true);
			}

			viewPager.setCurrentItem(currentPosition, false);
			if (listener != null){
				listener.onPagerSelected(views.get(currentPosition),
						currentPosition);
			}
			isScrollingOrPressed = false;

			handler.postDelayed(runnable, time);
		}
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		int max = views.size() - 1;
		int position = arg0;
		currentPosition = arg0;
		if (isCycle) {
			if (arg0 == 0) {
				currentPosition = max - 1;
			} else if (arg0 == max) {
				currentPosition = 1;
			}
			position = currentPosition - 1;
		}
		setIndicator(position);
	}

	/**
	 * 设置viewpager是否可以滚动
	 * 
	 * @param enable
	 */
	public void setScrollable(boolean enable) {
		viewPager.setScrollable(enable);
	}

	/**
	 * 返回当前位置,循环时需要注意返回的position包含之前在views最前方与最后方加入的视图，即当前页面试图在views集合的位置
	 * 
	 * @return
	 */
	public int getCurrentPostion() {
		return currentPosition;
	}

	/**
	 * 设置指示器
	 * 
	 * @param selectedPosition
	 *            默认指示器位置
	 */
	private void setIndicator(int selectedPosition) {
		for (int i = 0; i < indicators.length; i++) {
			indicators[i]
					.setBackgroundResource(R.drawable.cycleviewpager_indicator_circle_gray);
		}
		if (indicators.length > selectedPosition) {
			indicators[selectedPosition].setBackgroundResource(R.drawable
					.cycleviewpager_indicator_circle_black);
		}
	}

	/**
	 * 如果当前页面嵌套在另一个viewPager中，为了在进行滚动时阻断父ViewPager滚动，可以阻止父ViewPager滑动事件
	 * 父ViewPager需要实现ParentViewPager中的setScrollable方法
	 */
	public void disableParentViewPagerTouchEvent(cn.androiddevelop.cycleviewpager.lib.BaseViewPager parentViewPager) {
		if (parentViewPager != null) {
			parentViewPager.setScrollable(false);
		}
	}

	/**
	 * 设置试图切换停止后的监听器
	 * 
	 * @param listener
	 *            页面监听器
	 */
	public void setOnCycleViewPagerListener(CycleViewPagerIdleListener listener) {
		this.listener = listener;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			isScrollingOrPressed = true;
			handler.removeCallbacks(runnable);
		}else if(event.getAction() == MotionEvent.ACTION_UP){
			isScrollingOrPressed = false;
			handler.postDelayed(runnable, time);
		}

		return false;
	}
}