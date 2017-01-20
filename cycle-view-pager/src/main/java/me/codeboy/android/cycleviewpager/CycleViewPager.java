package me.codeboy.android.cycleviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import me.codeboy.android.cycleviewpager.adapter.CycleViewPagerAdapter;

/**
 * 实现可循环，可轮播的viewpager
 *
 * @author Yuedong Li
 */
public class CycleViewPager extends RelativeLayout implements OnPageChangeListener, View.OnTouchListener {
    private final static String TAG = "CycleViewPager";
    private final static int WHEEL_SIGNAL = 100; // 转动信号
    private final static int CYCLE_SIZE_THRESHOLD = 3; // 滚动时，view数目需要超过此值
    private List<View> mViewList = new ArrayList<View>();
    private TextView[] mIndicators;
    private LinearLayout mIndicatorContainer; // 指示器
    private int mmIndicatorSpace = -1; //指示器间距
    private RelativeLayout mCycleViewPagerContainer; //父容器
    private ViewPager mViewPager;
    private ParentViewPager mParentViewPager;
    private boolean mParentViewScrollable = true; //父viewPager是否可滚动
    private CycleViewPagerAdapter mAdapter;
    private CycleViewPageHandler mHandler;
    private int mWheelTime = 5000; // 轮播时间
    private int mCurrentPosition = 0; // 轮播当前位置
    private boolean mCycle = true; // 是否循环
    private boolean mWheel = false; // 是否轮播
    private boolean mNeedIndicator = false; //是否需要指示器,views数目大于1时才需要
    private boolean mUseDefaultIndicator = true; //使用系统指示器
    private boolean mPageReplaceByCode = false; //是否是程序切换了viewPager的页面
    private OnPageListener mListener; // 回调接口
    private boolean mFinished = false; //view是否已经从窗口卸载掉了
    private int mIndicatorSelectedBackground = R.drawable.cycleviewpager_indicator_circle_black; //指示器选中的背景颜色
    private int mIndicatorUnselectedBackground = R.drawable.cycleviewpager_indicator_circle_gray;//指示器未选中的背景颜色

    public CycleViewPager(Context context) {
        super(context);
        init(context);
    }

