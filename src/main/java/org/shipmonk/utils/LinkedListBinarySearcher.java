package org.shipmonk.utils;

import org.shipmonk.domain.nodes.Node;

import java.util.Optional;

public class LinkedListBinarySearcher<T> {

    private final LinkedListMiddleFinder<T> finder;

    public LinkedListBinarySearcher(final LinkedListMiddleFinder<T> finder) {
        this.finder = finder;
    }

    public Optional<Node<T>> search(final T value, final Node<T> head) {
        Node<T> start = head;
        Node<T> last = null;

        do {
            Node<T> middle = this.finder.getMiddle(start, last);

            if (middle == null)
                return Optional.empty();

            if (middle.hasEqualsValue(value)) {
                return Optional.of(middle);
            } else if (middle.hasHigherValue(value)) {
                last = middle;
            } else {
                start = middle.next();
            }
        } while (last == null || last != start);

        return Optional.empty();
    }
}
