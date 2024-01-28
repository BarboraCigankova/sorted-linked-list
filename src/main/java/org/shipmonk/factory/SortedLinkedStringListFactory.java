package org.shipmonk.factory;

import org.shipmonk.domain.SortedLinkedListBuilder;
import org.shipmonk.domain.SortedLinkedStringListBuilder;

public class SortedLinkedStringListFactory implements SortedLinkedListFactory<String> {

    @Override
    public SortedLinkedListBuilder<String> createLinkedListBuilder() {
        return new SortedLinkedStringListBuilder();
    }

}