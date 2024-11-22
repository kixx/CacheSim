package org.bsdro.cache;


public class Cache {
    private final int waySize;
    private final int setSize;
    private final int lineSize;

    private final CacheSet[] sets;

    public Cache(int waySize, int setSize, int lineSize, CacheEvictionStrategy strategy) {
        this.waySize = waySize;
        this.setSize = setSize;
        this.lineSize = lineSize;

        CacheEviction eviction = getCacheEviction(strategy);
        sets = new CacheSet[setSize];
        for (int i = 0; i < setSize; i++) {
            sets[i] = new CacheSet(waySize, eviction);
        }
    }

    private CacheEviction getCacheEviction(CacheEvictionStrategy strategy) {
        switch (strategy) {
            case LRU:
                return new CacheEvictionLRU();
            case LFU:
                return new CacheEvictionLFU(waySize);
            case FIFO:
                return new CacheEvictionFIFO();
            default:
                throw new IllegalArgumentException("Unsupported cache eviction strategy: " + strategy);
        }
    }

    public void put(long address) {
        int setIndex = (int) getSetIndex(address);
        long tag = getTag(address);
        System.out.println(" - idx: " + setIndex + ", tag: " + Long.toHexString(tag));
        sets[setIndex].put(tag);
    }

    private long getSetIndex(long address) {
        return (address / lineSize) % setSize;
    }

    private long getTag(long address) {
        return address / ((long) lineSize * setSize);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("        ");
        for (int i = 0; i < waySize; i++) {
            sb.append(String.format("|   Tag %02d  |", i));
        }
        sb.append("\n--------");
        for (int i = 0; i < waySize; i++) {
            sb.append("-------------");
        }
        sb.append("\n");
        for (int i = 0; i < setSize; i++) {
            sb.append("Set ").append(String.format("%02d",i)).append(": ").append(sets[i]).append("\n");
        }
        return sb.toString();
    }

}
