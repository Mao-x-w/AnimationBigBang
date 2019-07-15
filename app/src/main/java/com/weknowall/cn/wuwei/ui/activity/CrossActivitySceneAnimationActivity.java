package com.weknowall.cn.wuwei.ui.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.widget.ToolBar;
import com.weknowall.cn.wuwei.widget.image.WebImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CrossActivitySceneAnimationActivity extends BaseActivity {

    @BindView(R.id.search)
    ImageView mSearch;
    @BindView(R.id.web_image_view)
    WebImageView mWebImageView;
    @BindView(R.id.toolbar)
    ToolBar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_activity_scene_animation);
        ButterKnife.bind(this);
        mWebImageView.setImageUrl("https://s1.st.meishij.net/r/21/119/2654771/s2654771_141984835065242.jpg");
    }

    @OnClick({R.id.search, R.id.web_image_view})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                Bundle options = ActivityOptions.makeSceneTransitionAnimation(this, mSearch, getString(R.string.transition_search_back)).toBundle();
                startActivityForResult(new Intent(this, CrossActivitySearchActivity.class), 0, options);
                break;
            case R.id.web_image_view:
                Bundle bundle=ActivityOptions.makeSceneTransitionAnimation(this,mWebImageView,getString(R.string.transition_detail)).toBundle();
                startActivity(new Intent(this,CrossActivityDetailActivity.class),bundle);
                break;
        }
    }
}
