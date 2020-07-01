package com.example.lib_httpclient;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: laomao
 * Date: 2020/6/16
 * Time: 11:45 AM
 */
public class Request {
    Map<String, String> headers = new HashMap<>();
    String method;
    HttpUrl httpUrl;
    RequestBody requestBody;

    private Request(Builder builder) {
        this.headers = builder.headers;
        this.method = builder.method;
        this.httpUrl = builder.httpUrl;
        this.requestBody = builder.requestBody;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getMethod() {
        return method;
    }

    public HttpUrl getHttpUrl() {
        return httpUrl;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public static final class Builder {
        Map<String, String> headers = new HashMap<>();
        String method;
        HttpUrl httpUrl;
        RequestBody requestBody;

        public Request build() {
            return new Request(this);
        }

        public Builder addHeader(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder removeHeader(String key) {
            this.headers.remove(key);
            return this;
        }

        public Builder setHttpUrl(String url) {
            try {
                this.httpUrl = new HttpUrl(url);
                return this;
            } catch (MalformedURLException e) {
                throw new IllegalStateException("http url format error!", e);
            }
        }

        public Builder post(RequestBody requestBody) {
            this.requestBody = requestBody;
            this.method = "POST";
            return this;
        }

        public Builder get() {
            this.method = "GET";
            return this;
        }
    }
}
