# Dubbo 教程

https://www.bilibili.com/video/BV1ns411c7jV


Dubbo Register
Dubbo Consumer
Dubbo Provider
Dubbo Monitor（Dubbo OPS)


Dubbo的协议
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

Dubbo的序列化协议：
* kryo协议：首选
* FST
* hessian2
* fastjson

