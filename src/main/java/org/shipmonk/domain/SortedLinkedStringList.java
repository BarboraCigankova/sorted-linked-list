package org.shipmonk.domain;

import org.shipmonk.domain.nodes.Node;

public final class SortedLinkedStringList extends SortedLinkedList<String> {

    private final Node<String> head;

    SortedLinkedStringList(final Node<String> head) {
        this.head = head;
    }

    @Override
    public Node<String> head() {
        return this.head;
    }

    @Override
    public SortedLinkedListBuilder<String> toBuilder() {
        return new SortedLinkedStringListBuilder(this.head);
    }
}