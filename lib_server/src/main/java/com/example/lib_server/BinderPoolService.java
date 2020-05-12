package com.example.lib_server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.lib_server.aidlImpl.BinderPoolImpl;

/**
 * User: laomao
 * Date: 2020/4/28
 * Time: 11:29 AM
 */
public class BinderPoolService extends Service {

    private Binder mBinderPool = new BinderPoolImpl();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinderPool;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
