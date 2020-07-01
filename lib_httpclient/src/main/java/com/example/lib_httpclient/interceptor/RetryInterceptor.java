package com.example.lib_httpclient.interceptor;

import android.util.Log;


import com.example.lib_httpclient.Response;
import com.example.lib_httpclient.other.Call;

import java.io.IOException;

/**
 * @author wangfei
 * @date 2019/10/10.
 * 重连连接器
 */
public class RetryInterceptor implements Interceptor {
    @Override
    public Response intercept(InterceptorChain interceptorChain) throws IOException {
        Log.e("interceptor", "重试拦截器已运行");
        Call call = interceptorChain.call;
        IOException ioException = null;

        for (int i = 0; i < call.getHttpClient().getRetryTimes(); i++) {
            if (call.isCanceled()) {
                throw new IOException("this task is canceled");
            }
            try {
                return interceptorChain.proceed();
            } catch (IOException e) {
                ioException = e;
            }
        }
        throw ioException;
    }
}
