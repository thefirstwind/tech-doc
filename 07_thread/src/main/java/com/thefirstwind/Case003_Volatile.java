package com.thefirstwind;

import java.util.concurrent.TimeUnit;

public class Case003_Volatile {
    boolean running = true;
    void m(){
        System.out.println("m " + Thread.currentThread().getName() + " start");
        while(running){
            System.out.println("m " + Thread.currentThread().getName() + " running");
        }
        System.out.println("m " + Thread.currentThread().getName() + " end");
    }

    public static void main(String[] args){
        Case003_Volatile t = new Case003_Volatile();
        new Thread(t::m, "t1").start();
        new Thread(t::m, "t2").start();
        new Thread(t::m, "t3").start();

        try{
            TimeUnit.SECONDS.sleep(1);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        t.running = false;
    }
}
