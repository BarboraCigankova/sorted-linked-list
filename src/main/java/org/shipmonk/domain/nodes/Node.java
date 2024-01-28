package org.shipmonk.domain.nodes;

public sealed interface Node<T> permits IntNode, StringNode {

    T data();

    Node<T> next();

    Node<T> prev();

    void setNext(final Node<T> next);

    void setPrev(final Node<T> prev);

    boolean isLowerOrEquals(final Node<T> node);

    boolean hasLowerValue(final T value);

    boolean hasEqualsValue(final T value);

    boolean hasHigherValue(final T value);
}