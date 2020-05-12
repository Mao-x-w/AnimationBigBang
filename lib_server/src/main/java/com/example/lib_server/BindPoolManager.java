package com.example.lib_server;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.weknowall.cn.wuwei.aidl.IBinderPool;

import java.util.concurrent.CountDownLatch;

/**
 * 这个类直接写到了lib_server类库中了，如果是通过另一个App实现进程间通信的话，就得在当前app中写
 * <p>
 * 这里的aidl文件不用写2份，因为Service在单独的进程中，所以使用到的.stub也不是同一个
 * <p>
 * User: laomao
 * Date: 2020/4/28
 * Time: 11:48 AM
 */
public class BindPoolManager {

    private static volatile BindPoolManager mInstance;
    private final Context mApplicationContext;
    private CountDownLatch mCountDownLatch;
    private IBinderPool mIBinderPool;

    private BindPoolManager(Context context) {
        mApplicationContext = context.getApplicationContext();
        connectBinderPoolService();
    }

    public static BindPoolManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (BindPoolManager.class) {
                if (mInstance == null) {
                    mInstance = new BindPoolManager(context);
                }
            }
        }
        return mInstance;
    }

    private void connectBinderPoolService() {
        mCountDownLatch = new CountDownLatch(1);
        Intent intent = new Intent("com.example.lib_server.BinderPoolService");
        intent.setPackage("com.laomao.mvp");
        mApplicationContext.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mIBinderPool = IBinderPool.Stub.asInterface(service);

                try {
                    mIBinderPool.asBinder().linkToDeath(mDeathRecipient, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                mCountDownLatch.countDown();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);

        try {
            // 这里会一直等待，直到service建立连接成功，并调用了 mCountDownLatch.countDown()
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mIBinderPool.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mIBinderPool = null;
            connectBinderPoolService();
        }
    };


    public IBinder queryBinder(int binderCode) {
        IBinder binder = null;
        try {
            if (mIBinderPool != null)
                binder = mIBinderPool.queryBinder(binderCode);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return binder;
    }
}
