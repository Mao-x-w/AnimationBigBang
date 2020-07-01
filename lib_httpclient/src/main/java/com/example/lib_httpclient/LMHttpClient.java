package com.example.lib_httpclient;

import com.example.lib_httpclient.interceptor.Interceptor;
import com.example.lib_httpclient.other.Call;
import com.example.lib_httpclient.other.ConnectionPool;
import com.example.lib_httpclient.other.Dispather;

import java.util.List;

/**
 * User: laomao
 * Date: 2020/6/16
 * Time: 11:45 AM
 */
public class LMHttpClient {

    private Dispather dispatcher;
    private List<Interceptor> interceptors;
    private int retryTimes;
    private ConnectionPool connectionPool;

    private LMHttpClient(Builder builder) {
        this.dispatcher = builder.dispatcher;
        this.interceptors = builder.interceptors;
        this.retryTimes = builder.retryTimes;
        this.connectionPool = builder.connectionPool;
    }

    public Call newCall(Request request) {
        return new Call(this, request);
    }

    public static class Builder {

        private Dispather dispatcher;
        private List<Interceptor> interceptors;
        private int retryTimes;
        private ConnectionPool connectionPool;

        public LMHttpClient build() {

            return new LMHttpClient(this);
        }

        public Builder setDispatcher(Dispather dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
            return this;
        }

        public Builder setRetryTimes(int retryTimes) {
            this.retryTimes = retryTimes;
            return this;
        }

        public Builder setConnectionPool(ConnectionPool connectionPool) {
            this.connectionPool = connectionPool;
            return this;
        }
    }

    public Dispather getDispatcher() {
        return dispatcher;
    }

    public List<Interceptor> getInterceptors() {
        return interceptors;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public ConnectionPool getConnectionPool() {
        return connectionPool;
    }
}
