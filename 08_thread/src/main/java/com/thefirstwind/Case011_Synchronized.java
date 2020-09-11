package com.thefirstwind;

public class Case011_Synchronized {

    private int count = 10;
    private Object o = new Object();

    public void m (){
        synchronized (o){
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }
}
