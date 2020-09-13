package com.thefirstwind;

public class Case008_DCL_Singleton {

    private static volatile Case008_DCL_Singleton INSTANCE;

    private Case008_DCL_Singleton(){

    }

    public static Case008_DCL_Singleton getInstance(){
        if(INSTANCE == null){
            synchronized (Case008_DCL_Singleton.class){
                if(INSTANCE == null){
                    try{
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    INSTANCE = new Case008_DCL_Singleton();
                }
            }
        }
        return INSTANCE;
    }

    public void m(){System.out.println("m:" + this.hashCode());}

    public static void main(String[] args) throws InterruptedException {

        String hashcode = "";
        for(long i = 0 ; i < 100_00L; i++){
            Thread t = new Thread(Case008_DCL_Singleton.getInstance()::m, "t" + i);
            t.start();
            t.join();

            Thread t2 = new Thread(new Runnable(){
                @Override
                public void run() {
                    Case008_DCL_Singleton.getInstance().m();
                }
            });
            t2.start();
            t2.join();
        }
    }
}
