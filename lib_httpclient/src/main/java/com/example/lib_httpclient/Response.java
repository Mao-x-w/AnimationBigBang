package com.example.lib_httpclient;

import java.util.HashMap;
import java.util.Map;

/**
 * User: laomao
 * Date: 2020/6/16
 * Time: 11:45 AM
 */
public class Response {
    int code;
    int contentLength = -1;
    Map<String, String> headers = new HashMap<>();
    String body;
    boolean isKeepAlive;

    public Response() {
    }

    public Response(int code, int contentLength, Map<String, String> headers, String body, boolean isKeepAlive) {
        this.code = code;
        this.contentLength = contentLength;
        this.headers = headers;
        this.body = body;
        this.isKeepAlive = isKeepAlive;
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

    public int getCode() {
        return code;
    }

    public int getContentLength() {
        return contentLength;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public boolean isKeepAlive() {
        return isKeepAlive;
    }
}
