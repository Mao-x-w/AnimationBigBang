package com.weknowall.app_data.repository;

import com.weknowall.app_data.store.DataStoreFactoryImpl;
import com.weknowall.app_data.store.IDataStore;
import com.weknowall.app_domain.executor.ThreadExecutor;

import org.reactivestreams.Subscriber;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.schedulers.Schedulers;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 10-36
 */

@Singleton
public class RepositoryImpl<D extends IDataStore,F extends DataStoreFactoryImpl<D>> {

    private F mDataStoreFactory;
    private ThreadExecutor threadExecutor;

    @Inject
    public RepositoryImpl(F dataStoreFactory) {
        mDataStoreFactory = dataStoreFactory;
    }

    @Inject
    public void provideThreadExecutor(ThreadExecutor threadExecutor) {
        this.threadExecutor = threadExecutor;
    }

    public F getDataStoreFactory() {
        return mDataStoreFactory;
    }

    D getNetDataStore(){
        return mDataStoreFactory.getNetDataStore();
    }

    D getCacheDataStore(){
        return mDataStoreFactory.getCacheDataStore();
    }

    /**
     * 先从缓存加载数据然后再从网络中加载
     * @param cache 缓存加载项
     * @param api 网络加载项
     * @param <T> 泛型
     * @return
     */
    <T> Observable<T> getCacheThenApi(Observable<T> cache, Observable<T> api){
        return Observable.create(emitter -> {
            cache.subscribe(t -> {
                emitter.onNext(t);
                executeApi(emitter, api);
            },e -> {
                executeApi(emitter, api);
            });
        });
    }

    /**
     * 从网络加载数据
     * @param sb
     * @param api
     * @param <T>
     */
    private <T> void executeApi(ObservableEmitter<T> emitter, Observable<T> api) {
        api.subscribeOn(Schedulers.from(threadExecutor))
                .subscribe(emitter::onNext,emitter::onError,emitter::onComplete);
    }

}
