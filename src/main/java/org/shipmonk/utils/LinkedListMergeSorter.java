package org.shipmonk.utils;

import org.shipmonk.domain.nodes.Node;

public class LinkedListMergeSorter<T> {

    private final LinkedListMiddleFinder<T> finder;

    public LinkedListMergeSorter(final LinkedListMiddleFinder<T> finder) {
        this.finder = finder;
    }

    public Node<T> mergeSort(final Node<T> head) {
        if (head == null || head.next() == null) {
            return head;
        }

        final Node<T> middle = this.finder.getMiddle(head);
        final Node<T> middleNext = middle.next();
        middle.setNext(null);

        final Node<T> left = mergeSort(head);
        final Node<T> right = mergeSort(middleNext);

        return merge(left, right);
    }

    private Node<T> merge(Node<T> node1, Node<T> node2) {
        Node<T> newHead;
        Node<T> tail;

        if (node1.isLowerOrEquals(node2)) {
            newHead = node1;
            node1 = node1.next();
        } else {
            newHead = node2;
            node2 = node2.next();
        }
        tail = newHead;

        while (node1 != null && node2 != null) {
            if (node1.isLowerOrEquals(node2)) {
                tail.setNext(node1);
                node1.setPrev(tail);

                node1 = node1.next();
            } else {
                tail.setNext(node2);
                node2.setPrev(tail);

                node2 = node2.next();
            }

            tail = tail.next();
        }

        if (node1 != null) {
            tail.setNext(node1);
            node1.setPrev(tail);
        }
        if (node2 != null) {
            tail.setNext(node2);
            node2.setPrev(tail);
        }

        newHead.setPrev(null);
        return newHead;
    }
}