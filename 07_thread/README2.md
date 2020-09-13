# Java多线程高并发详解2
## 1 AQS(CLH)
![](images/84B971A2-B562-47AE-A894-7ED910A4ED6A.png)

### 1.1 AstomicInteger 的原子操作
没有使用 atomicInteger的时候
```java
import java.util.ArrayList;
import java.util.List;

public class Case102_NoAtomicInteger {
    // 可见性、禁止命令乱序执行
    volatile int count = 0;

    // 加锁：慢
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

```

改用 AtomicInteger方法改写
```java
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
```
输出结果
```
98247
```

### 1.2 对比 Atomic 和 Synchronized 对比 LongAdder之间的效率
```
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class Case103_AtomicVsSyncVsLongAdder {

    static long count2 = 0L;
    static AtomicLong count1 = new AtomicLong(0);
    static LongAdder count3 = new LongAdder();

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[1000];

        for(int i = 0; i< threads.length; i ++){
            threads[i] = new Thread(()->{
                for(int k=0; k<100_000; k++) count1.getAndIncrement();
            });
        }
        long start = System.currentTimeMillis();

        // 启动线程
        for(Thread t : threads) t.start();
        // 结束线程
        for(Thread t : threads) t.join();
        long end = System.currentTimeMillis();
        System.out.println("AtomicLong: " + count1.get() + " time " + (end-start));


        //-----------------------------

        Object lock = new Object();
        for(int i = 0; i< threads.length; i ++){
            threads[i] = new Thread(new Runnable(){
                @Override
                public void run(){
                    for( int k =0; k < 100_000; k++){
                        synchronized ((lock)){
                            count2++;
                        }
                    }
                }
            });
        }

        // 启动线程
        for(Thread t : threads) t.start();
        // 结束线程
        for(Thread t : threads) t.join();
        System.out.println("synchronized: " + count2 + " time " + (end-start));

        //-----------------------------

        for(int i = 0; i< threads.length; i ++){
            threads[i] = new Thread(()->{
                for(int k=0; k<100_000; k++) count3.increment();
            });
        }

        start = System.currentTimeMillis();
        // 启动线程
        for(Thread t : threads) t.start();
        // 结束线程
        for(Thread t : threads) t.join();
        end = System.currentTimeMillis();

        System.out.println("LongAdder: " + count3.longValue() + " time " + (end-start));


    }
}

```
输出结果
```
AtomicLong: 100000000 time 1806
synchronized: 100000000 time 1806
LongAdder: 100000000 time 495
```
为什么 LongAdder会非常快: 分段锁概念
![](images/403D29C6-0A29-4A8C-9944-DF2E602DF4AB.png)

## 2 ReentrantLock
可重入锁：锁了一次还可以再锁一次。
https://www.bilibili.com/video/BV1xK4y1C7aT?p=14