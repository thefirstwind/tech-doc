package com.thefirstwind;

public class Case002_Synchronized {
    static volatile int i = 0;
    public static void n() { i++;}
    public static synchronized void m(){}
    public static void main(String[] args){
        for(int j=0; j< 1000_000; j++){
            m();
            n();
        }
    }

}
