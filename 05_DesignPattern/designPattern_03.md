# UML中的类图及类图之间的关系
```
本文需安装插件才能正常浏览 plantuml图
https://chrome.google.com/webstore/detail/pegmatite/jegkfbnfbfnohncpcfcimepibmhlkldo

Idea 中如果要正常浏览，需要使用 Markdown navigator插件替换原有的Markdown插件
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
* 聚合关系
* 组合关系
* 泛化关系
* 实现关系
### 1 依赖关系
```plantuml
@startuml
class Person {
  String name;
  void call(MobilePhone mp);
}
class MobilePhone {
  void transfer();
}
note bottom of Person 
public void call(MobilePhone mp){ 
    mp.transfer();
}   
end note
Person ..> MobilePhone : 打电话
@enduml
```
### 2 关联关系
```plantuml
@startuml
class Teacher {
  String name;
  List<Student> stus;
  void teaching();
}
class Student {
  String name;
  List<Teacher> teas;
  void study();
}
Teacher -- Student : 教学者 学习者
@enduml
```
### 3 聚合关系
```plantuml
@startuml
class University {
  List<Teacher> teas;
}
class Teacher {
  String name;
  void teaching();
}
University o-- Teacher
@enduml
```
### 4 组合关系
```plantuml
@startuml
class Head {
  Mouth mouth;
}
class Mouth {
  void eat();
}
Head *-- Mouth
@enduml
```
### 5 泛化关系
```plantuml
@startuml
class Person {
  String name;
  int age;
  void speak();
}
class Student{
  long studentNo;
  void study();
}
class Teacher{
  long teacherNo;
  void teaching();
}
Person <|-- Student
Person <|-- Teacher
@enduml
```
### 6 实现关系
```plantuml
@startuml
Interface Vehicle {
  void move();
}
class Car{
  void move();
}
class Ship{
  void move();
}
Vehicle <|.. Car
Vehicle <|.. Ship
@enduml
```