package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.widget.TextView;

import com.laomao.apt_annotation.BindddView;
import com.laomao.apt_api.launcher.AutoBind;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;

/**
 * User: laomao
 * Date: 2019-12-02
 * Time: 18-54
 */
public class AptDemoActivity extends BaseActivity {

    @BindddView(R.id.apt_demo_title)
    public TextView mTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apt_demo);

        AutoBind.getInstance().inject(this);
        mTitle.setText("测试");
    }
}
