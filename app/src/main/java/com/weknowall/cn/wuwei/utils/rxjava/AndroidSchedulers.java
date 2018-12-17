package com.weknowall.cn.wuwei.utils.rxjava;

import android.os.Looper;

/**
 * User: laomao
 * Date: 2018-12-03
 * Time: 16-53
 */

public class AndroidSchedulers {


    private static AndroidSchedulers mInstance;
    private LooperScheduler mLooperScheduler;

    private AndroidSchedulers() {
        if (mLooperScheduler == null)
            mLooperScheduler = new LooperScheduler(null, Looper.getMainLooper());
    }

    private static AndroidSchedulers getInstance() {
        if (mInstance == null) {
            mInstance = new AndroidSchedulers();
        }
        return mInstance;
    }

    public static Scheduler mainThread() {
        return getInstance().mLooperScheduler;
    }
}
