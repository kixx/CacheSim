package org.bsdro.cache;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class CacheEvictionFIFO implements CacheEviction {
    private final Queue<Long> fifoOrder;

    public CacheEvictionFIFO() {
        fifoOrder = new LinkedList<>();
    }

    @Override
    public long getTagToEvict() {
        Long fifoTag = fifoOrder.poll();
        if (fifoTag != null) {
            return fifoTag;
        }
        throw new NoSuchElementException("Eviction fifo tag not found");
    }

    @Override
    public void updateTag(long tag) {
        fifoOrder.offer(tag);
    }
}
