package com.thefirstwind;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读锁 + 写锁
 * 读锁：共享锁
 * 写锁：排他锁
 */
public class Case112_ReadWriteLock {
    // 当前这个锁，一旦加锁，其他人是无法拿到的（资源独占)
    static Lock lock = new ReentrantLock();

    private static int value;

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public static void read(Lock lock){
        try{
            // 读的时候上锁
            lock.lock();
            Thread.sleep(1000);
            System.out.println("read over");
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    public static void write(Lock lock, int v){
        try{
            lock.lock();
            Thread.sleep(1000);
            value = v;
            System.out.println("write over!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args){
//        Runnable readR = ()-> read(lock);
//        Runnable writeR = ()->write(lock, new Random().nextInt());
        Runnable readR = ()->read(readLock);
        Runnable writeR = ()->write(writeLock, new Random().nextInt());

        for(int i =0; i< 18; i++) new Thread(readR).start();
        for(int i =0; i< 2; i++) new Thread(writeR).start();
    }
}
