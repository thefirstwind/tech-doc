# learn google guava basics

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

## 4 Guava: CharMatcher
## 5 Guava: Join and Split Collections
## 6 Guava: Functional Cookbook
## 7 Guava: Ordering Cookbook
## 8 Guava: Collections Cookbook




