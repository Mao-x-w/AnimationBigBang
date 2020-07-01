package com.example.lib_httpclient.interceptor;

import android.util.Log;


import com.example.lib_httpclient.HttpConnection;
import com.example.lib_httpclient.HttpUrl;
import com.example.lib_httpclient.LMHttpClient;
import com.example.lib_httpclient.Request;
import com.example.lib_httpclient.Response;

import java.io.IOException;

/**
 * @author wangfei
 * @date 2019/10/10.
 */
public class ConnectionInterceptor implements Interceptor {
    @Override
    public Response intercept(InterceptorChain interceptorChain) throws IOException {
        Log.e("interceptor", "连接拦截器开始运行");
        Request request = interceptorChain.call.getRequest();
        LMHttpClient httpClient = interceptorChain.call.getHttpClient();
        HttpUrl httpUrl = request.getHttpUrl();

        HttpConnection httpConnection = httpClient.getConnectionPool().getHttpConnection(httpUrl.getHost(),httpUrl.getPort());
        if(null == httpConnection){
            httpConnection = new HttpConnection();
        }else{
            Log.e("interceptor", "从连接池中获得连接");
        }
        httpConnection.setRequest(request);

        try {
            Response response = interceptorChain.proceed(httpConnection);
            if (response.isKeepAlive()){
                httpClient.getConnectionPool().putHttpConnection(httpConnection);
            }else{
                httpConnection.close();
            }
            return response;
        }catch (IOException e){
            httpConnection.close();
            throw e;
        }
    }
}
