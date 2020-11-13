# learn google guava basics

<!--https://www.baeldung.com/guava-guide-->

## 1 Guava: Preconditions
* No arguments. Exceptions are thrown without an error message
* An extra Object argument acting as an error message. Exceptions are thrown with an error message
* An extra String argument, with an arbitrary number of additional Object arguments acting as an error message with a placeholder. This behaves a bit like printf, but for GWT compatibility and efficiency it only allows %s indicators
###  添加依赖
```xml
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>29.0-jre</version>
</dependency>
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.10.0</version>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.2.0</version>
</dependency>

```
### 1.1 Without an Error Message
We can use checkArgument without passing any extra parameter to the checkArgument method:
```java
  /**
   * Without an Error Message
   */
  @Test
  public void whenCheckArgumentEvaluatesFalse_throwsException() {
    int age = -18;
    assertThatThrownBy(() -> Preconditions.checkArgument(age > 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(null).hasNoCause();
  }
```
### 1.2 With an Error Message
We can get a meaningful error message from the checkArgument method by passing an error message:
```java
  /**
   * With an Error Message
   */
  @Test
  public void givenErrorMsg_whenCheckArgEvalsFalse_throwsException() {
    int age = -18;
    String message = "Age can't be zero or less than zero.";
    assertThatThrownBy(() -> Preconditions.checkArgument(age > 0, message))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message).hasNoCause();
  }
```

### 1.3 With a Template Error Message
We can get a meaningful error message along with dynamic data from the checkArgument method by passing an error message:
```java
  /**
   * With a Template Error Message
   */
  @Test
  public void givenTemplateMsg_whenCheckArgEvalsFalse_throwsException() {
    int age = -18;
    String message = "Age should be positive number, you supplied %s.";
    assertThatThrownBy(
            () -> Preconditions.checkArgument(age > 0, message, age))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(message, age).hasNoCause();
  }
```

### 1.4 checkElementIndex
The method checkElementIndex checks that an index is a valid index in a list, string or an array of a specified size. An element index may range from 0 inclusive to size exclusive. You don't pass a list, string or array directly, you just pass its size. This method throws an IndexOutOfBoundsException if the index is not a valid element index, else it returns an index that's being passed to the method.
```java
  /**
   * checkElementIndex
   */
  @Test
  public void givenArrayAndMsg_whenCheckElementEvalsFalse_throwsException() {
    int[] numbers = { 1, 2, 3, 4, 5 };
    String message = "Please check the bound of an array and retry";

    assertThatThrownBy(() ->
            Preconditions.checkElementIndex(6, numbers.length - 1, message))
            .isInstanceOf(IndexOutOfBoundsException.class)
            .hasMessageStartingWith(message).hasNoCause();
  }
```

### 1.5 checkNotNull
The method checkNotNull checks whether a value supplied as a parameter is null. It returns the value that's been checked. If the value that has been passed to this method is null, then a NullPointerException is thrown.
```java
  /**
   * checkNotNull
   */
  @Test
  public void givenNullString_whenCheckNotNullWithMessage_throwsException () {
    String nullObject = null;
    String message = "Please check the Object supplied, its null!";

    assertThatThrownBy(() -> Preconditions.checkNotNull(nullObject, message))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(message).hasNoCause();
  }
```
```java
  @Test
  public void whenCheckNotNullWithTemplateMessage_throwsException() {
    String nullObject = null;
    String message = "Please check the Object supplied, its %s!";

    assertThatThrownBy(
            () -> Preconditions.checkNotNull(nullObject, message,
                    new Object[] { null }))
            .isInstanceOf(NullPointerException.class)
            .hasMessage(message, nullObject).hasNoCause();
  }
```

### 1.6 checkPositionIndex
The method checkPositionIndex checks that an index passed as an argument to this method is a valid index in a list, string or array of a specified size. A position index may range from 0 inclusive to size inclusive. You don't pass the list, string or array directly, you just pass its size.

