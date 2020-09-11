package com.thefirstwind;

public class Case003_Sleep_Yield_Join {

    public static void main(String[] args){
//        testSleep();
//        testYield();
        testJoin();
    }

    private static void testSleep() {
        new Thread(()->{
            for(int i = 0 ; i < 100; i++){
                System.out.println("A" + i);
                try{
                    Thread.sleep(500);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private static void testYield() {

        new Thread(()->{
            for(int i = 0; i < 100; i++){
                System.out.println("A" + i);
                if(i%10 == 0) Thread.yield();
            }
        }).start();

        new Thread(()->{
            for(int i = 0; i < 100; i++){
                System.out.println("B" + i);
                if(i%10 == 0) Thread.yield();
            }
        }).start();

    }

    private static void testJoin() {

        Thread t1 = new Thread(()->{
            for(int i = 0; i < 100; i++){
                System.out.println("A" + i);
                try{
                    Thread.sleep(10);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        Thread t2 = new Thread(()->{
            try{
                t1.join();
                System.out.println("B");

            }catch(InterruptedException e){
                e.printStackTrace();
            }
        });

        Thread t3 = new Thread(()->{
            try{
                t1.join();
                System.out.println("C");

            }catch(InterruptedException e){
                e.printStackTrace();
            }
        });


        t1.start();
        t2.start();
        t3.start();
    }
}
