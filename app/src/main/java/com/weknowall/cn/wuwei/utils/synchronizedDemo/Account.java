package com.weknowall.cn.wuwei.utils.synchronizedDemo;

/**
 * User: laomao
 * Date: 2019-02-27
 * Time: 11-39
 */
public class Account {

    public int money;

    public Account() {
        money=520;
    }

    public void fetchMoney(int m) {
        money-=m;
    }

    public void saveMoney(int m) {
        money+= m;
    }
}
