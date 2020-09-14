# 设计模式

## 01 Singleton
推荐2种方法
### 01.1 static
```java
public class Case01_Singleton_1 {

    pirivate static final Case01_Singleton_1 INSTANCE = new Case01_Singleton_1();

    private Case01_Singleton_1(){}

    public static Case01_Singleton_1 getInstance() {
        return INSTANCE;
    }

    public static void main (String[] args){
        Case01_Singleton_1 t1 = Case01_Singleton_1.getInstance();
        Case01_Singleton_1 t2 = Case01_Singleton_1.getInstance();
        System.out.println(t1 == t2);
    }
}

```
### 01.2 DCL:double check lock
## 02 strategy
用户定义不同的规则
* comparable
* comparetor
## 03 Factory
## 04 Facade
## 05 Decorator
## 06 Chain of Respontory
## 07 Observer
## 08 Composite
## 09 flyweight
## 10 Proxy
## 11 Iterator
## 12 visitor
## 13 Builder
## 14 Adapter
## 15 Bridge
## 16 Command
## 17 prototype
## 18 memento
## 19 TemplateMethod
## 20 State

<!--https://www.bilibili.com/video/BV1tK411W7xx?p=15-->