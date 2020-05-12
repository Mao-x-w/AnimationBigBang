package com.weknowall.cn.wuwei.utils.proxy;

import com.weknowall.cn.wuwei.utils.Logs;

/**
 * User: laomao
 * Date: 2020/5/9
 * Time: 7:34 PM
 */
public class OperateImpl implements IOperate {
    @Override
    public void loadData() {
        Logs.e("开始加载数据");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
