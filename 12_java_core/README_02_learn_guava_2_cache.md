# Guava Cache

<!--https://www.baeldung.com/guava-cache-->
<!--https://www.baeldung.com/guava-cacheloader-->

[github](https://github.com/thefirstwind/tech-doc/blob/master/12_java_core/src/main/java/com/thefirstwind/Case003_guava_cache.java)

## 1 How to Use Guava Cache
First, we'll create the CacheLoader – used to compute the value stored in the cache.  
From this, we'll use the handy CacheBuilder to build our cache using the given specifications:

```java
@Test
public void whenCacheMiss_thenValueIsComputed() {
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
```

## 2 Eviction Policies
Every cache needs to remove values at some point. Let's discuss the mechanism of evicting values out of the cache – using different criteria.

We can limit the size of our cache using maximumSize(). If the cache reaches the limit, the oldest items will be evicted.

```java
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
```
## 3 Weak Keys
weak references – allowing the garbage collector to collect cache keys that are not referenced elsewhere.
```java
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
```

## 4 Soft Values
We can allow the garbage collector to collect our cached values by using softValues() as in the following example:
```java
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
                                                                 
```

## 5 Handle null Values
```java
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

```
## 6 Refresh the Cache
```java
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
```
## 7 Preload the Cache
```java
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
```
## 8 RemovalNotification
Sometimes, you need to take some actions when a record is removed from the cache; so, let's discuss RemovalNotification.

We can register a RemovalListener to get notifications of a record being removed. We also have access to the cause of the removal – via the getCause() method.


```java
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
```

## 9 Notes
## 10 CacheLoader