package com.weknowall.cn.wuwei.algorithm;

/**
 * User: laomao
 * Date: 2020/5/26
 * Time: 10:19 AM
 */
public class StringParseASCII {

    public static void main(String[] args) {
        stringToAscii("abx");
    }

    private static void stringToAscii(String str) {

        StringBuilder sb=new StringBuilder();
        char tem;
        for (int i = 0; i < str.length(); i++) {
            tem = (char) (str.charAt(i) + 5);

            sb.append(tem>'z'?'a':tem);
        }

        System.out.println(sb.toString());
    }

}
