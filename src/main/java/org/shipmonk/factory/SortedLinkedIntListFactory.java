package org.shipmonk.factory;

import org.shipmonk.domain.SortedLinkedIntListBuilder;
import org.shipmonk.domain.SortedLinkedListBuilder;

public class SortedLinkedIntListFactory implements SortedLinkedListFactory<Integer> {

    @Override
    public SortedLinkedListBuilder<Integer> createLinkedListBuilder() {
        return new SortedLinkedIntListBuilder();
    }
}