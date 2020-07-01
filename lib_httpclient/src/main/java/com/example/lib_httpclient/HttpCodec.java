package com.example.lib_httpclient;

import android.graphics.Color;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * User: laomao
 * Date: 2020/6/17
 * Time: 2:07 PM
 */
public class HttpCodec {
    static final String CRLF = "\r\n";
    static final int CR = 13;//回车的ASCII码
    static final int LF = 10;//换行的ASCII码
    static final String SPACE = " ";//一个空格
    static final String HTTP_VERSION = "HTTP/1.1";//http的版本信息
    static final String COLON = ":";//冒号

    public static final String HEAD_HOST = "Host";
    public static final String HEAD_CONNECTION = "Connection";
    public static final String HEAD_CONTENT_TYPE = "Content-Type";
    public static final String HEAD_CONTENT_LENGTH = "Content-Length";
    public static final String HEAD_TRANSFER_ENCODING = "Transfer-Encoding";

    public static final String HEAD_VALUE_KEEP_ALIVE = "Keep-Alive";
    public static final String HEAD_VALUE_CHUNKED = "chunked";

    public static final String PROTOCOL_HTTPS = "https";
    public static final String PROTOCOL_HTTP = "http";

    public static final String ENCODE = "UTF-8";
    private final ByteBuffer mByteBuffer;

    public HttpCodec() {
        mByteBuffer = ByteBuffer.allocate(10 * 1024);
    }

    /**
     * 拼接request数据流，写入到socket通道
     * <p>
     * POST /index/home_feeds_7_1_3?source=android&format=json HTTP/1.1
     * User-Agent: model:android;Version:meishij7.2.3;udid:a4:50:46:ce:0f:22;channel:_360;imsi:null;statusbarHeight:32
     * Content-Type: application/x-www-form-urlencoded
     * Content-Length: 75
     * Host: t1api.meishi.cc
     * Connection: Keep-Alive
     * Accept-Encoding: gzip
     * <p>
     * page=1&source=android&format=json&lat=40.036306&lon=116.308165&cityCode=131
     *
     * @param os
     * @param request
     */
    public void writeRequest(OutputStream os, Request request) throws IOException {
        StringBuffer sb = new StringBuffer();
        //GET /v3/weather/weatherInfo?key=064a7778b8389441e30f91b8a60c9b23&city=%25E6%25B7%25B1%25E5%259C%25B3 HTTP/1.1
        sb.append(request.getMethod());
        sb.append(SPACE);
        sb.append(request.getHttpUrl().getFile());
        sb.append(SPACE);
        sb.append(HTTP_VERSION);
        sb.append(CRLF);

        // 拼接请求头
        Map<String, String> headers = request.getHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            sb.append(entry.getKey());
            sb.append(COLON);
            sb.append(SPACE);
            sb.append(entry.getValue());
            sb.append(CRLF);
        }

        sb.append(CRLF); // 请求头最后，再拼接一个回车换行

        // 拼接请求体
        RequestBody requestBody = request.getRequestBody();
        if (null != requestBody) {
            sb.append(requestBody.getBody());
        }

        os.write(sb.toString().getBytes());
        os.flush();
    }

    public String readLine(InputStream is) throws IOException {
        mByteBuffer.clear();
        mByteBuffer.mark();

        boolean isMaybeEofLine = false; // 可能为行结束的标志，当出现一个\r的时候，置为true，如果下一个是\n,就确定是行结束了

        byte b;

        while ((b = (byte) is.read()) != -1) {
            mByteBuffer.put(b);

            if (b == CR) { // 当读到了一个\r
                isMaybeEofLine = true;
            } else if (isMaybeEofLine) {
                if (b == LF) { // 读到了一个\n，意味着行结束了
                    byte[] lineBytes = new byte[mByteBuffer.position()]; // new一个一行数据大小的字节数组，position表示当前的位置
                    // Resets this buffer's position to the previously-marked position.
                    // 重置到标记的位置，以便从标记位置开始拿数据
                    mByteBuffer.reset(); //
                    mByteBuffer.get(lineBytes);

                    mByteBuffer.clear();
                    mByteBuffer.mark();
                    return new String(lineBytes, ENCODE);
                }
            }
        }

        throw new IOException("response read line error");
    }

    public Map<String, String> readHeaders(InputStream is) throws IOException {
        HashMap<String, String> headers = new HashMap<>();

        while (true) {
            String line = readLine(is);

            if (isEmptyLine(line)) {
                break;
            }

            int index = line.indexOf(":");

            if (index > 0) {
                String key = line.substring(0, index);

                // 这里加2，是因为value前面还有冒号和空格
                String value = line.substring(index + 2, line.length() - 2);

                headers.put(key, value);
            }

        }
        return headers;
    }

    /**
     * 根据长度读取字节数据
     *
     * @param is
     * @param length
     * @return
     * @throws IOException
     */
    public byte[] readBytes(InputStream is, int length) throws IOException {
        byte[] bytes = new byte[length];
        int readNum = 0;
        while (true) {
            readNum = is.read(bytes, readNum, length - readNum);

            if (readNum == length) {
                return bytes;
            }
        }
    }

    public String readChunked(InputStream is, int length) throws IOException {
        int len = -1;
        boolean isEmptyData = false;
        StringBuffer chunked = new StringBuffer();

        while (true) {
            if (len < 0) {

                // 读取的第一行是块的长度和回车
                String line = readLine(is);
                length += line.length();

                // 去掉\r\n
                line = line.substring(0, line.length() - 2);

                // 获取到块的长度，将16进制转换为10进制
                len = Integer.valueOf(line, 16);

                // 如果读到的是0，则再读一个\r\n就结束了
                isEmptyData = len == 0;
            } else {
                length += len + 2;// 这个貌似没用

                // 经过上面的操作，这个时候已经读到分块数据那里了，而上面已经知道这块数据的长度为len，而数据之后还有\r\n,一起读出来
                byte[] bytes = readBytes(is, len + 2);
                chunked.append(new String(bytes, ENCODE));
                len = -1;
                if (isEmptyData) {
                    return chunked.toString();
                }
            }
        }
    }

    /**
     * 判断是否为空行，如果读到的是/r/n，就意味是空行
     *
     * @param line
     * @return
     */
    private boolean isEmptyLine(String line) {
        return TextUtils.equals(line, CRLF);
    }

}
