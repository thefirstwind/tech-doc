package com.thefirstwind.clz;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.math.BigInteger;

public class Fibonacci {
  private static LoadingCache<Integer, BigInteger> memo = CacheBuilder.newBuilder()
          .build(CacheLoader.from(Fibonacci::getFibonacciNumber));

  public static BigInteger getFibonacciNumber(int n) {
    if (n == 0) {
      return BigInteger.ONE;
    } else {
      return BigInteger.valueOf(n).multiply(memo.getUnchecked(n - 1));
    }
  }
}
