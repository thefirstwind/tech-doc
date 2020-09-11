package com.thefirstwind;

public class Case015_Synchronzied {

    public synchronized void m1(){
        System.out.println(Thread.currentThread().getName() + " m1 start");
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m1 end");
    }

    public void m2(){
        System.out.println(Thread.currentThread().getName() + " m2 start");
        try{
            Thread.sleep(500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2 end");
    }

    public static void main(String[] args){
        Case015_Synchronzied t = new Case015_Synchronzied();

        new Thread(t::m1, "t1").start();
        new Thread(t::m2, "t2").start();
    }

}
