package com.example.lib_httpclient;

import android.text.TextUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * User: laomao
 * Date: 2020/6/16
 * Time: 4:06 PM
 */
public class HttpUrl {

    private String mHost;
    private String mProtocol;
    private String mFile;
    private int mPort;

    public HttpUrl(String url) throws MalformedURLException {

        URL localUrl = new URL(url);

        mHost = localUrl.getHost();
        mProtocol = localUrl.getProtocol();
        mFile = localUrl.getFile();
        mPort = localUrl.getPort();

        if (mPort == -1) {
            mPort = localUrl.getDefaultPort();
        }

        if (TextUtils.isEmpty(mFile)) {
            mFile = "/";
        }

    }

    public String getHost() {
        return mHost;
    }

    public String getProtocol() {
        return mProtocol;
    }

    public String getFile() {
        return mFile;
    }

    public int getPort() {
        return mPort;
    }
}
