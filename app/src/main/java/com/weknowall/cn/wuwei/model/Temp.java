package com.weknowall.cn.wuwei.model;

/**
 * User: laomao
 * Date: 2020/5/22
 * Time: 1:43 PM
 */
public class Temp {

    private static volatile Temp mInstance;

    private Temp() {
    }

    public static Temp getInstance() {
        if (mInstance == null) {
            synchronized (Temp.class) {
                if (mInstance == null) {
                    mInstance = new Temp();
                }
            }
        }
        return mInstance;
    }
}
