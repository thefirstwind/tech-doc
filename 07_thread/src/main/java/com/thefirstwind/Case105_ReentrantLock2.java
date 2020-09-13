package com.thefirstwind;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Case105_ReentrantLock2 {

    Lock lock = new ReentrantLock();

    /**
     * 加锁之后代码段m，同一时间只能被一个线程使用
     */
    void m1(){
        try{
            lock.lock();
            for(int i = 0 ; i < 3; i++){
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
//                if(i % 2 == 0 ) lock.t();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    synchronized void m2(){
        try{
            lock.lock();
            System.out.println("m2....");

        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args){

        // 生命一个可重入锁
        Case105_ReentrantLock2 r1 = new Case105_ReentrantLock2();
        new Thread(r1::m1).start();
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        new Thread(r1::m2).start();
    }
}
