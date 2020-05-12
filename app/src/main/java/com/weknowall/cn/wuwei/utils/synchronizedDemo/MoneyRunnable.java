package com.weknowall.cn.wuwei.utils.synchronizedDemo;

/**
 * User: laomao
 * Date: 2019-02-27
 * Time: 11-41
 */
public class MoneyRunnable implements Runnable {

    private Account mAccount;

    public MoneyRunnable(Account account) {
        mAccount = account;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        if ("A".equals(name)){
            synchronized (mAccount){
                for (int i = 0; i < 5; i++) {
                    mAccount.saveMoney(100);
                    mAccount.fetchMoney(100);
                    System.out.println(name+"取后的钱："+mAccount.money);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            for (int i = 0; i < 5; i++) {
                mAccount.fetchMoney(100);
                System.out.println(name+"取后的钱："+mAccount.money);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
