package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;

/**
 * User: laomao
 * Date: 2020/5/13
 * Time: 10:30 AM
 */
public class LeakCanaryActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 100000);
    }
}
