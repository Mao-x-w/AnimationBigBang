package com.weknowall.cn.wuwei.utils.rxjava;

/**
 * User: laomao
 * Date: 2018-11-27
 * Time: 15-51
 */
public class MapOnSubscribe<T, R> implements Observable.OnSubscribe<R> {

    private Observable mObservable;
    private Observable<T> mSource;
    private Observable.Transformer<? super T, ? extends R> mTransformer;

    public MapOnSubscribe(Observable observable, Observable<T> source, Observable.Transformer<? super T, ? extends R> transformer) {
        mObservable = observable;
        mSource = source;
        mTransformer = transformer;
    }

    @Override
    public void call(Subscriber<? super R> subscriber) {
        mSource.subscribe(new MapSubscriber<T,R>(subscriber, mTransformer));
    }
}
