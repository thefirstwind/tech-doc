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
* 优化器：
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

