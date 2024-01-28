package org.shipmonk.domain.nodes;

import com.google.common.base.Preconditions;

import java.util.Objects;

public final class StringNode implements Node<String> {

    private final String data;
    private Node<String> next;
    private Node<String> prev;

    public StringNode(final String data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    @Override
    public String data() {
        return this.data;
    }

    @Override
    public Node<String> next() {
        return this.next;
    }

    @Override
    public void setNext(final Node<String> next) {
        Preconditions.checkArgument(next == null || next.data() != null);
        this.next = next;
    }

    @Override
    public Node<String> prev() {
        return prev;
    }

    @Override
    public void setPrev(final Node<String> prev) {
        Preconditions.checkArgument(prev == null || prev.data() != null);
        this.prev = prev;
    }

    @Override
    public boolean isLowerOrEquals(final Node<String> node) {
        Preconditions.checkNotNull(node);
        return data.equals(node.data()) || data.compareTo(node.data()) < 0;
    }

    @Override
    public boolean hasLowerValue(final String value) {
        Preconditions.checkNotNull(value);
        return data.compareTo(value) < 0;
    }

    @Override
    public boolean hasEqualsValue(final String value) {
        Preconditions.checkNotNull(value);
        return data.equals(value);
    }

    @Override
    public boolean hasHigherValue(final String value) {
        Preconditions.checkNotNull(value);
        return data.compareTo(value) > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringNode that = (StringNode) o;
        return Objects.equals(data, that.data) && Objects.equals(next, that.next) && Objects.equals(prev, that.prev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, next, prev);
    }
}
