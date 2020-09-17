# Spring Cloud 的组件介绍

<!--https://blog.csdn.net/With_Her/article/details/97165385-->
Spring Cloud 提供几个组件：Eureka、Ribbon、Feign、Hystrix、Zuul

## 1 假设一个业务场景

电商网站， 支付订单流程如下：
* 创建订单，订单状态变为"已支付"
* 扣减相应的库存
* 通知仓储中心，进行发货
* 给用户这次购物增加积分

业务流程如下

![](images/626AAACA-551C-4BBC-B822-907032F39F9C.png)

## 2 Eureka 的应用

订单服务要调用其他服务怎么调用？
使用Euraka注册中心，专门负责服务注册和发现

每个服务都有一个 Eureka Client 去 Eureka Server找目标服务的地址

结构如图所示
![](images/39095E9E-D752-45B6-A13C-57F07550E24B.png)

## 3 Feign 的应用

订单服务调用其他服务的话，要写一大堆代码，如下所示
```java
CloseablehttpClient httpclient = HttpClients.createDetault();
HttpOst httpPost = new HttpPost("http://192.168.31.169:9000/");

List<NameValuePair> params = new ArrayList<NameValuePaire>();
params.add(new BasicNameValuePair("scope", "project"));
params.add(new BasicNameValuePair("q", "java"));

UrlEncodeFormEntity formEntity = new UrlEncodeFormEntity(params);
httpPost.setEntity(formEntity);
httpPost.setHeader(
   "user-Agent",
   "Mozilla/5.0 (Windows NT 6.3; Win64; x64)"     
);

CloseableHttpResponse response = null;
response = httpclient.execute(httpPost);
if(response.getStatusLine().getStatusCode() == 200){
    String context = EntityUtils.toString(response.getEntity(), "UTF-8");
    System.out.println(content);
}
if(response != null){
    response.close();
}
httpclient.close();
```
类似这样的代码

然而 Feign 给出了优雅的解决方案。

```java
@FeignClient("inventory-service")
public class InventoryService{
    @RequestMapping(value = "reduceStock/{goodsSkuId}", method = HttpMethod.PUT)
    @ResponseBody
    public ResultCode reduceStock(@PathVriable("goodsSkuId") Long goodsSkuId);
}
@Service
public class OrderService{
    @Autowired
    private InventoryService inventoryService;
    
    public ResultCode payOrder(){
        orderDao.updateStatus(id, OrderStatus.PAYED);
        inventoryService.reduceStock(goodsSkuId);
    }
}
```


可以通过注解定义 FeignClient接口，剩下的 建立连接、构造请求、发起请求、获取相应、解析相应 等等 都交给Feign干就可以了。

Feign
## 4 Ribbon 的应用
## 5 Hystrix的应用
## 6 Zuul的应用