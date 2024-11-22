package org.bsdro.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class CacheEvictionLFU implements CacheEviction {
    private final Map<Long, CacheLine> lines;
    private final PriorityQueue<CacheLine> lfuOrder;

    public CacheEvictionLFU(int waySize) {
        lines = new HashMap<>();
        this.lfuOrder = new PriorityQueue<>(waySize, (a, b) -> {
            if(a.frequency == b.frequency) {
                return Long.compare(a.lastAccessTime, b.lastAccessTime);
            }
            return Integer.compare(a.frequency, b.frequency);
        });
    }

    @Override
    public long getTagToEvict() {
        CacheLine lfuLine = lfuOrder.poll();
        if(lfuLine != null) {
            lines.remove(lfuLine.tag);
            return lfuLine.tag;
        }
        throw new NoSuchElementException("getTagToEvict called with empty LFU queue");
    }

    @Override
    public void updateTag(long tag) {
        if(lines.containsKey(tag)) {
            CacheLine line = lines.get(tag);
            lfuOrder.remove(line);
            line.frequency++;
            line.lastAccessTime = System.nanoTime();
            lfuOrder.offer(line);
        } else {
            CacheLine newLine = new CacheLine(tag);
            lines.put(tag, newLine);
            lfuOrder.offer(newLine);
        }
    }

    private static class CacheLine {
        long tag;
        int frequency;
        Long lastAccessTime;

        public CacheLine(long tag) {
            this.tag = tag;
            this.frequency = 1;
            this.lastAccessTime = System.nanoTime();
        }
    }
}
