package com.thefirstwind;

import java.util.concurrent.Semaphore;

public class Case113_Semaphore {

    public static void main (String[] args){


        // permits 允许的并行数量
        Semaphore s = new Semaphore(1);

        new Thread(()->{
            try{
                s.acquire();

                System.out.println("T running...");
                Thread.sleep(200);
                System.out.println("T running...");
                s.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            try{
                s.acquire();

                System.out.println("T2 running...");
                Thread.sleep(200);
                System.out.println("T2 running...");
                s.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