This method throws an IndexOutOfBoundsException if the index passed is not between 0 and the size given, else it returns the index value.
```java
  /**
   * checkPositionIndex
   */
  @Test
  public void givenArrayAndMsg_whenCheckPositionEvalsFalse_throwsException() {
    int[] numbers = { 1, 2, 3, 4, 5 };
    String message = "Please check the bound of an array and retry";

    assertThatThrownBy(
            () -> Preconditions.checkPositionIndex(6, numbers.length - 1, message))
            .isInstanceOf(IndexOutOfBoundsException.class)
            .hasMessageStartingWith(message).hasNoCause();
  }
```
### 1.7 checkState
The method checkState checks the validity of the state of an object and is not dependent on the method arguments. For example, an Iterator might use this to check that next has been called before any call to remove. This method throws an IllegalStateException if the state of an object (boolean value passed as an argument to the method) is in an invalid state.
```java
  /**
   * checkState
   */
  @Test
  public void givenStatesAndMsg_whenCheckStateEvalsFalse_throwsException() {
    int[] validStates = { -1, 0, 1 };
    int givenState = 10;
    String message = "You have entered an invalid state";

    assertThatThrownBy(
            () -> Preconditions.checkState(
                    Arrays.binarySearch(validStates, givenState) > 0, message))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageStartingWith(message).hasNoCause();
  }
```

## 2 Guava: Throwables
```java
try {
    methodThatMightThrowThrowable();
} catch (Throwable t) {
    Throwables.propagateIfPossible(t, Exception.class);
    throw new RuntimeException(t);
}

```

