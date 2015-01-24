package cn.androiddevelop.cycleviewpager.base;

import android.content.Context;
import android.os.Handler;

/**
 * 为了防止内存泄漏，定义外部类，防止内部类对外部类的引用
 * 
 * @author Li Yuedong
 * 
 */
public class MyHandler extends Handler {
	 Context context;

	public MyHandler(Context context) {
		this.context = context;
	}
};