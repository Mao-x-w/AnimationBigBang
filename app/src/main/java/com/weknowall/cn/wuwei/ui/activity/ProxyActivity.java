package com.weknowall.cn.wuwei.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Engine;
import com.weknowall.cn.wuwei.R;
import com.weknowall.cn.wuwei.ui.BaseActivity;
import com.weknowall.cn.wuwei.utils.Logs;
import com.weknowall.cn.wuwei.utils.proxy.IOperate;
import com.weknowall.cn.wuwei.utils.proxy.OperateImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import retrofit2.Retrofit;

/**
 * User: laomao
 * Date: 2020/5/9
 * Time: 4:49 PM
 */
public class ProxyActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proxy);

        OperateImpl operateImpl=new OperateImpl();
        IOperate operate = (IOperate) Proxy.newProxyInstance(IOperate.class.getClassLoader(), new Class[]{IOperate.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                long start = System.currentTimeMillis();
                Object o = method.invoke(operateImpl, args);
                Logs.d("加载方法用时："+(System.currentTimeMillis()-start)*1.0/1000*1.0+"s");
                return o;
            }
        });

        operate.loadData();

    }
}
