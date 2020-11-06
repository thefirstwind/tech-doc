package com.thefirstwind;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;

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
  }

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

  @Test
  public void case009LambdaPredicate(){

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

//  @Test
//  public void case002(){
//    Object fooHolder = (Foo1) () -> System.out.println("Hello");
//    System.out.println(fooHolder instanceof Foo1); // returns true
//  }

  interface Foo1 {
    void bar();
  }

  interface Foo2 {
    int bar(boolean baz);
  }

  interface Foo3 {
    String bar(Object baz, int mink);
  }

  interface Foo4 {
    default String bar() { // default so not counted
      return "baz";
    }
    void quux();
  }


}


