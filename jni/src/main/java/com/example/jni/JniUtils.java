package com.example.jni;

import java.lang.reflect.Method;

public class JniUtils {

    //固定写法，表示我们要加载的资源文件为libhello.so
    static {
        System.loadLibrary("native-lib");
    }

    public static native void replace(Method wrongClass, Method method);

    public static native String getString();

}
