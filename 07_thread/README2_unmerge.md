# Java多线程高并发讲解2

## 1 AQS
> 是一个用于构建锁和同步容器的框架。事实上concurrent包内许多类都是基于AQS构建，  
> 例如ReentrantLock，Semaphore，CountDownLatch，ReentrantReadWriteLock，  
> FutureTask等。AQS解决了在实现同步容器时设计的大量细节问题。

## 2 ReentrantLock
### 2.1 synchronzied 本身就是可重入锁
```java
public class Case104_ReeentrantLock1 {

    /**
     * 加锁之后代码段m，同一时间只能被一个线程使用
     */
    synchronized void m1(){
        for(int i = 0 ; i < 10; i++){
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            System.out.println(i);

            if(i % 2 == 0) m2();
        }
    }
    synchronized void m2(){
        System.out.println("m2....");
    }

    public static void main(String[] args){

        // 生命一个可重入锁
        Case104_ReeentrantLock1 r1 = new Case104_ReeentrantLock1();
        new Thread(r1::m1).start();
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        new Thread(r1::m2).start();
    }
}
```
### 2.2 ReentranLock
> ReentrantLock主要利用CAS+AQS队列来实现。它支持公平锁和非公平锁，两者的实现类似
#### 2.2.1 可以替代 synchronzied, 但是 lock是需要 lock 和 finally{ unlock}
> 参考  Case105_ReentrantLock2
```java
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Case105_ReentrantLock2 {

    Lock lock = new ReentrantLock();

    /**
     * 加锁之后代码段m，同一时间只能被一个线程使用
     */
    void m1(){
        try{
            lock.lock();
            for(int i = 0 ; i < 3; i++){
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
//                if(i % 2 == 0 ) lock.t();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    synchronized void m2(){
        try{
            lock.lock();
            System.out.println("m2....");

        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args){

        // 生命一个可重入锁
        Case105_ReentrantLock2 r1 = new Case105_ReentrantLock2();
        new Thread(r1::m1).start();
        try{
            TimeUnit.SECONDS.sleep(1);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        new Thread(r1::m2).start();
    }
}
```
#### 2.2.2 使用 tryLock
#### 2.2.3 使用 lockInterruptibly()试图唤醒原有sleep的线程
> 参考 Case106_ReentrantLock3
```
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 本段代码是有问题的
 */
public class Case106_ReentrantLock3 {

    public static void main(String[] args){
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(()->{
            try{
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end");
            }catch(InterruptedException e){
                System.out.println("interrupted!");
            }finally{
                lock.unlock();
                System.out.println("unlock 1!");
            }
        });
        t1.start();

        Thread t2 = new Thread(()->{
            try{
//                lock.tryLock();
                lock.lockInterruptibly(); // 可以对interrupte方法做出相应
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");
            }catch(InterruptedException e){
                System.out.println("interrupted! 2");
            }finally {
                lock.unlock();
                System.out.println("unlock 2!");
            }
        });
        t2.start();

        try{
            TimeUnit.SECONDS.sleep(1);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        t2.interrupt(); // 打断线程2的等待
    }
}

```
#### 2.2.4 ReentranLock也可以指定一个公平锁
* Lock lock = new ReentrantLock(true); // 表示为公平锁
> 使用队列等待，而不是自旋锁的抢

* Lock lock = new ReentrantLock(); // 表示为非公平锁
> 以下例子，打印出来锁释放的状态，2个线程抢占资源是公平的
```
import java.util.concurrent.locks.ReentrantLock;

public class Case108_ReentrantLock5  extends Thread{
    private static ReentrantLock lock = new ReentrantLock(true);
    public void run(){
        for(int i =0; i < 100; i ++){
            lock.lock();
            try{
                Thread.sleep(1);
                System.out.println(Thread.currentThread().getName() + "获得锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    public static void main(String[] args){
        Case108_ReentrantLock5 rl = new Case108_ReentrantLock5();
        Thread th1 = new Thread(rl);
        Thread th2 = new Thread(rl);
        th1.start();
        th2.start();
    }
}
```
* 面试题：用两个线程分别打印出 1 2 3 4，当线程1 打印完1 通知线程2 打印1，线程1 再开始打印2 ，以此类推。
> 注：该问题在高并发情况下，是需要使用线程之前通信的方法来实现，或者使用栅栏
```
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Case109_ReentrantLock6 extends Thread{
    private static ReentrantLock lock = new ReentrantLock(true);
    public void run(){
        for(int i =1; i < 5; i ++){
            lock.lock();
            try{
                Thread.sleep(1);
                System.out.println(Thread.currentThread().getName() + "-" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    public static void main(String[] args){
        Case109_ReentrantLock6 rl = new Case109_ReentrantLock6();
        Thread th1 = new Thread(rl::run, "th1");
        Thread th2 = new Thread(rl::run, "th2");
        th1.start();
        th2.start();
    }
}
```

## 3 CountDownLatch
> CountDownLatch 是原子的，没有线程安全的问题
> 倒数的门栓，5 4 3 2 1 门栓就开了
> 某种意义上 countDownLatch可以和 join的作用相同
> 但是 join只有在线程结束的时候才能继续，而 countDownLatch.countDown()更自由

```java
import java.util.concurrent.CountDownLatch;

public class Case110_CountDownLatch {
    
    public static void main(String[] args){
        usingJoin();
        usingCountDownLatch();
    }

    private static void usingCountDownLatch() {
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);

        for( int i = 0; i < threads.length ; i++){
            threads[i] = new Thread(()->{
                int result = 0;
                for( int j =0; j < 10000; j++) result +=j;
                latch.countDown();
                // 100 -> 99 -> 98 -> ....
            });
        }
        // 所有线程开始
        for(int i = 0; i < threads.length; i++){
            threads[i].start();
        }

        try{
            // 所有线程的门栓开始锁
            latch.await();
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        System.out.println("end latch");

    }

    private static void usingJoin() {
        Thread[] threads = new Thread[100];
        for( int i = 0; i < threads.length ; i++){
            threads[i] = new Thread(()->{
                int result = 0;
                for( int j =0; j < 10000; j++) result +=j;
            });
        }
        // 所有线程开始
        for(int i = 0; i < threads.length; i++){
            threads[i].start();
        }

        for(int i = 0; i < threads.length; i++) {
            try{
                // 线程结束、每一个线程都等着 合并在当前线程上
                threads[i].join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }

        System.out.println("end latch");
    }
}
```
