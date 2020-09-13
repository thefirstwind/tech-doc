package com.thefirstwind;

import java.util.ArrayList;
import java.util.List;

public class Case102_NoAtomicInteger {
    volatile int count = 0;

    synchronized void m(){
        for(int i = 0; i < 10000; i++){
            count++;
        }
    }

    public static void main(String[] args){
        Case102_NoAtomicInteger t = new Case102_NoAtomicInteger();

        List<Thread> threads = new ArrayList<Thread>();

        for( int i = 0; i < 10; i++){
            threads.add(new Thread(t::m, "thread-" + i));
        }
        threads.forEach((o) -> o.start());

        System.out.println(t.count);
    }
}
