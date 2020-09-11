package com.thefirstwind;

import java.util.concurrent.TimeUnit;

public class Case016_Synchronzied {

    /**
     * 面试题： 模拟银行账户
     * 堆业务写方法加锁
     * 堆业务读方法不加锁
     * 这样行不行？
     *
     * 该程序会造成脏读
     * 如果不允许脏读 必须要把 getBalance的 synchronzied打开
     */
    String name;
    double balance;

    public synchronized void set(String name, double balance){
        this.name = name;
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        this.balance = balance;
    }
    public /* synchronized */ double getBalance(String name){
        return this.balance;
    }

    public static void main(String[] args){
        Case016_Synchronzied a = new Case016_Synchronzied();
        new Thread(()->{
            a.set("zhangsan", 100.0);
        }).start();

        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(a.getBalance("zhangsan"));
        try{
            Thread.sleep(2000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(a.getBalance("zhangsan"));


    }
}
