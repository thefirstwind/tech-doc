package com.thefirstwind;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

public class Case103_AtomicVsSyncVsLongAdder {

    static long count2 = 0L;
    static AtomicLong count1 = new AtomicLong(0);
    static LongAdder count3 = new LongAdder();

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[1000];

        for(int i = 0; i< threads.length; i ++){
            threads[i] = new Thread(()->{
                for(int k=0; k<100_000; k++) count1.getAndIncrement();
            });
        }
        long start = System.currentTimeMillis();

        // 启动线程
        for(Thread t : threads) t.start();
        // 结束线程
        for(Thread t : threads) t.join();
        long end = System.currentTimeMillis();
        System.out.println("AtomicLong: " + count1.get() + " time " + (end-start));


        //-----------------------------

        Object lock = new Object();
        for(int i = 0; i< threads.length; i ++){
            threads[i] = new Thread(new Runnable(){
                @Override
                public void run(){
                    for( int k =0; k < 100_000; k++){
                        synchronized ((lock)){
                            count2++;
                        }
                    }
                }
            });
        }

        // 启动线程
        for(Thread t : threads) t.start();
        // 结束线程
        for(Thread t : threads) t.join();
        System.out.println("synchronized: " + count2 + " time " + (end-start));

        //-----------------------------

        for(int i = 0; i< threads.length; i ++){
            threads[i] = new Thread(()->{
                for(int k=0; k<100_000; k++) count3.increment();
            });
        }

        start = System.currentTimeMillis();
        // 启动线程
        for(Thread t : threads) t.start();
        // 结束线程
        for(Thread t : threads) t.join();
        end = System.currentTimeMillis();

        System.out.println("LongAdder: " + count3.longValue() + " time " + (end-start));


    }
}
