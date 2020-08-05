package com.weknowall.cn.wuwei.ui.activity.launchMode;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2020/3/16
 * Time: 11:26 AM
 */
public class ActivityB extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.b)
    public void onClick() {
        startActivity(ActivityC.class);
    }
}
