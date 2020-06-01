package com.weknowall.cn.wuwei.widget.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * User: laomao
 * Date: 2020/5/15
 * Time: 5:56 PM
 */
public class PieView extends View {

    private Paint mArcPaint;
    private Paint mCirclePaint;
    private RectF mBounds;

    private int mProgress;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void init() {
        mArcPaint = new Paint();
        mArcPaint.setStyle(Paint.Style.FILL);
//        mArcPaint.setStrokeWidth(1f);
        mArcPaint.setColor(Color.RED);

        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(2f);
        mCirclePaint.setColor(Color.BLACK);

        mBounds = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBounds.set(0,0,w,h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float angle = (float) (mProgress*1.0 / 100 * 360f);
        canvas.drawArc(mBounds,270,angle,true,mArcPaint);
        canvas.drawCircle(mBounds.centerX(),mBounds.centerY(),mBounds.height()/2,mCirclePaint);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
