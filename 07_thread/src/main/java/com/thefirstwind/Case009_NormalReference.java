package com.thefirstwind;

import java.io.IOException;

public class Case009_NormalReference {

    public static void main(String[] args) throws IOException {
        M o = new M();
        o = null;
        System.gc();
        System.in.read();
    }
}

