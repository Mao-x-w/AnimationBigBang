package com.weknowall.cn.wuwei.utils.rxjava;

import java.util.concurrent.Executors;

/**
 * User: laomao
 * Date: 2018-11-27
 * Time: 16-12
 */

public class Schedulers {

    private static final Scheduler ioScheduler=new Scheduler(Executors.newSingleThreadExecutor());

    public static Scheduler io(){
        return ioScheduler;
    }
}
