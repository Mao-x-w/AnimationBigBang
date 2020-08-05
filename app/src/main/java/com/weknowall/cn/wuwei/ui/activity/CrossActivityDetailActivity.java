package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weknowall.app_common.utils.DeviceHelper;
import com.weknowall.cn.wuwei.Constants;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.image.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class CrossActivityDetailActivity extends BaseActivity {

    @BindView(R.id.web_image_view)
    ImageView mWebImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_activity_detail);
        ButterKnife.bind(this);

//        mWebImageView.setImageUrl("https://s1.st.meishij.net/r/21/119/2654771/s2654771_141984835065242.jpg");
        GlideApp.with(this)
                .load(getIntent().getStringExtra(Constants.IntentExtra.URL))
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .priority(Priority.IMMEDIATE)
                .override(DeviceHelper.getScreenWidth(), getResources().getDimensionPixelSize(R.dimen.size_640))
                .transition(withCrossFade())
                .into(mWebImageView);

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
