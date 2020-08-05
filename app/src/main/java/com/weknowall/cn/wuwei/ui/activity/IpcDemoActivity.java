package com.weknowall.cn.wuwei.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.lib_server.BindPoolManager;
import com.example.lib_server.BinderCodeConstant;
import com.example.lib_server.TestService;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.aidl.ICompute;
import com.weknowall.cn.wuwei.aidl.IMyAidlInterface;
import com.weknowall.cn.wuwei.aidl.ISecurityCenter;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.Logs;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User: laomao
 * Date: 2020/3/27
 * Time: 3:34 PM
 */
public class IpcDemoActivity extends BaseActivity {

    private static String TAG = "IpcDemoActivity";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            Log.d(TAG, "收到messenger的回复：：：：" + bundle.getString("reply"));
        }
    };

    private Messenger mMessenger = new Messenger(mHandler);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc_demo);
        ButterKnife.bind(this);
    }

    /**
     * 使用bindService方法启动Service，Service随着调用者销毁而销毁，而且onBind方法只执行一次
     * 使用startService启动的，Service一直存在，onStartCommand多次执行
     * 无论使用哪种方法启动，onCreate都只执行一次
     *
     * @param view
     */
    @OnClick({R.id.bundle_test, R.id.messager_test, R.id.aidl_test, R.id.content_provider_test, R.id.binder_pool_test})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bundle_test:
                Intent intent = new Intent(getContext(), TestService.class);
                intent.putExtra("name", "bundle请求Server");
                intent.putExtra("type", "1");
                //
                bindService(intent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {

                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                }, BIND_AUTO_CREATE);
                break;
            case R.id.messager_test:
                Intent messagerIntent = new Intent(getContext(), TestService.class);
                messagerIntent.putExtra("name", "messenger请求Server");
                messagerIntent.putExtra("type", "2");
                bindService(messagerIntent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        Messenger messenger = new Messenger(service);
                        Message message = Message.obtain();
                        Bundle bundle = new Bundle();
                        bundle.putString("desc", "hello service,this is messenger client");
                        message.setData(bundle);
                        message.replyTo = mMessenger;
                        try {
                            messenger.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                }, BIND_AUTO_CREATE);

                break;
            case R.id.aidl_test:
                Intent aidlIntent = new Intent(getContext(), TestService.class);
                aidlIntent.putExtra("name", "aidl请求Server");
                aidlIntent.putExtra("type", "3");
                bindService(aidlIntent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        IMyAidlInterface myAidlInterface = IMyAidlInterface.Stub.asInterface(service);
                        try {
                            Log.d(TAG, "调用aidl接口的方法：" + myAidlInterface.getValue());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                }, BIND_AUTO_CREATE);
                break;
            case R.id.content_provider_test:
                Uri uri = Uri.parse("content://com.example.lib_server.CustomContentProvider/book");

                Cursor cursor = getContentResolver().query(uri, new String[]{"id", "name"}, null, null, null);

                if (cursor == null)
                    return;

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);

                    Logs.d("id:::" + id + "     name:::::" + name);
                }

                cursor.close();
                break;
            case R.id.binder_pool_test:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 这里连接线程池是耗时的，所以放到子线程了
                        testBindPool();
                    }
                }).start();
                break;
        }
    }

    private void testBindPool() {
        BindPoolManager bindPoolManager = BindPoolManager.getInstance(getApplicationContext());


        IBinder securityBinder = bindPoolManager.queryBinder(BinderCodeConstant.BINDER_SECURITY_CENTER);
        ISecurityCenter iSecurityCenter = ISecurityCenter.Stub.asInterface(securityBinder);

        try {
            String encrypt = iSecurityCenter.encrypt("hello 安卓");
            Logs.d("编码后的数据：：：：" + encrypt);
            String decrypt = iSecurityCenter.decrypt(encrypt);
            Logs.d("解码后的数据：：：：" + decrypt);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


        IBinder computeBinder = bindPoolManager.queryBinder(BinderCodeConstant.BINDER_COMPUTE);
        ICompute iCompute = ICompute.Stub.asInterface(computeBinder);

        try {
            int add = iCompute.add(1, 2);
            Logs.d("1+2的值是：：：："+add);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
