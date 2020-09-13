package com.thefirstwind;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Case101_AtomicInteger {
    AtomicInteger count = new AtomicInteger(0);

    void m(){
        for(int i = 0; i < 10000; i++){
            count.incrementAndGet(); // count++
        }
    }

    public static void main(String[] args){
        Case101_AtomicInteger t = new Case101_AtomicInteger();

        List<Thread> threads = new ArrayList<Thread>();

        for( int i = 0; i < 10; i++){
            threads.add(new Thread(t::m, "thread-" + i));
        }
        threads.forEach((o) -> o.start());

        System.out.println(t.count.get());
    }
}
