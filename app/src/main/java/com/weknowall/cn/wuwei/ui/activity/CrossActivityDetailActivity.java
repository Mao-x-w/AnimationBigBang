package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewTreeObserver;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.widget.image.WebImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CrossActivityDetailActivity extends BaseActivity {

    @BindView(R.id.web_image_view)
    WebImageView mWebImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_activity_detail);
        ButterKnife.bind(this);

        mWebImageView.setImageUrl("https://s1.st.meishij.net/r/21/119/2654771/s2654771_141984835065242.jpg");

        postponeEnterTransition();
        mWebImageView.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        mWebImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }
}
