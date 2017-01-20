package me.codeboy.android.cycleviewpager.example.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * rect view
 * Created by yuedong.lyd on 2017-01-17.
 */
public class IndicatorRect extends View {
    private int mStartPosition = 0; //开始绘制位置
    private int mEndPosition = 0; //结束位置
    private int mSelectedColor = Color.RED;  //选中颜色
    private int mDefaultColor = Color.WHITE; // 默认颜色
    private Paint mSelectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //选中画笔
    private Paint mDefaultPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //默认画笔

    public IndicatorRect(Context context) {
        super(context);
        init();
    }

    public IndicatorRect(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndicatorRect(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * init configuration
     */
    private void init() {
        mSelectedPaint.setColor(mSelectedColor);
        mDefaultPaint.setColor(mDefaultColor);
    }

    /**
     * 设置颜色
     *
     * @param defaultColor  默认颜色
     * @param selectedColor 选中颜色
     */
    public void setColor(int defaultColor, int selectedColor) {
        this.mDefaultColor = defaultColor;
        this.mSelectedColor = selectedColor;
        mSelectedPaint.setColor(mSelectedColor);
        mDefaultPaint.setColor(mDefaultColor);
    }

    /**
     * 设置选中部分
     *
     * @param start start position
     * @param end   end position
     */
    public void setSelectedPosition(int start, int end) {
        this.mStartPosition = start;
        this.mEndPosition = end;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        if (mStartPosition >= 0 && mEndPosition <= width) {
            canvas.drawRect(0, 0, mStartPosition, height, mDefaultPaint);
            canvas.drawRect(mStartPosition, 0, mEndPosition, height, mSelectedPaint);
            canvas.drawRect(mEndPosition, 0, width, height, mDefaultPaint);
            return;
        }
        if (mStartPosition < 0) {
            int tmpPosition = width + mStartPosition;
            canvas.drawRect(0, 0, mEndPosition, height, mSelectedPaint);
            canvas.drawRect(mEndPosition, 0, tmpPosition, height, mDefaultPaint);
            canvas.drawRect(tmpPosition, 0, width, height, mSelectedPaint);
            return;
        }
        if (mEndPosition > width) {
            int tmpPosition = mEndPosition - width;
            canvas.drawRect(0, 0, tmpPosition, height, mSelectedPaint);
            canvas.drawRect(tmpPosition, 0, mStartPosition, height, mDefaultPaint);
            canvas.drawRect(mStartPosition, 0, width, height, mSelectedPaint);
        }

    }
}
