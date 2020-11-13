package com.thefirstwind.clz;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.math.BigInteger;

public class FibonacciSequence {
  private static LoadingCache<Integer, BigInteger> memo = CacheBuilder.newBuilder()
          .maximumSize(100)
          .build(CacheLoader.from(FibonacciSequence::getFibonacciNumber));

  public static BigInteger getFibonacciNumber(int n) {
    if (n == 0) {
      return BigInteger.ZERO;
    } else if (n == 1) {
      return BigInteger.ONE;
    } else {
      return getFibonacciNumber(n - 1).add(getFibonacciNumber(n - 2));
    }
  }
}
