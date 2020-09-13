package com.thefirstwind;

import java.util.concurrent.locks.ReentrantLock;

public class Case108_ReentrantLock5  extends Thread{
    private static ReentrantLock lock = new ReentrantLock(true);
    public void run(){
        for(int i =0; i < 100; i ++){
            lock.lock();
            try{
                Thread.sleep(1);
                System.out.println(Thread.currentThread().getName() + "获得锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    public static void main(String[] args){
        Case108_ReentrantLock5 rl = new Case108_ReentrantLock5();
        Thread th1 = new Thread(rl);
        Thread th2 = new Thread(rl);
        th1.start();
        th2.start();
    }
}
