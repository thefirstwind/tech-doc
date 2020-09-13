package com.thefirstwind;

import java.util.concurrent.TimeUnit;

public class Case004_Volatile {
    volatile Boolean running = true;
    void m(){
        System.out.println("m " + Thread.currentThread().getName() + " start");
        while(running){
        }
        System.out.println("m " + Thread.currentThread().getName() + " end");
    }

    public static void main(String[] args){
        Case004_Volatile t = new Case004_Volatile();
        new Thread(t::m, "t1").start();
        new Thread(t::m, "t2").start();
        new Thread(t::m, "t3").start();
        try{
            Thread.sleep(100);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        t.running = false;

    }
}
