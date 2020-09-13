package com.thefirstwind;

import java.util.concurrent.TimeUnit;

public class Case104_ReentrantLock1 {

    /**
     * 加锁之后代码段m，同一时间只能被一个线程使用
     */
    synchronized void m1(){
        for(int i = 0 ; i < 10; i++){
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            System.out.println(i);

            if(i % 2 == 0) m2();
        }
    }
    synchronized void m2(){
        System.out.println("m2....");
    }

    public static void main(String[] args){

        // 生命一个可重入锁
        Case104_ReentrantLock1 r1 = new Case104_ReentrantLock1();
        new Thread(r1::m1).start();
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        new Thread(r1::m2).start();
    }
}
