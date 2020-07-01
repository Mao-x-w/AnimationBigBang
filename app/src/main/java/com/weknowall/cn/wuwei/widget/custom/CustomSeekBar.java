package com.weknowall.cn.wuwei.widget.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * User: laomao
 * Date: 2020/6/29
 * Time: 3:22 PM
 */
public class CustomSeekBar extends FrameLayout {
    public CustomSeekBar(Context context) {
        this(context,null);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }


}
