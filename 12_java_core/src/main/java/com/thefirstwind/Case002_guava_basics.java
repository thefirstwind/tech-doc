package com.thefirstwind;

import com.google.common.base.*;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.*;
import com.thefirstwind.clz.CostlySupplier;
import com.thefirstwind.clz.Fibonacci;
import com.thefirstwind.clz.FibonacciSequence;
import org.junit.Test;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.*;

public class Case002_guava_basics {


  /**
   * ========================================================
   https://www.baeldung.com/guava-preconditions
   * ========================================================
   */
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

  /**
   * ========================================================
   https://www.baeldung.com/guava-memoizer
   * ========================================================
   */
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
  /**
   * ========================================================
   https://www.baeldung.com/guava-string-charmatcher
   * ========================================================
   */

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

  /**
   * ========================================================
   https://www.baeldung.com/guava-joiner-and-splitter-tutorial
   * ========================================================
   */
  /**
   * Convert List into String Using Joiner
   */
  @Test
  public void whenConvertListToString_thenConverted() {
    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
    String result = Joiner.on(",").join(names);

    assertEquals(result, "John,Jane,Adam,Tom");
  }

  /**
   * Convert Map to String Using Joiner
   */
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

  /**
   * Join Nested Collections
   */
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

  /**
   * Handle Null Values While Using Joiner
   */
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

  /**
   * Create List From String Using Splitter
   */
  @Test
  public void whenCreateListFromString_thenCreated() {
    String input = "apple - banana - orange";
    List<String> result = Splitter.on("-").trimResults()
            .splitToList(input);

    assertThat(result, contains("apple", "banana", "orange"));
  }

  /**
   * Create Map From String Using Splitter
   */
  @Test
  public void whenCreateMapFromString_thenCreated() {
    String input = "John=first,Adam=second";
    Map<String, String> result = Splitter.on(",")
            .withKeyValueSeparator("=")
            .split(input);

    assertEquals("first", result.get("John"));
    assertEquals("second", result.get("Adam"));
  }

  /**
   * Split String With Multiple Separators
   */
  @Test
  public void whenSplitStringOnMultipleSeparator_thenSplit() {
    String input = "apple.banana,,orange,,.";
    List<String> result = Splitter.onPattern("[.,]")
            .omitEmptyStrings()
            .splitToList(input);

    assertThat(result, contains("apple", "banana", "orange"));
  }

  /**
   * Split a String at Specific Length
   */
  @Test
  public void whenSplitStringOnSpecificLength_thenSplit() {
    String input = "Hello world";
    List<String> result = Splitter.fixedLength(3).splitToList(input);

    assertThat(result, contains("Hel", "lo ", "wor", "ld"));
  }

  /**
   * Limit the Split Result
   */
  @Test
  public void whenLimitSplitting_thenLimited() {
    String input = "a,b,c,d,e";
    List<String> result = Splitter.on(",")
            .limit(4)
            .splitToList(input);

    assertEquals(4, result.size());
    assertThat(result, contains("a", "b", "c", "d,e"));
  }

  /**
   * ========================================================
   https://www.baeldung.com/guava-functions-predicates
   * ========================================================
   */
  /**
   * filter a collection by a condition (custom Predicate)
   */
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
  /**
   * filter out nulls from a collection
   */
  @Test
  public void functional_filter_null() {
    List<String> withNulls = Lists.newArrayList("a", "bc", null, "def");
    Iterable<String> withoutNuls = Iterables.filter(withNulls, Predicates.notNull());
    assertTrue(Iterables.all(withoutNuls, Predicates.notNull()));
  }

  /**
   * check condition for all elements of a collection
   */
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
  /**
   * negate a predicate
   */
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
  /**
   * apply a simple function
   */
  @Test
  public void functional_apply_function() {
    List<Integer> numbers = Lists.newArrayList(1, 2, 3);
    List<String> asStrings = Lists.transform(numbers, Functions.toStringFunction());
    assertThat(asStrings, contains("1", "2", "3"));
  }
  /**
   * sort collection by first applying an intermediary function
   */
  @Test
  public void functional_sort_collection() {
    List<Integer> numbers = Arrays.asList(2, 1, 11, 100, 8, 14);
    Ordering<Object> ordering = Ordering.natural().onResultOf(Functions.toStringFunction());
    List<Integer> inAlphabeticalOrder = ordering.sortedCopy(numbers);
    List<Integer> correctAlphabeticalOrder = Lists.newArrayList(1, 100, 11, 14, 2, 8);
    assertThat(correctAlphabeticalOrder, equalTo(inAlphabeticalOrder));
  }
  /**
   * complex example – chaining predicates and functions
   */
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
  /**
   * compose two functions
   */
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
  /**
   * create a Map backed by a Set and a Function
   */
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
  /**
   * create a Function out of a Predicate
   */
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
}
