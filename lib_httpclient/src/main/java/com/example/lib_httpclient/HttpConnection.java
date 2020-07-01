package com.example.lib_httpclient;

import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.ssl.SSLSocketFactory;

/**
 * User: laomao
 * Date: 2020/6/17
 * Time: 4:50 PM
 */
public class HttpConnection {

    private Socket mSocket;
    public long lastUseTime;

    private Request mRequest;
    private InputStream mInputStream;
    private OutputStream mOutputStream;

    public HttpConnection() {
    }

    public void updateLastUseTime() {
        lastUseTime = System.currentTimeMillis();
    }

    public boolean isSameAddress(String host, int port) {
        if (null == mSocket)
            return false;
        return TextUtils.equals(mRequest.getHttpUrl().getHost(), host) && mRequest.getHttpUrl().getPort() == port;
    }

    public void setRequest(Request request) {
        mRequest = request;
    }

    /**
     * 创建socket连接
     */
    private void createSocket() throws IOException {
        if (null == mSocket || mSocket.isClosed()) {
            HttpUrl httpUrl = mRequest.getHttpUrl();
            if (httpUrl.getProtocol().equalsIgnoreCase(HttpCodec.PROTOCOL_HTTPS)) {
                // 如果是https，需要使用SSLSocketFactory来创建socket
                mSocket = SSLSocketFactory.getDefault().createSocket();
            } else {
                mSocket = new Socket();
            }

            mSocket.connect(new InetSocketAddress(httpUrl.getHost(), httpUrl.getPort()));
            mInputStream = mSocket.getInputStream();
            mOutputStream = mSocket.getOutputStream();
        }
    }

    /**
     * 关闭socket的连接
     */
    public void close() {
        if (null != mSocket) {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public InputStream call(HttpCodec httpCodec) throws IOException {
        // 创建socket
        createSocket();
        // 发送请求
        httpCodec.writeRequest(mOutputStream, mRequest);
        return mInputStream;
    }
}
