package org.shipmonk.factory;

import com.google.common.base.Preconditions;
import org.shipmonk.domain.SortedLinkedList;
import org.shipmonk.domain.SortedLinkedListBuilder;

import java.util.LinkedList;

public interface SortedLinkedListFactory<T> {

    SortedLinkedListBuilder<T> createLinkedListBuilder();

    default SortedLinkedList<T> fromJavaLinkedList(final LinkedList<T> list){
        Preconditions.checkNotNull(list);

        final SortedLinkedListBuilder<T> builder = createLinkedListBuilder();
        list.forEach(builder::add);
        return builder.build();
    }

}