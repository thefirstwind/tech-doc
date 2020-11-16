package com.thefirstwind;

import com.google.common.base.Optional;
import com.google.common.cache.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class Case003_guava_cache {

  /**
   * How to Use Guava Cache
   */
  @Test
  public void whenCacheMiss_thenValueIsComputed() throws InterruptedException {

    CacheLoader<String, String> loader;
    loader = new CacheLoader<String, String>() {
      @Override
      public String load(String key) {
        return key.toUpperCase();
      }
    };

    LoadingCache<String, String> cache;
    cache = CacheBuilder.newBuilder().build(loader);

    System.out.println("cache map: " + cache.asMap());

    assertEquals(0, cache.size());
    assertEquals("HELLO", cache.getUnchecked("hello"));

    System.out.println("cache map: " + cache.asMap());
    assertEquals(1, cache.size());

    cache.getUnchecked("hello");
    System.out.println("cache size: " + cache.size());
    System.out.println("cache map: " + cache.asMap());
  }

  /**
   * Eviction by Size
   */
  @Test
  public void whenCacheReachMaxSize_thenEviction() {
    CacheLoader<String, String> loader;
    loader = new CacheLoader<String, String>() {
      @Override
      public String load(String key) {
        return key.toUpperCase();
      }
    };
    LoadingCache<String, String> cache;
    cache = CacheBuilder.newBuilder().maximumSize(3).build(loader);

    System.out.println("cache map: " + cache.asMap());

    cache.getUnchecked("first");
    cache.getUnchecked("second");
    cache.getUnchecked("third");
    cache.getUnchecked("forth");
    assertEquals(3, cache.size());
    System.out.println("cache map: " + cache.asMap());

    assertNull(cache.getIfPresent("first"));
    assertEquals("FORTH", cache.getIfPresent("forth"));
    System.out.println("cache map: " + cache.asMap());

  }

  /**
   * Eviction by Weight
   */
  @Test
  public void whenCacheReachMaxWeight_thenEviction() {
    CacheLoader<String, String> loader;
    loader = new CacheLoader<String, String>() {
      @Override
      public String load(String key) {
        return key.toUpperCase();
      }
    };

    Weigher<String, String> weighByLength;
    weighByLength = new Weigher<String, String>() {
      @Override
      public int weigh(String key, String value) {
        return value.length();
      }
    };

    LoadingCache<String, String> cache;
    cache = CacheBuilder.newBuilder()
            .maximumWeight(16)
            .weigher(weighByLength)
            .build(loader);

    System.out.println("cache map: " + cache.asMap());

    cache.getUnchecked("first");
    cache.getUnchecked("second");
    cache.getUnchecked("third");
    cache.getUnchecked("last");
    System.out.println("cache map: " + cache.asMap());

    assertEquals(3, cache.size());
    assertNull(cache.getIfPresent("first"));
    assertEquals("LAST", cache.getIfPresent("last"));
    System.out.println("cache map: " + cache.asMap());

  }

  /**
   * Eviction by Time
   */
  @Test
  public void whenEntryIdle_thenEviction()
          throws InterruptedException {
    CacheLoader<String, String> loader;
    loader = new CacheLoader<String, String>() {
      @Override
      public String load(String key) {
        return key.toUpperCase();
      }
    };

    LoadingCache<String, String> cache;
    cache = CacheBuilder.newBuilder()
            .expireAfterAccess(2, TimeUnit.MILLISECONDS)
            .build(loader);

    cache.getUnchecked("hello");
    assertEquals(1, cache.size());

    cache.getUnchecked("hello");
    Thread.sleep(300);

    cache.getUnchecked("test");
    assertEquals(1, cache.size());
    assertNull(cache.getIfPresent("hello"));
  }


  @Test
  public void whenEntryLiveTimeExpire_thenEviction()
          throws InterruptedException {
    CacheLoader<String, String> loader;
    loader = new CacheLoader<String, String>() {
      @Override
      public String load(String key) {
        return key.toUpperCase();
      }
    };

    LoadingCache<String, String> cache;
    cache = CacheBuilder.newBuilder()
            .expireAfterWrite(2,TimeUnit.MILLISECONDS)
            .build(loader);

    cache.getUnchecked("hello");
    assertEquals(1, cache.size());
    Thread.sleep(300);
    cache.getUnchecked("test");
    assertEquals(1, cache.size());
    assertNull(cache.getIfPresent("hello"));
  }


  /**
   *  Weak Keys
   */
  @Test
  public void whenWeakKeyHasNoRef_thenRemoveFromCache() {
    CacheLoader<String, String> loader;
    loader = new CacheLoader<String, String>() {
      @Override
      public String load(String key) {
        return key.toUpperCase();
      }
    };

    LoadingCache<String, String> cache;
    cache = CacheBuilder.newBuilder().weakKeys().build(loader);
  }

  /**
   * Soft Values
   */
  @Test
  public void whenSoftValue_thenRemoveFromCache() {
    CacheLoader<String, String> loader;
    loader = new CacheLoader<String, String>() {
      @Override
      public String load(String key) {
        return key.toUpperCase();
      }
    };
    LoadingCache<String, String> cache;
    cache = CacheBuilder.newBuilder().softValues().build(loader);
  }

  /**
   * Handle null Values
   */
  @Test
  public void whenNullValue_thenOptional() {
    CacheLoader<String, Optional<String>> loader;
    loader = new CacheLoader<String, Optional<String>>() {
      @Override
      public Optional<String> load(String key) {
        return Optional.fromNullable(getSuffix(key));
      }
    };

    LoadingCache<String, Optional<String>> cache;
    cache = CacheBuilder.newBuilder().build(loader);

    assertEquals("txt", cache.getUnchecked("text.txt").get());
    assertFalse(cache.getUnchecked("hello").isPresent());
  }
  private String getSuffix(final String str) {
    int lastIndex = str.lastIndexOf('.');
    if (lastIndex == -1) {
      return null;
    }
    return str.substring(lastIndex + 1);
  }

  /**
   * Refresh the Cache
   */
  @Test
  public void whenLiveTimeEnd_thenRefresh() {
    CacheLoader<String, String> loader;
    loader = new CacheLoader<String, String>() {
      @Override
      public String load(String key) {
        return key.toUpperCase();
      }
    };

    LoadingCache<String, String> cache;
    cache = CacheBuilder.newBuilder()
            .refreshAfterWrite(1,TimeUnit.MINUTES)
            .build(loader);
  }

  /**
   * Preload the Cache
   */
  @Test
  public void whenPreloadCache_thenUsePutAll() {
    CacheLoader<String, String> loader;
    loader = new CacheLoader<String, String>() {
      @Override
      public String load(String key) {
        return key.toUpperCase();
      }
    };

    LoadingCache<String, String> cache;
    cache = CacheBuilder.newBuilder().build(loader);

    Map<String, String> map = new HashMap<String, String>();
    map.put("first", "FIRST");
    map.put("second", "SECOND");
    cache.putAll(map);

    assertEquals(2, cache.size());
  }

  /**
   * RemovalNotification
   */

  @Test
  public void whenEntryRemovedFromCache_thenNotify() {
    CacheLoader<String, String> loader;
    loader = new CacheLoader<String, String>() {
      @Override
      public String load(final String key) {
        return key.toUpperCase();
      }
    };

    RemovalListener<String, String> listener;
    listener = new RemovalListener<String, String>() {
      @Override
      public void onRemoval(RemovalNotification<String, String> n){
        if (n.wasEvicted()) {
          System.out.println("wasEvicted");

          String cause = n.getCause().name();
          System.out.println("cause:" + cause);
          assertEquals(RemovalCause.SIZE.toString(),cause);
        }
      }
    };

    LoadingCache<String, String> cache;
    cache = CacheBuilder.newBuilder()
            .maximumSize(3)
            .removalListener(listener)
            .build(loader);
    System.out.println("cache map: " + cache.asMap());

    cache.getUnchecked("first");
    System.out.println("cache map: " + cache.asMap());
    cache.getUnchecked("second");
    System.out.println("cache map: " + cache.asMap());
    cache.getUnchecked("third");
    System.out.println("cache map: " + cache.asMap());
    cache.getUnchecked("last");
    System.out.println("cache map: " + cache.asMap());
    assertEquals(3, cache.size());
  }

}
