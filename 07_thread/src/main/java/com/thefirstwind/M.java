package com.thefirstwind;

public class M{
    @Override
    protected  void finalize(){
        // 打印出finalize 表示被GC回收
        System.out.println("finalize");
    }
}