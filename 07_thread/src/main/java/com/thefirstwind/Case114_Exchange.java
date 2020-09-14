package com.thefirstwind;

import java.util.concurrent.Exchanger;

public class Case114_Exchange {

    static Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args){
        new Thread(()->{
            String str = "T1";
            try {
                str = exchanger.exchange(str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + str);
        }, "t1").start();

        new Thread(()->{
            String str = "T2";
            try {
                str = exchanger.exchange(str);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + str);
        }, "t2").start();

    }
}
