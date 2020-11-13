package com.thefirstwind;

import com.google.common.base.Preconditions;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class Case002_guava_basics {


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

}
