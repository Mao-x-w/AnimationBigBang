package com.weknowall.cn.wuwei.utils.rxjava;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subscriptions.CompositeSubscription;

public class RxBus {
    private static volatile  RxBus mInstance;
    //Subject继承了Observable类又实现了Observer接口， Subject可以同时担当订阅者和被订阅者的角色
    private SerializedSubject<Object,Object> mSubject;
    //一个类产生多个Subscription对象，用一CompositeSubscription 存储起来，以进行批量的取消订阅。避免内存泄漏
    private HashMap<String,CompositeSubscription> mSubscriptionHashMap;
    private RxBus(){
        //Subject是非线程安全的,SerializedSubject将PublishSubject 转换成一个线程安全的Subject对象
        mSubject=new SerializedSubject<>(PublishSubject.create());
    }
    public static RxBus getInstance(){
        if(mInstance==null){
            synchronized (RxBus.class){
                if(mInstance==null){
                    mInstance=new RxBus();
                }
            }
        }
        return mInstance;
    }
    /**
     * 发生消息
     */
    public void post(Object o){
        mSubject.onNext(o);
    }

    /**
     * 返回指定类型的Observable实例
     * @param type:要处理的消息的类型
     * @param <T>
     * @return
     */
    public <T>Observable<T> toObservable(final Class<T> type){
        return mSubject.ofType(type);
    }

    /**
     * 是否已含有观察者订阅
     * @return
     */
    public boolean hasObservers(){
        return mSubject.hasObservers();
    }

    /**
     * 默认的订阅方法
     * @param <T>
     * @return
     */
    public <T>Subscription doSubscribe(Class<T> type, Action1<T> next){
        return toObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next);
    }
    /**
     * 默认的订阅方法
     * @param <T>
     * @return
     */
    public <T>Subscription doSubscribe(Class<T> type, Action1<T> next,Action1<Throwable> error){
        return toObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next,error);
    }
    /**
     * 默认的订阅方法
     * @param <T>
     * @return
     */
    public <T>Subscription doSubscribe(Class<T> type, Action1<T> next, Action1<Throwable> error, Action0 complete){
        return toObservable(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(next,error,complete);
    }

    /**
     * 保存订阅后的subscription,方便一次性取消订阅
     * @param o
     * @param subscription
     */
    public void addSubscription(Object o,Subscription subscription){
        if(mSubscriptionHashMap==null){
            mSubscriptionHashMap=new HashMap<>();
        }
        String key=o.getClass().getSimpleName();
        if(mSubscriptionHashMap.containsKey(key)){
            mSubscriptionHashMap.get(key).add(subscription);
        }else{
            CompositeSubscription compositeSubscription=new CompositeSubscription();
            compositeSubscription.add(subscription);
            mSubscriptionHashMap.put(key,compositeSubscription);
        }
    }

    /**
     * 取消订阅
     * @param o
     */
    public void unSubscribe(Object o){
        if(mSubscriptionHashMap==null){
            return;
        }
        String key=o.getClass().getSimpleName();
        if(!mSubscriptionHashMap.containsKey(key)){
            return;
        }
        if(mSubscriptionHashMap.get(key)!=null){
            mSubscriptionHashMap.get(key).unsubscribe();
        }
        mSubscriptionHashMap.remove(key);
    }
}