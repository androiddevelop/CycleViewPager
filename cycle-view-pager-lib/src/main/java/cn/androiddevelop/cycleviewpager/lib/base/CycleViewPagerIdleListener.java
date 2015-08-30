package cn.androiddevelop.cycleviewpager.lib.base;

import android.view.View;

/**
 * viewpager处于空闲状态监听器
 * 
 * @author YD
 *
 */
public interface CycleViewPagerIdleListener {

	public void onPagerSelected(View view, int position);
}
