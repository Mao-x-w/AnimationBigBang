package com.weknowall.app_presenter.dagger;

import com.weknowall.app_domain.executor.PostExecutionThread;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 16-20
 */

@Singleton
public class UIThread implements PostExecutionThread {

    @Inject
    public UIThread() {
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}
