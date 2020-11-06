# Java lambda


##  Lambda表达式
* 函数式接口
  * Function
  * Predicate
  * Consumer
  * Supplier
* Stream
* Optional
* Filter
* Map-Reduce
* 新的Date API

## 1. 函数式接口

### 1.1 匿名函数()的使用
```java
  @Test
  public void case001LambdaFunction(){
    //第一种
    Runnable runnable = new Runnable() {
      public void run() {
        System.out.println("不使用Lambda表达式");
      }
    };
    runnable.run();
    System.out.println("=======================");
    //第二种
    // Runnable runnable1 = () -> System.out.println("使用Lambda表达式1");

    Runnable runnable1 = () -> {
      System.out.println("使用Lambda表达式1");
      System.out.println("使用Lambda表达式2");
    };
    runnable1.run();

  }
```

### 1.2 有参无返回函数的使用
```java
  public void case002LambdaFunction(){
    //第一种：没有使用lambda表达式
    Consumer<String> consumer = new Consumer<String>() {
      @Override
      public void accept(String s) {
        System.out.println(s);
      }
    };
    consumer.accept("没有使用lambda:有参数，但是没有返回值");
    //第二种：使用lambda表达式
    Consumer<String> consumer1 = (String s)->{
      //此时只有一行输出代码，因此可以省去外部的{}
      System.out.println(s);
    };
    consumer1.accept("使用lambda:有参数，但是没有返回值");

  }
```

### 1.3 有参无返回函数的使用
```java
  public void case003LambdaFunction(){

    //第二种：使用lambda表达式
    Consumer<String> consumer = (s)->{
      //此时只有一行输出代码，因此可以省去外部的{}
      System.out.println(s);
    };
    consumer.accept("使用lambda:有参数，但是没有返回值");

  }
```

### 1.4 有多个参数，有返回值
```java
  public void case004LambdaFunction(){
    //第一种：没有使用lambda表达式
    Comparator<Integer> comparator = new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        System.out.println("o1:"+o1);
        return o1.compareTo(o2);
      }
    };
    System.out.println(comparator.compare(1,2));
    System.out.println("======================");
    //第二种：使用lambda表达式
    Comparator<Integer> comparator2 = (o1,o2)->{
      System.out.println("o1:"+o1);
      return o1.compareTo(o2);
    };
    System.out.println(comparator2.compare(1,2));

  }
```

### 1.5 自定义函数式接口

#### 特点
* 含有@FunctionalInterface注解

* 只有一个抽象方法

#### 函数式接口有什么用

* 函数式接口能够接受匿名内部类的实例化对象，换句话说，我们可以使用匿名内部类来实例化函数式接口的对象，
* 而Lambda表达式能够代替内部类实现代码的进一步简化。


* 并且java为我们提供了四个比较重要的函数式接口：
  * 消费型接口：Consumer< T> void accept(T t)有参数，无返回值的抽象方法；
  * 供给型接口：Supplier < T> T get() 无参有返回值的抽象方法；
  * 断定型接口： Predicate< T> boolean test(T t):有参，但是返回值类型是固定的boolean
  * 函数型接口： Function< T，R> R apply(T t)有参有返回值的抽象方法；

自定义一个我们自己的函数式接口
```java
  @FunctionalInterface
  public interface MyInterface{
    void test();
  }

  @Test
  public void case005LambdaFunction() {
    MyInterface myInterface = () -> System.out.println("hello test");
    myInterface.test();
  }

```

### 1.6 Function功能型函数式接口的应用
```java
  @Test
  public void case007LambdaFunction(){

    Function<Object, Object> fun = (s) -> {
      Integer in = (Integer)s;
      System.out.println("in:" + in);
      return in + 20;
    };
    Function<Object, Object> fun2 = (s) -> {
      Integer in = (Integer)s;
      System.out.println("in:" + in);
      return String.valueOf(in);
    };
    fun.apply(1);

    //value作为function1的参数，返回一个结果，该结果作为function2的参数，返回一个最终结果
    fun.andThen(fun2).apply(1);
  }

```
### 1.7 Consumer消费型函数式接口的应用
```java
 @Test
  public void case008LambdaConsumer(){

    Consumer<Object> fun = (s) -> {
      Integer in = (Integer)s;
      System.out.println("in:" + in);
    };
    Consumer<Object> fun2 = (s) -> {
      Integer in = (Integer)s;
      System.out.println("in:" + in);
    };
    fun.accept(1);

  }
```
### 1.8 Predicate断言型函数式接口的应用
### 1.9 Supplier供给型函数式接口的应用

List操作

Map操作

Groupingby操作

FilterMap操作

Optional操作可以防止NullPointException

给出一个详细的例子


Java8常见操作