package org.shipmonk.domain;

import com.google.common.base.Preconditions;
import org.shipmonk.domain.nodes.Node;
import org.shipmonk.utils.LinkedListBinarySearcher;
import org.shipmonk.utils.LinkedListMergeSorter;
import org.shipmonk.utils.LinkedListMiddleFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Optional;

public sealed abstract class SortedLinkedListBuilder<T> permits SortedLinkedStringListBuilder, SortedLinkedIntListBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(SortedLinkedListBuilder.class);

    private final LinkedListMiddleFinder<T> finder = new LinkedListMiddleFinder<>();
    private final LinkedListMergeSorter<T> sorter = new LinkedListMergeSorter<>(this.finder);
    private final LinkedListBinarySearcher<T> binarySearcher = new LinkedListBinarySearcher<>(this.finder);

    public abstract void add(final T value);

    public abstract void addAll(final Collection<T> values);

    public abstract void clear();

    public abstract SortedLinkedList<T> build();

    public void remove(final T value) {
        Preconditions.checkNotNull(value);

        final Optional<Node<T>> nodeToRemoveOpt = binarySearch(value);
        if (nodeToRemoveOpt.isEmpty()) {
            LOGGER.info("No node with required value was found.");
            return;
        }

        final Node<T> nodeToRemove = nodeToRemoveOpt.get();
        final Node<T> prev = nodeToRemove.prev();
        final Node<T> next = nodeToRemove.next();
        if (prev != null && next != null) {
            prev.setNext(next);
            next.setPrev(prev);
        } else if (prev == null) {
            next.setPrev(null);
        } else {
            prev.setNext(null);
        }
    }

    protected abstract Node<T> head();

    protected abstract void setHead(final Node<T> newHead);

    protected abstract boolean isSorted();

    protected abstract void sorted();

    protected void sort() {
        if (isSorted()) {
            return;
        }

        final Node<T> newHead = this.sorter.mergeSort(head());
        setHead(newHead);
        sorted();
    }

    private Optional<Node<T>> binarySearch(final T value) {
        sort();
        return binarySearcher.search(value, head());
    }
}