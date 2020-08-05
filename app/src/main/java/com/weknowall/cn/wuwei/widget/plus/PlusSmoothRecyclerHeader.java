package com.weknowall.cn.wuwei.widget.plus;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * User: Joy
 * Date: 2016/12/27
 * Time: 17:16
 * 专为SmoothAppBarLayout中的RecyclerView使用，使其位于SmoothAppBarLayout下方
 */

public class PlusSmoothRecyclerHeader extends PlusRecyclerHeaderFooter {

	View mLayoutView;

	public PlusSmoothRecyclerHeader(Context context) {
		this(context, null);
	}

	public PlusSmoothRecyclerHeader(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PlusSmoothRecyclerHeader(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mLayoutView = new View(context, attrs);
		addView(mLayoutView);
	}

	public void setHeight(int height) {
		LayoutParams params = (LayoutParams) mLayoutView.getLayoutParams();
		params.height = height;
		mLayoutView.setLayoutParams(params);
	}

	public void attachTo(@NonNull RecyclerView recycler) {
		super.attachTo(recycler, true);
	}
}
