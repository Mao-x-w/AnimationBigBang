package com.weknowall.cn.wuwei.widget.layoutManager;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

public class ViewPagerLayoutManager extends LinearLayoutManager {

    private PagerSnapHelper snapHelper;
    private ViewPagerListener listener;
    private int mDrift;

    public ViewPagerLayoutManager(Context context, int orientation) {
        this(context, orientation, false);
    }

    public ViewPagerLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        init();
    }

    private void init() {
        snapHelper = new PagerSnapHelper();
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        snapHelper.attachToRecyclerView(view);
        view.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                if (listener!=null&&getChildCount()==1){
                    listener.onInit();
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
                listener.onPageRelease(getPosition(view),mDrift>=0);
            }
        });
    }

    @Override
    public void onScrollStateChanged(int state) {
        if (state==RecyclerView.SCROLL_STATE_IDLE){
            View snapView = snapHelper.findSnapView(this);
            if (listener!=null&&getChildCount()==1){
                listener.onPageSelected(getPosition(snapView),getPosition(snapView)==getItemCount()-1);
            }
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        mDrift=dy;
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    public void setOnViewPagerListener(ViewPagerListener listener){
        this.listener = listener;
    }

    public interface ViewPagerListener{
        // 初始化完成
        void onInit();

        // 当前选中的页，isBottom表示是否滑到了底部
        void onPageSelected(int position,boolean isBottom);

        // 要释放的页，isNext表示的是滑动了下一页还是上一页
        void onPageRelease(int position,boolean isNext);
    }
}
