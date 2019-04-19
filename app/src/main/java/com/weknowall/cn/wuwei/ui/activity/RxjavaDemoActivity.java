package com.weknowall.cn.wuwei.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.Logs;
import com.weknowall.cn.wuwei.utils.rxjava.AndroidSchedulers;
import com.weknowall.cn.wuwei.utils.rxjava.Observable;
import com.weknowall.cn.wuwei.utils.rxjava.Schedulers;
import com.weknowall.cn.wuwei.utils.rxjava.Subscriber;


/**
 * User: laomao
 * Date: 2018-10-26
 * Time: 10-12
 *
 * 自己实现的Rxjava
 */

public class RxjavaDemoActivity extends BaseActivity {

    Handler mHandler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava_demo);

        initData();

        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }.execute("1111");
    }

    private void initData() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                Logs.e("OnSubscribe的call所在线程"+Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io()).map1(new Observable.Transformer<Integer, String>() {
            @Override
            public String call(Integer integer) {
                Logs.e("subscribeOn之后observerOn之前所在线程  所在线程"+Thread.currentThread().getName());

                return "字符串"+integer;
            }
        }).observerOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {

            @Override
            public void onStart() {
                super.onStart();
                Logs.e("onStart所在线程"+Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onNext(String s) {
                Logs.e("onNext所在线程"+Thread.currentThread().getName());

                Logs.e("自己实现的rxjava map:::"+s);
            }
        });
    }
}
