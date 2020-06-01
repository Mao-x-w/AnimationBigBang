package com.weknowall.cn.wuwei.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * User: laomao
 * Date: 2020/5/17
 * Time: 11:50 AM
 */
public class FlowLayout extends ViewGroup {

    //存放容器中所有的View
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    //存放每一行最高View的高度
    private List<Integer> mPerLineMaxHeight = new ArrayList<>();

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        super.generateLayoutParams(p);
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    /**
     * 对于流式布局，需要测量出每一行子View所占的高度，最后确定出总高度
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        int totalHeight = 0; // 控件的总高度
        int lineMaxHeight = 0; // 某一行的最大高度
        int lineTotalWidth = 0; // 某一行的view的总宽度

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            MarginLayoutParams layoutParams = (MarginLayoutParams) childView.getLayoutParams();

            int childViewWidth = childView.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childViewHeight = childView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;

            if (lineTotalWidth + childViewWidth > widthSize) {
                totalHeight += lineMaxHeight; // 当前的总高度+当前行的最大的view高度
                lineTotalWidth = childViewWidth; // 新起一行的宽度，就是最后那个放不下的view的宽度
                lineMaxHeight = childViewHeight; // 新起一行的高度，就是最后那个放不下的view的高度
            } else {
                lineTotalWidth += childViewWidth;

                // 将每一行的最高的view作为这一行的高度
                if (childViewHeight > lineMaxHeight)
                    lineMaxHeight = childViewHeight;
            }

            if (i == childCount - 1) {
                totalHeight += lineMaxHeight;
            }
        }

        // 如果设置的是Match_Parent或为具体高度，那么就按这个高度来，否则就按我们计算出来的高度来，也就是wrap_content
        heightSize = heightMode == MeasureSpec.EXACTLY ? heightSize : totalHeight;

        setMeasuredDimension(widthSize,heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mPerLineMaxHeight.clear();

        //存放每一行的子View
        List<View> lineViews = new ArrayList<>();
        //记录每一行已存放View的总宽度
        int totalLineWidth = 0;

        //记录每一行最高View的高度
        int lineMaxHeight = 0;

        /*************遍历所有View，将View添加到List<List<View>>集合中***************/
        //获得子View的总个数
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (totalLineWidth + childWidth > getWidth()) {
                mAllViews.add(lineViews);
                mPerLineMaxHeight.add(lineMaxHeight);
                //开启新的一行
                totalLineWidth = 0;
                lineMaxHeight = 0;
                lineViews = new ArrayList<>();
            }
            totalLineWidth += childWidth;
            lineViews.add(childView);
            lineMaxHeight = Math.max(lineMaxHeight, childHeight);
        }
        //单独处理最后一行
        mAllViews.add(lineViews);
        mPerLineMaxHeight.add(lineMaxHeight);
        /************遍历集合中的所有View并显示出来*****************/
        //表示一个View和父容器左边的距离
        int mLeft = 0;
        //表示View和父容器顶部的距离
        int mTop = 0;

        for (int i = 0; i < mAllViews.size(); i++) {
            //获得每一行的所有View
            lineViews = mAllViews.get(i);
            lineMaxHeight = mPerLineMaxHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View childView = lineViews.get(j);
                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
                int leftChild = mLeft + lp.leftMargin;
                int topChild = mTop + lp.topMargin;
                int rightChild = leftChild + childView.getMeasuredWidth();
                int bottomChild = topChild + childView.getMeasuredHeight();
                //四个参数分别表示View的左上角和右下角
                childView.layout(leftChild, topChild, rightChild, bottomChild);
                mLeft += lp.leftMargin + childView.getMeasuredWidth() + lp.rightMargin;
            }
            mLeft = 0;
            mTop += lineMaxHeight;
        }

    }

}
