# Mysql讲解

<!--https://www.bilibili.com/video/BV1bD4y1m7RU?from=search&seid=2211861062542380942-->

## 1 Mysql的相关知识点需要了解哪些
* 调优
* 索引
* MVCC
* 存储索引
* 事务
* 主从复制
* 读写分离
* 分库分表
* 锁
* 日志系统

## 2 Mysql的整体架构
* 连接器：管理连接，验证权限
* 分析器：词法分析，语法分析
  * sharding-sphere: 数据库中间件，重点关注
* 优化器：一共134个优化器自动选择
  * CBO：基于 成本 的优化
  * RBO：基于 规则 的优化
```
select * from A join B join C
并不是，先查A再查B再查C
```
![](images/2F4733C7-00F5-4417-9F45-15F56F1040F6.png)

如图 顺序不是严格按照 ABC的顺序执行的

如果一定要按照ABC的顺序的话，使用 straight_join 方法，一般来说不建议使用

* 执行器：用来跟存储引擎直接做交互
* 存储引擎：innodb，myisam，memory
  * innodb支持事务，myisam不支持
  * innodb支持外检，myisam不支持
  * innodb支持表锁和行锁，但是myisam只支持标锁
  * innodb在5.6之后 支持全文索引，myisam一直支持
  * innodb是索引的叶子节点，直接存放数据，myisam存的是地址

mysql的架构图
![](images/A763A8FE-6D1C-4668-83C6-8D707706D388.png)

* sql解析的工具：calcite、antlr
> sql解析工具

* 索引能加快数据，索引要不要保存到磁盘？ 要的
  * 局部性原理：数据和程序都有聚集成群的倾向，空间局部性，时间局部性
  * 磁盘预读：按照块区来读取。这个逻辑单位叫做页，datapage，一般是4K或者8K，在读取的时候都是4K的整数呗，innodb每次读取16K的数据

* mysql索引的数据结构
  * hash：memory索引存储使用hash索引，innodb支持自适应hash
  * 树：B+树
    * 多叉树
    * 节点有序
    * 每一个节点可以存储多个数据
    * 是一颗平衡树

## 3 B+树的介绍
B+树 里面一个节点所能保证的是 degree-1条记录
mysql的索引，一般有几层？一般情况下，3、4层足以支撑千万级表的查询

https://www.bilibili.com/video/BV1bD4y1m7RU?p=5


