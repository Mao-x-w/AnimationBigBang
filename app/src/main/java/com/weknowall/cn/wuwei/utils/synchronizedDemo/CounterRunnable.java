package com.weknowall.cn.wuwei.utils.synchronizedDemo;

/**
 * User: laomao
 * Date: 2019-02-27
 * Time: 11-08
 */
public class CounterRunnable implements Runnable {
    private int count;

    public CounterRunnable() {
        this.count = 0;
    }

    private void countAdd(){
        synchronized (this){
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName()+"："+count++);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void printCount(){
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName()+":count"+count);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        if ("A".equals(name)){
            countAdd();
        }else if ("B".equals(name)){
            printCount();
        }
    }
}
