package com.weknowall.cn.wuwei.utils.rxjava;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

/**
 * User: laomao
 * Date: 2018-12-03
 * Time: 16-56
 */

public class LooperScheduler extends Scheduler {

    private final Handler mHandler;

    public LooperScheduler(Executor executor, Looper looper) {
        super(executor);
        mHandler = new Handler(looper);
    }

    @Override
    public Worker createWorker() {
        return new HandlerWorker(mExecutor,mHandler);
    }

    static class HandlerWorker extends Worker{

        private Handler mHandler;

        public HandlerWorker(Executor executor, Handler handler) {
            super(executor);
            mHandler = handler;
        }

        @Override
        public void schedule(Runnable runnable) {
            mHandler.post(runnable);
        }
    }
}
