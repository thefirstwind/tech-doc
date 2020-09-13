package com.thefirstwind;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 本段代码是有问题的
 */
public class Case106_ReentrantLock3 {

    public static void main(String[] args){
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(()->{
            try{
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end");
            }catch(InterruptedException e){
                System.out.println("interrupted!");
            }finally{
                lock.unlock();
                System.out.println("unlock 1!");
            }
        });
        t1.start();

        Thread t2 = new Thread(()->{
            try{
//                lock.tryLock();
                lock.lockInterruptibly(); // 可以对interrupte方法做出相应
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");
            }catch(InterruptedException e){
                System.out.println("interrupted! 2");
            }finally {
                lock.unlock();
                System.out.println("unlock 2!");
            }
        });
        t2.start();

        try{
            TimeUnit.SECONDS.sleep(1);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        t2.interrupt(); // 打断线程2的等待
    }
}
