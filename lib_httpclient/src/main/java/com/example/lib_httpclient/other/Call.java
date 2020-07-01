package com.example.lib_httpclient.other;

import com.example.lib_httpclient.Callback;
import com.example.lib_httpclient.LMHttpClient;
import com.example.lib_httpclient.Request;
import com.example.lib_httpclient.Response;
import com.example.lib_httpclient.interceptor.CallServiceInterceptor;
import com.example.lib_httpclient.interceptor.ConnectionInterceptor;
import com.example.lib_httpclient.interceptor.HeadersInterceptor;
import com.example.lib_httpclient.interceptor.Interceptor;
import com.example.lib_httpclient.interceptor.InterceptorChain;
import com.example.lib_httpclient.interceptor.RetryInterceptor;


import java.io.IOException;
import java.util.ArrayList;

/**
 * @author wangfei
 * @date 2019/10/10.
 */
public class Call {

    private LMHttpClient httpClient;
    private Request request;

    public Request getRequest() {
        return request;
    }

    public LMHttpClient getHttpClient() {
        return httpClient;
    }

    //TODO 是否被执行过
    boolean executed;

    //TODO 是否被取消了
    boolean canceled;

    public boolean isCanceled() {
        return canceled;
    }

    public Call(LMHttpClient httpClient, Request request) {
        this.httpClient = httpClient;
        this.request = request;
    }

    /**
     * 拦截器进行处理 获取返回
     *
     * @return
     * @throws IOException
     */
    Response getResponse() throws IOException {
        ArrayList<Interceptor> interceptors = new ArrayList<>();
        interceptors.addAll(httpClient.getInterceptors());
        interceptors.add(new RetryInterceptor());
        interceptors.add(new HeadersInterceptor());
        interceptors.add(new ConnectionInterceptor());
        interceptors.add(new CallServiceInterceptor());
        InterceptorChain interceptorChain = new InterceptorChain(interceptors, 0, this, null);
        Response response = interceptorChain.proceed();
        return response;
    }


    /**
     * 将Call对象放到调度器里面去执行，如果已经加过了，就不能加了
     *
     * @param callback
     * @return
     */
    public Call enqueue(Callback callback) {

        synchronized (this) {
            if (executed) {
                throw new IllegalStateException("This Call Already Executed!");
            }
            executed = true;
        }
        httpClient.getDispatcher().enqueue(new AsyncCall(callback));
        return this;
    }


    final class AsyncCall implements Runnable {

        private Callback callback;

        public AsyncCall(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void run() {

            boolean signalledCallback = false;
            try {
                Response response = getResponse();
                if (canceled) {
                    signalledCallback = true;
                    callback.onFailure(Call.this, new IOException("this task had canceled"));
                } else {
                    signalledCallback = true;
                    callback.onResponse(Call.this, response);
                }
            } catch (IOException e) {
                if (!signalledCallback) {
                    callback.onFailure(Call.this, e);
                }
            } finally {
                //将这个任务从调度器移除
                httpClient.getDispatcher().finished(this);
            }
        }

        public String getHost() {
            return request.getHttpUrl().getHost();
        }
    }
}