## 3 Guava: Memoizer
### 3.1 Supplier Memoization Without Eviction
```java
  @Test
  public void givenMemoizedSupplier_whenGet_thenSubsequentGetsAreFast() {
    Supplier<BigInteger> memoizedSupplier;
    memoizedSupplier = Suppliers.memoize(CostlySupplier::generateBigNumber);

    BigInteger expectedValue = new BigInteger("12345");
    assertSupplierGetExecutionResultAndDuration(
            memoizedSupplier, expectedValue, 2000D);
    assertSupplierGetExecutionResultAndDuration(
            memoizedSupplier, expectedValue, 0D);
    assertSupplierGetExecutionResultAndDuration(
            memoizedSupplier, expectedValue, 0D);
  }
  private <T> void assertSupplierGetExecutionResultAndDuration(
          Supplier<T> supplier, T expectedValue, double expectedDurationInMs) {
    Instant start = Instant.now();
    T value = supplier.get();
    Long durationInMs = Duration.between(start, Instant.now()).toMillis();
    double marginOfErrorInMs = 100D;

    assertThat(value, is(equalTo(expectedValue)));
    assertThat(
            durationInMs.doubleValue(),
            is(closeTo(expectedDurationInMs, marginOfErrorInMs)));
  }

```
### 3.2 Function Memoization
```java
  @Test
  public void givenMemoizedSupplier_fibonacciSequence() {
    LoadingCache<Integer, BigInteger> memo = CacheBuilder.newBuilder()
            .build(CacheLoader.from(FibonacciSequence::getFibonacciNumber));
  }

  @Test
  public void givenMemoizedSupplier_fibonacci() {
    LoadingCache<Integer, BigInteger> memo = CacheBuilder.newBuilder()
            .expireAfterAccess(2, TimeUnit.SECONDS)
            .build(CacheLoader.from(Fibonacci::getFibonacciNumber));

  }

```
## 4 Guava: CharMatcher
### 4.1 Remove Special Characters from a String
```java
  /**
   * Remove Special Characters from a String
   */
  @Test
  public void whenRemoveSpecialCharacters_thenRemoved(){
    String input = "H*el.lo,}12";
    CharMatcher matcher = CharMatcher.javaLetterOrDigit();
    String result = matcher.retainFrom(input);

    assertEquals("Hello12", result);
  }
```
### 4.2 Remove Non ASCII Characters From String
```java
  /**
   * Remove Non ASCII Characters From String
   */
  @Test
  public void whenRemoveNonASCIIChars_thenRemoved() {
    String input = "あhello₤";

    String result = CharMatcher.ascii().retainFrom(input);
    assertEquals("hello", result);

    result = CharMatcher.inRange('0', 'z').retainFrom(input);
    assertEquals("hello", result);
  }
```
### 4.3 Remove Characters Not in the Charset
```java
  /**
   * Remove Characters Not in the Charset
   */
  @Test
  public void whenRemoveCharsNotInCharset_thenRemoved() {
    Charset charset = Charset.forName("cp437");
    CharsetEncoder encoder = charset.newEncoder();

    Predicate<Character> inRange = new Predicate<Character>() {
      @Override
      public boolean apply(Character c) {
        return encoder.canEncode(c);
      }
    };

    String result = CharMatcher.forPredicate(inRange)
            .retainFrom("helloは");
    assertEquals("hello", result);
  }
```
### 4.4 Validate String
```java
  /**
   * Validate String
   */
  @Test
  public void whenValidateString_thenValid(){
    String input = "hello";

    boolean result = CharMatcher.javaLowerCase().matchesAllOf(input);
    assertTrue(result);

    result = CharMatcher.is('e').matchesAnyOf(input);
    assertTrue(result);

    result = CharMatcher.javaDigit().matchesNoneOf(input);
    assertTrue(result);
  }
```
### 4.5 Trim String
```java
  /**
   * Trim String
   */
  @Test
  public void whenTrimString_thenTrimmed() {
    String input = "---hello,,,";

    String result = CharMatcher.is('-').trimLeadingFrom(input);
    assertEquals("hello,,,", result);

    result = CharMatcher.is(',').trimTrailingFrom(input);
    assertEquals("---hello", result);

    result = CharMatcher.anyOf("-,").trimFrom(input);
    assertEquals("hello", result);
  }
```
### 4.6 Collapse a String
```java
  /**
   * Collapse a String
   */
  @Test
  public void whenCollapseFromString_thenCollapsed() {
    String input = "       hel    lo      ";

    String result = CharMatcher.is(' ').collapseFrom(input, '-');
    assertEquals("-hel-lo-", result);

    result = CharMatcher.is(' ').trimAndCollapseFrom(input, '-');
    assertEquals("hel-lo", result);
  }
```
### 4.7 Replace from String
```java
  /**
   * Replace from String
   */
  @Test
  public void whenReplaceFromString_thenReplaced() {
    String input = "apple-banana.";

    String result = CharMatcher.anyOf("-.").replaceFrom(input, '!');
    assertEquals("apple!banana!", result);

    result = CharMatcher.is('-').replaceFrom(input, " and ");
    assertEquals("apple and banana.", result);
  }
```
### 4.8 Count Character Occurrences
```java
  /**
   * Count Character Occurrences
   */
  @Test
  public void whenCountCharInString_thenCorrect() {
    String input = "a, c, z, 1, 2";

    int result = CharMatcher.is(',').countIn(input);
    assertEquals(4, result);

    result = CharMatcher.inRange('a', 'h').countIn(input);
    assertEquals(2, result);
  }
```
## 5 Guava: Join and Split Collections
### 5.1 Convert List into String Using Joiner
```java
@Test
public void whenConvertListToString_thenConverted() {
    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
    String result = Joiner.on(",").join(names);
 
    assertEquals(result, "John,Jane,Adam,Tom");
}
```
### 5.2 Convert Map to String Using Joiner
```java
@Test
public void whenConvertMapToString_thenConverted() {
    Map<String, Integer> salary = Maps.newHashMap();
    salary.put("John", 1000);
    salary.put("Jane", 1500);
    String result = Joiner.on(" , ").withKeyValueSeparator(" = ")
                                    .join(salary);
 
    assertThat(result, containsString("John = 1000"));
    assertThat(result, containsString("Jane = 1500"));
}
```
### 5.3 Join Nested Collections
```java
@Test
public void whenJoinNestedCollections_thenJoined() {
    List<ArrayList<String>> nested = Lists.newArrayList(
      Lists.newArrayList("apple", "banana", "orange"),
      Lists.newArrayList("cat", "dog", "bird"),
      Lists.newArrayList("John", "Jane", "Adam"));
    String result = Joiner.on(";").join(Iterables.transform(nested,
      new Function<List<String>, String>() {
          @Override
          public String apply(List<String> input) {
              return Joiner.on("-").join(input);
          }
      }));
 
    assertThat(result, containsString("apple-banana-orange"));
    assertThat(result, containsString("cat-dog-bird"));
    assertThat(result, containsString("apple-banana-orange"));
}

```
### 5.4 Handle Null Values While Using Joiner
```java
@Test
public void whenConvertListToStringAndSkipNull_thenConverted() {
    List<String> names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
    String result = Joiner.on(",").skipNulls().join(names);
 
    assertEquals(result, "John,Jane,Adam,Tom");
}
@Test
public void whenUseForNull_thenUsed() {
    List<String> names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
    String result = Joiner.on(",").useForNull("nameless").join(names);
 
    assertEquals(result, "John,nameless,Jane,Adam,Tom");
}

```
### 5.5 Create List From String Using Splitter
```java
@Test
public void whenCreateListFromString_thenCreated() {
    String input = "apple - banana - orange";
    List<String> result = Splitter.on("-").trimResults()
                                          .splitToList(input);
 
    assertThat(result, contains("apple", "banana", "orange"));
}
```
### 5.6 Create Map From String Using Splitter
```java
@Test
public void whenCreateMapFromString_thenCreated() {
    String input = "John=first,Adam=second";
    Map<String, String> result = Splitter.on(",")
                                         .withKeyValueSeparator("=")
                                         .split(input);
 
    assertEquals("first", result.get("John"));
    assertEquals("second", result.get("Adam"));
}

```
### 5.7 Split String With Multiple Separators
```java
@Test
public void whenSplitStringOnMultipleSeparator_thenSplit() {
    String input = "apple.banana,,orange,,.";
    List<String> result = Splitter.onPattern("[.,]")
                                  .omitEmptyStrings()
                                  .splitToList(input);
 
    assertThat(result, contains("apple", "banana", "orange"));
}

```
### 5.8 Split a String at Specific Length

