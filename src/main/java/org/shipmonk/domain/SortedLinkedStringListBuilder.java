package org.shipmonk.domain;

import com.google.common.base.Preconditions;
import org.shipmonk.domain.nodes.Node;
import org.shipmonk.domain.nodes.StringNode;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

public final class SortedLinkedStringListBuilder extends SortedLinkedListBuilder<String> {

    private final AtomicBoolean isSorted = new AtomicBoolean(false);
    private Node<String> head;

    public SortedLinkedStringListBuilder() {
        this.head = null;
    }

    SortedLinkedStringListBuilder(final Node<String> head) {
        this.head = head;
    }

    @Override
    public void add(final String value) {
        Preconditions.checkNotNull(value);

        final Node<String> newNode = new StringNode(value);
        if (head() != null) {
            newNode.setNext(head());
            head().setPrev(newNode);
        }

        this.head = newNode;
        this.isSorted.set(false);
    }

    @Override
    public void addAll(final Collection<String> values) {
        Preconditions.checkNotNull(values);

        values.forEach(this::add);
        this.isSorted.set(false);
    }

    @Override
    public void clear() {
        this.head = null;
    }

    @Override
    public Node<String> head() {
        return head;
    }

    @Override
    protected void setHead(final Node<String> newHead) {
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
    public SortedLinkedList<String> build() {
        sort();
        return new SortedLinkedStringList(this.head);
    }
}