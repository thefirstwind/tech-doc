# JVM Tuning：JVM调优

## tuning入门- Java对象分配

### 概述
* 栈上分配
  * 线程私有小对象
  * 无逃逸
  * 支持标量替换
  * 无需调整
* 线程本地分配TLAB(Thread Local Allacation Buffer)
  * 占用eden，默认1%
  * 多线程的时候不用竞争eden就可以申请空间，提高效率
  * 小对象
  * 无需调整
* 老年代
  * 大对象
* eden

### new 一个对象的过程如何进内存
1 栈上分配
1.1 首先栈上分配，每一个线程都有一个JVM stack，很小的话就进线程
1.2 逃逸分析：一个方法里面，new的对象，方法以外没有用，就在栈上分配
1.3 标量替换：简单的 基础类型，综合在一起放
* 栈上分配的好处：栈帧弹出来，自动的就回收了，和GC没有关系。
2 不是私有小对象，有逃逸的话，代表分配不了。去线程本地分配TLAB（eden的1%）
3 分配不了，去老年代
4 如果不需要，去eden


```java
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

运行时添加以下JVM相关测参数

-XX:-DoEscapeAnalysis -XX:-EliminateAllocations -XX:-UseTLAB -XX:+PrintGC
-XX:+DoEscapeAnalysis -XX:+EliminateAllocations -XX:+UseTLAB -XX:+PrintGC
逃逸分析，标量替换，使用TLAB

输出1
[0.002s][warning][gc] -XX:+PrintGC is deprecated. Will use -Xlog:gc instead.
[0.010s][info   ][gc] Using G1
[0.228s][info   ][gc] GC(0) Pause Young (Normal) (G1 Evacuation Pause) 23M->0M(512M) 1.506ms
[1.306s][info   ][gc] GC(1) Pause Young (Normal) (G1 Evacuation Pause) 304M->1M(512M) 1.699ms
[2.219s][info   ][gc] GC(2) Pause Young (Normal) (G1 Evacuation Pause) 305M->1M(512M) 1.306ms
2487

输出2
[0.002s][warning][gc] -XX:+PrintGC is deprecated. Will use -Xlog:gc instead.
[0.010s][info   ][gc] Using G1
[0.157s][info   ][gc] GC(0) Pause Young (Normal) (G1 Evacuation Pause) 24M->0M(512M) 1.771ms
[0.393s][info   ][gc] GC(1) Pause Young (Normal) (G1 Evacuation Pause) 304M->0M(512M) 1.191ms
357
```

* 参考地址：https://www.bilibili.com/video/BV1mC4y1H7QC?p=5


###