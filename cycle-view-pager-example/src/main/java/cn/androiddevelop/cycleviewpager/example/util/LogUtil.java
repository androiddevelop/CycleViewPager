package cn.androiddevelop.cycleviewpager.example.util;

import android.util.Log;

/**
 * 日志工具类
 * 
 * @author Yuedong Li
 * 
 */
public class LogUtil {
	private static boolean ONOFF = true;

	/**
	 * 打印日志
	 * 
	 * @param key
	 * @param info
	 */
	public static void log(String key, String info) {
		if (ONOFF)
			Log.i(key, "-----> " + info);
	}

	/**
	 * 打印错误日志
	 * 
	 * @param key
	 * @param info
	 */
	public static void logError(String key, String info) {
		Log.e(key, "-----> " + info);
	}

	public static void setLogShow(boolean flag) {
		ONOFF = flag;
	}
}