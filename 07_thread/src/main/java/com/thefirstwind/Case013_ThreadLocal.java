package com.thefirstwind;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Case013_ThreadLocal {
    static ThreadLocal<Person> t1 = new ThreadLocal<>();

    public static void main(String[] args){
        new Thread(()->{
            try{
                TimeUnit.SECONDS.sleep(2);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(t1.get());
        }).start();

        new Thread(()->{
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            t1.set(new Person());
        });

        // ThreadLocal源码跟踪
        ThreadLocal<M> tl = new ThreadLocal<>();
        tl.set(new M());
        tl.remove();

        Map<String,String> o = new HashMap<>();
        o.put(null,null);
    }

    static class Person{
        String name = "zhangsan";

        public String toString(){
            String str = "name = " + name;
            return str;
        }
    }
}
