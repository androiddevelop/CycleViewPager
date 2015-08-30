package cn.androiddevelop.cycleviewpager.example.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cn.androiddevelop.cycleviewpager.example.R;

/**
 * view工厂
 * 
 * @author YD
 *
 */
public class ViewFactory {

	/**
	 * 获取一个简单的视图,包含title
	 * 
	 * @param text
	 * @return
	 */
	public static View getImageView(Context context, String text, String title) {
		View v = LayoutInflater.from(context).inflate(
				R.layout.cycleviewpager_textview, null);

		TextView textTv = (TextView) v.findViewById(R.id.text);
		TextView titleTv = (TextView) v.findViewById(R.id.title);
		textTv.setText(text);
		titleTv.setText(title);
		return v;
	}

	/**
	 * 获取一个简单的视图
	 * 
	 * @param text
	 * @return
	 */
	public static View getImageView(Context context, String text) {
		View v = LayoutInflater.from(context).inflate(
				R.layout.cycleviewpager_textview, null);

		TextView textTv = (TextView) v.findViewById(R.id.text);
		TextView titleTv = (TextView) v.findViewById(R.id.title);
		textTv.setText(text);
		titleTv.setVisibility(View.GONE);
		return v;
	}
}