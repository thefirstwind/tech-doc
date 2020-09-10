package com.thefirstwind;

public class Case_02_TLAB {
    class User {
        int id;
        String name;

        public User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    void alloc(int i){
        new User(i, "name" + i);
    }

    public static void main(String[] args){
        Case_02_TLAB t = new Case_02_TLAB();
        long start = System.currentTimeMillis();
        for( int i = 0; i < 1000_0000; i++) t.alloc(i);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
