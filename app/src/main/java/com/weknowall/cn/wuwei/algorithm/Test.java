package com.weknowall.cn.wuwei.algorithm;

import com.weknowall.cn.wuwei.algorithm.model.Son;
import com.weknowall.cn.wuwei.utils.rxjava.Observable;
import com.weknowall.cn.wuwei.utils.rxjava.Subscriber;

import rx.Observer;
import rx.functions.Func1;

/**
 * User: laomao
 * Date: 2020/5/26
 * Time: 5:06 PM
 */
public class Test {

    public static void main(String[] args) {
//        Son son=new Son("儿子");

//        observableTest();
        rxObservableTest();
    }

    private static void rxObservableTest() {
        rx.Observable.create(new rx.Observable.OnSubscribe<Integer>() {
            @Override
            public void call(rx.Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    System.out.println("start:::" + i);
                    subscriber.onNext(i);
                }
                subscriber.onCompleted();
            }
        }).flatMap(new Func1<Integer, rx.Observable<String>>() {
            @Override
            public rx.Observable<String> call(Integer integer) {
                return rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
                    @Override
                    public void call(rx.Subscriber<? super String> subscriber) {
                        System.out.println("flatMap:::" + integer + "!!!");
                        subscriber.onNext(integer + "!!!");
                    }
                });
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("subscribe:::" + s);
            }
        });
    }

    private static void observableTest() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    System.out.println("start:::" + i);
                    subscriber.onNext(i);
                }
            }
        }).flatMap(new Observable.Transformer<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                return Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        System.out.println("flatMap:::" + integer + "!!!");
                        subscriber.onNext(integer + "!!!");
                    }
                });
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("subscribe:::" + s);
            }
        });
    }
}
