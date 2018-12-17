package com.weknowall.cn.wuwei.utils.rxjava;

/**
 * User: laomao
 * Date: 2018-11-26
 * Time: 15-29
 */

public interface Observer<T> {
    void onComplete();
    void onError(Throwable t);
    void onNext(T t);
}
