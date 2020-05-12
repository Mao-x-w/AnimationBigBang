package com.weknowall.cn.wuwei.utils.rxjava;

import java.util.concurrent.Executor;

/**
 * User: laomao
 * Date: 2018-11-27
 * Time: 16-10
 */

public class Scheduler {

    public Executor mExecutor;

    public Scheduler(Executor executor) {
        mExecutor = executor;
    }

    public Worker createWorker(){
        return new Worker(mExecutor);
    }

    public static class Worker{

        private Executor mExecutor;

        public Worker(Executor executor) {
            mExecutor = executor;
        }

        public void schedule(Runnable runnable){
            mExecutor.execute(runnable);
        }
    }
}
