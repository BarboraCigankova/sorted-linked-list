package org.shipmonk.domain;

import com.google.common.collect.Ordering;
import org.shipmonk.TestUtils;
import org.shipmonk.domain.nodes.Node;
import org.shipmonk.factory.SortedLinkedIntListFactory;
import org.shipmonk.factory.SortedLinkedListFactory;
import org.shipmonk.factory.SortedLinkedStringListFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class SortedSortedLinkedListTest {

    private static final File DATA_FILE_INT_MILL = new File("src/test/resources/data_int_1_000_000.txt");
    private static final File DATA_FILE_STR_MILL = new File("src/test/resources/data_str_1_000_000.txt");
    private static final List<Integer> TEST_INT_DATA = List.of(43, 8, 6, 10, 55, 14);
    private static final List<String> TEST_STRING_DATA = List.of("SHIPMONK CHCE BÁRU", "aaaa", "ddDDdd", "ZZZ", "blabla", "SHIPMONK JE BOŽÍ");

    private final SortedLinkedListFactory<Integer> intListFactory = new SortedLinkedIntListFactory();
    private final SortedLinkedListFactory<String> stringListFactory = new SortedLinkedStringListFactory();

    @Test
    public void shouldAddValuesAndGetSortedIntLinkedList() throws Exception {
        //TestUtils.fillTestDataFileWithRandomInts(1_000_000, 10_000_000, DATA_FILE_INT_MILL);

        final List<Integer> dataList = TestUtils.getIntList(DATA_FILE_INT_MILL);

        long start = System.currentTimeMillis();

        final SortedLinkedListBuilder<Integer> builder = this.intListFactory.createLinkedListBuilder();
        dataList.forEach(builder::add);
        final SortedLinkedList<Integer> sortedLinkedList = builder.build();

        long duration = System.currentTimeMillis() - start;
        System.out.println("Int linked list ADD duration: " + duration);

        assertIntLinkedListSorted(sortedLinkedList);
    }

    @Test
    public void shouldAddValuesAndGetSortedStrLinkedList() throws Exception {
        //TestUtils.fillTestDataFileWithRandomString(1_000_000, 1, 1000, DATA_FILE_STR_MILL);

        final List<String> dataList = TestUtils.getStrList(DATA_FILE_STR_MILL);

        long start = System.currentTimeMillis();

        final SortedLinkedListBuilder<String> builder = this.stringListFactory.createLinkedListBuilder();
        dataList.forEach(builder::add);
        final SortedLinkedList<String> linkedList = builder.build();

        long duration = System.currentTimeMillis() - start;
        System.out.println("String linked list ADD duration: " + duration);

        assertStringLinkedListSorted(linkedList);
    }

    @Test
    public void shouldAddAllValuesAndGetSortedIntLinkedList() throws Exception {
        final List<Integer> dataList = TestUtils.getIntList(DATA_FILE_INT_MILL);

        long start = System.currentTimeMillis();

        final SortedLinkedListBuilder<Integer> builder = this.intListFactory.createLinkedListBuilder();
        builder.addAll(dataList);
        final SortedLinkedList<Integer> sortedLinkedList = builder.build();

        long duration = System.currentTimeMillis() - start;
        System.out.println("Int linked list ADD_ALL duration: " + duration);

        assertIntLinkedListSorted(sortedLinkedList);
    }

    @Test
    public void shouldAddAllValuesAndGetSortedStrLinkedList() throws Exception {
        final List<String> dataList = TestUtils.getStrList(DATA_FILE_STR_MILL);

        long start = System.currentTimeMillis();

        final SortedLinkedListBuilder<String> builder = this.stringListFactory.createLinkedListBuilder();
        builder.addAll(dataList);
        final SortedLinkedList<String> linkedList = builder.build();

        long duration = System.currentTimeMillis() - start;
        System.out.println("String linked list ADD_ALL duration: " + duration);

        assertStringLinkedListSorted(linkedList);
    }

    @Test
    public void shouldContainsIntLinkedList() throws Exception {
        {
            final SortedLinkedListBuilder<Integer> builder = this.intListFactory.createLinkedListBuilder();
            builder.addAll(TEST_INT_DATA);

            final SortedLinkedList<Integer> linkedList = builder.build();
            Assertions.assertTrue(linkedList.contains(TEST_INT_DATA.getFirst()));
            Assertions.assertFalse(linkedList.contains(-1));
        }

        {
            int requiredInt = -1;
            final List<Integer> listFromFile = TestUtils.getIntList(DATA_FILE_INT_MILL);
            listFromFile.add(requiredInt);

            long start = System.currentTimeMillis();
            final SortedLinkedListBuilder<Integer> builder = this.intListFactory.createLinkedListBuilder();
            builder.addAll(listFromFile);

            Assertions.assertTrue(builder.build().contains(requiredInt));

            long duration = System.currentTimeMillis() - start;
            System.out.println("Int linked list CONTAINS duration: " + duration);
        }
    }

    @Test
    public void shouldContainsStringLinkedList() throws Exception {
        {
            final SortedLinkedListBuilder<String> builder = this.stringListFactory.createLinkedListBuilder();
            builder.addAll(TEST_STRING_DATA);

            final SortedLinkedList<String> linkedList = builder.build();
            Assertions.assertTrue(linkedList.contains(TEST_STRING_DATA.getFirst()));
            Assertions.assertFalse(linkedList.contains("SHIPMONK JE NUDA"));
        }

        {
            final String requiredStr = "SHIPMONK JE NEJLEPŠÍ";
            final List<String> listFromFile = TestUtils.getStrList(DATA_FILE_STR_MILL);
            listFromFile.add(requiredStr);

            long start = System.currentTimeMillis();
            final SortedLinkedListBuilder<String> builder = this.stringListFactory.createLinkedListBuilder();
            builder.addAll(listFromFile);

            final SortedLinkedList<String> linkedList = builder.build();
            Assertions.assertTrue(linkedList.contains(requiredStr));

            long duration = System.currentTimeMillis() - start;
            System.out.println("String linked list CONTAINS duration: " + duration);
        }
    }

    @Test
    public void shouldRemoveFromIntLinkedList() {
        final SortedLinkedListBuilder<Integer> builder = this.intListFactory.createLinkedListBuilder();
        builder.addAll(TEST_INT_DATA);

        final int valueToRemove = TEST_INT_DATA.getFirst();

        Assertions.assertTrue(() -> {
            Node<Integer> node = builder.build().head();
            while (node.next() != null) {
                if (node.data().equals(valueToRemove)) {
                    return true;
                }
                node = node.next();
            }
            return false;
        });

        builder.remove(valueToRemove);

        Assertions.assertTrue(() -> {
            Node<Integer> node = builder.build().head();
            while (node.next() != null) {
                if (node.data().equals(valueToRemove)) {
                    return false;
                }
                node = node.next();
            }
            return true;
        });
    }

    @Test
    public void shouldRemoveFromStringLinkedList() {
        final SortedLinkedListBuilder<String> builder = this.stringListFactory.createLinkedListBuilder();
        builder.addAll(TEST_STRING_DATA);

        final String valueToRemove = TEST_STRING_DATA.getLast();

        Assertions.assertTrue(() -> {
            Node<String> node = builder.build().head();
            while (node.next() != null) {
                if (node.data().equals(valueToRemove)) {
                    return true;
                }
                node = node.next();
            }
            return false;
        });

        builder.remove(valueToRemove);

        Assertions.assertTrue(() -> {
            Node<String> node = builder.build().head();
            while (node.next() != null) {
                if (node.data().equals(valueToRemove)) {
                    return false;
                }
                node = node.next();
            }
            return true;
        });
    }

    @Test
    public void shouldNotThrowExceptionRemoveFromEmptyIntLinkedListOrRemoveNonExistentInt() {
        final SortedLinkedListBuilder<Integer> builder = this.intListFactory.createLinkedListBuilder();
        builder.remove(1_000_000);

        builder.addAll(TEST_INT_DATA);
        builder.remove(1_000_000);
    }

    @Test
    public void shouldNotThrowExceptionRemoveFromEmptyStringLinkedListOrRemoveNonExistentStr() {
        final SortedLinkedListBuilder<String> builder = this.stringListFactory.createLinkedListBuilder();
        builder.remove("empty");

        builder.addAll(TEST_STRING_DATA);
        builder.remove("bž");
    }

    @Test
    public void shouldBeEmpty() {
        {
            final SortedLinkedList<Integer> sortedLinkedList = this.intListFactory.createLinkedListBuilder().build();
            Assertions.assertTrue(sortedLinkedList.isEmpty());
        }

        {
            final SortedLinkedList<String> sortedLinkedList = this.stringListFactory.createLinkedListBuilder().build();
            Assertions.assertTrue(sortedLinkedList.isEmpty());
        }
    }

    @Test
    public void shouldClearAll() {
        {
            final SortedLinkedListBuilder<Integer> builder = this.intListFactory.createLinkedListBuilder();
            builder.addAll(TEST_INT_DATA);
            final SortedLinkedList<Integer> sortedLinkedList = builder.build();
            Assertions.assertFalse(sortedLinkedList.isEmpty());

            final SortedLinkedListBuilder<Integer> builderAfterAdd = sortedLinkedList.toBuilder();
            builderAfterAdd.clear();
            Assertions.assertTrue(builderAfterAdd.build().isEmpty());
        }

        {
            SortedLinkedListBuilder<String> builder = this.stringListFactory.createLinkedListBuilder();
            builder.addAll(TEST_STRING_DATA);
            final SortedLinkedList<String> sortedLinkedList = builder.build();
            Assertions.assertFalse(sortedLinkedList.isEmpty());

            final SortedLinkedListBuilder<String> builderAfterAdd = sortedLinkedList.toBuilder();
            builderAfterAdd.clear();
            Assertions.assertTrue(builderAfterAdd.build().isEmpty());
        }
    }

    @Test
    public void shouldGetIntJavaLinkedListFromSortedLinkedIntList() throws Exception {
        final List<Integer> listFromFile = TestUtils.getIntList(DATA_FILE_INT_MILL);

        final SortedLinkedListBuilder<Integer> builder = this.intListFactory.createLinkedListBuilder();
        builder.addAll(listFromFile);

        final SortedLinkedList<Integer> sortedLinkedList = builder.build();
        final LinkedList<Integer> javaLinkedList = sortedLinkedList.toJavaLinkedList();
        Assertions.assertTrue(Ordering.natural().isOrdered(javaLinkedList));
    }

    @Test
    public void shouldGetStringJavaLinkedListFromSortedLinkedStringList() throws Exception {
        final List<String> listFromFile = TestUtils.getStrList(DATA_FILE_STR_MILL);

        final SortedLinkedListBuilder<String> builder = this.stringListFactory.createLinkedListBuilder();
        builder.addAll(listFromFile);

        final SortedLinkedList<String> sortedLinkedList = builder.build();
        final LinkedList<String> javaLinkedList = sortedLinkedList.toJavaLinkedList();
        Assertions.assertTrue(Ordering.natural().isOrdered(javaLinkedList));
    }

    @Test
    public void shouldGetSortedLinkedIntListFromJavaLinkedList() throws Exception {
        final LinkedList<Integer> listFromFile = TestUtils.getIntList(DATA_FILE_INT_MILL);
        Assertions.assertFalse(Ordering.natural().isOrdered(listFromFile));

        final SortedLinkedList<Integer> sortedLinkedList = this.intListFactory.fromJavaLinkedList(listFromFile);
        assertIntLinkedListSorted(sortedLinkedList);
    }

    @Test
    public void shouldGetSortedLinkedStringListFromJavaLinkedList() throws Exception {
        final LinkedList<String> listFromFile = TestUtils.getStrList(DATA_FILE_STR_MILL);
        Assertions.assertFalse(Ordering.natural().isOrdered(listFromFile));

        final SortedLinkedList<String> sortedLinkedList = this.stringListFactory.fromJavaLinkedList(listFromFile);
        assertStringLinkedListSorted(sortedLinkedList);
    }

    private void assertIntLinkedListSorted(final SortedLinkedList<Integer> linkedList) {
        Assertions.assertTrue(() -> {
            Node<Integer> node = linkedList.head();
            while (node.next() != null) {
                if (node.data() > node.next().data()) {
                    return false;
                }
                node = node.next();
            }
            return true;
        });
    }

    private void assertStringLinkedListSorted(final SortedLinkedList<String> linkedList) {
        Assertions.assertTrue(() -> {
            Node<String> node = linkedList.head();

            while (node.next() != null) {
                if (node.data().compareTo(node.next().data()) > 0) {
                    return false;
                }
                node = node.next();
            }
            return true;
        });
    }
}