```java
@Test
public void whenSplitStringOnSpecificLength_thenSplit() {
    String input = "Hello world";
    List<String> result = Splitter.fixedLength(3).splitToList(input);
 
    assertThat(result, contains("Hel", "lo ", "wor", "ld"));
}
```
### 5.9 Limit the Split Result
```java
@Test
public void whenLimitSplitting_thenLimited() {
    String input = "a,b,c,d,e";
    List<String> result = Splitter.on(",")
                                  .limit(4)
                                  .splitToList(input);
 
    assertEquals(4, result.size());
    assertThat(result, contains("a", "b", "c", "d,e"));
}
```
## 6 Guava: Functional Cookbook
### 6.1 filter a collection by a condition (custom Predicate)
```java
  @Test
  public void functional_filter_collection() {
    List<Integer> numbers = Lists.newArrayList(1, 2, 3, 6, 10, 34, 57, 89);
    Predicate<Integer> acceptEven = new Predicate<Integer>() {
      @Override
      public boolean apply(Integer number) {
        return (number % 2) == 0;
      }
    };
    List<Integer> evenNumbers = Lists.newArrayList(Collections2.filter(numbers, acceptEven));
    Integer found = Collections.binarySearch(evenNumbers, 57);
    assertThat(found, lessThan(0));
  }
```
### 6.2 filter out nulls from a collection
```java
  @Test
  public void functional_filter_null() {
    List<String> withNulls = Lists.newArrayList("a", "bc", null, "def");
    Iterable<String> withoutNuls = Iterables.filter(withNulls, Predicates.notNull());
    assertTrue(Iterables.all(withoutNuls, Predicates.notNull()));
  }
```
### 6.3 check condition for all elements of a collection
```java
  @Test
  public void functional_check_condition() {
    List<Integer> evenNumbers = Lists.newArrayList(2, 6, 8, 10, 34, 90);
    Predicate<Integer> acceptEven = new Predicate<Integer>() {
      @Override
      public boolean apply(Integer number) {
        return (number % 2) == 0;
      }
    };
    assertTrue(Iterables.all(evenNumbers, acceptEven));
  }
```
### 6.4 negate a predicate
```java
  @Test
  public void functional_negate_predicate() {
    List<Integer> evenNumbers = Lists.newArrayList(2, 6, 8, 10, 34, 90);
    Predicate<Integer> acceptOdd = new Predicate<Integer>() {
      @Override
      public boolean apply(Integer number) {
        return (number % 2) != 0;
      }
    };
    assertTrue(Iterables.all(evenNumbers, Predicates.not(acceptOdd)));

  }
```
### 6.5 apply a simple function
```java
  @Test
  public void functional_apply_function() {
    List<Integer> numbers = Lists.newArrayList(1, 2, 3);
    List<String> asStrings = Lists.transform(numbers, Functions.toStringFunction());
    assertThat(asStrings, contains("1", "2", "3"));
  }

```
### 6.6 sort collection by first applying an intermediary function
```java
  @Test
  public void functional_sort_collection() {
    List<Integer> numbers = Arrays.asList(2, 1, 11, 100, 8, 14);
    Ordering<Object> ordering = Ordering.natural().onResultOf(Functions.toStringFunction());
    List<Integer> inAlphabeticalOrder = ordering.sortedCopy(numbers);
    List<Integer> correctAlphabeticalOrder = Lists.newArrayList(1, 100, 11, 14, 2, 8);
    assertThat(correctAlphabeticalOrder, equalTo(inAlphabeticalOrder));
  }
```
### 6.7 complex example – chaining predicates and functions
```java
  @Test
  public void functional_complex() {
    List<Integer> numbers = Arrays.asList(2, 1, 11, 100, 8, 14);
    Predicate<Integer> acceptEvenNumber = new Predicate<Integer>() {
      @Override
      public boolean apply(Integer number) {
        return (number % 2) == 0;
      }
    };
    Function<Integer, Integer> powerOfTwo = new Function<Integer, Integer>() {
      @Override
      public Integer apply(Integer input) {
        return (int) Math.pow(input, 2);
      }
    };

    FluentIterable<Integer> powerOfTwoOnlyForEvenNumbers =
            FluentIterable.from(numbers).filter(acceptEvenNumber).transform(powerOfTwo);
    assertThat(powerOfTwoOnlyForEvenNumbers, contains(4, 10000, 64, 196));

  }
```

