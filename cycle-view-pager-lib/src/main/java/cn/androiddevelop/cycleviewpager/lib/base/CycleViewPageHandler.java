package cn.androiddevelop.cycleviewpager.lib.base;

import android.content.Context;
import android.os.Handler;

import java.lang.ref.WeakReference;

/**
 * Created by yuedong.lyd on 6/7/15.
 * <p>
 * 构建防止内存泄露的handler
 * </p>
 */
public class CycleViewPageHandler {
    /**
     * 防止handler对activity有隐式引用，匿名内部类不会对外部类有引用
     */
    public static class UnleakHandler extends Handler {
        private final WeakReference<Context> context;

        public UnleakHandler(Context context) {
            this.context = new WeakReference<Context>(context);
        }
    }
}