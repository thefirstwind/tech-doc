package com.thefirstwind;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Case109_ReentrantLock6 extends Thread{
    private static ReentrantLock lock = new ReentrantLock(true);
    public void run(){
        for(int i =1; i < 5; i ++){
            lock.lock();
            try{
                Thread.sleep(1);
                System.out.println(Thread.currentThread().getName() + "-" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    public static void main(String[] args){
        Case109_ReentrantLock6 rl = new Case109_ReentrantLock6();
        Thread th1 = new Thread(rl::run, "th1");
        Thread th2 = new Thread(rl::run, "th2");
        th1.start();
        th2.start();
    }
}
