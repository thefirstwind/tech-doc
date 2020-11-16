package com.thefirstwind;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Case003_guava_cache {

  /**
   * How to Use Guava Cache
   */
  @Test
  public void whenCacheMiss_thenValueIsComputed() throws InterruptedException {

    for(int i = 1; i < 10000; i++) {

      System.out.println(i);
      Thread.sleep(100);
      CacheLoader<String, String> loader;
      loader = new CacheLoader<String, String>() {
        @Override
        public String load(String key) {
          return key.toUpperCase();
        }
      };

      LoadingCache<String, String> cache;
      cache = CacheBuilder.newBuilder().build(loader);

      assertEquals(0, cache.size());
      assertEquals("HELLO", cache.getUnchecked("hello"));
      assertEquals(1, cache.size());
    }
  }

}
