# Java多线程高并发讲解

## 1 volatile关键字的字节码原语

### 1.1 大厂真题
* 请描述synchronized 和 reentrantlock的底层实现及重入的底层原理 （百度 阿里）
* 请描述所得四种状态和升级过程 （百度 阿里）
* CAS的ABA问题如何解决 （百度）
* 请谈一下AQS，为什么AQS的底层是CAS + volatile（百度）
* 请谈一下你对volatile的理解 （美团 阿里）
* volatile的可见性 和 禁止名利重排序是如何实现的 （美团）
* CAS是什么 （美团）
* 请描述一下对象的创建过程 （美团 顺丰）
* 对象在内存中的内存布局 （美团 顺丰）
* DCL单例为什么要加volatile（美团）
* 解释一下所得四种状态（顺丰）
* Object o = new Object() 在内存中占了多少字节？ （顺丰）
* 请描述synchronized和 ReentrantLock的异同
* 聊聊你对 as-if-serial和 happens-before语句的理解（京东）
* 你了解ThreadLocal吗？ 你知道ThreadLocal中如何解决内存泄漏问题？（京东 阿里）
* 请描述一下锁的分类以及JDK中的应用（阿里）


77C9CAF7-869F-4B08-B3ED-F4A8478F958F
### CAS
![](images/40B32C01-EBAD-4FA8-B6D5-EC4C18B9A95D.png)
Compare And Swap (Cpmpare And Exchange)/自旋/自旋锁/无锁

应为经常配合循环操作，直到完成为止，所以泛指一类操作

CAS(v,a,b), 变量v，期待值a， 修改值b