package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;

import com.weknowall.cn.wuwei.ui.BaseActivity;

/**
 * User: laomao
 * Date: 2020/6/12
 * Time: 1:57 PM
 */
public class TransparentActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showToast("创建成功");
    }
}
