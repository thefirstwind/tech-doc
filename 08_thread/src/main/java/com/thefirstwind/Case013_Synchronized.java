package com.thefirstwind;

public class Case013_Synchronized {
    private int count = 10;

    public synchronized void m(){
        count--;
        System.out.println(Thread.currentThread().getName() + "count = " + count);
    }
}
