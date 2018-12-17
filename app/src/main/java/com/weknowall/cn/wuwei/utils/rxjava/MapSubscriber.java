package com.weknowall.cn.wuwei.utils.rxjava;

/**
 * User: laomao
 * Date: 2018-11-27
 * Time: 15-54
 */
public class MapSubscriber<T, R> extends Subscriber<T> {

    private Subscriber<? super R> mSubscriber;
    private Observable.Transformer<? super T, ? extends R> mTransformer;

    public MapSubscriber(Subscriber<? super R> subscriber, Observable.Transformer<? super T, ? extends R> transformer) {
        mSubscriber = subscriber;
        mTransformer = transformer;
    }

    @Override
    public void onComplete() {
        mSubscriber.onComplete();
    }

    @Override
    public void onError(Throwable t) {
        mSubscriber.onError(t);
    }

    @Override
    public void onNext(T t) {
        mSubscriber.onNext(mTransformer.call(t));
    }
}
