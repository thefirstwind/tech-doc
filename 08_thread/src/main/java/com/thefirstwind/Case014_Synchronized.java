package com.thefirstwind;

public class Case014_Synchronized {

    public static int count = 10;

    public synchronized  static void m(){
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm(){
        synchronized (Case014_Synchronized.class){
            count--;
        }
    }
}
