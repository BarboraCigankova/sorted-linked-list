package org.shipmonk.domain;

import com.google.common.collect.Lists;
import org.shipmonk.domain.nodes.Node;
import org.shipmonk.utils.LinkedListBinarySearcher;
import org.shipmonk.utils.LinkedListMiddleFinder;

import java.util.LinkedList;

public sealed abstract class SortedLinkedList<T> permits SortedLinkedStringList, SortedLinkedIntList {

    private final LinkedListMiddleFinder<T> finder = new LinkedListMiddleFinder<>();
    private final LinkedListBinarySearcher<T> binarySearcher = new LinkedListBinarySearcher<>(this.finder);

    public boolean contains(final T value) {
        return this.binarySearcher.search(value, head()).isPresent();
    }

    public boolean isEmpty() {
        return head() == null;
    }

    public LinkedList<T> toJavaLinkedList() {
        final LinkedList<T> list = Lists.newLinkedList();

        Node<T> node = head();
        while (node != null) {
            list.add(node.data());
            node = node.next();
        }

        return list;
    }

    public void print() {
        Node<T> node = head();
        while (node != null) {
            System.out.print(node.data() + " ");
            node = node.next();
        }
    }

    public abstract SortedLinkedListBuilder<T> toBuilder();

    protected abstract Node<T> head();
}