# UML中的类图及类图之间的关系
```
本文需安装插件才能正常浏览 plantuml图
https://chrome.google.com/webstore/detail/pegmatite/jegkfbnfbfnohncpcfcimepibmhlkldo
```
## 类之间的关系

![](images/9A8827C7-291C-4FF4-BD54-EB6BD726A986.png)

## 类图
```plantuml
@startuml
class Student {
  long no;
  String name;
  String school;
  float totalScore;
  void display();
}
@enduml
```

## 接口
```plantuml
@startuml
interface Graph {

  double getArea();
  double getPerimeter();
}
@enduml
```

## 有关系的类图

```plantuml
@startuml
interface Graph {
  double getArea();
  double getPerimeter();
}
class Client {
  void calculate(Graph tx);
}

class Rectangle {
  double length;
  double width;
  double getArea();
  double getPerimeter();
}
class Circular {
  double radius;
  double getArea();
  double getPerimeter();
}  
Graph <|.. Rectangle
Graph <|.. Circular
Graph <.. Client
@enduml
```

## 类之间的关系
* 依赖关系
* 关联关系
* 关联关系
* 聚合关系
* 组合关系
* 泛化关系
* 实现关系
### 1 依赖关系
### 2 依赖关系
### 3 关联关系
### 4 关联关系
### 5 聚合关系
### 6 组合关系
### 7 泛化关系
### 8 实现关系

![](images/FA761ED8-9EAB-4052-BE26-9BD65786F82C.png)

![](images/64AD4858-4E5D-4373-BC0E-01785DED24D3.png)

![](images/9464509F-FDEA-4BAD-BF47-B0ED74050F19.png)

![](images/87E75223-0513-4555-8A57-5103782E1237.png)

![](images/5F1DF2F9-8BFD-46A0-B207-5A6D72EFFBC2.png)

![](images/CFE67C44-BF9C-4AC9-90A3-DB5E6552413C.png)