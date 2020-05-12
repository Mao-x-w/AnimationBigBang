package com.example.lib_server.aidlImpl;

import android.os.RemoteException;

import com.weknowall.cn.wuwei.aidl.ICompute;

/**
 * User: laomao
 * Date: 2020/4/28
 * Time: 11:14 AM
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
