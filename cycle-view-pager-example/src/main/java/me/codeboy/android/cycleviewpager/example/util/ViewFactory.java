package me.codeboy.android.cycleviewpager.example.util;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import me.codeboy.android.cycleviewpager.example.R;

/**
 * view工厂
 *
 * @author YD
 */
public class ViewFactory {

    /**
     * 获取一个简单的视图,包含title
     *
     * @param text  content
     * @param title title
     * @return view
     */
    public static View getView(Context context, String text, String title) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.example_cycleviewpager_textview, null);

        TextView textTv = (TextView) v.findViewById(R.id.text);
        TextView titleTv = (TextView) v.findViewById(R.id.title);
        textTv.setText(text);
        titleTv.setText(title);
        return v;
    }

    /**
     * 获取一个简单的视图
     *
     * @param text content
     * @return view view
     */
    public static View getView(Context context, String text) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.example_cycleviewpager_textview, null);

        TextView textTv = (TextView) v.findViewById(R.id.text);
        TextView titleTv = (TextView) v.findViewById(R.id.title);
        textTv.setText(text);
        textTv.setTextColor(Color.BLACK);
        titleTv.setVisibility(View.GONE);
        return v;
    }
}