package org.bsdro.cache;

interface CacheEviction {
    long getTagToEvict();

    void updateTag(long tag);
}
