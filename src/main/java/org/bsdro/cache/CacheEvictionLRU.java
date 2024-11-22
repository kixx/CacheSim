package org.bsdro.cache;

import java.util.LinkedList;

public class CacheEvictionLRU implements CacheEviction {
    private final LinkedList<Long> lruOrder;

    public CacheEvictionLRU() {
        lruOrder = new LinkedList<>();
    }

    @Override
    public long getTagToEvict() {
        return lruOrder.removeLast();
    }

    @Override
    public void updateTag(long tag) {
        lruOrder.remove(tag); // remove if tag is already in LRU
        lruOrder.addFirst(tag);
    }
}
