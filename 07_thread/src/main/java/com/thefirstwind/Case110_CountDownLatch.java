package com.thefirstwind;

import java.util.concurrent.CountDownLatch;

public class Case110_CountDownLatch {
    
    public static void main(String[] args){
        usingJoin();
        usingCountDownLatch();
    }

    private static void usingCountDownLatch() {
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);

        for( int i = 0; i < threads.length ; i++){
            threads[i] = new Thread(()->{
                int result = 0;
                for( int j =0; j < 10000; j++) result +=j;
                latch.countDown();
                // 100 -> 99 -> 98 -> ....
            });
        }
        // 所有线程开始
        for(int i = 0; i < threads.length; i++){
            threads[i].start();
        }

        try{
            // 所有线程的门栓开始锁
            latch.await();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("end latch");

    }

    private static void usingJoin() {
        Thread[] threads = new Thread[100];
        for( int i = 0; i < threads.length ; i++){
            threads[i] = new Thread(()->{
                int result = 0;
                for( int j =0; j < 10000; j++) result +=j;
            });
        }
        // 所有线程开始
        for(int i = 0; i < threads.length; i++){
            threads[i].start();
        }

        for(int i = 0; i < threads.length; i++) {
            try{
                // 线程结束、每一个线程都等着 合并在当前线程上
                threads[i].join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }

        System.out.println("end latch");
    }
}
