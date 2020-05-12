package com.example.lib_server.aidlImpl;

import android.os.IBinder;
import android.os.RemoteException;

import com.example.lib_server.BinderCodeConstant;
import com.weknowall.cn.wuwei.aidl.IBinderPool;

/**
 * User: laomao
 * Date: 2020/4/28
 * Time: 11:15 AM
 */
public class BinderPoolImpl extends IBinderPool.Stub {

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case BinderCodeConstant.BINDER_SECURITY_CENTER:
                binder = new SecurityCenterImpl();
                break;
            case BinderCodeConstant.BINDER_COMPUTE:
                binder = new ComputeImpl();
                break;
        }
        return binder;
    }
}
