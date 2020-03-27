package com.laomao.apt_api;

import android.os.Bundle;

/**
 * User: laomao
 * Date: 2020/2/26
 * Time: 下午7:08
 */
public class BundleUtils {

    public static <T> T get(Bundle bundle,String key){
        return (T)bundle.get(key);
    }

    public static <T> T get(Bundle bundle,String key,Object defaultValue){
        Object o=bundle.get(key);

        if (o==null){
            o=defaultValue;
        }
        return (T)o;
    }
}
