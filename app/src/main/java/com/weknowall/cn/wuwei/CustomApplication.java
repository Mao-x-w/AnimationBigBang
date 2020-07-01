package com.weknowall.cn.wuwei;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.laomao.apt_api.ActivityBuilder;
import com.weknowall.app_common.Configuration;
import com.weknowall.app_presenter.dagger.modules.ApplicationModule;
import com.weknowall.cn.wuwei.dagger.components.ApplicationComponent;
import com.weknowall.cn.wuwei.dagger.components.DaggerApplicationComponent;
import com.weknowall.cn.wuwei.utils.AppFrontBack.AppFrontBackHelper;
import com.weknowall.cn.wuwei.utils.Logs;

import leakcanary.LeakCanary;

/**
 * User: mao
 * Date: 2017/2/12
 * Time: 11:47
 */

public class CustomApplication extends Application {

    private static CustomApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        Configuration.init(this);
        Configuration.getInstance().debug(BuildConfig.DEBUG);

//        ActivityBuilder.INSTANCE.init(this);

        AppFrontBackHelper helper=new AppFrontBackHelper();
        helper.register(this, new AppFrontBackHelper.OnAppStatusListener() {
            @Override
            public void onFront() {
                Toast.makeText(CustomApplication.this, "前台", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBack() {
                Toast.makeText(CustomApplication.this, "后台", Toast.LENGTH_SHORT).show();
            }
        });

        if (BuildConfig.DEBUG){
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);

        Logs.d("CustomApplication：：：onCreate");

    }

    public static CustomApplication getAppInstance(){
        return mInstance;
    }

    public ApplicationComponent getComponent(){
        return DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }
}
