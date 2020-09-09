# JVM 概述2

### 一个class的生命周期
![](images/8F46E6D0-D1A4-4663-B7EC-4B6C36B1AD5B.png)

### 运行数据区
![](images/B69E3755-3949-4C9A-AA14-1ED16B16BF7B.png)
* Program Counter: 程序计数器，程序运行到哪里了，该运行什么程序了
* JVM stacks: 装的是栈帧，记录的是frames
* Heap: 堆，用来保存new出来的对象(shared)
* native method stacks: 本地方法栈
* method area: 方法区(shared)
* run-time constant pool: (逻辑区) 常量池

![](images/91510A4B-2158-478C-9DBE-02275ED1978E.png)

#### 为什么需要记录当前线程的执行地址
![](images/67823C34-1F31-4BD7-AC58-827895C9C89A.png)
* 注：每个线程都有 PC VMS NMS
* PC: Program Counter
* VMS: JVM stacks
* NMS: native method stacks
* 线程内部使用的内存结构

#### 线程共享区域
![](images/5953402B-AD2F-4C8C-AE48-1DB62830F92C.png)

## JVM抽丝剥茧
## JVM知识体系推广
https://www.bilibili.com/video/BV1ZK411p7cr?p=2