    public CycleViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CycleViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化控件
     *
     * @param context 上下文
     */
    public void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout
                .cycleviewpager_content, this, true);
        mCycleViewPagerContainer = (RelativeLayout) view.findViewById(R.id
                .cycle_view_pager_container);

        mViewPager = (ViewPager) view.findViewById(R.id
                .cycle_view_pager);
        mViewPager.setOnTouchListener(this);
        mIndicatorContainer = (LinearLayout) view.findViewById(R.id.cycle_view_pager_indicator_container);

        mHandler = new CycleViewPageHandler(this) {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (mViewList.size() == 0 || mFinished) {
                    return;
                }

                if (msg.what == WHEEL_SIGNAL) {
                    int maxSize = mViewList.size();
                    if (maxSize <= CYCLE_SIZE_THRESHOLD) {
                        return;
                    }
                    mCurrentPosition = (mCurrentPosition + 1) % maxSize;
                    mViewPager.setCurrentItem(mCurrentPosition, true);
                    mHandler.removeCallbacks(runnable);
                    mHandler.postDelayed(runnable, mWheelTime);
                }
            }
        };
    }

    /**
     * 消息runnable
     */
    private final Runnable runnable = new Runnable() {

        @Override
        public void run() {
            if (!mFinished && mWheel) {
                mHandler.sendEmptyMessage(WHEEL_SIGNAL);
            }
        }
    };

    /**
     * 初始化viewpager,设置数据,默认循环
     *
     * @param views 要显示的views
     */
    public void setData(List<View> views) {
        setData(views, mCycle);
    }

    /**
     * 初始化viewpager,设置数据
     *
     * @param views 要显示的views
     * @param cycle 是否循环
     */
    public void setData(List<View> views, boolean cycle) {
        setData(views, cycle, mWheel);
    }

    /**
     * 初始化viewpager,设置数据,默认循环
     *
     * @param views 要显示的views
     * @param cycle 是否循环
     * @param wheel 是否轮播
     */
    public void setData(List<View> views, boolean cycle, boolean wheel) {
        setData(views, cycle, wheel, mWheelTime);
    }

    /**
     * 初始化viewpager,设置数据,默认循环
     *
     * @param views     要显示的views
     * @param cycle     是否循环
     * @param wheel     是否轮播
     * @param wheelTime 轮播时间间隔
     */
    public void setData(List<View> views, boolean cycle, boolean wheel, int wheelTime) {
        setData(views, cycle, wheel, wheelTime, 0);
    }

    /**
     * 初始化viewpager,设置数据和是否滚动
     *
     * @param views           要显示的views
     * @param cycle           是否循环
     * @param wheel           是否轮播
     * @param wheelTime       轮播时间间隔
     * @param defaultPosition 默认选择展示位置
     */
    public void setData(List<View> views, boolean cycle, boolean wheel, int wheelTime, int defaultPosition) {
        mViewList.clear();

        if (views != null && views.size() > 0) {
            mViewList.addAll(views);
        }

        int ivSize = mViewList.size();

        //没有view直接隐藏
        if (ivSize == 0) {
            setVisibility(View.GONE);
            return;
        }

        if (cycle && ivSize > CYCLE_SIZE_THRESHOLD) {
            this.mCycle = true;
        } else {
            this.mCycle = false;
            this.mWheel = false;
        }

        if (!mCycle && ivSize > 1 || mCycle && ivSize > CYCLE_SIZE_THRESHOLD) {
            mNeedIndicator = true;
        }

        if (mUseDefaultIndicator) {
            // 设置指示器
            mIndicators = new TextView[ivSize];
            if (mNeedIndicator) {
                if (mCycle) {
                    mIndicators = new TextView[ivSize - 2];
                }
                mIndicatorContainer.removeAllViews();
                LinearLayout.LayoutParams params;
                for (int i = 0; i < mIndicators.length; i++) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout
                            .cycleviewpager_indicator, null);
                    mIndicators[i] = (TextView) view.findViewById(R.id.cycle_view_pager_indicator);
                    if (mmIndicatorSpace > 0) {
                        params = (LinearLayout.LayoutParams) mIndicators[i].getLayoutParams();
                        params.setMargins(mmIndicatorSpace, params.topMargin, mmIndicatorSpace, params.bottomMargin);
                        mIndicators[i].setLayoutParams(params);
                    }
                    mIndicatorContainer.addView(view);
                }
            } else {
                mIndicatorContainer.setVerticalGravity(View.GONE);
            }
        }

        mAdapter = new CycleViewPagerAdapter();
        mAdapter.setViewList(mViewList);

        mViewPager.setOffscreenPageLimit(ivSize > 3 ? 3 : ivSize);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setAdapter(mAdapter);
        if (defaultPosition < 0 || defaultPosition >= mViewList.size()) {
            defaultPosition = 0;
        }

        // 默认指向第一项，下方viewPager.setCurrentItem将触发重新计算指示器指向
        setIndicator(defaultPosition);

        mViewPager.setCurrentItem(mCycle ? defaultPosition + 1 : defaultPosition);

        //轮播
        if (mCycle && wheel) {
            this.mWheel = true;
            mWheelTime = wheelTime;
            mHandler.postDelayed(runnable, wheelTime);
        }
    }

    /**
     * 设置指示器左方，默认指示器在右方
     */
    public void setIndicatorLeft() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIndicatorContainer.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        mIndicatorContainer.setLayoutParams(params);
    }

    /**
     * 设置指示器居中，默认指示器在右方
     */
    public void setIndicatorCenter() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIndicatorContainer.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mIndicatorContainer.setLayoutParams(params);
    }

    /**
     * 是否处于循环状态
     *
     * @return 是否处于循环状态
     */
    public boolean isCycle() {
        return mCycle;
    }

    /**
     * 是否处于轮播状态
     *
     * @return 是否轮播
     */
    public boolean isWheel() {
        return mWheel;
    }

    /**
     * 返回内置的viewpager
     *
     * @return mViewPager
     */
    public ViewPager getInnerViewPager() {
        return mViewPager;
    }

    /**
     * 获取默认指示器，可以通过修改margin属性修改间距，指示器的父view为LinearLayout
     *
     * @return 指示器数组
     */
    public TextView[] getIndicators() {
        return mIndicators;
    }

    /**
     * 设置指示器间距
     *
     * @param dpValue dp的值
     */
    public void setIndicatorsSpace(int dpValue) {
        mmIndicatorSpace = (int) (dpValue * getResources().getDisplayMetrics().density / 2 + 0.5);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (mListener != null) {
            mListener.onPageScrolled(arg0 - 1, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        mCurrentPosition = arg0;
        int position = arg0;
        if (mCycle) {
            if (mCurrentPosition == mViewList.size() - 1) {
                position = 1;
            } else if (mCurrentPosition == 0) {
                position = mViewList.size() - 2;
            }

            setIndicator(position - 1);

            if (mCurrentPosition == mViewList.size() - 1 || mCurrentPosition == 0) {
                final int newReplacePosition = getRealPosition(mCurrentPosition);
                //等待动画结束
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCurrentPosition = newReplacePosition;
                        mPageReplaceByCode = true;
                        mViewPager.setCurrentItem(mCurrentPosition, false);
                    }
                }, 250);
            }
        } else {
            setIndicator(position);
        }

        if (mListener != null) {
            if (!mPageReplaceByCode) {
                int realPosition = getRealPosition(mCurrentPosition);
                mListener.onPageSelected(mViewList.get(realPosition), mCycle ? realPosition - 1 : realPosition);
            } else {
                mPageReplaceByCode = false;
            }
        }
    }

    /**
     * 获取映射后的位置
     *
     * @param position position
     * @return 位置
     */
    private int getRealPosition(int position) {
        if (!mCycle) {
            return position;
        }

        if (position == 0) {
            return mViewList.size() - 2;
        }

        if (position == mViewList.size() - 1) {
            return 1;
        }
        return position;
    }

    /**
     * 获取当前位置
     *
     * @return 当前位置
     */
    public int getCurrentPostion() {
        return mCycle ? mCurrentPosition - 1 : mCurrentPosition;
    }

    /**
     * 获取指示器容器,容器位与右下角，margin和padding都设置0，自行设置
     *
     * @param useDefaultIndicator 使用默认指示器
     * @return container 已经清理所有子view
     */
    public LinearLayout getIndicatorContainer(boolean useDefaultIndicator) {
        if (!useDefaultIndicator) {
            mIndicatorContainer.removeAllViews();
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mIndicatorContainer.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            mIndicatorContainer.setLayoutParams(params);
            mIndicatorContainer.setPadding(0, 0, 0, 0);
            mIndicators = null;
        }
        this.mUseDefaultIndicator = useDefaultIndicator;
        return mIndicatorContainer;
    }

    /**
     * 获取容器，可以用来设置背景等
     *
     * @return 根容器
     */
    public RelativeLayout getCycleViewPagerContainer() {
        return mCycleViewPagerContainer;
    }

    /**
     * 设置指示器
     *
     * @param selectedPosition 默认指示器位置
     */
    private void setIndicator(int selectedPosition) {
        if (!mNeedIndicator || !mUseDefaultIndicator) {
            return;
        }
        for (int i = 0; i < mIndicators.length; i++) {
            mIndicators[i].setBackgroundResource(mIndicatorUnselectedBackground);
        }
        if (mIndicators.length > selectedPosition) {
            mIndicators[selectedPosition].setBackgroundResource(mIndicatorSelectedBackground);
        }
    }


    /**
     * 默认指示器背景，height = 5dp
     *
     * @param defaultBackgroundId  默认背景资源id
     * @param selectedBackgroundId 选中背景资源id
     */
    public void setIndicatorBackground(int defaultBackgroundId, int selectedBackgroundId) {
        this.mIndicatorUnselectedBackground = defaultBackgroundId;
        this.mIndicatorSelectedBackground = selectedBackgroundId;
    }

    /**
     * 如果当前页面嵌套在另一个viewPager中，为了在进行滚动时阻断父ViewPager滚动，可以阻止父ViewPager滑动事件
     * 父ViewPager需要实现ParentViewPager中的setScrollable方法
     *
     * @param scrollable 是否可以滚动
     */
