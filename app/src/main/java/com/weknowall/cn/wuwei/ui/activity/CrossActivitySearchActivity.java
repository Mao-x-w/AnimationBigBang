package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CrossActivitySearchActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross_activity_search);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back)
    public void onClick() {
        finishAfterTransition();
    }
}
