# Java多线程高并发详解3

* LockSupport
* 淘宝面试题
* 源码阅读技巧
* AQS源码解析


## 1 问题汇总
* 1 synchronized和reentrantlock的底层实现及重入得底层原理
* 2 锁的四种状态及升级过程
* 3 CMS和G1的异同
* 4 G1什么时候引发FullGC
* 5 除了CAS，原子类，synchronzied，Lock还有什么线程安全的方式
* 6 HashMap和HashTable的异同
* 7 允许null键的map你知道哪些
* 8 null键放在HashMap的哪里
* 9 为什么Hashtable扩容是2倍+1
* 10 红黑树的插入时间复杂度
* 11 解决哈希冲突的方式
* 12 现有1T的数据，内存只有1G，改怎么对他们排序
  * 外部排序
* 13 Tomcat为什么要重写类加载器
* 14 tcp握手挥手过程及其状态转换
* 15 mysql隔离级别
* 16 B树 和 B+树的区别
* 17 你知道哪些设计模式，他们在JDK源码中是怎么体现的
* 18 Java运行时数据区
* 19 说一个最熟悉的垃圾回收算法
* 20 吞吐量优先 和响应时间优先 的回收器是哪些
* 21 类加载全过程
* 22 线程池 7个参数
* 23 CAS的ABA问题怎么解决
* 24 Java内存模型
* 25 什么叫做阻塞队列的有界和无界
* 26 cookie和session介绍一下
* 27 说一下反射，反射会影响性能吗
* 28 谈一下AQS
* 29 为什么你说AQS的底层是CAS+volatile
* 30 JUC包里的同步组件主要实现AQS的那些主要方法
* 30 JUC包里的同步组件主要实现了AQS的那些主要方法

* 1 ConcurrentHashMap的底层原理
* 2 手写一个LRU 用 LinkedHashMap
* 3 HashMap底层数据结构
* 4 为什么红黑树不用普通的AVL树
* 5 为什么在8的时候链表编程树
* 6 为什么在6的时候从树退化成链表
* 7 线程池7个参数，该怎么配置最好
* 8 说一下volatile
* 9 volatile的可见性和禁止命令重排序怎么实现的
* 10 CAS是什么
* 11 PriorityQueue底层是什么，初始容量是多少，容量方式哪
  * 若初始大小 < 64, 则容量为原来的2倍+2，不然就扩容为原来的1.5倍
* 12 HashMap的容量为什么要设置为2的次幂
* 13 你知道跳表吗？什么场景会用到
  * ConcurrentSkipListMap,在多线程下需要自定义排序顺序时
* 14 CopyOnWriteArrayList知道吗，迭代器支持fail-fast吗
  * 线程安全ArrayList，写时复制，迭代器是采用快照风格，不支持fail-fast
* 15 innodb的底层数据结构
* 16 为什么用B+树而不是B树
  * B+树的磁盘读写代价更低
  * B+树的查询效率更加稳定
  * B+树更便于遍历
  * B+树更适合基于范围的查询
* 17 为什么用B+树不用红黑树

* 1 线程池的设计里体现了什么设计模式
* 2 说说你了解什么设计模式，直到责任连设计模式吗
* 3 wait/notify体现了什么设计模式
* 4 线程池7个参数
* 5 谈一下spring事务传播
* 6 谈一下IOC底层原理
* 7 怎么判断内存泄漏
* 8 设计模式说 5，6个
* 9 说一下你了解的MQ
* 10 谈一谈你对高并发的理解，你会从什么角度设计高并发程序
* 11 索引不适用的条件
* 12 填一下NIO和AIO
* 13 说一下select poll epoll
* 14 谈一下TCP的阻塞控制
* 15 你知道 LongAdder吗， 和 AtomicLong有什么区别

## 2 复习一下
* 线程的基本概念
* synchronzied
* volatile
* AtomicXXX
* 各种 JUC同步锁
  * ReentrantLock
    * 问：Synchronzied 和 ReentrantLock 到底有什么不同
    * 答：前者，系统自动加锁 后者手动加锁解锁。
    * 答：后者 可以做到 公平锁，前者不公平
  * CountDownLatch
  * CyclicBarrier
  * Phase
  * ReadWriteLock
  * Samaphore
  * Exchanger
  * LockSupport

葛总JUC同步锁都是基于AQS来实现的

## 3 AQS
![](iamges/D303BAB4-49F0-4FED-98E7-532A8783AA06.png)