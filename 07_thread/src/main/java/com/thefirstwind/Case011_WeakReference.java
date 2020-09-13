package com.thefirstwind;

import java.lang.ref.WeakReference;

public class Case011_WeakReference {
    public static void main(String[] args){
        WeakReference<M> m = new WeakReference<>(new M());

        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

    }
}
