package com.example.lib_httpclient;

import com.example.lib_httpclient.other.Call;

import java.io.IOException;

/**
 * User: laomao
 * Date: 2020/6/17
 * Time: 6:56 PM
 */
public interface Callback {
    void onFailure(Call call, IOException e);

    void onResponse(Call call, Response response);
}
