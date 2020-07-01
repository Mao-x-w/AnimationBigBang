package com.example.lib_httpclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * User: laomao
 * Date: 2020/6/16
 * Time: 4:06 PM
 */
public class RequestBody {

    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String CHARSET = "utf-8";

    Map<String, String> encodeBodys = new HashMap<>();

    public String getContentType() {
        return CONTENT_TYPE;
    }

    public int getContentLength() {
        return getBody().getBytes().length;
    }

    public String getBody() {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : encodeBodys.entrySet()) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }

        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public RequestBody add(String key, String value) {
        try {
            encodeBodys.put(URLEncoder.encode(key, CHARSET), URLEncoder.encode(value, CHARSET));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return this;
    }

}
