package com.weknowall.cn.wuwei.algorithm;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * User: laomao
 * Date: 2020/6/1
 * Time: 6:17 PM
 */
public class ReferenceDemo {

    public static void main(String[] args) {
        testSoftReference();
    }


    private static void testSoftReference() {
        String str = new String("abc");

        ReferenceQueue<String> referenceQueue = new ReferenceQueue<>();
        SoftReference<String> softReference = new SoftReference<>(str, referenceQueue);

        str = null;

        System.gc();

        System.out.println(softReference.get());

        Reference<? extends String> reference = referenceQueue.poll();

        System.out.println(reference);
    }
}
