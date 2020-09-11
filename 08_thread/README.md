https://www.bilibili.com/video/BV12A411E7zo/

## 考核thread理解的套路
* 上天
  * 锻炼解决问题技能
  * 高并发 缓存 大流量 大数据量
* 入地
  *  面试
  *  JVM OS 算法 线程 IO

## 基本概念
* 进程 线程 协程
* program app
* QQ runing -> 进程
* 线程 -> 一个进程里面的不同执行路径
* 协程 -> CPU - Ring0 - 12 - Ring3
  * Ring 0 -> 内核态 Ring3 -> 用户态
  * 内核调用/系统调用 - 线程的操作
  * 用户态启动线程
    * 进入到内核态 - 保存用户态的线程
    * 用户态 不经过内核态的线程 - 协程 Golang里的go程
* 用户态 - 内核态
  * int 0x80 - 128
  * sysenter cpu支持
  * 保存

### 什么是线程：
* 一个程序里不同的执行路径
> 参考 Case001_WhatIsThread.java

### 如何创建线程
* 1 extends Thread
* 2 implement Runable
* 3 new Thread(()->{}).start();
* 4 通过线程池  Executors.newCachedThread
> 参考 Case002_HowToCreateThread

### 线程的几个方法
* Thread.sleep()：睡一会儿，制定时间
* Thread.Yeild()：让出一下cpu，去等待队列里
* t1.join()：去另外一个线程去运行，经常用来等待另外一个线程的结束
> 参考 Case003_Sleep_Yield_Join

### 线程的几个状态
![](images/77C9CAF7-869F-4B08-B3ED-F4A8478F958F.png)
* 1 new
* 2 Runnable: Ready、Running
* 3 TimedWaiting
* 4 Waiting
* 5 Blocked
* 6 Terminated
> 参考 Case004_ThreadState

### 线程的Interrupte方法 和 stop方法
* stop不建议使用
* interrupte的作用不是打断线程的处理
* 在工程中 不能用以上2个方法，来控制程序业务逻辑的运行。
* 如果非要使用interrupte，在一个程序执行非常长的时间，sleep很久，那么就用interrupte打断，并且在sleep后异常抛出


## synchronized 关键字

* synchronized 锁定是，锁一个对象
> 参考 Case011_Synchronized

### synchronized(this) 和 public synchronized void m(){} 是等价的
> 注 Case012_Synchronized 和 Case013_Synchronized 是等价的
```
public class Case012_Synchronized {

    private int count = 10;

    public void m(){
        synchronized (this){
            count--;
            System.out.println(Thread.currentThread().getName() + "count = " + count);
        }
    }
}
```
```
public class Case013_Synchronized {
    private int count = 10;

    public synchronized void m(){
        count--;
        System.out.println(Thread.currentThread().getName() + "count = " + count);
    }
}

```

### synchronized 加载静态方法上，相当于 加载 Class上
```
public class Case014_Synchronized {

    public static int count = 10;

    public synchronized  static void m(){ // 这里等同于 synchronized(Case014_Synchronized.class)
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    public static void mm(){
        synchronized (Case014_Synchronized.class){
            count--;
        }
    }
}
```

### 在一个class里面，一个方法加了synchronized ，另外一个不加，那么他们之间的运行，是否可以并行

```
public class Case015_Synchronzied {

    public synchronized void m1(){
        System.out.println(Thread.currentThread().getName() + " m1 start");
        try{
            Thread.sleep(1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m1 end");
    }

    public void m2(){
        System.out.println(Thread.currentThread().getName() + " m2 start");
        try{
            Thread.sleep(500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2 end");
    }

    public static void main(String[] args){
        Case015_Synchronzied t = new Case015_Synchronzied();

        new Thread(t::m1, "t1").start();
        new Thread(t::m2, "t2").start();
    }

}
```
结论是可以的

### synchronzied的可重入特性
m1 m2 都加锁， m2调m1，可以吗？允许，是可重入锁
原因 如果有个父类，父类加锁，子类也加锁，子类调用父类 super方法，如果不可重入，就死锁了。

> 参考 Case017_Synchronzied

### 程序如果出现异常，锁会被释放
```
 程序在执行过程中，如果出现异常，默认情况锁会被释放
 所以，在并发处理过程中，有异常要多加小心，不然可能会发生不一致的情况。
 比如在一个web app处理过程中，多个servlet线程共同访问一个资源，这时如果异常处理不合适，
 在第一个线程中抛出异常，其他线程就会进入同步代码区，有可能会访问到异常产生时的数据。
 因此要非常小心的处理同步业务逻辑中的异常
```
> 参考 Case018_Synchronzied


### synchronized的历史 与 底层时间
锁升级问题

* 偏向锁
* 自旋锁
* OS重量级锁 /系统锁（自旋锁 10次以后 ）

###  什么情况下用自旋锁好， 什么情况下用 系统锁
* 执行时间长用 线程数多 系统锁
* 执行时间少 线程少 用自旋锁

马士兵说：没错，我就是厕所所长！(一) https://www.bilibili.com/read/cv2816942/  
马士兵说：没错，我就是厕所所长！(二) https://www.bilibili.com/read/cv2846899/
