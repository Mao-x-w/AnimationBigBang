package com.weknowall.app_presenter.presenter;

import android.support.annotation.NonNull;

import com.weknowall.app_presenter.view.IResultView;

import rx.Observer;

/**
 * User: laomao
 * Date: 2017-01-18
 * Time: 18-33
 */

public abstract class ResultPresenter<RQM,RQ,RPM,RP,V extends IResultView> implements IPresenter,IResultView, Observer<RPM> {

    private V view;

    public ResultPresenter() {
    }

    public void setView(@NonNull V view) {
        this.view = view;
    }

    public V getView(){
        return view;
    }

    public abstract void initialize(RQ... rqs);

    public abstract void execute(RQM... rqm);

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
    }


    /**
     * Observable中调用onCompleted{@link Observer#onCompleted()}
     */
    @Override
    public void onCompleted() {
        onSuccess();
        onFinish();
    }

    /**
     * Observable中调用onError{@link Observer#onError(Throwable)}
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onError();
        onFinish();
    }

    /**
     * Observable中调用onNext{@link Observer#onNext(Object)}
     */
    @Override
    public void onNext(RPM rpm) {

    }



    /**
     * View中调用
     */
    @Override
    public void onSuccess() {
        if (getView()!=null)
            getView().onSuccess();
    }

    /**
     * View中调用
     */
    @Override
    public void onError() {
        if (getView()!=null)
            getView().onError();
        onError(getClass());
    }

    /**
     * View中调用
     */
    @Override
    public void onError(Class clazz) {
        if (getView()!=null)
            getView().onError(clazz);
    }

    /**
     * View中调用
     */
    @Override
    public void onFinish() {
        if (getView()!=null)
            getView().onFinish();
    }

}
