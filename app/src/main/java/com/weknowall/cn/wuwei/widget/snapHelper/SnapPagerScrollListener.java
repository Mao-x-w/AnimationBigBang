package com.weknowall.cn.wuwei.widget.snapHelper;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SnapPagerScrollListener extends RecyclerView.OnScrollListener {

    // Constants
    public static final int ON_SCROLL = 0; // for notifiying the callback as soon as as the new View/Page passes the middle
    public static final int ON_SETTLED = 1; // for notifying the callback after the RecyclerViews state is SCROLL_STATE_IDLE. I use the mode for only firing API calls when the scroll has settled

    @IntDef({ON_SCROLL, ON_SETTLED})
    public @interface Type {
    }

    public interface OnChangeListener {
        void onSnapped(int position);
    }

    // Properties
    private final PagerSnapHelper snapHelper;
    private final int type;
    private final boolean notifyOnInit;
    private final OnChangeListener listener;
    private int snapPosition;

    // Constructor
    public SnapPagerScrollListener(PagerSnapHelper snapHelper, @Type int type, boolean notifyOnInit, OnChangeListener listener) {
        this.snapHelper = snapHelper;
        this.type = type;
        this.notifyOnInit = notifyOnInit;
        this.listener = listener;
        this.snapPosition = RecyclerView.NO_POSITION;
    }

    // Methods
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if ((type == ON_SCROLL) || !hasItemPosition()) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView));
        }
    }

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (type == ON_SETTLED && newState == RecyclerView.SCROLL_STATE_IDLE) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView));
        }
    }

    private int getSnapPosition(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager == null) {
            return RecyclerView.NO_POSITION;
        }

        View snapView = snapHelper.findSnapView(layoutManager);
        if (snapView == null) {
            return RecyclerView.NO_POSITION;
        }

        return layoutManager.getPosition(snapView);
    }

    private void notifyListenerIfNeeded(int newSnapPosition) {
        if (snapPosition != newSnapPosition) {
            if (notifyOnInit && !hasItemPosition()) {
                listener.onSnapped(newSnapPosition);
            } else if (hasItemPosition()) {
                listener.onSnapped(newSnapPosition);
            }

            snapPosition = newSnapPosition;
        }
    }

    private boolean hasItemPosition() {
        return snapPosition != RecyclerView.NO_POSITION;
    }
}