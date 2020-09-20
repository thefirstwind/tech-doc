# Dubbo 教程

https://www.bilibili.com/video/BV1ns411c7jV


Dubbo Register
Dubbo Consumer
Dubbo Provider
Dubbo Monitor（Dubbo OPS)


## 1 Dubbo的协议
* dubbo协议：采用单一场链接和NIO异步通讯，适合小数据量 大并发的服务调用，消费者机大于提供者机。
* rmi：存在反序列化风险，采用阻塞式短连接
* hession：用于集成Hession的服务，底层采用http通讯，确认是用Jetty实现
* http：采用spring的httpInvoiker实现
* webservice：基于CXF的frontend-simple和 transports-http实现
* thrift：Facebook捐给Apache的RPC框架
* memcached
* redis：
* rest：json标准
* grpc： stream 和 Reactive可以考虑使用

## 2 Dubbo的序列化协议：
* kryo协议：首选
* FST
* hessian2
* fastjson

## 3 Dubbo的负载均衡策略
* Random LoadBalance
* RoundRobin LoadBalance
* LeastActive LoadBalance: 最小活跃数，挑一个最快的
* ConsistentHash LoadBalance：一致hash


## 4 服务降级
* mock=force:return+null
* mock=fail:return+null

## 5 服务容错：
* Failover Cluster：重试次数
* Failfast Cluster：快速失败，只发一次调用
* Failsafe Cluster：失败安全，出错异常时，直接忽略
* Failback Cluster：失败自动回复
* Forking Cluster：并行调用多个服务器
* Broadcast Cluster：广播调用

## 6 服务容错：
实际开发过程中用hrystrix比较多> @EnableHystrix 开启服务容错功能
> @HystricCommand
> @HystricCommand(fallbackMethod="hello") 出错的时候调用的
>
>

## 7 Dubbo的原理
### 框架设计
### 启动解析、加载配置信息
### 服务暴露
### 服务引用
### 服务调用

