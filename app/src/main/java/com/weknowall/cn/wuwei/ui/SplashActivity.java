package com.weknowall.cn.wuwei.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.weknowall.cn.wuwei.ui.activity.MainActivity;
import com.weknowall.cn.wuwei.utils.PermissionsUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * User: laomao
 * Date: 2017-01-13
 * Time: 17-40
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.weknowall.cn.wuwei.R.layout.activity_splash);

        RxPermissions rxPermissions = new RxPermissions(SplashActivity.this);

        rxPermissions.requestEachCombined(PermissionsUtils.getMustLaunchPermissionNeverAsk(SplashActivity.this))
                .subscribe(permission -> {
                    toLoadData();
                });
    }

    private void toLoadData() {
        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    startActivity(MainActivity.class);
                    finish();
                });
    }

}
