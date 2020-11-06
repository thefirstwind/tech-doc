package com.thefirstwind;

import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Case001_lambda {

  /**
   * 匿名函数()的使用
   */
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

  /**
   * 有参无返回函数的使用
   */
  @Test
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

  /**
   * 有参无返回函数的使用
   */
  @Test
  public void case003LambdaFunction(){

    //第二种：使用lambda表达式
    Consumer<String> consumer = (s)->{
      //此时只有一行输出代码，因此可以省去外部的{}
      System.out.println(s);
    };
    consumer.accept("使用lambda:有参数，但是没有返回值");

  }

  /**
   * 有多个参数，有返回值
   */
  @Test
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

  @FunctionalInterface
  public interface MyInterface{
    void test();
  }

  @Test
  public void case005LambdaFunction() {
    MyInterface myInterface = () -> System.out.println("hello test");
    myInterface.test();
  }

  /**
   * 内部类 使用函数式接口调用
   */
  @FunctionalInterface
  public interface MyInterface2{
    int run(int a , int b, int c);
  }

  @Test
  public void case006LambdaFunction() {
    MyInterface2 myInterface = (a, b, c) -> {
      System.out.println("a" + a);
      System.out.println("b" + b);
      System.out.println("c" + c);
      return a + b + c;
    };
    System.out.println(myInterface.run(1,2,3));
  }

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


    Function<String, Integer> fun3 = s -> Integer.parseInt(s) +20;
    Function<Integer, String> fun4 = s -> "in_:" + String.valueOf(s);

    System.out.println("in_:" + fun3.apply("1"));
    System.out.println(fun3.andThen(fun4).apply("1"));
  }

  @Test
  public void case008LambdaConsumer(){

    Consumer<Object> fun = (s) -> {
      Integer in = (Integer)s;
      System.out.println("in:" + in);
    };
    fun.accept(1);

    Consumer<String> fun2 = s -> System.out.print("in_:" + s);
    fun2.accept("1");
  }

  @Test
  public void case009LambdaPredicate(){

    Predicate<Integer> fun = (s) -> {
      return (s == 3);
    };
    fun.test(1);


    Predicate<Integer> fun2 = s -> s == 3;
    fun2.test(1);
  }

  @Test
  public void case010LambdaSupplier(){

    Supplier<String> fun = () -> {
      return "hello supplier";
    };
    System.out.println(fun.get());

  }

  @Test
  public void case011LambdaMethodReference(){

    Supplier<HashMap<String,Object>> mapFun = HashMap::new;
  }

  @Test
  public void case012LambdaMethodReference(){
    List<String> list = Arrays.asList("1", "22", "333");
    list.forEach(s -> System.out.println(s));
    list.forEach(System.out::println);
  }

  @Test
  public void case013LambdaStream(){
    List<String> list = Arrays.asList("1", "22", "333");
    List<Integer> list2 = list.stream()
            .filter((s)-> s.length() >= 2)
            .sorted()
            .map((s) -> Integer.parseInt(s) + 1)
            .collect(Collectors.toList());

    System.out.println(list2);
  }

  @Test
  public void case014LambdaStream(){
    List<String> list = Arrays.asList("1", "2", "3");
    Stream<String> stream = list.stream();

    Stream<String> stream2 = Stream.of("1", "2", "3");
  }
  @Test
  public void case015LambdaStream(){
    String[] array = new String[]{"1","2","3"};
    Stream<String> stream1 = Arrays.stream(array);
    Stream<String> stream2 = Stream.of(array);
  }
  @Test
  public void case016LambdaStream(){
    Map<Integer, String> map = new HashMap<Integer, String>();
    map.put(1, "value1");
    map.put(2, "value2");
    map.put(3, "value3");
    Stream<Map.Entry<Integer,String>> stream = map.entrySet().stream();
  }




}


