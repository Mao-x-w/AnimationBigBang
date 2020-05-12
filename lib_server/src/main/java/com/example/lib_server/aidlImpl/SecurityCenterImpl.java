package com.example.lib_server.aidlImpl;

import android.os.RemoteException;

import com.weknowall.cn.wuwei.aidl.ISecurityCenter;

/**
 * User: laomao
 * Date: 2020/4/28
 * Time: 10:54 AM
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub {

    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
