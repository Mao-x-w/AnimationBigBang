package com.example.lib_httpclient.interceptor;

import com.example.lib_httpclient.HttpConnection;
import com.example.lib_httpclient.Response;
import com.example.lib_httpclient.other.Call;

import java.io.IOException;
import java.util.List;

/**
 * @author wangfei
 * @date 2019/10/10.
 */
public class InterceptorChain {
    final Call call;
    final List<Interceptor> interceptors;
    final int index;
    HttpConnection httpConnection;

    public InterceptorChain(List<Interceptor> interceptors, int index, Call call, HttpConnection httpConnection) {
        this.call = call;
        this.interceptors = interceptors;
        this.index = index;
        this.httpConnection = httpConnection;
    }

    public Response proceed(HttpConnection httpConnection) throws IOException {
        this.httpConnection = httpConnection;
        return proceed();
    }

    public Response proceed() throws IOException {
        if (index > interceptors.size()) {
            throw new IOException("Interceptor Chain Error");
        }
        Interceptor interceptor = interceptors.get(index);
        InterceptorChain next = new InterceptorChain(interceptors, index + 1, call, httpConnection);
        return interceptor.intercept(next);
    }
}
