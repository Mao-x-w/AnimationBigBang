package com.weknowall.cn.wuwei.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.weknowall.cn.wuwei.R;


/**
 * 使用贝塞尔二阶曲线实现的动画效果
 * User: laomao
 * Date: 2017-01-09
 * Time: 10-07
 */

public class BezierCurveAnimation {
    private ImageView mStartView;
    private ImageView mEndView;
    private ViewGroup mParentView;
    private float mStartX;
    private float mStartY;
    private float mToX;
    private float mToY;

    // 路径测量
    private PathMeasure mPathMeasure;
    // 贝塞尔曲线中间过程点坐标
    private float[] mCurrentPosition = new float[2];
    private ImageView mAnimationView;

    public BezierCurveAnimation(ImageView startView, ImageView endView, ViewGroup parentView) {
        mStartView = startView;
        mEndView = endView;
        mParentView = parentView;
    }

    public void startAnimation(Context context){
        // 创造出执行动画的Img（这个图片就是执行动画的图片,从开始位置出发,经过一个抛物线（贝塞尔曲线）,移动到购物车里）
        mAnimationView = new ImageView(context);
        mAnimationView.setImageDrawable(mStartView.getDrawable());
        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams((int) context.getResources().getDimension(R.dimen.size_60),(int) context.getResources().getDimension(R.dimen.size_60));
        mParentView.addView(mAnimationView, layoutParams);

        mEndView.post(new Runnable() {
            @Override
            public void run() {
                calculatePosition();
            }
        });
    }

    /**
     * 计算动画的起始位置
     */
    private void calculatePosition() {
        // 得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        mParentView.getLocationInWindow(parentLocation);

        // 得到开始View的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        mStartView.getLocationInWindow(startLoc);

        // 得到结束View的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        mEndView.getLocationInWindow(endLoc);

        // 开始掉落的起始点：起始点-父布局起始点+起始View宽度的一半
        mStartX = startLoc[0] - parentLocation[0] + mStartView.getWidth() / 2;
        mStartY = startLoc[1] - parentLocation[1] + mStartView.getHeight() / 2;

        // View掉落后的终点坐标：View起始点-父布局起始点+View宽度的一 半
        mToX = endLoc[0] - parentLocation[0];
        mToY = endLoc[1] - parentLocation[1];

        drawPath();
    }

    /**
     * 开始画动画路径
     */
    private void drawPath() {
        // 开始绘制贝塞尔曲线
        Path path = new Path();
        // 移动到起始点（贝塞尔曲线的起点）
        path.moveTo(mStartX, mStartY);
        // 使用二阶贝塞尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((mStartX + mToX) / 2, mStartY, mToX, mToY);
        // mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        // 属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(500);

        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                // mCurrentPosition此时就是中间距离点的坐标值
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                // 移动的图片（动画图片）的坐标设置为该中间点的坐标
                mAnimationView.setTranslationX(mCurrentPosition[0]);
                mAnimationView.setTranslationY(mCurrentPosition[1]);
            }
        });

        // 动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mListener!=null)
                    mListener.onAnimationStart();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mParentView.removeView(mAnimationView);
                if (mListener!=null){
                    mListener.onAnimationEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        // 开始执行动画
        valueAnimator.start();
    }

    private AnimationListener mListener;
    public void setAnimationListener(AnimationListener listener){
        mListener=listener;
    }

    public interface AnimationListener{
        void onAnimationStart();
        void onAnimationEnd();
    }
}
