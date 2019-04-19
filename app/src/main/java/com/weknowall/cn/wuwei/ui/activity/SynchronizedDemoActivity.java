package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.synchronizedDemo.Account;
import com.weknowall.cn.wuwei.utils.synchronizedDemo.MoneyRunnable;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2019-02-26
 * Time: 18-20
 */
public class SynchronizedDemoActivity extends BaseActivity {

    private Thread mThread1;
    private Thread mThread2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synchronized_demo);
        ButterKnife.bind(this);

//        SynchronizedRunnable runnable=new SynchronizedRunnable();
//        mThread1 = new Thread(new SynchronizedRunnable(),"thread1");
//        mThread2 = new Thread(new SynchronizedRunnable(),"thread2");

//        CounterRunnable runnable=new CounterRunnable();
//        mThread1=new Thread(runnable,"A");
//        mThread2=new Thread(runnable,"B");

        MoneyRunnable runnable=new MoneyRunnable(new Account());
        mThread1=new Thread(runnable,"A");
        mThread2=new Thread(runnable,"B");
    }

    @OnClick(R.id.synchronized_button)
    public void onViewClicked() {
        mThread1.start();
        mThread2.start();
    }
}