### 6.8 compose two functions
```java
  @Test
  public void functional_compose_two_functions() {
    List<Integer> numbers = Arrays.asList(2, 3);
    Function<Integer, Integer> powerOfTwo = new Function<Integer, Integer>() {
      @Override
      public Integer apply(Integer input) {
        return (int) Math.pow(input, 2);
      }
    };
    List<Integer> result = Lists.transform(numbers,
            Functions.compose(powerOfTwo, powerOfTwo));
    assertThat(result, contains(16, 81));

  }
```
### 6.9 create a Map backed by a Set and a Function
```java
  @Test
  public void functional_create_map() {
    Function<Integer, Integer> powerOfTwo = new Function<Integer, Integer>() {
      @Override
      public Integer apply(Integer input) {
        return (int) Math.pow(input, 2);
      }
    };
    Set<Integer> lowNumbers = Sets.newHashSet(2, 3, 4);

    Map<Integer, Integer> numberToPowerOfTwoMuttable = Maps.asMap(lowNumbers, powerOfTwo);
    Map<Integer, Integer> numberToPowerOfTwoImuttable = Maps.toMap(lowNumbers, powerOfTwo);
    assertThat(numberToPowerOfTwoMuttable.get(2), equalTo(4));
    assertThat(numberToPowerOfTwoImuttable.get(2), equalTo(4));

  }
```
### 6.10 create a Function out of a Predicate
```java
  @Test
  public void functional_create_function() {
    List<Integer> numbers = Lists.newArrayList(1, 2, 3, 6);
    Predicate<Integer> acceptEvenNumber = new Predicate<Integer>() {
      @Override
      public boolean apply(Integer number) {
        return (number % 2) == 0;
      }
    };
    Function<Integer, Boolean> isEventNumberFunction = Functions.forPredicate(acceptEvenNumber);
    List<Boolean> areNumbersEven = Lists.transform(numbers, isEventNumberFunction);

    assertThat(areNumbersEven, contains(false, true, false, true));

  }
```





