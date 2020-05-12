package com.example.lib_server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.weknowall.cn.wuwei.aidl.IMyAidlInterface;

/**
 * User: laomao
 * Date: 2020/3/27
 * Time: 3:06 PM
 */
public class TestService extends Service {

    private static final String TAG = "TestService";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        String name = intent.getStringExtra("name");
        Log.d(TAG, "收到了数据：：：：" + name);
        switch (intent.getStringExtra("type")) {
            case "1":
                return new MyEmptyBinder();
            case "2":
                return mMessenger.getBinder();
            case "3":
                return new MyBinder();
            default:
                return new MyEmptyBinder();
        }

    }


    private Handler mMessengerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            Log.d(TAG, "收到了数据：：：：" + bundle.getString("desc"));

            // 回复数据
            Messenger replyMessenger = msg.replyTo;
            Message message = Message.obtain();
            Bundle bundle1 = new Bundle();
            bundle1.putString("reply", "hi,I get your message,nice to meet you");
            message.setData(bundle1);
            try {
                replyMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    private Messenger mMessenger = new Messenger(mMessengerHandler);

    class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public String getValue() throws RemoteException {
            return "我是从aidl返回的数据";
        }
    }

    class MyEmptyBinder extends Binder {

    }
}
