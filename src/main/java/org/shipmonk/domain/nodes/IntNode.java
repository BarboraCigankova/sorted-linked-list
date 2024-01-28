package org.shipmonk.domain.nodes;

import com.google.common.base.Preconditions;

import java.util.Objects;

public final class IntNode implements Node<Integer> {

    private final int data;
    private Node<Integer> next;
    private Node<Integer> prev;

    public IntNode(int data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    @Override
    public Integer data() {
        return data;
    }

    @Override
    public Node<Integer> next() {
        return this.next;
    }

    @Override
    public void setNext(final Node<Integer> next) {
        Preconditions.checkArgument(next == null || next.data() != null);
        this.next = next;
    }

    @Override
    public Node<Integer> prev() {
        return prev;
    }

    @Override
    public void setPrev(final Node<Integer> prev) {
        Preconditions.checkArgument(prev == null || prev.data() != null);
        this.prev = prev;
    }

    @Override
    public boolean isLowerOrEquals(final Node<Integer> node) {
        Preconditions.checkNotNull(node);
        return data <= node.data();
    }

    @Override
    public boolean hasLowerValue(final Integer value) {
        Preconditions.checkNotNull(value);
        return data < value;
    }

    @Override
    public boolean hasEqualsValue(final Integer value) {
        Preconditions.checkNotNull(value);
        return data == value;
    }

    @Override
    public boolean hasHigherValue(final Integer value) {
        Preconditions.checkNotNull(value);
        return data > value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntNode intNode = (IntNode) o;
        return data == intNode.data && Objects.equals(next, intNode.next) && Objects.equals(prev, intNode.prev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, next, prev);
    }
}
