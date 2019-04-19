package com.weknowall.cn.wuwei.utils.synchronizedDemo;

/**
 * User: laomao
 * Date: 2019-02-27
 * Time: 10-01
 */
public class SynchronizedRunnable implements Runnable {

    private static int count;

    public SynchronizedRunnable() {
        count=0;
    }

    @Override
    public void run() {
        synchronized (this){
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+":"+count++);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
