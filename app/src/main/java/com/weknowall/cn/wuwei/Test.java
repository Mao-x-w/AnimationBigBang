package com.weknowall.cn.wuwei;

import android.content.Context;
import android.content.Intent;

import com.weknowall.cn.wuwei.ui.activity.KotlinDemoActivity;

/**
 * User: laomao
 * Date: 2020/2/25
 * Time: 下午5:25
 */
public class Test {

    public void start(Context context){
        Intent intent=new Intent(context,KotlinDemoActivity.class);
        intent.putExtra("id","11111");
        context.startActivity(intent);
    }

}
