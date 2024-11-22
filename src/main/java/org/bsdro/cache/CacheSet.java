package org.bsdro.cache;

import java.util.ArrayList;
import java.util.List;

class CacheSet {
    private final int waySize;
    private final List<Long> lines;
    private final CacheEviction eviction;

    public CacheSet(int waySize, CacheEviction eviction) {
        this.waySize = waySize;
        this.eviction = eviction;
        lines = new ArrayList<>();
    }

    public void put(long tag) {
        if (lines.contains(tag)) {              // already in cache -> just update LRU
            eviction.updateTag(tag);
        } else if (lines.size() >= waySize) {   // not in cache, cache full -> evict least recently used, add, update LRU
            long lruTag = eviction.getTagToEvict();
            lines.remove(lruTag);
            lines.add(tag);
            eviction.updateTag(lruTag);
        }
        else {                                  // not in cache, cache not full -> add, update LRU
            lines.add(tag);
            eviction.updateTag(tag);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int lineSize = lines.size();
        for (int i = 0; i < waySize; i++) {
            sb.append("| ");
            if (i < lineSize) {
                sb.append(String.format("0x%07x", lines.get(i)));
            } else {
                sb.append("         ");
            }
            sb.append(" |");
        }
        return sb.toString();
    }
}
