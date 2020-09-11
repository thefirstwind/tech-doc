package com.thefirstwind;

import java.util.concurrent.TimeUnit;

public class Case018_Synchronized {

    synchronized void m(){
        System.out.println("m start");
        try{
            TimeUnit.SECONDS.sleep(1);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("m end");
    }

    public static void main(String[] args){
        Case018_Synchronized_Child child = new Case018_Synchronized_Child();
        child.m();
    }


}
class Case018_Synchronized_Child extends Case018_Synchronized{

    @Override
    synchronized  void m(){
        System.out.println("child m start");
        super.m();
        System.out.println("child m end");

    }
}
