package org.shipmonk.utils;

import org.shipmonk.domain.nodes.Node;

public class LinkedListMiddleFinder<T> {

    public Node<T> getMiddle(final Node<T> head) {
        if (head == null) {
            return head;
        }

        Node<T> mid = head;
        Node<T> tail = head.next();

        while (mid.next() != null && (tail != null && tail.next() != null)) {
            mid = mid.next();
            tail = tail.next().next();
        }
        return mid;
    }

    public Node<T> getMiddle(final Node<T> start, final Node<T> end) {
        if (start == null) {
            return start;
        }

        Node<T> mid = start;
        Node<T> tail = start.next();

        while (tail != end) {
            tail = tail.next();

            if (tail != end) {
                mid = mid.next();
                tail = tail.next();
            }
        }
        return mid;
    }
}
