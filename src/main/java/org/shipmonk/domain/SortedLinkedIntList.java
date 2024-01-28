package org.shipmonk.domain;

import org.shipmonk.domain.nodes.Node;

public final class SortedLinkedIntList extends SortedLinkedList<Integer> {

    private final Node<Integer> head;

    SortedLinkedIntList(final Node<Integer> head) {
        this.head = head;
    }

    @Override
    public Node<Integer> head() {
        return this.head;
    }

    @Override
    public SortedLinkedListBuilder<Integer> toBuilder() {
        return new SortedLinkedIntListBuilder(this.head);
    }
}