package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Engine;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * User: laomao
 * Date: 2020/6/1
 * Time: 5:27 PM
 */
public class ReferenceDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_demo);

        testSoftReference();

    }

    private void testSoftReference() {
        String str=new String("abc");

        ReferenceQueue<String> referenceQueue=new ReferenceQueue<>();
        SoftReference<String> softReference=new SoftReference<>(str,referenceQueue);

        str=null;

        System.gc();

        System.out.println(softReference.get());

        Reference<? extends String> reference = referenceQueue.poll();

        System.out.println(reference);
    }


}
