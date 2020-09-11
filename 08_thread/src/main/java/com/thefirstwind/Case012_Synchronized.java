package com.thefirstwind;

public class Case012_Synchronized {

    private int count = 10;

    public void m(){
        synchronized (this){
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }
}