//    private void setParentViewPagerScrollable(boolean scrollable) {
//        if (mParentViewPager != null) {
//            this.mParentViewScrollable = scrollable;
//            mParentViewPager.setScrollable(this, scrollable);
//        }
//    }

    /**
     * 设置父viewpager,并同时设置是否滑动时间优先级
     *
     * @param parentViewPager parent viewPager
     * @param scrollable      是否可以滚动
     */
    public void setParentViewPagerAndScrollable(ParentViewPager parentViewPager, boolean scrollable) {
        if (parentViewPager != null) {
            mParentViewPager = parentViewPager;
            this.mParentViewScrollable = scrollable;
            mParentViewPager.setScrollable(this, scrollable);
        }
    }

    /**
     * 设置试图切换停止后的监听器
     *
     * @param listener 页面监听器
     */
    public void setOnPageListener(OnPageListener listener) {
        this.mListener = listener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mWheel) {
                mHandler.removeCallbacks(runnable);
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mWheel) {
                mHandler.postDelayed(runnable, mWheelTime);
            }
        }

        return false;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mFinished = true;
    }

    /**
     * inner handler
     */
    static class CycleViewPageHandler extends Handler {
        private final WeakReference<CycleViewPager> mCycleViewPager;

        public CycleViewPageHandler(CycleViewPager cycleViewPager) {
            this.mCycleViewPager = new WeakReference<CycleViewPager>(cycleViewPager);
        }

        @Override
        public void dispatchMessage(Message msg) {
            CycleViewPager cycleViewPager = mCycleViewPager.get();
            if (cycleViewPager == null) {
                return;
            }
            super.dispatchMessage(msg);
        }
    }

    /**
     * 监听器
     */
    public interface OnPageListener {
        void onPageSelected(View view, int position);

        void onPageScrolled(int position, float offsetPercent, int offsetPixels);
    }

}