package com.thefirstwind;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class Case004_guava_collections {
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-lists
   * ===========================================================
   */
  /**
   * Reverse a list
   */
  @Test
  public void whenReverseList_thenReversed() {
    List<String> names = Lists.newArrayList("John", "Adam", "Jane");

    List<String> reversed = Lists.reverse(names);
    assertThat(reversed, contains("Jane", "Adam", "John"));
  }

  /**
   * Generate Character List from a String
   */
  @Test
  public void whenCreateCharacterListFromString_thenCreated() {
    List<Character> chars = Lists.charactersOf("John");

    assertEquals(4, chars.size());
    assertThat(chars, contains('J', 'o', 'h', 'n'));
  }

  /**
   * Partition a List
   */
  @Test
  public void whenPartitionList_thenPartitioned(){
    List<String> names = Lists.newArrayList("John","Jane","Adam","Tom","Viki","Tyler");

    List<List<String>> result = Lists.partition(names, 2);

    assertEquals(3, result.size());
    assertThat(result.get(0), contains("John", "Jane"));
    assertThat(result.get(1), contains("Adam", "Tom"));
    assertThat(result.get(2), contains("Viki", "Tyler"));
  }

  /**
   * Remove Duplicates From List
   */
  @Test
  public void whenRemoveDuplicatesFromList_thenRemoved() {
    List<Character> chars = Lists.newArrayList('h','e','l','l','o');
    assertEquals(5, chars.size());

    List<Character> result = ImmutableSet.copyOf(chars).asList();
    assertThat(result, contains('h', 'e', 'l', 'o'));
  }

  /**
   * Remove Null Values from List
   */
  @Test
  public void whenRemoveNullFromList_thenRemoved() {
    List<String> names = Lists.newArrayList("John", null, "Adam", null, "Jane");
    Iterables.removeIf(names, Predicates.isNull());

    assertEquals(3, names.size());
    assertThat(names, contains("John", "Adam", "Jane"));
  }

  /**
   * Convert a List to an ImmutableList
   */
  @Test
  public void whenCreateImmutableList_thenCreated() {
    List<String> names = Lists.newArrayList("John", "Adam", "Jane");

    names.add("Tom");
    assertEquals(4, names.size());

    ImmutableList<String> immutable = ImmutableList.copyOf(names);
    assertThat(immutable, contains("John", "Adam", "Jane", "Tom"));
  }
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-sets
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-maps
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-collections
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-mapmaker
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-multiset
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-minmax-priority-queue-and-evicting-queue
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-rangemap
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-table
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-ordering
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-class-to-instance-map
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-rangeset
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-bimap
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-multimap
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-set-function-map-tutorial
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-filter-and-transform-a-collection
   * https://www.baeldung.com/guava-order
   * https://www.baeldung.com/guava-21-new
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-order
   * ===========================================================
   */
  /**
   * ===========================================================
   * https://www.baeldung.com/guava-21-new
   * ===========================================================
   */
}
