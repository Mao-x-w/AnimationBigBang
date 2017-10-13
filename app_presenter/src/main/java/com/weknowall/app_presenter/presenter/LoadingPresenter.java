package com.weknowall.app_presenter.presenter;

import android.support.annotation.StringRes;

import com.weknowall.app_data.exception.HttpResponseException;
import com.weknowall.app_domain.executor.PostExecutionThread;
import com.weknowall.app_domain.executor.ThreadExecutor;
import com.weknowall.app_presenter.subscriber.LoadingSubscriber;
import com.weknowall.app_presenter.view.ILoadingView;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * User: laomao
 * Date: 2017-01-19
 * Time: 10-19
 */

public abstract class LoadingPresenter<R,RQM, RQ, RPM, RP, V extends ILoadingView> extends ResultPresenter<RQM, RQ, RPM, RP, V> implements ILoadingView {

    private boolean canShowLoading = true;
    private R mRepository;

    private ThreadExecutor mWorkThread;
    private PostExecutionThread mResultThread;
    private Subscription mSubscription= Subscriptions.empty();

    public LoadingPresenter(R repository,ThreadExecutor workThread, PostExecutionThread resultThread) {
        super();
        mRepository = repository;
        mWorkThread = workThread;
        mResultThread = resultThread;
    }

    protected abstract Observable<RPM> buildUseCaseObservable(RQM... rqms);

    public void execute(Subscriber<RPM> rpmSubscriber, RQM... rqms){
        mSubscription = buildUseCaseObservable(rqms)
                .subscribeOn(Schedulers.from(mWorkThread))
                .observeOn(mResultThread.getScheduler())
                .unsubscribeOn(Schedulers.from(mWorkThread))
                .subscribe(rpmSubscriber);
    }

    public void unSubscribe(){
        mSubscription.unsubscribe();
    }

    public R getRepository(){
        return mRepository;
    }

    @Override
    public void execute(RQM... rqm) {
        showLoading();
        execute(new LoadingSubscriber<RPM>(this),rqm);
    }


    /**
     * Observable加载数据过程中走的方法
     *
     * @param rpm
     */
    @Override
    public void onNext(RPM rpm) {
        if (rpm == null)
            return;
        hideLoading();
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        hideLoading();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (e instanceof HttpResponseException) {
            showMessage(e.getMessage());
        }
//        else {
//            showMessage(R.string.load_net_error);
//        }
        hideLoading();
    }


    /**
     * Loading框的显示与隐藏，以及信息提示  start
     */
    @Override
    public void showLoading() {
        if (getView() != null && canShowLoading)
            getView().showLoading();
    }

    @Override
    public void hideLoading() {
        if (getView() != null && canShowLoading)
            getView().hideLoading();
    }

    @Override
    public void showMessage(String message) {
        if (getView() != null)
            getView().showMessage(message);
    }

    @Override
    public void showMessage(@StringRes int resId) {
        if (getView() != null)
            getView().showMessage(resId);
    }

    public void setCanShowLoading(boolean canShowLoading) {
        this.canShowLoading = canShowLoading;
    }

}
