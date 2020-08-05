package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.Logs;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2020/6/15
 * Time: 11:35 AM
 */
public class AnimateDemoActivity extends BaseActivity {

    @BindView(R.id.test_animate)
    TextView mTestAnimate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_demo);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.test_animate, R.id.value_animate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test_animate:
                showToast("点击了");
                Logs.d("left::"+mTestAnimate.getLeft()+"  top::"+mTestAnimate.getTop()+"  right::"+mTestAnimate.getRight()+"  bottom::"+mTestAnimate.getBottom());
                break;
            case R.id.value_animate:
                mTestAnimate.animate().setDuration(1000).translationX(200f).start();
                break;
        }
    }
}
