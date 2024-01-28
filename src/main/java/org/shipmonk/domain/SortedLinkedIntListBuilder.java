package org.shipmonk.domain;

import com.google.common.base.Preconditions;
import org.shipmonk.domain.nodes.IntNode;
import org.shipmonk.domain.nodes.Node;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SortedLinkedIntListBuilder extends SortedLinkedListBuilder<Integer>{

    private final AtomicBoolean isSorted = new AtomicBoolean(false);
    private Node<Integer> head;

    public SortedLinkedIntListBuilder() {
    }

    SortedLinkedIntListBuilder(final Node<Integer> head) {
        this.head = head;
    }

    @Override
    public void add(final Integer value) {
        Preconditions.checkNotNull(value);

        final Node<Integer> newNode = new IntNode(value);
        if (head() != null) {
            newNode.setNext(head());
            head().setPrev(newNode);
        }

        this.head = newNode;
        isSorted.set(false);
    }

    @Override
    public void addAll(final Collection<Integer> values) {
        Preconditions.checkNotNull(values);

        values.forEach(this::add);
        this.isSorted.set(false);
    }

    @Override
    public void clear() {
        this.head = null;
    }

    @Override
    public Node<Integer> head() {
        return head;
    }

    @Override
    protected void setHead(final Node<Integer> newHead) {
        this.head = newHead;
    }

    @Override
    protected boolean isSorted() {
        return isSorted.get();
    }

    @Override
    protected void sorted() {
        this.isSorted.set(true);
    }

    @Override
    public SortedLinkedList<Integer> build() {
        sort();
        return new SortedLinkedIntList(head);
    }
}